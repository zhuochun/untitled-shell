package sg.edu.nus.comp.cs4218.extended1;

import sg.edu.nus.comp.cs4218.ITool;

/**
 * The pipe tools allows the output of one program to 
 * be sent to the input of another program. With the 
 * help of pipe tool multiple small (and simple) programs
 * can be connected to accomplish large number of tasks.
 * 
 * Command Format - PROGRAM-1-STANDARD_OUTPUT | PROGRAM-2-STANDARD_INPUT
 * Where "|" is the pipe operator and PROGRAM-1-STANDARD_OUTPUT is the standard output of
 * program 1 and PROGRAM-2-STANDARD_INPUT is the standard input of program 2.
 *
 */
public interface IPipingTool extends ITool{
	
	/**
	 * Pipe the stdout of *from* to stdin of *to*
	 * @return The stdout of *to*
	 */
	String pipe(ITool from, ITool to);
	String pipe(String stdout, ITool to);
}
