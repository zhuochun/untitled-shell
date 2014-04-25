package sg.edu.nus.comp.cs4218.impl.hackathon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.impl.extended1.PIPINGTool;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTETool;

public class HackathonTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

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
	 *              this is a complete show stopper bug as method does not work as expected
	 * @throws IOException 
	 */
	@Test
	public void test_6_1() throws IOException {
		createFile("test1.txt", "1\n2\n3\n");
		createFile("test2.txt", "a\nb\n");
		createFile("test3.txt", "1\n2\n3\n4\n");

		PASTETool pasteTool = new PASTETool(
				"-d ; test1.txt test2.txt test3.txt".split(" "));
		String stdout = pasteTool.execute(folder.getRoot(), null);

		assertEquals("1;a;1\n2;b;2\n3;;3\n;;4\n", stdout);		
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

	private File createFile(String filename, String content) throws IOException {
		File file = folder.newFile(filename);

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();

        return file;
	}
}
