package picture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;

import materials.DBUtil;

public class GamePictureDAO {
	public int getPicture(int key) {
		String sql = "SELECT * FROM game_picture WHERE game_id = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int pictureId = rs.getInt("picture_id");
				return pictureId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return -1;
	}

	public int insertGamePicture(Connection conn, int game_id, int picture_id) {
		String sql = "INSERT INTO game_picture VALUES (?, ?)";

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, game_id);
			stmt.setInt(2, picture_id);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException("뭔가 잘 못 됨");
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}

	public int deleteGamePicture(Connection conn, int game_id) {
		String sql = "DELETE FROM game_picture WHERE game_id = ?";

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, game_id);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException("뭔가 잘 못 됨");
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
}
