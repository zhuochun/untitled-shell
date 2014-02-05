package sg.edu.nus.comp.cs4218.impl.fileutils;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.DiffUtils;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;


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
				if(!origin.exists()){
				     origin.createNewFile();
				}
				if(!target.exists()){
				     target.createNewFile();
				}
				FileWriter fw = new FileWriter(origin);
				BufferedWriter bw = new BufferedWriter(fw);
				
				
				//construct origin file
				for(int i=0;i<50;i++){
				    bw.write("Hello World" + i);
				    bw.flush();
				}
				
				
				
				IcopyTool.copy(origin, target);
				assertTrue(DiffTool.isDifferent(origin, target));
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Test
		public void testFileNotExists(){
			try{
				File origin = folder.newFile("origin.txt");
				File target = folder.newFile("target.txt");
				
				if(!origin.exists()){
				     origin.createNewFile();
				}
				
				FileWriter fw = new FileWriter(origin);
				BufferedWriter bw = new BufferedWriter(fw);
				FileReader fr = new FileReader(target);
				BufferedReader br = new BufferedReader(fr);
				
				//construct origin file
				bw.write("Hello World");
				bw.flush();
				IcopyTool.copy(origin, target);
				
				//assertTrue(!target.exists());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


