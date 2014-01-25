package sg.edu.nus.comp.cs4218.extended2;

import sg.edu.nus.comp.cs4218.ITool;

/*
 * 
 * sort : sort lines of text file
 *
 * Command Format - sort [OPTIONS] [FILE]
 *	FILE - Name of the file
 *	OPTIONS
 *		-c : Check whether the given file is already sorted, if it is not all sorted, print a
 *           diagnostic containing the first line that is out of order
 *	    -help : Brief information about supported options
 */

public interface ISortTool extends ITool{
	String sortFile(String input);
	String checkIfSorted(String input);
	String getHelp();
}
