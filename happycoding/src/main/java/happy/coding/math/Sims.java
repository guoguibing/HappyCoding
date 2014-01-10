package happy.coding.math;

import happy.coding.io.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Similarity measures
 * 
 * @author guoguibing
 * 
 */
public class Sims {

	/**
	 * @return cosine similarity
	 */
	public static double cos(List<Double> a, List<Double> b) {
		if (Lists.isEmpty(a) || Lists.isEmpty(b) || a.size() != b.size())
			return Double.NaN;

		double sum = 0.0, sum_a = 0.0, sum_b = 0.0;
		for (int i = 0; i < a.size(); i++) {
			double ai = a.get(i), bi = b.get(i);
			sum += ai * bi;
			sum_a += ai * ai;
			sum_b += bi * bi;
		}

		double val = Math.sqrt(sum_a) * Math.sqrt(sum_b);

		return sum / val;
	}

	/**
	 * Calculate Constrained Pearson Correlation (CPC)
	 * 
	 * @param u
	 *            user u's ratings
	 * @param v
	 *            user v's ratings
	 * @param median
	 *            median rating in a rating range
	 * 
	 * @return Constrained PCC Correlation (CPC)
	 */
	public static double cpc(List<Double> u, List<Double> v, double median) {
		if (Lists.isEmpty(u) || Lists.isEmpty(v))
			return Double.NaN;

		double sumNum = 0.0, sumDen1 = 0.0, sumDen2 = 0.0;
		for (int i = 0; i < u.size(); i++) {
			double ui = u.get(i) - median;
			double vi = v.get(i) - median;

			sumNum += ui * vi;
			sumDen1 += Math.pow(ui, 2);
			sumDen2 += Math.pow(vi, 2);
		}
		return sumNum / (Math.sqrt(sumDen1) * Math.sqrt(sumDen2));
	}

	/**
	 * Calculate Mean Squared Difference (MSD) similarity proposed by Shardanand
	 * and Maes [1995]:
	 * 
	 * <i>Social information filtering: Algorithms for automating “word of
	 * mouth”</i>
	 * 
	 * @param u
	 *            user u's ratings
	 * @param v
	 *            user v's ratings
	 * @return MSD similarity
	 */
	public static double msd(List<Double> u, List<Double> v) {
		double sum = 0.0;

		for (int i = 0; i < u.size(); i++) {
			double ui = u.get(i);
			double vi = v.get(i);

			sum += Math.pow(ui - vi, 2);
		}

		double sim = u.size() / sum;
		if (Double.isInfinite(sim))
			sim = 1.0;

		return sim;
	}

	/**
	 * calculate Pearson Correlation Coefficient (PCC) between two vectors of
	 * ratings
	 * 
	 * @param a
	 *            first vector of ratings
	 * @param b
	 *            second vector of ratings
	 * @return Pearson Correlation Coefficient (PCC) value. <br/>
	 *         If vector a or b is null or the length is less than 2, Double.NaN
	 *         is returned.
	 */
	public static double pcc(List<? extends Number> a, List<? extends Number> b) {
		if (a == null || b == null || a.size() < 2 || b.size() < 2 || a.size() != b.size())
			return Double.NaN;

		double mu_a = Stats.mean(a);
		double mu_b = Stats.mean(b);

		double num = 0.0, den_a = 0.0, den_b = 0.0;
		for (int i = 0; i < a.size(); i++) {
			double ai = a.get(i).doubleValue() - mu_a;
			double bi = b.get(i).doubleValue() - mu_b;

			num += ai * bi;
			den_a += ai * ai;
			den_b += bi * bi;
		}

		return num / (Math.sqrt(den_a) * Math.sqrt(den_b));
	}

	/**
	 * calculate extend Jaccard Coefficient between two vectors of ratings
	 */
	public static double exJaccard(List<Double> a, List<Double> b) {
		double num = 0.0, den_a = 0.0, den_b = 0.0;
		for (int i = 0; i < a.size(); i++) {
			double ai = a.get(i);
			double bi = b.get(i);

			num += ai * bi;
			den_a += ai * ai;
			den_b += bi * bi;
		}

		return num / (den_a + den_b - num);
	}

	/**
	 * calculate Dice Coefficient between two vectors of ratings
	 */
	public static double dice(List<Double> a, List<Double> b) {
		double num = 0.0, den_a = 0.0, den_b = 0.0;
		for (int i = 0; i < a.size(); i++) {
			double ai = a.get(i);
			double bi = b.get(i);

			num += 2 * ai * bi;
			den_a += ai * ai;
			den_b += bi * bi;
		}

		return num / (den_a + den_b);
	}

	/**
	 * Jaccard's coefficient is defined as the number of common rated items of
	 * two users divided by the total number of their unique rated items.
	 * 
	 * @return Jaccard's coefficient
	 */
	public static double jaccard(List<Integer> uItems, List<Integer> vItems) {
		int common = 0, all = 0;

		Set<Integer> items = new HashSet<>();
		items.addAll(uItems);
		items.addAll(vItems);

		all = items.size();
		common = uItems.size() + vItems.size() - all;

		return (common + 0.0) / all;

	}

}
