// chen hao
package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class SORTTool extends ATool implements ISortTool {

	private ArgList argList = new ArgList();

	public SORTTool(String[] arguments) {
		super(arguments);
		argList.invalidOptionCheck=true;
	}

	@Override
	public String sortFile(String input) {
		StringBuilder sortedString = new StringBuilder();
		String []stringToSort = input.split("\r\n");
		Vector <String> stringToSortVect = new Vector<String>(Arrays.asList(stringToSort));
		Collections.sort(stringToSortVect);
		for(int i = 0 ; i < stringToSortVect.size();i++){
			sortedString = sortedString.append(stringToSortVect.get(i)+"\n");
		}
		return new String(sortedString);
	}

	@Override
	public String checkIfSorted(String input) {
		StringBuilder disorderInfo = new StringBuilder();
		int disorderLineIndex = -1;  
		String []stringToCheck = input.split("\r\n");
		Vector <String> stringToCheckVect = new Vector<String>(Arrays.asList(stringToCheck));
		for(int i = 1;i < stringToCheckVect.size(); i++){
			if(stringToCheckVect.get(i-1).compareToIgnoreCase(stringToCheckVect.get(i))==1){
				disorderLineIndex = i;
				break;
			}
		}

		if(disorderLineIndex >= 0){
			disorderInfo.append("sort: sortFile.txt:");
			disorderInfo.append(""+(disorderLineIndex+1));
			disorderInfo.append("disorder: ");
			disorderInfo.append(""+stringToCheckVect.get(disorderLineIndex+1)+"\n");
			return new String(disorderInfo);
		}
		else{
			return "";
		}

	}

	@Override
	public String getHelp() {
		StringBuilder helpInfo = new StringBuilder();
		helpInfo = helpInfo.append("Command Format - sort [OPTIONS] [FILE]\r");
		helpInfo = helpInfo.append(" FILE - Name of the file\r");
		helpInfo = helpInfo.append(" OPTIONS\r");
		helpInfo = helpInfo.append("       -c : Check whether the given file is already sorted,\r" );
		helpInfo = helpInfo.append("            if it is not all sorted, print a diagnostic containing\r");
		helpInfo = helpInfo.append("            the first line that is out of order");
		helpInfo = helpInfo.append("       -help : Brief information about supported options");
		return new String(helpInfo);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		try {
			argList.parseArgs(this.args);

			if (argList.isEmpty() || argList.getParams().length < 1) {
				setStatusCode(9);
				return "Error: at least 1 parameters required";
			}


			String option = new String();
			File sortFile = new File(PathUtils.PathResolver(workingDir,
					argList.getParam(0)));
			FileReader fr = new FileReader (sortFile);
			BufferedReader br = new BufferedReader (fr);
			StringBuilder input = new StringBuilder();
			String tmp = new String();

			tmp = br.readLine();
			while(tmp != null){
				input= input.append(tmp);
				tmp = br.readLine();
			}

			if(argList.getParams().length==2){
				option = argList.getParam(1);
			}


			if(option.equals("c")){
				return checkIfSorted(new String(input));

			}
			else if(option.equals("help")){
				return getHelp();

			}
			else{
				return sortFile(new String(input));
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


