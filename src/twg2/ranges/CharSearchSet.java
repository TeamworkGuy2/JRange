package twg2.ranges;

import twg2.collections.primitiveCollections.CharListSorted;

/** A {@link CharSearcher} that contains a {@link CharRange}
 * and a {@link CharSearcherMutable}
 * @author TeamworkGuy2
 * @since 2014-11-2
 */
@javax.annotation.Generated("StringTemplate")
public final class CharSearchSet implements CharSearcher {
	private CharListSorted values;
	private CharRangeSearcherMutableImpl ranges;
	private boolean locked;


	public CharSearchSet() {
		this.values = new CharListSorted();
		this.ranges = new CharRangeSearcherMutableImpl();
	}


	@Override
	public boolean contains(char ch) {
		return values.indexOf(ch) > -1 || ranges.contains(ch);
	}


	@Override
	public int indexOfMatch(char ch) {
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


	public void addChar(char ch) {
		checkLocked();
		if(values.contains(ch)) {
			throw new IllegalArgumentException("duplicate searcher char '" + ch + "'");
		}
		values.add(ch);
	}


	public boolean removeCharAt(int index) {
		checkLocked();
		values.remove(index);
		return false;
	}


	public boolean removeChar(char ch) {
		checkLocked();
		return values.removeValue(ch);
	}


	public void addRange(char start, char end) {
		checkLocked();
		ranges.addRange(start, end);
	}


	public boolean removeRangeAt(int index) {
		checkLocked();
		ranges.removeRangeAt(index);
		return true;
	}


	public boolean removeRange(char start, char end) {
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
			throw new IllegalStateException("cannot modify a locked CharSearcher");
		}
	}


	public CharSearcher toImmutable() {
		this.setLocked(true);
		return this;
	}


	public CharListSorted toCharList() {
		CharListSorted vals = values.copy();
		for(int i = 0, size = ranges.size(); i < size; i++) {
			char low = ranges.getLowerBound(i);
			char high = ranges.getUpperBound(i);
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
