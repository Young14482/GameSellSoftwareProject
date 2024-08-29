package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import game.Game;
import materials.DBUtil;
import user.User;

public class OrderListDAO {
	public int insert(int order_id) {
		Game g = Game.getCurGame();
		String sql = "INSERT INTO order_list (order_id, game_id, order_discount) VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, order_id);
			stmt.setInt(2, g.getGame_Id());
			stmt.setInt(3, g.getGame_discount());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, stmt, conn);
		}
		return 0;
	}

	public int checkGame(int order_idOrNot) {
		Game g = Game.getCurGame();
		String sql = "SELECT COUNT(*) AS '이새끼이거있음' FROM order_list WHERE order_id = ? AND game_id = ?;";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, order_idOrNot);
			stmt.setInt(2, g.getGame_Id());
			rs = stmt.executeQuery();
			if(rs.next()) {
				int result = rs.getInt("이새끼이거있음");
				return result;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "예외 발생");
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return 0;
	}
	public int deleteOrderListWithOrderId(Connection conn, List<Integer> orderIdList) {
		String sql = "DELETE FROM order_list WHERE order_id = ?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			for(int orderId : orderIdList) {
				stmt.setInt(1, orderId);
				stmt.executeUpdate();
			}
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
		return 0;
}
}
