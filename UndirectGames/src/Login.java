import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class GameTab extends JPanel {
	public GameTab() {
		setLayout(new GridLayout(5, 2));
		JLabel image1 = new JLabel();
		JLabel image2 = new JLabel();
		JLabel image3 = new JLabel();
		JLabel image4 = new JLabel();
		JLabel image5 = new JLabel();
		JLabel image6 = new JLabel();
		JLabel image7 = new JLabel();
		JLabel image8 = new JLabel();
		JLabel image9 = new JLabel();
		JLabel image10 = new JLabel();
		
		
		Path path1 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path2 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path3 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path4 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path5 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path6 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path7 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path8 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path9 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		Path path10 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "header (1).jpg");
		try {
			byte[] readAllBytes1 = Files.readAllBytes(path1);
			byte[] readAllBytes2 = Files.readAllBytes(path2);
			byte[] readAllBytes3 = Files.readAllBytes(path3);
			byte[] readAllBytes4 = Files.readAllBytes(path4);
			byte[] readAllBytes5 = Files.readAllBytes(path5);
			byte[] readAllBytes6 = Files.readAllBytes(path6);
			byte[] readAllBytes7 = Files.readAllBytes(path7);
			byte[] readAllBytes8 = Files.readAllBytes(path8);
			byte[] readAllBytes9 = Files.readAllBytes(path9);
			byte[] readAllBytes10 = Files.readAllBytes(path10);
			ImageIcon icon1 = new ImageIcon(readAllBytes1);
			ImageIcon icon2 = new ImageIcon(readAllBytes1);
			ImageIcon icon3 = new ImageIcon(readAllBytes1);
			ImageIcon icon4 = new ImageIcon(readAllBytes1);
			ImageIcon icon5 = new ImageIcon(readAllBytes1);
			ImageIcon icon6 = new ImageIcon(readAllBytes1);
			ImageIcon icon7 = new ImageIcon(readAllBytes1);
			ImageIcon icon8 = new ImageIcon(readAllBytes1);
			ImageIcon icon9 = new ImageIcon(readAllBytes1);
			ImageIcon icon10 = new ImageIcon(readAllBytes1);
			image1.setIcon(icon1);
			image2.setIcon(icon2);
			image3.setIcon(icon3);
			image4.setIcon(icon4);
			image5.setIcon(icon5);
			image6.setIcon(icon6);
			image7.setIcon(icon7);
			image8.setIcon(icon8);
			image9.setIcon(icon9);
			image10.setIcon(icon10);
		} catch (Exception e) {
		}
		add(image1);
		add(image2);
		add(image3);
		add(image4);
		add(image5);
		add(image6);
		add(image7);
		add(image8);
		add(image9);
		add(image10);
		
	}
}

class Basic extends JFrame {
	
	GameTab gameTab;
	
	public Basic() {
		gameTab = new GameTab();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("게임 탭", gameTab);
		tabbedPane.setPreferredSize(new Dimension(1000, 1000));
		
		add(tabbedPane);
		pack();
	}
}


// 로그인 화면
public class Login extends JFrame implements ActionListener {
	
	public Login() {
		super("로그인");
		
		JPanel centerPnl = new JPanel();
		JPanel southPnl = new JPanel();
		JPanel northPnl = new JPanel();
		JLabel jojoImage = new JLabel();
		JLabel adImage = new JLabel();
		Path path = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "조조소프트.png");
		Path path2 = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "기적의 검.jpg");
		try {
			byte[] bytes = Files.readAllBytes(path);
			byte[] bytes2 = Files.readAllBytes(path2);
			ImageIcon icon = new ImageIcon(bytes);
			ImageIcon icon2 = new ImageIcon(bytes2);
			jojoImage.setIcon(icon);
			adImage.setIcon(icon2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel idLbl = new JLabel("아이디 : ");
		idLbl.setBounds(189, 62, 48, 15);
		JLabel pwLbl = new JLabel("비밀번호 : ");
		pwLbl.setBounds(189, 103, 60, 15);
		
		JTextField idField = new JTextField(10);
		idField.setBounds(287, 59, 116, 21);
		JTextField pwField = new JTextField(10);
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
		JOptionPane.showMessageDialog(this, "확인용");
	}
	
	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}


