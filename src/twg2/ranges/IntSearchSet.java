package twg2.ranges;

import twg2.collections.primitiveCollections.IntListSorted;

/** A {@link IntSearcher} that contains a {@link IntRange}
 * and a {@link CharSearcherMutable}
 * @author TeamworkGuy2
 * @since 2014-11-2
 */
@javax.annotation.Generated("StringTemplate")
public final class IntSearchSet implements IntSearcher {
	private IntListSorted values;
	private IntRangeSearcherMutableImpl ranges;
	private boolean locked;


	public IntSearchSet() {
		this.values = new IntListSorted();
		this.ranges = new IntRangeSearcherMutableImpl();
	}


	@Override
	public boolean contains(int ch) {
		return values.indexOf(ch) > -1 || ranges.contains(ch);
	}


	@Override
	public int indexOfMatch(int ch) {
		int index = values.indexOf(ch);
		if(index > -1) {
			return index;
		}
		else {
			index = ranges.indexOfMatch(ch);
			if(index > -1) {
				return values.size() + index;
			}
			else {
				return -1;
			}
		}
	}


	public int size() {
		return values.size() + ranges.size();
	}


	public void addInt(int ch) {
		checkLocked();
		if(values.contains(ch)) {
			throw new IllegalArgumentException("duplicate searcher int '" + ch + "'");
		}
		values.add(ch);
	}


	public boolean removeIntAt(int index) {
		checkLocked();
		values.remove(index);
		return false;
	}


	public boolean removeInt(int ch) {
		checkLocked();
		return values.removeValue(ch);
	}


	public void addRange(int start, int end) {
		checkLocked();
		ranges.addRange(start, end);
	}


	public boolean removeRangeAt(int index) {
		checkLocked();
		ranges.removeRangeAt(index);
		return true;
	}


	public boolean removeRange(int start, int end) {
		checkLocked();
		return ranges.removeEqualRange(start, end);
	}


	public boolean isLocked() {
		return locked;
	}


	public void setLocked(boolean locked) {
		this.locked = locked;
	}


	private void checkLocked() {
		if(locked) {
			throw new IllegalStateException("cannot modify a locked IntSearcher");
		}
	}


	public IntSearcher toImmutable() {
		this.setLocked(true);
		return this;
	}


	public IntListSorted toIntList() {
		IntListSorted vals = values.copy();
		for(int i = 0, size = ranges.size(); i < size; i++) {
			int low = ranges.getLowerBound(i);
			int high = ranges.getUpperBound(i);
			for(int ii = low; ii <= high; ii++) {
				vals.add(ii);
			}
		}
		return vals;
	}

}
