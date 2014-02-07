//chen hao

package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;

public class COPYTool extends ATool implements ICopyTool{
	
	private ArgList argList = new ArgList();
	
	public COPYTool(String[] arguments) {
		super(arguments);
		
		argList.invalidOptionCheck = true;
		
	}

	@Override
	public boolean copy(File from, File to) {
		try{
			if(!from.isFile()){
				throw new FileNotFoundException("Missing original file"); 
			}
			if(!to.isFile()){
				to.createNewFile();
			}
			Files.copy(from.toPath(),to.toPath());
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
		
		// make sure stdin exists
		if (stdin == null) { 
			stdin = "";
		    return "copy commmand must have two params";
		
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
		
		if(copy(new File(argList.getParam(1)),new File (argList.getParam(2)))){
			return "copy successful";
		}
		else{
			setStatusCode(9);
			return "copy unsuccessful";
		}
	}
}
