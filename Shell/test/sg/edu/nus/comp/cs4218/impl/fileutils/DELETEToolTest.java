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

	IDeleteTool IDeleteTool;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		IDeleteTool = new DELETETool(null);
	}

	@After
	public void tearDown() throws Exception {
		IDeleteTool = null;
	}

	@Test
	public void testDeleteExistsFile() throws IOException {
		File origin = folder.newFile();

		IDeleteTool.delete(origin);

		assertEquals(0, IDeleteTool.getStatusCode());
		assertFalse(origin.exists());
	}

	@Test
	public void testDeleteNotExistsFile() {
		File notExists = new File(PathUtils.pathResolver(folder.getRoot(), "notExists.txt"));
		
		IDeleteTool.delete(notExists);
		
		assertNotEquals(0, IDeleteTool.getStatusCode());
	}
	
	@Test
	public void testDeleteEmptyDirectory() throws IOException {
		File origin = folder.newFolder();

		IDeleteTool.delete(origin);

		assertNotEquals(0, IDeleteTool.getStatusCode());
		assertTrue(origin.exists());
	}

	@Test
	public void testDeleteNotEmptyDirectoryExists() throws IOException {
		folder.newFile();

		IDeleteTool.delete(folder.getRoot());

		assertNotEquals(0, IDeleteTool.getStatusCode());
		assertTrue(folder.getRoot().exists());
	}

}