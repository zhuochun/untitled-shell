package sg.edu.nus.comp.cs4218.extended2;

import sg.edu.nus.comp.cs4218.ITool;

/*
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
*/


public interface IWcTool extends ITool {

	String getCharacterCount(String input);
	String getWordCount(String input);
	String getNewLineCount(String input);
	String getHelp();
	
}
