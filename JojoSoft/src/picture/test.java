package picture;


public class test {
	public static void main(String[] args) {
		PictureDAO dao = new PictureDAO();
		byte[] byts = dao.selectImageAndReturnBytes();
		System.out.println(byts);
		dao.insert("광고:테스트용 광고이미지", byts);
		
	}
}
