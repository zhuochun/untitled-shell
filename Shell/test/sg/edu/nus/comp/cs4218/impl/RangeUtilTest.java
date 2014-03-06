package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RangeUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRangeDefaultConstructor() {
		RangeUtils.Range r = new RangeUtils().new Range();
		
		assertEquals(0, r.left);
		assertEquals(0, r.right);
	}

	@Test
	public void testRangeConstructorWithTwoIntAndLeftLessThanRight() {
		RangeUtils.Range r = new RangeUtils().new Range(1, 2);
		
		assertEquals(1, r.left);
		assertEquals(2, r.right);
	}
	
	@Test
	public void testRangeConstructorWithTwoIntAndLeftEqualsRight() {
		RangeUtils.Range r = new RangeUtils().new Range(1, 1);
		
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
		RangeUtils.Range r = new RangeUtils().new Range(1, 2);
		RangeUtils.Range nr = new RangeUtils().new Range(r);
		
		assertEquals(1, nr.left);
		assertEquals(2, nr.right);
	}
	
	@Test
	public void testParseRange() {
		String rawList = "1-2,3-4,5-7";
		ArrayList<RangeUtils.Range> range = RangeUtils.parseRange(rawList);
		ArrayList<RangeUtils.Range> expected = new ArrayList<RangeUtils.Range>();
		
		expected.add(new RangeUtils().new Range(1, 2));
		expected.add(new RangeUtils().new Range(3, 4));
		expected.add(new RangeUtils().new Range(5, 7));
		
		for (int i = 0; i < expected.size(); i ++) {
			assertTrue(range.get(i).left == expected.get(i).left);
			assertTrue(range.get(i).right == expected.get(i).right);
		}
	}
	
	@Test
	public void testParseRangeOutOfOrder() {
		String rawList = "1-2,11-19,3-4,5-7";
		ArrayList<RangeUtils.Range> range = RangeUtils.parseRange(rawList);
		ArrayList<RangeUtils.Range> expected = new ArrayList<RangeUtils.Range>();
		
		expected.add(new RangeUtils().new Range(1, 2));
		expected.add(new RangeUtils().new Range(11, 19));
		expected.add(new RangeUtils().new Range(3, 4));
		expected.add(new RangeUtils().new Range(5, 7));
		
		for (int i = 0; i < expected.size(); i ++) {
			assertTrue(range.get(i).left == expected.get(i).left);
			assertTrue(range.get(i).right == expected.get(i).right);
		}
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
	public void testParseRangeWithNoDash() {
		String rawList = "12,3-4,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("LIST in wrong format!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testParseRangeWithNoDashAndNotTheFirstRange() {
		String rawList = "1-2,34,5-7";
		try {
			RangeUtils.parseRange(rawList);
		} catch (IllegalArgumentException e) {
			assertEquals("LIST in wrong format!", e.getMessage());
		} catch (Exception ex) {
			assertTrue(false);
		}
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
}
