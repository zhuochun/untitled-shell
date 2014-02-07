package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.impl.DiffUtils;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
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
		tool = new MoveTool(new String[] {""});
		String output = tool.execute(currentDir, null);;
		
		assertEquals("Move Command need at least 2 params", output);
	}
	
	@Test
	public void testMoveOneParam() {
		tool = new MoveTool(new String[] {"aklsadfjklsa.txt"});
		String output = tool.execute(currentDir, null);;
		
		assertEquals("Move Command need at least 2 params", output);
	}
	
	@Test
	public void testMoveFileDoesNotExist() {
		tool = new MoveTool(new String[] {"aklsadfjklsa.txt", "baklsadfjklsa.txt"});
		String output = tool.execute(currentDir, null);
		
		assertEquals("No such file or directory!", output);
	}
	
	@Test
	public void testMoveFileToSameFolderWithDifferentName() {
		File testFile = new File("aklsadfjklsa.txt");
		File testNewFile = new File("baklsadfjklsa.txt");
		File compareFile = new File("compare");
		
		try {
			FileUtils.createDummyFile(compareFile, 10);
			FileUtils.createDummyFile(testFile, 10);
			
			tool = new MoveTool(new String[] {"aklsadfjklsa.txt", "baklsadfjklsa.txt"});
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
			
			tool = new MoveTool(new String[] {"aklsadfjklsa.txt", "aklsadfjklsa.txt"});
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
			
			tool = new MoveTool(new String[] {"aklsadfjklsa.txt", "../baklsadfjklsa"});
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

//
//
//	@Test
//	public void testSrcFileNotExists() {
//		try {
//			File src = folder.newFile("Src.txt");
//			File dest = folder.getRoot();
//
//			movetool.move(src, dest);
//
//			assertTrue(!src.exists());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testBeforeMove() {
//		try {
//			File src = folder.newFile("Src.txt");
//			File target = folder.newFile("target.txt");
//
//			FileWriter fw = new FileWriter(src);
//			BufferedWriter bw = new BufferedWriter(fw);
//			FileReader fr = new FileReader(target);
//			BufferedReader br = new BufferedReader(fr);
//
//			// construct origin file
//			String content = "abcde12345ABC@#$%-;()*";
//			bw.write(content);
//			bw.flush();
//			// String in = new String (content);
//
//			assertEquals(br.readLine(), null);
//
//			movetool.move(src, target);
//			br = new BufferedReader(fr);
//			bw.close();
//			br.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testAfterMove() {
//		try {
//			File src = folder.newFile("Src.txt");
//			File dest = folder.newFile("target.txt");
//			/*
//			 * if(!src.exists()){ src.createNewFile(); } if(!dest.exists()){
//			 * dest.createNewFile(); }
//			 */
//
//			FileWriter fw = new FileWriter(src);
//			BufferedWriter bw = new BufferedWriter(fw);
//			FileReader fr = new FileReader(dest);
//			BufferedReader br = new BufferedReader(fr);
//
//			// construct origin file
//			String content = "abcde12345ABC@#$%-;()*";
//			bw.write(content);
//			bw.flush();
//			String in = new String(content);
//
//			assertEquals(br.readLine(), null);
//
//			movetool.move(src, dest);
//			br = new BufferedReader(fr);
//
//			bw.close();
//			br.close();
//			assertEquals(br.readLine(), in);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testDiffFormatFile() {
//		try {
//			File src = new File("Shell/test/rainbow1.jpg");
//			File dest = new File("Shell/test/child");
//
//			assertEquals(movetool.move(src, dest), false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}