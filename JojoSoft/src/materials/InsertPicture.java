package materials;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;


/* 해당 클래스는 이미지 파일 데이터를 문자열로 변환하여
 * 데이터 베이스에 등록할 때만 사용하는 클래스입니다.
 * path에 자신의 컴퓨터에 저장되어 있는 이미지 경로와 이미지 이름을 작성
 * pictureDAO.insert메소드에 저장할 이름을 작성하여 실행시키면
 * 자동으로 데이터 베이스에 등록됩니다.
 */
public class InsertPicture {
	static PictureDAO pictureDAO;
	
	public static void main(String[] args) {
		pictureDAO = new PictureDAO();
		
		Path path = Paths.get("C:\\Users\\GGG\\Desktop\\자유주제 프로젝트", "마피아.jpg"); // 경로와 이미지파일 이름 작성
		try {
			byte[] bytes = Files.readAllBytes(path);
			
			pictureDAO.insert("광고:마피아", bytes); // 데이터 베이스에 저장할 이름을 작성
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
