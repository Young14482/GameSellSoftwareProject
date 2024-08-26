package materials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Function {

	// 문자열을 넣으면 메소드 내용을 토대로 암호화 된 문자열을 반환
	public static String changePW(String password) {
		String result = "";

		for (int i = 0; i < password.length(); i++) {
			char split = password.charAt(i);
			int splitNum = Integer.valueOf(split);

			if (splitNum == 0 || splitNum == 127) {
			} else {
				splitNum++;
			}
			String change = String.valueOf((char) splitNum);

			result += change;
		}
		return result;
	}

	// 암호화 된 비밀번호를 원복시켜 반환
	public static String changePWBasic(String password) {
		String result = "";

		for (int i = 0; i < password.length(); i++) {
			char split = password.charAt(i);
			int splitNum = Integer.valueOf(split);

			if (splitNum == 0 || splitNum == 127) {
			} else {
				splitNum--;
			}
			String change = String.valueOf((char) splitNum);

			result += change;
		}
		return result;
	}

	public static boolean containsKorean(String str) {

		// 문자열을 순회하며 한글이 포함되어 있는지 확인
		for (char c : str.toCharArray()) {
			if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
				return true; // 한글이 포함되어 있으면 true 반환
			}
		}

		return false; // 한글이 포함되어 있지 않으면 false 반환
	}

	// 어떤 광고를 띄울지 랜덤으로 번호를 정해서 광고 id를 반환하는 메소드
	public static int randomPictureNum(List<Integer> pictureNumList) {
		Random random = new Random();
		int result = random.nextInt(pictureNumList.size());
		return pictureNumList.get(result);
	}

}
