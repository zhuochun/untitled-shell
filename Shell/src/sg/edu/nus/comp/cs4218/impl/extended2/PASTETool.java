package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.extended2.IPasteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

public class PASTETool extends ATool implements IPasteTool {

	private ArgList argList = new ArgList();

	
	public PASTETool(String[] arguments) {
		super(arguments);
		argList.invalidOptionCheck = true;
	}

	@Override
	public String pasteSerial(String[] input) {
		
		StringBuilder sb = new StringBuilder();
		for (String s : input){
			sb.append(s);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		return sb.toString();
	}

	@Override
	public String pasteUseDelimiter(String delim, String[] input) {
		
		StringBuilder sb = new StringBuilder();
		for(String s : input){
			sb.append(s);
			sb.append(delim);
		}
	return sb.toString();
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		
		if(args==null || args.length == 0){
			setStatusCode(9);
			return "Error, no input";
		}
		
			return null;
	}

}
