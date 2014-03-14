package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.ITool;

public class ShellStateTest {

	Shell shell;
	ITool tool;
	File currentPath;
	ToolRunnable runnable;

	@Before
	public void setUp() throws Exception {
		shell = new Shell();
		currentPath = Directory.get();
	}

	@After
	public void tearDown() throws Exception {
		shell = null;
		tool = null;
		runnable = null;
	}

	@Test
	public void testCdRoot() throws ClassNotFoundException {
		tool = shell.parse("cd ~");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals(PathUtils.PathResolver(currentPath, "~"), Directory.get() + "/");
	}

	@Test
	public void testCdUp() throws ClassNotFoundException {
		tool = shell.parse("cd ..");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals(PathUtils.PathResolver(currentPath, ".."), Directory.get() + "/");
	}
}