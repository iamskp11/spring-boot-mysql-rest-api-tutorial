package com.example.easynotes.utils;
import java.util.ArrayList;
import java.util.List;

public class textUtils {
	private textUtils() {

	}

	public static List<String> splitString(String text) {
		List<String> splitStrings = new ArrayList<>();

		int start_ptr = 0;
		while(start_ptr < text.length()) {
			while(start_ptr < text.length() && Character.isWhitespace(text.charAt(start_ptr))) {
				start_ptr += 1;
			}
			String str="";
			int end_ptr = start_ptr;
			while(end_ptr < text.length() && !Character.isWhitespace(text.charAt(end_ptr))) {
				str = str + text.charAt(end_ptr);
				end_ptr += 1;
			}
			if(str.length() > 0) splitStrings.add(str);
			start_ptr = end_ptr;
		}
		return splitStrings;
	} 
	
}
