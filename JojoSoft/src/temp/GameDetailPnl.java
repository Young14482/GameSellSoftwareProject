package temp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import game.Game;
import materials.JLableFactory;
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
	private JLabel lblGameInfo;
	private JPanel pnlCenter;

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

		JButton btnInsertCart = new JButton("장바구니");

		pnlEast.add(lblGameProfile, BorderLayout.NORTH);
		pnlEast.add(pnlGameInfo, BorderLayout.CENTER);
		pnlEast.add(btnInsertCart, BorderLayout.SOUTH);

		// 게임 메인정보
		pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.setPreferredSize(new Dimension(600, 800));

		lblInGame = new JLabel();
		lblInGame.setPreferredSize(new Dimension(0, 380));

		lblGameInfo = JLableFactory.createLblWithFont("");

		JScrollPane js = new JScrollPane(pnlCenter);
		js.getVerticalScrollBar().setUnitIncrement(10);
		js.setBorder(BorderFactory.createEmptyBorder());

		// 여백용 lbl
		JLabel lblBlank1 = new JLabel();
		lblBlank1.setPreferredSize(new Dimension(40, 0));
		
		pnlCenter.add(lblInGame, BorderLayout.NORTH);
		pnlCenter.add(lblGameInfo, BorderLayout.CENTER);
		pnlCenter.add(lblBlank1, BorderLayout.EAST);

		// 여백용 lbl
		JLabel lblBlank2 = new JLabel();
		lblBlank2.setPreferredSize(new Dimension(70, 0));

		add(pnlNorth, BorderLayout.NORTH);
		add(lblBlank2, BorderLayout.WEST);
		add(pnlEast, BorderLayout.EAST);
		add(js, BorderLayout.CENTER);

	}

	public void update() {
		Game g = Game.getCurGame();
		GamePictureDAO gamePictureDAO = new GamePictureDAO();
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
			lblGameInfo.setText("<html>" + str);
			pnlCenter.setPreferredSize(new Dimension(600, str.length() * 1));
		}
	}
}
