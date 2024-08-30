package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import game.Game;
import game.GameDAO;
import materials.JPanelFactory;

public class SearchPnlAdmin extends JPanel {
	private JTextField tfField;
	private String saveStr;

	public SearchPnlAdmin(String searchStr, PnlBasicAdmin pnlBasic, String AnotherSearchStr) {
		reconstruction(searchStr, pnlBasic, AnotherSearchStr);
	}

	public void reconstruction(String searchStr, PnlBasicAdmin pnlBasic, String AnotherSearchStr) {
		saveStr = searchStr;
		JPanel mainPnl = new JPanel();
		mainPnl.setPreferredSize(new Dimension(800, 1200));
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		JPanel searchPnl = new JPanel();
		searchPnl.setLayout(null);
		searchPnl.setPreferredSize(new Dimension(900, 220));

		JLabel guideLbl = new JLabel("결과 내에서 재 검색 : ");
		guideLbl.setFont(new Font("굴림", Font.BOLD, 14));
		guideLbl.setBounds(100, 100, 160, 30);

		tfField = new JTextField(30);
		tfField.setBounds(260, 100, 400, 30);

		JButton searchBtn = new JButton("검색");
		searchBtn.setActionCommand("검색 창 내부 검색");
		searchBtn.setBounds(680, 96, 110, 38);
		searchBtn.addActionListener(pnlBasic);

		JLabel borderLbl = new JLabel();
		borderLbl.setBounds(80, 10, 750, 200);
		borderLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		searchPnl.add(guideLbl);
		searchPnl.add(tfField);
		searchPnl.add(searchBtn);
		searchPnl.add(borderLbl);

		JPanel printPnl = new JPanel();
		printPnl.setLayout(new FlowLayout());
		printPnl.setPreferredSize(new Dimension(750, 1000));

		if (!searchStr.equals("")) {
			GameDAO gameDao = new GameDAO();

			List<Game> gameList = gameDao.getSearchedList(searchStr, AnotherSearchStr, null, null, null,
					GameDAO.ORDER_BY_RELEASE_DESC, 0);
			// List<Game> gameList = gameDao.returnGameToSearchDetail(searchStr);
			JPanelFactory factory = new JPanelFactory();
			for (int i = 0; i < gameList.size(); i++) {
				JPanel pnl = factory.createGamePnl(gameList.get(i));
				printPnl.add(pnl);
			}
		}

		mainPnl.add(searchPnl);
		mainPnl.add(printPnl);

		JScrollPane scrollPane = new JScrollPane(mainPnl);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		setPreferredSize(new Dimension(1000, 1000));
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

	}

	public JTextField getTfField() {
		return tfField;
	}

	public String getSaveStr() {
		return saveStr;
	}

}
