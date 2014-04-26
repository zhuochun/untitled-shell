package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.fileutils.ICatTool;

public class CATToolTest {
	
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	private ICatTool cattool;

	@Before
	public void setUp() throws Exception {
		cattool = new CATTool(null);
	}

	@After
	public void tearDown() throws Exception {
		cattool = null;
	}

	@Test
	public void testGetStringForNullFile() {
		cattool.getStringForFile(null);
		assertNotEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testGetStringForNonExistFile() {
		cattool.getStringForFile(new File("notexists"));
		assertNotEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testGetStringForDirectory() {
		cattool.getStringForFile(folder.getRoot());
		assertNotEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testGetStringForEmptyFile() throws IOException {
		String content = "";
		File file = createFile("test.cab", content);
		String str = cattool.getStringForFile(file);

		assertEquals(content, str);
		assertEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testGetStringForNormalFileEn() throws IOException {
		String content = "abc\ncde\nfgh\t\nijk\r\n!@#$%^&*()";
		File file = createFile("test.cab", content);
		String str = cattool.getStringForFile(file);

		assertEquals(content, str);
		assertEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testExecuteWithNullStdIn() {
		String stdout = cattool.execute(null, null);
		
		assertEquals("", stdout);
		assertEquals(0, cattool.getStatusCode());
	}
	
	@Test
	public void testExecuteWithMultipleStdIn() {
		cattool = new CATTool(new String[] { "-", "-" });
		
		String stdout = cattool.execute(null, "hello");
		
		assertEquals("hello", stdout);
		assertEquals(0, cattool.getStatusCode());
	}
	
	@Test
	public void testExecuteWithStdInAndFile() throws IOException {
		String filename = "test.txt";
		String content  = "abc\ncde\nfgh\t\nijk\r\n!@#$%^&*()";
		createFile(filename, content);

		cattool = new CATTool(new String[] { "-", filename });

		String stdout = cattool.execute(folder.getRoot(), "abc");
		
		assertEquals("abc", stdout);
		assertEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testExecuteWithFileAndStdIn() throws IOException {
		String filename = "test.txt";
		String content  = "abc\ncde\nfgh\t\nijk\r\n!@#$%^&*()";
		createFile(filename, content);

		cattool = new CATTool(new String[] { filename, "-" });

		String stdout = cattool.execute(folder.getRoot(), "abc");
		
		assertEquals(content, stdout);
		assertEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testExecuteWithFile() throws IOException {
		String content = "abc\ncde\nfgh\t\nijk\r\n!@#$%^&*()";
		createFile("test test.cab", content);

		cattool = new CATTool(new String[] { "test test.cab" });
		String stdout = cattool.execute(folder.getRoot(), null);

		assertEquals(content, stdout);
		assertEquals(0, cattool.getStatusCode());
	}

	@Test
	public void testExecuteWithRelativeFilePath() throws IOException {
		String filename = "test.txt";
		String content  = "abc\ncde\nfgh\t\nijk\r\n!@#$%^&*()";
		createFile(filename, content);
		File childFolder = folder.newFolder("testFolder");

		cattool = new CATTool(new String[] { "../" + filename });

		String stdout = cattool.execute(childFolder, null);
		
		assertEquals(content, stdout);
		assertEquals(0, cattool.getStatusCode());
	}
	
	@Test
	public void testExecuteWithInvalidOptions() {
		cattool = new CATTool(new String[] { "-a" });
		cattool.execute(null, null);
		assertNotEquals(0, cattool.getStatusCode());
	}
	
	@Test
	public void testExecuteWithUnexistsFile() {
		cattool = new CATTool("noExists".split(" "));
		cattool.execute(folder.getRoot(), "");
		assertNotEquals(0, cattool.getStatusCode());
	}
	
	private File createFile(String filename, String content) throws IOException {
		File file = folder.newFile(filename);

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();

        return file;
	}

}
