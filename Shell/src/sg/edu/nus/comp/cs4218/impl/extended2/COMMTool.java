package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;

public class COMMTool extends ATool implements ICommTool {

	private ArgList argList = new ArgList();
	private String prevLineOfA, prevLineOfB;
	
	public COMMTool(String[] arguments) {
		super(arguments);
		
		argList.invalidOptionCheck = true;
		
		argList.registerAcceptableOption("c", 
						"Check that the input is correctly sorted.");
		argList.registerAcceptableOption("d",
						"Do not check that the input is correctly sorted.");
		argList.registerAcceptableOption("help",
						"Brief information about supported options");
	}

	@Override
	public String compareFiles(String input1, String input2) {
		BufferedReader brA;
		BufferedReader brB;
		
		try {
			brA = new BufferedReader(new FileReader(input1));
			brB = new BufferedReader(new FileReader(input2));

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String compareFilesCheckSortStatus(String input1, String input2) {
		BufferedReader brA;
		BufferedReader brB;
		
		try {
			brA = new BufferedReader(new FileReader(input1));
			brB = new BufferedReader(new FileReader(input2));

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String compareFilesDoNotCheckSortStatus(String input1, String input2) {
		BufferedReader brA;
		BufferedReader brB;
		
		try {
			brA = new BufferedReader(new FileReader(input1));
			brB = new BufferedReader(new FileReader(input2));

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getHelp() {
		StringBuilder help = new StringBuilder();

		help.append("Command Format - cut [OPTIONS] [FILE]\n");
		help.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		help.append("OPTIONS\n");

		for (Option opt : argList.getAcceptableOptions()) {
			help.append("  " + opt.toString() + "\n");
		}

		// remove the last trailing \n
		if (help.length() > 0) {
			help.deleteCharAt(help.length() - 1);
		}

		return help.toString();
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

		// help option?
		if (argList.hasOptions() && argList.getOption(0).equals("help")) {
			return getHelp();
		}
		
		// command does not have options and parameters
		if (!argList.hasOptions() && !argList.hasParams()) {
			return getHelp();
		}
		
		// if both -c -d appears, throw exception
		if (argList.hasOption("c") && argList.hasOption("d")) {
			throw new IllegalArgumentException("Option error!");
		}
		
		String[] filePaths = argList.getParams();
		String fileAPath, fileBPath;
		String result;
		
		if (filePaths.length != 2) {
			throw new IllegalArgumentException("Parameters in wrong format!");
		} else {
			try {
				fileAPath = PathUtils.PathResolver(workingDir, filePaths[0]);
				fileBPath = PathUtils.PathResolver(workingDir, filePaths[1]);
			} catch (RuntimeException e) {
				setStatusCode(2);
				return e.getMessage();
			}
			
			// process input
			if (argList.hasOption("c")) {
				result = compareFilesCheckSortStatus(fileAPath, fileBPath);
			} else
			if (argList.hasOption("d")) {
				result = compareFilesDoNotCheckSortStatus(fileAPath, fileBPath);
			} else {
				result = compareFiles(fileAPath, fileBPath);
			}
		}
		
		return result;
	}

}
