package materials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class PictureDAO {
	
	public byte[] getData(int key) {
		String sql = "SELECT data FROM picture WHERE id = ?";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				String data = rs.getString("data");
				Decoder decoder = Base64.getDecoder();
				byte[] decode = decoder.decode(data);
				return decode;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}
	
	public int insert(String name, byte[] data) {
		String sql = "INSERT INTO picture (name, data) VALUES (?, ?)";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Encoder encoder = Base64.getEncoder();
			String dataStr = encoder.encodeToString(data);
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, dataStr);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, stmt, conn);
		}
		return 0;
	}
}
