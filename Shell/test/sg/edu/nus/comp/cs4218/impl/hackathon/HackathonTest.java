package sg.edu.nus.comp.cs4218.impl.hackathon;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;
import sg.edu.nus.comp.cs4218.extended2.IWcTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.impl.extended1.PIPINGTool;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTETool;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTTool;
import sg.edu.nus.comp.cs4218.impl.extended2.UNIQTool;
import sg.edu.nus.comp.cs4218.impl.extended2.WCTool;

public class HackathonTest {
	private ISortTool sortTool;
	private IWcTool wcTool;
	File testData3;
	File myFile2;
	File tmpFile1;
	File tmpFile2;

	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	}
	
	@Before
	public void before() throws IOException {
		callBeforeSort();
		callBeforeWC();
	}

	@After
	public void after() {
		callAfterSort();
		callAfterWC();
	}
	
	public void callBeforeSort() throws IOException {
		sortTool = new SORTTool(null);
		// creating testFile of unsorted order
		testData3 = new File("testData3.txt");
		testData3.createNewFile();
		writeFile("testData3.txt", "lorem ipsum\ndolor sit amet");

		// creating testFile of sorted order
		myFile2 = new File("sortFile.txt");
		myFile2.createNewFile();
		writeFile("sortFile.txt", "aaa\r\nbbb\r\nccc\r\nddd\r\neee");
	}
	
	public void callAfterSort(){
		sortTool = null;
		File file1 = new File("sortFile.txt");
		if (file1.exists()) {
			file1.delete();
		}

		File file2 = new File("testData3.txt");
		if (file2.exists()) {
			file2.delete();
		}
	}
	
	public void callBeforeWC() throws IOException{
		wcTool = new WCTool(null);
		tmpFile1 = new File("tmpFile1.txt");
		tmpFile1.createNewFile();
		writeFile("tmpFile1.txt", "hello world");
		
		tmpFile2 = new File("tmpFile2.txt");
		tmpFile2.createNewFile();
		writeFile("tmpFile2.txt", "hello world");
	}
	
	public void callAfterWC(){
		wcTool = null;
		File file1 = new File("tmpFile1.txt");
		if (file1.exists()) {
			file1.delete();
		}

		File file2 = new File("tmpFile2.txt");
		if (file2.exists()) {
			file2.delete();
		}
	}

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
	public void checkIfSortedForMultipleFile(){
		ISortTool newSortTool = new SORTTool(new String[]{"-c", "sortFile.txt","testData3.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result,newSortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/" + "sortFile.txt"));
	}

    @Test
	public void sortedTestForMultipleFile(){
		ISortTool newSortTool = new SORTTool(new String[]{"sortFile.txt","testData3.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, newSortTool.sortFile("aaa\r\nbbb\r\nccc\r\nddd\r\neee"));
	}
	
	@Test
	public void sortedTestForMultipleFileTwo(){
		ISortTool newSortTool = new SORTTool(new String[]{"testData3.txt", "sortFile.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, newSortTool.sortFile("lorem ipsum\ndolor sit amet"));
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
	public void checkIfSortedTestForUnsortedFile() {// the input is file, function input in a string recheck
		String result = sortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/" + "testData3.txt");
		assertEquals(result, "sort: " +"testData3.txt:2 disorder: dolor sit amet\n");
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
	 * @throws IOException 
	 */
	@Test
	public void test_7_1() throws IOException {
		String content = "not dummy file content\n"
				+ "a dummy file content\n"
				+ "a dummy file content\n"
				+ "A DUMMY FILE CONTENT\n"
				+ "NOT DUMMY FILE CONTENT\n"
				+ "The End\n";

		String expected = "not dummy file content\n"
				+ "a dummy file content\n"
				+ "A DUMMY FILE CONTENT\n"
				+ "NOT DUMMY FILE CONTENT\n"
				+ "The End\n";

		createFile("test.txt", content);
		
		UNIQTool uniq = new UNIQTool("test.txt".split(" "));
		String stdout = uniq.execute(folder.getRoot(), null);

		assertEquals(expected, stdout);
	}

	/**
	 * 7.1 Extended Case Sensitive
	 */
	@Test
	public void test_7_1_case() throws IOException {
		String content = "not dummy file content\n"
				+ "a dummy file content\n"
				+ "a dummy file content\n"
				+ "A DUMMY FILE CONTENT\n"
				+ "NOT DUMMY FILE CONTENT\n"
				+ "The End\n";

		String expected = "not dummy file content\n"
				+ "a dummy file content\n"
				+ "NOT DUMMY FILE CONTENT\n"
				+ "The End\n";

		createFile("test.txt", content);
		
		UNIQTool uniq = new UNIQTool("-i test.txt".split(" "));
		String stdout = uniq.execute(folder.getRoot(), null);

		assertEquals(expected, stdout);
	}

	/**
	 * 8.1
	 * Test Case:  Execute Wc Tool Without Any Option Using The Above File (See Appendix 8.1)
	 * Buggy Source Code: public String execute(File workingDir, String stdin)
	 * Description: Executing wc on the above file result in “null”, whereas we expect to receive the information regarding the number of character, word, and new line.
	 */
	@Test
	public void testHasParamButNoOption(){
		IWcTool newWCTool = new WCTool(new String[]{"", "tmpFile1.txt"});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		String expectedResult = wcTool.getCharacterCount("hello world")+" "+ 
		wcTool.getWordCount("hello world") + " " +
		wcTool.getNewLineCount("hello world");		
		assertEquals(result, expectedResult);
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
