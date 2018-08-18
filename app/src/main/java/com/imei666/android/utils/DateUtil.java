package com.imei666.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
	public static String getNowStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}

	public static boolean isNowAvailable(String startDate,String endDate) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = simpleDateFormat.parse(startDate);
			Date end = simpleDateFormat.parse(endDate);
			Date now = new Date();
			return (now.before(end)&&now.after(start));
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public static String getDateAfter(long miliSeconds) {
		String result = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long cur = System.currentTimeMillis();
			long after = cur + miliSeconds;
			Date date = new Date(after);
			return simpleDateFormat.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "";
	}
}
