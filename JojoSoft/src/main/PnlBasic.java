package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import game.Game;
import materials.DataManager;
import order.OrderDAO;
import order.OrderListDAO;

//메인 패널
public class PnlBasic extends JPanel implements ActionListener {
	private JPanel pnlToolBar;
	private JPanel pnlMainInfo;
	private JScrollPane js;
	private JPanel memberInfoPnl;
	private CardLayout cardLayout;
	private JPanel pnlContainer;
	private GameDetailPnl gameDetailPnl;
	private OrderDAO orderDAO;
	private OrderListDAO orderListDAO;

	public PnlBasic() {
		DataManager.inputData("pnlBasic", this);
		pnlToolBar = new PnlToolBar(this);
		memberInfoPnl = new MemberInfoPnl(getLnlNickname());
		gameDetailPnl = new GameDetailPnl();

		// CardLayout과 패널 컨테이너 설정
		cardLayout = new CardLayout();
		pnlContainer = new JPanel(cardLayout);

		// 메인 정보 패널 및 스크롤 패널 설정
		pnlMainInfo = new PnlMainInfo();
		js = new JScrollPane(pnlMainInfo);
		js.getVerticalScrollBar().setUnitIncrement(10);

		// CardLayout에 패널 추가
		pnlContainer.add(js, "MainPanel");
		pnlContainer.add(memberInfoPnl, "MemberInfoPanel");
		pnlContainer.add(gameDetailPnl, "GameDetail");

		setLayout(new BorderLayout());

		setLayout(new BorderLayout());

		add(pnlToolBar, BorderLayout.NORTH);

		add(pnlContainer, BorderLayout.CENTER);
	}

	public JLabel getLnlNickname() {
		return ((PnlToolBar) pnlToolBar).getLnlNickname();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("회원 정보")) {
			cardLayout.show(pnlContainer, "MemberInfoPanel");
		} else if (e.getActionCommand().equals("JOJOSOFT")) {
			cardLayout.show(pnlContainer, "MainPanel");
		} else if (e.getActionCommand().equals("로그아웃")) {
			// 기본적으로 내장된 윈도우 메소드를 통해 모든 창을 가져옴.
			// 반복문을 통하여 가져오는 모든 창들을 dispose(종료)시킴
			// 새로운 로그인 창을 만들어서 visible을 true로 변경함
			// import는 import java.awt.Window; 를 사용
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new Login().setVisible(true);
		} else if (e.getActionCommand().equals("장바구니에 담기")) {
			orderDAO = new OrderDAO();
			orderListDAO = new OrderListDAO();
			int order_id = orderDAO.insert();
			int result = orderListDAO.insert(order_id);
			if (result == 1) {
				System.out.println("등록 완료");
				cardLayout.show(pnlContainer, "MainPanel");
			} else {
				System.out.println("등록 실패");
			}
		}
	}

	public void changeScreenToGameDetail() {
		cardLayout.show(pnlContainer, "GameDetail");
		gameDetailPnl.update();
	}
}
