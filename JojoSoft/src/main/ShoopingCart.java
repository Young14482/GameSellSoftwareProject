package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import materials.DataManager;
import materials.Function;
import materials.JLableFactory;
import user.User;
import user.UserDAO;

class BuyDialog extends JDialog implements ActionListener {
	JPanel shoopingCart;
	List<Integer> buyGameIdList;
	int totalPaymentMoney;
	public BuyDialog(int resultPrice, int userChargeMoney, ShoopingCart shoopingCart, List<Integer> buyGameIdList) {
		this.shoopingCart = shoopingCart;
		this.totalPaymentMoney = resultPrice;
		this.buyGameIdList = buyGameIdList;
		setLocationRelativeTo(shoopingCart);

		JPanel centerPnl = new JPanel();
		centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
		JPanel southPnl = new JPanel();

		JLabel guideLbl = new JLabel("     결제를 진행하시겠습니까?     ");
		JLabel resultPriceLbl = new JLabel("     총 상품 가격 : " + resultPrice + "     ");
		JLabel userChargeMoneyLbl = new JLabel("     회원님의 충전된 잔고 : " + userChargeMoney + "     ");

		JButton checkBtn = new JButton("확인");
		JButton backBtn = new JButton("결제 취소");
		checkBtn.addActionListener(this);
		backBtn.addActionListener(this);

		centerPnl.add(guideLbl);
		centerPnl.add(resultPriceLbl);
		centerPnl.add(userChargeMoneyLbl);

		southPnl.add(checkBtn);
		southPnl.add(backBtn);

		add(centerPnl, "Center");
		add(southPnl, "South");

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("확인")) {
			UserDAO userDAO = new UserDAO();
			String grade = userDAO.payment(User.getCurUser().getUserId(), buyGameIdList, totalPaymentMoney);
			if (grade != null) {
				User.getCurUser().setUserUsedCash(User.getCurUser().getUserUsedCash() + totalPaymentMoney);
				User.getCurUser().setUserChargeMoney(User.getCurUser().getUserChargeMoney() - totalPaymentMoney);
				
				if (!grade.equals("")) {
					User.getCurUser().setUserGrade(grade);
				}
				JOptionPane.showMessageDialog(this, "결제가 완료되었습니다.");
				this.setVisible(false);
				
				// 쇼핑 카트 패널에서 모든 항목 제거
				shoopingCart.removeAll();
				// 패널의 레이아웃과 내용 갱신
				((ShoopingCart) shoopingCart).reconstruction();
				shoopingCart.revalidate();
				shoopingCart.repaint();
			} else {
				JOptionPane.showMessageDialog(this, "오류발생. UserDAO의 payment메소드 검토");
			}
		} else if (e.getActionCommand().equals("결제 취소")) {
			this.setVisible(false);
		}
	}
}

public class ShoopingCart extends JPanel implements ActionListener {
	private List<List<String>> gameInfoList;
	private List<JCheckBox> checkBoxList;
	private List<JPanel> pnlList;
	private List<String> gameIdList;
	private JPanel mainPnl;
	public ShoopingCart() {
		reconstruction();
	}

