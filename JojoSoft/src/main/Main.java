package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import game.Game;
import game.GameDAO;
import materials.JLableFactory;
import picture.IconManager;
import picture.PictureDAO;

class Test extends JFrame {
	public Test() {
		GameDAO gdao = new GameDAO();
		JPanel pnl = new JPanel(new BorderLayout());

		pnl.setPreferredSize(new Dimension(700, 900));
//		pnl.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 200));
		JLabel lbl = JLableFactory.createLblwithIcon(52);
		JLabel lbl2 = JLableFactory.createLblwithIcon(52);
		lbl.setPreferredSize(new Dimension(700, 377));

		JTextArea jta = new JTextArea("");
		jta.setEditable(false);
		jta.setBorder(BorderFactory.createEmptyBorder());
		jta.setBackground(null);
		jta.setLineWrap(true);
		pnl.add(lbl, "North");
		JPanel pnl2 = new JPanel(new BorderLayout());
		pnl2.setPreferredSize(new Dimension(700, 377));
		pnl2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		lbl2.setPreferredSize(new Dimension(700, 377));

		pnl2.add(lbl2);
		pnl.add(pnl2, "South");
		pnl.add(jta);

		JScrollPane js = new JScrollPane(pnl);
		js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		js.getVerticalScrollBar().setUnitIncrement(10);
		add(js);
		jta.setText(gdao.getGame(1).getGame_info());

//		setResizable(false);
		setSize(900, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

class Test2 extends JFrame {
	public Test2() {
		GameDetailPnl pnl = new GameDetailPnl();
		pnl.setBackground(Color.RED);
		Game.setCurGame(new GameDAO().getGame(9));
		pnl.update();
		add(pnl);
		JLabel lbl = new JLabel(IconManager.getInstance().getIconByKey(71));
//		add(lbl);
		setSize(920, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

public class Main {
	public static void main(String[] args) {
		new Test2().setVisible(true);
	}
}
