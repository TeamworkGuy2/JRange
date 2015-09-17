package twg2.ranges;

/**
 * @author TeamworkGuy2
 * @since 2014-12-13
 * @see IntRange
 * @see IntSearcher
 */
@javax.annotation.Generated("StringTemplate")
public interface IntRangeSearcher extends IntRange, IntSearcher {

	@Override
	public boolean contains(int val);


	@Override
	public int indexOfMatch(int val);


	@Override
	public int getLowerBound(int i);


	@Override
	public int getUpperBound(int i);


	@Override
	public int size();

}
