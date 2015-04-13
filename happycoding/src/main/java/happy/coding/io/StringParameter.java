package happy.coding.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringParameter {

	private Map<String, List<String>> params = null;
	private static final String headKey = "head";

	public StringParameter(String[] parameters) {
		params = new HashMap<>();

		// parameter head
		int i = 0;
		String head = parameters[i];
		if (!(head.startsWith("-") || head.startsWith("--"))) {
			params.put(headKey, Arrays.asList(head));
			i++;
		}

		// parameter options
		List<String> vals = null;
		for (; i < parameters.length; i++) {
			if (parameters[i].startsWith("-") || parameters[i].startsWith("--")) {
				vals = new ArrayList<>();
				params.put(parameters[i], vals);
			} else {
				vals.add(parameters[i]);
			}
		}
	}

	public List<String> getOptions(String key) {
		return params.containsKey(key) ? params.get(key) : null;
	}

	public String getHead() {
		return getString(headKey);
	}

	public String getString(String key) {
		List<String> options = this.getOptions(key);

		if (options != null)
			return options.get(0);

		return null;
	}

	public float getFloat(String key) {
		return Float.parseFloat(getString(key));
	}

	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	public boolean contains(String key) {
		return params.containsKey(key);
	}

}
