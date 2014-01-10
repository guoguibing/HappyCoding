package happy.coding.math;

import happy.coding.io.Logs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class Randoms
{
	private static Random		_random		= new Random(System.currentTimeMillis());

	private static List<Object>	_tempList	= new ArrayList<>();

	/**
	 * Random generate an integer in [0, range)
	 * 
	 * @param range
	 * @return
	 */
	public static int uniform(int range)
	{
		return uniform(0, range);
	}

	public static void seed(long seed)
	{
		_random = new Random(seed);
	}

	/**
	 * Random generate an integer in [min, max)
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int uniform(int min, int max)
	{
		return min + _random.nextInt(max - min);
	}

	/**
	 * return real number uniformly in [0, 1)
	 */
	public static double random()
	{
		return uniform();
	}

	/**
	 * a random double array with values in [0, 1)
	 * 
	 * @param size
	 *            the size of random array
	 */
	public static double[] doubles(int size)
	{
		double[] array = new double[size];
		for (int i = 0; i < size; i++)
			array[i] = random();

		return array;
	}

	/**
	 * a random double array with values in [min, max)
	 * 
	 * @param size
	 *            the size of random array
	 */
	public static double[] doubles(double min, double max, int size)
	{
		double[] array = new double[size];
		for (int i = 0; i < size; i++)
			array[i] = uniform(min, max);

		return array;
	}

	/**
	 * random (uniformly distributed) double in [0, 1)
	 */
	public static double uniform()
	{
		return uniform(0.0, 1.0);
	}

	/**
	 * random (uniformly distributed) double in [min, max)
	 */
	public static double uniform(double min, double max)
	{
		return min + (max - min) * _random.nextDouble();
	}

	/**
	 * Return a boolean, which is true with probability p, and false otherwise.
	 */
	public static boolean bernoulli(double p)
	{
		return uniform() < p;
	}

	/**
	 * Return a boolean, which is true with probability .5, and false otherwise.
	 */
	public static boolean bernoulli()
	{
		return bernoulli(0.5);
	}

	/**
	 * random standard Gaussain distributed double
	 * <p>
	 * Reference: http://www.design.caltech.edu/erik/Misc/Gaussian.html
	 * </p>
	 * <p>
	 * Anotehr useful reference: http://www.protonfish.com/random.shtml: G = X + X + X, where X is a random double
	 * </p>
	 * 
	 */
	public static double gaussian()
	{
		double x, y, r;

		do
		{
			x = uniform(-1.0, 1.0);
			y = uniform(-1.0, 1.0);
			r = x * x + y * y;
		} while (r >= 1.0 || r == 0.0);

		r = Math.sqrt((-2.0 * Math.log(r)) / r);

		return x * r; // or y * r;
	}

	/**
	 * return a real number from a Gaussian distribution with given mean and stddev
	 * 
	 * @param mu
	 *            mean
	 * @param sigma
	 *            stddev
	 * 
	 */
	public static double gaussian(double mu, double sigma)
	{
		return mu + sigma * gaussian();
	}

	/**
	 * Return an integer with a Poisson distribution with mean lambda.
	 */
	public static int poisson(double lambda)
	{
		// using algorithm given by Knuth
		// see http://en.wikipedia.org/wiki/Poisson_distribution
		int k = 0;
		double p = 1.0;
		double L = Math.exp(-lambda);
		do
		{
			k++;
			p *= uniform();
		} while (p >= L);
		return k - 1;
	}

	/**
	 * Return a real number with a Pareto distribution with parameter alpha.
	 */
	public static double pareto(double alpha)
	{
		return Math.pow(1 - uniform(), -1.0 / alpha) - 1.0;
	}

	/**
	 * Return a real number with a Cauchy distribution.
	 */
	public static double cauchy()
	{
		return Math.tan(Math.PI * (uniform() - 0.5));
	}

	/**
	 * Return a number from a discrete distribution: i with probability a[i]. Precondition: array entries are
	 * nonnegative and their sum (very nearly) equals 1.0.
	 */
	public static int discrete(double[] a)
	{
		double EPSILON = 1E-14;
		double sum = 0.0;
		for (int i = 0; i < a.length; i++)
		{
			if (a[i] < 0.0) throw new IllegalArgumentException("array entry " + i + " is negative: " + a[i]);
			sum = sum + a[i];
		}
		if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON) throw new IllegalArgumentException(
				"sum of array entries not equal to one: " + sum);

		// the for loop may not return a value when both r is (nearly) 1.0 and when the
		// cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
		while (true)
		{
			double r = uniform();
			sum = 0.0;
			for (int i = 0; i < a.length; i++)
			{
				sum = sum + a[i];
				if (sum > r) return i;
			}
		}
	}

	/**
	 * Return a real number from an exponential distribution with rate lambda.
	 */
	public static double exp(double lambda)
	{
		return -Math.log(1 - uniform()) / lambda;
	}

	/**
	 * generate next random integer in a range besides exceptions
	 * 
	 * @param range
	 *            the range located of next integer
	 * @param exceptions
	 *            the exception values when generating integers, sorted first
	 * @return next no-repeated random integer
	 */
	public static int nextNoRepeatInt(int range, int... exceptions)
	{
		return nextNoRepeatInt(0, range, exceptions);
	}

	/**
	 * generate next random integer in a range [min, max) besides exceptions
	 * 
	 * @param min
	 *            the minimum of range
	 * @param max
	 *            the maximum of range
	 * @param exceptions
	 *            the exception values when generating integers, sorted first
	 * @return next no-repeated random integer
	 */

	public static int nextNoRepeatInt(int min, int max, int... exceptions)
	{
		int next;
		while (true)
		{
			next = min + _random.nextInt(max - min);
			if (exceptions != null && exceptions.length > 0 && Arrays.binarySearch(exceptions, next) >= 0)
			{
				continue;
			}
			if (_tempList.contains(next)) continue;
			else
			{
				_tempList.add(next);
				break;
			}
		}
		return next;
	}

	/**
	 * Generate no repeat {@code size} indexes from {@code min} to {@code max}
	 * 
	 */
	public static int[] indexs(int size, int min, int max)
	{
		Set<Integer> used = new HashSet<>();
		int[] index = new int[size];

		for (int i = 0; i < index.length; i++)
		{
			while (true)
			{
				int ind = uniform(min, max);

				if (!used.contains(ind))
				{
					index[i] = ind;
					used.add(ind);
					break;
				}
			}
		}

		return index;
	}

	public static void clearCache()
	{
		_tempList.clear();
	}

	/**
	 * generate next integers array with no repeated elements
	 * 
	 * @param length
	 *            the length of the array
	 * @param range
	 *            the index range of the array, default [0, range)
	 * @param exceptions
	 *            the exceptions when generating values
	 * @return ascending sorted integer array
	 * @throws Exception
	 *             if the range is less than length, an exception will be thrown
	 */
	public static int[] nextNoRepeatIntArray(int length, int range) throws Exception
	{
		return nextNoRepeatIntArray(length, 0, range, null);
	}

	public static int[] nextNoRepeatIntArray(int length, int range, int... exceptions) throws Exception
	{
		return nextNoRepeatIntArray(length, 0, range, exceptions);
	}

	public static int[] nextNoRepeatIntArray(int length, int min, int max) throws Exception
	{
		return nextNoRepeatIntArray(length, min, max, null);
	}

	public static int[] nextNoRepeatIntArray(int length, int min, int max, int... exceptions) throws Exception
	{
		int maxLen = max - min; // because max itself is not counted
		if (maxLen < length) throw new Exception("The range is less than legth");

		int[] index = new int[length];
		if (maxLen == length)
		{
			for (int i = 0; i < length; i++)
				index[i] = min + i;
		} else
		{
			Randoms.clearCache();
			for (int i = 0; i < index.length; i++)
				index[i] = Randoms.nextNoRepeatInt(min, max, exceptions);
			Arrays.sort(index);
		}
		return index;
	}

	public static int[] ints(int range, int size)
	{
		int[] data = new int[size];

		for (int i = 0; i < size; i++)
			data[i] = uniform(range);

		return data;
	}

	public static int[] ints(int min, int max, int size)
	{
		int[] data = new int[size];

		for (int i = 0; i < size; i++)
			data[i] = uniform(min, max);

		return data;
	}

	public static List<Double> list(int size)
	{
		return list(size, 0, 1, false);
	}

	public static List<Double> list(int size, int min, int max)
	{
		return list(size, min, max, false);
	}

	public static List<Double> list(int size, int min, int max, boolean isInteger)
	{
		List<Double> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
		{
			if (isInteger) list.add(uniform(min, max) + 0.0);
			else list.add(uniform(min + 0.0, max + 0.0));
		}

		return list;
	}

	/**
	 * Generate a permutation from min to max
	 * 
	 * @param min
	 *            the minimum value
	 * @param max
	 *            the maximum value
	 * @return a permutation
	 */
	public static List<Integer> permute(int min, int max)
	{
		List<Integer> list = new ArrayList<>();

		int len = max - min + 1;
		for (int i = 0; i < len; i++)
		{
			while (true)
			{
				int index = uniform(min, max + 1);

				if (!list.contains(index))
				{
					list.add(index);
					break;
				}
			}
		}

		return list;
	}

	@Test
	public void example_permute()
	{
		for (int i = 0; i < 20; i++)
		{
			List<Integer> perm = Randoms.permute(1, 5);
			Logs.debug("Iteration " + (i + 1) + " : " + perm);
		}
	}

	@Test
	public void example_gaussian()
	{
		for (int i = 0; i < 20; i++)
		{
			Logs.debug(gaussian());
		}
	}
}
