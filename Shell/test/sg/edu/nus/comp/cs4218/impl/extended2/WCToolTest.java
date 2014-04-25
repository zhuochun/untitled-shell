package sg.edu.nus.comp.cs4218.impl.extended2;

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

import sg.edu.nus.comp.cs4218.extended2.IWcTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class WCToolTest {
	private IWcTool wcTool;
	File tmpFile1;
	File tmpFile2;
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	}

	@Before
	public void before() throws IOException{
		wcTool = new WCTool(null);
		
		tmpFile1 = new File("tmpFile1.txt");
		tmpFile1.createNewFile();
		writeFile("tmpFile1.txt", "hello world");
		
		
		tmpFile2 = new File("tmpFile2.txt");
		tmpFile2.createNewFile();
		
	}

	@After
	public void after() {
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

	//test getCharacterCount method with string
	@Test
	public void getCharacterCountTest() {
		String input = "Test this"; 
		assertEquals("9", wcTool.getCharacterCount(input));
	}

	//test getCharacterCount method with string having a newline
	@Test
	public void getCharacterCountStringWithNewlineTest() {
		String input = "Test this\n"; 
		assertEquals("10", wcTool.getCharacterCount(input));
	}
	
	//test getCharacterCount method with empty string
	@Test
	public void getCharacterCountEmptyStringTest() {
		String input = "";// empty string
		assertEquals("0", wcTool.getCharacterCount(input));
	}
	
	@Test
	public void getCharacterCountNullStringTest(){
		String input = null;
		assertEquals("0", wcTool.getCharacterCount(input));
	}


	//test getWordCountTest, String with newline
	@Test
	public void getWordCountTest() {

		String input = "\n Test 4 3 \n"; 
		assertEquals("3", wcTool.getWordCount(input));
	}

	//test getWordCountTest for null string
	@Test
	public void getWordCountForNullTest() {
		String input = null;
		assertEquals("0", wcTool.getWordCount(input));
	}


	//Test getNewLineCount with string with newline
	@Test
	public void getNewLineCountTest() {
		String input = "Test this\n\r"; // with new line character
		assertEquals("2", wcTool.getNewLineCount(input));
	}

	//Test getNewLineCount with null string
	@Test
	public void getNewLineCountForNullTest() {
		String input = null;
		assertEquals("0", wcTool.getNewLineCount(input));
	}
	
	@Test
	public void getHelpTest(){
		StringBuilder helpInfo = new StringBuilder();
		helpInfo = helpInfo.append("Command Format - wc [OPTIONS] [FILE]\n");
		helpInfo = helpInfo.append(" FILE - Name of the file, when no file is present (denoted by -) use standard input\n");
		helpInfo = helpInfo.append(" OPTIONS\n");
		helpInfo = helpInfo.append("       -m : Print only the character counts\n" );
		helpInfo = helpInfo.append("       -w : Print only the word counts\n");
		helpInfo = helpInfo.append("       -l : Print only the newline counts\n");
		helpInfo = helpInfo.append("       -help : Brief information about supported options");
		assertEquals(new String(helpInfo), wcTool.getHelp());
	}
	@Test
	public void executWithIllegalInputTest(){
		IWcTool newWCTool = new WCTool(new String[]{"-i"});
		newWCTool.execute(null, null);
		assertEquals(9, newWCTool.getStatusCode());
	}
	
	@Test
	public void executeWithNoOption(){
		
		IWcTool newWCTool = new WCTool(new String[]{});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, wcTool.getHelp());
	}
	
	@Test
	public void executeWithHelpOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-help"});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, wcTool.getHelp());
	}
	

	
	@Test
	public void executeWithMOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-m", "tmpFile1.txt"});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, wcTool.getCharacterCount("hello world"));
	}
	
	@Test
	public void executeWithWOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-w", "tmpFile1.txt"});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, wcTool.getWordCount("hello world"));
	}
	
	@Test
	public void executeWithIOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-l", "tmpFile1.txt"});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, wcTool.getNewLineCount("hello world"));
	}
}
