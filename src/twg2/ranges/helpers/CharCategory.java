package twg2.ranges.helpers;

import twg2.ranges.CharRangeSearcherMutableImpl;
import twg2.ranges.CharSearcher;
import twg2.ranges.CharSearcherMutable;

/** A set of enums to represent various common character categories
 * @author TeamworkGuy2
 * @since 2014-10-9
 */
public enum CharCategory {
	DIGIT(new CharRangeSearcherMutableImpl('0', '9').toImmutable()),
	ALPHA_UPPER(new CharRangeSearcherMutableImpl('A', 'Z').toImmutable()),
	ALPHA_LOWER(new CharRangeSearcherMutableImpl('a', 'z').toImmutable()),
	ALPHA_UPPER_OR_LOWER(new CharRangeSearcherMutableImpl('A', 'Z', 'a', 'z').toImmutable()),
	WHITESPACE(new CharSearcherMutable((char)32/*space (' ')*/, (char)9/*horizontal tab*/, (char)12/*form feed*/,
			(char)10/*line feed*/, (char)13/*carriage return*/ /*TODO handle CR LF combinations*/).toImmutable()),
	LINE_TERMINATOR(new CharSearcherMutable((char)10/*line feed*/,
			(char)13/*carriage return*/ /*TODO handle CR LF combinations*/).toImmutable()),
	HEXADECIMAL(new CharRangeSearcherMutableImpl('0', '9', 'A', 'F', 'a', 'f').toImmutable());

	private CharSearcher charSearcher;


	private CharCategory(CharSearcher charSearcher) {
		this.charSearcher = charSearcher;
	}


	public CharSearcher getCharCondition() {
		return charSearcher;
	}

}
