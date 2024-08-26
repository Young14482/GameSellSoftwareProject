package materials;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import picture.PictureDAO;

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

		Path path1 = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프", "uncharted.jpg"); // 경로와 이미지파일 이름 작성
//		Path path2 = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프", "baldur's gate 3.jpg"); // 경로와 이미지파일 이름 작성
//		Path path3 = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프", "wukong.jpg"); // 경로와 이미지파일 이름 작성
		try {
			byte[] bytes1 = Files.readAllBytes(path1);
//			byte[] bytes2 = Files.readAllBytes(path2);
//			byte[] bytes3 = Files.readAllBytes(path3);
			// DLC 기본게임 번들
			int row1 = pictureDAO.insert("기본게임: " + path1.getFileName().toString(), bytes1); // 데이터 베이스에 저장할 이름을 작성
//		int row2 = pictureDAO.insert("기본게임: "+ path2.getFileName().toString(), bytes2); // 데이터 베이스에 저장할 이름을 작성
//		int row3 = pictureDAO.insert("기본게임: "+ path3.getFileName().toString(), bytes3); // 데이터 베이스에 저장할 이름을 작성
			System.out.println("1번행 삽입: " + row1);
//			System.out.println("2번행 삽입: " + row2);
//			System.out.println("3번행 삽입: " + row3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
