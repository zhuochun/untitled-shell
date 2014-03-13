package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.IUniqTool;

public class UNIQToolTest {
	private IUniqTool uniqTool;
	@Before
	public void before() {
		uniqTool = new UNIQTool(null);
	}

	@After
	public void after() {
		uniqTool = null;
	}
	
	//test getUnique method
	@Test
	public void getUniqueTest() {
		String input1 = "ab cd ef";
		String input4 = " AB   cd ef";
		assertEquals(input1,uniqTool.getUnique(true, input1));
		assertEquals("",uniqTool.getUnique(false, input4));
		assertEquals(input4,uniqTool.getUnique(true, input4));
	}
	
	//test getUnique method for null
	@Test
	public void getUniqueForNullTest(){
		assertEquals("",uniqTool.getUnique(true, null));
	}
	
	//test getUniqueSkipNum method for valid range
	@Test
	public void getUniqueSkipNumValidRangeTest(){
		String input1 = "a b c d e";
		String input2 = "b  b c d e";
		assertEquals(input1,uniqTool.getUniqueSkipNum(1,true, input1));
		assertEquals(input2,uniqTool.getUniqueSkipNum(1,false, input2));
	}
	
	//test getUniqueSkipNum method for out of range
	@Test
	public void getUniqueSkipNumInvalidRangeTest(){
		String input1 = "a b c d e";
		String input2 = "b  b c d e";
		assertEquals(input1,uniqTool.getUniqueSkipNum(100,true, input1));
		assertEquals("",uniqTool.getUniqueSkipNum(100,false, input2));
	}
	
	//test getUniqueSkipNum method for null
	@Test
	public void getUniqueSkipNumForNullTest(){
		assertEquals("",uniqTool.getUniqueSkipNum(1,true, null));
	}

	@Test
	public void testGetHelp() {
		assertTrue(uniqTool.getHelp().matches("^Command Format -(.|\n)+OPTIONS(.|\n)+$"));
	}
}
