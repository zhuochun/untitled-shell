package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class DELETEToolTest {

	IDeleteTool iDeleteTool;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		iDeleteTool = new DELETETool(null);
	}

	@After
	public void tearDown() throws Exception {
		iDeleteTool = null;
	}

	@Test
	public void testDeleteExistsFile() throws IOException {
		File origin = folder.newFile();

		iDeleteTool.delete(origin);

		assertEquals(0, iDeleteTool.getStatusCode());
		assertFalse(origin.exists());
	}

	@Test
	public void testDeleteNotExistsFile() {
		File notExists = new File(PathUtils.pathResolver(folder.getRoot(), "notExists.txt"));
		
		iDeleteTool.delete(notExists);
		
		assertNotEquals(0, iDeleteTool.getStatusCode());
	}
	
	@Test
	public void testDeleteEmptyDirectory() throws IOException {
		File origin = folder.newFolder();

		iDeleteTool.delete(origin);

		assertNotEquals(0, iDeleteTool.getStatusCode());
		assertTrue(origin.exists());
	}

	@Test
	public void testDeleteNotEmptyDirectoryExists() throws IOException {
		folder.newFile();

		iDeleteTool.delete(folder.getRoot());

		assertNotEquals(0, iDeleteTool.getStatusCode());
		assertTrue(folder.getRoot().exists());
	}
	@Test
	public void testDeleteWithNullArgs(){
		iDeleteTool.execute(null, null);
		assertNotEquals(0,iDeleteTool.getStatusCode());
	}
	@Test
	public void testCopyExecuteWithLessArgs() {
		iDeleteTool = new DELETETool("".split(" "));
		iDeleteTool.execute(null,null);

		assertNotEquals(0, iDeleteTool.getStatusCode());
	}

	@Test
	public void testCopyExecuteWithNonExistsArgs() {
		iDeleteTool = new DELETETool("a.txt b.txt".split(" "));
		iDeleteTool.execute(folder.getRoot(), null);

		assertNotEquals(0, iDeleteTool.getStatusCode());
	}
	@Test
	public void testExecuteNormalCommand() throws IOException {
		folder.newFile("toDelete.txt");

		iDeleteTool = new DELETETool(
				"toDelete.txt".split(" "));
		iDeleteTool.execute(folder.getRoot(), null);

		File toDelete = new File(PathUtils.pathResolver(folder.getRoot(),
				"toDelete.txt"));

		assertEquals(0, iDeleteTool.getStatusCode());
		assertFalse(toDelete.exists());
	}
}