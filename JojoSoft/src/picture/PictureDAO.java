package picture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.swing.JOptionPane;

import materials.DBUtil;

public class PictureDAO {

	public Picture getPicture(int key) {
		String sql = "SELECT * FROM picture WHERE id = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				String data = rs.getString("data");
				Decoder decoder = Base64.getDecoder();
				byte[] decode = decoder.decode(data);
				return new Picture(name, decode);
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

	public List<Integer> findAdIdAndAddToList() {
		String sql = "SELECT id FROM picture WHERE name LIKE ?;";

		List<Integer> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "광고%");
			System.out.println(1);
			rs = stmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("id"));
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
