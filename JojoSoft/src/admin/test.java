package admin;

import picture.PictureDAO;

public class test {
	public static void main(String[] args) {
		PictureDAO dao = new PictureDAO();
		dao.insertSelectImageToDB();
	}
}
