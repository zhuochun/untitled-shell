package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
				
				if(!origin.exists()){
				     origin.createNewFile();
				}
				assertTrue(origin.exists());
				
			    IDeleteTool.delete(origin);
			    
			    assertFalse(origin.exists());
		
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testDeleteNotExists(){
			try{
				File origin = folder.newFile("originForDdelete.txt");
				assertFalse(origin.exists());
                
				IDeleteTool.delete(origin);
			    
			    assertFalse(origin.exists());
			    
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}