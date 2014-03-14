package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.ITool;

public class ShellComboToolTest {

	Shell shell;
	ITool tool;
	File currentPath;
	ToolRunnable runnable;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

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
	public void testBasicCombo() throws ClassNotFoundException, IOException {
		// pwd
		tool = shell.parse("pwd");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals(currentPath.toString() + "\n", outContent.toString());
		outContent.reset(); // reset the output buffer to zero
		
		// cd ..
		tool = shell.parse("cd ..");
		runnable = new ToolRunnable(tool, "");
		runnable.run();
		
		// pwd
		tool = shell.parse("pwd");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertNotEquals(currentPath.toString() + "\n", outContent.toString());
		outContent.reset(); // reset the output buffer to zero
		
		// change to temp folder for testing
		Directory.set(folder.getRoot());

		folder.newFile("file1.txt");
		folder.newFolder("testDir");
		folder.newFile("file2.java");
		
		// ls
		tool = shell.parse("ls");

		runnable = new ToolRunnable(tool, "");
		runnable.run();

		assertEquals("file1.txt\nfile2.java\ntestDir\n", outContent.toString());
		outContent.reset(); // reset the output buffer to zero
	}

}