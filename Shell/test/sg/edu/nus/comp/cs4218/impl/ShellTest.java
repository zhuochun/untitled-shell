package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.extended1.GREPTool;
import sg.edu.nus.comp.cs4218.impl.extended1.PIPINGTool;

public class ShellTest {
	
	Shell shell;
	ITool tool;

	@Before
	public void setUp() throws Exception {
		shell = new Shell();
	}

	@After
	public void tearDown() throws Exception {
		shell = null;
	}

	@Test
	public void testParseFileUtilCommands() throws ClassNotFoundException {
		String[] cmds = new String[] { "cat", "cd", "copy", "delete", "echo",
				"ls", "move", "pwd" };
		
		String prefix = "sg.edu.nus.comp.cs4218.impl.fileutils.";

		// default lowercase
		for (String cmd : cmds) {
			tool = shell.parse(cmd);
			assertEquals(Class.forName(prefix + cmd.toUpperCase() + "Tool"), tool.getClass());
		}

		// uppercase
		for (String cmd : cmds) {
			tool = shell.parse(cmd.toUpperCase());
			assertEquals(Class.forName(prefix + cmd.toUpperCase() + "Tool"), tool.getClass());
		}
	}

	@Test
	public void testParseExtended1Commands() throws ClassNotFoundException {
		tool = shell.parse("grep");
		assertEquals(GREPTool.class, tool.getClass());

		tool = shell.parse("GREP");
		assertEquals(GREPTool.class, tool.getClass());

		tool = shell.parse("|");
		assertEquals(PIPINGTool.class, tool.getClass());
	}

	@Test
	public void testParseExtended2Commands() throws ClassNotFoundException {
		String[] cmds = new String[] { "comm", "cut", "paste", "sort", "uniq", "wc" };
		
		String prefix = "sg.edu.nus.comp.cs4218.impl.extended2.";

		// default lowercase
		for (String cmd : cmds) {
			tool = shell.parse(cmd);
			assertEquals(Class.forName(prefix + cmd.toUpperCase() + "Tool"), tool.getClass());
		}

		// uppercase
		for (String cmd : cmds) {
			tool = shell.parse(cmd.toUpperCase());
			assertEquals(Class.forName(prefix + cmd.toUpperCase() + "Tool"), tool.getClass());
		}
	}

	@Test
	public void testParseNotExistsCommand() throws ClassNotFoundException {
		tool = shell.parse("");
		assertEquals(null, tool);
		tool = shell.parse("       ");
		assertEquals(null, tool);
		tool = shell.parse("grap");
		assertEquals(null, tool);
		tool = shell.parse("sart");
		assertEquals(null, tool);
	}
}
