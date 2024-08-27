

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import materials.JLableFactory;

public class GameDetailPnl extends JPanel {
	public GameDetailPnl() {
		Game g = Game.getCurGame();
		if(g != null) {
			setLayout(new BorderLayout());
			
			JPanel pnlNorth = new JPanel();
			JLabel lblTitle = JLableFactory.createLblWithFont(g.getGame_name(), 25);
			pnlNorth.add(lblTitle);
			add(pnlNorth);
		} else {
			JLabel lbl = JLableFactory.createLblWithFont("공란처리용");
			add(lbl);
		}
	}
}
