package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

/**
 * cat - concatenate files and print on the standard output
 * 
 * cat [OPTION] [FILE]...
 */
public class CATTool extends ATool implements ICatTool {
	
	private ArgList argList = new ArgList();

	public CATTool(String[] arguments) {
		super(arguments);
		argList.parseArgs(this.args);
	}

	@Override
	public String getStringForFile(File toRead) {
		if (toRead == null || !toRead.exists()){
			setStatusCode(1);
			return String.format("Error: No such file or directory");
		} else if (toRead.isDirectory()) {
			setStatusCode(1);
			return String.format("Error: {0} is a directory", toRead.getName());
		} else if (toRead.isFile()) {
			try {
				setStatusCode(0);
				return readFileContent(toRead);
			} catch (IOException e) {
				setStatusCode(1);
				return e.toString();
			}
		}

		return null;
	}
	
	private String readFileContent(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();

		return sb.toString();
	}
	
	@Override
	public String execute(File workingDir, String stdin) {
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
			for (String arg : argList.getArguments()) {
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
