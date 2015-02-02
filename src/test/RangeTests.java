package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import primitiveCollections.IntArrayList;
import primitiveCollections.IntList;
import ranges.IntRangeSearcherMutable;
import ranges.IntRangeSearcherMutableImpl;
import ranges.Ranges;
import checks.Check;
import collectionUtils.ListUtil;

/**
 * @author TeamworkGuy2
 * @since 2014-12-13
 */
public class RangeTests {


	@Test
	public void testRemoveRangeSections() {
		IntList rangePoints = new IntArrayList(new int[] { 0, 10, 20, 24, 50, 54 });
		IntRangeSearcherMutable ranges = new IntRangeSearcherMutableImpl(rangePoints, false, false, true);

		Integer[] indices = { 0, 1, 9, 10, 24, 26, 54 };
		Boolean[] expected = { true, true, true, true, true, false, true };

		Check.assertTests(indices, expected, "", "", (val) -> ranges.isMatch(val));

		int size = ranges.size();
		ranges.addRange(25, 30);
		Assert.assertEquals("ranges did not combine correctly", size, ranges.size());

		ranges.removeRange(23, 27);

		indices = new Integer[] { 19, 20, 22, 23, 26, 28, 30, 31 };
		expected = new Boolean[] { false, true, true, false, false, true, true, false };

		Check.assertTests(indices, expected, "", "", (val) -> ranges.isMatch(val));

		ranges.removeRange(25, 30);
		ranges.removeRange(40, 50);
		ranges.removeRange(54, 54);
		ranges.removeRange(52, 52);
		ranges.removeRange(50, 54);

		Assert.assertEquals("ranges did not remove correctly", 2, ranges.size());
	}


	@Test
	public void charSearchRangeTest() {
		int[] duplicateRanges = new int[] {
				1, 2,
				3, 5,
				3, 5,
				1, 2,
				3, 5,
		};
		IntArrayList duplicateRangeList = new IntArrayList(duplicateRanges, 0, duplicateRanges.length);
		System.out.println("with duplicate ranges:\t\t" + duplicateRangeList);
		Check.assertException(() -> Ranges.throwIfDuplicateRanges(duplicateRangeList));
		System.out.println("removed duplicate ranges:\t" + duplicateRangeList);

		int[][] ranges = new int[][] {
				{ 1, 4, 3, 6 },
				{ 1, 7, 3, 6 },
				{ 1, 2, 3, 6 },
				{ 3, 6, 6, 7 },
				{ 1, 2, 3, 3 },
				{ 3, 3, 2, 4 },
		};
		// expected range overlap values
		Boolean[] expectedOverlap = new Boolean[] { true, true, false, true, false, true };
		// expected range overlap counts
		Integer[] expectedOverlapCount = new Integer[] { 2, 4, 0, 1, 0, 1 };

		for(int i = 0, size = ranges.length; i < size; i ++) {
			System.out.println("range [" + ranges[i][0] + ", " + ranges[i][1] +
					"], to [" + ranges[i][2] + ", " + ranges[i][3] + "], overlap: " +
					Ranges.doRangesOverlap(ranges[i][0], ranges[i][1], ranges[i][2], ranges[i][3]) +
					", count: " + Ranges.rangeOverlapCount(ranges[i][0], ranges[i][1], ranges[i][2], ranges[i][3]));
		}

		Check.assertTests(ranges, expectedOverlap, "", "",
				(range) -> Ranges.doRangesOverlap(range[0], range[1], range[2], range[3]));
		Check.assertTests(ranges, expectedOverlapCount, "", "",
				(range) -> Ranges.rangeOverlapCount(range[0], range[1], range[2], range[3]));
	}


	@Test
	public void listUtilAddTest() {
		@SuppressWarnings("unchecked")
		List<String>[] lists = new List[] {
				Arrays.asList("a", "unique", "list"),
				Arrays.asList("list", "non", "unique", "list"),
				Arrays.asList(""),
				Arrays.asList(),
		};
		Boolean[] expect = new Boolean[] { true, false, true, true };

		Check.assertTests(lists, expect, "", "", (list) -> ListUtil.isUnique(list));
	}

}
