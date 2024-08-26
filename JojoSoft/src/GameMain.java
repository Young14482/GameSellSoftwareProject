import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import materials.Game;
import materials.GameDAO;
import materials.JLableFactory;
import materials.User;
import picture.IconManager;
import picture.PictureDAO;

// 메인 패널
class PnlBasic extends JPanel {
	private JPanel pnlToolBar;
	private JPanel pnlMainInfo;
	private JScrollPane js;

	public PnlBasic() {
		setLayout(new BorderLayout());
		pnlToolBar = new PnlToolBar();
		add(pnlToolBar, BorderLayout.NORTH);

		pnlMainInfo = new PnlMainInfo();
		js = new JScrollPane(pnlMainInfo);
		add(js, BorderLayout.CENTER);
	}
}

// 상단부 검색바 구성
class PnlToolBar extends JPanel {
	public PnlToolBar() {
		setLayout(new BorderLayout());
		// pnlEast: 로그인,로그아웃, 사용자 닉네임 알려주는 패널
		JPanel pnlEast = new JPanel(new GridLayout(3, 0));
		JLabel lnlNickname = new JLabel(User.getCurUser().getUserNickName() + "님 환영합니다");
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

		setPreferredSize(new Dimension(900, 100));
	}
}

// 패널 테스트용 프레임
class PnlTest extends JFrame {
	private PnlBasic pnlBasic;

	public PnlTest() {
		pnlBasic = new PnlBasic();
		add(pnlBasic);
		setSize(920, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

// 게임 메인화면이 나올 패널
class PnlMainInfo extends JPanel {
	private PictureDAO pictureDAO;
	private PnlProduction pnlProduction;
	private PnlGames pnlGames;

	public PnlMainInfo() {
		pnlProduction = new PnlProduction();

		pnlGames = new PnlGames();

		setLayout(new BorderLayout());
		add(pnlProduction, BorderLayout.WEST);
		add(pnlGames, BorderLayout.CENTER);
	}
}

// 게임제조사 이미지 담을 패널
class PnlProduction extends JPanel {
	private JLableFactory lableFactory;

	public PnlProduction() {
		lableFactory = new JLableFactory();
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(BorderFactory.createLineBorder(Color.RED));
		setPreferredSize(new Dimension(160, 0));

		JLabel lblProduction1 = lableFactory.createLblwithIcon(6);
		JLabel lblProduction2 = lableFactory.createLblwithIcon(7);
		JLabel lblProduction3 = lableFactory.createLblwithIcon(8);
		JLabel lblProduction4 = lableFactory.createLblwithIcon(9);
		JLabel lblProduction5 = lableFactory.createLblwithIcon(10);
		JLabel lblProduction6 = lableFactory.createLblwithIcon(11);

		add(lblProduction1);
		add(lblProduction2);
		add(lblProduction3);
		add(lblProduction4);
		add(lblProduction5);
		add(lblProduction6);
	}

}

// 게임들을 본격적으로 띄울 탭 패널
class PnlGames extends JTabbedPane {
	private PnlLatestGames latestGames;

	public PnlGames() {
		latestGames = new PnlLatestGames();
		add(latestGames, "최신게임");
	}
}

// 게임 정보 패널부분
class PnlLatestGames extends JPanel {
	private PictureDAO pictureDAO;
	private GameDAO gameDAO;
	private JLableFactory lableFactory;

	public PnlLatestGames() {
		lableFactory = new JLableFactory();
		gameDAO = new GameDAO();
		pictureDAO = new PictureDAO();
		setPreferredSize(new Dimension(500, 100 + 85 * 10));
		setBorder(BorderFactory.createLineBorder(Color.cyan));
		Game g1 = gameDAO.getGame(1); // 임시로 게임 1번만 불러옴
		Game g2 = gameDAO.getGame(2); // 임시로 게임 1번만 불러옴
		Game g3 = gameDAO.getGame(3); // 임시로 게임 1번만 불러옴

		JPanel pnlGame1 = createGamePnl(g1);
		JPanel pnlGame2 = createGamePnl(g2);
		JPanel pnlGame3 = createGamePnl(g3);

		add(pnlGame1);
		add(pnlGame2);
		add(pnlGame3);
	}

	private JPanel createGamePnl(Game g) {
		JPanel pnlGame1 = new JPanel();
		pnlGame1.setLayout(new BorderLayout());

		// 게임 이름 부분
		JPanel pnlGameMid = new JPanel(new GridLayout(3, 0));
		JLabel lblGameName = lableFactory.createLblWithFont("   " + g.getGame_name());

		// 게임 장르 + 유형
		JLabel lblGameGenre = lableFactory
				.createLblWithFont("   장르: " + g.getGame_genre() + " | 게임유형: " + g.getGame_category());

		// 게임 제작사
		JLabel lblGameProdu = lableFactory.createLblWithFont("   제작사: " + g.getGame_production());

		pnlGameMid.add(lblGameName);
		pnlGameMid.add(lblGameGenre);
		pnlGameMid.add(lblGameProdu);

		// 게임 사진
		JLabel lblGameProfile = new JLabel();
		lblGameProfile.setPreferredSize(new Dimension(150, 85));
		lblGameProfile.setIcon(IconManager.getInstance().getIconByKey(g.getGame_profile()));
		
		// 게임 가격
		JPanel pnlEast = new JPanel();
		JLabel lblPrice = lableFactory.createLblWithFont("정상가: " + g.getGame_price() + "원");
		
		pnlEast.add(lblPrice);

		pnlGame1.add(lblGameProfile, BorderLayout.WEST);
		pnlGame1.add(pnlGameMid, BorderLayout.CENTER);
		pnlGame1.add(pnlEast,BorderLayout.EAST);
		pnlGame1.setPreferredSize(new Dimension(700, 85));
		pnlGame1.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		return pnlGame1;
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
//		Path path = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프", "bandai.png");
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
