package happy.coding.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class used to map each parameter to values when passing to a supported
 * method
 * 
 * @author guoguibing
 * 
 */
public class Params {

	// multimap unsuitable since we need to preserve orders of multi values
	private Map<String, List<Object>> map;

	public Params() {
		map = new HashMap<String, List<Object>>();
	}

	/**
	 * add a parameter-value pair 
	 */
	public Params addParam(String key, Object val) {

		if (!map.containsKey(key))
			map.put(key, new ArrayList<>());

		map.get(key).add(val);

		return this;
	}

	/**
	 * @return the first value of a parameter
	 */
	public String getParam(String key) {
		if (!map.containsKey(key))
			return null;

		List<Object> vals = map.get(key);
		return vals.size() > 0 ? vals.get(0).toString() : null;
	}

	/**
	 * @return the value of a parameter {@code key}
	 * 
	 */
	public String getParam(String key, String val) {
		String v = getParam(key);

		return v == null ? val : v;
	}

	/**
	 * @return a list of parameter values
	 */
	public List<Object> getParams(String key) {
		return map.containsKey(key) ? map.get(key) : null;
	}

}
