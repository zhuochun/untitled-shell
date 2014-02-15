package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.IWcTool;

public class WCToolTest {
	private IWcTool wcTool;

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
		assertEquals("0", wcTool.getWordCount(input));
	}
}
