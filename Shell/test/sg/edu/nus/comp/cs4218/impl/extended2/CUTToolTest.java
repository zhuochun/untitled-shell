package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ICutTool;

public class CUTToolTest {
	
	private ICutTool cutTool;
	private String helpString;
	
	@Before
	public void before() {
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
	}

	@After
	public void after() {
		cutTool = null;
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
		
		assertEquals(0, cutTool.getStatusCode());
		assertEquals(helpString, actual);
	}
	
	@Test
	public void executeInvalidOptionTest() {
		cutTool = new CUTTool(new String[] {"-asdf"});
		
		String actual = cutTool.execute(null, null);
		
		assertNotEquals(0, cutTool.getStatusCode());
		assertEquals(helpString, actual);
	}
	
	@Test
	public void executeOptionWithoutProperContent() {
		cutTool = new CUTTool(new String[] {"-asdf"});
		
		String actual = cutTool.execute(null, null);
		
		assertNotEquals(0, cutTool.getStatusCode());
		assertEquals(helpString, actual);
	}
}
