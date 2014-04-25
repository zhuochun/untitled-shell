package sg.edu.nus.comp.cs4218.impl.hackathon;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.extended1.PIPINGTool;

public class HackathonTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 5.1
	 * Test Case: Tests the -c flag case of sort on multiple files (3) (See Appendix (5.1))
	 * Buggy Source Code: public String execute();
	 * Description: Status code expected not 0, actual result 0 - only one file
	 * 				can be passed as an argument when sort is called with -c flag
	 */
	@Test
	public void test_5_1() {
		fail("Not yet implemented");
	}

	/**
	 * 5.2
	 * Test Case: Tests the -c flag case of sort with stdin (See Appendix (5.2))
	 * Buggy Source Code: public String execute();
	 * Description:
	 *  Expected: sort: pasteTest3.txt:2 disorder: dolor sit amet
	 *  Actual: [empty string]
	 * The disorder should be found in provided file, but is not found. (See Appendix (5.0))
	 */
	@Test
	public void test_5_2() {
		fail("Not yet implemented");
	}

	/**
	 * 6.1
	 * Test Case: N/A
	 * Buggy Source Code: public String pasteUseDelimiter();
	 * Description: delimiter should only be in between the columns, not after all columns
	 *              this is a complete showstopper bug as method does not work as expected
	 */
	@Test
	public void test_6_1() {
		fail("Not yet implemented");
	}

	/**
	 * 7.1
	 * Test Case:  Execute Uniq Tool Without Any Option Using The Above File (See Appendix 7.1))
	 * Buggy Source Code: public String getUnique(boolean checkCase, String input)
	 */
	@Test
	public void test_7_1() {
		fail("Not yet implemented");
	}

	/**
	 * 8.1
	 * Test Case:  Execute Wc Tool Without Any Option Using The Above File (See Appendix 8.1)
	 * Buggy Source Code: public String execute(File workingDir, String stdin)
	 * Description: Executing wc on the above file result in “null”, whereas we expect to receive the information regarding the number of character, word, and new line.
	 */
	@Test
	public void test_8_1() {
		fail("Not yet implemented");
	}

	/**
	 * 12.1
	 * Test Case: Creating piping tool with echo '1' | cut -c 1 - (See Appendix 12.1)
	 * Buggy Source Code: public void parseArgs(String[] args)
	 * Description: File not exist. This should not read a file.
	 */
	@Test
	public void test_12_1() {
		// note: quotation marks are removed before passing in as arguments
		String[] args = "echo 1 | cut -c 1 -".split(" ");
		PIPINGTool pipe = new PIPINGTool(args);

		String stdout = pipe.execute(null, null);
		
		assertEquals(0, pipe.getStatusCode());
		assertEquals("1\n", stdout);
	}

}
