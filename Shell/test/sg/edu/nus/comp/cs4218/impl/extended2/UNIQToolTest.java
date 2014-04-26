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

import sg.edu.nus.comp.cs4218.extended2.IUniqTool;

public class UNIQToolTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private IUniqTool uniqTool;

	@Before
	public void before() {
		uniqTool = new UNIQTool(null);
	}

	@After
	public void after() {
		uniqTool = null;
	}

	// test getUnique method
	@Test
	public void getUniqueTest() {
		String input1 = "ab cd ef";
		String input4 = " AB   cd ef";
		assertEquals(input1, uniqTool.getUnique(true, input1));
		assertEquals(null, uniqTool.getUnique(false, input4));
		assertEquals(input4, uniqTool.getUnique(true, input4));
	}

	// test getUnique method for null
	@Test
	public void getUniqueForNullTest() {
		assertEquals(null, uniqTool.getUnique(true, null));
	}

	// test getUniqueSkipNum method for valid range
	@Test
	public void getUniqueSkipNumValidRangeTest() {
		String input1 = "a b c d e";
		String input2 = "b  b c d e";
		assertEquals(input1, uniqTool.getUniqueSkipNum(1, true, input1));
		assertEquals(input2, uniqTool.getUniqueSkipNum(1, false, input2));
	}

	// test getUniqueSkipNum method for out of range
	@Test
	public void getUniqueSkipNumInvalidRangeTest() {
		String input1 = "a b c d e";
		String input2 = "b  b c d e";
		assertEquals(input1, uniqTool.getUniqueSkipNum(100, true, input1));
		assertEquals(null, uniqTool.getUniqueSkipNum(100, false, input2));
	}

	// test getUniqueSkipNum method for null
	@Test
	public void getUniqueSkipNumForNullTest() {
		assertEquals(null, uniqTool.getUniqueSkipNum(1, true, null));
	}

	@Test
	public void testExecuteGetHelp() {
		uniqTool = new UNIQTool(new String[] { "-help" });

		String stdout = uniqTool.execute(null, null);

		assertEquals(0, uniqTool.getStatusCode());
		assertTrue(stdout.matches("^Command Format -(.|\n)+OPTIONS(.|\n)+$"));
	}

	@Test
	public void testExecuteInvalidOptions() {
		uniqTool = new UNIQTool(new String[] { "-b" });
		uniqTool.execute(null, null);
		assertNotEquals(0, uniqTool.getStatusCode());
	}

	@Test
	public void testExecuteWithoutParams() {
		uniqTool = new UNIQTool(new String[] {});
		uniqTool.execute(null, null);
		assertEquals(0, uniqTool.getStatusCode());
	}

	@Test
	public void testExecuteStdinWithOptionI() {
		uniqTool = new UNIQTool(new String[] { "-i", "-", "-" });
		String stdout = uniqTool.execute(null, "a b c d\nA B c d\n");
		assertEquals("a b c d\n", stdout);
	}

	@Test
	public void testExecuteStdinWithOptionF() {
		uniqTool = new UNIQTool(new String[] { "-f", "2" });
		String stdout = uniqTool.execute(null, "a b c\na b c\n");
		assertEquals("a b c\n", stdout);
	}

	@Test
	public void testExecuteFiles() throws IOException {
		createFile("t1.txt", "a b c d e\n");
		createFile("t2.txt", "A b c d e\n");
		uniqTool = new UNIQTool(new String[] { "t1.txt", "t2.txt", "-" });
		String stdout = uniqTool.execute(folder.getRoot(), null);
		assertEquals("a b c d e\n", stdout);
	}

	@Test
	public void testExecuteFile() throws IOException {
		createFile("test.txt", "a b c d e\n");
		uniqTool = new UNIQTool(new String[] { "test.txt", "-" });
		String stdout = uniqTool.execute(folder.getRoot(), "A b c d e\n");
		assertEquals("a b c d e\n", stdout);
	}

	@Test
	public void testExecuteFileWithOptionI() throws IOException {
		createFile("test.txt", "a b c d e\n");
		uniqTool = new UNIQTool(new String[] { "-i", "test.txt", "-" });
		String stdout = uniqTool.execute(folder.getRoot(), "A b C d e\n");
		assertEquals("a b c d e\n", stdout);
	}

	private File createFile(String filename, String content) throws IOException {
		File file = folder.newFile(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();

		return file;
	}
}
