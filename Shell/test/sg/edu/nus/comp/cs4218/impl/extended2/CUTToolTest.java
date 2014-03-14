package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ICutTool;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class CUTToolTest {
	
	private ICutTool cutTool;
	private String helpString;
	private File testAbsoluteRandomFile;
	private File testRelativeFile;
	private File testCurrentFolderFile;
	private String testFileName = "test.txt";
	private String testFileNotExistName = "asdlfj.txt";
	private String testFileAbsoluteName;
	private String testFileRelativeName = ".././test.txt";
	private String testFileRelativeFromHomeName;
	
	@Before
	public void before() throws Exception {
		cutTool = new CUTTool(null);
		
		// set up get help string.
		StringBuilder sb = new StringBuilder();;
		
		sb.append("Command Format - cut [OPTIONS] [FILE]\n");
		sb.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		sb.append("OPTIONS\n");
		
		sb.append("  -c STRING : Use LIST as the list of characters to cut out.\n");
		sb.append("  -d STRING : Use DELIM as the field-separator character instead of the TAB character.\n");
		sb.append("  -help : Brief information about supported options.\n");
		
		helpString = sb.toString();
		
		testCurrentFolderFile = new File(testFileName);
		
		String testFilePath = PathUtils.getCurrentPath().toString();
		
		testFileRelativeFromHomeName = testFilePath.replaceFirst(System.getProperty("user.home"),
																 "~") + "/" + testFileName;
		testFileAbsoluteName = PathUtils.GetRandomSubpath(Paths.get(testFilePath)).toString() + "/" + testFileName;
		testAbsoluteRandomFile = new File(testFileAbsoluteName);
		
		testRelativeFile = new File(PathUtils.PathResolver(testFilePath, testFileRelativeName));
		
		BufferedWriter output = new BufferedWriter(new FileWriter(testAbsoluteRandomFile));
		
		output.write("123456789012345\n");
		output.write("askldjfklasdjfasd\n");
		output.write("1,2,3,4,5,6,7,8,9\n");
		output.write("1, 2, 3, 4, 5, 6, 7, 8, 9\n");
		
		output.close();
		
		output = new BufferedWriter(new FileWriter(testCurrentFolderFile));
		
		output.write("123456789012345\n");
		output.write("askldjfklasdjfasd\n");
		output.write("1,2,3,4,5,6,7,8,9\n");
		output.write("1, 2, 3, 4, 5, 6, 7, 8, 9\n");
		
		output.close();
		
		output = new BufferedWriter(new FileWriter(testRelativeFile));
		
		output.write("123456789012345\n");
		output.write("askldjfklasdjfasd\n");
		output.write("1,2,3,4,5,6,7,8,9\n");
		output.write("1, 2, 3, 4, 5, 6, 7, 8, 9\n");
		
		output.close();
	}

	@After
	public void after() {
		cutTool = null;
		testAbsoluteRandomFile.delete();
		testRelativeFile.delete();
		testCurrentFolderFile.delete();
	}
	
	@Test
	public void getHelpTest() {
		String actual = cutTool.getHelp();
		
		assertEquals(helpString, actual);
	}

	//Test cutSpecfiedCharacters method with valid range
	@Test
	public void cutSpecfiedCharactersListWithInRangeTest() {
		String list1 = "1,8-9,3-10";
		String input1 = "123456789012345";
		String output11 = "134567890";
		assertEquals(output11,cutTool.cutSpecfiedCharacters(list1,input1));
	}
	
	//Test cutSpecfiedCharacters method out of range
	@Test
	public void cutSpecfiedCharactersListOurOfRangeTest() {
		String list1 = "1,8-9,3-16";
		String input1 = "123456789012345";
		String output11 = "13456789012345";
		assertEquals(output11,cutTool.cutSpecfiedCharacters(list1,input1));
	}
	
	
	//Test cutSpecifiedCharactersUseDelimiter method with valid range
	@Test
	public void cutSpecifiedCharactersUseDelimiterListWithInRangeTest(){
		String list1 = "1,8-9,3-15";
		String input1 = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
		String output1 = "1 3 4 5 6 7 8 9 10 11 12 13 14 15";	
		assertEquals(output1,cutTool.cutSpecifiedCharactersUseDelimiter(list1," ",input1));

	}
	
	//Test cutSpecifiedCharactersUseDelimiter method out of range
	@Test
	public void cutSpecifiedCharactersUseDelimiterListOutOfRangeTest(){
		String list1 = "1,8-9,3-100";
		String input1 = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
		String output1 = "1 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";		
		assertEquals(output1,cutTool.cutSpecifiedCharactersUseDelimiter(list1," ",input1));	
	}
	
	// Test execute as well as exploiting range utility used in cut tool
	
	// Test get help option
	@Test
	public void executeHelpOptionTest() {
		cutTool = new CUTTool(new String[] {"-help"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeNoOption() {
		cutTool = new CUTTool(new String[] {testFileName});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeInvalidOptionTest() {
		cutTool = new CUTTool(new String[] {"-asdf"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals("Error: Illegal option -asdf\n" + helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeOptionWithoutProperContent() {
		cutTool = new CUTTool(new String[] {"-c"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals("Error: Invalid option -c\n" + helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeTwoOptionsWithoutProperContent() {
		cutTool = new CUTTool(new String[] {"-c", "-d"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals("Error: Invalid option -c\n" + helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeTwoOptionsWithProperContent() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", "-d", "-"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals("Error: More than one option.\n" + helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeThreeOptionsWithProperContentAndDuplication() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", "-c", "2,3,4", "-d", "-"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals("Error: More than one option.\n" + helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeListOptionWithNoFileSpecified() {
		cutTool = new CUTTool(new String[] {"-c",  "1,2,3"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeDelimOptionWithNoFileSpecified() {
		cutTool = new CUTTool(new String[] {"-d", "1,2,3"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeDelimOptionWithMoreThanNecessaryParam() {
		cutTool = new CUTTool(new String[] {"-d", "-", "1,2,3", "alskdj", "asdfsadf"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutOptionWithMoreThanNecessaryParam() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", "alskdj", "asdas"});
		
		String actual = cutTool.execute(null, null);
		
		assertEquals(helpString, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", testFileName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123\n");
		expected.append("ask\n");
		expected.append("1,2\n");
		expected.append("1, \n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithAbsoluteFileAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123\n");
		expected.append("ask\n");
		expected.append("1,2\n");
		expected.append("1, \n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithRelativeFileAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", testFileRelativeName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123\n");
		expected.append("ask\n");
		expected.append("1,2\n");
		expected.append("1, \n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithRelativeFileFromHomeAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", testFileRelativeFromHomeName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123\n");
		expected.append("ask\n");
		expected.append("1,2\n");
		expected.append("1, \n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithNonExistingFileAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,3", testFileNotExistName});
		
		String expected = "Error: No such file or directory";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndInvalidListWithMultipleComma() {
		cutTool = new CUTTool(new String[] {"-c", "1,,,,,,,17", testFileAbsoluteName});
		
		String expected = "LIST in wrong format!";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndInvalidListWithMultipleDash() {
		cutTool = new CUTTool(new String[] {"-c", "1,2----3,17", testFileAbsoluteName});
		
		String expected = "LIST in wrong format!";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndSingleValueOutOfUpperBound() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,300", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("125\n");
		expected.append("asd\n");
		expected.append("1,9\n");
		expected.append("1,9\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndSingleValueAtUpperBound() {
		cutTool = new CUTTool(new String[] {"-c", "1,2,17", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("125\n");
		expected.append("asd\n");
		expected.append("1,9\n");
		expected.append("1,,\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndSingleInvalidValue() {
		cutTool = new CUTTool(new String[] {"-c", "1,a,17", testFileAbsoluteName});
		
		String expected = "Numbers in wrong format!";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndSingleValueOutOfLowerBound() {
		cutTool = new CUTTool(new String[] {"-c", "0,2,3", testFileAbsoluteName});
		
		String expected = "Values may not include zero.\n";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidOrdinaryRangeValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,3-7,10-13", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("12345670123\n");
		expected.append("askldjfasdj\n");
		expected.append("1,2,3,4,6,7\n");
		expected.append("1, 2, 34, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidOrdinaryRangeValueOutOfOrder() {
		cutTool = new CUTTool(new String[] {"-c", "10-13,3-7,1-2", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("12345670123\n");
		expected.append("askldjfasdj\n");
		expected.append("1,2,3,4,6,7\n");
		expected.append("1, 2, 34, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidIntersectingRangeValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-4,3-6,2-7,10-13", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("12345670123\n");
		expected.append("askldjfasdj\n");
		expected.append("1,2,3,4,6,7\n");
		expected.append("1, 2, 34, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidIdenticalRangeValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,1-2,10-13,10-13", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("120123\n");
		expected.append("asasdj\n");
		expected.append("1,,6,7\n");
		expected.append("1,4, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidOverlappingRangeValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,10-13,11-12", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("120123\n");
		expected.append("asasdj\n");
		expected.append("1,,6,7\n");
		expected.append("1,4, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidOverlappingMarginRangeValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,10-11,11-13", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("120123\n");
		expected.append("asasdj\n");
		expected.append("1,,6,7\n");
		expected.append("1,4, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidRangeAndSingleValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,10-12,13", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("120123\n");
		expected.append("asasdj\n");
		expected.append("1,,6,7\n");
		expected.append("1,4, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndValidSingleRangeAndSingleValue() {
		cutTool = new CUTTool(new String[] {"-c", "1-1,2-2,10-10,11-12,13", testFileAbsoluteName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("120123\n");
		expected.append("asasdj\n");
		expected.append("1,,6,7\n");
		expected.append("1,4, 5\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndInvalidRangeUpperBoundLowerThanLowerBound() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,7-3,10-13", testFileAbsoluteName});
		
		String expected = "Invalid Range!";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeCutWithCurrentFileAndInvalidAlphabeticRange() {
		cutTool = new CUTTool(new String[] {"-c", "1-2,@-b,10-13", testFileAbsoluteName});
		
		String expected = "Numbers in wrong format!";
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected, actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeDelimWithCurrentFileAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-d", ",", "1,2,3", testFileName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123456789012345\n");
		expected.append("askldjfklasdjfasd\n");
		expected.append("1,2,3\n");
		expected.append("1, 2, 3\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeAnotherValidDelimWithCurrentFileAndSingleValueRange() {
		cutTool = new CUTTool(new String[] {"-d", "-", "1,2,3", testFileName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123456789012345\n");
		expected.append("askldjfklasdjfasd\n");
		expected.append("1,2,3,4,5,6,7,8,9\n");
		expected.append("1, 2, 3, 4, 5, 6, 7, 8, 9\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeValidDelimWithCurrentFileAndValidRangeValue() {
		cutTool = new CUTTool(new String[] {"-d", ",", "1-3,2-7", testFileName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123456789012345\n");
		expected.append("askldjfklasdjfasd\n");
		expected.append("1,2,3,4,5,6,7\n");
		expected.append("1, 2, 3, 4, 5, 6, 7\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeValidDelimWithCurrentFileAndInvalidRangeValueOverUpperBound() {
		cutTool = new CUTTool(new String[] {"-d", ",", "1-3,5-100", testFileName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123456789012345\n");
		expected.append("askldjfklasdjfasd\n");
		expected.append("1,2,3,5,6,7,8,9\n");
		expected.append("1, 2, 3, 5, 6, 7, 8, 9\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeValidDelimWithCurrentFileAndInvalidRangeValueBelowLowerBound() {
		cutTool = new CUTTool(new String[] {"-d", ",", "0-3,5-100", testFileName});
		
		String expected = "Values may not include zero.\n";		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertNotEquals(0, cutTool.getStatusCode());
	}
	
	@Test
	public void executeValidDelimWithCurrentFileAndValidRangeAndSingleValue() {
		cutTool = new CUTTool(new String[] {"-d", ",", "1-3,2-7,9", testFileName});
		
		StringBuilder expected = new StringBuilder();
		
		expected.append("123456789012345\n");
		expected.append("askldjfklasdjfasd\n");
		expected.append("1,2,3,4,5,6,7,9\n");
		expected.append("1, 2, 3, 4, 5, 6, 7, 9\n");
		
		String actual = cutTool.execute(PathUtils.getCurrentPath().toFile(), null);
		
		assertEquals(expected.toString(), actual);
		assertEquals(0, cutTool.getStatusCode());
	}
}
