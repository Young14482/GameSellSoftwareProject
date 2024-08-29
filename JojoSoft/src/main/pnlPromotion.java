package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import game.Game;
import game.GameDAO;
import materials.JPanelFactory;

public class pnlPromotion extends JPanel implements ActionListener {
	private JPanel pnlCenter;
	private JPanel pnlNorth;
	private String category;
	private int order;
	private JComboBox<String> cbOrder;

	public pnlPromotion() {
		category = "전체";
		order = GameDAO.ORDER_BY_RELEASE_DESC;

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setBorder(new LineBorder(Color.BLACK));
		pnlMain.add(pnlNorth, "North");
		createPanelNorth();
		pnlCenter = new JPanel();
		pnlMain.add(pnlCenter);
		createPanelCenter();

		JScrollPane js = new JScrollPane(pnlMain);
		js.setPreferredSize(new Dimension(820, 560));
		js.getVerticalScrollBar().setUnitIncrement(10);
		add(js);
	}

	private void createPanelCenter() {
		pnlCenter.removeAll();
		GameDAO gameDAO = new GameDAO();
		List<Game> list = gameDAO.getDisCountListOption(category, order);
		JPanelFactory panelFactory = new JPanelFactory();
		for (Game game : list) {
			JPanel pnl = panelFactory.createGamePnl(game);
			pnlCenter.add(pnl);
		}
		pnlCenter.setPreferredSize(new Dimension(500, 50 + 87 * list.size()));
		revalidate();
		repaint();
	}

	private void createPanelNorth() {
		JPanel pnlCenter = new JPanel();
		pnlNorth.add(pnlCenter);
		JPanel pnlEast = new JPanel();
		pnlNorth.add(pnlEast, "East");

		ButtonGroup group = new ButtonGroup();

		JRadioButton rbAll = new JRadioButton("전체");
		rbAll.addActionListener(this);
		rbAll.setSelected(true);
		group.add(rbAll);
		pnlCenter.add(rbAll);

		JRadioButton rbBasic = new JRadioButton("기본게임");
		rbBasic.addActionListener(this);
		group.add(rbBasic);
		pnlCenter.add(rbBasic);

		JRadioButton rbDLC = new JRadioButton("DLC");
		rbDLC.addActionListener(this);
		group.add(rbDLC);
		pnlCenter.add(rbDLC);

		JRadioButton rbBundle = new JRadioButton("번들");
		rbBundle.addActionListener(this);
		group.add(rbBundle);
		pnlCenter.add(rbBundle);

		String[] orderArr = { ":::정렬순 선택:::", "출시일순", "높은가격순", "낮은가격순" };
		cbOrder = new JComboBox<>(orderArr);
		cbOrder.addActionListener(this);
		cbOrder.setPreferredSize(new Dimension(125, 25));
		pnlEast.add(cbOrder);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("comboBoxChanged")) {
			order = cbOrder.getSelectedIndex() == 0 ? 0 : cbOrder.getSelectedIndex() - 1;
		} else {
			category = command;
		}
		createPanelCenter();

	}

}
