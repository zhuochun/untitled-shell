package sg.edu.nus.comp.cs4218.extended2;

import sg.edu.nus.comp.cs4218.ITool;

/*
 * 
 * comm : Compares two sorted files line by line. With no options, produce three-column output. 
 * 		 Column one contains lines unique to FILE1, column two contains lines unique to FILE2, 
 * 		 and column three contains lines common to both files.
 *	
 *	Command Format - comm [OPTIONS] FILE1 FILE2
 *	FILE1 - Name of the file 1
 *	FILE2 - Name of the file 2
 *		-c : check that the input is correctly sorted
 *      -d : do not check that the input is correctly sorted
 *      -help : Brief information about supported options
 */

public interface ICommTool extends ITool{
	String compareFiles(String input1, String input2);
	String compareFilesCheckSortStatus(String input1, String input2);
	String compareFilesDoNotCheckSortStatus(String input1, String input2);
	String getHelp();

}
