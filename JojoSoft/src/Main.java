import java.util.List;

import game.Game;
import game.GameDAO;

public class Main {
	public static void main(String[] args) {
		GameDAO gdao = new GameDAO();
		System.out.println(gdao.getGenreList());

		List<Game> gameListByGenre = gdao.getSearchedList("철", "에디", "격투", "반다이남코", "dlc", GameDAO.ORDER_BY_PRICE_ASC);

		for (Game game : gameListByGenre) {
			System.out.println(game);
		}
	}
}
