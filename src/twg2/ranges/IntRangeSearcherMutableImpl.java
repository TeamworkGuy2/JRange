package twg2.ranges;

import twg2.ranges.helpers.Ranges;

/**
 * @author TeamworkGuy2
 * @since 2014-10-29
 */
@javax.annotation.Generated("StringTemplate")
public final class IntRangeSearcherMutableImpl implements IntRangeSearcherMutable {
	private twg2.collections.primitiveCollections.IntArrayList rangePoints;
	private final boolean locked;
	private boolean throwIfRangeExists;
	private boolean combineOverlap;


	public IntRangeSearcherMutableImpl(int start1, int end1) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
	}


	public IntRangeSearcherMutableImpl(int start1, int end1, int start2, int end2) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
		addRange(start2, end2);
	}


	public IntRangeSearcherMutableImpl(int start1, int end1, int start2, int end2, int start3, int end3) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
		addRange(start2, end2);
		addRange(start3, end3);
	}


	public IntRangeSearcherMutableImpl(twg2.collections.primitiveCollections.IntList rangePoints) {
		this(rangePoints, false, true, false);
	}


	public IntRangeSearcherMutableImpl(twg2.collections.primitiveCollections.IntList rangePoints, boolean locked,
			boolean throwErrorIfEqualRangeExists, boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.IntArrayList();
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;

		for(int i = 0, size = rangePoints.size(); i < size; i += 2) {
			addRange(rangePoints.get(i), rangePoints.get(i + 1));
		}
		this.locked = locked;
	}


	public IntRangeSearcherMutableImpl(IntRangeSearcher src) {
		this(src, false, true, false);
	}


	public IntRangeSearcherMutableImpl(IntRangeSearcher src, boolean locked,
			boolean throwErrorIfEqualRangeExists, boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.IntArrayList();
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;

		for(int i = 0, size = src.size(); i < size; i++) {
			int start = src.getLowerBound(i);
			int end = src.getUpperBound(i);
			if(throwIfRangeExists) {
				Ranges.throwIfRangeExists(rangePoints, start, end);
			}
			addRange(start, end);
		}
		this.locked = locked;
	}


	public IntRangeSearcherMutableImpl() {
		this(false);
	}


	public IntRangeSearcherMutableImpl(boolean locked) {
		this(locked, true, false);
	}


	public IntRangeSearcherMutableImpl(boolean locked, boolean throwErrorIfEqualRangeExists,
			boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.IntArrayList();
		this.locked = locked;
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;
	}


	public IntRangeSearcherMutableImpl copy() {
		IntRangeSearcherMutableImpl copy = new IntRangeSearcherMutableImpl(this.rangePoints, this.locked, this.throwIfRangeExists, this.combineOverlap);
		return copy;
	}


	@Override
	public boolean contains(int val) {
		return indexOfMatch(val) > -1;
	}


	@Override
	public int indexOfMatch(int val) {
		for(int index = 0, size = rangePoints.size(); index < size; index += 2) {
			if(rangePoints.get(index) <= val && rangePoints.get(index + 1) >= val) {
				return index;
			}
		}
		return -1;
	}


	@Override
	public int getLowerBound(int i) {
		return rangePoints.get(i << 1);
	}


	@Override
	public int getUpperBound(int i) {
		return rangePoints.get((i << 1) + 1);
	}


	@Override
	public int size() {
		return rangePoints.size() >> 1;
	}


	private void checkLocked() {
		if(locked) {
			throw new IllegalStateException("cannot modify a locked IntRangeSearcherMutableImpl");
		}
	}


	@Override
	public void addRange(int start, int end) {
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
	public boolean removeEqualRange(int start, int end) {
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
	public void removeRange(int start, int end) {
		Ranges.removeMatchingRangeSections(rangePoints, start, end);
	}


	@Override
	public IntRangeSearcher toImmutable() {
		return new IntRangeSearcherMutableImpl(this, true, true, false);
	}


	public twg2.collections.primitiveCollections.IntArrayList toIntList() {
		twg2.collections.primitiveCollections.IntArrayList vals = new twg2.collections.primitiveCollections.IntArrayList();
		for(int i = 0, size = this.size(); i < size; i++) {
			int low = this.getLowerBound(i);
			int high = this.getUpperBound(i);
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
