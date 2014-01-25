package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.fileutils.ICatTool;

public class CATToolTest {
	
	private ICatTool cattool;

	@Before
	public void setUp() throws Exception {
		cattool = new CATTool(null);
	}

	@After
	public void tearDown() throws Exception {
		cattool = null;
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
