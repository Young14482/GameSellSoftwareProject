package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import game.GameDAO;
import materials.JLableFactory;

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
		JPanel pnl2 = new JPanel();
		pnl2.setPreferredSize(new Dimension(700, 377));
		pnl2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbl2.setPreferredSize(new Dimension(700, 377));

		pnl2.add(lbl2);
		pnl.add(pnl2, "South");
		pnl.add(jta);
		JScrollPane js = new JScrollPane(pnl);
		js.setHorizontalScrollBarPolicy(js.HORIZONTAL_SCROLLBAR_NEVER);
		pnl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(js.getVerticalScrollBar().getValue());
				js.getVerticalScrollBar().setValue(396);
				super.mouseClicked(e);
			}

		});
		js.getVerticalScrollBar().setUnitIncrement(10);
		js.getVerticalScrollBar().setValueIsAdjusting(false);
		add(js);
		jta.setText(gdao.getGame(1).getGame_info());

//		setResizable(false);
		setSize(900, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

public class Main {
	public static void main(String[] args) {
		new Test().setVisible(true);
	}
}
