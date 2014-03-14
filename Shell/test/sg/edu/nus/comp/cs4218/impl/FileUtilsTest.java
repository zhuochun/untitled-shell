package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.FileUtils;

public class FileUtilsTest {
	
	FileUtils fileutils = new FileUtils();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateDummyFile() {
		File testFile = new File("testFile");
		
		try {
			FileUtils.createDummyFile(testFile, 10);
			
			assertTrue(testFile.exists());
			
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(testFile));

			int ch;
			while ((ch = br.read()) != -1) {
				sb.append((char) ch);
			}

			br.close();
			
			assertEquals("0000000000", sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			testFile.delete();
		}
	}

	@Test
	public void testDiffTwoIdenticalFiles() {
		File testFile1 = new File("testFile1");
		File testFile2 = new File("testFile2");
		
		try {
			FileUtils.createDummyFile(testFile1, 10);
			FileUtils.createDummyFile(testFile2, 10);
			
			assertTrue(FileUtils.diffTwoFiles(testFile1, testFile2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile1.delete();
			testFile2.delete();
		}
	}

	@Test
	public void testDiffTwoDifferentFiles() {
		File testFile1 = new File("testFile1");
		File testFile2 = new File("testFile2");
		
		try {
			FileUtils.createDummyFile(testFile1, 10);
			FileUtils.createDummyFile(testFile2, 20);
			
			assertFalse(FileUtils.diffTwoFiles(testFile1, testFile2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile1.delete();
			testFile2.delete();
		}
	}

	@Test
	public void testDiffTwoIndenticalFilesInDifferentFolder() {
		File testFile1 = new File("testFile1");
		File testFile2 = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/testFile2");
		
		try {
			FileUtils.createDummyFile(testFile1, 10);
			FileUtils.createDummyFile(testFile2, 10);
			
			assertTrue(FileUtils.diffTwoFiles(testFile1, testFile2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile1.delete();
			testFile2.delete();
		}
	}
	
	@Test
	public void testDiffTwoDifferentFilesInDifferentFolder() {
		File testFile1 = new File("testFile1");
		File testFile2 = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/testFile2");
		
		try {
			FileUtils.createDummyFile(testFile1, 10);
			FileUtils.createDummyFile(testFile2, 20);
			
			assertFalse(FileUtils.diffTwoFiles(testFile1, testFile2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			testFile1.delete();
			testFile2.delete();
		}
	}
}
