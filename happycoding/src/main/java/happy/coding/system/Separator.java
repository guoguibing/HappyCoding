package happy.coding.system;

/**
 * The commonly used separators 
 *  
 * @author guoguibing
 *
 */
public class Separator
{
	private String					symbol;

	public final static Separator	line			= new Separator("\n");
	public final static Separator	tab				= new Separator("\t");
	public final static Separator	empty			= new Separator(" ");
	public final static Separator	comma			= new Separator(", ");
	public final static Separator	colon			= new Separator(": ");
	public final static Separator	double_colon	= new Separator("::");

	public Separator(String _sep)
	{
		symbol = _sep;
	}

	public String symbol()
	{
		return symbol;
	}
}
