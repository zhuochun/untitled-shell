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
	File testFile1, testFile2, testFile3;

	@Before
	public void setUp() throws Exception {
		testFile1 = new File("testFile1");
		testFile2 = new File("testFile2");
		testFile3 = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/testFile3");
	}

	@After
	public void tearDown() throws Exception {
		testFile1.delete();
		testFile2.delete();
		testFile3.delete();
	}

	@Test
	public void testCreateDummyFile() throws Exception{
		FileUtils.createDummyFile(testFile1, 10);
		
		assertTrue(testFile1.exists());
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(testFile1));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();
		
		assertEquals("0000000000", sb.toString());		
	}

	@Test
	public void testDiffTwoIdenticalFiles() throws IOException, Exception {	
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile2, 10);
		
		assertTrue(FileUtils.diffTwoFiles(testFile1, testFile2));
	}

	@Test
	public void testDiffTwoDifferentFiles() throws IOException, Exception {
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile2, 20);
		
		assertFalse(FileUtils.diffTwoFiles(testFile1, testFile2));
	}

	@Test
	public void testDiffTwoIndenticalFilesInDifferentFolder() throws IOException, Exception {		
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile3, 10);
		
		assertTrue(FileUtils.diffTwoFiles(testFile1, testFile3));
	}
	
	@Test
	public void testDiffTwoDifferentFilesInDifferentFolder() throws IOException, Exception {
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile3, 20);
		
		assertFalse(FileUtils.diffTwoFiles(testFile1, testFile3));
	}
}
