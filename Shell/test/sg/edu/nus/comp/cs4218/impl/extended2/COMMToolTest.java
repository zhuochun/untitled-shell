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

import sg.edu.nus.comp.cs4218.extended2.ICommTool;

public class COMMToolTest {

	private static ICommTool commTool; 

	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	} 

	@BeforeClass 
	public static void executeThisBeforeClass() throws IOException{

		File myFile1 = new File("testFile1.txt");
		myFile1.createNewFile();
		writeFile("testFile1.txt", "aaa\r\nbbb\r\nccc\r\nddd");

		File myFile2 = new File("testFile2.txt");
		myFile2.createNewFile();
		writeFile("testFile2.txt", "aaf\r\nabb\r\nccc\r\nfff");

		//testFile 3 will be the file in unsorted order 
		File myFile3 = new File("testFile3.txt");
		myFile3.createNewFile();
		writeFile("testFile3.txt", "zzz\r\nccc\r\naaa\r\nbbb");

	}

	@AfterClass 
	public static void executeThisAfterClass(){

		File myFile1 = new File("testFile1.txt");
		myFile1.delete();

		File myFile2 = new File("testFile2.txt");
		myFile2.delete();

		File myFile3 = new File("testFile3.txt");
		myFile3.delete();
			
	}

	@Before
	public void before() throws IOException{
		commTool = new COMMTool(null);
	}

	@After
	public void after(){
		commTool = null;
	}


	//test compareFiles method, with sorted file
	@Test
	public void compareFilesSortedFile() {
		String result = commTool.compareFiles("testFile1.txt", "testFile2.txt");
		assertEquals(result , "aaa\n\taaf\n\tabb\nbbb\n\t\tccc\nddd\n\tfff\n");
	}

	//test compareFiles method, without sorted file
	@Test
	public void compareFilesUnSortedFile() {
		String result = commTool.compareFiles("testFile1.txt", "testFile3.txt");
		assertEquals(result , "aaa\nbbb\nccc\nddd\n\tzzz\ncomm: File 2 is not in sorted order \n\tccc\n\taaa\n\tbbb\n");
	}
	
	//test compareFilesCheckSortStatus method, with sorted
	@Test
	public void compareFilesCheckSortStatusSortedFile() throws IOException { 

		String result = commTool.compareFilesCheckSortStatus("testFile1.txt", "testFile2.txt");
		assertEquals(result , "aaa\n\taaf\n\tabb\nbbb\n\t\tccc\nddd\n\tfff\n");
	}


	//test compareFilesCheckSortStatus method, without sorted
	@Test
	public void compareFilesCheckSortStatusNotSortedFile() throws IOException { 
		String result = commTool.compareFilesCheckSortStatus("testFile1.txt", "testFile3.txt");
		assertEquals(result , "aaa\nbbb\nccc\nddd\n\tzzz\ncomm: File 2 is not in sorted order \n");
	}
	
	//test compareFilesDoNotCheckSortStatus method, with sorted
	@Test
	public void compareFilesDoNotCheckSortStatusSortedFile() throws IOException { 

		String result = commTool.compareFilesDoNotCheckSortStatus("testFile1.txt", "testFile2.txt");
		assertEquals(result , "aaa\n\taaf\n\tabb\nbbb\n\t\tccc\nddd\n\tfff\n");
	}
	
	//test compareFilesDoNotCheckSortStatus method, without sorted
	@Test
	public void compareFilesDoNotCheckSortStatusNotSortedFile() throws IOException { 
		String result = commTool.compareFilesDoNotCheckSortStatus("testFile1.txt", "testFile3.txt");
		assertEquals(result , "aaa\nbbb\nccc\nddd\n\tzzz\n\tccc\n\taaa\n\tbbb\n");

	}


}
