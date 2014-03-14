package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;

public class SORTToolTest {

	private static ISortTool sortTool;

	@BeforeClass
	public static void executeThisBeforeClass() throws IOException {

		// creating testFile of unsorted order
		File myFile1 = new File("unSortFile.txt");
		myFile1.createNewFile();
		writeFile("unSortFile.txt", "zzz\r\nbbb\r\naaa\r\nggg\r\nfff");

		// creating testFile of sorted order
		File myFile2 = new File("sortFile.txt");
		myFile2.createNewFile();
		writeFile("sortFile.txt", "aaa\r\nbbb\r\nccc\r\nddd\r\neee");

	}

	@Before
	public void before() throws IOException {
		sortTool = new SORTTool(null);
	}

	@After
	public void after() {
		sortTool = null;
	}

	@AfterClass
	public static void executeThisAfterClass() {

		File file1 = new File("sortFile.txt");
		if (file1.exists()) {
			file1.delete();
		}

		File file2 = new File("unSortFile.txt");
		if (file2.exists()) {
			file2.delete();
		}
	}
	
	//test sortFile method on sorted file
	// input should be string 
	@Test
	public void sortFileTestForSortedFile() {
		String result = sortTool.sortFile("aaa\r\nbbb\r\nccc\r\nddd\r\neee");
		assertEquals(result, "aaa\nbbb\nccc\nddd\neee\n");
	}

	//test sortFile method on unsorted file
	@Test
	public void sortFileTestForUnsortedFile() {
		String result = sortTool.sortFile("zzz\r\nbbb\r\naaa\r\nggg\r\nfff");
		assertEquals(result, "aaa\nbbb\nfff\nggg\nzzz\n");
	}

	//test checkIfSorted method on sorted file
	@Test
	public void checkIfSortedTestForUnsortedFile() {// the input is file, function input in a string recheck
		String result = sortTool.checkIfSorted("zzz\r\nbbb\r\naaa\r\nggg\r\nfff");
		assertEquals(result, "sort: sortFile.txt:2 disorder: bbb\n");
	}

	//test checkIfSorted method on unsorted file
	@Test
	public void checkIfSortedTestForSortedFile() {
		String result = sortTool.checkIfSorted("aaa\r\nbbb\r\nccc\r\nddd\r\neee");
		assertEquals(result, "");
	}

	@Test
	public void executeWithIllegalOption(){
		ISortTool newSortTool = new SORTTool(new String[]{"-i"});
		newSortTool.execute(null, null);
		assertEquals(9,newSortTool.getStatusCode());
	}
	
	@Test
	public void executeWithNoOption(){
		ISortTool newSortTool = new SORTTool(new String[]{"aaa bbb"});
		String result = newSortTool.execute(null, null);
		assertEquals(result, newSortTool.sortFile("aaa bbb"));
	}
	
	@Test
	public void executeWithHelpOption(){
		ISortTool newSortTool = new SORTTool(new String[]{"-help"});
		String result = newSortTool.execute(null, null);
		assertEquals(result,newSortTool.getHelp());
	}
	
	@Test
	public void executeWithCOption(){
		ISortTool newSortTool = new SORTTool(new String[]{"-c", "aaa bbb"});
		String result = newSortTool.execute(null, null);
		assertEquals(result,newSortTool.checkIfSorted("aaa bbb"));
	}
	
	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	}
}
