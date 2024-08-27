package temp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import game.Game;

//메인 패널
public class PnlBasic extends JPanel implements ActionListener {
	private JPanel pnlToolBar;
	private JPanel pnlMainInfo;
	private JScrollPane js;
	private JPanel memberInfoPnl;
	private CardLayout cardLayout;
	private JPanel pnlContainer;
	private GameDetailPnl gameDetailPnl;

	public PnlBasic() {
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
		}
	}

	public void changeScreenToGameDetail() {
		System.out.println(Game.getCurGame());
		cardLayout.show(pnlContainer, "GameDetail");
		gameDetailPnl.update();
	}
}
