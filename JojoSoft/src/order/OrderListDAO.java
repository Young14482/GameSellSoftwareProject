package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Encoder;

import game.Game;
import materials.DBUtil;

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
}
