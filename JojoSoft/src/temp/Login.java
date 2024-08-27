package temp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lombok.Getter;
import materials.Function;
import picture.IconManager;
import picture.PictureDAO;
import user.User;
import user.UserDAO;

// 회원가입 버튼을 눌렀을 때 나오는 다이얼로그 창 화면
class AccessionMember extends JDialog implements ActionListener {
	private JTextField idField;
	private JPasswordField pwField;
	private JPasswordField pwFieldCheck;
	private JTextField phoneNumField;
	private JTextField nickNameField;
	private JTextField birthField;
	private JLabel warningLbl;
	private UserDAO userDAO;

	public AccessionMember(List<Integer> pictureNumList, JFrame frame) {
		userDAO = new UserDAO();
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel();
		JPanel pnlNorth = new JPanel();

		idField = new JTextField(10);
		idField.setBounds(280, 10, 116, 21);
		pwField = new JPasswordField(10);
		pwField.setBounds(280, 40, 116, 21);
		pwFieldCheck = new JPasswordField(10);
		pwFieldCheck.setBounds(280, 70, 116, 21);
		phoneNumField = new JTextField(10);
		phoneNumField.setBounds(280, 100, 116, 21);
		nickNameField = new JTextField(10);
		nickNameField.setBounds(280, 130, 116, 21);
		birthField = new JTextField(10);
		birthField.setBounds(280, 160, 116, 21);

		JLabel idLbl = new JLabel("아이디 : ");
		idLbl.setBounds(180, 10, 60, 15);
		JLabel pwLbl = new JLabel("비밀번호 : ");
		pwLbl.setBounds(180, 40, 77, 15);
		JLabel pwLblCheck = new JLabel("비밀번호 확인 : ");
		pwLblCheck.setBounds(180, 70, 100, 15);
		JLabel phoneLbl = new JLabel("전화번호 : ");
		phoneLbl.setBounds(180, 100, 100, 15);
		JLabel nickNameLbl = new JLabel("닉네임 : ");
		nickNameLbl.setBounds(180, 130, 67, 15);
		JLabel birthLbl = new JLabel("생년월일 : ");
		birthLbl.setBounds(180, 160, 77, 15);

		int randomPictureNum = Function.randomPictureNum(pictureNumList);

		JLabel JojoImage = new JLabel();
		ImageIcon JojoIcon = IconManager.getInstance().getIconByKey(1);
		JojoImage.setIcon(JojoIcon);

		JLabel adImage = new JLabel();
		ImageIcon adIcon = IconManager.getInstance().getIconByKey(randomPictureNum);
		adImage.setIcon(adIcon);

		warningLbl = new JLabel("");
		warningLbl.setHorizontalAlignment(SwingConstants.CENTER);
		warningLbl.setBounds(115, 225, 343, 21);
		pnlCenter.add(warningLbl);

		pnlCenter.setLayout(null);

		pnlCenter.add(idLbl);
		pnlCenter.add(idField);
		pnlCenter.add(pwLbl);
		pnlCenter.add(pwField);
		pnlCenter.add(pwLblCheck);
		pnlCenter.add(pwFieldCheck);
		pnlCenter.add(phoneLbl);
		pnlCenter.add(phoneNumField);
		pnlCenter.add(nickNameLbl);
		pnlCenter.add(nickNameField);
		pnlCenter.add(birthLbl);
		pnlCenter.add(birthField);
		pnlNorth.add(JojoImage);
		pnlSouth.add(adImage);
		getContentPane().add(pnlCenter, "Center");

		JButton registBtn = new JButton("등록하기");
		registBtn.setBounds(236, 195, 97, 23);
		pnlCenter.add(registBtn);
		registBtn.addActionListener(this);

		getContentPane().add(pnlSouth, "South");
		getContentPane().add(pnlNorth, "North");

		setModal(true);
		setSize(600, 600);
		setLocationRelativeTo(frame);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void clearIdPw() {
		idField.setText("");
		pwField.setText("");
	}

	// 회원가입 버튼을 눌렀을 때의 액션리스너
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = idField.getText();
		String pw = String.valueOf(pwField.getPassword());
		String pwCheck = String.valueOf(pwFieldCheck.getPassword());
		String nickName = nickNameField.getText();
		String birth = birthField.getText();
		String phoneNumber = phoneNumField.getText();

		if (id.equals("") || pw.equals("") || nickName.equals("") || birth.equals("")) {
			warningLbl.setText("모든 칸에 입력하세요");
		} else if (!pw.equals(pwCheck)) {
			warningLbl.setText("비밀번호와 비밀번호 확인이 서로 일치하지 않습니다.");
		} else if (Function.containsKorean(id)) {
			warningLbl.setText("아이디에 한글을 포함할 수 없습니다.");
		} else {
			String result = userDAO.checkUser(id, nickName, birth, phoneNumber);
			if (result == null) {
				this.setVisible(false);
				userDAO.insertUser(id, pw, nickName, birth, phoneNumber);
				JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
			} else if (result.equals("닉네임")) {
				warningLbl.setText("이미 존재하는 닉네임 입니다.");
			} else if (result.equals("아이디")) {
				warningLbl.setText("이미 존재하는 아이디 입니다.");
			} else if (result.equals("생년월일")) {
				warningLbl.setText("생년월일의 양식을 맞춰주세요 ex : 2012-08-04");
			} else if (result.equals("전화번호")) {
				warningLbl.setText("이미 존재하는 전화번호 입니다.");
			} else if (result.equals("전화번호양식")) {
				warningLbl.setText("전화번호 양식이 잘못되었습니다. ex : 010-1234-1234");
			}
		}
	}
}

