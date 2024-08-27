import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import game.Game;
import game.GameDAO;
import materials.JLableFactory;
import materials.JPanelFactory;
import user.User;

// 메인 패널
class PnlBasic extends JPanel implements ActionListener {
	private JPanel pnlToolBar;
	private JPanel pnlMainInfo;
	private JScrollPane js;
	private JPanel memberInfoPnl;
	private CardLayout cardLayout;
	private JPanel pnlContainer;

	public PnlBasic() {
		memberInfoPnl = new MemberInfoPnl();
		pnlToolBar = new PnlToolBar(this);

		// CardLayout과 패널 컨테이너 설정
		cardLayout = new CardLayout();
		pnlContainer = new JPanel(cardLayout);

		// 메인 정보 패널 및 스크롤 패널 설정
		pnlMainInfo = new PnlMainInfo();
		js = new JScrollPane(pnlMainInfo);
		js.getVerticalScrollBar().setUnitIncrement(10);

		// CardLayout에 패널 추가
		pnlContainer.add(js, "MainPanel");
		pnlContainer.add(memberInfoPnl, "MemberInfoPanel");

		setLayout(new BorderLayout());
		
		add(pnlToolBar, BorderLayout.NORTH);

		add(pnlContainer, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("회원 정보")) {
			cardLayout.show(pnlContainer, "MemberInfoPanel");
		} else if (e.getActionCommand().equals("JOJOSOFT")) {
			cardLayout.show(pnlContainer, "MainPanel");
		}
	}
}

// 상단부 검색바 구성
class PnlToolBar extends JPanel {

	private JLabel lnlNickname;

	public PnlToolBar(PnlBasic pnlBasic) {
		setLayout(new BorderLayout());
		// pnlEast: 로그인,로그아웃, 사용자 닉네임 알려주는 패널
		JPanel pnlEast = new JPanel(new GridLayout(3, 0));
		lnlNickname = new JLabel(User.getCurUser().getUserNickName() + "님 환영합니다");
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
		JButton btnPromotion = new JButton("프로모션");
		JButton userInfoBtn = new JButton("회원 정보");
		userInfoBtn.addActionListener(pnlBasic);

		pnlBtns.add(btnGame);
		pnlBtns.add(btnPromotion);
		pnlBtns.add(userInfoBtn);

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
	private PnlRecommendedGame recommendedGame;
	
	public PnlGames() {
		latestGames = new PnlLatestGames();
		recommendedGame = new PnlRecommendedGame();
		add(latestGames, "최신게임");
		add(recommendedGame, "추천게임");
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
}

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

// 패널 만든거 확인용 메인클래스
public class GameMain {
	public void printMain() {
		new PnlTest().setVisible(true);
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
