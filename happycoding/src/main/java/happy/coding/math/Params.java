package happy.coding.math;

import java.util.HashMap;

/**
 * A class used to map each parameter to values when passing to a supported
 * method
 * 
 * @author guoguibing
 * 
 */
public class Params {

	private HashMap<String, Object> map;

	public Params() {
		map = new HashMap<>();
	}

	public Params setKey(String key, Object val) {
		map.put(key, val);

		return this;
	}

	public String getKey(String key) {
		return map.get(key).toString();
	}

	/**
	 * retriev the value of {@code key}
	 * 
	 * @return the value of a key {@code key}
	 * 
	 */
	public String getKey(String key, String val) {
		if (map.containsKey(key))
			return map.get(key).toString();
		else
			return val;
	}

	public Params setKey(String key, Integer val) {
		return setKey(key, val);
	}

	public Params setKey(String key, Double val) {
		return setKey(key, val);
	}

}
