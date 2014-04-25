package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTTool;

public class NewSORTToolTest {

	private ISortTool sortTool;
	File testFile1;
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
		testFile1 = new File("unSortFile.txt");
		testFile1.createNewFile();
		writeFile("unSortFile.txt", "lorem ipsum\ndolor sit amet");

		// creating testFile of sorted order
		myFile2 = new File("sortFile.txt");
		myFile2.createNewFile();
		writeFile("sortFile.txt", "aaa\r\nbbb\r\nccc\r\nddd\r\neee");
	}
	
	@Test
	public void testCheckIfSortedForUnsortedFile(){
		String result = sortTool.checkIfSorted(PathUtils.getCurrentPath()+ "\\" + "unSortFile.txt");
		assertEquals(result, "sort: unSortFile.txt:2 disorder: dolor sit amet\n");
	}

}
