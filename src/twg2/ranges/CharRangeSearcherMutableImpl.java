package twg2.ranges;

import twg2.ranges.helpers.Ranges;

/**
 * @author TeamworkGuy2
 * @since 2014-10-29
 */
@javax.annotation.Generated("StringTemplate")
public final class CharRangeSearcherMutableImpl implements CharRangeSearcherMutable {
	private twg2.collections.primitiveCollections.CharArrayList rangePoints;
	private final boolean locked;
	private boolean throwIfRangeExists;
	private boolean combineOverlap;


	public CharRangeSearcherMutableImpl(char start1, char end1) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
	}


	public CharRangeSearcherMutableImpl(char start1, char end1, char start2, char end2) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
		addRange(start2, end2);
	}


	public CharRangeSearcherMutableImpl(char start1, char end1, char start2, char end2, char start3, char end3) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
		addRange(start2, end2);
		addRange(start3, end3);
	}


	public CharRangeSearcherMutableImpl(twg2.collections.primitiveCollections.CharList rangePoints) {
		this(rangePoints, false, true, false);
	}


	public CharRangeSearcherMutableImpl(twg2.collections.primitiveCollections.CharList rangePoints, boolean locked,
			boolean throwErrorIfEqualRangeExists, boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.CharArrayList();
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;

		for(int i = 0, size = rangePoints.size(); i < size; i += 2) {
			addRange(rangePoints.get(i), rangePoints.get(i + 1));
		}
		this.locked = locked;
	}


	public CharRangeSearcherMutableImpl(CharRangeSearcher src) {
		this(src, false, true, false);
	}


	public CharRangeSearcherMutableImpl(CharRangeSearcher src, boolean locked,
			boolean throwErrorIfEqualRangeExists, boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.CharArrayList();
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;

		for(int i = 0, size = src.size(); i < size; i++) {
			char start = src.getLowerBound(i);
			char end = src.getUpperBound(i);
			if(throwIfRangeExists) {
				Ranges.throwIfRangeExists(rangePoints, start, end);
			}
			addRange(start, end);
		}
		this.locked = locked;
	}


	public CharRangeSearcherMutableImpl() {
		this(false);
	}


	public CharRangeSearcherMutableImpl(boolean locked) {
		this(locked, true, false);
	}


	public CharRangeSearcherMutableImpl(boolean locked, boolean throwErrorIfEqualRangeExists,
			boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.CharArrayList();
		this.locked = locked;
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;
	}


	@Override
	public boolean contains(char val) {
		return indexOfMatch(val) > -1;
	}


	@Override
	public int indexOfMatch(char val) {
		for(int index = 0, size = rangePoints.size(); index < size; index += 2) {
			if(rangePoints.get(index) <= val && rangePoints.get(index + 1) >= val) {
				return index;
			}
		}
		return -1;
	}


	@Override
	public char getLowerBound(int i) {
		return rangePoints.get(i << 1);
	}


	@Override
	public char getUpperBound(int i) {
		return rangePoints.get((i << 1) + 1);
	}


	@Override
	public int size() {
		return rangePoints.size() >> 1;
	}


	private void checkLocked() {
		if(locked) {
			throw new IllegalStateException("cannot modify a locked CharRangeSearcherMutableImpl");
		}
	}


	@Override
	public void addRange(char start, char end) {
		checkLocked();
		if(throwIfRangeExists) {
			Ranges.throwIfRangeExists(rangePoints, start, end);
		}
		if(combineOverlap) {
			Ranges.addRangeCombineOverlap(rangePoints, start, end);
		}
		else {
			rangePoints.add(start);
			rangePoints.add(end);
		}
	}


	@Override
	public boolean removeRangeAt(int index) {
		checkLocked();
		rangePoints.remove((index << 1) + 1);
		rangePoints.remove((index << 1));
		return true;
	}


	@Override
	public boolean removeEqualRange(char start, char end) {
		checkLocked();
		int index = Ranges.indexOfRange(rangePoints, start, end);
		if(index > -1) {
			rangePoints.remove(index + 1);
			rangePoints.remove(index);
			return true;
		}
		return false;
	}


	@Override
	public void removeRange(char start, char end) {
		Ranges.removeMatchingRangeSections(rangePoints, start, end);
	}


	@Override
	public CharRangeSearcher toImmutable() {
		return new CharRangeSearcherMutableImpl(this, true, true, false);
	}


	public twg2.collections.primitiveCollections.CharArrayList toCharList() {
		twg2.collections.primitiveCollections.CharArrayList vals = new twg2.collections.primitiveCollections.CharArrayList();
		for(int i = 0, size = this.size(); i < size; i++) {
			char low = this.getLowerBound(i);
			char high = this.getUpperBound(i);
			for(char ii = low; ii <= high; ii++) {
				vals.add(ii);
			}
		}
		return vals;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		int maxR = rangePoints.size() - 2;
		for(int i = 0; i < maxR; i+=2) {
			sb.append(rangePoints.get(i));
			sb.append('-');
			sb.append(rangePoints.get(i + 1));
			sb.append(", ");
		}
		if(maxR > -2) {
			sb.append(rangePoints.get(maxR));
			sb.append('-');
			sb.append(rangePoints.get(maxR + 1));
		}
		sb.append(']');
		return sb.toString();
	}

}
