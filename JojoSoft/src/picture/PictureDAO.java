package picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import admin.InsertImageToDB;
import materials.DBUtil;

public class PictureDAO implements ActionListener {

	String imageName;
	private InsertImageToDB insertImageToDB;
	private PictureDAO pictureDAO;
	private byte[] byts;

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

	public byte[] selectImageAndReturnBytes() {
		byte[] imageBytes = null;

		// 파일 선택을 위한 JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		// 이미지 파일만 선택할 수 있도록 필터 설정
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("이미지 파일", "jpg", "png", "gif", "bmp"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				// 파일을 byte 배열로 읽기
				imageBytes = Files.readAllBytes(selectedFile.toPath());

			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "이미지를 불러오는 데 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return imageBytes;
	}

	public void insertSelectImageToDB() {
		pictureDAO = new PictureDAO();
		byts = pictureDAO.selectImageAndReturnBytes();
		insertImageToDB = new InsertImageToDB(this);
		insertImageToDB.setVisible(true);
	}

	public int updatePicture(Connection conn, int id, Picture picture) {
		String sql = "UPDATE picture SET name = ?, data = ? WHERE id = ?";

		PreparedStatement stmt = null;

		try {
			Encoder encoder = Base64.getEncoder();
			String dataStr = encoder.encodeToString(picture.getData());
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, picture.getName());
			stmt.setString(2, dataStr);
			stmt.setInt(3, id);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
		return 0;
	}

	public int insertPicture(Connection conn, Picture picture) {
		String sql = "INSERT INTO picture (name, data) VALUES (?, ?)";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Encoder encoder = Base64.getEncoder();
			String dataStr = encoder.encodeToString(picture.getData());
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, picture.getName());
			stmt.setString(2, dataStr);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, null);
		}
		return -1;
	}

	public int deletePicture(Connection conn, int id) {
		String sql = "DELETE FROM picture WHERE id = ?";

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException("뭔가 잘 못 됨");
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("저장하기")) {
			imageName = insertImageToDB.getResultField().getText();
			insertImageToDB.setVisible(false);
			pictureDAO.insert(imageName, byts);
		}
	}
}
