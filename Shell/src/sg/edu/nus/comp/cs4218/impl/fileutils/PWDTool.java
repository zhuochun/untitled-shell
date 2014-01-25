package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.fileutils.IPwdTool;


public class PWDTool extends ATool implements IPwdTool{
	public PWDTool() {
		super(null);
	}

	@Override
	public String getStringForDirectory(File directory) {
		//Error Handling
		if(directory==null || !directory.exists() || !directory.isDirectory()){
			setStatusCode(1);
			return "Error: Cannot find working directory";
		}
		//Processing the 
		return directory.getAbsolutePath();
	}

	@Override
	public String execute(File workingDir, String stdin) {
		return getStringForDirectory(workingDir);
	}


}
