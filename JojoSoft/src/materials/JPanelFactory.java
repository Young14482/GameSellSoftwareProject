package materials;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import picture.IconManager;

public class JPanelFactory {
	JLableFactory lableFactory;

	public JPanel createGamePnl(Game g) {
		JPanel pnlGame = new JPanel();
		lableFactory = new JLableFactory();
		pnlGame.setLayout(new BorderLayout());		

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
		pnlEast.setLayout(new GridLayout(3, 0));
		pnlEast.setPreferredSize(new Dimension(150,85));
		JLabel lblOriginPrice = lableFactory.createLblWithFont("정상가: " + g.getGame_price() + "원");
		
		JLabel lblDiscount = lableFactory.createLblWithFont("할인률: " + g.getGame_discount() + "%");
		
		int sellPrice = g.getGame_price() * (100-g.getGame_discount()) / 100;
		int result = Math.round(sellPrice/100) * 100;
		JLabel lblSellPrice = lableFactory.createLblWithFont("판매가: " + result + "원");

		pnlEast.add(lblOriginPrice);
		pnlEast.add(lblDiscount);
		pnlEast.add(lblSellPrice);

		pnlGame.add(lblGameProfile, BorderLayout.WEST);
		pnlGame.add(pnlGameMid, BorderLayout.CENTER);
		pnlGame.add(pnlEast, BorderLayout.EAST);
		pnlGame.setPreferredSize(new Dimension(700, 85));
		pnlGame.setBorder(BorderFactory.createLineBorder(Color.RED));

		return pnlGame;
	}
}
