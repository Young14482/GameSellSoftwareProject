package materials;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import picture.IconManager;

public class JLableFactory {

	public JLabel createLblwithIcon(int key) {
		JLabel lblProduction1 = new JLabel();
		lblProduction1.setPreferredSize(new Dimension(150, 85));
		lblProduction1.setIcon(IconManager.getInstance().getIconByKey(key));
		return lblProduction1;
	}

	public JLabel createLblWithFont(String str) {
		JLabel lblGameName = new JLabel();
		lblGameName.setFont(new Font("휴먼모음T", Font.PLAIN, 16));
		lblGameName.setText(str);
		return lblGameName;
	}
}
