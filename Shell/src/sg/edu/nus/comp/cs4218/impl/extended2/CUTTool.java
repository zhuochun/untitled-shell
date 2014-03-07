package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.extended2.ICutTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;
import sg.edu.nus.comp.cs4218.impl.RangeUtils;
import sg.edu.nus.comp.cs4218.impl.RangeUtils.Range;

public class CUTTool extends ATool implements ICutTool {
	
	private ArgList argList = new ArgList();

	public CUTTool(String[] arguments) {
		super(arguments);
		
		argList.invalidOptionCheck = true;
		
		argList.registerAcceptableOption("c", ArgType.STRING, 
						"Use LIST as the list of characters to cut out.");
		argList.registerAcceptableOption("d", ArgType.STRING,
						"Use DELIM as the field-separator character"
						+ " instead of the TAB character.");
		argList.registerAcceptableOption("help",
						"Brief information about supported options");
	}

	@Override
	public String cutSpecfiedCharacters(String list, String input) {
		StringBuilder finalString = new StringBuilder();
		ArrayList<Range> rangeList = RangeUtils.mergeRange(
												RangeUtils.parseRange(list)); 
		
		for (Range range : rangeList) {	
			finalString.append(input.substring(range.left - 1,
											   Math.min(input.length(),
												        range.right)));
			
			if (range.right > input.length()) {
				break;
			}
		}
		
		return finalString.toString();
	}

	@Override
	public String cutSpecifiedCharactersUseDelimiter(String list, String delim,
			String input) {
		StringBuilder finalString = new StringBuilder();
		ArrayList<Range> rangeList = RangeUtils.mergeRange(
												RangeUtils.parseRange(list));
		
		String[] fields = input.split(delim);
		
		for (Range range : rangeList) {
			for (int i = range.left - 1; i < Math.min(fields.length, range.right); i ++) {
				finalString.append(fields[i]);
				finalString.append(delim);
			}
			
			if (range.right > input.length()) {
				break;
			}
		}
		
		finalString.deleteCharAt(finalString.length() - 1);
		
		return finalString.toString();
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
		
		// determine where the input comes from
		String input = (stdin == null? "" : stdin);
		
		// if the input comes from a file
		if (argList.hasParams() && argList.getParam(0) != "-") {
			try {
				input = FileUtils.readFileContent(new File(PathUtils.
						PathResolver(workingDir, argList.getParam(0))));
			} catch (IOException e) {
				setStatusCode(1);
				return e.getMessage();
			} catch (RuntimeException e) {
				setStatusCode(2);
				return e.getMessage();
			}
		}
		
		// process input
		BufferedReader br = new BufferedReader(new StringReader(input));
		StringBuilder result = new StringBuilder();
		String list = argList.getOptionValue("c");

		try {
			String line;
			while ((line = br.readLine()) != null) {
				result.append(argList.hasOption("d") ? cutSpecfiedCharacters(list, line) : 
									cutSpecifiedCharactersUseDelimiter(list, 
											argList.getOptionValue("d"), line));
				result.append("\n");
			}

			br.close();
		} catch (IOException e) {
			setStatusCode(2);
			result.append("Error: file reading exception.\n");
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			result.append(e.getMessage());
		}
		
		return result.toString();
	}
}
