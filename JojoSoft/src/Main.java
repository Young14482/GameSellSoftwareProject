import java.util.List;

import game.Game;
import game.GameDAO;

public class Main {
	public static void main(String[] args) {
		GameDAO gdao = new GameDAO();
		System.out.println(gdao.getGenreList());

		List<Game> gameListByGenre = gdao.getGameListByGenre("격투");

		for (Game game : gameListByGenre) {
			System.out.println(game);
		}
	}
}
