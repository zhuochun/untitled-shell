//chen hao
package sg.edu.nus.comp.cs4218.impl.fileutils;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.DiffUtils;


public class COPYToolTest {
	
		
		ICopyTool IcopyTool;
		DiffUtils DiffTool;
		
		@Rule
		 public TemporaryFolder folder = new TemporaryFolder();

		@Before
		public void setUp() throws Exception {
			IcopyTool = new COPYTool(null);
			DiffTool = new DiffUtils();
		}

		@After
		public void tearDown() throws Exception {
			IcopyTool = null;
			DiffTool = null;
		}
		@Test
		public void testCopyFileExists(){
			try{
				File origin = folder.newFile("originForCopy.txt");
				File target = folder.newFile("targetForCopy.txt");
				
				FileWriter fw = new FileWriter(origin);
				BufferedWriter bw = new BufferedWriter(fw);
				
				for(int i=0;i<50;i++){
				    bw.write("Hello World" + i);
				    bw.flush();
				}
				bw.close();

				IcopyTool.copy(origin, target);
				assertTrue(!DiffTool.isDifferent(origin, target));	
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testCopyFileNotTxtExists(){
			try{
			
				File origin =new File(System.getProperty("user.dir")+"//test//Frankie Valli - Cant Take My Eyes Off You.mp3");
				File target = new File(System.getProperty("user.dir")+"//test//Frankie Valli - Cant Take My Eyes Off You_copy.mp3");
				target.delete();
				target.createNewFile();
				IcopyTool.copy(origin, target);
				assertTrue(!DiffTool.isDifferent(origin, target));
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testExecuteNormalCommand(){
			try{
				File origin = folder.newFile("originForCopy.txt");
				File target = folder.newFile("targetForCopy.txt");
				
				FileWriter fw = new FileWriter(origin);
				BufferedWriter bw = new BufferedWriter(fw);
				
				for(int i=0;i<50;i++){
				    bw.write("Hello World" + i);
				    bw.flush();
				}
				bw.close();

				String result = IcopyTool.execute(folder.getRoot(), "copy "+"originForCopy.txt "+"targetForCopy.txt");
				assertTrue(!DiffTool.isDifferent(origin, target));	
				assertEquals(result, "copy successful");
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void CopyExecuteWihtNullSdtin(){
			String result = IcopyTool.execute(null, null);
			assertEquals(result,"copy commmand must have two params");
		}
		
	}


