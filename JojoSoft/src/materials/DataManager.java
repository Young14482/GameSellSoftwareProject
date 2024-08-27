package materials;

import java.util.HashMap;
import java.util.Map;

public class DataManager {
	private static final Map<String, Object> dataMap = new HashMap<>();

	public static void inputData(String key, Object value) {
		dataMap.put(key, value);
	}

	public static Object getData(String key) {
		return dataMap.get(key);
	}
}
