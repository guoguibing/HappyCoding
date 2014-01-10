package happy.coding.math;


/**
 * Implementation of the Poission distribution
 * @author guoguibing
 *
 */
public class Poission
{
	public static double pmf(int k, double lambda)
	{
		return Math.pow(lambda, k) * Math.exp(-lambda) / Maths.factorial(k);
	}
}