class FindId extends JDialog {
	private JTextField phoneField;

	public FindId(List<Integer> pictureNumList, Login login) {
		JLabel adImage = new JLabel();
		getContentPane().add(adImage, BorderLayout.SOUTH);

		login.changeAd(adImage);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel requestLbl = new JLabel("가입시 작성한 전화번호를 입력해주세요.");
		requestLbl.setFont(new Font("굴림", Font.PLAIN, 15));
		requestLbl.setBounds(168, 36, 283, 34);
		panel.add(requestLbl);

		phoneField = new JTextField();
		phoneField.setBounds(283, 80, 116, 21);
		panel.add(phoneField);
		phoneField.setColumns(10);

		JLabel phoneLbl = new JLabel("전화번호 :");
		phoneLbl.setBounds(197, 83, 84, 15);
		panel.add(phoneLbl);

		JLabel resultLbl = new JLabel();
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		resultLbl.setBounds(41, 171, 508, 28);
		panel.add(resultLbl);

		JButton btn = new JButton("확인");
		btn.setBounds(250, 125, 97, 23);
		btn.addActionListener(login);
		panel.add(btn);

		JButton closeBtn = new JButton("창 닫기");
		closeBtn.setBounds(250, 160, 97, 23);
		closeBtn.addActionListener(login);
		panel.add(closeBtn);

		setModal(true);
		setSize(600, 400);
		setLocationRelativeTo(login);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public JTextField getPhoneField() {
		return phoneField;
	}

}

@Getter
class FindPw extends JDialog {
	private JTextField idField;
	private JLabel phoneLbl;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JLabel phoneLbl2;
	private JButton btn;
	private JButton closeBtn;
	private JLabel requestLbl;

