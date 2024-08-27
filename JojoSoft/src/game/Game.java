package game;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Game {
	private int game_Id;
	private String game_name; 
	private int game_price;
	private int game_discount;
	private int age_limit;
	private String game_genre; 
	private String game_production; 
	private String game_info;
	private Date game_release;
	private int game_profile;
	private String game_category;
	private static Game curGame;
	
	
	public static Game getCurGame() {
		return curGame;
	}
	public static void setCurGame(Game curGame) {
		Game.curGame = curGame;
	}
}
