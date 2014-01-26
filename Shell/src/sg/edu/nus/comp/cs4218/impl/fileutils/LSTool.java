package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

/*
 * Ls Command: ls [path]
 */
public class LSTool extends ATool implements ILsTool {
	
	private List<File> argDirectories = new ArrayList<File>();

	public LSTool(String[] arguments) {
		super(arguments);

		if (this.args != null) {
			for (String arg : this.args) {
				if (arg != null && !arg.startsWith("-") && !arg.isEmpty()) {
					this.argDirectories.add(new File(arg));
				}
			}
		}
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
		if (argDirectories.size() > 0) {
			return getStringForFiles(getFiles(argDirectories.get(0)));
		} else {
			return getStringForFiles(getFiles(workingDir));
		}
	}

}