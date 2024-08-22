package materials;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Function {

	// 로그인 할 때 아이디와 패스워드를 받아와 디비에 존재하는 유저인지 확인하는 메소드
	public User findMember(String userId, String userPw, Connection conn) {
		String sql = "select * from user where user_id = ? and user_pw = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			String changePw = changePW(userPw);
			stmt.setString(2, changePw);

			rs = stmt.executeQuery();

			if (rs.next()) {
				String userid = rs.getString("user_id");
				String userpw = rs.getString("user_pw");
				String userNickName = rs.getString("user_nickname");
				String userGrade = rs.getString("user_grade");
				String userBirth = rs.getString("user_birth");
				int userUsedCash = rs.getInt("user_usedcash");

				User user = new User(userid, userpw, userNickName, userGrade, userBirth, userUsedCash);

				return user;
			} else {
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, function 클래스 검토 요망");
		} finally {
			DBUtil.closeAll(rs, stmt, null);
		}

		return null;
	}

	// 회원가입 시 중복된 아이디 또는 중복된 닉네임이 있는지 체크하는 메소드
	public String checkUser(String userId, String userNickName, String birth, Connection conn) {
		String sqlId = "select * from user where user_id = ?";
		String sqlNick = "select * from user where user_nickname = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement stmt2 = null;
		ResultSet rs2 = null;
		try {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, function 클래스 검토 요망");
		} finally {
			DBUtil.closeAll(rs, stmt, null);
			DBUtil.closeAll(rs2, stmt2, null);
		}

		String exp = "^(19[1-9][0-9]|200[0-9]|201[0-9]|2020)-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		// 정규표현식으로 문자열의 동등함을 비교
		Pattern p = Pattern.compile(exp);
		Matcher m = p.matcher(birth);
		System.out.println(m.matches());
		if (m.matches()) {
			return null;
		} else {
			return "생년월일";
		}
	}
	
	// 유저를 데이터 베이스에 저장하는 메소드
	public int insertUser(Connection conn, String userId, String userPw, String userNickName, String userBirth) {
		String sql = "insert into user(user_id, user_pw, user_nickname, user_birth) values (?, ?, ?, ?);";
		int result = 0;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			String changePw = changePW(userPw);
			stmt.setString(2, changePw);
			stmt.setString(3, userNickName);
			stmt.setString(4, userBirth);
			
			result = stmt.executeUpdate();
			if (result == 1) {
				System.out.println("정상적으로 업데이트");
				return result;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, function 클래스 검토 요망");
		}
		
		return result;
	}

	// 문자열을 넣으면 메소드 내용을 토대로 암호화 된 문자열을 반환
	public String changePW(String password) {
		String result = "";

		for (int i = 0; i < password.length(); i++) {
			char split = password.charAt(i);
			int splitNum = Integer.valueOf(split);
			
			if (splitNum == 0 || splitNum == 127) {
			} else {
				splitNum++;
			}
			String change = String.valueOf((char) splitNum);
			
			result += change;
		}
		return result;
	}
	
	// 암호화 된 비밀번호를 원복시켜 반환
	public String changePWBasic(String password) {
		String result = "";

		for (int i = 0; i < password.length(); i++) {
			char split = password.charAt(i);
			int splitNum = Integer.valueOf(split);
			
			if (splitNum == 0 || splitNum == 127) {
			} else {
				splitNum--;
			}
			String change = String.valueOf((char) splitNum);
			
			result += change;
		}
		return result;
	}
}
