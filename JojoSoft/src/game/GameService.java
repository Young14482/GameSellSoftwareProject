package game;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import materials.DBUtil;
import picture.GamePictureDAO;
import picture.Picture;
import picture.PictureDAO;

public class GameService {
	private GameDAO gameDAO;
	private PictureDAO pictureDAO;
	private GamePictureDAO gamePictureDAO;

	public GameService() {
		gameDAO = new GameDAO();
		pictureDAO = new PictureDAO();
		gamePictureDAO = new GamePictureDAO();
	}

	public int UpdateGame(Game g, Picture picThumnail, Picture picInGame) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			gameDAO.updateGame(conn, g);
			if (picThumnail != null) {
				pictureDAO.updatePicture(conn, g.getGame_profile(), picThumnail);
			}
			if (picInGame != null) {
				int id = gamePictureDAO.getPicture(g.getGame_Id());
				pictureDAO.updatePicture(conn, id, picInGame);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException("SQL예외 게임 수정 실패!");
			}
		} finally {
			DBUtil.closeConnection(conn);
		}

		return 0;
	}

	public int insertGame(Game g, Picture picThumnail, Picture picInGame) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			int thumnailId = pictureDAO.insertPicture(conn, picThumnail);
			int inGameId = pictureDAO.insertPicture(conn, picInGame);

			g.setGame_profile(thumnailId);
			int game_id = gameDAO.insertGame(conn, g);
			gamePictureDAO.insertGamePicture(conn, game_id, inGameId);

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("SQL예외 게임 추가 실패!");
		} finally {
			DBUtil.closeConnection(conn);
		}

		return 0;
	}

	public int deleteGame(Game game) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			int game_Id = game.getGame_Id();
			int inGameId = gamePictureDAO.getPicture(game_Id);
			gamePictureDAO.deleteGamePicture(conn, game_Id);

			gameDAO.delteGame(conn, game_Id);
			pictureDAO.deletePicture(conn, game.getGame_profile());
			pictureDAO.deletePicture(conn, inGameId);

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException("SQL예외 게임 추가 실패!");
			}
			throw new RuntimeException("SQL예외 게임 추가 실패!");
		} finally {
			DBUtil.closeConnection(conn);
		}

		return 0;
	}
}