	public void reconstruction() {
		// 게임 이름, 게임 기본 가격, 할인율, 이미지 데이터 아이디 순서로 리스트에 삽입
		DataManager.inputData("ShoopingCart", this);
		
		gameIdList = new ArrayList<>();
		gameInfoList = UserDAO.userShoopingCart(User.getCurUser().getUserId());

		checkBoxList = new ArrayList<>();
		pnlList = new ArrayList<>();

		setLayout(new BorderLayout());
		mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS)); // BoxLayout으로 수직 배치 설정

		JScrollPane js = new JScrollPane(mainPnl);
		js.getVerticalScrollBar().setUnitIncrement(10);

		if (gameInfoList.size() < 1) {
			JLabel lbl = new JLabel("현재 장바구니에 담겨있는 목록이 없습니다.");
			add(lbl, BorderLayout.CENTER);
		} else {
			setLayout(new BorderLayout());
			for (int i = 0; i < gameInfoList.size(); i++) {
				JPanel pnl = new JPanel();
				pnl.setLayout(null);
				pnl.setPreferredSize(new Dimension(800, 150)); // 패널의 크기를 명시적으로 설정
				pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // 최대 크기를 설정하여 늘어나지 않도록 제한

				Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

				JCheckBox checkBox = new JCheckBox();
				checkBox.setBounds(10, 75, 20, 20);
				checkBoxList.add(checkBox);
				JLabel image = JLableFactory.createLblwithIcon(Integer.valueOf(gameInfoList.get(i).get(3)));
				image.setBounds(40, 40, 150, 85);
				image.setBorder(border);
				
				int x = 200;
				JLabel nameLbl = new JLabel(gameInfoList.get(i).get(0));
				nameLbl.setBounds(x, 60, (nameLbl.getText().length()*10 + 20), 40);
				nameLbl.setBorder(border);
				x = x + nameLbl.getWidth() + 10;

				JLabel priceLbl = new JLabel("상품 가격 : " + gameInfoList.get(i).get(1) + "원");
				priceLbl.setBounds(x, 60, (priceLbl.getText().length()*10 + 20), 40);
				priceLbl.setBorder(border);
				x = x + priceLbl.getWidth() + 10;

				JLabel discountLbl = new JLabel("할인 된 가격 : " + Function.priceCalculate(
						Integer.valueOf(gameInfoList.get(i).get(1)), Integer.valueOf(gameInfoList.get(i).get(2))) + "원");
				discountLbl.setBounds(x, 60, (discountLbl.getText().length()*10 + 20), 40);
				discountLbl.setBorder(border);
				x = x + discountLbl.getWidth() + 10;

				JLabel discountPercentLbl = new JLabel("할인율 : " + gameInfoList.get(i).get(2) + "%");
				discountPercentLbl.setBounds(x, 60, (discountPercentLbl.getText().length()*10 + 20), 40);
				discountPercentLbl.setBorder(border);

				gameIdList.add(gameInfoList.get(i).get(4));
				
				pnl.add(checkBox);
				pnl.add(image);
				pnl.add(nameLbl);
				pnl.add(priceLbl);
				pnl.add(discountLbl);
				pnl.add(discountPercentLbl);
				
				pnlList.add(pnl);
				mainPnl.add(pnl);
			}

			JPanel southPnl = new JPanel();
			JButton buyBtn = new JButton("체크된 목록 모두 구매하기");
			buyBtn.setPreferredSize(new Dimension(200, 40));
			southPnl.add(buyBtn);
			JButton deleteBtn = new JButton("체크된 목록 모두 삭제하기");
			deleteBtn.setPreferredSize(new Dimension(200, 40));
			deleteBtn.setBackground(new Color(250, 50, 50));
			southPnl.add(deleteBtn);

			buyBtn.addActionListener(this);
			deleteBtn.addActionListener(this);

			add(js, BorderLayout.CENTER);
			add(southPnl, BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("체크된 목록 모두 구매하기")) {
			int count = boxCheck();
			if (count != 0) {
				UserDAO userDAO = new UserDAO();
				List<Integer> buyGameIdList = new ArrayList<>();
				
				int resultPrice = 0;
				for (int i = 0; i < checkBoxList.size(); i++) {
					if (checkBoxList.get(i).isSelected()) {
						int discountPrice = Function.priceCalculate(Integer.valueOf(gameInfoList.get(i).get(1)), Integer.valueOf(gameInfoList.get(i).get(2)));
						resultPrice += discountPrice;
						buyGameIdList.add(Integer.valueOf(gameIdList.get(i)));
					}
				}
				int userChargeMoney = userDAO.takeUserChargeMoney(User.getCurUser().getUserId());
				if (userChargeMoney >= resultPrice) {
					Dialog buyDialog = new BuyDialog(resultPrice, userChargeMoney, this, buyGameIdList);
					buyDialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(this, "잔고가 부족하여 구매를 진행할 수 없습니다.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "체크된 목록이 없습니다.");
			}
		} else if (e.getActionCommand().equals("체크된 목록 모두 삭제하기")) {
			int count = boxCheck();
			if (count != 0) {
				boolean result = UserDAO.removeShoopingList(User.getCurUser().getUserId(), gameIdList, checkBoxList);
				if (result) {
					this.removeAll();
					this.reconstruction();
					this.revalidate();
					this.repaint();
				} else {
					JOptionPane.showMessageDialog(this, "에러발생");
				}
				
			} else {
				JOptionPane.showMessageDialog(this, "체크된 목록이 없습니다.");
			}
		}
	}

	private int boxCheck() {
		int count = 0;
		for (int i = 0; i < checkBoxList.size(); i++) {
			if (checkBoxList.get(i).isSelected()) {
				count++;
			}
		}
		return count;
	}
}
