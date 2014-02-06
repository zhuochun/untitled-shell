package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.fileutils.ILsTool;

public class LSToolTest {
	
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
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
		assertNotEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testGetFilesFromNonExistsDirectory() {
		File notExistsDir = new File("notexists");
		List<File> files = lstool.getFiles(notExistsDir);

		assertEquals(0, files.size());
		assertNotEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testGetFilesFromEmptyDirectory() throws IOException {
		List<File> files = lstool.getFiles(folder.getRoot());
		
		assertEquals(0, files.size());
		assertEquals(0, lstool.getStatusCode());
	}

	@Test
	public void testGetFilesFromDirectory() throws IOException {
		folder.newFile("test1.txt");

		List<File> files = lstool.getFiles(folder.getRoot());
		
		assertEquals(1, files.size());
		assertEquals("test1.txt", files.get(0).getName());
		assertEquals(0, lstool.getStatusCode());
	}

	@Test
	public void testGetFilesDirsFromDirectory() throws IOException {
		List<String> fileNames = new ArrayList<String>();
		fileNames.add("testFolder");
		fileNames.add("test.txt");

		folder.newFolder(fileNames.get(0));
		folder.newFile(fileNames.get(1));

		List<File> files = lstool.getFiles(folder.getRoot());
		
		assertEquals(2, files.size());
		assertTrue(fileNames.contains(files.get(0).getName()));
		assertTrue(fileNames.contains(files.get(1).getName()));
		assertEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testGetStringForNullFile() {
		String ls = lstool.getStringForFiles(null);

		assertEquals("", ls);
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

	@Test
	public void testExecuteWithInvalidOptions() {
		lstool = new LSTool(new String[] { "-a" });
		lstool.execute(null, null);
		assertNotEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testExecuteWithFile() throws IOException {
		folder.newFile("file1.txt");

		lstool = new LSTool("file1.txt".split(" "));
		String stdout = lstool.execute(folder.getRoot(), null);
		
		assertEquals("file1.txt\n", stdout);
		assertEquals(0, lstool.getStatusCode());
	}
	
	@Test
	public void testExecuteWithFileAndDirectory() throws IOException {
		folder.newFile("file1.txt");
		folder.newFolder("testDir");
		folder.newFile("file2.java");
		
		lstool = new LSTool(null);
		String stdout = lstool.execute(folder.getRoot(), null);

		assertEquals("file1.txt\nfile2.java\ntestDir\n", stdout);
		assertEquals(0, lstool.getStatusCode());
	}

}