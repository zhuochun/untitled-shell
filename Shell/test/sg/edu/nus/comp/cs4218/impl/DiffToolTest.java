//chen hao

package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DiffToolTest {
	DiffUtils diffTool;

	@Before
	public void setUp() throws Exception {
		diffTool = new DiffUtils();
	}

	@After
	public void tearDown() throws Exception {
		diffTool = null;
	}

	@Test
	public void testTwoFilesWithSameContent() {
		try{
			File file1 = new File("file1");
			File file2 = new File("file2");
			
			file1.createNewFile();
			file2.createNewFile();
			
			FileWriter fw1 = new FileWriter (file1);
			FileWriter fw2 = new FileWriter (file2);

			BufferedWriter bw1 = new BufferedWriter (fw1);
			BufferedWriter bw2 = new BufferedWriter (fw2);

			
			for(int i= 0 ; i<50;i++){
				String addIn= new String("Add Something"+i);
				bw1.write(addIn);
				bw2.write(addIn);
				bw1.flush();
				bw2.flush();
			}
			System.out.println("here");
			bw1.close();
			bw2.close();
			
			assertTrue(!diffTool.isDifferent(file1, file2));
			
			file1.delete();
			file2.delete();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testTwoSameMp3File(){
		try{
			File music1 =new File(System.getProperty("user.dir")+"//test//Lenka - Trouble Is A Friend.mp3");
			File music2 = new File (System.getProperty("user.dir")+"//test//Lenka - Trouble Is A Friend - Copy.mp3");
			assertTrue(diffTool.isDifferent(music1, music2));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTwoDiffMp3File(){
		try{
			File music1 =new File(System.getProperty("user.dir")+"//test//Lenka - Trouble Is A Friend.mp3");
			File music2 = new File (System.getProperty("user.dir")+"//test//Frankie Valli - Cant Take My Eyes Off You.mp3");
			assertTrue(diffTool.isDifferent(music1, music2));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTwoFilesWithDifferentContent() {
		try{
			File file1 = new File("file1");
			File file2 = new File("file2");
			
			file1.createNewFile();
			file2.createNewFile();
			
			FileWriter fw1 = new FileWriter (file1);
			FileWriter fw2 = new FileWriter (file2);

			BufferedWriter bw1 = new BufferedWriter (fw1);
			BufferedWriter bw2 = new BufferedWriter (fw2);

			
			for(int i= 0 ; i<50;i++){
				String addIn1 = new String("Add Something"+i);
				String addIn2 = new String("Add Something"+(i+1));
				bw1.write(addIn1);
				bw2.write(addIn2);
			}

			bw1.close();
			bw2.close();
			
			assertTrue(diffTool.isDifferent(file1, file2));
			
			file1.delete();
			file2.delete();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
