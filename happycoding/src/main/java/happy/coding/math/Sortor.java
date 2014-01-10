package happy.coding.math;

import happy.coding.io.Logs;
import happy.coding.io.Strings;
import happy.coding.system.Debug;
import happy.coding.system.Separator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * An example class for implementing sorting interface
 * <p>
 * if we want to compare with another object of same Class, implement
 * {@code Comparable}; </br> if we want to compare 2 objects with different
 * Class, need to implement {@code Comparator};
 * </p>
 * 
 * @author guoguibing
 * 
 */
class Sortee implements Comparable<Sortee> {

	public double data;

	public Sortee(double _data) {
		data = _data;
	}

	@Override
	public int compareTo(Sortee o) {
		if (data < o.data)
			return -1;
		else if (data > o.data)
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return Strings.toString(data);
	}
}

/**
 * Implementations for sorting algorithms, although a ready-to-use method is
 * {@code Collections.sort()}.
 * 
 * <p>
 * refers to: http://en.wikipedia.org/wiki/Sorting_algorithm
 * </p>
 * 
 * TODO list:
 * <ul>
 * <li>Heap_sort: http://en.wikipedia.org/wiki/Heapsort</li>
 * <li>Counting_sort:
 * http://en.wikipedia.org/wiki/Sorting_algorithm#Counting_sort</li>
 * <li>Bucket_sort: http://en.wikipedia.org/wiki/Sorting_algorithm#Bucket_sort</li>
 * <li>Radix_sort: http://en.wikipedia.org/wiki/Sorting_algorithm#Radix_sort</li>
 * <li>Distribution_sort:
 * http://en.wikipedia.org/wiki/Sorting_algorithm#Distribution_sort</li>
 * <li>Tim_sort: http://en.wikipedia.org/wiki/Sorting_algorithm#Timsort</li>
 * </ul>
 * 
 * @author guoguibing
 * 
 */
public class Sortor {
	/**
	 * refers to: http://en.wikipedia.org/wiki/Insertion_sort
	 * 
	 * <p>
	 * Every repetition of insertion sort removes an element from the input
	 * data, inserting it into the correct position in the already-sorted list,
	 * until no input elements remain.
	 * </p>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> void insertion(List<? extends Comparable<T>> data) {
		int N = data.size();

		for (int i = 1; i < N; i++) {
			for (int j = i; j >= 1; j--) {
				if (data.get(j).compareTo((T) data.get(j - 1)) < 0) {
					swap((List<Comparable<T>>) data, j - 1, j);
				} else {
					break;
				}
			}
		}

	}

	/**
	 * refers to: http://en.wikipedia.org/wiki/Selection_sort
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> void selection(List<? extends Comparable<T>> data) {
		int N = data.size();

		for (int i = 0; i < N - 1; i++) {
			Comparable<T> min = data.get(i);
			int index = i;

			for (int j = i + 1; j < N; j++) {
				if (min.compareTo((T) data.get(j)) > 0) {
					min = data.get(j);
					index = j;
				}
			}
			if (index > i)
				swap((List<Comparable<T>>) data, i, index);
		}

	}

	/**
	 * a.k.a <em>sinking sort</em>, refers to:
	 * http://en.wikipedia.org/wiki/Bubble_sort
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> void bubble(List<? extends Comparable<T>> data) {
		int N = data.size();

		int count = 0;

		while (true) {
			int i = 0;
			boolean swap = false;
			Comparable<T> t0 = data.get(i);

			for (int j = 1; j < N - count; j++) {
				Comparable<T> t1 = data.get(j);
				if (t0.compareTo((T) t1) > 0) {
					swap((List<Comparable<T>>) data, i, j);
					swap = true;
				}

				i = j;
				t0 = data.get(i);
			}

			count++;

			if (Debug.OFF)
				Logs.debug("Step " + count + ": " + Strings.toString(data));

			// if swap = false, means no swapping is occurred in last iteration, i.e. already sorted
			if (!swap)
				break;

		}

	}

	/**
	 * refers to: http://en.wikipedia.org/wiki/Shell_sort
	 * 
	 * <p>
	 * It improves the insertion sort by using a greater gap.
	 * </p>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> void shell(List<? extends Comparable<T>> d) {
		List<Comparable<T>> data = (List<Comparable<T>>) d;
		int N = data.size();

		/* Marcin Ciura's gap sequence */
		int[] gaps = { 701, 301, 132, 57, 23, 10, 4, 1 };

