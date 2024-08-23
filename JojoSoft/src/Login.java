import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
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

import materials.DBUtil;
import materials.Function;
import materials.User;
import picture.IconManager;
import picture.PictureDAO;

// 회원가입 버튼을 눌렀을 때 나오는 다이얼로그 창 화면
class AccessionMember extends JDialog implements ActionListener {
	private JTextField idField;
	private JPasswordField pwField;
	private JPasswordField pwFieldCheck;
	private JTextField phoneNumField;
	private JTextField nickNameField;
	private JTextField birthField;
	private JLabel warningLbl;
	private Connection conn;

	public AccessionMember(Connection conn, List<Integer> pictureNumList) {
		this.conn = conn;
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
		String pw = pwField.getText();
		String pwCheck = pwFieldCheck.getText();
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
			String result = Function.checkUser(id, nickName, birth, phoneNumber, conn);
			if (result == null) {
				this.setVisible(false);
				Function.insertUser(conn, id, pw, nickName, birth, phoneNumber);
				JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
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

// 로그인 화면
public class Login extends JFrame implements ActionListener {
	private JTextField idField;
	private JPasswordField pwField;
	Connection conn;
	List<Integer> pictureNumList;

	private JLabel adImage;

	public Login() {
		super("로그인");

		conn = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
		} catch (Exception e) {
			e.printStackTrace();
		}
		pictureNumList = new ArrayList<Integer>();
		Function.findAdIdAndAddToList(pictureNumList, conn);

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
		loginBtn.setBounds(189, 183, 97, 23);
		loginBtn.addActionListener(this);

		JButton joinMemberBtn = new JButton("회원 가입");
		joinMemberBtn.setBounds(306, 183, 97, 23);
		joinMemberBtn.addActionListener(this);

		northPnl.add(jojoImage);
		centerPnl.setLayout(null);
		centerPnl.add(idLbl);
		centerPnl.add(idField);
		centerPnl.add(pwLbl);
		centerPnl.add(pwField);
		centerPnl.add(loginBtn);
		centerPnl.add(joinMemberBtn);
		southPnl.add(adImage);

		getContentPane().add(centerPnl, "Center");
		getContentPane().add(southPnl, "South");
		getContentPane().add(northPnl, "North");

		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AccessionMember accessionMember = new AccessionMember(conn, pictureNumList);
		String command = e.getActionCommand();
		if (command.equals("로그인")) {

			User user = Function.findMember(idField.getText(), pwField.getText(), conn);

			if (user == null) {
				JOptionPane.showMessageDialog(this, "없는 회원입니다.");
			} else {
				JOptionPane.showMessageDialog(this, "환영합니다.");
				this.setVisible(false);
				new GameMain().printMain();
			}

		} else if (command.equals("회원 가입")) {
			accessionMember.setVisible(true);
			idField.setText("");
			pwField.setText("");
			int pictureNum = Function.randomPictureNum(pictureNumList);
			ImageIcon adIcon = IconManager.getInstance().getIconByKey(pictureNum);
			adImage.setIcon(adIcon);
		}
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
