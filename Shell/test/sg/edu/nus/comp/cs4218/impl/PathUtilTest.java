package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class PathUtilTest {
	
	String currentDir;
	String homeDir;
	String rootDir;
	
	File testFolder;
	File testFile;
	
	PathUtils pathutils = new PathUtils();

	@Before
	public void setUp() throws Exception {
		currentDir = System.getProperty("user.dir") + "/";
		homeDir = System.getProperty("user.home") + "/";
		
		rootDir = "/";
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetCurrentPath() {
		String expected = System.getProperty("user.dir");
		String actual = PathUtils.getCurrentPath().toString();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testPathWithSingleDot() {
		String path = ".";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir, newPath);
	}

	@Test
	public void testPathWithDoubleDots() {
		String path = "..";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParent() + "/", newPath);
	}
	
	@Test
	public void testPathWithMultipleDots() {
		String path = ".........";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir + path, newPath);
	}
	
	@Test
	public void testPathWithSingleDotAndOneForwardSlash() {
		String path = "./";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir, newPath);
	}
	
	@Test
	public void testPathWithSingleDotAndMultipleForwardSlash() {
		String path = "./////";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir, newPath);
	}
	
	@Test
	public void testPathWithDoubleDotAndSingleForwardSlash() {
		String path = "../";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParent() + "/", newPath);
	}
	
	@Test
	public void testPathWithDoubleDotAndMultipleForwardSlash() {
		String path = "..////////////";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParent() + "/", newPath);
	}
	
	@Test
	public void testPathWithSingleTudle() {
		String path = "~";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(homeDir, newPath);
	}
	
	@Test
	public void testPathWithMultipleTudle() {
		String path = "~~~~~~~~~~";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir + path, newPath);
	}
	
	@Test
	public void testPathWithSingleTudleAndSingleForwardSlash() {
		String path = "~/";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(homeDir, newPath);
	}
	
	@Test
	public void testPathWithSingleTudleAndMultipleForwardSlash() {
		String path = "~//////////////";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(homeDir, newPath);
	}
	
	@Test
	public void testPathWithSingleForwardSlash() {
		String path = "/";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals("/", newPath);
	}
	
	@Test
	public void testPathWithMultipleForwardSlash() {
		String path = "////////////";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals("/", newPath);
	}
	
	@Test
	public void testPathWithMultipeLayersOfSingleDots() {
		String path = "./././././././";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir, newPath);
	}
	
	@Test
	public void testPathWithMultipeLayersOfDoubleDots() {
		String path = "../../..";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParentFile().getParentFile().getParent() + "/", newPath);
		
		path = "../../../";
		newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParentFile().getParentFile().getParent() + "/", newPath);
	}
	
	@Test
	public void testPathWithSingleAndDoubleDots() {
		String path = "./.././../././././../";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParentFile().getParentFile().getParent() + "/", newPath);
	}
	
	@Test
	public void testPathWithSingleAndDoubleDotsAndMultipleSlashes() {
		String path = "./..//./../////////./././/////.///..//";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParentFile().getParentFile().getParent() + "/", newPath);
	}
	
	@Test
	public void testPathWithTudleAndForwardSlash() {
		String path = "~/~/~/";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(homeDir + "~/~/", newPath);
		
		path = "~~///~/~";
		newPath = PathUtils.PathResolver(currentDir, path);
		assertEquals(currentDir + "~~/~/~", newPath);
	}
	
	@Test
	public void testPathWithTudleAndForwardSlashAndAlphabets() {
		String path = "~/asdfsadf";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(homeDir + "asdfsadf", newPath);
	}
	
	@Test
	public void testPathWithTudleAndAlphabets() {
		String path = "~oiwejf";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir + "~oiwejf", newPath);
	}
	
	@Test
	public void testPathWithAllThreeSpecialCharacters() {
		String path = "../~/~";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParent() + "/~/~", newPath);
	}
	
	@Test
	public void testPathWithAllThreeSpecialCharactersAndMultipleSlashes() {
		String path = "..////////~/~";
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParent() + "/~/~", newPath);
		
		path = "../////~///~/~";
		newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(new File(currentDir).getParent() + "/~/~/~", newPath);
	}
	
	@Test
	public void testPathWithValidRelativePath() {
		String newFolderName = "aaaaaaaa";
		
		String newPath = PathUtils.PathResolver(currentDir, newFolderName);
		
		assertEquals(currentDir + newFolderName, newPath);
	}
	
	@Test
	public void testPathWithInvalidRelativePath() {
		String path = "aaaaaaaa";		
		String newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir + path, newPath);
		
		path = path + "/";
		newPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(currentDir + path, newPath);
	}
	
	@Test
	public void testPathWithSpecialCharacterAndAlphabets() {
		Path p = Paths.get(currentDir);
		int depth = p.getNameCount();
		int newDepth = (int)(Math.random() * 1000000) % depth;
		
		if (newDepth == 0) {
			newDepth = 1;
		}
		
		Path newPath = p.subpath(0, newDepth);
		
		String folderName = "test";
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < depth - newDepth; i ++) {
			sb.append("../");
		}
		
		sb.append(folderName);
		
		String actualPath = PathUtils.PathResolver(currentDir, sb.toString());
				
		assertEquals("/" + newPath.toString() + "/" + folderName, actualPath);
	}
	
	@Test
	public void testPathWithAbsolutePath() {
		Path p = Paths.get(currentDir);
		int depth = p.getNameCount();
		int newDepth = (int)(Math.random() * 1000000) % depth;
		
		if (newDepth == 0) {
			newDepth = 1;
		}
		
		Path newPath = p.subpath(0, newDepth);
		
		String folderName = "test/";
		String path = "/" + newPath.toString() + "/" + folderName;
		String actualPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(path, actualPath);
		
		folderName = "test/asdj@@#%%@&*#*@Rflk";
		
		path = "/" + newPath.toString() + "/" + folderName;
		actualPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(path, actualPath);
		
		folderName = "test/asdj@@#%%@&*#*@Rflk/";
		
		path = "/" + newPath.toString() + "/" + folderName;
		actualPath = PathUtils.PathResolver(currentDir, path);
		
		assertEquals(path, actualPath);
		
		folderName = "test/asdj@@#%%@&*#*@Rflk/";
		
		path = "/" + newPath.toString() + "/" + folderName;
		actualPath = PathUtils.PathResolver(currentDir, path + "/////");
		
		assertEquals(path, actualPath);
		
		folderName = "test/asdj@@#%%@&*#*@Rflk/";
		
		path = "/" + newPath.toString() + "/" + folderName;
		actualPath = PathUtils.PathResolver(currentDir, path + "~~////~~/");
		
		assertEquals(path + "~~/~~/", actualPath);
	}
	
	@Test
	public void testGetRandomSubpath() {
		Path p = Paths.get(currentDir);
		Path newP = PathUtils.GetRandomSubpath(p);
		
		assertEquals(newP.toString() + "/", PathUtils.PathResolver(currentDir, newP.toString()));
	}
}
