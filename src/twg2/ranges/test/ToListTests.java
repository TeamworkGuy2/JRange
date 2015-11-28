package twg2.ranges.test;

import org.junit.Test;

import checks.CheckCollections;
import twg2.collections.primitiveCollections.CharArrayList;
import twg2.ranges.CharRangeSearcherMutableImpl;
import twg2.ranges.CharSearchSet;

/**
 * @author TeamworkGuy2
 * @since 2015-11-26
 */
public class ToListTests {

	@Test
	public void testRangeSearcherMutableImpl() {
		CharRangeSearcherMutableImpl ranges = new CharRangeSearcherMutableImpl(false);
		ranges.addRange('a', 'f');
		ranges.addRange('0', '1');

		CharArrayList expect = CharArrayList.of('a', 'b', 'c', 'd', 'e', 'f', '0', '1');
		CheckCollections.assertLooseEquals(ranges.toCharList().toList(), expect.toList());
	}


	@Test
	public void testSearchSet() {
		CharSearchSet set = new CharSearchSet();
		set.addRange('a', 'f');
		set.addChar('0');
		set.addChar('1');

		CharArrayList expect = CharArrayList.of('a', 'b', 'c', 'd', 'e', 'f', '0', '1');
		CheckCollections.assertLooseEquals(set.toCharList().toList(), expect.toList());
	}

}
