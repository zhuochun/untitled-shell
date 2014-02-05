package sg.edu.nus.comp.cs4218.impl.extended1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GREPToolTest {
	
	private GREPTool grep;

	@Before
	public void setUp() throws Exception {
		grep = new GREPTool(null);
	}

	@After
	public void tearDown() throws Exception {
		grep = null;
	}
	
	@Test
	public void testExecuteWithInvalidArgs() {
		grep = new GREPTool(new String[] { "-D" });
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
	public void testGetCountOfMatchingLines() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOnlyMatchingLines() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMatchingLinesWithTrailingContext() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMatchingLinesWithLeadingContext() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMatchingLinesWithOutputContext() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMatchingLinesOnlyMatchingPart() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNonMatchingLines() {
		fail("Not yet implemented");
	}

}
