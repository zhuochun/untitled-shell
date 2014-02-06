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
	
	public void testParseWithOptionAfterParamsNoCheck() {
		String arguments = "test.txt -c";

		args.optionsFirstCheck = false;
		args.parseArgs(arguments.split(" "));

		assertArrayEquals(arguments.split(" "), args.getArguments());
		assertArrayEquals("-c".split(" "), args.getInvalidOptions());
	}
	
	@Test
	public void testParseWithParamsAndOptions() {
		String[] arguments = { "-test", "", "-1", "--", "\'h", "we\'", ">", "|", "-", "test" };

		args.registerAcceptableOption("test", null);
		args.parseArgs(arguments);
		
		assertFalse(args.isEmpty());
		assertArrayEquals(new String[] { "-test", "-1", "--", "h we", ">", "|", "-", "test" }, args.getArguments());
		assertArrayEquals(new String[] { "h we", ">", "|", "-", "test" }, args.getParams());
		assertArrayEquals(new String[] { "test" }, args.getOptions());
		assertArrayEquals(new String[] { "1", "-" }, args.getInvalidOptions());
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
		assertEquals("quote string", args.getArguments()[0]);
	}

	@Test
	public void testParseWithSingleQuotedOptions() {
		String arguments = "'single \"quote\" string'";
		
		args.parseArgs(arguments.split(" "));
		
		assertEquals("single \"quote\" string", args.getParams()[0]);
		assertEquals("single \"quote\" string", args.getArguments()[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithIncompleteDoubleQuotedOptions() {
		String[] arguments = { "\"quote" };
		args.parseArgs(arguments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithInvalidOptions() {
		args.invalidOptionCheck = true;
		String[] arguments = { "-quote", "-well" };
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
		String[] arguments = ArgList.split("ls -a test\\ hello.txt");
		assertArrayEquals(new String[] { "ls", "-a", "test hello.txt" }, arguments);
	}
	
}
