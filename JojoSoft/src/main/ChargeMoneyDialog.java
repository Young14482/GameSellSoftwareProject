package main;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChargeMoneyDialog extends JDialog {
	private JLabel lbl;
	private JTextField tf;

	public ChargeMoneyDialog(PnlBasic pnlBasic) {

		JPanel pnl = new JPanel();
		pnl.setPreferredSize(new Dimension(290, 250));
		pnl.setLayout(null);
		lbl = new JLabel("충전하실 금액을 입력해주세요");
		lbl.setBounds(50, 50, 240, 30);
		tf = new JTextField(10);
		tf.setBounds(70, 100, 150, 30);
		JButton btn = new JButton("확인");
		btn.setBounds(95, 150, 100, 30);
		btn.setActionCommand("금액 충전");
		btn.addActionListener(pnlBasic);

		pnl.add(lbl);
		pnl.add(tf);
		pnl.add(btn);

		add(pnl);
		pack();
		setLocationRelativeTo(pnlBasic);
	}

	public JLabel getLbl() {
		return lbl;
	}

	public void setLbl(JLabel lbl) {
		this.lbl = lbl;
	}

	public JTextField getTf() {
		return tf;
	}

	public void setTf(JTextField tf) {
		this.tf = tf;
	}

}
