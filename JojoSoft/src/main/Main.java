package main;
import game.GameDAO;

public class Main {
	public static void main(String[] args) {
		GameDAO gdao = new GameDAO();
		System.out.println(gdao.getGenreList());
		System.out.println(Math.round(7215 / 100) * 100);
	}
}
