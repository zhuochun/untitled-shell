package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;

public class COPYTool extends ATool implements ICopyTool{

	public COPYTool(String[] arguments) {
		super(arguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean copy(File from, File to) {
		try{
			if(!from.isFile()){
				throw new FileNotFoundException("Missing original file"); 
			}
			if(!to.isFile()){
				throw new FileNotFoundException("Missing target file"); 
			}
		    FileReader fr = new FileReader(from);
		    FileWriter fw = new FileWriter(to);
		    BufferedReader bs = new BufferedReader (fr);
		    BufferedWriter bw = new BufferedWriter (fw);
		    String buffer = new String();;
		    
		    while((buffer=bs.readLine()) != null){
		    	bw.write(buffer);
		    	bw.flush();
		    }
		}catch (FileNotFoundException e){
			e.printStackTrace();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
