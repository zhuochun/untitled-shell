package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.fileutils.ILsTool;

public class LSToolTest {
	
	ILsTool lstool;

	@Before
	public void setUp() throws Exception {
		lstool = new LSTool(null);
	}

	@After
	public void tearDown() throws Exception {
		lstool = null;
	}
	
	@Test
	public void testGetFilesFromNullDirectory() {
		List<File> files = lstool.getFiles(null);

		assertEquals(0, files.size());
		assertEquals(1, lstool.getStatusCode());
	}
	
	@Test
	public void testGetFilesFromNonExistsDirectory() {
		File notExistsDir = new File("notexists");
		List<File> files = lstool.getFiles(notExistsDir);

		assertEquals(0, files.size());
		assertEquals(1, lstool.getStatusCode());
	}

	@Test
	public void testGetFilesFromDirectory() throws IOException {
		File tempFile = File.createTempFile("exists", "tmp");
		File existsDir = new File(tempFile.getParent());
		
		List<File> files = lstool.getFiles(existsDir);
		
		assertTrue(files.size() > 0);
		assertEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testGetStringForNullFile() {
		String ls = lstool.getStringForFiles(null);

		assertEquals("\n", ls);
		assertEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testGetStringForOneFile() {
		List<File> files = new ArrayList<File>();
		files.add(new File("file1.txt"));
		
		String ls = lstool.getStringForFiles(files);

		assertEquals("file1.txt\n", ls);
		assertEquals(0, lstool.getStatusCode());
	}

	@Test
	public void testGetStringForFilesAndDirectory() {
		List<File> files = new ArrayList<File>();
		files.add(new File("file1.txt"));
		files.add(new File("./testDir"));
		files.add(new File("file2.java"));
		
		String ls = lstool.getStringForFiles(files);

		assertEquals("file1.txt\ntestDir\nfile2.java\n", ls);
		assertEquals(0, lstool.getStatusCode());
	}

}