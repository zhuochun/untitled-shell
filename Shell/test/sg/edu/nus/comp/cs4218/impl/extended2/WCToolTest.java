package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.extended2.IWcTool;

public class WCToolTest {
	private IWcTool wcTool;
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();


	@Before
	public void before() {
		wcTool = new WCTool(null);
	}

	@After
	public void after() {
		wcTool = null;
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
		String result = newWCTool.execute(null, null);
		assertEquals(result, wcTool.getHelp());
	}
	
	@Test
	public void executeWithHelpOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-help"});
		String result = newWCTool.execute(null, null);
		assertEquals(result, wcTool.getHelp());
	}
	

	
	@Test
	public void executeWithMOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-m", "hello world"});
		String result = newWCTool.execute(null, null);
		assertEquals(result, wcTool.getCharacterCount("hello world"));
	}
	
	@Test
	public void executeWithWOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-w", "hello world"});
		String result = newWCTool.execute(null, null);
		assertEquals(result, wcTool.getWordCount("hello world"));
	}
	
	@Test
	public void executeWithIOption(){
		IWcTool newWCTool = new WCTool(new String[]{"-l", "hello world"});
		String result = newWCTool.execute(null, null);
		assertEquals(result, wcTool.getNewLineCount("hello world"));
	}
}
