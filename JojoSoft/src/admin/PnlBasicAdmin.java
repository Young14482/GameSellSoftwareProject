package admin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.GameDetailPnl;
import main.Login;
import main.ShoopingCart;
import materials.DataManager;
import materials.pnlGameList;

//메인 패널
public class PnlBasicAdmin extends JPanel implements ActionListener {
	private JPanel pnlToolBarAdmin;
	private JScrollPane js;
	private CardLayout cardLayout;
	private JPanel pnlContainer;
	private GameDetailPnl gameDetailPnl;
	private pnlGameList pnlGameList;
	private PnlAddGame pnlAddGame;
	
	public PnlBasicAdmin() {
		DataManager.inputData("pnlBasicAdmin", this);
		pnlToolBarAdmin = new PnlToolBarAdmin(this);

		// CardLayout과 패널 컨테이너 설정
		cardLayout = new CardLayout();
		pnlContainer = new JPanel(cardLayout);
		// 메인 정보 패널 및 스크롤 패널 설정
		js.getVerticalScrollBar().setUnitIncrement(10);
		gameDetailPnl = new GameDetailPnl();
		pnlGameList = new pnlGameList();
		pnlAddGame = new PnlAddGame();

		// CardLayout에 패널 추가
		pnlContainer.add(pnlGameList, "MainPanel");
		pnlContainer.add(pnlAddGame, "AddGame");

		setLayout(new BorderLayout());

		add(pnlToolBarAdmin, BorderLayout.NORTH);

		add(pnlContainer, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		 if (e.getActionCommand().equals("JOJOSOFT")) {
			cardLayout.show(pnlContainer, "MainPanel");
		} else if (e.getActionCommand().equals("상품 추가")) {
			
		} else if (e.getActionCommand().equals("상품 수정")) {
			
		} else if (e.getActionCommand().equals("로그아웃")) {
			// 기본적으로 내장된 윈도우 메소드를 통해 모든 창을 가져옴.
			// 반복문을 통하여 가져오는 모든 창들을 dispose(종료)시킴
			// 새로운 로그인 창을 만들어서 visible을 true로 변경함
			// import는 import java.awt.Window; 를 사용
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new Login().setVisible(true);
		} 
	}

	public void changeScreenToGameDetail() {
		cardLayout.show(pnlContainer, "GameDetail");
		gameDetailPnl.update();
	}
	private void showCartDialog(String str) {
		String[] options = { "계속 쇼핑", "장바구니" };
		int choice = JOptionPane.showOptionDialog(this, str, "알림",  JOptionPane.DEFAULT_OPTION
				, JOptionPane.QUESTION_MESSAGE, null, options, null);
		
		if (choice == 1) {
			cardLayout.show(pnlContainer, "ShoopingCart");
			ShoopingCart getShoopingCart = (ShoopingCart) DataManager.getData("ShoopingCart");
			getShoopingCart.removeAll();
			getShoopingCart.reconstruction();
			getShoopingCart.revalidate();
			getShoopingCart.repaint();
		} else {
			cardLayout.show(pnlContainer, "MainPanel");
		}
	}
}
