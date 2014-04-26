package sg.edu.nus.comp.cs4218.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class deals with Range issues.
 */
public class RangeUtils {
	/**
	 * This class defines a range with two parameters: left and right. The
	 * resulted range is [left, right], inclusive.
	 */
	public class Range{
		public int left, right;
		
		/**
		 * Default constructor. Initialize the range to be [0, 0].
		 */
		public Range() {
			left = right = 0;
		}
		
		/**
		 * Alternative constructor. Initialize the range to be [a, b], where
		 * a and b are parameters specified by the caller.
		 * 
		 * @param a
		 * 		is the lower bound of the range (inclusive).
		 * @param b
		 * 		is the upper bound of the range (inclusive). 
		 */
		public Range(int a, int b) {
			left = a;
			right = b;
			
			if (right < left) {
				throw new InvalidParameterException();
			}
		}
		
		/**
		 * Alternative constructor. Initialize the range to be a, where a
		 * should be specified by the caller.
		 *  
		 * @param a
		 * 		is the expected range.
		 */
		public Range(Range a) {
			left = a.left;
			right = a.right;
		}
	};
	
	/**
	 * This function is used to parse a list of range from String to a list of
	 * Range.
	 * 
	 * @param list
	 * 		is the String specifies a list of the range. The String must be in
	 * a format of "a-b,c-d,e-f", where a, b, c, d, e, f are positive integers
	 * and left hand side of '-' must less or equal to right hand side of '-'.
	 * 
	 * Note: String parsed to here won't contain any spaces since all the
	 * spaces will be eliminated during constructing the ArgList.
	 * 
	 * @return
	 * 		an ArrayList of unresolved Range.
	 */
	public static ArrayList<Range> parseRange(String list) {
		ArrayList<Range> rangeList = new ArrayList<Range>();

		String[] subRange = list.split(",");
		
		for (String range : subRange) {
			String[] bounds = range.split("-");
			
			// not a single value or a range
			if (bounds.length != 2 && bounds.length != 1 ||
				bounds.length == 1 && bounds[0].equals("")) {
				throw new IllegalArgumentException("LIST in wrong format!");
			} else {
				try {
					int left, right;
					
					left = right = Integer.parseInt(bounds[0]);
					
					if (bounds.length == 2) {
						right = Integer.parseInt(bounds[1]);
					}
					
					RangeUtils.Range r = new RangeUtils().new Range(left, right);
					
					rangeList.add(r);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Numbers in wrong format!");
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("Invalid Range!");
				}
			}
		}
		
		return rangeList;
	}
	
	/**
	 * This function is used to merge two ranges.
	 * 
	 * @param a
	 * 		one of the range under merge.
	 * @param b
	 * 		the other range under merge.
	 * @return
	 * 		an ArrayList of the resolved range. It contains 2 ranges if the
	 * two given ranges cannot be merged; it contains 1 merged range if the
	 * two given ranges has an intersection.
	 */
	public static ArrayList<Range> mergeRange(Range a, Range b) {
		ArrayList<Range> range = new ArrayList<Range>();
		
		if (a == null || b == null) {
			return null;
		}
		
		if (a.left < b.left) {
			range.add(a);
			range.add(b);
		} else {
			range.add(b);
			range.add(a);
		}
		
		// b intersect with a
		if (range.get(1).left <= range.get(0).right) {
			if (range.get(1).right > range.get(0).right) {
				range.get(0).right = range.get(1).right;
			}
			
			range.remove(1);
		}
		
		return range;
	}
	
	/**
	 * This function is used to merge and sort the range list according to
	 * left end.
	 * 
	 * If two ranges share the same left end, one with smaller right end will
	 * be prioritized.
	 *   
	 * @param rangeList
	 * 		is a list of unresolved Range.
	 * @return
	 * 		an ArrayList of resolved and sorted Range.
	 */
	public static ArrayList<Range> mergeRange(ArrayList<Range> rangeList) {
		ArrayList<Range> finalRange = new ArrayList<Range>();
		
		if (rangeList == null) {
			return null;
		}
		
		// Sort according to left end. If tied, range with smaller right end
		// will be placed before the other
		Collections.sort(rangeList, new Comparator<Range>() {
			@Override
			public int compare(Range a, Range b) {
				if (a.left - b.left < 0) return -1;
				if (a.left - b.left > 0) return 1;
				
				return a.right - b.right;
			}
		});
		
		Range curRange = rangeList.get(0);
		
		for (int i = 1; i < rangeList.size(); i ++) {
			ArrayList<Range> tmpRange = mergeRange(curRange, rangeList.get(i));
			
			curRange = tmpRange.get(tmpRange.size() - 1);
			
			// the range can no longer be merged
			if (tmpRange.size() == 2) {
				// push back the previous range
				finalRange.add(tmpRange.get(0));
			}
		}
		
		finalRange.add(curRange);
		
		return finalRange;
	}
}
