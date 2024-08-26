package game;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import materials.DBUtil;
import materials.IResultMapper;

public class GameDAO {
	public static final IResultMapper<Game> gameMapper = new GameMapper();
	public static final int ORDER_BY_RELEASE_DESC = 0;
	public static final int ORDER_BY_PRICE_ASC = 1;
	public static final int ORDER_BY_PRICE_DESC = 2;

	public Game getGame(int key) {
		String sql = "SELECT * FROM game WHERE game_Id = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return gameMapper.resultMapping(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}

	public int insertGame(String game_name, int game_price, int age_limit, String game_genre, String game_production,
			String game_ifgo, Date game_release, int game_profile, String game_category) {
		String sql = "INSERT INTO game (game_name, game_price, age_limit, game_genre, game_production,\r\n"
				+ "	game_ifgo, game_release, game_profile, game_category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, game_name);
			stmt.setInt(2, game_price);
			stmt.setInt(3, age_limit);
			stmt.setString(4, game_genre);
			stmt.setString(5, game_production);
			stmt.setString(6, game_ifgo);
			stmt.setDate(7, game_release);
			stmt.setInt(8, game_profile);
			stmt.setString(9, game_category);
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, stmt, conn);
		}
		return 0;
	}

	public List<String> getGenreList() {
		String sql = "SELECT DISTINCT game_genre FROM game";
		List<String> list = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String game_genre = rs.getString("game_genre");
				list.add(game_genre);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}

	public List<Game> getGameListByGenre(String gameGenre) {
		String sql = "SELECT * FROM game WHERE game_genre = ? LIMIT 10";
		List<Game> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, gameGenre);
			rs = stmt.executeQuery();

			while (rs.next()) {
				list.add(gameMapper.resultMapping(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}

	public List<Game> getSearchedListDefault() {
		return getSearchedList(null, null, null, null, null, GameDAO.ORDER_BY_RELEASE_DESC);
	}

	public List<Game> getSearchedListNoOrder(String main, String sub, String genre, String production, String category) {
		return getSearchedList(main, sub, genre, production, category, GameDAO.ORDER_BY_RELEASE_DESC);
	}

	public List<Game> getSearchedList(String main, String sub, String genre, String production, String category, int order) {
		String sql = "SELECT * FROM game ";// WHERE game_genre = ? LIMIT 10";
		StringJoiner stringJoiner = new StringJoiner(" AND ", "WHERE ", " ");
		boolean where = false;
		if (main != null) {
			stringJoiner.add("game_name Like '%" + main + "%'");
			where = true;
		}
		if (sub != null) {
			stringJoiner.add("game_name Like '%" + sub + "%'");
			where = true;
		}
		if (genre != null) {
			stringJoiner.add("game_genre = ?");
			where = true;
		}
		if (production != null) {
			stringJoiner.add("game_production = ?");
			where = true;
		}
		if (category != null) {
			stringJoiner.add("game_category = ?");
			where = true;
		}
		if (where) {
			sql += stringJoiner;
		}
		if (order == ORDER_BY_RELEASE_DESC) {
			sql += "ORDER BY game_release DESC";
		} else if (order == ORDER_BY_PRICE_DESC) {
			sql += "ORDER BY ROUND(game_price * (100-game_discount)*100, -2) DESC";
		} else if (order == ORDER_BY_PRICE_ASC) {
			sql += "ORDER BY ROUND(game_price * (100-game_discount)*100, -2)";
		}
		sql += " LIMIT 10";
		List<Game> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection("jojosoft");
			stmt = conn.prepareStatement(sql);
			int param = 1;
			if (genre != null) {
				stmt.setString(param, genre);
				param++;
			}
			if (production != null) {
				stmt.setString(param, production);
				param++;
			}
			if (category != null) {
				stmt.setString(param, category);
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				list.add(gameMapper.resultMapping(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, stmt, conn);
		}
		return null;
	}
}
