package sg.edu.nus.comp.cs4218.extended2;

import sg.edu.nus.comp.cs4218.ITool;

/*
 * 
 * uniq : Writes the unique lines in the given input. The input need not be sorted, but repeated input lines are detected only if they are adjacent.
 *
 * Command Format - uniq [OPTIONS] [FILE]
 * FILE - Name of the file, when no file is present (denoted by "-") use standard input
 * OPTIONS
 * 		-f NUM : Skips NUM fields on each line before checking for uniqueness. Use a null
 *             string for comparison if a line has fewer than n fields. Fields are sequences of
 *             non-space non-tab characters that are separated from each other by at least one
 *             space or tab.
 *      -i : Ignore differences in case when comparing lines.
 *      -help : Brief information about supported options
 */

public interface IUniqTool extends ITool{
	
	String getUnique(boolean checkCase, String input);
	String getUniqueSkipNum(int NUM, boolean checkCase, String input);
	String getHelp();

}
