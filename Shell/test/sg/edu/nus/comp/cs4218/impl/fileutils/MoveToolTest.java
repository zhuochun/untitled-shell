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

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private IMoveTool tool;
	private File currentDir;

	@Before
	public void setUp() throws Exception {
		currentDir = new File(System.getProperty("user.dir"));
	}

	@After
	public void tearDown() throws Exception {
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
	public void testMoveFileToNonExistFolder() {
		File testFile = new File("aklsadfjklsa.txt");
		
		try {
			testFile.createNewFile();
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "/baklsa/dfjklsa"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("No such file or directory!", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
		}
	}
	
	@Test
	public void testMoveADirectory() {
		tool = new MOVETool(new String[] {System.getProperty("user.dir"), "dfjklsa"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("Origin is not a file!", output);
	}
	
	@Test
	public void testMoveFileToSameFolderWithDifferentName() {
		File testFile = new File("aklsadfjklsa.txt");
		File testNewFile = new File("baklsadfjklsa.txt");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "baklsadfjklsa.txt"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("", output);
			
			assertFalse(testFile.exists());
			assertTrue(testNewFile.exists());
			assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
			testNewFile.delete();
			compareFile.delete();
		}
	}
	
	@Test
	public void testMoveFileToSameFolderWithSameName() {
		File testFile = new File("aklsadfjklsa.txt");
		File testNewFile = new File("aklsadfjklsa.txt");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "aklsadfjklsa.txt"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("", output);
			
			assertTrue(testNewFile.exists());
			assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
			testNewFile.delete();
			compareFile.delete();
		}
	}
	
	@Test
	public void testMoveFileToSameFolderWithSameNameUsingDot() {
		File testFile = new File("aklsadfjklsa.txt");
		File testNewFile = new File("aklsadfjklsa.txt");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "."});
			String output = tool.execute(currentDir, null);
			
			assertEquals("", output);
			
			assertTrue(testNewFile.exists());
			assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
			testNewFile.delete();
			compareFile.delete();
		}
	}
	
	@Test
	public void testMoveFileToParentFolder() {
		File testFile = new File("aklsadfjklsa.txt");
		File testNewFile = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/baklsadfjklsa");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "../baklsadfjklsa"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("", output);
			
			assertFalse(testFile.exists());
			assertTrue(testNewFile.exists());
			assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
			testNewFile.delete();
			compareFile.delete();
		}
	}
	
	@Test
	public void testMoveFileToAFolder() {
		File testFile = new File("aklsadfjklsa.txt");
		File testFolder = new File("testFolder");
		
		testFolder.mkdir();
		
		File testNewFile = new File(testFolder.getAbsolutePath() + "/aklsadfjklsa.txt");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "testFolder"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("", output);
			
			assertFalse(testFile.exists());
			assertTrue(testNewFile.exists());
			assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
			testFolder.delete();
			testNewFile.delete();
			compareFile.delete();
		}
	}
	
	@Test
	public void testMoveFileToOneOfFolderOnTheSubPath() {
		File testFile = new File("aklsadfjklsa.txt");
		String randomSubpath = PathUtils.GetRandomSubpath(Paths.get(currentDir.toString())).toString();
		File testNewFile = new File(randomSubpath + "/baklsadfjklsa");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", randomSubpath + "/baklsadfjklsa"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("", output);
			
			assertFalse(testFile.exists());
			assertTrue(testNewFile.exists());
			assertTrue(FileUtils.diffTwoFiles(testNewFile, compareFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
			testNewFile.delete();
			compareFile.delete();
		}
	}
	
	@Test
	public void testMoveToFolderCannotWrite() {
		File testFile = new File("aklsadfjklsa.txt");
		try {
			testFile.createNewFile();
			
			tool = new MOVETool(new String[] {"aklsadfjklsa.txt", "/"});
			String output = tool.execute(currentDir, null);
			
			assertEquals("File cannot be moved!", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile.delete();
		}
	}
}