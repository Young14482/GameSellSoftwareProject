package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import game.Game;
import game.GameDAO;
import materials.DataManager;
import materials.JLableFactory;
import materials.JPanelFactory;
import picture.IconManager;
import user.User;

// 상단부 검색바 구성
class PnlToolBar extends JPanel {
	private JLabel lnlNickname;

	public PnlToolBar(PnlBasic pnlBasic) {
		setLayout(new BorderLayout());
		// pnlEast: 로그인,로그아웃, 사용자 닉네임 알려주는 패널
		JPanel pnlEast = new JPanel(new GridLayout(3, 0));
		lnlNickname = new JLabel(User.getCurUser().getUserNickName() + "님 환영합니다");
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(pnlBasic);
		JButton btnCart = new JButton("장바구니");
		btnCart.addActionListener(pnlBasic);

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
		btnLogo.addActionListener(pnlBasic);
		JTextField tfSeach = new JTextField(20);
		JButton btnSeach = new JButton("검색");

		pnlSerch.add(btnLogo);
		pnlSerch.add(tfSeach);
		pnlSerch.add(btnSeach);

		// 게임,프로모션 버튼들 담을 패널
		JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlBtns.setBorder(new LineBorder(Color.green));
		JButton btnGame = new JButton("게임");
		btnGame.addActionListener(pnlBasic);
		JButton btnPromotion = new JButton("프로모션");
		JButton userInfoBtn = new JButton("회원 정보");
		JButton chargeMoneyBtn = new JButton("금액 충전 하기");
		chargeMoneyBtn.addActionListener(pnlBasic);
		userInfoBtn.addActionListener(pnlBasic);

		pnlBtns.add(btnGame);
		pnlBtns.add(btnPromotion);
		pnlBtns.add(userInfoBtn);
		pnlBtns.add(chargeMoneyBtn);

		pnlCenter.add(pnlSerch);
		pnlCenter.add(pnlBtns);

		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);

		setPreferredSize(new Dimension(900, 100));
	}

	public JLabel getLnlNickname() {
		return lnlNickname;
	}
}

// 게임 메인화면이 나올 패널
class PnlMainInfo extends JPanel {
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

	public PnlProduction() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(BorderFactory.createLineBorder(Color.RED));
		setPreferredSize(new Dimension(160, 0));

		JLabel lblProduction1 = JLableFactory.createLblwithIcon(6);
		JLabel lblProduction2 = JLableFactory.createLblwithIcon(7);
		JLabel lblProduction3 = JLableFactory.createLblwithIcon(8);
		JLabel lblProduction4 = JLableFactory.createLblwithIcon(9);
		JLabel lblProduction5 = JLableFactory.createLblwithIcon(10);
		JLabel lblProduction6 = JLableFactory.createLblwithIcon(11);

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
	private PnlRecommendedGame recommendedGame;
	private PnlDiscountGame discountGame;

	public PnlGames() {
		latestGames = new PnlLatestGames();
		recommendedGame = new PnlRecommendedGame();
		discountGame = new PnlDiscountGame();
		add(latestGames, "최신게임");
		add(recommendedGame, "추천게임");
		add(discountGame, "할인게임");
	}
}

// 게임 정보 패널부분
class PnlLatestGames extends JPanel {
	private GameDAO gameDAO;
	private JPanelFactory panelFactory;

	public PnlLatestGames() {
		panelFactory = new JPanelFactory();
		gameDAO = new GameDAO();
		setBorder(BorderFactory.createLineBorder(Color.cyan));
		List<Game> list = gameDAO.getSearchedListDefault();
		setPreferredSize(new Dimension(500, 100 + 85 * list.size()));

		for (Game game : list) {
			JPanel pnlGame = panelFactory.createGamePnl(game);
			add(pnlGame);
		}
	}

	private JPanel createGamePnl(Game g) {
		JPanel pnlGame1 = new JPanel();
		pnlGame1.setLayout(new BorderLayout());

		// 게임 이름 부분
		JPanel pnlGameMid = new JPanel(new GridLayout(3, 0));
		JLabel lblGameName = JLableFactory.createLblWithFont("   " + g.getGame_name());

		// 게임 장르 + 유형
		JLabel lblGameGenre = JLableFactory
				.createLblWithFont("   장르: " + g.getGame_genre() + " | 게임유형: " + g.getGame_category());

		// 게임 제작사
		JLabel lblGameProdu = JLableFactory.createLblWithFont("   제작사: " + g.getGame_production());

		pnlGameMid.add(lblGameName);
		pnlGameMid.add(lblGameGenre);
		pnlGameMid.add(lblGameProdu);

		// 게임 사진
		JLabel lblGameProfile = new JLabel();
		lblGameProfile.setPreferredSize(new Dimension(150, 85));
		lblGameProfile.setIcon(IconManager.getInstance().getIconByKey(g.getGame_profile()));

		// 게임 가격
		JPanel pnlEast = new JPanel();
		JLabel lblPrice = JLableFactory.createLblWithFont("정상가: " + g.getGame_price() + "원");

		pnlEast.add(lblPrice);

		pnlGame1.add(lblGameProfile, BorderLayout.WEST);
		pnlGame1.add(pnlGameMid, BorderLayout.CENTER);
		pnlGame1.add(pnlEast, BorderLayout.EAST);
		pnlGame1.setPreferredSize(new Dimension(700, 85));
		pnlGame1.setBorder(BorderFactory.createLineBorder(Color.RED));

		return pnlGame1;
	}

}

// 추천게임패널
class PnlRecommendedGame extends JPanel {
	private GameDAO gameDAO;
	private JPanelFactory panelFactory;

	public PnlRecommendedGame() {
		panelFactory = new JPanelFactory();
		gameDAO = new GameDAO();
		List<Game> list = gameDAO.getRandomList();
		setPreferredSize(new Dimension(500, 100 + 85 * list.size()));
		for (Game game : list) {
			JPanel pnlGame = panelFactory.createGamePnl(game);
			add(pnlGame);
		}
	}
}

class PnlDiscountGame extends JPanel {
	private GameDAO gameDAO;
	private JPanelFactory panelFactory;

	public PnlDiscountGame() {
		panelFactory = new JPanelFactory();
		gameDAO = new GameDAO();
		List<Game> list = gameDAO.getDiscountList();
		setPreferredSize(new Dimension(500, 100 + 85 * list.size()));
		for (Game game : list) {
			JPanel pnlGame = panelFactory.createGamePnl(game);
			add(pnlGame);
		}
	}
}

// 패널 만든거 확인용 메인클래스
public class GameMain extends JFrame {
	private PnlBasic pnlBasic;

	public GameMain() {
		pnlBasic = new PnlBasic();
		add(pnlBasic);
		DataManager.inputData("pnlBasic", pnlBasic);
		setSize(920, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	}

//	public static void main(String[] args) {
//		new PnlTest().setVisible(true);
//
////		PictureDAO pictureDAO = new PictureDAO();
////		Path path = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프", "bandai.png");
////		
////		try {
////			byte[] readAllBytes = Files.readAllBytes(path);
////			
////			int row = pictureDAO.insert(path.getFileName().toString(), readAllBytes);
////			System.out.println("삽입된 행 개수: " + row);
////			
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
}
