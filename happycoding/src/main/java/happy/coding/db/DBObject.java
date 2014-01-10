package happy.coding.db;

/**
 * A Database Object dealing with the common operations for data storage and access
 * 
 * @author guoguibing
 * 
 */
public class DBObject
{
	protected final static String	sep	= "\t";
	
	/**
	 * replace string to be usable for database
	 * 
	 * @param old
	 *            old string
	 * @return new string after replace ' to be ''
	 */
	protected String replace(String old)
	{
		if (old == null) return "";
		return old.replace("\'", "''");
	}

	protected String deReplace(String old)
	{
		if (old == null) return "";
		return old.replace("''", "'");
	}

}
