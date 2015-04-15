package happy.coding.io;

import happy.coding.math.Maths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A configer for a line of string
 * 
 * @author Guo Guibing
 *
 */
public class LineConfiger extends StringMap {

	private Map<String, List<String>> params = null;
	private static final String headKey = "main.paramater";

	public LineConfiger(String setup) {
		this(setup.split("[,\t ]"));
	}

	public LineConfiger(String[] parameters) {
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
			boolean isString = !Maths.isNumeric(parameters[i]);
			boolean isWithDash = parameters[i].startsWith("-") || parameters[i].startsWith("--");
			// remove cases like -1, -2 values
			if (isWithDash && isString) {
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

	public String getMainParam() {
		return getString(headKey);
	}

	public String getString(String key) {
		List<String> options = this.getOptions(key);

		if (options != null)
			return options.get(0);

		return null;
	}

	@Override
	public boolean contains(String key) {
		return params.containsKey(key);
	}

}
