package happy.coding.math;

import happy.coding.io.Logs;

import org.junit.Test;

public class Maths {

	/**
	 * Golden ratio: http://en.wikipedia.org/wiki/Golden_ratio
	 * 
	 * <p>
	 * (a+b)/a = a/b = phi (golden ratio) = 1.618033988749895
	 * </p>
	 */
	public final static double golden_ratio = 0.5 * (Math.sqrt(5) + 1);

	public final static double zero = 1e-6;

	public static boolean isEqual(double d1, double d2) {
		return Math.abs(d1 - d2) < zero;
	}

	/**
	 * Check if given string is a number (digits only)
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNumber(String string) {
		return string.matches("^\\d+$");
	}

	/**
	 * Check if given string is numeric (-+0..9(.)0...9)
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNumeric(String string) {
		return string.matches("^[-+]?\\d+(\\.\\d+)?$");
	}

	/**
	 * Check if given string is number with dot separator and two decimals.
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNumberWith2Decimals(String string) {
		return string.matches("^\\d+\\.\\d{2}$");
	}

	public static boolean isInt(double data) {
		return (Maths.isEqual(data, Math.floor(data))) && !Double.isInfinite(data);
	}

	/**
	 * @return n!
	 */
	public static int factorial(int n) {
		if (n < 0)
			return 0;
		if (n == 0 || n == 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * @return ln(e)=log_e(n)
	 */
	public static double ln(double n) {
		return Math.log(n);
	}

	public static double log(double n, int base) {
		return Math.log(n) / Math.log(base);
	}

	/**
	 * logistic function g(x)
	 */
	public static double logistic(double x) {
		return g(x);
	}

	/**
	 * logistic function g(x)
	 */
	public static double g(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

	/**
	 * gradient value of logistic function g(x)
	 */
	public static double gd(double x) {
		return g(x) * g(-x);
	}

	/**
	 * get the normalized value using min-max normalizaiton
	 * 
	 * @param x
	 *            value to be normalized
	 * @param min
	 *            min value
	 * @param max
	 *            max value
	 * @return normalized value
	 */
	public static double normalize(double x, double min, double max) {
		if (max > min)
			return (x - min) / (max - min);
		else if (isEqual(min, max))
			return x / max;
		return x;
	}

	/**
	 * Fabonacci sequence
	 * 
	 */
	public static int fabonacci(int n) {
		assert n > 0;

		if (n == 1)
			return 0;
		else if (n == 2)
			return 1;
		else
			return fabonacci(n - 1) + fabonacci(n - 2);
	}

	/**
	 * greatest common divisor (gcd) or greatest common factor (gcf)
	 * 
	 * <p>
	 * reference: http://en.wikipedia.org/wiki/Greatest_common_divisor
	 * </p>
	 * 
	 */
	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

	/**
	 * least common multiple (lcm)
	 * 
	 */
	public static int lcm(int a, int b) {
		if (a > 0 && b > 0)
			return (int) ((0.0 + a * b) / gcd(a, b));
		else
			return 0;
	}

	/** sqrt(a^2 + b^2) without under/overflow. **/

	public static double hypot(double a, double b) {
		double r;
		if (Math.abs(a) > Math.abs(b)) {
			r = b / a;
			r = Math.abs(a) * Math.sqrt(1 + r * r);
		} else if (!isEqual(b, 0.0)) {
			r = a / b;
			r = Math.abs(b) * Math.sqrt(1 + r * r);
		} else {
			r = 0.0;
		}
		return r;
	}

	@Test
	public void e_numbers() {
		Logs.debug("Golden ratio = " + Maths.golden_ratio);
		Logs.debug("gcd(54, 24) = " + gcd(24, 54) + ", expected = 6");
		Logs.debug("lcm(4, 6) = " + lcm(4, 6) + ", expected = 12");
		Logs.debug("lcm(21, 6) = " + lcm(21, 6) + ", expected = 42");

		String line = "Fabonacci sequence: ";
		for (int i = 1; i < 16; i++)
			line += fabonacci(i) + " ";

		Logs.debug(line);
		Logs.debug(factorial(35));
	}

	@Test
	public void e_log() {
		Logs.debug("ln(2) = " + ln(2));
		Logs.debug("ln(e) = " + ln(Math.E));
		Logs.debug("log(2) = " + Math.log(2));
		Logs.debug("log(e) = " + Math.log(Math.E));
		Logs.debug("log10(100) = " + Math.log10(100));
	}
}
