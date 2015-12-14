package twg2.ranges;

import twg2.collections.primitiveCollections.FloatListSorted;

/** A {@link FloatSearcher} that contains a {@link FloatRange}
 * and a {@link CharSearcherMutable}
 * @author TeamworkGuy2
 * @since 2014-11-2
 */
@javax.annotation.Generated("StringTemplate")
public final class FloatSearchSet implements FloatSearcher {
	private FloatListSorted values;
	private FloatRangeSearcherMutableImpl ranges;
	private boolean locked;


	public FloatSearchSet() {
		this.values = new FloatListSorted();
		this.ranges = new FloatRangeSearcherMutableImpl();
	}


	// package-private
	FloatSearchSet(FloatListSorted values, FloatRangeSearcherMutableImpl ranges, boolean locked) {
		this.values = values.copy();
		this.ranges = ranges.copy();
		this.locked = locked;
	}


	public FloatSearchSet copy() {
		FloatSearchSet copy = new FloatSearchSet(this.values, this.ranges, this.locked);
		return copy;
	}


	@Override
	public boolean contains(float ch) {
		return values.indexOf(ch) > -1 || ranges.contains(ch);
	}


	@Override
	public int indexOfMatch(float ch) {
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


	public void addFloat(float ch) {
		checkLocked();
		if(values.contains(ch)) {
			throw new IllegalArgumentException("duplicate searcher float '" + ch + "'");
		}
		values.add(ch);
	}


	public boolean removeFloatAt(int index) {
		checkLocked();
		values.remove(index);
		return false;
	}


	public boolean removeFloat(float ch) {
		checkLocked();
		return values.removeValue(ch);
	}


	public void addRange(float start, float end) {
		checkLocked();
		ranges.addRange(start, end);
	}


	public boolean removeRangeAt(int index) {
		checkLocked();
		ranges.removeRangeAt(index);
		return true;
	}


	public boolean removeRange(float start, float end) {
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
			throw new IllegalStateException("cannot modify a locked FloatSearcher");
		}
	}


	public FloatSearcher toImmutable() {
		this.setLocked(true);
		return this;
	}


	public FloatListSorted toFloatList() {
		FloatListSorted vals = values.copy();
		for(int i = 0, size = ranges.size(); i < size; i++) {
			float low = ranges.getLowerBound(i);
			float high = ranges.getUpperBound(i);
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
