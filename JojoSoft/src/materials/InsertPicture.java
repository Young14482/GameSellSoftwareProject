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
		for(int i = 1; i <= 40; i++) {
			Path path1 = Paths.get("C:\\Users\\GGG\\Desktop\\춘식\\팀프\\게임\\플레이사진", i+".jpg"); // 경로와 이미지파일 이름 작성
			try {
				byte[] bytes1 = Files.readAllBytes(path1);
				int row1 = pictureDAO.insert("인게임: " + path1.getFileName().toString(), bytes1); // 데이터 베이스에 저장할 이름을 작성
				System.out.println( i + "번 사진 입력 완료" + row1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
