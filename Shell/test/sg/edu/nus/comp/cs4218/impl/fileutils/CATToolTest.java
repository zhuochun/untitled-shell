package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
	public void testGetStringForStandardInput() {
		fail("Not yet implemented");
	}
	
	private File createFile(String filename, String content) throws IOException {
		File file = folder.newFile(filename);

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();

        return file;
	}

}
