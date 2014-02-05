package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.ArgList;

import sg.edu.nus.comp.cs4218.fileutils.ICdTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class CDTool extends ATool implements ICdTool {
	
	private ArgList argList = new ArgList();
	
	public CDTool(String[] arguments) {
		super(arguments);
		
		argList.parseArgs(arguments);
	}
	
	@Override
	public File changeDirectory(String newDirectory) {
		// TODO
		return null;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// TODO
		return null;
	}
}
