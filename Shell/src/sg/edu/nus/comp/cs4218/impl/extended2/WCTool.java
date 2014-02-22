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

			if (argList.isEmpty() || argList.getParams().length < 2) {
				setStatusCode(9);
				return "Error: at least 2 parameters required";
			}

			String input = new String(argList.getParam(0));
			String option = new String(argList.getParam(1));


			if(option.equals("m")){
				return getCharacterCount(input);

			}
			else if(option.equals("w")){
				return getWordCount(input);

			}
			else if(option.equals("l")){
				return getNewLineCount(input);

			}
			else{
				return getHelp();
			}
		}catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage();
		}catch(Exception e){
			setStatusCode(9);
			return e.getMessage();
		}
	}
}
