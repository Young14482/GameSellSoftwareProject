import game.Game;
import game.GameDAO;

public class Main {
	public static void main(String[] args) {
		GameDAO gdao = new GameDAO();
		System.out.println(gdao.getGenreList());

	}
}
