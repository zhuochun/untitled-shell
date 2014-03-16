package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.ITool;

public class IntegrationPipeTest {

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
	public void testPipeIncompleteCommand() {
		runCommand("ls |");
		
		assertTrue(outContent.size() == 0);
		assertTrue(errContent.toString().contains("Error"));

		clearStdoutAndStderr();
	}
	
	@Test
	public void testEchoWithGrepCommand() {
		runCommand("echo hello world | grep world");
	}
	
	private void runCommand(String cmd) {
		tool = shell.parse(cmd);

		runnable = new ToolRunnable(tool, "");
		runnable.run();
	}
	
	private void clearStdoutAndStderr() {
		outContent.reset();
		errContent.reset();
	}
}
