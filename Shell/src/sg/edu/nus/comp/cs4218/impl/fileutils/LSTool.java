package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

/*
 * ls - list directory contents
 * 
 * ls [OPTION]... [FILE]...
 * 
 * List  information  about	 the FILEs (the current directory by default).
 */
public class LSTool extends ATool implements ILsTool {
	
	private List<File> argDirectories = new ArrayList<File>();
	private ArgList argList = new ArgList();

	public LSTool(String[] arguments) {
		super(arguments);
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
		if (files == null || files.size() == 0) {
			return "\n";
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

		if (argList.hasInvalidOptions()) {
			setStatusCode(9);
			return "Error: Invalid Option " + argList.getInvalidOptions()[0];
		}
		
		if (argList.hasParams()) {
			for (String param : argList.getParams()) {
				this.argDirectories.add(new File(param));
			}
		}

		if (argDirectories.size() > 0) {
			return getStringForFiles(getFiles(argDirectories.get(0)));
		} else {
			return getStringForFiles(getFiles(workingDir));
		}
	}

}