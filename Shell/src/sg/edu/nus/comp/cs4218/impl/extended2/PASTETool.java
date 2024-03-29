package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.extended2.IPasteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

/*
 * paste : writes to standard output lines consisting of sequentially corresponding 
 * lines of each given file, separated by a TAB character
 * 
 * Command Format - paste [OPTIONS] [FILE]
 * 		FILE - Name of the file, when no file is present (denoted by "-") use standard input OPTIONS
 * 		-s : Paste one file at a time instead of in parallel
 * 		-d DELIM: Use characters from the DELIM instead of TAB character
 * 		-help : Brief information about supported options
 */
public class PASTETool extends ATool implements IPasteTool {

	private ArgList argList = new ArgList();
	private final String DELIM = "\t";

	/**
	 * Initialize Paste tool
	 * 
	 * @param arguments
	 */
	public PASTETool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;

		argList.registerAcceptableOption("s",
				"Paste one file at a time instead of in parallel.");
		argList.registerAcceptableOption("d", ArgType.STRING,
				"Use characters from the DELIM instead of TAB character");
		argList.registerAcceptableOption("help",
				"Brief information about supported options");
	}

	/**
	 * paste inputs array in serialized string
	 * 
	 * @param input String Array
	 * 
	 * @return serialized string
	 */
	@Override
	public String pasteSerial(String[] input) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < input.length; i++) {
			if (input[i] != null) {
				sb.append(input[i]);
				
				if (i + 1 < input.length) {
					sb.append(DELIM);
				}
			}
		}

		sb.append("\n");

		return sb.toString();
	}

	/**
	 * paste inputs array in serialized string using delim passed in
	 * 
	 * @param delim
	 * @param input
	 * 
	 * @return serialized string
	 */
	@Override
	public String pasteUseDelimiter(String delim, String[] input) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < input.length; i++) {
			if (input[i] != null) {
				sb.append(input[i]);
				
				if (i + 1 < input.length) {
					sb.append(delim);
				}
			}
		}

		sb.append("\n");

		return sb.toString();
	}

	/**
	 * get help information
	 * 
	 * @return help information string
	 */
	@Override
	public String getHelp() {
		StringBuilder help = new StringBuilder();

		help.append("Command Format - paste [OPTIONS] [FILE]");
		help.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		help.append("OPTIONS\n");

		for (Option opt : argList.getAcceptableOptions()) {
			help.append("  " + opt.toString() + "\n");
		}

		// remove the last trailing \n
		help.deleteCharAt(help.length() - 1);

		return help.toString();
	}

	/**
	 * execute paste tool
	 * 
	 * @param workingDir
	 * @param stdin
	 * 
	 * @return stdout of paste tool
	 */
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

		// no params
		if (!argList.hasParams()) {
			setStatusCode(4);
			return "Error: No input provided";
		}

		// set inputs
		try {
			ArrayList<String[]> inputs = new ArrayList<String[]>();
			int maxInputLength = setInputs(inputs, workingDir, stdin);

			return processInputs(inputs, maxInputLength, workingDir);
		} catch (IOException e) {
			setStatusCode(1);
			return e.getMessage();
		} catch (RuntimeException e) {
			setStatusCode(2);
			return e.getMessage();
		}
	}

	/**
	 * actual execution of paste tool depending on inputs
	 * 
	 * @param inputs
	 * @param maxInputLength
	 * @param workingDir
	 * 
	 * @return stdout of paste tool
	 */
	private String processInputs(ArrayList<String[]> inputs,
			int maxInputLength, File workingDir) {
		// process inputs in serial
		StringBuilder result = new StringBuilder();

		if (argList.hasOption("s")) {
			for (String[] input : inputs) {
				result.append(pasteSerial(input));
			}
		} else {
			String delim = argList.hasOption("d") ? argList.getOptionValue("d")
					: DELIM;

			for (int i = 0; i < maxInputLength; i++) {
				String[] lines = new String[inputs.size()];

				for (int j = 0; j < inputs.size(); j++) {
					if (i < inputs.get(j).length) {
						lines[j] = inputs.get(j)[i];
					} else {
						lines[j] = "";
					}
				}

				result.append(pasteUseDelimiter(delim, lines));
			}
		}

		return result.toString();
	}

	private int setInputs(ArrayList<String[]> inputs, File workingDir,
			String stdin) throws IOException, RuntimeException {
		int maxLen = 0;

		String[] params = argList.getParams();

		for (int i = 0; i < params.length; i++) {
			String[] input = new String[0];

			if (params[i].equals("-")) {
				input = getStringLines(stdin);
			} else {
				input = FileUtils.readFileLines(new File(PathUtils
						.pathResolver(workingDir, params[i])));
			}

			if (input.length > maxLen) {
				maxLen = input.length;
			}

			inputs.add(input);
		}

		return maxLen;
	}

	/**
	 * read input to an array of string lines
	 * 
	 * @param input
	 * 
	 * @return an array of lines
	 */
	private String[] getStringLines(String input) {
		if (input == null) {
			return new String[0];
		}

		BufferedReader br = new BufferedReader(new StringReader(input));
		ArrayList<String> result = new ArrayList<String>();

		try {
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}

			br.close();
		} catch (IOException e) {
			setStatusCode(2);
			result.add("Error: file reading exception.\n");
		}

		return result.toArray(new String[0]);
	}

}
