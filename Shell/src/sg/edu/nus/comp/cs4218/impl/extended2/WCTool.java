/**
 * 
 * wc : Prints the number of bytes, words, and lines in given file
 *
 * Command Format - wc [OPTIONS] [FILE]
 * FILE - Name of the file, when no file is present (denoted by "-") use standard input
 * OPTIONS
 *		-m : Print only the character counts
 *      -w : Print only the word counts
 *      -l : Print only the newline counts
 *		-help : Brief information about supported options
 * if there is no option, return the characters, words and new lines of the 
 * if there is option but no file parameters, return error messages and status cosde will be set
 */
package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.extended2.IWcTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class WCTool extends ATool implements IWcTool {

	private ArgList argList = new ArgList();
	public WCTool(String[] arguments) {
		super(arguments);
		argList.invalidOptionCheck=true;

		argList.registerAcceptableOption("m", 
				"Print only the character counts.");
		argList.registerAcceptableOption("w", 
				"Print only the word counts");
		argList.registerAcceptableOption("l", 
				"Print only the newline counts");
		argList.registerAcceptableOption("help", 
				"Brief information about supported options");

	}

	@Override
	/*
	 * This function is used to count the characters in the given content
	 * @param input  the given content
	 * @return the number of characters in the content
	 */
	public String getCharacterCount(String input) {
		if(input == null)
			return "0";
		else{
			return input.length()+"";
		}
	}

	@Override
	/*
	 * This function is used to count the words in the given content
	 * @param input  the given content
	 * @return the number of words in the content
	 */
	public String getWordCount(String input) {
		String result = "0";
		if(input == null)
			return result;
		else{
			String newInput = input;
			
			newInput=newInput.replaceAll("\n", " ");
			newInput=newInput.trim();
			String []words = newInput.split(" ");
			result = words.length+"";
			return result;
		}
	}

	@Override
	/*
	 * This function is used to count the newlines in the given content
	 * @param input  the given content
	 * @return the number of newlines in the content
	 */
	public String getNewLineCount(String input) {
		String result = "0";
		if(input == null)
			return result;
		else{
			int count=0;
			int index = input.indexOf("\r"); 
			String output = input;
			while(index>=0){
				count++;
				output = output.substring(index+1);
				index = output.indexOf("\r");
			}
			result = (count+1)+"";
			return result;
		}
	}

	@Override
	/*
	 * This function is used to output the help information
	 * @return help information
	 */
	public String getHelp() {
		String output;
		StringBuilder helpInfo = new StringBuilder();
		helpInfo = helpInfo.append("Command Format - wc [OPTIONS] [FILE]\n");
		helpInfo = helpInfo.append(" FILE - Name of the file, when no file is present (denoted by -) use standard input\n");
		helpInfo = helpInfo.append(" OPTIONS\n");
		helpInfo = helpInfo.append("       -m : Print only the character counts\n" );
		helpInfo = helpInfo.append("       -w : Print only the word counts\n");
		helpInfo = helpInfo.append("       -l : Print only the newline counts\n");
		helpInfo = helpInfo.append("       -help : Brief information about supported options");
		output = new String(helpInfo);
		return output;
	}

	@Override
	/*
	 * This function is used to execute and call the respective methods in wc command
	 * @param workingDir the directory of current working environment
	 * @param stdin the input command
	 * @return the execute result and information 
	 */
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


		try{
			String file = argList.getParam(0);
			String filePath = PathUtils.pathResolver(workingDir, file);
			String content = FileUtils.readFileContent(new File(filePath));

			if(argList.hasOption("m")){
				return getCharacterCount(content);
			}
			else if(argList.hasOption("w")){
				return getWordCount(content);
			}
			else if(argList.hasOption("l")){
				return getNewLineCount(content);
			}
			if(!argList.hasOptions()){
				String result = getCharacterCount(content)+" "+getWordCount(content)+" "+
						getNewLineCount(content);
				return result;
			
			}
		}catch(Exception e){
			setStatusCode(0);
			return e.getMessage();
		}
		return null;
	}
}
