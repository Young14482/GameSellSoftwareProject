import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import materials.DBUtil;
import user.User;


// 구매한 게임 이력을 확인할수 있는 탭
class ShoopingInfo extends JPanel {
	public ShoopingInfo() {

		setLayout(new GridLayout(5, 1));
		// 게임 아이디, 유저 아이디, 게임이름, 구매날짜, 당시 구매가, 결제 상태 순서로 리스트에 들어가있음
		List<List<String>> userInfoList = makeUserInfoList();

		for (int i = 0; i < userInfoList.size(); i++) {

			JPanel pnl = new JPanel();

			makeLbl("게임 아이디 : ", userInfoList.get(i), 0, pnl);
			makeLbl("유저 아이디 : ", userInfoList.get(i), 1, pnl);
			makeLbl("게임 이름 : ", userInfoList.get(i), 2, pnl);
			makeLbl("구매 날짜 : ", userInfoList.get(i), 3, pnl);
			makeLbl("구매 가격 : ", userInfoList.get(i), 4, pnl);
			makeLbl("", userInfoList.get(i), 5, pnl);

			add(pnl);
		}
	}
	
	private JLabel makeLbl(String detail, List<String> list, int index, JPanel pnl) {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		JLabel lbl = new JLabel(detail + list.get(index));
		lbl.setPreferredSize(new Dimension(lbl.getText().length()*11+5, 40));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setBorder(border);
		pnl.add(lbl);
		return lbl;
	}

	private List<List<String>> makeUserInfoList() {

//		select game_id, A.user_nickname, D.game_name, order_date,round(D.game_price * (100 - C.order_discount) / 100, -2) as '당시 구매가' from `user` as A
//		   join `order` as B using (user_id)
//		    join `order_list` as C using (order_Id)
//		    join game as D using (game_id);

		List<List<String>> result = new ArrayList<>();

		String sql = "select game_id, A.user_id, D.game_name, order_date,round(D.game_price * (100 - C.order_discount) / 100, -2) as '당시 구매가', order_status from `user` as A\r\n"
				+ "   join `order` as B using (user_id)\r\n" + "    join `order_list` as C using (order_Id)\r\n"
				+ "    join game as D using (game_id)\r\n" + "    where A.user_id = ?;";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, User.getCurUser().getUserId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				List<String> collect = new ArrayList<>();
				for (int i = 1; i < 7; i++) {
					if (i != 4) {
						if (i == 6) {
							if (rs.getString(i).equals("1")) {
								collect.add("결제 완료");
							} else {
								collect.add("결제 X");
							}
						}
						collect.add(rs.getString(i));
					} else {
						String before = rs.getString(i);
						String after = before.substring(0, 10);
						collect.add(after);
					}
				}
				result.add(collect);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생. ShoopingInfo 클래스 확인");
		}
		return result;
	}
}

// 정보수정 탭. 구현 중
class InfoChange extends JPanel {
	public InfoChange() {

		JPanel eastPnl = new JPanel();
		JPanel westPnl = new JPanel();
		eastPnl.setLayout(new GridLayout(6, 1));
		westPnl.setLayout(new GridLayout(4, 1));
		JLabel idLbl = new JLabel("아이디 : " + User.getCurUser().getUserId());
		idLbl.setFont(new Font("굴림", Font.BOLD, 18));
		JLabel nickNameLbl = new JLabel("닉네임 : " + User.getCurUser().getUserNickName());
		nickNameLbl.setFont(new Font("굴림", Font.BOLD, 18));
		JLabel phoneLbl = new JLabel("등록된 전화번호 : " + User.getCurUser().getUserPhoneNumber());
		phoneLbl.setFont(new Font("굴림", Font.BOLD, 18));
		JLabel birthLbl = new JLabel("등록된 생년월일 : " + User.getCurUser().getUserBirth());
		birthLbl.setFont(new Font("굴림", Font.BOLD, 18));
		JLabel gradeLbl = new JLabel("회원 등급 : " + User.getCurUser().getUserGrade());
		gradeLbl.setFont(new Font("굴림", Font.BOLD, 18));
		JLabel moneyLbl = new JLabel("현재까지 사용금액 : " + User.getCurUser().getUserUsedCash());
		moneyLbl.setFont(new Font("굴림", Font.BOLD, 18));
		
		JButton idChangeBtn = new JButton("비밀번호 변경하기");
		JButton nickNameChangeBtn = new JButton("닉네임 변경하기");
		JButton phoneNumChangeBtn = new JButton("전화번호 변경하기");
		JButton dateChangeBtn = new JButton("생년월일 변경하기");
		
		
		eastPnl.add(idLbl);
		eastPnl.add(nickNameLbl);
		eastPnl.add(phoneLbl);
		eastPnl.add(birthLbl);
		eastPnl.add(gradeLbl);
		eastPnl.add(moneyLbl);
		
		westPnl.add(idChangeBtn);
		westPnl.add(nickNameChangeBtn);
		westPnl.add(phoneNumChangeBtn);
		westPnl.add(dateChangeBtn);
		
		add(eastPnl, "East");
		add(westPnl, "west");
	}
}

// 회원 정보 보기 버튼을 눌렀을 때 띄워주는 창
public class MemberInfoPnl extends JPanel {

	private ShoopingInfo shoopingInfo = new ShoopingInfo();

	public MemberInfoPnl() {
		JTabbedPane tabbedPane = new JTabbedPane();
		InfoChange infoChange = new InfoChange();
		tabbedPane.addTab("회원 정보 수정", infoChange);
		tabbedPane.addTab("쇼핑 정보", shoopingInfo);
		add(tabbedPane);
		setSize(1200, 600);
	}
//	public static void main(String[] args) {
//		new MemberInfoPnl().setVisible(true);
//	}
}
