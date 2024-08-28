package admin;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import picture.PictureDAO;

public class InsertImageToDB extends JFrame {
    private JTextField resultField;

    public InsertImageToDB(PictureDAO pictureDAO) {
        setTitle("이미지 등록하기");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel lbl = new JLabel("DB에 등록할 이미지의 이름을 작성해주세요");
        add(lbl);
        // 결과를 표시할 JTextField
        resultField = new JTextField(20);
        add(resultField);

        // 버튼을 눌러 다이얼로그 띄우기
        JButton openDialogButton = new JButton("저장하기");
        openDialogButton.addActionListener(pictureDAO);
        add(openDialogButton);
    }

	public JTextField getResultField() {
		return resultField;
	}

}


