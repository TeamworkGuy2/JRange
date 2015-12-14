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


	// package-private
	IntSearchSet(IntListSorted values, IntRangeSearcherMutableImpl ranges, boolean locked) {
		this.values = values.copy();
		this.ranges = ranges.copy();
		this.locked = locked;
	}


	public IntSearchSet copy() {
		IntSearchSet copy = new IntSearchSet(this.values, this.ranges, this.locked);
		return copy;
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


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		int maxV = values.size() - 1;
		for(int i = 0; i < maxV; i++) {
			sb.append(values.get(i));
			sb.append(", ");
		}
		if(maxV > -1) {
			sb.append(values.get(maxV));
		}

		int maxR = ranges.size() - 1;
		if(maxV > -1 && maxR > -1) {
			sb.append(", ");
		}
		for(int i = 0; i < maxR; i++) {
			sb.append(ranges.getLowerBound(i));
			sb.append('-');
			sb.append(ranges.getUpperBound(i));
			sb.append(", ");
		}
		if(maxR > -1) {
			sb.append(ranges.getLowerBound(maxR));
			sb.append('-');
			sb.append(ranges.getUpperBound(maxR));
		}
		sb.append(']');
		return sb.toString();
	}

}
