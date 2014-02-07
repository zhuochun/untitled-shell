package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CDToolTest {

	CDTool tool;
	
	String workingDir;
	String homeDir;
	File workingDirFile;
	File originDir;
	
	@Before
	public void setUp() throws Exception {		
		workingDir = System.getProperty("user.dir");
		homeDir = System.getProperty("user.home");
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

	@Test
	public void testExecutePathWithSingleDotAndMultipleForwardSlash() {
		tool = new CDTool(new String[] {"./////"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.toString(), output);
	}
	
	@Test
	public void testExecutePathWithDoubleDotAndSingleForwardSlash() {
		tool = new CDTool(new String[] {"../"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.getParent(), output);
	}
	
	@Test
	public void testExecutePathWithDoubleDotAndMultipleForwardSlash() {
		tool = new CDTool(new String[] {"..////////////"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.getParent(), output);
	}
	
	@Test
	public void testExecutePathWithSingleTudle() {
		tool = new CDTool(new String[] {"~"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(homeDir, output);
	}
	
	@Test
	public void testExecutePathWithEmptyString() {
		tool = new CDTool(new String[] {""});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(homeDir, output);
	}
	
	@Test
	public void testExecutePathWithMultipleTudle() {
		tool = new CDTool(new String[] {"~~~~~~~~~~"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("No such file or directory!", output);
	}
	
	@Test
	public void testExecutePathWithSingleTudleAndSingleForwardSlash() {
		tool = new CDTool(new String[] {"~/"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(homeDir, output);
	}
	
	@Test
	public void testExecutePathWithSingleTudleAndMultipleForwardSlash() {
		tool = new CDTool(new String[] {"~//////////////"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(homeDir, output);
	}
	
	@Test
	public void testExecutePathWithSingleForwardSlash() {
		tool = new CDTool(new String[] {"/"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("/", output);
	}
	
	@Test
	public void testExecutePathWithMultipleForwardSlash() {
		tool = new CDTool(new String[] {"////////////"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("/", output);
	}
	
	@Test
	public void testExecutePathWithMultipeLayersOfSingleDots() {
		tool = new CDTool(new String[] {"./././././././"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(workingDir, output);
	}
	
	@Test
	public void testExecutePathWithMultipeLayersOfDoubleDots() {
		tool = new CDTool(new String[] {"../../.."});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.getParentFile().getParentFile().getParent(), output);
	}
	
	@Test
	public void testExecutePathWithSingleAndDoubleDotss() {
		tool = new CDTool(new String[] {"./.././../././././../"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.getParentFile().getParentFile().getParent(), output);
	}
	
	@Test
	public void testExecutePathWithSingleAndDoubleDotsAndMultipleSlashes() {
		tool = new CDTool(new String[] {"./..//./../////////./././/////.///..//"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals(originDir.getParentFile().getParentFile().getParent(), output);
	}
	
	@Test
	public void testExecutePathWithTudleAndForwardSlash() {
		tool = new CDTool(new String[] {"~/~/~/"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("No such file or directory!", output);
		
		File testFolder = new File("~/~/");
		testFolder.mkdir();
		output = tool.execute(workingDirFile, null);
		assertEquals("No such file or directory!", output);
		testFolder.delete();
	}
	
	@Test
	public void testExecutePathWithTudleAndForwardSlashAndAlphabets() {
		tool = new CDTool(new String[] {"~/asdfsadf"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("No such file or directory!", output);
		
		File testFolder = new File(homeDir + "/asdfsadf");
		testFolder.mkdir();
		output = tool.execute(workingDirFile, null);
		assertEquals(homeDir + "/asdfsadf", output);
		testFolder.delete();
	}
	
	@Test
	public void testExecutePathWithTudleAndAlphabets() {
		tool = new CDTool(new String[] {"~oiwejf"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("No such file or directory!", output);
		
		File testFolder = new File("~oiwejf");
		testFolder.mkdir();
		output = tool.execute(workingDirFile, null);
		assertEquals(workingDir + "/~oiwejf", output);
		testFolder.delete();
	}
	
	@Test
	public void testExecutePathWithAllThreeSpecialCharacters() {
		tool = new CDTool(new String[] {"../~/~"});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("No such file or directory!", output);
	}
	
	@Test
	public void testExecutePathWithSpecialCharacterAndAlphabets() {
		Path p = Paths.get(workingDir);
		int depth = p.getNameCount();
		int newDepth = 2 + (int)(Math.random() * 1000000) % (depth - 2);
		
		Path newPath = p.subpath(0, newDepth);
		
		String folderName = "test";
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < depth - newDepth; i ++) {
			sb.append("../");
		}
		
		sb.append(folderName);
		
		tool = new CDTool(new String[] {sb.toString()});
		
		File testFolder = new File("/" + newPath.toString() + "/" + folderName);
		testFolder.mkdir();
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("/" + newPath.toString() + "/" + folderName, output);
		
		testFolder.delete();
		
		try {
			testFolder.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		output = tool.execute(workingDirFile, null);
		
		assertEquals("test is a file!", output);
		
		testFolder.delete();
	}
	
	@Test
	public void testExecutePathWithExtremeLongDoubleDotsSequence() {
		Path p = Paths.get(workingDir);
		int depth = p.getNameCount();
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i <= depth + 1; i ++) {
			sb.append("../");
		}
		
		tool = new CDTool(new String[] {sb.toString()});
		
		String output = tool.execute(workingDirFile, null);
		
		assertEquals("/", output);
	}
}
