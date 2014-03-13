//chen hao
package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import sg.edu.nus.comp.cs4218.extended2.IWcTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class WCTool extends ATool implements IWcTool {

	private ArgList argList = new ArgList();
	public WCTool(String[] arguments) {
		super(arguments);
		argList.invalidOptionCheck=true;
	}

	@Override
	public String getCharacterCount(String input) {
		if(input == null)
			return "0";
		else{
			return input.length()+"";
		}
	}

	@Override
	public String getWordCount(String input) {
		if(input == null)
			return "0";
		else{
			input=input.replaceAll("\n", " ");
			input=input.trim();
			String []words = input.split(" ");
			return words.length+"";
		}
	}

	@Override
	public String getNewLineCount(String input) {
		if(input == null)
			return "0";
		else{
			int count=0;
			int index = input.indexOf("\r"); 
			while(index>=0){
				count++;
				input = input.substring(index+1);
				index = input.indexOf("\r");
			}
			return (count+1)+"";
		}
	}

	@Override
	public String getHelp() {
		StringBuilder helpInfo = new StringBuilder();
		helpInfo = helpInfo.append("Command Format - wc [OPTIONS] [FILE]\n");
		helpInfo = helpInfo.append(" FILE - Name of the file, when no file is present (denoted by -) use standard input\n");
		helpInfo = helpInfo.append(" OPTIONS\n");
		helpInfo = helpInfo.append("       -m : Print only the character counts\n" );
		helpInfo = helpInfo.append("       -w : Print only the word counts\n");
		helpInfo = helpInfo.append("       -l : Print only the newline counts\n");
		helpInfo = helpInfo.append("       -help : Brief information about supported options");
		return new String(helpInfo);
	}

	@Override
	public String execute(File workingDir, String stdin) {
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

		// if both -m -w -l appears, throw exception
		if ((argList.hasOption("m") && argList.hasOption("w"))
				||(argList.hasOption("m") && argList.hasOption("l")
						||(argList.hasOption("w") && argList.hasOption("l")))) {
			throw new IllegalArgumentException("Option error!");
		}


		String input = new String(argList.getParam(0));

		if(argList.hasOption("m")){
			return getCharacterCount(input);

		}
		else if(argList.hasOption("w")){
			return getWordCount(input);

		}
		else if(argList.hasOption("l")){
			return getNewLineCount(input);

		}
		else{
			return getHelp();
		}
	}
}
