package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import materials.Function;
import materials.JLableFactory;
import user.User;
import user.UserDAO;

public class ShoopingCart extends JPanel {
    public ShoopingCart() {
        // 게임 이름, 게임 가격, 할인율, 이미지 데이터 id 순서로 리스트 들어감
        List<List<String>> list = UserDAO.userShoopingCart(User.getCurUser().getUserId());

        List<JCheckBox> checkBoxList = new ArrayList<>();
        JLableFactory lableFactory = new JLableFactory();
        
        setLayout(new BorderLayout());
        JPanel mainPnl = new JPanel();
        mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));  // BoxLayout으로 수직 배치 설정
        
        JScrollPane js = new JScrollPane(mainPnl);
        js.getVerticalScrollBar().setUnitIncrement(10);

        if (list.size() < 1) {
            JLabel lbl = new JLabel("현재 장바구니에 담겨있는 목록이 없습니다.");
            add(lbl, BorderLayout.CENTER);
        } else {
            for (int i = 0; i < list.size(); i++) {
                JPanel pnl = new JPanel();
                pnl.setLayout(null);
                pnl.setPreferredSize(new Dimension(800, 150));  // 패널의 크기를 명시적으로 설정
                pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));  // 최대 크기를 설정하여 늘어나지 않도록 제한
                
                Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
                
                JCheckBox checkBox = new JCheckBox();
                checkBox.setBounds(10, 75, 20, 20);
                
                JLabel image = lableFactory.createLblwithIcon(Integer.valueOf(list.get(i).get(3)));
                image.setBounds(40, 40, 150, 85);
                image.setBorder(border);
                
                JLabel nameLbl = new JLabel(list.get(i).get(0));
                nameLbl.setBounds(200, 60, 250, 50);
                nameLbl.setBorder(border);
                
                JLabel priceLbl = new JLabel("상품 가격 : " + list.get(i).get(1) + "원");
                priceLbl.setBounds(470, 60, 150, 50);
                priceLbl.setBorder(border);
                
                JLabel discountLbl = new JLabel("할인 된 가격 : " + Function.priceCalculate(Integer.valueOf(list.get(i).get(1)), Integer.valueOf(list.get(i).get(2))) + "원");
                discountLbl.setBounds(640, 60, 150, 50);
                discountLbl.setBorder(border);
                
                JLabel discountPercentLbl = new JLabel("할인율 : " + list.get(i).get(2) + "%");
                discountPercentLbl.setBounds(810, 60, 100, 50);
                discountPercentLbl.setBorder(border);
                
                pnl.add(checkBox);
                pnl.add(image);
                pnl.add(nameLbl);
                pnl.add(priceLbl);
                pnl.add(discountLbl);
                pnl.add(discountPercentLbl);
                mainPnl.add(pnl);
            }
            
            JPanel southPnl = new JPanel();
            JButton buyBtn = new JButton("체크된 목록 모두 구매하기");
            southPnl.add(buyBtn);
            add(js, BorderLayout.CENTER);
            add(southPnl, BorderLayout.SOUTH);
        }
    }
}
