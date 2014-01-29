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


public class COPYToolTest {
	
		
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
		public void testCopyFileExists(){
			try{
				File origin = new File("originForCopy.txt");
				File target = new File("targetForCopy.txt");
				if(!origin.exists()){
				     origin.createNewFile();
				}
				if(!target.exists()){
				     target.createNewFile();
				}
				FileWriter fw = new FileWriter(origin);
				BufferedWriter bw = new BufferedWriter(fw);
				FileReader fr = new FileReader(target);
				BufferedReader br = new BufferedReader(fr);
				
				//construct origin file
				bw.write("Hello World");
				bw.flush();
				String in = new String ("Hello World");
				
				
				assertEquals(br.readLine(),null);
				
				IcopyTool.copy(origin, target);
				br = new BufferedReader(fr);
				assertEquals(br.readLine(), in);
				
				origin.delete();
				target.delete();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testFileNotExists(){
			try{
				File origin = new File("origin.txt");
				File target = new File("target.txt");
				
				assertThrowsExcption(IcopyTool.copy(origin, target));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