	public FindPw(List<Integer> pictureNumList, Login login) {

		JLabel adImage = new JLabel();
		getContentPane().add(adImage, BorderLayout.SOUTH);

		login.changeAd(adImage);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		requestLbl = new JLabel("회원님의 아이디를 입력해주세요");
		requestLbl.setFont(new Font("굴림", Font.PLAIN, 15));
		requestLbl.setBounds(195, 36, 283, 34);
		panel.add(requestLbl);

		idField = new JTextField();
		idField.setBounds(283, 75, 116, 21);
		panel.add(idField);
		idField.setColumns(10);

		phoneLbl = new JLabel("아이디 :");
		phoneLbl.setBounds(197, 78, 84, 15);
		panel.add(phoneLbl);

		passwordField = new JPasswordField();
		passwordField.setBounds(283, 75, 116, 21);
		panel.add(passwordField);
		passwordField.setColumns(10);
		passwordField.setVisible(false);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(283, 100, 116, 21);
		panel.add(passwordField2);
		passwordField2.setColumns(10);
		passwordField2.setVisible(false);

		phoneLbl2 = new JLabel("비밀번호 재입력 :");
		phoneLbl2.setBounds(170, 103, 120, 15);
		panel.add(phoneLbl2);
		phoneLbl2.setVisible(false);

		JLabel resultLbl = new JLabel();
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		resultLbl.setBounds(41, 171, 508, 28);
		panel.add(resultLbl);

		btn = new JButton("확인");
		btn.setActionCommand("pw확인");
		btn.setBounds(250, 125, 97, 23);
		btn.addActionListener(login);
		panel.add(btn);

		closeBtn = new JButton("창 닫기");
		closeBtn.setActionCommand("pw창 닫기");
		closeBtn.setBounds(250, 160, 97, 23);
		closeBtn.addActionListener(login);
		panel.add(closeBtn);

		setModal(true);
		setSize(600, 400);
		setLocationRelativeTo(login);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public String getPW() {
		return String.valueOf(passwordField.getPassword());
	}

	public String getPW2() {
		return String.valueOf(passwordField2.getPassword());
	}
}

// 로그인 화면
public class Login extends JFrame implements ActionListener {
	private JTextField idField;
	private JPasswordField pwField;
	private UserDAO userDAO;
	private PictureDAO pictureDAO;
	private List<Integer> pictureNumList;

	private JLabel adImage;

