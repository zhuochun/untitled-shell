package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.FileUtils;

public class FileUtilsTest {
	
	FileUtils fileutils = new FileUtils();
	File testFile1, testFile2, testFile3;
	File dummyFile, dummyDir;

	@Before
	public void setUp() throws Exception {
		dummyFile = new File("dummy.txt");
		dummyFile.createNewFile();
		
		dummyDir = new File("dummy");
		dummyDir.mkdir();
		
		testFile1 = new File("testFile1");
		testFile2 = new File("testFile2");
		testFile3 = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/testFile3");
	}

	@After
	public void tearDown() throws Exception {
		testFile1.delete();
		testFile2.delete();
		testFile3.delete();
		
		dummyFile.delete();
		dummyDir.delete();
	}
	
	@Test
	public void testCreatDummyFileWithLengthExistPath() throws Exception {
		FileUtils.createDummyFile(dummyFile, 10);
		
		assertTrue(dummyFile.exists());
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(dummyFile));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();
		
		assertEquals("0000000000", sb.toString());
	}

	@Test
	public void testCreateDummyFileWithLengthNonExistPath() throws Exception{
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
		
		testFile1.delete();
	}
	
	@Test
	public void testCreatDummyFileWithContentExistPath() throws Exception {
		FileUtils.createDummyFile(dummyFile, "asd");
		
		assertTrue(dummyFile.exists());
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(dummyFile));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();
		
		assertEquals("asd", sb.toString());
	}

	@Test
	public void testCreateDummyFileWithContentNonExistPath() throws Exception{
		FileUtils.createDummyFile(testFile1, "asd");
		
		assertTrue(testFile1.exists());
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(testFile1));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();
		
		assertEquals("asd", sb.toString());
		
		testFile1.delete();
	}
	
	@Test
	public void testDiffOneNonExistFileAsFirstFile() throws IOException {
		boolean exception = false;
		
		try {
			FileUtils.diffTwoFiles(new File("asdfasd.txt"), dummyFile);
		} catch (FileNotFoundException e) {
			exception = true;
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(exception);
	}
	
	@Test
	public void testDiffOneNonExistFileAsSecondFile() throws IOException {
		boolean exception = false;
		
		try {
			FileUtils.diffTwoFiles(dummyFile, new File("asdfasd.txt"));
		} catch (FileNotFoundException e) {
			exception = true;
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(exception);
	}
	
	@Test
	public void testDiffTwoNonExistFiles() throws IOException {
		boolean exception = false;
		
		try {
			FileUtils.diffTwoFiles(new File("asdfasd.txt"), new File("dddddd.txt"));
		} catch (FileNotFoundException e) {
			exception = true;
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(exception);
	}

	@Test
	public void testDiffTwoIdenticalFiles() throws IOException, Exception {	
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile2, 10);
		
		assertTrue(FileUtils.diffTwoFiles(testFile1, testFile2));
		
		testFile1.delete();
		testFile2.delete();
	}

	@Test
	public void testDiffTwoDifferentFiles() throws IOException, Exception {
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile2, 20);
		
		assertFalse(FileUtils.diffTwoFiles(testFile1, testFile2));
		
		testFile1.delete();
		testFile2.delete();
	}

	@Test
	public void testDiffTwoIndenticalFilesInDifferentFolder() throws IOException, Exception {		
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile3, 10);
		
		assertTrue(FileUtils.diffTwoFiles(testFile1, testFile3));
		
		testFile1.delete();
		testFile3.delete();
	}
	
	@Test
	public void testDiffTwoDifferentFilesInDifferentFolder() throws IOException, Exception {
		FileUtils.createDummyFile(testFile1, 10);
		FileUtils.createDummyFile(testFile3, 20);
		
		assertFalse(FileUtils.diffTwoFiles(testFile1, testFile3));
		
		testFile1.delete();
		testFile3.delete();
	}
	
	@Test
	public void testReadFileLinesNullPath() {
		boolean exception = false; 
		
		try {
			FileUtils.readFileLines(null);
		} catch (FileNotFoundException e) {
			exception = true;
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(exception);
	}
	
	@Test
	public void testReadFileLinesNonExistPath() {
		boolean exception = false;
		
		try {
			FileUtils.readFileLines(testFile1);
		} catch (FileNotFoundException e) {
			exception = true;
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(exception);
	}
	
	@Test
	public void testReadFileLinesDirectory() {
		boolean exception = false;
		
		try {
			FileUtils.readFileLines(dummyDir);
		} catch (FileSystemException e) {
			exception = true;
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(exception);
	}
	
	@Test
	public void testWriteAndRead() throws Exception {
		String content = "abcdefg\nabcdefg";
		FileUtils.createDummyFile(testFile1, content);

		assertEquals(content, FileUtils.readFileContent(testFile1));
		
		testFile1.delete();
	}
}
