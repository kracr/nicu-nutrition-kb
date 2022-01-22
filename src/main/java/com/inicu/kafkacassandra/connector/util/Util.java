/**
 * 
 */
package com.inicu.kafkacassandra.connector.util;

import com.inicu.kafkacassandra.connector.constants.Constants;

/**
 * @author sanoob
 *
 */
public class Util {
	
	public static void consolePrint(String part1, String part2){
		 String whitespaces = new String(new char[Math.abs(part1.length()-Constants.MAX_WHITESPACES)]).replace('\0', ' ');;
         String line = String.format("%s"+whitespaces+"%s", part1, part2);
         System.out.println(line);
	}

}