	public Login() {
		super("로그인");
		userDAO = new UserDAO();
		pictureDAO = new PictureDAO();
		pictureNumList = pictureDAO.findAdIdAndAddToList();

		JPanel centerPnl = new JPanel();
		JPanel southPnl = new JPanel();
		JPanel northPnl = new JPanel();
		JLabel jojoImage = new JLabel();
		adImage = new JLabel();
		int pictureNum = Function.randomPictureNum(pictureNumList);

		ImageIcon jojoIcon = IconManager.getInstance().getIconByKey(1);
		jojoImage.setIcon(jojoIcon);

		ImageIcon adIcon = IconManager.getInstance().getIconByKey(pictureNum);
		adImage.setIcon(adIcon);

		JLabel idLbl = new JLabel("아이디 : ");
		idLbl.setBounds(189, 62, 60, 15);
		JLabel pwLbl = new JLabel("비밀번호 : ");
		pwLbl.setBounds(189, 103, 80, 15);

		idField = new JTextField(10);
		idField.setBounds(287, 59, 116, 21);
		pwField = new JPasswordField(10);
		pwField.setBounds(287, 100, 116, 21);

		JButton loginBtn = new JButton("로그인");
		loginBtn.setBounds(200, 150, 90, 23);
		loginBtn.addActionListener(this);

		JButton joinMemberBtn = new JButton("회원 가입");
		joinMemberBtn.setBounds(299, 150, 97, 23);
		joinMemberBtn.addActionListener(this);

		JButton findIdBtn = new JButton("아이디 찾기");
		findIdBtn.setBounds(228, 190, 141, 23);
		findIdBtn.addActionListener(this);

		JButton findPwBtn = new JButton("비밀번호 찾기");
		findPwBtn.setBounds(229, 220, 140, 23);
		findPwBtn.addActionListener(this);

		northPnl.add(jojoImage);
		centerPnl.setLayout(null);
		centerPnl.add(idLbl);
		centerPnl.add(idField);
		centerPnl.add(pwLbl);
		centerPnl.add(pwField);
		centerPnl.add(loginBtn);
		centerPnl.add(joinMemberBtn);
		centerPnl.add(findIdBtn);
		centerPnl.add(findPwBtn);
		southPnl.add(adImage);

		getContentPane().add(centerPnl, "Center");
		getContentPane().add(southPnl, "South");
		getContentPane().add(northPnl, "North");

		setSize(600, 600);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//		System.out.println(d);
		setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FindId findId = new FindId(pictureNumList, this);
		FindPw findPw = new FindPw(pictureNumList, this);

		AccessionMember accessionMember = new AccessionMember(pictureNumList, this);
		String command = e.getActionCommand();
		if (command.equals("로그인")) {

			User user = userDAO.findMember(idField.getText(), String.valueOf(pwField.getPassword()));

			if (user == null) {
				JOptionPane.showMessageDialog(this, "없는 회원입니다.");
			} else {
				JOptionPane.showMessageDialog(this, "환영합니다.");
				User.setCurUser(user);
				this.setVisible(false);
				new GameMain().printMain();
			}

		} else if (command.equals("회원 가입")) {
			accessionMember.setVisible(true);
			idField.setText("");
			pwField.setText("");
			changeAd(adImage);
		} else if (command.equals("아이디 찾기")) {
			findId.setVisible(true);
			changeAd(adImage);
		} else if (command.equals("비밀번호 찾기")) {
			findPw.setVisible(true);
			changeAd(adImage);
		} else if (command.equals("확인")) {
			findId = (FindId) ((JButton) e.getSource()).getTopLevelAncestor();
			String text = findId.getPhoneField().getText();
			int result = userDAO.findIdToUsingPhoneNumber(text);
			if (result == 1) {
				findId.setVisible(false);
			}
		} else if (command.equals("창 닫기")) {
			findId = (FindId) ((JButton) e.getSource()).getTopLevelAncestor();
			findId.setVisible(false);
		} else if (command.equals("pw확인")) {
			findPw = (FindPw) ((JButton) e.getSource()).getTopLevelAncestor();
			int result = userDAO.findIdToUsingId("아이디로 찾기", findPw.getIdField().getText());

			if (result == 1) {
				findPw.getRequestLbl().setText("변경할 비밀번호를 입력해주세요.");
				findPw.getRequestLbl().setBounds(185, 36, 283, 34);
				findPw.getPhoneLbl().setText("새 비밀번호 :");
				findPw.getPhoneLbl().setBounds(170, 78, 84, 15);
				findPw.getIdField().setVisible(false);
				findPw.getPhoneLbl2().setVisible(true);
				findPw.getPasswordField().setVisible(true);
				findPw.getPasswordField2().setVisible(true);
				findPw.getBtn().setActionCommand("pw확인2");
			}
		} else if (command.equals("pw창 닫기")) {
			findPw = (FindPw) ((JButton) e.getSource()).getTopLevelAncestor();
			findPw.setVisible(false);
		} else if (command.equals("pw확인2")) {
			findPw = (FindPw) ((JButton) e.getSource()).getTopLevelAncestor();
			if (findPw.getPW().equals(findPw.getPW2())) {
				int result = userDAO.changeUserInfo("비밀번호 변경", findPw.getIdField().getText(), findPw.getPW(), null, null, null);
				if (result == 1) {
					JOptionPane.showMessageDialog(findPw, "비밀번호 변경이 완료되었습니다.");
					findPw.setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(findPw, "입력한 비밀번호가 동일하지 않습니다.");
			}
		}
	}

	public void changeAd(JLabel adImage) {
		int pictureNum = Function.randomPictureNum(pictureNumList);
		ImageIcon adIcon = IconManager.getInstance().getIconByKey(pictureNum);
		adImage.setIcon(adIcon);
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
