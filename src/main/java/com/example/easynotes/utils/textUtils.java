package com.example.easynotes.utils;
import java.util.ArrayList;
import java.util.List;

public class textUtils {
	private textUtils() {

	}
	private static boolean isStringEmpty(String text) {
		return text.length() == 0;
	}

	private static boolean isStringWhiteSpace(String text) {
		return Character.isWhitespace(text.charAt(0));
	}

	public static List<String> splitString(String text) {
		List<String> splitStrings = new ArrayList<>();
		String str="";

		for(int i=0;i<=text.length();i++) {
			if(i==text.length()) {
				if(!isStringEmpty(str)) {
					splitStrings.add(str);
					break;
				}
			}
			if(Character.isWhitespace(text.charAt(i))) {
				if(!isStringEmpty(str) && !isStringWhiteSpace(str)) {
					splitStrings.add(str);
					str = "";
				}
			}
			else str = str + text.charAt(i);
		}
		return splitStrings;
	} 
	
}
