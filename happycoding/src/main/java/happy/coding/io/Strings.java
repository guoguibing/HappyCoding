package happy.coding.io;

import happy.coding.io.FileIO.Converter;
import happy.coding.io.FileIO.MapWriter;
import happy.coding.math.Maths;
import happy.coding.system.Separator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class Strings {
	public static final String HELLO = "Hello World!";
	public static final String EMPTY = "";

	private static Separator separator = Separator.line;

	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * get the last substring of string str with maximum length
	 * 
	 * @param str
	 *            source string
	 * @param maxLength
	 *            maximum length of strings
	 * @return the last substring of string str with maximum length; if greater;
	 *         then "..." is padded to start position
	 */
	public static String last(String str, int maxLength) {
		if (str.length() + 3 < maxLength)
			return str;
		return "..." + str.substring(str.length() - maxLength + 3);
	}

	/**
	 * Concatenates an array of string
	 * 
	 * @param objs
	 *            the objects to be concatenated
	 * @param sep
	 *            the separator between strings
	 * @return the concatenated strings
	 */
	public static String toString(Object[] objs, String sep) {
		return Joiner.on(sep).skipNulls().join(objs);
	}

	/**
	 * default sep="\n" between all objects
	 * 
	 */
	public static String toString(Object[] strings) {
		return toString(strings, "\n");
	}

	/**
	 * <p>
	 * Returns padding using the specified delimiter repeated to a given length.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.repeat(0, 'e')  = ""
	 * StringUtils.repeat(3, 'e')  = "eee"
	 * StringUtils.repeat(-2, 'e') = ""
	 * </pre>
	 * 
	 * <p>
	 * Note: this method doesn't not support padding with <a
	 * href="http://www.unicode.org/glossary/#supplementary_character">Unicode
	 * Supplementary Characters</a> as they require a pair of {@code char}s to
	 * be represented. If you are needing to support full I18N of your
	 * applications consider using {@link #repeat(String, int)} instead.
	 * </p>
	 * 
	 * @param ch
	 *            character to repeat
	 * @param repeat
	 *            number of times to repeat char, negative treated as zero
	 * @return String with repeated character
	 * @see #repeat(String, int)
	 */
	public static String repeat(char ch, int repeat) {
		char[] buf = new char[repeat];
		for (int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
	 * <p>
	 * Repeat a String {@code repeat} times to form a new String.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.repeat(null, 2) = null
	 * StringUtils.repeat("", 0)   = ""
	 * StringUtils.repeat("", 2)   = ""
	 * StringUtils.repeat("a", 3)  = "aaa"
	 * StringUtils.repeat("ab", 2) = "abab"
	 * StringUtils.repeat("a", -2) = ""
	 * </pre>
	 * 
	 * @param str
	 *            the String to be repeated, may be null
	 * @param repeat
	 *            number of times to repeat {@code str}, negative treated as
	 *            zero
	 * @return a new String consisting of the original String repeated,
	 *         {@code null} if null String input
	 */
	public static String repeat(String str, int repeat) {
		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return repeat(str.charAt(0), repeat);
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			return repeat(str.charAt(0), repeat);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuilder buf = new StringBuilder(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}

	/**
	 * filtering special symbols with a predefined symbol
	 * 
	 */
	public static String filterWebString(String webString, char c) {
		char[] symbols = { ':', '：' };
		String result = webString;

		for (char s : symbols) {
			result = result.replace(s, c);
		}

		return result;
	}

	public static String toString(double data) {
		return toString(data, 4);
	}

	public static String toString(double[][] data) {
		int rows = data.length;
		int cols = data[0].length;
		StringBuilder sb = new StringBuilder();
		sb.append("Dimension: " + rows + " x " + cols + "\n");

		for (int i = 0; i < rows; i++) {
			sb.append("[");
			for (int j = 0; j < cols; j++) {
				sb.append(String.format("%.6f", data[i][j]));
				if (j < cols - 1)
					sb.append("\t");
			}
			sb.append("]\n");
		}

		return sb.toString();
	}

	public static String toString(Number data, int bits) {
		double val = data.doubleValue();
		if (Maths.isInt(val))
			return (int) val + "";

		String format = "%." + bits + "f";
		return String.format(format, val);
	}

	public static List<String> split(String str, String reg) {
		Iterable<String> iter = Splitter.on(reg).omitEmptyStrings()
				.trimResults().split(str);

		return Lists.newArrayList(iter);
	}

	public static String shortStr(String input) {
		return shortStr(input, 50);
	}

	public static String shortStr(String input, int len) {
		int begin = 0;
		if (input.length() > len)
			begin = input.length() - len;

		return input.substring(begin);
	}

	public static <T> String toString(Collection<T> ts) {
		return toString(ts, ",");
	}

	public static <T> String toString(Collection<T> ts, String sep) {
		return Joiner.on(sep).skipNulls().join(ts);
	}

	public static <T> String toString(Collection<T> ts, Converter<T, String> lw)
			throws Exception {
		if (ts == null || ts.size() == 0)
			return null;
		StringBuilder sb = new StringBuilder();

		int N = ts.size(), i = 0;
		for (T t : ts) {
			String line = lw != null ? lw.transform(t) : t.toString();

			sb.append(line);
			if (i++ < N - 1)
				sb.append(separator.symbol());
		}
		return sb.toString();
	}

	public static <K, V> String toString(Map<K, V> map) {
		return toString(map, "\n");
	}

	public static <K, V> String toString(Map<K, V> map, String sep) {
		return Joiner.on(sep).withKeyValueSeparator(" -> ").join(map);
	}

	public static <K, V> String toString(Map<K, V> map, MapWriter<K, V> mw) {
		StringBuilder sb = new StringBuilder();

		int size = map.size();
		int count = 0;
		for (Entry<K, V> en : map.entrySet()) {
			K key = en.getKey();
			V val = en.getValue();
			String line = mw != null ? mw.processEntry(key, val) : key + " -> "
					+ val;

			sb.append(line);
			if (count++ < size - 1)
				sb.append(separator.symbol());
		}

		return sb.toString();
	}

	public static String toString(double[] data) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < data.length; i++) {
			sb.append(toString(data[i]));
			if (i < data.length - 1)
				sb.append(", ");
		}
		sb.append("]");

		return sb.toString();
	}

	public static String toString(int[] data) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			if (i < data.length - 1)
				sb.append(", ");
		}
		sb.append("]");

		return sb.toString();
	}

	/**
	 * convert to a section of message
	 */
	public static String toSection(List<String> msgs) {
		StringBuilder sb = new StringBuilder();

		int repeat = 50;

		sb.append(" *\n");
		for (String msg : msgs) {
			sb.append(" * " + msg + "\n");
			if (msg.length() > repeat)
				repeat = msg.length();
		}
		sb.append(" *\n");

		String stars = Strings.repeat('*', repeat);
		String head = "\n/*" + stars + "\n";
		sb.insert(0, head);

		String tail = " *" + stars + "/";
		sb.append(tail);

		return sb.toString();
	}

	public static void setSeparator(Separator _separator) {
		separator = _separator;
	}

	public static boolean equals(String a, String b) {
		if (a == null || b == null)
			return false;

		return a.equals(b);
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		if (a == null || b == null)
			return false;

		return a.equalsIgnoreCase(b);
	}

	@Test
	public void example() {
		String a = null, b = null;
		testEquals(a, b);

		a = "";
		b = "";
		testEquals(a, b);

		a = "abc";
		b = new String("abc");
		testEquals(a, b);
	}

	private void testEquals(String a, String b) {
		Logs.debug("a = {}, b = {}, a == b? {}", new Object[] { a, b, a == b });
		Logs.debug("a = {}, b = {}, a == b? {}",
				new Object[] { a, b, Strings.equals(a, b) });
	}

}
