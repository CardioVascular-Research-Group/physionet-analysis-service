package edu.jhu.cvrg.services.physionetAnalysisService.wrapper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomString {

	private static Set<String> tempStringSet = new HashSet<String>();
	
	private static final char[] symbols = new char[36];

	static {
	    for (int idx = 0; idx < 10; ++idx)
	      symbols[idx] = (char) ('0' + idx);
	    for (int idx = 10; idx < 36; ++idx)
	      symbols[idx] = (char) ('a' + idx - 10);
	}

	private static final Random random = new Random();
	
	
	public static String newString(int lenght){
		String tempName = RandomString._next(lenght);
		while (tempStringSet.contains(tempName)) {
			tempName = RandomString._next(lenght);
		}
		tempStringSet.add(tempName);
		
		return tempName;
	}

	private static String _next(int lenght) {
		char[] buf = new char[lenght];
		
	    for (int idx = 0; idx < buf.length; ++idx){ 
	    	buf[idx] = symbols[random.nextInt(symbols.length)];
	    }
	    return new String(buf);
	}
	
	public static void release(String str) {
		tempStringSet.remove(str);
	}
	
	public static void main(String[] args) {
		System.out.println(RandomString.newString(1));
		System.out.println(RandomString.newString(2));
		System.out.println(RandomString.newString(3));
		System.out.println(RandomString.newString(4));
		System.out.println(RandomString.newString(5));
		System.out.println(RandomString.newString(6));
		System.out.println(RandomString.newString(7));
		System.out.println(RandomString.newString(8));
	}
}
