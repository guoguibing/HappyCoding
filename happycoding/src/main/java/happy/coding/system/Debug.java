package happy.coding.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Define constants or methods often used during the debug process
 * 
 * @author guoguibing
 *
 */
public class Debug
{
	public final static boolean	ON		= true;
	public final static boolean	OFF		= false;

	public final static String	dirPath	= Systems.getDesktop();

	/**
	 * Calculate the amount of memory used by calling obj's method
	 * 
	 * @param obj
	 *            the instance of caller
	 * @param method
	 *            the method to be called
	 * @param args
	 *            the arguments to be passed for this method
	 * 
	 * @return How much memory is used by calling obj's method (in KByte)
	 * @throws Exception
	 * 
	 */
	public static double memory(Object obj, Method method, Object... args) throws Exception
	{
		double mem = 0.0;
		Runtime runtime = Runtime.getRuntime();
		double start, end;

		start = runtime.freeMemory();
		method.invoke(obj, args);
		end = runtime.freeMemory();

		mem = end - start;
		mem /= 1000.0;

		return mem;
	}

	public static void pipeErrors()
	{
		pipeErrors(dirPath + "errors.txt");
	}

	/**
	 * Redirect system errors into a file
	 * 
	 */
	public static void pipeErrors(String filePath)
	{
		try
		{
			System.setErr(new PrintStream(new File(filePath)));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void stopHere()
	{
		try
		{
			System.in.read();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void pipeConsoles()
	{
		pipeConsoles(dirPath + "console.txt");
	}

	/**
	 * Redirect system outputs into a file
	 * 
	 */
	public static void pipeConsoles(String filePath)
	{
		try
		{
			System.setOut(new PrintStream(new File(filePath)));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * To show the usage of this class
	 */
	@Test
	public void example()
	{
		Debug.pipeConsoles();
		Debug.pipeErrors();

		System.out.println("Hello world!");
		System.err.println("Hello error!");
	}
}
