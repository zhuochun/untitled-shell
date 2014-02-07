package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * For cd command, we have these cases to test:
 * 
 * Case 1: Special Characters
 *	1.1 '.', current dir
 *	1.2 '..', parent dir
 *	1.3 '/', root dir
 *	1.4 '~', home dir (this case cannot be handled by Path class!!!!!)
 *
 * Case 2: Relative Path
 *	2.1 valid path (all directories/ files are exisiting)
 *	2.2 contain some non-exisit direcotories/ files
 *	2.3 contain special characters
 *
 * Case 3: Absolute Path
 *	3.1 valid path (all directories/ files are exisiting)
 *		3.1.1 not deeper than current directory's parent
 *		3.1.2 same as current directory
 *		3.1.3 deeper than current directory
 *	3.2 contain some non-exisit direcotories/ files
 *	3.3 contain special characters
 *
 * Case 4: Cross Platform Path
 *	4.1 Windows path used in Mac/Linux/Solarius
 *	4.2 Mac/Linus/Solarius path used in Windows
 *
 * Case 5: Extremely Long Names
 *	5.1 File name longer than 2^31 - 1 characters
 *
 * Case 6: White Characters
 *	6.1 ' ' (Space)
 *	6.2 '	' (Tab)
 *
 * Case 7: Escape Characers
 *	7.1 Characeters behind '\'
 *
 * Case 8: Non-alphabetic/digit Characters
 * 	8.1 '@', '!', '#', '$'.....
 *
 */
public class PathUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
