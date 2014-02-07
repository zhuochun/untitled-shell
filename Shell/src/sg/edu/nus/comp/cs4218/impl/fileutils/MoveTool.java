package sg.edu.nus.comp.cs4218.impl.fileutils;


import java.io.File;
import java.io.FileNotFoundException;


import java.nio.file.Files;

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
			if(!to.exists()){
					;
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
		// make sure stdin exists
				if (stdin == null) { 
					stdin = "";
					return "Move command need to have Two params";
				}
				
				//TODO: at this moment we assume no stdin. confirm with others
				// how to parse stdin!!!
				
				// parse arguments
				try {
					argList.parseArgs(this.args);
				} catch (IllegalArgumentException e) {
					setStatusCode(9);
					return e.getMessage();
				}
				
				if(move(new File(argList.getParam(0)),new File (argList.getParam(1)))){
					return "Move File Successful!";
				}
				else{
					setStatusCode(9);
					return "Move File Failed";
				}
			}

}
