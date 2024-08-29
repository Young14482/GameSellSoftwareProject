package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import game.BundleGameDAO;
import game.Game;
import game.GameDAO;
import materials.DataManager;
import materials.JLableFactory;
import materials.JPanelFactory;
import picture.GamePictureDAO;
import picture.IconManager;

public class GameDetailPnl extends JPanel {
	private JLabel lblTitle;
	private JLabel lblGameProfile;
	private JLabel lblAgeLimit;
	private JLabel lblProduction;
	private JLabel lblGenre;
	private JLabel lblReleaseDate;
	private JLabel lblSellMoney;
	private JLabel lblOriginMoney;
	private JLabel lblInGame;
	private JPanel pnlCenter;
	private JTextArea jtaGameInfo;
	private JScrollPane js;
	private JPanel pnlCenterSouth;

	public GameDetailPnl() {
		setLayout(new BorderLayout());
		// 게임 제목 패널
		JPanel pnlNorth = new JPanel();
		lblTitle = JLableFactory.createLblWithFont("", 25);
		pnlNorth.add(lblTitle);

		// 게임 간단 정보 패널
		JPanel pnlEast = new JPanel();
		pnlEast.setLayout(new BorderLayout());
		lblGameProfile = new JLabel();
		lblGameProfile.setPreferredSize(new Dimension(155, 85));

		JPanel pnlGameInfo = new JPanel();
		pnlGameInfo.setLayout(new GridLayout(6, 0));

		lblOriginMoney = JLableFactory.createLblWithFont("");
		lblSellMoney = JLableFactory.createLblWithFont("");
		lblReleaseDate = JLableFactory.createLblWithFont("");
		lblGenre = JLableFactory.createLblWithFont("");
		lblProduction = JLableFactory.createLblWithFont("");
		lblAgeLimit = JLableFactory.createLblWithFont("");

		pnlGameInfo.add(lblOriginMoney);
		pnlGameInfo.add(lblSellMoney);
		pnlGameInfo.add(lblReleaseDate);
		pnlGameInfo.add(lblGenre);
		pnlGameInfo.add(lblProduction);
		pnlGameInfo.add(lblAgeLimit);

		JButton btnInsertCart = new JButton("장바구니에 담기");
		btnInsertCart.addActionListener((PnlBasic) DataManager.getData("pnlBasic"));

		pnlEast.add(lblGameProfile, BorderLayout.NORTH);
		pnlEast.add(pnlGameInfo, BorderLayout.CENTER);
		pnlEast.add(btnInsertCart, BorderLayout.SOUTH);

		// 게임 메인정보
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
//		pnlCenter.setPreferredSize(new Dimension(600, 800));

		lblInGame = new JLabel();
//		lblInGame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		lblInGame.setHorizontalAlignment(JLabel.CENTER);
		lblInGame.setPreferredSize(new Dimension(0, 380));

		jtaGameInfo = new JTextArea("", 0, 0);
		jtaGameInfo.setEditable(false);
		jtaGameInfo.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 40));
		jtaGameInfo.setBackground(null);
		jtaGameInfo.setLineWrap(true);

		// 텍스트필드 스크롤바 초기위치를 상단으로 초기화 함
		DefaultCaret caret = (DefaultCaret) jtaGameInfo.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		// 번들일 경우 게임들 출력되는 패널
		pnlCenterSouth = new JPanel();

		js = new JScrollPane(pnlCenter);
		js.getVerticalScrollBar().setUnitIncrement(10);
		js.setBorder(BorderFactory.createEmptyBorder());
		js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnlCenter.add(lblInGame, BorderLayout.NORTH);
		pnlCenter.add(jtaGameInfo, BorderLayout.CENTER);
		pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlEast, BorderLayout.EAST);
		add(js, BorderLayout.CENTER);

	}

	public void update() {
		Game g = Game.getCurGame();
		GamePictureDAO gamePictureDAO = new GamePictureDAO();
		BundleGameDAO bundleGameDAO = new BundleGameDAO();
		GameDAO gameDAO = new GameDAO();
		JPanelFactory jPanelFactory = new JPanelFactory();
		if (g != null) {
			lblTitle.setText(g.getGame_name());

			lblGameProfile.setIcon(IconManager.getInstance().getIconByKey(g.getGame_profile()));
			lblOriginMoney.setText("정상가: " + g.getGame_price() + "원");
			int sellPrice = g.getGame_price() * (100 - g.getGame_discount()) / 100;
			int result = Math.round(sellPrice / 100) * 100;
			lblSellMoney.setText("판매가: " + result + "원");
			lblReleaseDate.setText("출시일: " + g.getGame_release());
			lblGenre.setText("장르: " + g.getGame_genre());
			lblProduction.setText("제작사: " + g.getGame_production());
			lblAgeLimit.setText("연령제한: " + g.getAge_limit() + "세");

			int key = gamePictureDAO.getPicture(g.getGame_Id());
			lblInGame.setIcon(IconManager.getInstance().getIconByKey(key));
			String str = g.getGame_info();
			jtaGameInfo.setText(str);
			pnlCenterSouth.removeAll();
			pnlCenterSouth.setPreferredSize(new Dimension(0, 0));
			if (g.getGame_category().equals("번들")) {
				List<Integer> gameBundle = bundleGameDAO.getGameInBundleList(g.getGame_Id());
				for (int game_id : gameBundle) {
					Game game = gameDAO.getGame(game_id);
					JPanel gamePnl = jPanelFactory.createGamePnl(game);
					pnlCenterSouth.setPreferredSize(new Dimension(0, 90 * gameBundle.size()));
					pnlCenterSouth.add(gamePnl);
				}
			}

		}
	}
}
