package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.IWcTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class VerifyBugWCToolTest {
	private IWcTool wcTool;
	File tmpFile1;
	File tmpFile2;

	public static void writeFile(String fileName, String s) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(s);
		out.close();
	}

	
	@Before
	public void before() throws IOException{
		wcTool = new WCTool(null);
		tmpFile1 = new File("tmpFile1.txt");
		tmpFile1.createNewFile();
		writeFile("tmpFile1.txt", "hello world");
		
		tmpFile2 = new File("tmpFile2.txt");
		tmpFile2.createNewFile();
		writeFile("tmpFile2.txt", "hello world");
	}

	@After
	public void after() {
		wcTool = null;
		File file1 = new File("tmpFile1.txt");
		if (file1.exists()) {
			file1.delete();
		}

		File file2 = new File("tmpFile2.txt");
		if (file2.exists()) {
			file2.delete();
		}
		
	}
	
	@Test
	public void testHasParamButNoOption(){
		IWcTool newWCTool = new WCTool(new String[]{"", "tmpFile1.txt"});
		String result = newWCTool.execute(PathUtils.getCurrentPath().toFile(), null);
		String expectedResult = wcTool.getCharacterCount("hello world")+" "+ 
		wcTool.getWordCount("hello world") + " " +
		wcTool.getNewLineCount("hello world");		
		assertEquals(result, expectedResult);
	}
	

	


}
