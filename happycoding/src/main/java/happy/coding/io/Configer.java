package happy.coding.io;

import happy.coding.system.Systems;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * A configure class for .conf/.properties file
 * 
 * @author guoguibing
 * 
 */
public class Configer {
	private Properties p = null;

	public Configer(String conf) throws Exception {
		p = new Properties();
		p.load(new FileInputStream(FileIO.getResource(conf)));
	}

	/**
	 * @return the key value as a trimmed string
	 * 
	 */
	public String getString(String key) {
		return p.getProperty(key).trim();
	}

	/**
	 * set a value to a specific key
	 * 
	 * @param key
	 *            property key
	 * @param val
	 *            property value
	 */
	public void setString(String key, String val) {
		p.setProperty(key, val);
	}

	public boolean containsKey(String key) {
		return p.containsKey(key);
	}

	/**
	 * @return the file IO path: supporting windows, linux and unix
	 */
	public String getPath(String key) {
		switch (Systems.getOs()) {
		case Windows:
			return getString(key + ".wins");
		case Linux:
		case Unix:
			return getString(key + ".lins");

		default:
			return null;
		}
	}

	/**
	 * return the key value as a float
	 * 
	 */
	public float getFloat(String key) {
		return Float.parseFloat(getString(key));
	}

	/**
	 * return the key value as a double
	 * 
	 */
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	/**
	 * return the key value as an integer
	 * 
	 */
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	/**
	 * return the key value as boolean
	 * 
	 */
	public boolean isOn(String key) {
		String option = getString(key).toLowerCase();
		switch (option) {
		case "on":
			return true;

		case "off":
		default:
			return false;
		}
	}

	/**
	 * return the key values as a set of float values; It supports two ways: one
	 * is individual value (e.g., "1", "0.5"); the other is range values in the
	 * form of "min..step..max" (e.g, "0.5..0.1..0.8")
	 * 
	 */
	public List<Double> getRange(String key) {

		// value sets
		String delim = "[, \t]";
		String str = getString(key);
		StringTokenizer st = new StringTokenizer(str, delim);
		if (st.countTokens() > 1)
			return getValues(key, delim);

		// single value
		List<Double> vals = getValues(key, "(\\.\\.)");
		if (vals.size() < 3)
			return vals;

		// value ranges
		double min = vals.get(0), step = vals.get(1), max = vals.get(2);
		vals.clear();

		if (min > max) {
			// inverse orer from max --> min
			while (min > max) {
				vals.add(min);
				min -= step;
			}
			vals.add(max);

		} else {

			while (min < max) {
				vals.add(min);
				min += step;
			}
			// no repeated values
			if (Math.abs(max + step - min) > 1e-6)
				vals.add(max);
		}

		return vals;
	}

	/**
	 * return the key values as a set of float values; It supports two ways: one
	 * is individual value (e.g., "1", "0.5"); the other is range values in the
	 * form of "a,b,c" (e.g, "0.5,0.8,0.6") *
	 */
	public List<Double> getSet(String key) {
		return getValues(key, "[, \t]");
	}

	/**
	 * return a set of float values set for a key, separated by the string "reg"
	 * 
	 */
	public List<Double> getValues(String key, String reg) {
		List<Double> values = new ArrayList<>();

		String val = p.getProperty(key);

		if (val != null) {
			String[] data = val.split(reg);

			for (int i = 0; i < data.length; i++) {
				values.add(new Double(data[i]));
			}
		}

		return values;
	}

}
