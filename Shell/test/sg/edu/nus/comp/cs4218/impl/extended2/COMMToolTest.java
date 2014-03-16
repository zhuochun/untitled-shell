package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class COMMToolTest {

	private static ICommTool commTool; 
	private String helpString;
	private static String testFile1 = "testFile1.txt";
	private static String testFile2 = "testFile2.txt";
	private static String testFile3 = "testFile3.txt";
	private static String sortedFileName = "sortedFile.txt";
	private static String unSortedFileName = "unSortedFile.txt";
	private static String nonExistFile = "askldjflksdfj.txt";

	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	} 

	@BeforeClass 
	public static void executeThisBeforeClass() throws IOException{

		File myFile1 = new File(testFile1);
		myFile1.createNewFile();
		writeFile("testFile1.txt", "aaa\r\nbbb\r\nccc\r\nddd");

		File myFile2 = new File(testFile2);
		myFile2.createNewFile();
		writeFile("testFile2.txt", "aaf\r\nabb\r\nccc\r\nfff");

		//testFile 3 will be the file in unsorted order 
		File myFile3 = new File(testFile3);
		myFile3.createNewFile();
		writeFile("testFile3.txt", "zzz\r\nccc\r\naaa\r\nbbb");
		
		// another sorted file
		File sortedFile = new File(sortedFileName);
		sortedFile.createNewFile();
		writeFile("sortedFile.txt", "aaa\ncaa\nccc\ndda\nddx\neee\n");

		// another unsorted file
		File unSortedFile = new File(unSortedFileName);
		unSortedFile.createNewFile();
		writeFile("unSortedFile.txt", "aaa\ncaa\ndda\nccc\nddx\neee\n");
	}

	@AfterClass 
	public static void executeThisAfterClass(){

		File myFile1 = new File(testFile1);
		myFile1.delete();

		File myFile2 = new File(testFile2);
		myFile2.delete();

		File myFile3 = new File(testFile3);
		myFile3.delete();
		
		File sortedFile = new File(sortedFileName);
		sortedFile.delete();

		File unSortedFile = new File(unSortedFileName);
		unSortedFile.delete();			
	}

	@Before
	public void before() throws IOException{
		commTool = new COMMTool(null);
		// set up get help string.
		StringBuilder sb = new StringBuilder();;
		
		sb.append("Command Format - cut [OPTIONS] [FILE]\n");
		sb.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		sb.append("OPTIONS\n");
		
		sb.append("  -c : Check that the input is correctly sorted.\n");
		sb.append("  -d : Do not check that the input is correctly sorted.\n");
		sb.append("  -help : Brief information about supported options.\n");
		
		helpString = sb.toString();
	}

	@After
	public void after(){
		commTool = null;
	}
	
	@Test
	public void getHelpTest() {
		String actual = commTool.getHelp();
		assertEquals(helpString, actual);
	}


	//test compareFiles method, with sorted file
	@Test
	public void compareFilesSortedFile() {
		String result = commTool.compareFiles("testFile1.txt", "testFile2.txt");
		assertEquals("aaa\n\taaf\n\tabb\nbbb\n\t\tccc\nddd\n\tfff\n", result);
	}

	//test compareFiles method, without sorted file
	@Test
	public void compareFilesUnSortedFile() {
		String result = commTool.compareFiles("testFile1.txt", "testFile3.txt");
		assertEquals("aaa\nbbb\nccc\nddd\n\tzzz\ncomm: File 2 is not in sorted order \n\tccc\n\taaa\n\tbbb\n", result);
	}
	
	//test compareFilesCheckSortStatus method, with sorted
	@Test
	public void compareFilesCheckSortStatusSortedFile() throws IOException { 

		String result = commTool.compareFilesCheckSortStatus("testFile1.txt", "testFile2.txt");
		assertEquals("aaa\n\taaf\n\tabb\nbbb\n\t\tccc\nddd\n\tfff\n", result);
	}


	//test compareFilesCheckSortStatus method, without sorted
	@Test
	public void compareFilesCheckSortStatusNotSortedFile() throws IOException { 
		String result = commTool.compareFilesCheckSortStatus("testFile1.txt", "testFile3.txt");
		assertEquals("aaa\nbbb\nccc\nddd\n\tzzz\ncomm: File 2 is not in sorted order \n", result);
	}
	
	//test compareFilesDoNotCheckSortStatus method, with sorted
	@Test
	public void compareFilesDoNotCheckSortStatusSortedFile() throws IOException { 

		String result = commTool.compareFilesDoNotCheckSortStatus("testFile1.txt", "testFile2.txt");
		assertEquals("aaa\n\taaf\n\tabb\nbbb\n\t\tccc\nddd\n\tfff\n", result);
	}
	
	//test compareFilesDoNotCheckSortStatus method, without sorted
	@Test
	public void compareFilesDoNotCheckSortStatusNotSortedFile() throws IOException { 
		String result = commTool.compareFilesDoNotCheckSortStatus("testFile1.txt", "testFile3.txt");
		assertEquals("aaa\nbbb\nccc\nddd\n\tzzz\n\tccc\n\taaa\n\tbbb\n", result);
	}
	
	@Test
	public void executeHelpOption() {
		commTool = new COMMTool(new String[] {"-help"});
		
		String actual = commTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionNoParamEmptyString() {
		commTool = new COMMTool(new String[] {""});
		
		String actual = commTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionNoParamNullString() {
		commTool = new COMMTool(null);
		
		String actual = commTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeInvalidOption() {
		commTool = new COMMTool(new String[] {"-asdf"});
		
		String actual = commTool.execute(null, null);
		
		assertEquals("Error: Illegal option -asdf\n" + helpString, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}

	@Test
	public void executeOptionWithoutProperContent() {
		commTool = new COMMTool(new String[] {"-c"});
		
		String actual = commTool.execute(null, null);
		
		assertEquals("Error: No file is specified!\n" + helpString, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeTwoOptionsWithoutProperContent() {
		commTool = new COMMTool(new String[] {"-c", "-d"});
		
		String actual = commTool.execute(null, null);
		
		assertEquals("Error: More than one option.\n" + helpString, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionWithIdenticalFile() {
		commTool = new COMMTool(new String[] {testFile1, testFile1});
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t\taaa\n\t\tbbb\n\t\tccc\n\t\tddd\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionWithFileAndNonExistFile() {
		commTool = new COMMTool(new String[] {testFile1, nonExistFile});
		
		String expected = "Error: No such file or directory\n";
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionWithTwoSortedFile() {
		commTool = new COMMTool(new String[] {testFile1, sortedFileName});
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t\taaa\nbbb\n\tcaa\n\t\tccc\n\tdda\nddd\n\tddx\n\teee\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionWithSortedAndUnsortedFile() {
		commTool = new COMMTool(new String[] {testFile1, testFile3});
		
		StringBuilder sb = new StringBuilder();
		sb.append("aaa\nbbb\nccc\nddd\n\tzzz\n");
		sb.append("comm: File 2 is not in sorted order \n\tccc\n\taaa\n\tbbb\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionWithUnsortedAndSortedFile() {
		commTool = new COMMTool(new String[] {testFile3, testFile1});
		
		StringBuilder sb = new StringBuilder();
		sb.append("\taaa\n\tbbb\n\tccc\n\tddd\nzzz\n");
		sb.append("comm: File 1 is not in sorted order \nccc\naaa\nbbb\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeNoOptionWithUnsortedAndUnSortedFile() {
		commTool = new COMMTool(new String[] {testFile3, unSortedFileName});
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\taaa\n\tcaa\n\tdda\n");
		sb.append("comm: File 2 is not in sorted order \n\tccc\n\tddx\n\teee\nzzz\n");
		sb.append("comm: File 1 is not in sorted order \nccc\naaa\nbbb\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeCompareWithIdenticalFile() {
		commTool = new COMMTool(new String[] {"-c", testFile1, testFile1});
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t\taaa\n\t\tbbb\n\t\tccc\n\t\tddd\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeCompareWithTwoSortedFile() {
		commTool = new COMMTool(new String[] {"-c", testFile1, sortedFileName});
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t\taaa\nbbb\n\tcaa\n\t\tccc\n\tdda\nddd\n\tddx\n\teee\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeCompareWithFileAndNonExistFile() {
		commTool = new COMMTool(new String[] {"-c", testFile1, nonExistFile});
		
		String expected = "Error: No such file or directory\n";
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeDontCheckSortWithIdenticalFile() {
		commTool = new COMMTool(new String[] {"-d", testFile1, testFile1});
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t\taaa\n\t\tbbb\n\t\tccc\n\t\tddd\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeDontCheckSortWithTwoSortedFile() {
		commTool = new COMMTool(new String[] {"-d", testFile1, sortedFileName});
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t\taaa\nbbb\n\tcaa\n\t\tccc\n\tdda\nddd\n\tddx\n\teee\n");
		
		String expected = sb.toString();
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertEquals(0, commTool.getStatusCode());
	}
	
	@Test
	public void executeDontCheckSortWithFileAndNonExistFile() {
		commTool = new COMMTool(new String[] {"-d", testFile1, nonExistFile});
		
		String expected = "Error: No such file or directory\n";
		String actual = commTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, commTool.getStatusCode());
	}
}
