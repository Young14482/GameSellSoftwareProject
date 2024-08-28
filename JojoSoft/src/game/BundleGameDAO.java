package game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import materials.DBUtil;

public class BundleGameDAO {
	public int insert(int key, int key2) {
		GameDAO gameDAO = new GameDAO();
		Game g = gameDAO.getGame(key);
		String sql = "INSERT INTO bundle_game (bundle_id, game_id) VALUES (?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		int bundleNum = -1;
		if (g.getGame_category().equals("번들")) {
			bundleNum = g.getGame_Id();
		}

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, bundleNum);
			stmt.setInt(2, key2);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, stmt, conn);
		}
		return 0;
	}
	
	public List<Integer> getGameInBundleList(int key){
		String sql = "SELECT * FROM bundle_game WHERE bundle_Id LIKE ?;";

		List<Integer> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);
			rs = stmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("game_id"));
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
