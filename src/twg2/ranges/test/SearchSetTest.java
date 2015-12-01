package twg2.ranges.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.ranges.CharSearchSet;

/**
 * @author TeamworkGuy2
 * @since 2015-11-29
 */
public class SearchSetTest {

	@Test
	public void toStringTest() {
		CharSearchSet set = new CharSearchSet();
		set.addChar('$');
		set.addChar('_');
		set.addRange('0', '3');
		set.addRange('a', 'f');

		Assert.assertEquals("[$, _, 0-3, a-f]", set.toString());
	}

}
