package happy.coding.db;

/**
 * Abstract Data Access Object for Derby database systems
 * 
 * the implemented class needs to assign suitable value for field
 * <i>database</i>
 * 
 * @author guoguibing
 * 
 */
public class DerbyDao extends GenericDao
{
	static
	{
		DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
		URL = "jdbc:derby:";

		// set property for log4j.properties
		System.setProperty("Password", "through@pass");
	}

}
