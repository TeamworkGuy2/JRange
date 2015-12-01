package twg2.ranges;

import twg2.ranges.helpers.Ranges;

/**
 * @author TeamworkGuy2
 * @since 2014-10-29
 */
@javax.annotation.Generated("StringTemplate")
public final class FloatRangeSearcherMutableImpl implements FloatRangeSearcherMutable {
	private twg2.collections.primitiveCollections.FloatArrayList rangePoints;
	private final boolean locked;
	private boolean throwIfRangeExists;
	private boolean combineOverlap;


	public FloatRangeSearcherMutableImpl(float start1, float end1) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
	}


	public FloatRangeSearcherMutableImpl(float start1, float end1, float start2, float end2) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
		addRange(start2, end2);
	}


	public FloatRangeSearcherMutableImpl(float start1, float end1, float start2, float end2, float start3, float end3) {
		this(false);
		rangePoints.add(start1);
		rangePoints.add(end1);
		addRange(start2, end2);
		addRange(start3, end3);
	}


	public FloatRangeSearcherMutableImpl(twg2.collections.primitiveCollections.FloatList rangePoints) {
		this(rangePoints, false, true, false);
	}


	public FloatRangeSearcherMutableImpl(twg2.collections.primitiveCollections.FloatList rangePoints, boolean locked,
			boolean throwErrorIfEqualRangeExists, boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.FloatArrayList();
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;

		for(int i = 0, size = rangePoints.size(); i < size; i += 2) {
			addRange(rangePoints.get(i), rangePoints.get(i + 1));
		}
		this.locked = locked;
	}


	public FloatRangeSearcherMutableImpl(FloatRangeSearcher src) {
		this(src, false, true, false);
	}


	public FloatRangeSearcherMutableImpl(FloatRangeSearcher src, boolean locked,
			boolean throwErrorIfEqualRangeExists, boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.FloatArrayList();
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;

		for(int i = 0, size = src.size(); i < size; i++) {
			float start = src.getLowerBound(i);
			float end = src.getUpperBound(i);
			if(throwIfRangeExists) {
				Ranges.throwIfRangeExists(rangePoints, start, end);
			}
			addRange(start, end);
		}
		this.locked = locked;
	}


	public FloatRangeSearcherMutableImpl() {
		this(false);
	}


	public FloatRangeSearcherMutableImpl(boolean locked) {
		this(locked, true, false);
	}


	public FloatRangeSearcherMutableImpl(boolean locked, boolean throwErrorIfEqualRangeExists,
			boolean combineOverlappingRanges) {
		this.rangePoints = new twg2.collections.primitiveCollections.FloatArrayList();
		this.locked = locked;
		this.throwIfRangeExists = throwErrorIfEqualRangeExists;
		this.combineOverlap = combineOverlappingRanges;
	}


	@Override
	public boolean contains(float val) {
		return indexOfMatch(val) > -1;
	}


	@Override
	public int indexOfMatch(float val) {
		for(int index = 0, size = rangePoints.size(); index < size; index += 2) {
			if(rangePoints.get(index) <= val && rangePoints.get(index + 1) >= val) {
				return index;
			}
		}
		return -1;
	}


	@Override
	public float getLowerBound(int i) {
		return rangePoints.get(i << 1);
	}


	@Override
	public float getUpperBound(int i) {
		return rangePoints.get((i << 1) + 1);
	}


	@Override
	public int size() {
		return rangePoints.size() >> 1;
	}


	private void checkLocked() {
		if(locked) {
			throw new IllegalStateException("cannot modify a locked FloatRangeSearcherMutableImpl");
		}
	}


	@Override
	public void addRange(float start, float end) {
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
	public boolean removeEqualRange(float start, float end) {
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
	public void removeRange(float start, float end) {
		Ranges.removeMatchingRangeSections(rangePoints, start, end);
	}


	@Override
	public FloatRangeSearcher toImmutable() {
		return new FloatRangeSearcherMutableImpl(this, true, true, false);
	}


	public twg2.collections.primitiveCollections.FloatArrayList toFloatList() {
		twg2.collections.primitiveCollections.FloatArrayList vals = new twg2.collections.primitiveCollections.FloatArrayList();
		for(int i = 0, size = this.size(); i < size; i++) {
			float low = this.getLowerBound(i);
			float high = this.getUpperBound(i);
			for(float ii = low; ii <= high; ii++) {
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
