package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.IPasteTool;

public class PASTEToolTest {

	private IPasteTool pasteTool;
	
	@Before
	public void before() {
		pasteTool = new PASTETool(null);
	}

	@After
	public void after() {
		pasteTool = null;
	}
	
	//Test pasteSerial method
	//@Corrected
	@Test
	public void pasteSerialTest() {
		String[] input = {"1","2","3","4","5","6"};
		String output = "1\t2\t3\t4\t5\t6\t\n";
		assertEquals(output, pasteTool.pasteSerial(input));
	}

	@Test
	public void pasteSerialNullTest() {
		String[] input = {"1","2","3","4","5","6", null};
		String output = "1\t2\t3\t4\t5\t6\t\n";
		assertEquals(output, pasteTool.pasteSerial(input));
	}
	
	//Test pasteUseDelimiter method
	//@Corrected
	@Test
	public void pasteUseDelimiterTest(){
		String[] input = {"1","2","3","4","5","6"};
		String output = "1|2|3|4|5|6|\n";
		assertEquals(output, pasteTool.pasteUseDelimiter("|",input));		
	}
	
	@Test
	public void pasteUseDelimiterNullTest(){
		String[] input = {"1","2","3","4","5","6", null};
		String output = "1|2|3|4|5|6|\n";
		assertEquals(output, pasteTool.pasteUseDelimiter("|",input));		
	}

	@Test
	public void testGetHelp() {
		assertTrue(pasteTool.getHelp().matches("^Command Format -(.|\n)+OPTIONS(.|\n)+$"));
	}

	@Test
	public void textExecuteInvalidOptions() {
		pasteTool = new PASTETool(new String[] { "-i" });
		pasteTool.execute(null, null);
		assertNotEquals(0, pasteTool.getStatusCode());
	}

	@Test
	public void testExecuteWithoutParams() {
		pasteTool = new PASTETool(new String[] {});
		pasteTool.execute(null, null);
		assertNotEquals(0, pasteTool.getStatusCode());
	}
}