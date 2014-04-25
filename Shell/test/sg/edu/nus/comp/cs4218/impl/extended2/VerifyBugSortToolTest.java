package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

/* This test case is used to verify the bug 5.1 and bug 5.2
 * 
 */

public class VerifyBugSortToolTest {
	private ISortTool sortTool;
	File testData3;
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
		testData3 = new File("testData3.txt");
		testData3.createNewFile();
		writeFile("testData3.txt", "lorem ipsum\ndolor sit amet");

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

		File file2 = new File("testData3.txt");
		if (file2.exists()) {
			file2.delete();
		}
	}
	@Test
	public void checkIfSortedTestForUnsortedFile() {// the input is file, function input in a string recheck
		String result = sortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/" + "testData3.txt");
		assertEquals(result, "sort: testData3.txt:2 disorder: dolor sit amet\n");
	}
	
	@Test
	public void checkIfSortedForMultipleFile(){
		ISortTool newSortTool = new SORTTool(new String[]{"-c", "sortFile.txt","testData3.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result,newSortTool.checkIfSorted(PathUtils.getCurrentPath()+ "/" + "sortFile.txt"));
	}
	
	@Test
	public void sortedTestForMultipleFile(){
		ISortTool newSortTool = new SORTTool(new String[]{"sortFile.txt","testData3.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, newSortTool.sortFile("aaa\r\nbbb\r\nccc\r\nddd\r\neee"));
	}
	
	@Test
	public void sortedTestForMultipleFileTwo(){
		ISortTool newSortTool = new SORTTool(new String[]{"testData3.txt", "sortFile.txt"});
		String result = newSortTool.execute(PathUtils.getCurrentPath().toFile(), null);
		assertEquals(result, newSortTool.sortFile("lorem ipsum\ndolor sit amet"));
	}
}
