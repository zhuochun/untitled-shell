package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

/**
 * cat - concatenate files and print on the standard output
 * 
 * cat [OPTION] [FILE]...
 */
public class CATTool extends ATool implements ICatTool {
	
	private ArgList argList = new ArgList();

	public CATTool(String[] arguments) {
		super(arguments);
		
		argList.invalidOptionCheck = true;
	}

	@Override
	public String getStringForFile(File toRead) {
		try {
			return FileUtils.readFileContent(toRead);
		} catch (IOException e) {
			setStatusCode(1);
			return e.getMessage();
		} catch (RuntimeException e) {
			setStatusCode(2);
			return e.getMessage();
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

		StringBuilder output = new StringBuilder();
		String regulatedStdin = stdin;
		
		// make sure stdin exists
		if (regulatedStdin == null) { regulatedStdin = ""; }

		// process arguments
		if (argList.isEmpty() || argList.getParam(0).equals("-")) {
			output.append(regulatedStdin);
		} else {
			for (String arg : argList.getParams()) {
				if (arg.equals("-")) {
					continue;
				} else {
					File toRead = new File(PathUtils.pathResolver(workingDir, arg));
					output.append(getStringForFile(toRead));
				}
			}
		}

		return output.toString();
	}

}
