package sg.edu.nus.comp.cs4218.impl.extended1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GREPToolTest {
	
	private GREPTool grep;

	private String input = "testtest\ntest world\n" +
			"123\n789\n0123\nhello\n" +
			"hello test\nyahoo\nk09lk";
	private String matchOutput = "testtest\ntest world\nhello test";
	private String matchTrailingOutput = "testtest\ntest world\n123\nhello test\nyahoo";
	private String matchLeadingOutput = "testtest\ntest world\nhello\nhello test";
	private String matchLeadingOutputAll = "testtest\ntest world\n123\n789\n0123\nhello\nhello test";
	private String matchContextOutput = "testtest\ntest world\n123\n--\n--\nhello\nhello test\nyahoo";
	private String matchContextOutputAll = "testtest\ntest world\n123\n789\n0123\nhello\n--\n" +
			"123\n789\n0123\nhello\nhello test\nyahoo\nk09lk";
	private String partMatchOutput= "test\ntest\ntest\ntest";
	private String nonMatchOutput = "123\n789\n0123\nhello\nyahoo\nk09lk";

	@Before
	public void setUp() throws Exception {
		grep = new GREPTool(null);
	}

	@After
	public void tearDown() throws Exception {
		grep = null;
	}
	
	@Test
	public void testGetCountOfMatchingLinesNoMatching() {
		int output = grep.getCountOfMatchingLines("abc", input);
		assertEquals(0, output);
	}

	@Test
	public void testGetCountOfMatchingLines() {
		int output = grep.getCountOfMatchingLines("test", input);
		assertEquals(3, output);
	}

	@Test
	public void testGetOnlyMatchingLinesWithNoMatching() {
		String output = grep.getOnlyMatchingLines("abc", input);
		assertEquals("", output);
	}

	@Test
	public void testGetOnlyMatchingLines() {
		String output = grep.getOnlyMatchingLines("test", input);
		assertEquals(matchOutput, output);
	}

	@Test
	public void testGetMatchingLinesWithTrailingContext() {
		String output = grep.getMatchingLinesWithTrailingContext(1, "test", input);
		assertEquals(matchTrailingOutput, output);
	}

	@Test
	public void testGetMatchingLinesWithTrailingContextMoreLines() {
		String output = grep.getMatchingLinesWithTrailingContext(30, "test", input);
		assertEquals(input, output);
	}

	@Test
	public void testGetMatchingLinesWithLeadingContext() {
		String output = grep.getMatchingLinesWithLeadingContext(1, "test", input);
		assertEquals(matchLeadingOutput, output);
	}

	@Test
	public void testGetMatchingLinesWithLeadingContextMoreLines() {
		String output = grep.getMatchingLinesWithLeadingContext(30, "test", input);
		assertEquals(matchLeadingOutputAll, output);
	}

	@Test
	public void testGetMatchingLinesWithOutputContext() {
		String output = grep.getMatchingLinesWithOutputContext(1, "test", input);
		assertEquals(matchContextOutput, output);
	}

	@Test
	public void testGetMatchingLinesWithOutputContextMoreLines() {
		String output = grep.getMatchingLinesWithOutputContext(30, "test", input);
		assertEquals(matchContextOutputAll, output);
	}

	@Test
	public void testGetMatchingLinesOnlyMatchingPart() {
		String output = grep.getMatchingLinesOnlyMatchingPart("test", input);
		assertEquals(partMatchOutput, output);
	}

	@Test
	public void testGetMatchingLinesOnlyMatchingPartNoMatching() {
		String output = grep.getMatchingLinesOnlyMatchingPart("abc", input);
		assertEquals("", output);
	}

	@Test
	public void testGetNonMatchingLines() {
		String output = grep.getNonMatchingLines("test", input);
		assertEquals(nonMatchOutput, output);
	}
	
	@Test
	public void testGetHelp() {
		grep = new GREPTool(new String[] { "-help" });

		String stdout = grep.execute(null, null);

		assertEquals(0, grep.getStatusCode());
		assertTrue(stdout.matches("^Command Format -(.|\n)+OPTIONS(.|\n)+$"));
	}

	@Test
	public void testGetNonMatchingLinesWithNoMatching() {
		String output = grep.getNonMatchingLines("abc", input);
		assertEquals(input, output);
	}

	@Test
	public void testExecuteWithInvalidArgs() {
		grep = new GREPTool(new String[] { "-D" });
		grep.execute(null, null);
		assertNotEquals(0, grep.getStatusCode());
	}

	@Test
	public void testExecuteWithoutPattern() {
		grep = new GREPTool(new String[] {});
		grep.execute(null, null);
		assertNotEquals(0, grep.getStatusCode());
	}
	
	@Test
	public void testExecuteWithOptionAfterFileArgs() {
		grep = new GREPTool(new String[] { "file1.txt", "-C" });
		grep.execute(null, null);
		assertNotEquals(0, grep.getStatusCode());
	}

	@Test
	public void testExecuteWithOptionWithInvalidNum() {
		grep = new GREPTool("-A ab -".split(" "));
		grep.execute(null, null);
		assertNotEquals(0, grep.getStatusCode());
	}
	
	@Test
	public void testExecuteWithStdin() {
		grep = new GREPTool("test".split(" "));
		String stdout = grep.execute(null, input);
		assertEquals(matchOutput, stdout);
	}

}
