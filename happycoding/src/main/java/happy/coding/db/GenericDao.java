package happy.coding.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Data Access Object
 * 
 * @author guoguibing
 * 
 */
public class GenericDao
{
	protected static String		DRIVER	= null;
	protected static String		URL		= null;
	protected static Connection	conn	= null;
	protected static Statement	stmt	= null;

	// the specific database
	protected static String		database;

	// logger
	protected static Logger		logger	= LoggerFactory.getLogger(GenericDao.class);

	public GenericDao()
	{
		getConn();
	}

	/**
	 * Create database tables
	 * 
	 * @param sqls
	 *            the SQLs to be executed to create tables
	 * @throws Exception
	 */
	protected void createTables(String... sqls) throws Exception
	{
		for (String sql : sqls)
			createTable(sql);
	}

	protected void createTables() throws Exception
	{}

	protected void createTable(String sql) throws Exception
	{
		logger.debug(sql);
		stmt.execute(sql);
	}

	/**
	 * Drop database tables
	 * 
	 * @param tables
	 *            the array of table ids that needs to be dropped
	 * @throws Exception
	 */
	protected void dropTables(String... tables) throws Exception
	{
		for (String tableId : tables)
			dropTable(tableId);
	}

	protected void dropTable(String tableId) throws Exception
	{
		String sql = "DROP TABLE " + tableId;
		logger.debug(sql);
		stmt.execute(sql);
	}

	/**
	 * Clear database tables data
	 * 
	 * @param tables
	 *            the array of table ids whose data needs to be clear
	 * @throws Exception
	 */
	protected void clearTables(String... tables) throws Exception
	{
		for (String tableId : tables)
			clearTable(tableId);
	}

	protected void clearTable(String tableId) throws Exception
	{
		String sql = "DELETE FROM " + tableId;
		logger.debug("Clear table: {}", sql);
		stmt.execute(sql);
	}

	protected void closeConn() throws SQLException
	{
		if (stmt != null) stmt.close();
		if (conn != null) conn.close();
	}

	protected void getConn()
	{
		try
		{
			Class.forName(DRIVER).newInstance();
			String sql = URL + database + ";create=true";
			conn = DriverManager.getConnection(sql);
			stmt = conn.createStatement();

			logger.debug("Get connection: {}", sql);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
