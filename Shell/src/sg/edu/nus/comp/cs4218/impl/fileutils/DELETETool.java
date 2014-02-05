package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.FileNotFoundException;

import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class DELETETool extends ATool implements IDeleteTool {

	public DELETETool(String[] arguments) {
		super(arguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean delete(File toDelete) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
