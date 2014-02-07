package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

public class MoveTool extends ATool implements IMoveTool{
	
	private ArgList argList = new ArgList();
	public MoveTool(String[] arguments){
		super (arguments);
	}


	@Override
	public boolean move(File from, File to) {

		try{
			if(!from.isFile()){
				throw new FileNotFoundException();
			}
			if(!to.isFile()){
				throw new FileNotFoundException();
			}
				
			 from.renameTo(to);
			 from.delete();
			 return true;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e){
			setStatusCode(9);
			return e.getMessage();
		}
			StringBuilder output = new StringBuilder();
		
			if(stdin == null) {stdin = "";}
			
			if(argList.isEmpty()){
				output.append(stdin);
				}
			else {
				
					
				}
				
			
		
		return null;
	}

}
