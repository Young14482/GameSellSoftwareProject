package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import materials.DBUtil;
import materials.Function;
import materials.IResultMapper;

public class UserDAO {
	private static final IResultMapper<User> userMapper = new UserMapper();

	// 로그인 할 때 아이디와 패스워드를 받아와 디비에 존재하는 유저인지 확인하는 메소드
	public User findMember(String userId, String userPw) {
		String sql = "select * from user where user_id = ? and user_pw = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			String changePw = Function.changePW(userPw);
			stmt.setString(2, changePw);

			rs = stmt.executeQuery();

			if (rs.next()) {
				return userMapper.resultMapping(rs);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, UserDAO 클래스 find 검토 요망");
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}

		return null;
	}

	// 회원가입 시 중복된 아이디 또는 중복된 닉네임이 있는지 체크하는 메소드
	public String checkUser(String userId, String userNickName, String birth, String phoneNum) {
		String sqlId = "select * from user where user_id = ?";
		String sqlNick = "select * from user where user_nickname = ?";
		String sqlPhone = "select * from user where user_phonenumber = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement stmt2 = null;
		ResultSet rs2 = null;
		PreparedStatement stmt3 = null;
		ResultSet rs3 = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sqlId);
			stmt.setString(1, userId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				return "아이디";
			}

			stmt2 = conn.prepareStatement(sqlNick);
			stmt2.setString(1, userNickName);
			rs2 = stmt2.executeQuery();
			if (rs2.next()) {
				return "닉네임";
			}

			stmt3 = conn.prepareStatement(sqlPhone);
			stmt3.setString(1, phoneNum);
			rs3 = stmt3.executeQuery();
			if (rs3.next()) {
				return "전화번호";
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, UserDAO 클래스 check 검토 요망");
		} finally {
			DBUtil.closeAll(rs, stmt, null);
			DBUtil.closeAll(rs2, stmt2, null);
			DBUtil.closeAll(rs3, stmt3, conn);
		}

		String exp = "^(19[1-9][0-9]|20[0-1][0-9]|2020)-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		String exp2 = "^\\d{3}-\\d{3,4}-\\d{4}$";
		// 정규표현식으로 문자열의 동등함을 비교
		Pattern p = Pattern.compile(exp);
		Matcher m1 = p.matcher(birth);
		Pattern p2 = Pattern.compile(exp2);
		Matcher m2 = p2.matcher(phoneNum);

