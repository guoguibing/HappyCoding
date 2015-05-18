package happy.coding.system;

import java.text.SimpleDateFormat;

public class Dates {
	public final static String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	public final static String PATTERN_dd_MM_yyyy = "dd/MM/yyyy";
	public final static String PATTERN_MM_dd_yyyy = "MM/dd/yyyy";
	public final static String PATTERN_yyyy_MM_dd_HH_mm_SS = "yyyy-MM-dd HH-mm-SS";

	private static final SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_yyyy_MM_dd_HH_mm_SS);

	public static java.sql.Date parse(String date) throws Exception {
		return parse(date, PATTERN_yyyy_MM_dd);
	}

	public static java.sql.Date parse(String date, String pattern) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return new java.sql.Date(sdf.parse(date).getTime());
	}

	public static String toString(long mms, String pattern) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new java.sql.Date(mms));
	}

	public static String toString(long mms) throws Exception {
		return sdf.format(new java.sql.Date(mms));
	}

	public static String now() {
		return sdf.format(new java.util.Date());
	}

	/**
	 * Convert time in milliseconds to human-readable format.
	 * 
	 * @param msType
	 *            The time in milliseconds
	 * @return a human-readable string version of the time
	 */
	public static String parse(long msType) {
		long original = msType;
		int ms = (int) (msType % 1000);

		original = original / 1000;
		int sec = (int) (original % 60);

		original = original / 60;
		int min = (int) (original % 60);

		original = original / 60;
		int hr = (int) (original % 24);

		original = original / 24;
		int day = (int) original;

		if (day > 1) {
			return String.format("%d days, %02d:%02d:%02d.%03d", day, hr, min, sec, ms);
		} else if (day > 0) {
			return String.format("%d day, %02d:%02d:%02d.%03d", day, hr, min, sec, ms);
		} else if (hr > 0) {
			return String.format("%02d:%02d:%02d", hr, min, sec);
		} else {
			return String.format("%02d:%02d", min, sec);
		}
	}

}
