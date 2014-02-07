package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

/*
 * ls - list directory contents
 * 
 * ls [OPTION]... [FILE]...
 * 
 * List  information  about	 the FILEs (the current directory by default).
 */
public class LSTool extends ATool implements ILsTool {
	
	private ArgList argList = new ArgList();

	public LSTool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;
	}

	@Override
	public List<File> getFiles(File directory) {
		List<File> files = new ArrayList<File>();

		if (directory == null || !directory.exists()){
			setStatusCode(1);
		} else if (directory.isFile()) {
			files.add(directory);
		} else if (directory.isDirectory()) {
			String[] list = directory.list();
			
			for (String file : list) {
				files.add(new File(file));
			}
		}
		
		return files;
	}

	@Override
	public String getStringForFiles(List<File> files) {
		if (files == null || files.isEmpty()) {
			return "";
		}
		
		StringBuffer ls = new StringBuffer();
		
		for (File file : files) {
			ls.append(file.getName());
			ls.append("\n");
		}

		return ls.toString();
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage();
		}
		
		// set ls directory
		File lsDir = workingDir;
		
		// only take the first one for now
		if (argList.hasParams()) {
			lsDir = new File(PathUtils.PathResolver(workingDir, argList.getParam(0)));
		}

		return getStringForFiles(getFiles(lsDir));
	}

}