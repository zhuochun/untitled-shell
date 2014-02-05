package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.COPYTool;
public class MoveTool extends ATool implements IMoveTool{
	
	public MoveTool(String[] arguments){
		super (arguments);
	}


	@Override
	public boolean move(File from, File to) {
		// TODO Auto-generated method stub
		try{
			if(!from.isFile()){
				throw new FileNotFoundException();
			}
			if(!to.isFile()){
				throw new FileNotFoundException();
			}
			else {	
				FileReader fr = new FileReader(from);
			    FileWriter fw = new FileWriter(to);
			    BufferedReader bs = new BufferedReader (fr);
			    BufferedWriter bw = new BufferedWriter (fw);
			    String buffer = new String();;
			    
			    while((buffer=bs.readLine()) != null){
			    	bw.write(buffer);
			    	bw.flush();

			    }
			    
			  from.delete();
			}	    
		} catch(Exception e){
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
