package user;

public class User {
	private String userId;
	private String userPw;
	private String userNickName;
	private String userGrade;
	private String userBirth;
	private String userPhoneNumber;
	private int userUsedCash;
	private static User curUser;

	public User(String userId, String userPw, String userNickName, String userGrade, String userBirth, String userPhoneNumber,
			int userUsedCash) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userNickName = userNickName;
		this.userGrade = userGrade;
		this.userBirth = userBirth;
		this.userPhoneNumber = userPhoneNumber;
		this.userUsedCash = userUsedCash;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public int getUserUsedCash() {
		return userUsedCash;
	}

	public void setUserUsedCash(int userUsedCash) {
		this.userUsedCash = userUsedCash;
	}

	public String getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public static void setCurUser(User user) {
		curUser = user;
	}

	public static User getCurUser() {
		return curUser;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPw=" + userPw + ", userNickName=" + userNickName + ", userGrade=" + userGrade
				+ ", userUsedCash=" + userUsedCash + "]";
	}
}
