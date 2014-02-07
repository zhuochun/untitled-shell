package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CDToolTest {

	CDTool tool;
	
	String workingDir;
	File workingDirFile;
	File originDir;
	
	@Before
	public void setUp() throws Exception {		
		workingDir = System.getProperty("user.dir");
		workingDirFile = new File(workingDir);
		originDir = new File(System.getProperty("user.dir"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuteWithSingleDot() {
		tool = new CDTool(new String[] {"."});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.toString(), output);
	}
	
	@Test
	public void testExecuteNoSuchFileOrDirectory() {
		tool = new CDTool(new String[] {"lkasdjflkasjdfkljsadf"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("No such file or directory!", output);
	}
	
	@Test
	public void testExecuteIsFile() {
		tool = new CDTool(new String[] {"testFile"});
		File testFile = new File("testFile");
		
		try {
			testFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("testFile is a file!", output);
		assertEquals(originDir, workingDirFile);
		
		testFile.delete();
	}
	
	@Test
	public void testExecuteChangeDir() {
		tool = new CDTool(new String[] {".."});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.getParent(), output);
	}
}
