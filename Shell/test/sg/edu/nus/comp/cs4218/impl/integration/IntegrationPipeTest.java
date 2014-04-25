package sg.edu.nus.comp.cs4218.impl.integration;

import static org.junit.Assert.*;

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
import sg.edu.nus.comp.cs4218.impl.Directory;
import sg.edu.nus.comp.cs4218.impl.Shell;
import sg.edu.nus.comp.cs4218.impl.ToolRunnable;

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
	public void testPipeInvalidCommand() {
		runCommand("ls | omg");
		
		assertTrue(outContent.size() == 0);
		assertTrue(errContent.toString().contains("Error"));

		clearStdoutAndStderr();
	}
	
	@Test
	public void testEchoWithGrepCommand() {
		runCommand("echo hello world | grep world");
		assertEquals("hello world\n", outContent.toString());

		clearStdoutAndStderr();

		runCommand("echo hello world | grep nothing");
		assertEquals("\n", outContent.toString());

		clearStdoutAndStderr();
	}

	@Test
	public void testLsCatGrepCommand() throws IOException {
		// set current dir to test folder
		Directory.set(folder.getRoot());
		// create a list files in test folder
		folder.newFile("test.txt");
		folder.newFolder("uniq");
		folder.newFolder("testHello");
		folder.newFile("abc.exe");

		runCommand("ls | cat | grep test");
		assertEquals("test.txt\ntestHello\n", outContent.toString());
		clearStdoutAndStderr();
		
		runCommand("ls | cat | grep -c te"); // test with option
		assertEquals("2\n", outContent.toString());
		clearStdoutAndStderr();
	}
	
	@Test
	public void testEchoPasteCommand() {
		runCommand("echo hello | paste -");
		assertEquals("hello\t\n\n", outContent.toString());
		clearStdoutAndStderr();
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
