package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import materials.IResultMapper;

public class UserMapper implements IResultMapper<User> {

	@Override
	public User resultMapping(ResultSet rs) {
		try {
			String userid = rs.getString("user_id");
			String userpw = rs.getString("user_pw");
			String userNickName = rs.getString("user_nickname");
			String userGrade = rs.getString("user_grade");
			String userBirth = rs.getString("user_birth");
			String userPhoneNumber = rs.getString("user_phonenumber");
			int userUsedCash = rs.getInt("user_usedcash");
			int userchargeMoney = rs.getInt("user_chargeMoney");

			return new User(userid, userpw, userNickName, userGrade, userBirth, userPhoneNumber, userUsedCash, userchargeMoney);
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException("User 매핑 중 예외 발생", e);
		}
	}

}
