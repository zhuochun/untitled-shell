package sg.edu.nus.comp.cs4218.extended2;

import sg.edu.nus.comp.cs4218.ITool;

/*
 * 
 * cut : prints a substring that is specified in a certain range
 *
 *	Command Format - cut [OPTIONS] [FILE]
 *		FILE - Name of the file, when no file is present (denoted by "-") use standard input OPTIONS
 *			-c LIST: Use LIST as the list of characters to cut out. Items within the list may be
 *					separated by commas, and ranges of characters can be separated with dashes.
 *					For example, list ‘1-5,10,12,18-30’ specifies characters 1 through 5, 10,12 and
 *					18 through 30.
 *			-d DELIM: Use DELIM as the field-separator character instead of the TAB character
 *			-help : Brief information about supported options
 */

public interface ICutTool extends ITool {

	String cutSpecfiedCharacters(String list, String input);
	String cutSpecifiedCharactersUseDelimiter(String list, String delim, String input);
	String getHelp();

}
