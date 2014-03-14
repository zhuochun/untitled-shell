package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.RangeUtils.Range;

public class RangeUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private void assertRangeListEqual(ArrayList<Range> expected,
			  ArrayList<Range> actual) {
		for (int i = 0; i < expected.size(); i ++) {
			assertEquals(expected.get(i).left, actual.get(i).left);
			assertEquals(expected.get(i).right, actual.get(i).right);
		}
	}

	@Test
	public void testRangeDefaultConstructor() {
		Range r = new RangeUtils().new Range();
		
		assertEquals(0, r.left);
		assertEquals(0, r.right);
	}

	@Test
	public void testRangeConstructorWithTwoIntAndLeftLessThanRight() {
		Range r = new RangeUtils().new Range(1, 2);
		
		assertEquals(1, r.left);
		assertEquals(2, r.right);
	}
	
	@Test
	public void testRangeConstructorWithTwoIntAndLeftEqualsRight() {
		Range r = new RangeUtils().new Range(1, 1);
		
		assertEquals(1, r.left);
		assertEquals(1, r.right);
	}
	
	@Test
	public void testRangeConstructorWithTwoIntAndLeftGreaterThanRight() {
		try {
			new RangeUtils().new Range(2, 1);
		} catch (InvalidParameterException e) {
			// if the correct exception is caught, it passes the test
			assertTrue(true);
		} catch (Exception e) {
			// otherwise the test case fail
			assertTrue(false);
		}
	}
	
	@Test
	public void testRangeConstructorWithARange() {
		Range r = new RangeUtils().new Range(1, 2);
		Range nr = new RangeUtils().new Range(r);
		
		assertEquals(1, nr.left);
		assertEquals(2, nr.right);
	}
	
	@Test
	public void testParseRange() {
		String rawList = "1-2,3-4,5-7";
		ArrayList<Range> range = RangeUtils.parseRange(rawList);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 2));
		expected.add(new RangeUtils().new Range(3, 4));
		expected.add(new RangeUtils().new Range(5, 7));
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testParseRangeOutOfOrder() {
		String rawList = "1-2,11-19,3-4,5-7";
		ArrayList<Range> range = RangeUtils.parseRange(rawList);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 2));
		expected.add(new RangeUtils().new Range(11, 19));
		expected.add(new RangeUtils().new Range(3, 4));
		expected.add(new RangeUtils().new Range(5, 7));
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testParseRangeWithMultipleDashesInOneRange() {
		String rawList = "1--2,3-4,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("LIST in wrong format!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testParseRangeWithMultipleDashesInOneRangeAndNotTheFirstRange() {
		String rawList = "1-2,3--4,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("LIST in wrong format!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testParseRangeWithSingleValue() {
		String rawList = "12,3-4,5-7";
		ArrayList<Range> range = RangeUtils.parseRange(rawList);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(12, 12));
		expected.add(new RangeUtils().new Range(3, 4));
		expected.add(new RangeUtils().new Range(5, 7));
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testParseRangeWithMultipleCommas() {
		String rawList = "1-2,,,,3-4,,,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("LIST in wrong format!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testParseRangeWithInvalidRange() {
		String rawList = "1-2,4-3,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid Range!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testParseRangeWithWrongNumberFormat() {
		String rawList = "1-2,a-b,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("Numbers in wrong format!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testMergeRangeWithDisjointRanges() {
		Range r1 = new RangeUtils().new Range(1, 2);
		Range r2 = new RangeUtils().new Range(3, 4);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(r1);
		expected.add(r2);
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithDisjointRangesOutOfOrder() {
		Range r1 = new RangeUtils().new Range(3, 4);
		Range r2 = new RangeUtils().new Range(1, 2);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(r2);
		expected.add(r1);
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRanges() {
		Range r1 = new RangeUtils().new Range(1, 3);
		Range r2 = new RangeUtils().new Range(2, 4);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRangesOutOfOrder() {
		Range r1 = new RangeUtils().new Range(2, 4);
		Range r2 = new RangeUtils().new Range(1, 3);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRangesWithBoundaryOverlap() {
		Range r1 = new RangeUtils().new Range(1, 2);
		Range r2 = new RangeUtils().new Range(2, 4);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRangesWithBoundaryOverlapOutOfOrder() {
		Range r1 = new RangeUtils().new Range(2, 4);
		Range r2 = new RangeUtils().new Range(1, 2);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRangesWithOverlappingRanges() {
		Range r1 = new RangeUtils().new Range(1, 4);
		Range r2 = new RangeUtils().new Range(2, 3);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRangesWithOverlappingRangesOutOfOrder() {
		Range r1 = new RangeUtils().new Range(2, 3);
		Range r2 = new RangeUtils().new Range(1, 4);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithIntersectingRangesWithIdenticalRanges() {
		Range r1 = new RangeUtils().new Range(1, 4);
		Range r2 = new RangeUtils().new Range(1, 4);
		
		ArrayList<Range> range = RangeUtils.mergeRange(r1, r2);
		ArrayList<Range> expected = new ArrayList<Range>();
		
		expected.add(new RangeUtils().new Range(1, 4));
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
	
	@Test
	public void testMergeRangeWithNull() {
		Range r1 = new RangeUtils().new Range(1, 4);
		ArrayList<Range> range = RangeUtils.mergeRange(r1, null);
		
		assertEquals(range, null);
		
		range = RangeUtils.mergeRange(null, r1);
		
		assertEquals(range, null);
		
		range = RangeUtils.mergeRange(null, null);
		
		assertEquals(range, null);
	}
	
	@Test
	public void testMergeRangeList() {
		ArrayList<Range> range = new ArrayList<Range>();
		ArrayList<Range> expected = new ArrayList<Range>();
		
		range.add(new RangeUtils().new Range(2, 4));
		range.add(new RangeUtils().new Range(1, 4));
		range.add(new RangeUtils().new Range(3, 6));
		range.add(new RangeUtils().new Range(7, 10));
		range.add(new RangeUtils().new Range(4, 5));
		
		expected.add(new RangeUtils().new Range(1, 6));
		expected.add(new RangeUtils().new Range(7, 10));
		
		range = RangeUtils.mergeRange(range);
		
		assertEquals(expected.size(), range.size());
		
		assertRangeListEqual(expected, range);
	}
}
