import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import sun.net.www.content.image.jpeg;

class PnlBasic extends JPanel {
	private JPanel pnlToolBar;

	public PnlBasic() {
		pnlToolBar = new PnlToolBar();
		pnlToolBar.setBorder(new LineBorder(Color.RED));
		add(pnlToolBar, "NORTH");
		
	}
}

class PnlToolBar extends JPanel {
	public PnlToolBar() {
		setLayout(new BorderLayout());
		JPanel pnlEast = new JPanel(new GridLayout(3, 0));
		JLabel lnlNickname = new JLabel("스폰지밥님 환영합니다");
		JButton btnLogout = new JButton("로그아웃");
		JButton btnCart = new JButton("장바구니");

		pnlEast.add(lnlNickname);
		pnlEast.add(btnLogout);
		pnlEast.add(btnCart);
		pnlEast.setBorder(new LineBorder(Color.RED));

		JPanel pnlCenter = new JPanel(new GridLayout(2, 0));
		JPanel pnlSerch = new JPanel();
		pnlSerch.setBorder(new LineBorder(Color.CYAN));
		JButton btnLogo = new JButton("JOJOSOFT");
		JTextField tfSeach = new JTextField(20);
		JButton btnSeach = new JButton("검색");
		pnlCenter.setBorder(new LineBorder(Color.BLACK));

		pnlSerch.add(btnLogo);
		pnlSerch.add(tfSeach);
		pnlSerch.add(btnSeach);
		
		JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlBtns.setBorder(new LineBorder(Color.green));
		JButton btnGame = new JButton("게임");
		JButton btnPromotion = new JButton("프로모션");
		
		pnlBtns.add(btnGame);
		pnlBtns.add(btnPromotion);
		
		pnlCenter.add(pnlSerch);
		pnlCenter.add(pnlBtns);

		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(700,100));
	}
}

class PnlTest extends JFrame {
	private PnlBasic pnlBasic;

	public PnlTest() {
		pnlBasic = new PnlBasic();
		add(pnlBasic);
		setSize(720, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

public class GameMain {
	public static void main(String[] args) {
		new PnlTest().setVisible(true);
	}
}
