package happy.coding.io;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Apply delegate mode
 * 
 * @author guoguibing
 * 
 */
public class Logs {
	private final static Logger logger = LoggerFactory.getLogger(Logs.class);

	public static Logger getLogger() {
		return logger;
	}

	public static Logger config(String config, boolean isXml) {
		if (isXml)
			DOMConfigurator.configure(config);
		else
			PropertyConfigurator.configure(config);

		return logger;
	}

	public static void debug(double data) {
		logger.debug(Strings.toString(data));
	}

	public static void debug(Object msg) {
		logger.debug(msg.toString());
	}

	public static void debug(String msg) {
		logger.debug(msg);
	}

	public static void debug(String format, Object arg) {
		logger.debug(format, arg);
	}

	public static void debug(String format, Object... args) {
		logger.debug(format, args);
	}

	public static void info(double data) {
		logger.info(Strings.toString(data));
	}

	public static void info(Object msg) {
		if (msg == null)
			logger.info("");
		else
			logger.info(msg.toString());
	}

	public static void info(String format, Object arg) {
		logger.info(format, arg);
	}

	public static void info(String format, Object... args) {
		logger.info(format, args);
	}

	public static void error(double data) {
		logger.error(Strings.toString(data));
	}

	public static void error(Object msg) {
		logger.error(msg.toString());
	}

	public static void warn(String msg) {
		logger.warn(msg);
	}

	public static void warn(String format, Object arg) {
		logger.warn(format, arg);
	}

	public static void warn(String format, Object... args) {
		logger.warn(format, args);
	}

	public static void warn(double data) {
		logger.warn(Strings.toString(data));
	}

	public static void warn(Object msg) {
		logger.warn(msg.toString());
	}

	public static void error(String msg) {
		logger.error(msg);
	}

	public static void error(String format, Object arg) {
		logger.error(format, arg);
	}

	public static void error(String format, Object... args) {
		logger.error(format, args);
	}

	public static void off() {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
	}

	public static void on() {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.DEBUG);
	}

	@Test
	public void example() {
		String[] msgs = new String[] { "Section content: ", "@author guoguibing" };

		debug(msgs);
	}

}
