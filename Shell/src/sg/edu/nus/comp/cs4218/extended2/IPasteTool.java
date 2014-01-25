package sg.edu.nus.comp.cs4218.extended2;

import sg.edu.nus.comp.cs4218.ITool;

/*
 * 
 * paste : writes to standard output lines consisting of sequentially corresponding 
 * lines of each given file, separated by a TAB character
 * 
 * Command Format - paste [OPTIONS] [FILE]
 * 		FILE - Name of the file, when no file is present (denoted by "-") use standard input OPTIONS
 * 		-s : paste one file at a time instead of in parallel
 * 		-d DELIM: Use characters from the DELIM instead of TAB character
 * 		-help : Brief information about supported options
 */

public interface IPasteTool extends ITool{
	String pasteSerial(String[] input);
	String pasteUseDelimiter(String delim, String[] input);
	String getHelp();
}
