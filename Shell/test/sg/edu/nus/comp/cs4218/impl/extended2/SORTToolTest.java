package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class SORTToolTest {

	private ISortTool sortTool;
	File myFile1;
	File myFile2;

	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	}

	@Before
	public void before() throws IOException {
		sortTool = new SORTTool(null);
		// creating testFile of unsorted order
		myFile1 = new File("unSortFile.txt");
		myFile1.createNewFile();
		writeFile("unSortFile.txt", "zzz\r\nbbb\r\naaa\r\nggg\r\nfff");

		// creating testFile of sorted order
		myFile2 = new File("sortFile.txt");
		myFile2.createNewFile();
		writeFile("sortFile.txt", "aaa\r\nbbb\r\nccc\r\nddd\r\neee");
	}

	@After
	public void after() {
		sortTool = null;
		File file1 = new File("sortFile.txt");
		if (file1.exists()) {
			file1.delete();
		}

		File file2 = new File("unSortFile.txt");
		if (file2.exists()) {
			file2.delete();
		}
	}

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

	//test checkIfSorted method on unsorted file
	@Test
	public void checkIfSortedTestForUnsortedFile() {// the input is file, function input in a string recheck
		String result = sortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/" + "unSortFile.txt");
		assertEquals(result, "sort: unSortFile.txt:2 disorder: bbb\n");
	}

	//test checkIfSorted method on sorted file
	@Test
	public void checkIfSortedTestForSortedFile() {
		String result = sortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/"+"sortFile.txt");
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
		ISortTool newSortTool = new SORTTool(new String[]{"sortFile.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, newSortTool.sortFile("aaa\r\nbbb\r\nccc\r\nddd\r\neee"));


	}
	@Test
	public void executeWithCOption(){
		ISortTool newSortTool = new SORTTool(new String[]{"-c", "sortFile.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result,newSortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/" + "sortFile.txt"));

	}

	@Test
	public void executeWithHelpOption(){
		ISortTool newSortTool = new SORTTool(new String[]{"-help"});
		String result = newSortTool.execute(null, null);
		assertEquals(result,newSortTool.getHelp());
	}


}
