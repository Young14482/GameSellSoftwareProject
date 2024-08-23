import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.sun.javafx.collections.SetListenerHelper;

import materials.PictureDAO;
import sun.net.www.content.image.jpeg;

// 메인 패널
class PnlBasic extends JPanel {
	private JPanel pnlToolBar;
	private JPanel pnlMainInfo;

	public PnlBasic() {
		setLayout(new BorderLayout());
		pnlToolBar = new PnlToolBar();
		pnlToolBar.setBorder(new LineBorder(Color.RED));
		add(pnlToolBar, BorderLayout.NORTH);
		
		pnlMainInfo = new PnlMainInfo();
		add(pnlMainInfo, BorderLayout.WEST);
	}
}
// 상단부 검색바 구성
class PnlToolBar extends JPanel {
	public PnlToolBar() {
		setLayout(new BorderLayout());
		// pnlEast: 로그인,로그아웃, 사용자 닉네임 알려주는 패널
		JPanel pnlEast = new JPanel(new GridLayout(3, 0));
		JLabel lnlNickname = new JLabel("스폰지밥님 환영합니다");
		JButton btnLogout = new JButton("로그아웃");
		JButton btnCart = new JButton("장바구니");

		pnlEast.add(lnlNickname);
		pnlEast.add(btnLogout);
		pnlEast.add(btnCart);
		pnlEast.setBorder(new LineBorder(Color.RED));
		
		// 검색바랑 검색바 밑 버튼들 나누기 위한 패널생성
		JPanel pnlCenter = new JPanel(new GridLayout(2, 0));
		pnlCenter.setBorder(new LineBorder(Color.BLACK));
		
		// 검색바 패널
		JPanel pnlSerch = new JPanel();
		pnlSerch.setBorder(new LineBorder(Color.CYAN));
		JButton btnLogo = new JButton("JOJOSOFT");
		JTextField tfSeach = new JTextField(20);
		JButton btnSeach = new JButton("검색");

		pnlSerch.add(btnLogo);
		pnlSerch.add(tfSeach);
		pnlSerch.add(btnSeach);
		
		// 게임,프로모션 버튼들 담을 패널
		JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlBtns.setBorder(new LineBorder(Color.green));
		JButton btnGame = new JButton("게임");
		JButton btnPromotion = new JButton("프로모션");
		
		pnlBtns.add(btnGame);
		pnlBtns.add(btnPromotion);
		
		pnlCenter.add(pnlSerch);
		pnlCenter.add(pnlBtns);

		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(700,100));
	}
}
// 패널 테스트용 프레임
class PnlTest extends JFrame {
	private PnlBasic pnlBasic;
	
	public PnlTest() {
		pnlBasic = new PnlBasic();
		add(pnlBasic);
		setSize(720, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
// 게임 메인화면이 나올 패널
class PnlMainInfo extends JPanel{
	private PictureDAO pictureDAO;
	private PnlProduction pnlProduction;
	public PnlMainInfo() {
		pnlProduction = new PnlProduction();
		setLayout(new BorderLayout());
		add(pnlProduction,BorderLayout.WEST);
	}
}

// 게임제조사 이미지 담을 패널
class PnlProduction extends JPanel {
	private PictureDAO pictureDAO;
	public PnlProduction() {
		pictureDAO = new PictureDAO();
		setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblProduction1 = new JLabel();
		lblProduction1.setPreferredSize(new Dimension(150,85));
		lblProduction1.setIcon(new ImageIcon(pictureDAO.getData(6)));
		add(lblProduction1);
	}
}

// 패널 만든거 확인용 메인클래스
public class GameMain {
	public void printMain() {
		new PnlTest().setVisible(true);
	}
	
	public static void main(String[] args) {
		new PnlTest().setVisible(true);
//		PictureDAO pictureDAO = new PictureDAO();
//		Path path = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프", "capcom.jpg");
//		
//		try {
//			byte[] readAllBytes = Files.readAllBytes(path);
//			
//			int row = pictureDAO.insert(path.getFileName().toString(), readAllBytes);
//			System.out.println("삽입된 행 개수: " + row);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