		for (int k = 0; k < gaps.length; k++) {
			for (int i = gaps[k]; i < N; i++) {
				Comparable<T> temp = data.get(i);
				int j = 0;
				for (j = i; j >= gaps[k] && data.get(j - gaps[k]).compareTo((T) temp) > 0; j -= gaps[k]) {
					data.set(j, data.get(j - gaps[k]));
				}

				data.set(j, temp);
			}
		}

	}

	/**
	 * refers to: http://en.wikipedia.org/wiki/Comb_sort
	 * 
	 * <p>
	 * It improves the bubble sort by using a greater gap. In bubble sort, when
	 * any two elements are compared, they always have a gap (distance from each
	 * other) of 1. The basic idea of comb sort is that the gap can be much more
	 * than 1. (Shell sort is also based on this idea, but it is a modification
	 * of insertion sort rather than bubble sort).
	 * </p>
	 * 
	 * <p>
	 * In other words, the inner loop of bubble sort, which does the actual
	 * swap, is modified such that gap between swapped elements goes down (for
	 * each iteration of outer loop) in steps of shrink factor. i.e. [input size
	 * / shrink factor, input size / shrink factor^2, input size / shrink
	 * factor^3, .... , 1 ]. Unlike in bubble sort, where the gap is constant
	 * i.e. 1.
	 * </p>
	 * 
	 * <p>
	 * The shrink factor has a great effect on the efficiency of comb sort. In
	 * the original article, the author suggested 4/3=1.3334. A value too small
	 * slows the algorithm down because more comparisons must be made, whereas a
	 * value too large means that no comparisons will be made. A better shrink
	 * factor is 1/(1-1/e^phi)=1.24733, where phi is the golden ratio.
	 * </p>
	 * 
	 * <p>
	 * The gap starts out as the length of the list being sorted divided by the
	 * shrink factor (generally 1.3), and the list is sorted with that value
	 * (rounded down to an integer if needed) as the gap. Then the gap is
	 * divided by the shrink factor again, the list is sorted with this new gap,
	 * and the process repeats until the gap is 1. At this point, comb sort
	 * continues using a gap of 1 until the list is fully sorted. The final
	 * stage of the sort is thus equivalent to a bubble sort, but by this time
	 * most turtles (small values) have been dealt with, so a bubble sort will
	 * be efficient.
	 * </p>
	 * 
	 * <p>
	 * Guibing note: the essential idea is to move the turtle values faster to
	 * the beginning of list by setting a greater gap.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public static <T> void comb(List<? extends Comparable<T>> d) {
		int N = d.size();
		double factor = 1.24733;

		while (true) {
			int gap = (int) (N / factor);
			if (gap >= 1)
				factor *= factor;
			else
				break;

			/* bubble sort (swap) */
			for (int i = 0; i + gap < N; i++) {
				Comparable<T> t0 = d.get(i);
				Comparable<T> t1 = d.get(i + gap);
				if (t0.compareTo((T) t1) > 0)
					swap((List<Comparable<T>>) d, i, i + gap);
			}
		}

	}

	/**
	 * refers to: http://en.wikipedia.org/wiki/Merge_sort
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<? extends Comparable<T>> merge(List<? extends Comparable<T>> d) {
		int N = d.size();
		if (N <= 1)
			return d;

		List<Comparable<T>> left = new ArrayList<>();
		List<Comparable<T>> right = new ArrayList<>();

		int mid = N / 2;
		for (int i = 0; i < N; i++) {
			if (i < mid)
				left.add(d.get(i));
			else
				right.add(d.get(i));
		}
		left = (List<Comparable<T>>) merge(left);
		right = (List<Comparable<T>>) merge(right);

		return combine(left, right);
	}

	@SuppressWarnings("unchecked")
	private static <T> List<? extends Comparable<T>> combine(List<? extends Comparable<T>> left,
			List<? extends Comparable<T>> right) {
		List<Comparable<T>> result = new ArrayList<>();

		while (left.size() > 0 || right.size() > 0) {
			if (left.size() > 0 && right.size() > 0) {
				if (left.get(0).compareTo((T) right.get(0)) < 0) {
					result.add(left.get(0));
					left.remove(0);
				} else {
					result.add(right.get(0));
					right.remove(0);
				}

			} else if (left.size() > 0) {
				result.add(left.get(0));
				left.remove(0);
			} else if (right.size() > 0) {
				result.add(right.get(0));
				right.remove(0);
			}
		}
		return result;
	}

	/**
	 * refers to: http://en.wikipedia.org/wiki/Quicksort
	 * 
	 * TODO: need to implement
	 * 
	 */
	public static <T> void quick(List<? extends Comparable<T>> d) {
		// int N = d.size();
	}

	public static <T> void swap(List<Comparable<T>> data, int i, int j) {
		Comparable<T> swap = data.get(i);
		data.set(i, data.get(j));
		data.set(j, swap);
	}

	/**
	 * Return k largest elements (sorted) and their indices from a given array.
	 * The original array will be changed, so refer to the first k element of
	 * array1 and array2 after calling this method.
	 * 
	 * @param array1
	 *            original array of data elements
	 * @param array2
	 *            original array containing data index
	 * @param first
	 *            the first element in the array. Use 0 to deal with the whole
	 *            array.
	 * @param last
	 *            the last element in the array. Use the maximum index of the
	 *            array to deal with the whole array.
	 * @param k
	 *            the number of items
	 */
	public static void kLargest(double[] array1, int[] array2, int first, int last, int k) {
		int pivotIndex;
		int firstIndex = first;
		int lastIndex = last;

		while (lastIndex > k * 10) {
			pivotIndex = partition(array1, array2, firstIndex, lastIndex, false);

			if (pivotIndex < k) {
				firstIndex = pivotIndex + 1;
			} else if (pivotIndex < k * 10) { // go out and sort
				lastIndex = pivotIndex;
				break;
			} else {
				lastIndex = pivotIndex;
			}
		}

		quickSort(array1, array2, first, lastIndex, false);
	}

	/**
	 * Return k smallest elements (sorted) and their indices from a given array.
	 * The original array will be changed, so refer to the first k element of
	 * array1 and array2 after calling this method.
	 * 
	 * @param array1
	 *            original array of data elements
	 * @param array2
	 *            original array containing data index
	 * @param first
	 *            the first element in the array. Use 0 to deal with the whole
	 *            array.
	 * @param last
	 *            the last element in the array. Use the maximum index of the
	 *            array to deal with the whole array.
	 * @param k
	 *            the number of items
	 */
	public static void kSmallest(double[] array1, int[] array2, int first, int last, int k) {
		int pivotIndex;
		int firstIndex = first;
		int lastIndex = last;

		while (lastIndex > k * 10) {
			pivotIndex = partition(array1, array2, firstIndex, lastIndex, true);

			if (pivotIndex < k) {
				firstIndex = pivotIndex + 1;
			} else if (pivotIndex < k * 10) { // go out and sort
				lastIndex = pivotIndex;
				break;
			} else {
				lastIndex = pivotIndex;
			}
		}

		quickSort(array1, array2, first, lastIndex, true);
	}

	/**
	 * Sort the given array. The original array will be sorted.
	 * 
	 * @param array
	 *            original array of data elements
	 * @param first
	 *            the first element to be sorted in the array. Use 0 for sorting
	 *            the whole array.
	 * @param last
	 *            the last element to be sorted in the array. Use the maximum
	 *            index of the array for sorting the whole array.
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 */
	public static void quickSort(int[] array, int first, int last, boolean increasingOrder) {
		int pivotIndex;

		if (first < last) {
			pivotIndex = partition(array, first, last, increasingOrder);
			quickSort(array, first, pivotIndex - 1, increasingOrder);
			quickSort(array, pivotIndex + 1, last, increasingOrder);
		}
	}

	/**
	 * Sort the given array, and returns original index as well. The original
	 * array will be sorted.
	 * 
	 * @param array1
	 *            original array of data elements
	 * @param array2
	 *            original array containing data index
	 * @param first
	 *            the first element to be sorted in the array. Use 0 for sorting
	 *            the whole array.
	 * @param last
	 *            the last element to be sorted in the array. Use the maximum
	 *            index of the array for sorting the whole array.
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 */
	public static void quickSort(double[] array1, int[] array2, int first, int last, boolean increasingOrder) {
		int pivotIndex;

		if (first < last) {
			pivotIndex = partition(array1, array2, first, last, increasingOrder);
			quickSort(array1, array2, first, pivotIndex - 1, increasingOrder);
			quickSort(array1, array2, pivotIndex + 1, last, increasingOrder);
		}
	}

	/**
	 * Sort the given array, and returns original index as well. The original
	 * array will be sorted.
	 * 
	 * @param array1
	 *            original array of data elements of type int
	 * @param array2
	 *            original array containing data index
	 * @param first
	 *            the first element to be sorted in the array. Use 0 for sorting
	 *            the whole array.
	 * @param last
	 *            the last element to be sorted in the array. Use the maximum
	 *            index of the array for sorting the whole array.
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 */
	public static void quickSort(int[] array1, int[] array2, int first, int last, boolean increasingOrder) {
		int pivotIndex;

		if (first < last) {
			pivotIndex = partition(array1, array2, first, last, increasingOrder);
			quickSort(array1, array2, first, pivotIndex - 1, increasingOrder);
			quickSort(array1, array2, pivotIndex + 1, last, increasingOrder);
		}
	}

	/**
	 * Sort the given array, and returns original index as well. The original
	 * array will be sorted.
	 * 
	 * @param array1
	 *            original array of data elements of type int
	 * @param array2
	 *            original array containing data of type double
	 * @param first
	 *            the first element to be sorted in the array. Use 0 for sorting
	 *            the whole array.
	 * @param last
	 *            the last element to be sorted in the array. Use the maximum
	 *            index of the array for sorting the whole array.
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 */
	public static void quickSort(int[] array1, double[] array2, int first, int last, boolean increasingOrder) {
		int pivotIndex;

		if (first < last) {
			pivotIndex = partition(array1, array2, first, last, increasingOrder);
			quickSort(array1, array2, first, pivotIndex - 1, increasingOrder);
			quickSort(array1, array2, pivotIndex + 1, last, increasingOrder);
		}
	}

	/**
	 * Partition the given array into two section: smaller and larger than
	 * threshold. The threshold is selected from the first element of original
	 * array.
	 * 
	 * @param array
	 *            original array of data elements
	 * @param first
	 *            the first element in the array
	 * @param last
	 *            the last element in the array
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 * @return the index of threshold item after partitioning
	 */
	private static int partition(int[] array, int first, int last, boolean increasingOrder) {
		int tmpInt;
		int pivot = array[first];

		int lastS1 = first;

		for (int firstUnknown = first + 1; firstUnknown <= last; ++firstUnknown) {
			if (increasingOrder) {
				if (array[firstUnknown] < pivot) {
					++lastS1;

					tmpInt = array[firstUnknown];
					array[firstUnknown] = array[lastS1];
					array[lastS1] = tmpInt;
				}
			} else {
				if (array[firstUnknown] > pivot) {
					++lastS1;

					tmpInt = array[firstUnknown];
					array[firstUnknown] = array[lastS1];
					array[lastS1] = tmpInt;
				}
			}
		}

		tmpInt = array[first];
		array[first] = array[lastS1];
		array[lastS1] = tmpInt;

		return lastS1;
	}

	/**
	 * Partition the given array into two section: smaller and larger than
	 * threshold. The threshold is selected from the first element of original
	 * array.
	 * 
	 * @param array1
	 *            original array of data elements
	 * @param array2
	 *            original array containing data index
	 * @param first
	 *            the first element in the array
	 * @param last
	 *            the last element in the array
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 * @return the index of threshold item after partitioning
	 */
	private static int partition(double[] array1, int[] array2, int first, int last, boolean increasingOrder) {
		double tmpDouble;
		int tmpInt;
		double pivot = array1[first];

		int lastS1 = first;

		for (int firstUnknown = first + 1; firstUnknown <= last; ++firstUnknown) {
			if (increasingOrder) {
				if (array1[firstUnknown] < pivot) {
					++lastS1;

					tmpDouble = array1[firstUnknown];
					array1[firstUnknown] = array1[lastS1];
					array1[lastS1] = tmpDouble;

					tmpInt = array2[firstUnknown];
					array2[firstUnknown] = array2[lastS1];
					array2[lastS1] = tmpInt;
				}
			} else {
				if (array1[firstUnknown] > pivot) {
					++lastS1;

					tmpDouble = array1[firstUnknown];
					array1[firstUnknown] = array1[lastS1];
					array1[lastS1] = tmpDouble;

					tmpInt = array2[firstUnknown];
					array2[firstUnknown] = array2[lastS1];
					array2[lastS1] = tmpInt;
				}
			}
		}

		tmpDouble = array1[first];
		array1[first] = array1[lastS1];
		array1[lastS1] = tmpDouble;

		tmpInt = array2[first];
		array2[first] = array2[lastS1];
		array2[lastS1] = tmpInt;

		return lastS1;
	}

	/**
	 * Partition the given array into two section: smaller and larger than
	 * threshold. The threshold is selected from the first element of original
	 * array.
	 * 
	 * @param array1
	 *            original array of data elements of type int
	 * @param array2
	 *            original array containing data index
	 * @param first
	 *            the first element in the array
	 * @param last
	 *            the last element in the array
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 * @return the index of threshold item after partitioning
	 */
	private static int partition(int[] array1, int[] array2, int first, int last, boolean increasingOrder) {
		int tmp1;
		int tmpInt;
		int pivot = array1[first];

		int lastS1 = first;

		for (int firstUnknown = first + 1; firstUnknown <= last; ++firstUnknown) {
			if (increasingOrder) {
				if (array1[firstUnknown] < pivot) {
					++lastS1;

					tmp1 = array1[firstUnknown];
					array1[firstUnknown] = array1[lastS1];
					array1[lastS1] = tmp1;

					tmpInt = array2[firstUnknown];
					array2[firstUnknown] = array2[lastS1];
					array2[lastS1] = tmpInt;
				}
			} else {
				if (array1[firstUnknown] > pivot) {
					++lastS1;

					tmp1 = array1[firstUnknown];
					array1[firstUnknown] = array1[lastS1];
					array1[lastS1] = tmp1;

					tmpInt = array2[firstUnknown];
					array2[firstUnknown] = array2[lastS1];
					array2[lastS1] = tmpInt;
				}
			}
		}

		tmp1 = array1[first];
		array1[first] = array1[lastS1];
		array1[lastS1] = tmp1;

		tmpInt = array2[first];
		array2[first] = array2[lastS1];
		array2[lastS1] = tmpInt;

		return lastS1;
	}

	/**
	 * Partition the given array into two section: smaller and larger than
	 * threshold. The threshold is selected from the first element of original
	 * array.
	 * 
	 * @param array1
	 *            original array of data elements of type int
	 * @param array2
	 *            original array containing data of type double
	 * @param first
	 *            the first element in the array
	 * @param last
	 *            the last element in the array
	 * @param increasingOrder
	 *            indicating the sort is in increasing order. Use true for
	 *            increasing order, false for decreasing order.
	 * @return the index of threshold item after partitioning
	 */
	private static int partition(int[] array1, double[] array2, int first, int last, boolean increasingOrder) {
		int tmp1;
		double tmp2;
		int pivot = array1[first];

		int lastS1 = first;

		for (int firstUnknown = first + 1; firstUnknown <= last; ++firstUnknown) {
			if (increasingOrder) {
				if (array1[firstUnknown] < pivot) {
					++lastS1;

					tmp1 = array1[firstUnknown];
					array1[firstUnknown] = array1[lastS1];
					array1[lastS1] = tmp1;

					tmp2 = array2[firstUnknown];
					array2[firstUnknown] = array2[lastS1];
					array2[lastS1] = tmp2;
				}
			} else {
				if (array1[firstUnknown] > pivot) {
					++lastS1;

					tmp1 = array1[firstUnknown];
					array1[firstUnknown] = array1[lastS1];
					array1[lastS1] = tmp1;

					tmp2 = array2[firstUnknown];
					array2[firstUnknown] = array2[lastS1];
					array2[lastS1] = tmp2;
				}
			}
		}

		tmp1 = array1[first];
		array1[first] = array1[lastS1];
		array1[lastS1] = tmp1;

		tmp2 = array2[first];
		array2[first] = array2[lastS1];
		array2[lastS1] = tmp2;

		return lastS1;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void example() {
		List<Sortee> data = new ArrayList<>();

		int N = 10;

		for (int i = 0; i < N; i++)
			data.add(new Sortee(Randoms.random()));

		Strings.setSeparator(Separator.comma);
		Logs.debug("Before   : " + Strings.toString(data));

		List<Sortee> raw = prepareData(data);
		Sortor.insertion(raw);
		Logs.debug("Insertion: " + Strings.toString(raw));

		raw = prepareData(data);
		Sortor.selection(raw);
		Logs.debug("Selection: " + Strings.toString(raw));

		raw = prepareData(data);
		Sortor.bubble(raw);
		Logs.debug("Bubble   : " + Strings.toString(raw));

		raw = prepareData(data);
		Sortor.shell(raw);
		Logs.debug("Shell    : " + Strings.toString(raw));

		raw = prepareData(data);
		Sortor.comb(raw);
		Logs.debug("Comb     : " + Strings.toString(raw));

		raw = prepareData(data);
		raw = (List<Sortee>) Sortor.merge(raw);
		Logs.debug("Merge    : " + Strings.toString(raw));
	}

	private List<Sortee> prepareData(List<Sortee> data) {
		List<Sortee> raw = new ArrayList<>();
		raw.addAll(data);
		return raw;
	}
}
