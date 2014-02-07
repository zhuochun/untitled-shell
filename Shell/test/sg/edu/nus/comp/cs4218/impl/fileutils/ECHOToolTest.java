package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.fileutils.IEchoTool;

public class ECHOToolTest {

	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	public IEchoTool echotool;
	
	@Before
	public void setUp() throws Exception {
		echotool = new ECHOTool(null);
	}

	@After
	public void tearDown() throws Exception {
		echotool = null;
	}
	
	@Test
	public void testEchoWithDoubleQuotes(){
		String st = new String();
		st = "This is a test \"string\"";
		
		assertEquals(echotool.echo(st),"This is a test string");
	}
	@Test
	public void testEchoWithSingleQuotes(){
		String st = new String();
		st = "'This is a test' string";
		
		assertEquals(echotool.echo(st),"This is a test string");
	}
	@Test
	public void testEchoWitBothQuotes(){
		String st = new String();
		st = "This is a \"test\" 'string'";
		assertEquals(echotool.echo(st),"This is a test string");
	}
	
}



