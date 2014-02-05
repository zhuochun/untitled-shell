package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.FileUtils;

/**
 * cat - concatenate files and print on the standard output
 * 
 * cat [OPTION] [FILE]...
 */
public class CATTool extends ATool implements ICatTool {
	
	private ArgList argList = new ArgList();

	public CATTool(String[] arguments) {
		super(arguments);
	}

	@Override
	public String getStringForFile(File toRead) {
		try {
			return FileUtils.readFileContent(toRead);
		} catch (IOException e) {
			setStatusCode(1);
			return e.toString();
		} catch (RuntimeException e) {
			setStatusCode(2);
			return e.toString();
		}
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

		StringBuilder output = new StringBuilder();
		
		// make sure stdin exists
		if (stdin == null) { stdin = ""; }

		// process arguments
		if (argList.isEmpty()) {
			output.append(stdin);
		} else {
			for (String arg : argList.getParams()) {
				if (arg.equals(">")) {
					break;
				} else if (arg.equals("-")) {
					output.append(stdin);
					stdin = ""; // clear stdin after read
				} else {
					// construct the file
					File toRead = new File(workingDir, arg);
					output.append(getStringForFile(toRead));
				}
			}
		}

		return output.toString();
	}

}
