package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArgListTest {
	
	ArgList args;

	@Before
	public void setUp() throws Exception {
		args = new ArgList();
	}

	@After
	public void tearDown() throws Exception {
		args = null;
	}

	@Test
	public void testRegisterAcceptableOption() {
		assertEquals(0, args.getAcceptableOptions().length);

		args.registerAcceptableOption("a");
		args.registerAcceptableOption("b");
		args.registerAcceptableOption("a");
		
		assertEquals(2, args.getAcceptableOptions().length);
	}
	
	@Test
	public void testParseNullArgs() {
		args.parseArgs(null);

		assertTrue(args.isEmpty());
		assertFalse(args.hasParams());
		assertFalse(args.hasOptions());
		assertFalse(args.hasInvalidOptions());
	}
	
	@Test
	public void testParseZeroArgs() {
		args.parseArgs(new String[0]);

		assertTrue(args.isEmpty());
		assertFalse(args.hasParams());
		assertFalse(args.hasOptions());
		assertFalse(args.hasInvalidOptions());
	}
	
	@Test
	public void testParseWithParamsAndOptions() {
		String[] arguments = { "\r\n", "test", "", "-", "-test", "-1", "--" };

		args.registerAcceptableOption("test");
		args.parseArgs(arguments);
		
		assertFalse(args.isEmpty());
		assertEquals(3, args.getArguments().length);
		assertTrue(args.hasParams());
		assertEquals(2, args.getParams().length);
		assertTrue(args.hasOptions());
		assertEquals(1, args.getOptions().length);
		assertTrue(args.hasInvalidOptions());
		assertEquals(2, args.getInvalidOptions().length);
	}
	
}
