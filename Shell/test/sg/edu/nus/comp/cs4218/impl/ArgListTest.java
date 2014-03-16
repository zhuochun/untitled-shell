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
		
		assertTrue(args.hasOptions());
		assertArrayEquals(new String[] { "c", "b" }, args.getOptions());
		assertTrue(args.hasParams());
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

		assertFalse(args.hasOptions());
		assertArrayEquals(arguments.split(" "), args.getArguments());
		assertArrayEquals("-c".split(" "), args.getInvalidOptions());
	}
	
	@Test
	public void testParseWithParamsAndOptions() {
		String[] arguments = { "-test", "", "-1", "--", "\'h we\'", ">", "|", "-", "test" };

		args.invalidOptionCheck = false;
		args.optionsFirstCheck = false;
		args.registerAcceptableOption("test", null);

		args.parseArgs(arguments);
		
		assertFalse(args.isEmpty());
		assertTrue(args.hasArgument("-test"));
		assertTrue(args.hasOptions());
		assertTrue(args.hasOption("test"));
		assertTrue(args.hasInvalidOptions());
		assertEquals("test", args.getOption(0));

		assertArrayEquals(new String[] { "-test", "-1", "--", "h we", ">", "|", "-", "test" }, args.getArguments());
		assertArrayEquals(new String[] { "--", "h we", ">", "|", "-", "test" }, args.getParams());
		assertArrayEquals(new String[] { "test" }, args.getOptions());
		assertArrayEquals(new String[] { "1" }, args.getInvalidOptions());
	}
	
	@Test
	public void testParseWithDuplicatedOptions() {
		String[] arguments = { "-N", "12", "-N", "5" };

		args.registerAcceptableOption("N", ArgType.NUM, null);
		args.parseArgs(arguments);
		
		assertFalse(args.hasInvalidOptions());
		assertEquals("5", args.getOptionValue("N"));
		assertArrayEquals(new String[] { "N" }, args.getOptions());
	}

	@Test
	public void testParseWithValueOptions() {
		String[] arguments = { "-N", "12", "-T", "-S", "tt", "-t", "aa", "-" };
		
		args.registerAcceptableOption("T", ArgType.RAW, null);
		args.registerAcceptableOption("N", ArgType.NUM, null);
		args.registerAcceptableOption("S", ArgType.STRING, null);
		args.registerAcceptableOption("t", null, null);
		args.parseArgs(arguments);
		
		assertTrue(args.hasParam("-"));
		assertFalse(args.hasInvalidOptions());
		assertEquals("12", args.getOptionValue("N"));
		assertEquals(null, args.getOptionValue("T"));
		assertEquals("tt", args.getOptionValue("S"));
		assertEquals("aa", args.getOptionValue("t"));
		assertArrayEquals(new String[] { "N", "T", "S", "t" }, args.getOptions());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithInvalidNumValueOptions() {
		String[] arguments = { "-N", "-T" };
		
		args.invalidOptionCheck = true;
		args.registerAcceptableOption("N", ArgType.NUM, null);
		args.parseArgs(arguments);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseWithInvalidStringValueOptions() {
		String[] arguments = { "-N", "'T" };
		
		args.invalidOptionCheck = true;
		args.registerAcceptableOption("N", ArgType.STRING, null);
		args.parseArgs(arguments);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseWithInvalidStringValueOptions2() {
		String[] arguments = { "-N", "\"T\"" };
		
		args.invalidOptionCheck = true;
		args.registerAcceptableOption("N", ArgType.STRING, null);
		args.parseArgs(arguments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithIncompleteNumValueOptionsAtLast() {
		String[] arguments = { "-test" };

		args.registerAcceptableOption("test", ArgType.NUM, null);
		args.parseArgs(arguments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseWithIncompleteStringValueOptionsAtLast() {
		String[] arguments = { "-test" };

		args.registerAcceptableOption("test", ArgType.STRING, null);
		args.parseArgs(arguments);
	}
	
	@Test
	public void testParseWithDoubleQuotedStringInline() {
		String arguments = "Des\"ktop/\"file.txt Desk'top'/\"file.txt\"";
		
		args.parseArgs(arguments.split(" "));
		
		assertEquals("Desktop/file.txt", args.getParam(0));
		assertEquals("Desktop/file.txt", args.getArgument(0));
		assertEquals("Desktop/file.txt", args.getParam(1));
		assertEquals("Desktop/file.txt", args.getArgument(1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseWithImcompleteDoubleQuotedStringInline() {
		String arguments = "Des\"ktop/file.txt";
		args.parseArgs(arguments.split(" "));
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
		assertArrayEquals(new String[] {}, arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitBlankLine() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "     ";
		String cmd = ArgList.split(line, arguments);

		assertEquals("", cmd);
		assertArrayEquals(new String[] {}, arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitLineDashedSpace() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "ls -a a\\ a b\\\\ c d\\\\\\ d e\\\\\\\\ f g\\\\\\\\\\ g";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "a\\ a", "b\\\\", "c", "d\\\\\\ d", "e\\\\\\\\", "f", "g\\\\\\\\\\ g" },
				arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitLineTrailingSpace() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "ls -a a\\ ";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "a\\ "},
				arguments.toArray(new String[0]));
	}
	
	@Test
	public void testSplitLineWithQuote() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "ls -a \"he\"ll\"o' world\"";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "\"he\"ll\"o' world\"" },  arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitLineWithSingleQuote() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "ls -a 'hello\" world' t'es't te''st";
		String cmd = ArgList.split(line, arguments);

		assertEquals("ls", cmd);
		assertArrayEquals(new String[] { "-a", "'hello\" world'", "t'es't", "te''st" },  arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitWithPipe() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "\"test1 | test2\" | abc";
		String cmd = ArgList.split(line, arguments);

		assertEquals("pipe", cmd);
		assertArrayEquals(new String[] { "\"test1 | test2\"", "|", "abc" },  arguments.toArray(new String[0]));
	}

	@Test
	public void testSplitWithInvalidPipe() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "\"test1 | test2\" abc";
		String cmd = ArgList.split(line, arguments);

		assertEquals("\"test1 | test2\"", cmd);
		assertArrayEquals(new String[] { "abc" },  arguments.toArray(new String[0]));
	}
	
	@Test
	public void testSplitWithPipeInOption() {
		ArrayList<String> arguments = new ArrayList<String>();

		String line = "test -a \\| test2";
		String cmd = ArgList.split(line, arguments);

		assertEquals("test", cmd);
		assertArrayEquals(new String[] { "-a", "\\|", "test2" },  arguments.toArray(new String[0]));
	}
	
}