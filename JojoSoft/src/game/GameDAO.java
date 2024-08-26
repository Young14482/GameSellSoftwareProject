package game;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import materials.DBUtil;

public class GameDAO {
	public Game getGame(int key) {
		String sql = "SELECT * FROM game WHERE game_Id = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int game_Id = rs.getInt("game_Id");
				String game_name = rs.getString("game_name");
				int game_price = rs.getInt("game_price");
				int game_discount = rs.getInt("game_discount");
				int age_limit = rs.getInt("age_limit");
				String game_genre = rs.getString("game_genre");
				String game_production = rs.getString("game_production");
				String game_ifgo = rs.getString("game_info");
				Date game_release = rs.getDate("game_release");
				int game_profile = rs.getInt("game_profile");
				String game_category = rs.getString("game_category");

				return new Game(game_Id, game_name, game_price, game_discount, age_limit, game_genre, game_production, game_ifgo,
						game_release, game_profile, game_category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}

	public int insertGame(String game_name, int game_price, int age_limit, String game_genre, String game_production,
			String game_ifgo, Date game_release, int game_profile, String game_category) {
		String sql = "INSERT INTO game (game_name, game_price, age_limit, game_genre, game_production,\r\n"
				+ "	game_ifgo, game_release, game_profile, game_category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, game_name);
			stmt.setInt(2, game_price);
			stmt.setInt(3, age_limit);
			stmt.setString(4, game_genre);
			stmt.setString(5, game_production);
			stmt.setString(6, game_ifgo);
			stmt.setDate(7, game_release);
			stmt.setInt(8, game_profile);
			stmt.setString(9, game_category);
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, stmt, conn);
		}
		return 0;
	}
}
