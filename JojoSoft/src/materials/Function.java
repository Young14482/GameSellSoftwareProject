package materials;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	// 회원가입 시 아이디의 제약사항을 검토하는 메소드
	public static boolean constraintsId(String strId) {
		String exp = "^\\w{6,20}$";
		// 정규표현식으로 문자열의 동등함을 비교
		Pattern p = Pattern.compile(exp);
		Matcher m = p.matcher(strId);
		if (m.matches()) {
			return true;
		}
		
		return false;
	}
	
	// 랜덤한 문자 10개를 만들어주는 메소드
	public static String randomStringGenerator() {
	        int length = 10;
	        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        Random random = new Random();

	        // StringBuilder를 사용하여 랜덤 문자열 생성
	        StringBuilder sb = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int index = random.nextInt(characters.length());
	            sb.append(characters.charAt(index));
	        }

	        String randomString = sb.toString();
	        return randomString;
	}
	
	

	public static int priceCalculate(int price, int discountNumber) {
		int sellPrice = price * (100 - discountNumber) / 100;
		int result = Math.round(sellPrice / 100) * 100;
		return result;
	}

	// 어떤 광고를 띄울지 랜덤으로 번호를 정해서 광고 id를 반환하는 메소드
	public static int randomPictureNum(List<Integer> pictureNumList) {
		Random random = new Random();
		int result = random.nextInt(pictureNumList.size());
		return pictureNumList.get(result);
	}
}
