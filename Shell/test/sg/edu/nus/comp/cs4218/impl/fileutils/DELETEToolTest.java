// chen hao
package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;


public class DELETEToolTest {
	
		
		IDeleteTool IDeleteTool;
		
		 @Rule
		 public TemporaryFolder folder = new TemporaryFolder();

		@Before
		public void setUp() throws Exception {
			IDeleteTool = new DELETETool(null);
		}

		@After
		public void tearDown() throws Exception {
			IDeleteTool = null;
		}
		@Test
		public void testDeleteFileExists(){
			try{
				File origin = folder.newFile("originForDelete.txt");
				
				assertTrue(origin.exists());
				
			    IDeleteTool.delete(origin);
			    
			    assertFalse(origin.exists());
		
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testDeleteEmptyDirectoryExists(){
			try{
				File origin = folder.newFolder("originForDelete");
				
				assertTrue(origin.exists());
				
			    IDeleteTool.delete(origin);
			    
			    assertFalse(origin.exists());
		
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void testDeleteNotEmptyDirectoryExists(){
			try{
				File origin = folder.newFile("originFileDelete.txt");
				
				
				assertTrue(folder.getRoot().exists());
				
			    IDeleteTool.delete(folder.getRoot());
			    
			    assertTrue(folder.getRoot().exists());
		
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}