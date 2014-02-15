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
	@Test
	public void pasteSerialTest() {
		String[] input = {"1","2","3","4","5","6"};
		String output = "1 2 3 4 5 6 \n";
		assertEquals(output, pasteTool.pasteSerial(input));
	}
	
	
	
	//Test pasteUseDelimiter method 
	@Test
	public void pasteUseDelimiterTest(){
		String[] input = {"1","2","3","4","5","6"};
		String output = "1|2|3|4|5|6|";
		assertEquals(output, pasteTool.pasteUseDelimiter("|",input));		
	}
	

}