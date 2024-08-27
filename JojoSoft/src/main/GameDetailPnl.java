package main;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import materials.JLableFactory;

public class GameDetailPnl extends JPanel {
	private JLabel lblTitle;

	public GameDetailPnl() {
		setLayout(new BorderLayout());
		JPanel pnlNorth = new JPanel();
		lblTitle = JLableFactory.createLblWithFont("", 25);
		pnlNorth.add(lblTitle);
		add(pnlNorth);
	}

	public void update() {
		Game g = Game.getCurGame();
		if (g != null) {
			lblTitle.setText(g.getGame_name());
		}
	}
}
