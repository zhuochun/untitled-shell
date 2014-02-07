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

	ICopyTool IcopyTool;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		IcopyTool = new COPYTool(null);
	}

	@After
	public void tearDown() throws Exception {
		IcopyTool = null;
	}

	@Test
	public void testCopyFileNormal() throws IOException {
		File origin = folder.newFile("originForCopy.txt");
		File target = new File(PathUtils.PathResolver(folder.getRoot(),
				"test.txt"));

		IcopyTool.copy(origin, target);

		assertEquals(0, IcopyTool.getStatusCode());
		assertTrue(target.exists());
	}

	@Test
	public void testCopyOriginFileNotExists() {
		File origin = new File(PathUtils.PathResolver(folder.getRoot(),
				"notExists.txt"));
		File target = new File(PathUtils.PathResolver(folder.getRoot(),
				"hopeExists.txt"));

		IcopyTool.copy(origin, target);

		assertNotEquals(0, IcopyTool.getStatusCode());
		assertFalse(target.exists());
	}

	@Test
	public void testCopyOriginIsDirectory() throws IOException {
		File origin = folder.newFolder();
		File target = new File(PathUtils.PathResolver(folder.getRoot(),
				"hopeExists.txt"));

		IcopyTool.copy(origin, target);

		assertNotEquals(0, IcopyTool.getStatusCode());
		assertFalse(target.exists());
	}

	@Test
	public void testCopyOriginToItself() throws IOException {
		File origin = folder.newFile("originForCopy.txt");

		IcopyTool.copy(origin, origin);

		assertEquals(0, IcopyTool.getStatusCode());
		assertTrue(origin.exists());
	}

	@Test
	public void testCopyTargetFileExists() throws IOException {
		File origin = folder.newFile("originForCopy.txt");
		File target = folder.newFile("targetForCopy.txt");

		IcopyTool.copy(origin, target);

		assertNotEquals(0, IcopyTool.getStatusCode());
	}

	@Test
	public void testCopyTargetIsDirectory() throws IOException {
		File origin = folder.newFile();
		File target = folder.newFolder();
		File realTarget = new File(PathUtils.PathResolver(target, origin.getName()));

		IcopyTool.copy(origin, target);

		assertEquals(0, IcopyTool.getStatusCode());
		assertTrue(realTarget.exists());
	}

	@Test
	public void testCopyTargetUnderNonExistsDirectory() throws IOException {
		File origin = folder.newFile();
		File target = new File(PathUtils.PathResolver(folder.getRoot(), "noDir/noFile.mp3"));

		IcopyTool.copy(origin, target);

		assertNotEquals(0, IcopyTool.getStatusCode());
	}

	@Test
	public void testExecuteNormalCommand() throws IOException {
		folder.newFile("originForCopy.txt");

		IcopyTool = new COPYTool(
				"originForCopy.txt targetForCopy.txt".split(" "));
		IcopyTool.execute(folder.getRoot(), null);

		File target = new File(PathUtils.PathResolver(folder.getRoot(),
				"targetForCopy.txt"));

		assertEquals(0, IcopyTool.getStatusCode());
		assertTrue(target.exists());
	}

	@Test
	public void CopyExecuteWithNullArgs() {
		IcopyTool.execute(null, null);
		assertNotEquals(0, IcopyTool.getStatusCode());
	}

	@Test
	public void CopyExecuteWithLessArgs() {
		IcopyTool = new COPYTool("a".split(" "));
		IcopyTool.execute(null, null);

		assertNotEquals(0, IcopyTool.getStatusCode());
	}

	@Test
	public void CopyExecuteWithNonExistsArgs() {
		IcopyTool = new COPYTool("a.txt b.txt".split(" "));
		IcopyTool.execute(folder.getRoot(), null);

		assertNotEquals(0, IcopyTool.getStatusCode());
	}

}
