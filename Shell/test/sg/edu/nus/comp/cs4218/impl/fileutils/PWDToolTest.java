package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.fileutils.IPwdTool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class PWDToolTest {
	//TODO Always test against the interface! 
	private IPwdTool pwdtool; 
	
	@Before
	public void before(){
		pwdtool = new PWDTool();
	}

    @After
	public void after(){
		pwdtool = null;
	}
	
	@Test
	public void getStringForDirectoryTest() throws IOException {
		//Test expected behavior
		//Create a tmp-file and get (existing) parent directory
		String existsDirString = File.createTempFile("exists", "tmp").getParent();
		File existsDir = new File(existsDirString);
		String dirString = pwdtool.getStringForDirectory(existsDir);
		assertTrue(dirString.equals(existsDirString));
		assertEquals(pwdtool.getStatusCode(), 0);
    }


	@Test
	public void getStringForNonExistingDirectoryTest() throws IOException { 
		//Test error-handling 1
		//Reference non-existing file
		File notExistsDir = new File("notexists");
        pwdtool.getStringForDirectory(notExistsDir);
		assertNotEquals(pwdtool.getStatusCode(), 0);
    }
		

	@Test
	public void getStringForNullDirectoryTest() throws IOException { 
		//Test error-handling 2
		pwdtool.getStringForDirectory(null);
		assertNotEquals(pwdtool.getStatusCode(), 0);
	}
	
	@Test
	public void getStringForFileTest() throws IOException { 
		//Test error-handling 3
		File file = new File("test.txt");
		file.createNewFile();
		pwdtool.getStringForDirectory(file);
		assertNotEquals(pwdtool.getStatusCode(), 0);
		file.delete();
	}
	
	@Test
	public void executeTest() {
		String actual = pwdtool.execute(PathUtils.getCurrentPath().toFile(), "");
		assertEquals(PathUtils.getCurrentPath().toString(), actual);
	}
}
