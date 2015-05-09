package happy.coding.system;

/**
 * Memory used in Java programs, measured in bytes
 * 
 * <ul>
 * <li>Arrays in Java are implemented as objects, typically with two instance variables (a pointer to the memory
 * location (8 bytes) of the first array element and the length (8 bytes)). For primitive types, an array of N elements
 * uses 16 bytes of header information, plus N times the number of bytes needed to store an element.</li>
 * <li>Typically the JVM allocates memory in 8 byte blocks so a string of size 1, 2, 3, or 4 would consume the same
 * amount of memory (48 bytes). Float and Double each use 16 bytes, as would a user-defined data type just containing a
 * single double instance variable.</li>
 * </ul>
 * 
 * @author Felix
 * 
 */
public class Memory
{
	public static final int	m_boolean	= 1;
	public static final int	m_byte		= 1;
	public static final int	m_char		= 2;
	public static final int	m_int		= 4;
	public static final int	m_float		= 4;
	public static final int	m_long		= 8;
	public static final int	m_double	= 8;

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(byte[] data)
	{
		return 16 + data.length * m_byte;
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(boolean[] data)
	{
		return 16 + data.length * m_boolean;
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(char[] data)
	{
		return 16 + data.length * m_char;
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 *         <p>
	 *         A String uses a total of 40 + 2N bytes: object overhead (8 bytes), a reference to a character array (4
	 *         bytes), three int values (4 bytes each), plus a character array of size N (16 + 2N bytes)
	 *         </p>
	 */
	public static int bytes(String data)
	{
		return 24 + bytes(data.toCharArray());
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(int[] data)
	{
		return 16 + data.length * m_int;
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(double[] data)
	{
		return 16 + data.length * m_double;
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(int[][] data)
	{
		int m = 0;

		for (int i = 0; i < data.length; i++)
			m += bytes(data[i]);

		return m;
	}
	
	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(int[][][] data){
		int m=0;
		
		for(int i=0; i<data.length; i++)
			m+= bytes(data[i]);
		
		return m;
	}

	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(double[][] data)
	{
		int m = 0;

		for (int i = 0; i < data.length; i++)
			m += bytes(data[i]);

		return m;
	}
	
	/**
	 * @return the number of bytes that {@code data} consumes
	 */
	public static int bytes(double[][][] data)
	{
		int m = 0;
		
		for (int i = 0; i < data.length; i++)
			m += bytes(data[i]);
		
		return m;
	}
}
