package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import materials.DBUtil;
import user.User;

public class OrderDAO {
	/**
	 * 
	 * @return
	 */
	public int checkAndInsert() {
		List<Integer> list = getNotBuyList();
		OrderListDAO dao = new OrderListDAO();
		if(dao.checkGame(list) != 0) {
			return 0;
		}
		User u = User.getCurUser();
		String sql = "INSERT INTO `order` (user_id) VALUES (?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, u.getUserId());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return 0;
	}
	/**
	 * 체크안하고 바로 넣는 버젼
	 * @return 잘들어가면 1 아니면 0
	 */
	public int insert() {
		OrderListDAO dao = new OrderListDAO();
		User u = User.getCurUser();
		String sql = "INSERT INTO `order` (user_id) VALUES (?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, u.getUserId());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return 0;
	}
	/**
	 * @return 결제상태를 보고 결제 안 된것들의 order_id를 리스트화 해서 반환
	 */
	public List<Integer> getNotBuyList() {
		User u = User.getCurUser();
		String sql = "SELECT order_id FROM `order` WHERE order_status = 0 AND user_id = ?;";

		List<Integer> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUserId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("order_id"));
			}
			return list;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생, function 클래스 검토 요망");
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}
}
