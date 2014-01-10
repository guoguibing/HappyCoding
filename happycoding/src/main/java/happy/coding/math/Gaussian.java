package happy.coding.math;

import happy.coding.io.Logs;

import org.junit.Test;

/**
 * <h3>Gaussian Distribution</h3>
 * <p>
 * The approximation is accurate to absolute error less than $8 * 10^(-16)$. <br/>
 * Reference paper: George Marsaglia, Evaluating the Normal Distribution.<br/>
 * Reference page: http://introcs.cs.princeton.edu/java/21function/
 * </p>
 * 
 * @author Guo Guibing
 * 
 */
public class Gaussian
{

	/**
	 * Standard Gaussian distribution
	 * 
	 * @return phi(x) = standard Gaussian pdf
	 */
	public static double pdf(double x)
	{
		return Math.exp(-0.5 * x * x) / Math.sqrt(2 * Math.PI);
	}

	/**
	 * Gaussian distribution
	 * 
	 * @param mu
	 *            mean value
	 * @param sigma
	 *            standard deviation
	 * @return phi(x) = Gaussian pdf
	 */
	public static double pdf(double x, double mu, double sigma)
	{
		return pdf((x - mu) / sigma) / sigma;
	}

	/**
	 * Standard Gaussian cdf using Taylor approximation
	 * 
	 * @return $\Phi(z) = \int_{-\infinity}^z pdf(x) dx$
	 */
	public static double cdf(double z)
	{
		if (z < -8.0) return 0.0;
		if (z > 8.0) return 1.0;

		double sum = 0.0, term = z;
		for (int i = 3; sum + term != sum; i += 2)
		{
			sum += term;
			term = term * z * z / i;
		}

		return 0.5 + pdf(z) * sum;
	}

	/**
	 * Gaussian cdf using Taylor approximation
	 */
	public static double cdf(double z, double mu, double sigma)
	{
		return cdf((z - mu) / sigma);
	}

	@Test
	public void examples()
	{
		double xs[] = { 0.1, 1.2, 2.3, 3.4, 4.5, 5.6, 6.7, 7.8, -1.1, -3.3, -5.5, -7.7, 1.96 };

		for (int i = 0; i < xs.length; i++)
		{
			double x = xs[i];
			double cdf = cdf(x);

			Logs.debug("x = " + x + ", cdf = " + cdf);
		}
	}
}
