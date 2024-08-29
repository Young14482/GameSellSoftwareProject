package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import materials.DBUtil;
import materials.Function;

public class DeleteUserDAO {
	public int insertDeleteUser(Connection conn) {
		User u = User.getCurUser();
		String sql = "insert into deleteusers(user_id, user_pw, user_nickname, user_birth, user_phonenumber) values (?, ?, ?, ?, ?);";
		int result = 0;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUserId());
			stmt.setString(2, u.getUserPw());
			stmt.setString(3, u.getUserNickName());
			stmt.setString(4, u.getUserBirth());
			stmt.setString(5, u.getUserPhoneNumber());

			result = stmt.executeUpdate();
			if (result == 1) {
				conn.commit();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
