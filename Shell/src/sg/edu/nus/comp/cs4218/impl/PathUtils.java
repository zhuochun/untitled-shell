package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class deals with path issues.
 */
public class PathUtils {

	/**
	 * This function is used to resolve the path information.
	 * 
	 * The function takes in the current working path and the new path, and
	 * will return the resolved path. If the path represents a possible 
	 * directory, the returned path will have a '/' at behind. Any duplicate
	 * '/' will be resolved to only one '/'. E.g. "adsf///asdf//sd/s/" will be
	 * resolved as "adsf/asdf/sd/s/", and "aa/bb" will be resolved as "aa/bb"
	 * if "bb" does not exist or is a file under aa, if "bb" exists and is a
	 * directory, "aa/bb/" will be returned. Further more, "/" is not allowed
	 * in a file/folder name.
	 * 
	 * Special cases:
	 * 		1. Starting with '~/' will be considered as starting from home dir
	 * 		2. Starting with '/' will be considered as starting from root dir
	 * 		3. All '*' fuzzy search expression are not supported currently  
	 * 
	 * Please be noted that this function will not verify if the path is valid,
	 * the caller should validate the path by itself.
	 * 
	 * @return
	 * 		the resolved absolute path.
	 */
	public static String pathResolver(String currentPath, String newPath) {
		String finalAbsolutePath;
		String regulatedNewPath = newPath;
		
		// special cases
		if (regulatedNewPath.equals("~")) {
			regulatedNewPath = System.getProperty("user.home") + "/";
		} else
		if (regulatedNewPath.startsWith("~/")) {
			regulatedNewPath = System.getProperty("user.home").
							 concat('/' + regulatedNewPath.substring(2));
		}
		
		Path curPath = Paths.get(currentPath).resolve(regulatedNewPath).normalize();
		
		finalAbsolutePath = curPath.toString();
		
		// if new path is a directory exist, put "/" behind
		if (curPath.toFile().exists() && curPath.toFile().isDirectory()) {
			if (!finalAbsolutePath.endsWith("/")) {
				finalAbsolutePath = finalAbsolutePath + "/";
			}
		}
		
		// if new path does not exist but should be a directory, put a "/"
		// behind
		if (regulatedNewPath.endsWith("/") && !finalAbsolutePath.endsWith("/")) {
			finalAbsolutePath = finalAbsolutePath + "/";
		}
		
		return finalAbsolutePath;
	}
	
	// Alternative interface of pathResover
	public static String pathResolver(File currentPath, String newPath) {
		return pathResolver(currentPath.toString(), newPath);
	}

	/**
	 * This function is used to get the last element on a file path. The last
	 * element is the last file/ directory on the path.
	 * 
	 * @param path
	 * 		is the path under query.
	 * @return
	 * 		the last element of the given path.
	 */
	public static String getLastElementOfPath(String path) {
		return Paths.get(path).getFileName().toString();
	}
	
	// Alternative interface of getLastElementOfPath
	public static String getLastElementOfPath(File path) {
		return getLastElementOfPath(path.toString());
	}
	
	/**
	 * This function is used to get a random sub-path from a given path. The
	 * resulted sub-path's depth will be longer than 2.
	 * 
	 * @param path
	 * 		is the original path.
	 * @return
	 * 		a random sub-path generated from the given path.
	 */
	public static Path getRandomSubpath(Path path) {
		int depth = path.getNameCount();
		int newDepth;

		if (depth > 2) {
			newDepth = 2 + (int)(Math.random() * 1000000) % (depth - 2);
		} else {
			newDepth = depth;
		}
			
		
		return Paths.get("/" + path.subpath(0, newDepth).toString());
	}
	
	/**
	 * This function is used to get the current absolute working path.
	 * 
	 * @return
	 * 		the current absolute working path.
	 */
	public static Path getCurrentPath() {
		return Paths.get(System.getProperty("user.dir") + "/");
	}
}
