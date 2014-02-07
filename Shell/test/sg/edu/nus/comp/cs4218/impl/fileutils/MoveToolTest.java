package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.impl.DiffUtils;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
public class MoveToolTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	 
	private IMoveTool movetool;
	private DiffUtils DiffTool;
	@Before
	public void setUp() throws Exception {
		movetool = new MoveTool(null);
	}

	@After
	public void tearDown() throws Exception {
		movetool = null;
	}
	
	@Test
	public void testMovedFileExists(){
		try{
			File src = folder.newFile("Src.txt");
			File dest = folder.newFile("C://Dest.txt");

		
			movetool.move(src, dest);
			assertTrue(dest.exists());
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	@Test
	public void testSrcFileNotExists(){
		try{
			File src = folder.newFile("Src.txt");
			File dest = folder.newFile("Dest.txt");
			
	
			movetool.move(src, dest);
			
			assertTrue(!src.exists());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testBeforeMove(){
		try{
				File src = folder.newFile("Src.txt");
				File dest = folder.newFile("Dest.txt");

				FileWriter fw = new FileWriter(src);
				BufferedWriter bw = new BufferedWriter(fw);
				FileReader fr = new FileReader(dest);
				BufferedReader br = new BufferedReader(fr);
				
				//construct origin file
				String content = "abcde12345ABC@#$%-;()*";
				bw.write(content);
				bw.flush();
				//String in = new String (content);
				
				
				assertEquals(br.readLine(),null);
				
				movetool.move(src, dest);
				br = new BufferedReader(fr);
				bw.close();
				br.close();

				
				
			}catch(Exception e){
				e.printStackTrace();
			}
	}
		@Test
		public void testAfterMove(){
			try{
					File src = folder.newFile("Src.txt");
					File dest = folder.newFile("Dest.txt");
	/*				if(!src.exists()){
					     src.createNewFile();
					}
					if(!dest.exists()){
					     dest.createNewFile();
					}
					*/
					FileWriter fw = new FileWriter(src);
					BufferedWriter bw = new BufferedWriter(fw);
					FileReader fr = new FileReader(dest);
					BufferedReader br = new BufferedReader(fr);
					
					//construct origin file
					String content = "abcde12345ABC@#$%-;()*";
					bw.write(content);
					bw.flush();
					String in = new String (content);
					
					
					assertEquals(br.readLine(), null);
					
					movetool.move(src, dest);
					br = new BufferedReader(fr);
	
					bw.close();
					br.close();
					assertEquals(br.readLine(), in);
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
		}
@Test
public void testExcuteWithNullStdin(){
	assertEquals(movetool.execute(null,null), "Move command need to have Two params");
}


}