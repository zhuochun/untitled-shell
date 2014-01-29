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
import org.junit.Test;

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;


public class DELETEToolTest {
	
		
		ICopyTool IcopyTool;

		@Before
		public void setUp() throws Exception {
			IcopyTool = new COPYTool(null);
		}

		@After
		public void tearDown() throws Exception {
			IcopyTool = null;
		}
		@Test
		public void testDeleteFileExists(){
			try{
				File origin = new File("originForDelete.txt");
				
				if(!origin.exists()){
				     origin.createNewFile();
				}
				assertFalse(origin.exists());
		
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testDeleteNotExists(){
			try{
				File origin = new File("originForDdelete.txt");
				
				//assertThrowsExcption(IcopyTool.copy(origin, target));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}