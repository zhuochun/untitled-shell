package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ECHOToolTest {
	
	@Test
	public void testEchoExecuteEmptyArgs() {
		ECHOTool echo = new ECHOTool(null);
		
		String stdout = echo.execute(null, null);
		
		assertEquals("\n", stdout);
	}

	@Test
	public void testEchoExecute() {
		ECHOTool echo = new ECHOTool("hello world".split(" "));
		
		String stdout = echo.execute(null, null);
		
		assertEquals("hello world\n", stdout);
	}
	
	@Test
	public void testEchoExecuteWithHBar() {
		ECHOTool echo = new ECHOTool("-".split(" "));
		
		String stdout = echo.execute(null, null);
		
		assertEquals("\n", stdout);
	}
	
	@Test
	public void testEchoExecuteWithTwoHBar() {
		ECHOTool echo = new ECHOTool("- -".split(" "));
		
		String stdout = echo.execute(null, null);
		
		assertEquals("-\n", stdout);
	}
}



