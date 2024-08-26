package game;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import materials.IResultMapper;

public class GameMapper implements IResultMapper<Game> {
	@Override
	public Game resultMapping(ResultSet rs) {
		try {
			int game_Id = rs.getInt("game_Id");
			String game_name = rs.getString("game_name");
			int game_price = rs.getInt("game_price");
			int game_discount = rs.getInt("game_discount");
			int age_limit = rs.getInt("age_limit");
			String game_genre = rs.getString("game_genre");
			String game_production = rs.getString("game_production");
			String game_info = rs.getString("game_info");
			Date game_release = rs.getDate("game_release");
			int game_profile = rs.getInt("game_profile");
			String game_category = rs.getString("game_category");

			game_info = game_info.replace("\\n", "\n");

			return new Game(game_Id, game_name, game_price, game_discount, age_limit, game_genre, game_production, game_info,
					game_release, game_profile, game_category);
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException("Game 매핑 중 예외 발생", e);
		}
	}
}
