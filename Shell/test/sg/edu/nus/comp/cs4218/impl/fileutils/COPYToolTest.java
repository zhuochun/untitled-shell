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

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class COPYToolTest {

	ICopyTool iCopyTool;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		iCopyTool = new COPYTool(null);
	}

	@After
	public void tearDown() throws Exception {
		iCopyTool = null;
	}

	@Test
	public void testCopyFileNormal() throws IOException {
		File origin = folder.newFile("originForCopy.txt");
		File target = new File(PathUtils.pathResolver(folder.getRoot(),
				"test.txt"));

		iCopyTool.copy(origin, target);

		assertEquals(0, iCopyTool.getStatusCode());
		assertTrue(target.exists());
	}

	@Test
	public void testCopyOriginFileNotExists() {
		File origin = new File(PathUtils.pathResolver(folder.getRoot(),
				"notExists.txt"));
		File target = new File(PathUtils.pathResolver(folder.getRoot(),
				"hopeExists.txt"));

		iCopyTool.copy(origin, target);

		assertNotEquals(0, iCopyTool.getStatusCode());
		assertFalse(target.exists());
	}

	@Test
	public void testCopyOriginIsDirectory() throws IOException {
		File origin = folder.newFolder();
		File target = new File(PathUtils.pathResolver(folder.getRoot(),
				"hopeExists.txt"));

		iCopyTool.copy(origin, target);

		assertNotEquals(0, iCopyTool.getStatusCode());
		assertFalse(target.exists());
	}

	@Test
	public void testCopyOriginToItself() throws IOException {
		File origin = folder.newFile("originForCopy.txt");

		iCopyTool.copy(origin, origin);

		assertEquals(0, iCopyTool.getStatusCode());
		assertTrue(origin.exists());
	}

	@Test
	public void testCopyTargetFileExists() throws IOException {
		File origin = folder.newFile("originForCopy.txt");
		File target = folder.newFile("targetForCopy.txt");

		iCopyTool.copy(origin, target);

		assertNotEquals(0, iCopyTool.getStatusCode());
	}

	@Test
	public void testCopyTargetIsDirectory() throws IOException {
		File origin = folder.newFile();
		File target = folder.newFolder();
		File realTarget = new File(PathUtils.pathResolver(target, origin.getName()));

		iCopyTool.copy(origin, target);

		assertEquals(0, iCopyTool.getStatusCode());
		assertTrue(realTarget.exists());
	}

	@Test
	public void testCopyTargetUnderNonExistsDirectory() throws IOException {
		File origin = folder.newFile();
		File target = new File(PathUtils.pathResolver(folder.getRoot(), "noDir/noFile.mp3"));

		iCopyTool.copy(origin, target);

		assertNotEquals(0, iCopyTool.getStatusCode());
	}

	@Test
	public void testExecuteNormalCommand() throws IOException {
		folder.newFile("originForCopy.txt");

		iCopyTool = new COPYTool(
				"originForCopy.txt targetForCopy.txt".split(" "));
		iCopyTool.execute(folder.getRoot(), null);

		File target = new File(PathUtils.pathResolver(folder.getRoot(),
				"targetForCopy.txt"));

		assertEquals(0, iCopyTool.getStatusCode());
		assertTrue(target.exists());
	}

	@Test
	public void testCopyExecuteWithNullArgs() {
		iCopyTool.execute(null, null);
		assertNotEquals(0, iCopyTool.getStatusCode());
	}

	@Test
	public void testCopyExecuteWithLessArgs() {
		iCopyTool = new COPYTool("a".split(" "));
		iCopyTool.execute(null, null);

		assertNotEquals(0, iCopyTool.getStatusCode());
	}

	@Test
	public void testCopyExecuteWithNonExistsArgs() {
		iCopyTool = new COPYTool("a.txt b.txt".split(" "));
		iCopyTool.execute(folder.getRoot(), null);

		assertNotEquals(0, iCopyTool.getStatusCode());
	}
	
	@Test
	public void testWithException() throws IllegalArgumentException{
		iCopyTool = new COPYTool(new String[]{"a","b"});
		iCopyTool.execute(folder.getRoot(), null);
		assertNotEquals(9, iCopyTool.getStatusCode());
	}
}
