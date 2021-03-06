package twg2.ranges;

import twg2.collections.primitiveCollections.IntListSorted;

/** A {@link IntSearcher} that contains a set of ints to compare input int to.
 * @author TeamworkGuy2
 * @since 2014-11-1
 */
@javax.annotation.Generated("StringTemplate")
public final class IntSearcherMutable implements IntSearcher {
	private IntListSorted values;
	private final boolean locked;


	@SafeVarargs
	public IntSearcherMutable(int... cs) {
		this.locked = false;
		if(cs != null) {
			this.values = new IntListSorted(cs.length);
			for(int c : cs) {
				if(this.values.contains(c)) {
					throw new IllegalArgumentException("duplicate searcher int '" + c + "'");
				}
				this.values.add(c);
			}
		}
		else {
			this.values = new IntListSorted();
		}
	}


	public IntSearcherMutable(IntSearcherMutable valueSearcher, boolean locked) {
		this(valueSearcher.values, locked);
	}


	public IntSearcherMutable(IntListSorted values, boolean locked) {
		this.locked = locked;
		this.values = new IntListSorted();

		for(int i = 0, size = values.size(); i < size; i++) {
			int c = values.get(i);
			if(this.values.contains(c)) {
				throw new IllegalArgumentException("duplicate searcher int '" + c + "'");
			}
			this.values.add(c);
		}
	}


	public IntSearcherMutable copy() {
		IntSearcherMutable copy = new IntSearcherMutable(this.values, this.locked);
		return copy;
	}


	@Override
	public boolean contains(int value) {
		return indexOfMatch(value) > -1;
	}


	@Override
	public int indexOfMatch(int value) {
		return values.indexOf(value);
	}


	public int size() {
		return values.size();
	}


	public boolean isLocked() {
		return locked;
	}


	private void checkLocked() {
		if(locked) {
			throw new IllegalStateException("cannot modify a locked IntSearcher");
		}
	}


	public void addInt(int value) {
		checkLocked();
		if(values.indexOf(value) > -1) {
			throw new IllegalArgumentException("duplicate searcher int '" + value + "'");
		}
		values.add(value);
	}


	public boolean removeIntAt(int index) {
		checkLocked();
		values.remove(index);
		return true;
	}


	public boolean removeInt(int value) {
		checkLocked();
		int index = values.indexOf(value);
		if (index == -1) {
			return false;
		}
		values.remove(index);
		return true;
	}


	public IntSearcher toImmutable() {
		return new IntSearcherMutable(this, true);
	}


	public IntListSorted toList() {
		return values.copy();
	}


	/** WARNING: this is provided for performance optimizations, this returns the underlying sorted values
	 * that represent this mutable searcher. This method's name, parameters, or return type may change.
	 */
	public IntListSorted getValuesRaw() {
		return values;
	}


	@Override
	public String toString() {
		return values.toString();
	}

}
