package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

		args.invalidOptionCheck = false;
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
	public void testParseWithInvalidValueOptions() {
		String[] arguments = { "-N", "-T" };
		
		args.invalidOptionCheck = true;
		args.registerAcceptableOption("N", ArgType.NUM, null);
		args.parseArgs(arguments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithIncompleteValueOptionsAtLast() {
		String[] arguments = { "-test" };

		args.registerAcceptableOption("test", ArgType.NUM, null);
		args.parseArgs(arguments);
	}
	
	@Test
	public void testParseWithDoubleQuotedStringInline() {
		String arguments = "Desktop/\"hello world\"/file.txt";
		
		args.parseArgs(arguments.split(" "));
		
		assertEquals("Desktop/hello world/file.txt", args.getParam(0));
		assertEquals("Desktop/hello world/file.txt", args.getArgument(0));
	}
	
	@Test
	public void testParseWithDoubleQuotedStringInline2() {
		String arguments = "Des\"ktop/\"file.txt Desk\"top\"/\"file.txt\"";
		
		args.parseArgs(arguments.split(" "));
		
		assertEquals("Desktop/file.txt", args.getParam(0));
		assertEquals("Desktop/file.txt", args.getArgument(0));
		assertEquals("Desktop/file.txt", args.getParam(1));
		assertEquals("Desktop/file.txt", args.getArgument(1));
	}
	
	@Test
	public void testParseWithDoubleQuotedStringStartEnd() {
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
	public void testSplitEmptyLine() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "";
		String cmd = ArgList.split(line, arguments);

		assertEquals("", cmd);
		assertArrayEquals(new String[] {},  arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitLineDashed() {
		ArrayList<String> arguments = new ArrayList<String>();

		// input: ls -a a\a\\a a\\\b a\\\\c
		String line = "ls -a a\\a\\\\a b\\\\\\b c\\\\\\\\c";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "aa\\a", "b\\b", "c\\\\c" },  arguments.toArray(new String[0]));
	}
	
	@Test
	public void testSplitLineDashedSpace() {
		ArrayList<String> arguments = new ArrayList<String>();

		// input: ls -a a\ a b\\ c d\\\ d e\\\\ f
		String line = "ls -a a\\ a b\\\\ c d\\\\\\ d e\\\\\\\\ f";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "a a", "b\\", "c", "d\\ d", "e\\\\", "f" },  arguments.toArray(new String[0]));
	}
	
	@Test
	public void testSplitLineWithQuote() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "ls -a \"hello world\"";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "\"hello", "world\"" },  arguments.toArray(new String[0]));
	}
	
}