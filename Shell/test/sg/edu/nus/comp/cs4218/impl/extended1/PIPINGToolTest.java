package sg.edu.nus.comp.cs4218.impl.extended1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PIPINGToolTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testExecuteEmptyPipe() {
		PIPINGTool pipe = new PIPINGTool("|".split(" "));
		
		pipe.execute(null, null);
		
		assertNotEquals(0, pipe.getStatusCode());
	}
	
	@Test
	public void testExecutePartialEmptyPipe() {
		PIPINGTool pipe = new PIPINGTool("ls |".split(" "));
		
		pipe.execute(null, null);
		
		assertNotEquals(0, pipe.getStatusCode());
	}

	@Test
	public void testExecutePipeWithNoExistsTool() {
		PIPINGTool pipe = new PIPINGTool("omg |".split(" "));
		
		pipe.execute(null, null);
		
		assertNotEquals(0, pipe.getStatusCode());
	}

	@Test
	public void testExecutePipeTwoTools() throws IOException {
		folder.newFolder("testFolder");
		
		PIPINGTool pipe = new PIPINGTool("ls | cat -".split(" "));
		
		String stdout = pipe.execute(folder.getRoot(), null);
		
		assertEquals("testFolder", stdout);
		assertEquals(0, pipe.getStatusCode());
	}

	@Test
	public void testExecutePipeTwoToolsWithArgs() throws IOException {
		folder.newFolder("testFolder");
		
		PIPINGTool pipe = new PIPINGTool("echo hello world | cat -".split(" "));
		
		String stdout = pipe.execute(folder.getRoot(), null);
		
		assertEquals("hello world\n", stdout);
		assertEquals(0, pipe.getStatusCode());
	}

	@Test
	public void testExecutePipeThreeTools() throws IOException {
		folder.newFolder("testFolder");
		
		PIPINGTool pipe = new PIPINGTool("ls | cat - | cat".split(" "));
		
		String stdout = pipe.execute(folder.getRoot(), null);
		
		assertEquals("testFolder", stdout);
		assertEquals(0, pipe.getStatusCode());
	}
	
	@Test
	public void testExecutePipeFromToolsWithInvalidOptions() throws IOException {
		folder.newFolder("testFolder");
		
		PIPINGTool pipe = new PIPINGTool("ls -D | cat -".split(" "));
		
		pipe.execute(folder.getRoot(), null);
		
		assertNotEquals(0, pipe.getStatusCode());
	}

	@Test
	public void testExecutePipeToToolWithInvalidOptions() throws IOException {
		folder.newFolder("testFolder");
		
		PIPINGTool pipe = new PIPINGTool("ls | cat -G -".split(" "));
		
		pipe.execute(folder.getRoot(), null);
		
		assertNotEquals(0, pipe.getStatusCode());
	}
	
	@Test
	public void testExecutePipe3rdToToolWithInvalidOptions() throws IOException {
		folder.newFolder("testFolder");
		
		PIPINGTool pipe = new PIPINGTool("ls | cat - | cat -G".split(" "));
		
		pipe.execute(folder.getRoot(), null);
		
		assertNotEquals(0, pipe.getStatusCode());
	}
}