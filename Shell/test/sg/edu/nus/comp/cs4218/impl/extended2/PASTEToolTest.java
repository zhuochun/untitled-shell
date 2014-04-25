package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.extended2.IPasteTool;

public class PASTEToolTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

	private IPasteTool pasteTool;
	
	@Before
	public void before() {
		pasteTool = new PASTETool(null);
	}

	@After
	public void after() {
		pasteTool = null;
	}
	
	//Test pasteSerial method
	//@Corrected
	@Test
	public void pasteSerialTest() {
		String[] input = {"1","2","3","4","5","6"};
		String output = "1\t2\t3\t4\t5\t6\n";
		assertEquals(output, pasteTool.pasteSerial(input));
	}

	@Test
	public void pasteSerialNullTest() {
		String[] input = {"1","2","3","4","5","6", null};
		String output = "1\t2\t3\t4\t5\t6\t\n";
		assertEquals(output, pasteTool.pasteSerial(input));
	}

	@Test
	public void pasteSerialNullTest2() {
		String[] input = {"1","2","3", null,"4","5","6"};
		String output = "1\t2\t3\t4\t5\t6\n";
		assertEquals(output, pasteTool.pasteSerial(input));
	}
	
	//Test pasteUseDelimiter method
	//@Corrected
	@Test
	public void pasteUseDelimiterTest(){
		String[] input = {"1","2","3","4","5","6"};
		String output = "1|2|3|4|5|6\n";
		assertEquals(output, pasteTool.pasteUseDelimiter("|",input));		
	}
	
	@Test
	public void pasteUseDelimiterNullTest(){
		String[] input = {"1","2","3","4","5","6", null};
		String output = "1|2|3|4|5|6|\n";
		assertEquals(output, pasteTool.pasteUseDelimiter("|",input));		
	}

	@Test
	public void pasteUseDelimiterNullTest2(){
		String[] input = {"1","2","3","4",null,"5","6"};
		String output = "1|2|3|4|5|6\n";
		assertEquals(output, pasteTool.pasteUseDelimiter("|",input));		
	}

	@Test
	public void testExecuteGetHelp() {
		pasteTool = new PASTETool(new String[] { "-help" });

		String stdout = pasteTool.execute(null, null);

		assertEquals(0, pasteTool.getStatusCode());
		assertTrue(stdout.matches("^Command Format -(.|\n)+OPTIONS(.|\n)+$"));
	}

	@Test
	public void textExecuteInvalidOptions() {
		pasteTool = new PASTETool(new String[] { "-i" });
		pasteTool.execute(null, null);
		assertNotEquals(0, pasteTool.getStatusCode());
	}

	@Test
	public void testExecuteWithoutParams() {
		pasteTool = new PASTETool(new String[] {});
		pasteTool.execute(null, null);
		assertNotEquals(0, pasteTool.getStatusCode());
	}
	
	@Test
	public void testExecuteStdin() {
		pasteTool = new PASTETool(new String[] { "-", "-" });
		String stdout = pasteTool.execute(null, "1\n2\n");
		assertEquals("1\t1\n2\t2\n", stdout);		
	}

	@Test
	public void testExecuteStdinWithOptionD() {
		pasteTool = new PASTETool(new String[] { "-d", "|", "-", "-" });
		String stdout = pasteTool.execute(null, "1\n2\n");
		assertEquals("1|1\n2|2\n", stdout);		
	}

	@Test
	public void testExecuteFiles() throws IOException {
		createFile("test1.txt", "1\n2\n3\n");
		createFile("test2.txt", "1\n2\n3\n4\n");
		pasteTool = new PASTETool(new String[] { "test1.txt", "test2.txt" });
		String stdout = pasteTool.execute(folder.getRoot(), null);
		assertEquals("1\t1\n2\t2\n3\t3\n\t4\n", stdout);		
	}

	@Test
	public void testExecuteFile() throws IOException {
		createFile("test.txt", "1\n2\n3\n");
		pasteTool = new PASTETool(new String[] { "test.txt", "-" });
		String stdout = pasteTool.execute(folder.getRoot(), "1\n2\n");
		assertEquals("1\t1\n2\t2\n3\t\n", stdout);		
	}

	@Test
	public void testExecuteFileWithOptionS() throws IOException {
		createFile("test.txt", "1\n2\n");
		pasteTool = new PASTETool(new String[] { "-s", "test.txt", "-" });
		String stdout = pasteTool.execute(folder.getRoot(), "1\n2\n");
		assertEquals("1\t2\n1\t2\n", stdout);		
	}

	private File createFile(String filename, String content) throws IOException {
		File file = folder.newFile(filename);

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();

        return file;
	}
}