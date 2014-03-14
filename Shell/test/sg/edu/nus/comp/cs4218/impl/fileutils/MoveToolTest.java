package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;

public class MoveToolTest {

	private IMoveTool tool;
	private File currentDir, testFile, testNewFile, compareFile,
				 testParentNewFile, testRandomNewFile;
	private String randomSubpath;
	
	@Before
	public void setUp() throws Exception {
		currentDir = new File(System.getProperty("user.dir"));
		
		testFile = new File("aklsadfjklsa.txt");
		testNewFile = new File("baklsadfjklsa.txt");
		compareFile = new File("compare");
		testParentNewFile = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/baklsadfjklsa");
		
		randomSubpath = PathUtils.GetRandomSubpath(Paths.get(currentDir.toString())).toString();
		testRandomNewFile = new File(randomSubpath + "/baklsadfjklsa");
	}

	@After
	public void tearDown() throws Exception {
		testFile.delete();
		testNewFile.delete();
		compareFile.delete();
		testParentNewFile.delete();
		testRandomNewFile.delete();
	}

	@Test
	public void testMoveZeroParam() {
		tool = new MOVETool(new String[] {""});
		String output = tool.execute(currentDir, null);;
		
		assertEquals("Move Command need at least 2 params", output);
	}
	
	@Test
	public void testMoveOneParam() {
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt"});
		String output = tool.execute(currentDir, null);;
		
		assertEquals("Move Command need at least 2 params", output);
	}
	
	@Test
	public void testMoveFileDoesNotExist() {
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "baklsadfjklsa.txt"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("No such file or directory!", output);
	}
	
	@Test
	public void testMoveFileToNonExistFolder() throws IOException {
		testFile.createNewFile();
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "/baklsa/dfjklsa"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("No such file or directory!", output);
	}
	
	@Test
	public void testMoveADirectory() {
		tool = new MOVETool(new String[] {System.getProperty("user.dir"), "dfjklsa"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("Origin is not a file!", output);
	}
	
	@Test
	public void testMoveFileToSameFolderWithDifferentName() throws IOException, Exception {
		FileUtils.createDummyFile(compareFile, 10);
		FileUtils.createDummyFile(testFile, 10);
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "baklsadfjklsa.txt"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("", output);
		
		assertFalse(testFile.exists());
		assertTrue(testNewFile.exists());
		assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
	}
	
	@Test
	public void testMoveFileToSameFolderWithSameName() throws IOException, Exception {
		File testFile = new File("aklsadfjklsa.txt");
		File compareFile = new File("compare");
		
		FileUtils.createDummyFile(compareFile, 10);
		FileUtils.createDummyFile(testFile, 10);
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "aklsadfjklsa.txt"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("", output);
		
		assertTrue(testFile.exists());
		assertTrue(FileUtils.diffTwoFiles(testFile, compareFile));
	}
	
	@Test
	public void testMoveFileToSameFolderWithSameNameUsingDot() throws IOException, Exception {
		FileUtils.createDummyFile(compareFile, 10);
		FileUtils.createDummyFile(testFile, 10);
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "."});
		String output = tool.execute(currentDir, null);
		
		assertEquals("", output);
		
		assertTrue(testFile.exists());
		assertTrue(FileUtils.diffTwoFiles(testFile, compareFile));
	}
	
	@Test
	public void testMoveFileToParentFolder() throws IOException, Exception {
		FileUtils.createDummyFile(compareFile, 10);
		FileUtils.createDummyFile(testFile, 10);
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "../baklsadfjklsa"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("", output);
		
		assertFalse(testFile.exists());
		assertTrue(testParentNewFile.exists());
		assertTrue(FileUtils.diffTwoFiles(testParentNewFile, compareFile));
	}
	
	@Test
	public void testMoveFileToOneOfFolderOnTheSubPath() throws IOException, Exception {
		FileUtils.createDummyFile(compareFile, 10);
		FileUtils.createDummyFile(testFile, 10);
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", randomSubpath + "/baklsadfjklsa"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("", output);
		
		assertFalse(testFile.exists());
		assertTrue(testRandomNewFile.exists());
		assertTrue(FileUtils.diffTwoFiles(testRandomNewFile, compareFile));
	}
	
	@Test
	public void testMoveToFolderCannotWrite() throws IOException {
		testFile.createNewFile();
		
		tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "/"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("File cannot be moved!", output);
	}
}