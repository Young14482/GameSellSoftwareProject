package materials;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class PnlGameList extends JPanel implements ActionListener {
	GameDAO gameDAO;
	private JLabel lblAmount;
	private JPanel pnlGame;
	private JComboBox<String> cbOrder;
	private JComboBox<String> cbGenre;
	private JComboBox<String> cbProduction;
	private JComboBox<String> cbCategory;
	private int order;
	private String genre;
	private String production;
	private String category;
	private JScrollPane js;
	private List<JButton> btnList;
	private JPanel pnlSouth;
	private int page;
	private int amount;
	private int endPage;
	private JPanel pnlOption;

	public PnlGameList() {
		gameDAO = new GameDAO();
		setLayout(new BorderLayout());

		initOptions();

		pnlOption = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		pnlOption.setPreferredSize(new Dimension(160, 0));
		pnlOption.setBorder(new EmptyBorder(40, 0, 0, 0));
		add(pnlOption, "West");

		recreateOptionComponents();

		JPanel pnlMain = new JPanel(new BorderLayout());
		lblAmount = new JLabel();

		JPanel pnlAmount = new JPanel();
		lblAmount.setOpaque(true);
		lblAmount.setBackground(new Color(238, 238, 238));
		lblAmount.setBorder(new LineBorder(Color.BLACK));
		lblAmount.setPreferredSize(new Dimension(700, 25));
		pnlAmount.add(lblAmount);

		pnlMain.add(pnlAmount, "North");

		pnlGame = new JPanel();
		pnlMain.add(pnlGame);

		pnlSouth = new JPanel();
		pnlMain.add(pnlSouth, "South");

		js = new JScrollPane(pnlMain);
		js.getVerticalScrollBar().setUnitIncrement(10);
		add(js);
		update();
	}

	public void initOptions() {
		order = GameDAO.ORDER_BY_RELEASE_DESC;
		genre = null;
		production = null;
		category = null;
		btnList = new ArrayList<>();
		page = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("검색하기")) {
			order = cbOrder.getSelectedIndex() == 0 ? 0 : cbOrder.getSelectedIndex() - 1;
			genre = cbGenre.getSelectedIndex() == 0 ? null : (String) cbGenre.getSelectedItem();
			production = cbProduction.getSelectedIndex() == 0 ? null : (String) cbProduction.getSelectedItem();
			category = cbCategory.getSelectedIndex() == 0 ? null : (String) cbCategory.getSelectedItem();
			page = 0;
			update();
		} else if (command.equals("<<")) {
			page = 0;
			update();
		} else if (command.equals("<")) {
			page--;
			update();
		} else if (command.equals(">")) {
			page++;
			update();
		} else if (command.equals(">>")) {
			page = endPage;
			update();
		} else {
			page = Integer.parseInt(e.getActionCommand()) - 1;
			update();
		}
	}

	public void update() {
		clearComponents();

		setAmount();

		createGameComponents();

		createButtons();

		repaint();
		revalidate();
		js.getVerticalScrollBar().setValue(0);
	}

	public void recreateOptionComponents() {
		pnlOption.removeAll();

		String[] orderArr = { ":::정렬순 선택:::", "출시일순", "높은가격순", "낮은가격순" };
		cbOrder = new JComboBox<>(orderArr);
		cbOrder.setPreferredSize(new Dimension(125, 25));
		List<String> genreList = gameDAO.getGenreList();
		genreList.add(0, "::장르 선택::");
		cbGenre = new JComboBox<>(genreList.toArray(new String[0]));
		cbGenre.setPreferredSize(new Dimension(125, 25));
		List<String> productionList = gameDAO.getProductionList();
		productionList.add(0, "::제작사 선택::");
		cbProduction = new JComboBox<>(productionList.toArray(new String[0]));
		cbProduction.setPreferredSize(new Dimension(125, 25));
		List<String> categoryList = gameDAO.getCategoryList();
		categoryList.add(0, "::게임유형 선택::");
		cbCategory = new JComboBox<>(categoryList.toArray(new String[0]));
		cbCategory.setPreferredSize(new Dimension(125, 25));
		JButton searchConfirm = new JButton("검색하기");
		searchConfirm.setPreferredSize(new Dimension(125, 25));
		searchConfirm.addActionListener(this);

		pnlOption.add(cbOrder);
		pnlOption.add(cbGenre);
		pnlOption.add(cbProduction);
		pnlOption.add(cbCategory);
		pnlOption.add(searchConfirm);
	}

	private void createButtons() {
		JButton left2 = new JButton("<<");
		JButton left = new JButton("<");
		if (page == 0) {
			left2.setEnabled(false);
			left.setEnabled(false);
		}
		left2.addActionListener(this);
		left.addActionListener(this);
		pnlSouth.add(left2);
		pnlSouth.add(left);
		int start = 0;
		if (amount > 50 && page > 2) {
			if (endPage - 2 < page) {
				start = endPage - 4;
			} else {
				start = page - 2;
			}
		}
		for (int i = 0; i <= endPage && i < 5; i++) {
			JButton btn = new JButton((i + start + 1) + "");
			if (i == page) {
				btn.setEnabled(false);
			}

			btn.addActionListener(this);
			btnList.add(btn);
			pnlSouth.add(btn);
		}
		JButton right = new JButton(">");
		JButton right2 = new JButton(">>");
		right.addActionListener(this);
		right2.addActionListener(this);
		pnlSouth.add(right);
		pnlSouth.add(right2);
		if (page == endPage) {
			right.setEnabled(false);
			right2.setEnabled(false);
		}
	}

	private void createGameComponents() {
		List<Game> list = gameDAO.getSearchedList(null, null, genre, production, category, order, page);
		JPanelFactory panelFactory = new JPanelFactory();
		for (Game game : list) {
			JPanel pnl = panelFactory.createGamePnl(game);
			pnlGame.add(pnl);
		}
		pnlGame.setPreferredSize(new Dimension(500, 50 + 85 * list.size()));
	}

	private void clearComponents() {
		pnlGame.removeAll();
		pnlSouth.removeAll();
		btnList.clear();
	}

	private void setAmount() {
		amount = gameDAO.getSearchedCount(null, null, genre, production, category);
		lblAmount.setText("  - 총 " + amount + " 개");
		endPage = (amount - 1) / 10;
	}

}
