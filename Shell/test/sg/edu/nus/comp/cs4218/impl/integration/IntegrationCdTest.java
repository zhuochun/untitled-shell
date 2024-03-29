package sg.edu.nus.comp.cs4218.impl.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.Directory;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.impl.Shell;
import sg.edu.nus.comp.cs4218.impl.ToolRunnable;

public class IntegrationCdTest {

	Shell shell;
	ITool tool;
	File currentPath;
	ToolRunnable runnable;
	Directory dir = new Directory();
	// to capture stdin/stdout
	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception {
		shell = new Shell();
		currentPath = Directory.get();

	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void tearDown() throws Exception {
		shell = null;
		tool = null;
		runnable = null;

	    System.setOut(null);
	    System.setErr(null);
	}

	@Test
	public void testCdRoot() throws ClassNotFoundException {
		tool = shell.parse("cd ~");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals(PathUtils.pathResolver(currentPath, "~"), Directory.get() + "/");
	}
	
	@Test
	public void testCdUp() throws ClassNotFoundException {
		tool = shell.parse("cd ..");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals(PathUtils.pathResolver(currentPath, ".."), Directory.get() + "/");
	}

	@Test
	public void testCdUpAtRoot() {
		String root = PathUtils.pathResolver(currentPath, "/");

		Directory.set(root); // set to root
		tool = shell.parse("cd ../..");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals(root, Directory.get().toString());
	}

	@Test
	public void testCdInvalid() throws ClassNotFoundException {
		tool = shell.parse("cd !");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals("No such file or directory!\n", errContent.toString());
		assertEquals(PathUtils.pathResolver(currentPath, "."), Directory.get() + "/");
	}
}