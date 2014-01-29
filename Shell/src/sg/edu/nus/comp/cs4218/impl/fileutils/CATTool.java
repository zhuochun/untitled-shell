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
 * 
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
			int ch;
			StringBuilder sb = new StringBuilder();
			BufferedReader br;

			try {
				br = new BufferedReader(new FileReader(toRead));

				while ((ch = br.read()) != -1) {
					sb.append((char) ch);
				}

				br.close();
			} catch (IOException e) {
				setStatusCode(1);
			}

			setStatusCode(0);
			return sb.toString();
		}

		return null;
	}
	
	public String getStringForStandardInput() {
		// TODO
		return "";
	}
	
	public void writeStringToFile(File file) {
		// TODO
		if (file.exists()) {
			
		}
	}

	@Override
	public String execute(File workingDir, String stdin) {
		StringBuilder output = new StringBuilder();

		if (argList.isEmpty()) {
			output.append(getStringForStandardInput());
		} else {
			for (String arg : argList.getArguments()) {
				if (arg.equals(">")) {
					break;
				} else if (arg.equals("-")) {
					output.append(getStringForStandardInput());
				} else {
					output.append(getStringForFile(new File(arg)));
				}
			}
		}
		
		if (argList.hasArgument(">")) {
			
		} else {
			
		}

		return null;
	}

}
