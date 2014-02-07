//chen hao
package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.FileNotFoundException;

import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

public class DELETETool extends ATool implements IDeleteTool {

	private ArgList argList = new ArgList();

	public DELETETool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;
	}

	@Override
	//We can delete file or empty directory
	public boolean delete(File toDelete) {
		try{
			if(!toDelete.exists())
				throw new FileNotFoundException();
			else{
				if(toDelete.isDirectory()){
					// If it is a directory and not empty, cannot delete it 
					if(toDelete.list().length>0){
						System.out.println("Cannot delete the directory" + 
								toDelete.toString()+" since the folder is not empty");
						return false;
					}
					else{
						toDelete.delete();
						return true;
					}
				}
				// if the directory is empty, delete it
				else{
					// Delete the file
					toDelete.delete();
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// make sure stdin exists
		if (stdin == null) { 
			stdin = ""; 
			return "delete command must have 1 param";
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

		if(argList.getParams().length>=1){
			if(delete(new File(argList.getParam(0)))){
				return "delete successful";
			}
			else{
				setStatusCode(9);
				return "delete unsuccessful";
			}
		}
		else{
			setStatusCode(9);
			return "the number of parameters should be more than 1";
		}
	}

}