		if (!m1.matches()) {
			return "전화번호양식";

		} else if (!m2.matches() || !isDate(phoneNum)) {
			return "생년월일";
		} else {
			return null;
		}
	}

	private boolean isDate(String date) {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			int year = LocalDate.parse(date, dateTimeFormatter).getYear();

			return true;
		} catch (DateTimeParseException ex) {
			return false;
		}
	}

	// 유저를 데이터 베이스에 저장하는 메소드
	public int insertUser(String userId, String userPw, String userNickName, String userBirth, String userPhoneNumber) {
		String sql = "insert into user(user_id, user_pw, user_nickname, user_birth, user_phonenumber) values (?, ?, ?, ?, ?);";
		int result = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			String changePw = Function.changePW(userPw);
			stmt.setString(2, changePw);
			stmt.setString(3, userNickName);
			stmt.setString(4, userBirth);
			stmt.setString(5, userPhoneNumber);

			result = stmt.executeUpdate();
			if (result == 1) {
				conn.commit();
				return result;
			}
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "예외 발생, UserDAO 클래스 insert 검토 요망");
		} finally {
			DBUtil.closeAll(null, stmt, conn);
		}
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 전화번호를 이용하여 유저 아이디를 찾는 메소드
	public int findIdToUsingPhoneNumber(String phoneNumber) {

		Matcher m = phoneNumberFome(phoneNumber);

		if (!m.matches()) {
			JOptionPane.showMessageDialog(null, "전화번호 양식이 올바르지 않습니다. ex : 010-1234-1234");
		} else {
			String sql = "select user_id from user where user_phonenumber = ?";

			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				conn = DBUtil.getConnection("jojosoft");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, phoneNumber);

				rs = stmt.executeQuery();
				if (rs.next()) {
					JOptionPane.showMessageDialog(null, "아이디 : " + rs.getString("user_id"));
					return 1;
				} else {
					JOptionPane.showMessageDialog(null, "해당 전화번호로 등록된 아이디가 없습니다.");
					return 0;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "예외 발생, function 클래스 검토 요망");
			} finally {
				DBUtil.closeAll(rs, stmt, conn);
			}
		}
		return 0;
	}

	// 아이디를 이용하여 유저를 찾는 메소드
	public int findIdToUsingId(String select, String userId) {

		String sql = "select user_id from user where user_id= ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "유저 확인 완료");
				conn.commit();
				return 1;
			} else {
				JOptionPane.showMessageDialog(null, "해당 아이디로 등록된 기록이 없습니다.");
				conn.commit();
				return 0;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, function 클래스 검토 요망");
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return 0;
	}

	/**
	 * <<<<<<< HEAD 유저 아이디를 통해 유저의 정보를 변경하는 메소드 String select 문구를 바꾸어서 기능을 변경시킬 수 있음
	 * select 종류 : 아이디로 찾기, 비밀번호 변경, 닉네임 변경, 생년월일 변경 비밀번호, 닉네임, 전화번호, 생년월일도 변경할 수
	 * 있도록 추가 0을 리턴하면 메소드로 걸러내지 못한 값을 반영하려고 하여 반영에 실패하였을 때 0을 리턴, 1을 리턴하면 정상적으로
	 * 반영되었을 때, 2를 리턴하면 올바른 양식대로 작성하지 않았을 때, 3을 리턴하면 기존과 동일한 값을 입력하였을 때 4를 리턴하면 이미
	 * 존재하는 정보라 (Unique) 반영할 수 없을 때 ======= 유저 아이디를 통해 유저의 정보를 변경하는 메소드 String
	 * select 문구를 바꾸어서 기능을 변경시킬 수 있음 select 종류 : 아이디로 찾기, 비밀번호 변경, 닉네임 변경, 생년월일 변경
	 * 비밀번호, 닉네임, 전화번호, 생년월일도 변경할 수 있도록 추가 >>>>>>> stash
	 */
	public int changeUserInfo(String select, String userId, String userPw, String userNickName, String phoneNumber,
			String userBirth) {

		String sql = null;
		if (select.equals("비밀번호 변경")) {
			sql = "update user set user_pw = ? where user_id = ?";
		} else if (select.equals("닉네임 변경")) {
			sql = "update user set user_nickname = ? where user_id = ?";
		} else if (select.equals("전화번호 변경")) {
			sql = "update user set user_phonenumber = ? where user_id = ?";
		} else if (select.equals("생년월일 변경")) {
			sql = "update user set user_birth = ? where user_id = ?";
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtAnother = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);

			if (select.equals("비밀번호 변경")) {
				String duplicateCheck = "select user_pw from user where user_id= ?";
				stmtAnother = conn.prepareStatement(duplicateCheck);
				stmtAnother.setString(1, userId);
				rs = stmtAnother.executeQuery();
				rs.next();
				String comparison = rs.getString("user_pw");
				String changePw = Function.changePW(userPw);

				if (comparison.equals(changePw)) {
					// 3을 리턴하면 기존과 동일한 비밀번호라는 의미.
					return 3;
				} else {
					stmt.setString(1, changePw);
					stmt.setString(2, userId);
					int result = stmt.executeUpdate();
					if (result == 1) {
						conn.commit();
						return result;
					} else {
						conn.rollback();
					}
				}
			} else if (select.equals("닉네임 변경")) {
				String duplicateCheck = "select user_nickname from user where user_id= ?";
				stmtAnother = conn.prepareStatement(duplicateCheck);
				stmtAnother.setString(1, userId);
				rs = stmtAnother.executeQuery();
				rs.next();
				String comparison = rs.getString("user_nickname");

				if (comparison.equals(userNickName)) {
					// 3을 리턴하면 기존과 동일한 닉네임이라는 의미
					return 3;
				}

				// 기존의 stmt와 rs를 사용하려고 했으나 이클립스에서 해당 방법을 권장하지 않아(경고(노란색 밑줄))
				// 새로운 stmt와 rs를 다시 작성하게 되었습니다.
				String existCheck = "select user_nickname from user where user_nickname = ?";
				PreparedStatement statement = conn.prepareStatement(existCheck);
				statement.setString(1, userNickName);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					// 이미 변경하려는 닉네임이 존재할 때
					return 4;
				}

				stmt.setString(1, userNickName);
				stmt.setString(2, userId);

				int result = stmt.executeUpdate();
				if (result == 1) {
					conn.commit();
					return result;
				}

			} else if (select.equals("전화번호 변경")) {

				Matcher m = phoneNumberFome(phoneNumber);
				if (!m.matches()) {
					// 만약 2를 리턴하면 양식이 틀렸다는 의미
					return 2;
				}

				String duplicateCheck = "select user_phonenumber from user where user_id= ?";
				stmtAnother = conn.prepareStatement(duplicateCheck);
				stmtAnother.setString(1, userId);
				rs = stmtAnother.executeQuery();
				rs.next();
				String comparison = rs.getString("user_phonenumber");
				if (comparison.equals(phoneNumber)) {
					// 3을 리턴하면 기존과 동일한 전화번호라는 의미
					return 3;
				}

				// 기존의 stmt와 rs를 사용하려고 했으나 이클립스에서 해당 방법을 권장하지 않아(경고(노란색 밑줄))
				// 새로운 stmt와 rs를 다시 작성하게 되었습니다.
				String existCheck = "select user_phonenumber from user where user_phonenumber = ?";
				PreparedStatement statement = conn.prepareStatement(existCheck);
				statement.setString(1, phoneNumber);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					// 이미 변경하려는 전화번호가 존재할 때
					return 4;
				}

				stmt.setString(1, phoneNumber);
				stmt.setString(2, userId);
				int result = stmt.executeUpdate();

				if (result == 1) {
					conn.commit();
					return result;
				}

			} else if (select.equals("생년월일 변경")) {

				Matcher m = birthForm(userBirth);
				if (!m.matches()) {
					// 만약 2를 리턴하면 양식이 틀렸다는 의미
					return 2;
				}

				String duplicateCheck = "select user_birth from user where user_id= ?";
				stmtAnother = conn.prepareStatement(duplicateCheck);
				stmtAnother.setString(1, userId);
				rs = stmtAnother.executeQuery();
				rs.next();
				String comparison = rs.getString("user_birth");

				if (comparison.equals(userBirth)) {
					// 3을 리턴하면 기존과 동일한 생년월일이라는 의미
					return 3;
				}
				stmt.setString(1, userBirth);
				stmt.setString(2, userId);
				int result = stmt.executeUpdate();

				if (result == 1) {
					conn.commit();
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeAll(rs, stmt, conn);
		}
		return 0;
	}

	// 로그인 한 유저의 게임 관련 결제 이력을 나타내는 정보
	public static List<List<String>> makeUserInfoList() {

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
			JOptionPane.showMessageDialog(null, "예외 발생. UserDAO 클래스 확인");
		}
		return result;
	}

	// 유저가 장바구니에 담은 게임들을 찾아서 반환하는 메소드
	// (order 테이블에서 유저 아이디로 검색하여 order_status 값들 중 0인 값만 가져옴)
	public static List<List<String>> userShoopingCart(String userId) {
		String sql = "select C.game_name, C.game_price, B.order_discount, D.id as 'imageData', C.game_id from `order` as A \r\n"
				+ "join `order_list` as B using (order_id)\r\n" + "join `game` as C using (game_id)\r\n"
				+ "join `picture` as D on (C.game_profile = D.id)\r\n" + "where A.user_id = ? and A.order_status = 0;";
		List<List<String>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			// 게임 이름, 게임 기본 가격, 할인율, 이미지 데이터 아이디 순서로 리스트에 삽입
			while (rs.next()) {
				List<String> detailList = new ArrayList<>();
				for (int i = 1; i < 6; i++) {
					detailList.add(rs.getString(i));
				}
				list.add(detailList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeAll(rs, stmt, conn);
		}
		return list;
	}

	// 유저의 아이디를 통하여 돈을 충전시켜주는 메소드
	public static int chargeMoney(String userId, int chargeMoney) {
		String sql = "update user set user_chargeMoney = user_chargeMoney + ? where user_id = ?;";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, chargeMoney);
			stmt.setString(2, userId);
			int result = stmt.executeUpdate();
			if (result == 1) {
				conn.commit();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeAll(null, stmt, conn);
		}
		return 0;
	}

	// 유저의 현재 충전되어 있는 금액을 리턴하는 메소드
	// 오류가 발생하면 -1을 반환
	public int takeUserChargeMoney(String userId) {
		String sql = "select user_chargeMoney from user where user_id = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, UserDAO 클래스 userMoneyCheck 메소드 검토 ");
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return -1;
	}

	// 장바구니에 담겨있는 목록 중 체크한 목록들을 모두 구매시켜주고 DB값을 변경해주는 메소드
	public boolean payment(String userId, List<Integer> buyGameIdList, int totalPaymentMoney) {

		String sqlSelectOrderId = "SELECT order_id FROM `order` where user_id = ? and order_status = 0;";
		String sqlUpdateUser = "update user set user_chargeMoney = user_chargeMoney - ?, user_usedcash = user_usedcash + ? where user_id = ?;";
		String sqlUpdateOrder = "update `order` set order_status = 1 where user_id = ? and order_status = 0;";
		String sqlDeleteOrderDetail = "DELETE FROM order_list\r\n" + "		WHERE order_id = ?";
		for (int i = 0; i < buyGameIdList.size(); i++) {
			sqlDeleteOrderDetail += (" and game_id != " + buyGameIdList.get(i));
		}

		Connection conn1 = null;
		PreparedStatement stmt1 = null;
		Connection conn2 = null;
		PreparedStatement stmt2 = null;
		Connection conn3 = null;
		PreparedStatement stmt3 = null;
		Connection conn4 = null;
		PreparedStatement stmt4 = null;

		ResultSet rs = null;
		try {
			conn1 = DBUtil.getConnection("jojosoft");
			conn2 = DBUtil.getConnection("jojosoft");
			conn3 = DBUtil.getConnection("jojosoft");
			conn4 = DBUtil.getConnection("jojosoft");

			conn1.setAutoCommit(false);
			conn2.setAutoCommit(false);
			conn3.setAutoCommit(false);
			conn4.setAutoCommit(false);

			stmt1 = conn1.prepareStatement(sqlSelectOrderId);
			stmt2 = conn2.prepareStatement(sqlUpdateUser);
			stmt3 = conn3.prepareStatement(sqlUpdateOrder);
			stmt4 = conn3.prepareStatement(sqlDeleteOrderDetail);

			stmt1.setString(1, userId);
			stmt2.setInt(1, totalPaymentMoney);
			stmt2.setInt(2, totalPaymentMoney);
			stmt2.setString(3, userId);
			stmt3.setString(1, userId);

			rs = stmt1.executeQuery();
			rs.next();
			int orderId = rs.getInt(1);
			stmt4.setInt(1, orderId);

			int result1 = stmt2.executeUpdate();
			int result2 = stmt3.executeUpdate();
			int result3 = stmt4.executeUpdate();
			conn2.commit();
			conn3.commit();
			conn4.commit();
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, UserDAO 클래스 payment 메소드 검토 ");
		} finally {
			try {
				conn2.rollback();
				conn3.rollback();
				conn4.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeAll(null, stmt1, conn1);
			DBUtil.closeAll(null, stmt2, conn2);
			DBUtil.closeAll(null, stmt3, conn3);
		}
		return false;
	}
	
	// 장바구니에 담겨있는 목록 중 체크한 목록들을 모두 지우는 메소드
		public static boolean removeShoopingList(String userId, List<String> gameIdList, List<JCheckBox> boxList) {

			String sqlSelectOrderId = "SELECT order_id FROM `order` where user_id = ? and order_status = 0;";
			String sqlDeleteOrderDetail = "DELETE FROM order_list\r\n" + "		WHERE order_id = ?";
			for (int i = 0; i < gameIdList.size(); i++) {
				if (boxList.get(i).isSelected()) {
					sqlDeleteOrderDetail += (" and game_id = " + gameIdList.get(i));
				}
			}

			Connection conn1 = null;
			PreparedStatement stmt1 = null;
			Connection conn2 = null;
			PreparedStatement stmt2 = null;

			ResultSet rs = null;
			try {
				conn1 = DBUtil.getConnection("jojosoft");
				conn2 = DBUtil.getConnection("jojosoft");

				conn1.setAutoCommit(false);
				conn2.setAutoCommit(false);

				stmt1 = conn1.prepareStatement(sqlSelectOrderId);
				stmt2 = conn2.prepareStatement(sqlDeleteOrderDetail);

				stmt1.setString(1, userId);
				
				rs = stmt1.executeQuery();
				rs.next();
				int orderId = rs.getInt(1);
				stmt2.setInt(1, orderId);

				stmt2.executeUpdate();
				conn2.commit();
				return true;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "예외 발생, UserDAO 클래스 payment 메소드 검토 ");
			} finally {
				try {
					conn2.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DBUtil.closeAll(null, stmt1, conn1);
				DBUtil.closeAll(null, stmt2, conn2);
			}
			return false;
		}

	// 전화번호 양식 체크 메소드
	private Matcher phoneNumberFome(String phoneNumber) {
		String exp = "^(010-[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9])$";
		// 정규표현식으로 문자열의 동등함을 비교
		Pattern p = Pattern.compile(exp);
		Matcher m = p.matcher(phoneNumber);
		return m;
	}

	// 생년월일 양식 체크 메소드
	private Matcher birthForm(String birth) {
		String exp = "^(19[1-9][0-9]|200[0-9]|201[0-9]|2020)-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		// 정규표현식으로 문자열의 동등함을 비교
		Pattern p = Pattern.compile(exp);
		Matcher m = p.matcher(birth);
		return m;
	}

}
