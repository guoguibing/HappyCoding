package happy.coding.io;

/**
 * 
 * @author guoguibing
 * 
 * @param <K>
 *            the type of key
 */
public class KeyValPair<K> implements Comparable<KeyValPair<K>> {
	private K key;
	private Number val;
	private boolean isSortByKey;

	public KeyValPair(K left, Number right) {
		this(left, right, false);
	}

	public KeyValPair(K left, Number right, boolean isSortByKey) {
		this.key = left;
		this.val = right;
		this.isSortByKey = isSortByKey;
	}

	@Override
	public int compareTo(KeyValPair<K> that) {
		int com = Double.compare(this.val.doubleValue(), that.val.doubleValue());

		if (isSortByKey && com == 0)
			com = this.key.toString().compareTo(that.key.toString());

		return com;
	}

	public K getKey() {
		return key;
	}

	public double getValue() {
		return val.doubleValue();
	}

	@Override
	public String toString() {
		return key + ": " + val.floatValue();
	}

}
