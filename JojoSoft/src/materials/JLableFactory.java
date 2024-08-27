package materials;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import picture.IconManager;

public class JLableFactory {

	static public JLabel createLblwithIcon(int key) {
		JLabel lblProduction1 = new JLabel();
		lblProduction1.setPreferredSize(new Dimension(150, 85));
		lblProduction1.setIcon(IconManager.getInstance().getIconByKey(key));
		return lblProduction1;
	}

	static public JLabel createLblWithFont(String str, String fontStyle, int fontSize) {
		JLabel lblGameName = new JLabel();
		lblGameName.setFont(new Font(fontStyle, Font.PLAIN, fontSize));
		lblGameName.setText(str);
		return lblGameName;
	}

	static public JLabel createLblWithFont(String str) {
		return createLblWithFont(str, "휴먼모음T", 16);
	}

	static public JLabel createLblWithFont(String str, int fontSize) {
		return createLblWithFont(str, "휴먼모음T", fontSize);
	}

}
