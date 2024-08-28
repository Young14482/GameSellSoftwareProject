package materials;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import game.Game;
import game.GameDAO;

public class pnlGameList extends JPanel implements ActionListener {
	GameDAO gameDAO;

	public pnlGameList() {
		gameDAO = new GameDAO();
		setLayout(new BorderLayout());
		JPanel pnlOption = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		pnlOption.setPreferredSize(new Dimension(160, 0));
		pnlOption.setBorder(new EmptyBorder(40, 0, 0, 0));
		add(pnlOption, "West");

		String[] orderArr = { ":::정렬순 선택:::", "출시일순", "높은가격순", "낮은가격순" };
		JComboBox<String> cbOrder = new JComboBox<>(orderArr);
		cbOrder.setPreferredSize(new Dimension(125, 25));
		List<String> genreList = gameDAO.getGenreList();
		genreList.add(0, "::장르 선택::");
		JComboBox<String> cbGenre = new JComboBox<>(genreList.toArray(new String[0]));
		cbGenre.setPreferredSize(new Dimension(125, 25));
		List<String> productionList = gameDAO.getProductionList();
		productionList.add(0, "::제작사 선택::");
		JComboBox<String> cbProduction = new JComboBox<>(productionList.toArray(new String[0]));
		cbProduction.setPreferredSize(new Dimension(125, 25));
		JButton searchConfirm = new JButton("검색하기");
		searchConfirm.setPreferredSize(new Dimension(125, 25));
		searchConfirm.addActionListener(this);

		pnlOption.add(cbOrder);
		pnlOption.add(cbGenre);
		pnlOption.add(cbProduction);
		pnlOption.add(searchConfirm);
		JPanel pnlGame = new JPanel();
		JScrollPane js = new JScrollPane(pnlGame);
		JLabel lblAmount = new JLabel();
		int amount = gameDAO.getSearchedCount(null, null, null, null, null);
		lblAmount.setText("  - 총 " + amount + " 개");

		lblAmount.setOpaque(true);
		lblAmount.setBackground(new Color(238, 238, 238));
		lblAmount.setBorder(new LineBorder(Color.BLACK));
		lblAmount.setPreferredSize(new Dimension(700, 25));
		pnlGame.add(lblAmount);
		js.getVerticalScrollBar().setUnitIncrement(10);
		add(js);
		List<Game> list = gameDAO.getSearchedListDefault();
		pnlGame.setPreferredSize(new Dimension(500, 150 + 85 * list.size()));
		pnlGame.setBackground(Color.GRAY);
		JPanelFactory panelFactory = new JPanelFactory();
		for (Game game : list) {
			JPanel pnl = panelFactory.createGamePnl(game);
			pnlGame.add(pnl);
		}
		for (int i = 0; i < amount / 10 && i < 5; i++) {
			JButton btn = new JButton(i + 1 + "");
			pnlGame.add(btn);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("검색하기")) {

		}
	}

}
