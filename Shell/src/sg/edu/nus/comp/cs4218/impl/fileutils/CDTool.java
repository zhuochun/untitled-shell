package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.ArgList;

import sg.edu.nus.comp.cs4218.fileutils.ICdTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

/**
 * cd - change current working directory
 *
 * cd [new working directory]
 * 
 * new working directory = '/', '~', '..', path
 * 
 * If the directory cannot be found, an error message will be returned and an
 * error code will be set.
 */
public class CDTool extends ATool implements ICdTool {
	
	private ArgList argList = new ArgList();
	private File curWorkingDir;
	
	public CDTool(String[] arguments) {
		super(arguments);
		
		argList.parseArgs(arguments);
	}
	
	@Override
	public File changeDirectory(String newDirectory) {
		return new File(curWorkingDir, newDirectory);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		curWorkingDir = workingDir; 
		return null;
	}
}
