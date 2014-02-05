package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;

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

		args.registerAcceptableOption("a", "a");
		args.registerAcceptableOption("b", ArgType.NUM, "b");
		
		assertEquals(2, args.getAcceptableOptions().length);
		assertEquals("-a : a", args.getAcceptableOptions()[0].toString());
		assertEquals("-b NUM : b", args.getAcceptableOptions()[1].toString());
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
	public void testParseSequenceInOrder() {
		String[] arguments = { "-c", "-b", "-", "test" };
		
		args.registerAcceptableOption("b", null);
		args.registerAcceptableOption("c", null);
		args.parseArgs(arguments);
		
		assertArrayEquals(new String[] { "c", "b" }, args.getOptions());
		assertArrayEquals(new String[] { "-", "test" }, args.getParams());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithOptionAfterParams() {
		String arguments = "test.txt -c";
		args.parseArgs(arguments.split(" "));
	}
	
	@Test
	public void testParseWithParamsAndOptions() {
		String[] arguments = { "\r\n", "-test", "", "-1", "--", "-", "test" };

		args.registerAcceptableOption("test", null);
		args.parseArgs(arguments);
		
		assertFalse(args.isEmpty());
		assertEquals(5, args.getArguments().length);
		assertTrue(args.hasParams());
		assertEquals(2, args.getParams().length);
		assertTrue(args.hasOptions());
		assertEquals(1, args.getOptions().length);
		assertTrue(args.hasInvalidOptions());
		assertEquals(2, args.getInvalidOptions().length);
	}

	@Test
	public void testParseWithValueOptions() {
		String[] arguments = { "-N", "12", "-T", "-S", "tt", "-" };
		
		args.registerAcceptableOption("T", ArgType.RAW, null);
		args.registerAcceptableOption("N", ArgType.NUM, null);
		args.registerAcceptableOption("S", ArgType.NUM, null);
		args.parseArgs(arguments);
		
		assertEquals("12", args.getOptionValue("N"));
		assertEquals(null, args.getOptionValue("T"));
		assertEquals("tt", args.getOptionValue("S"));
		assertArrayEquals(new String[] { "N", "T", "S" }, args.getOptions());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithIncompleteValueOptionsAtLast() {
		String[] arguments = { "-test" };

		args.registerAcceptableOption("test", ArgType.NUM, null);
		args.parseArgs(arguments);
	}
	
	@Test
	public void testParseWithDoubleQuotedOptions() {
		String arguments = "\"quote string\"";
		
		args.parseArgs(arguments.split(" "));
		
		assertEquals("quote string", args.getParams()[0]);
		assertEquals(arguments, args.getArguments()[0]);
	}

	@Test
	public void testParseWithSingleQuotedOptions() {
		String arguments = "'single \"quote\" string'";
		
		args.parseArgs(arguments.split(" "));
		
		assertEquals("single \"quote\" string", args.getParams()[0]);
		assertEquals(arguments, args.getArguments()[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithIncompleteDoubleQuotedOptions() {
		String[] arguments = { "\"quote" };
		args.parseArgs(arguments);
	}
	
	@Test
	public void testSplitLineNormal() {
		String[] arguments = ArgList.split("ls -a ab\\r\\n d\\t ef");
		assertArrayEquals(new String[] { "ls", "-a", "abrn", "dt", "ef" },  arguments);
	}
	
	@Test
	public void testSplitLineWithQuote() {
		String[] arguments = ArgList.split("ls \"hello world.txt\"");
		assertArrayEquals(new String[] { "ls", "\"hello", "world.txt\""},  arguments);
	}
	
	@Test
	public void testSplitLineWithDashSpace() {
		// TODO fix me
		String[] arguments = ArgList.split("ls -a test\\ hello.txt");
		assertArrayEquals(new String[] { "ls", "-a", "test hello.txt" }, arguments);
	}
	
}
