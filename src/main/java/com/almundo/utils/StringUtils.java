package com.almundo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author frank
 */
public class StringUtils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * Date format
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if(date != null) {
			return dateFormat.format(date);
		}else {
			return null;
		}
	}
}
