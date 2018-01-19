package org.jeecgframework.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private static DateUtil dateUtil = new DateUtil();

	private DateUtil() {
		// TODO Auto-generated constructor stub
	}

	public static DateUtil getInstance() {
		return dateUtil;
	}
	
	/**
	 * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of
	 * December in the year 2002
	 */
	public static final String ISO_DATE_FORMAT = "yyyyMMdd";

	/**
	 * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th
	 * day of December in the year 2002
	 */
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * Expanded ISO 8601 Date format yyyy-MM-dd HH:mm  2002-12-25 13:45 for the 25th
	 * day of December in the year 2002
	 */
	public static final String ISO_SHORT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	/**
	 * yyyy-MM-dd hh:mm:ss
	 */
	public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy年MM月dd日
	 */
	public static final String CHINESE_EXPANDED_DATE_FORMAT = "yyyy年MM月dd日";
	/**
	 * yyyy/mm/dd；mm/dd；点:分:秒（12/24小时制）
	 */
	public static final String TAIWAN_DATE_FORMAT = "yyyy/MM/ddHHmm";

	/**
	 * Default lenient setting for getDate.
	 */
	private static boolean LENIENT_DATE = false;

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateFormart(Date date) {

		return stringToDate(dateToString(date, ISO_EXPANDED_DATE_FORMAT));
	}

	/**
	 * 暂时不用
	 * 
	 * @param JD
	 * @return
	 */
	protected static final float normalizedJulian(float JD) {

		float f = Math.round(JD + 0.5f) - 0.5f;

		return f;
	}

	public static String getGrpEndDate(String effdate, String loadEndDate,
			String loadBeginDate) {
		long qoutDays = getDateQuot(effdate, loadEndDate);
		String endDate = "";
		if (qoutDays >= 365) {

			endDate = dateToString(
					addDay(addYear(stringToDate(loadBeginDate), 1), -1),
					"yyyy-MM-dd");
		} else {
			endDate = loadEndDate;
		}
		return endDate;
	}

	/**
	 * 浮点值转换成日期格式<br>
	 * 暂时不用 Returns the Date from a julian. The Julian date will be converted to
	 * noon GMT, such that it matches the nearest half-integer (i.e., a julian
	 * date of 1.4 gets changed to 1.5, and 0.9 gets changed to 0.5.)
	 * 
	 * @param JD
	 *            the Julian date
	 * @return the Gregorian date
	 */
	public static final Date toDate(float JD) {

		/*
		 * To convert a Julian Day Number to a Gregorian date, assume that it is
		 * for 0 hours, Greenwich time (so that it ends in 0.5). Do the
		 * following calculations, again dropping the fractional part of all
		 * multiplicatons and divisions. Note: This method will not give dates
		 * accurately on the Gregorian Proleptic Calendar, i.e., the calendar
		 * you get by extending the Gregorian calendar backwards to years
		 * earlier than 1582. using the Gregorian leap year rules. In
		 * particular, the method fails if Y<400.
		 */
		float Z = (normalizedJulian(JD)) + 0.5f;
		float W = (int) ((Z - 1867216.25f) / 36524.25f);
		float X = (int) (W / 4f);
		float A = Z + 1 + W - X;
		float B = A + 1524;
		float C = (int) ((B - 122.1) / 365.25);
		float D = (int) (365.25f * C);
		float E = (int) ((B - D) / 30.6001);
		float F = (int) (30.6001f * E);
		int day = (int) (B - D - F);
		int month = (int) (E - 1);

		if (month > 12) {
			month = month - 12;
		}

		int year = (int) (C - 4715); // (if Month is January or February) or
										// C-4716 (otherwise)

		if (month > 2) {
			year--;
		}

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1); // damn 0 offsets
		c.set(Calendar.DATE, day);

		return c.getTime();
	}

	/**
	 * Returns the days between two dates. Positive values indicate that the
	 * second date is after the first, and negative values indicate, well, the
	 * opposite. Relying on specific times is problematic.
	 * 
	 * @param early
	 *            the "first date"
	 * @param late
	 *            the "second date"
	 * @return the days between the two dates
	 */
	public static final int daysBetween(Date early, Date late) {
		java.util.GregorianCalendar calst = new java.util.GregorianCalendar();
		java.util.GregorianCalendar caled = new java.util.GregorianCalendar();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		calst.set(java.util.GregorianCalendar.MINUTE, 0);
		calst.set(java.util.GregorianCalendar.SECOND, 0);
		caled.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		caled.set(java.util.GregorianCalendar.MINUTE, 0);
		caled.set(java.util.GregorianCalendar.SECOND, 0);
		// startdate.setHours(0);
		// startdate.setMinutes(0);
		// startdate.setSeconds(0);
		// enddate.setHours(0);
		// enddate.setMinutes(0);
		// enddate.setSeconds(0);

		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;
		return days;

		// Calendar c1 = Calendar.getInstance();
		// Calendar c2 = Calendar.getInstance();
		// c1.setTime(early);
		// c2.setTime(late);

		// return daysBetween(c1, c2);
	}

	/**
	 * Returns the days between two dates. Positive values indicate that the
	 * second date is after the first, and negative values indicate, well, the
	 * opposite.
	 * 
	 * @param early
	 * @param late
	 * @return the days between two dates.
	 */
	public static final int daysBetween(Calendar early, Calendar late) {

		return (int) (toJulian(late) - toJulian(early));
	}

	/**
	 * Return a Julian date based on the input parameter. This is based from
	 * calculations found at <a
	 * href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day
	 * Calculations (Gregorian Calendar)</a>, provided by Bill Jeffrys.
	 * 
	 * @param c
	 *            a calendar instance
	 * @return the julian day number
	 */
	public static final float toJulian(Calendar c) {

		int Y = c.get(Calendar.YEAR);
		int M = c.get(Calendar.MONTH);
		int D = c.get(Calendar.DATE);
		int A = Y / 100;
		int B = A / 4;
		int C = 2 - A + B;
		float E = (int) (365.25f * (Y + 4716));
		float F = (int) (30.6001f * (M + 1));
		float JD = C + D + E + F - 1524.5f;

		return JD;
	}

	/**
	 * 暂时不用 Return a Julian date based on the input parameter. This is based
	 * from calculations found at <a
	 * href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day
	 * Calculations (Gregorian Calendar)</a>, provided by Bill Jeffrys.
	 * 
	 * @param date
	 * @return the julian day number
	 */
	public static final float toJulian(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return toJulian(c);
	}

	/**
	 * 日期增加
	 * 
	 * @param isoString
	 *            日期字符串
	 * @param fmt
	 *            格式
	 * @param field
	 *            年/月/日 Calendar.YEAR/Calendar.MONTH/Calendar.DATE
	 * @param amount
	 *            增加数量
	 * @return
	 * @throws ParseException
	 */
	public static final String dateIncrease(String isoString, String fmt,
			int field, int amount) {

		try {
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(stringToDate(isoString, fmt, true));
			cal.add(field, amount);

			return dateToString(cal.getTime(), fmt);

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Time Field Rolling function. Rolls (up/down) a single unit of time on the
	 * given time field.
	 * 
	 * @param isoString
	 * @param field
	 *            the time field.
	 * @param up
	 *            Indicates if rolling up or rolling down the field value.
	 * @param expanded
	 *            use formating char's
	 * @exception ParseException
	 *                if an unknown field value is given.
	 */
	public static final String roll(String isoString, String fmt, int field,
			boolean up) throws ParseException {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(stringToDate(isoString, fmt));
		cal.roll(field, up);

		return dateToString(cal.getTime(), fmt);
	}

	/**
	 * Time Field Rolling function. Rolls (up/down) a single unit of time on the
	 * given time field.
	 * 
	 * @param isoString
	 * @param field
	 *            the time field.
	 * @param up
	 *            Indicates if rolling up or rolling down the field value.
	 * @exception ParseException
	 *                if an unknown field value is given.
	 */
	public static final String roll(String isoString, int field, boolean up)
			throws ParseException {

		return roll(isoString, DATETIME_PATTERN, field, up);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @param lenient
	 *            日期越界标志
	 * @return
	 */
	public static Date stringToDate(String dateText, String format,
			boolean lenient) {

		if (dateText == null) {

			return null;
		}

		DateFormat df = null;

		try {

			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format);
			}

			// setLenient avoids allowing dates like 9/32/2001
			// which would otherwise parse to 10/2/2001
			df.setLenient(false);

			return df.parse(dateText);
		} catch (ParseException e) {

			return null;
		}
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {

		return stringToDate(dateString, format, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 */
	public static Date stringToDate(String dateString) {
		if (!"".equals(dateString) && dateString != null) {
			// ISO_DATE_FORMAT = "yyyyMMdd";
			if (dateString.trim().length() == 8) {
				return stringToDate(dateString, ISO_DATE_FORMAT, LENIENT_DATE);
			} else if (dateString.trim().length() == 10) {
				// ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
				return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT,
						LENIENT_DATE);
			} else if (dateString.trim().length() == 19) {
				// DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
				return stringToDate(dateString, DATETIME_PATTERN, LENIENT_DATE);
			} else if (dateString.trim().length() == 11) {
				// CHINESE_EXPANDED_DATE_FORMAT = "yyyy年MM月dd日";
				return stringToDate(dateString, CHINESE_EXPANDED_DATE_FORMAT,
						LENIENT_DATE);
			}
		}
		return null;
	}

	/**
	 * 根据时间变量返回时间字符串
	 * 
	 * @return 返回时间字符串
	 * @param pattern
	 *            时间字符串样式
	 * @param date
	 *            时间变量
	 */
	public static String dateToString(Date date, String pattern) {

		if (date == null) {

			return null;
		}

		try {

			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);

			return sfDate.format(date);
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 返回当前时间
	 * 
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();

		return dtNow;
	}

	/**
	 * 返回当前日期字符串
	 * 
	 * @param pattern
	 *            日期字符串样式
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		return dateToString(getCurrentDateTime(), pattern);
	}

	/**
	 * 返回当前日期字符串 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		return dateToString(getCurrentDateTime(), ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 返回当前日期+时间字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringWithTime(Date date) {

		return dateToString(date, DATETIME_PATTERN);
	}

	/**
	 * 日期增加-按日增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	/**
	 * 日期增加-按月增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByMonth(Date date, int mnt) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, mnt);

		return cal.getTime();
	}

	/**
	 * 日期增加-按年增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByYear(Date date, int mnt) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, mnt);

		return cal.getTime();
	}

	/**
	 * 日期增加-按年增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static String dateIncreaseByYearforString(String date, int mnt) {
		Date date1 = stringToDate(date);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date1);
		cal.add(Calendar.YEAR, mnt);
		String ss = dateToString(cal.getTime(), ISO_DATE_FORMAT);
		return dateIncreaseByDay(ss, -1);
	}

	/**
	 * 日期增加
	 * 
	 * @param date
	 *            日期字符串 yyyy-MM-dd
	 * @param days
	 * @return 日期字符串 yyyy-MM-dd
	 */
	public static String dateIncreaseByDay(String date, int days) {
		return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
	}

	/**
	 * 日期增加
	 * 
	 * @param date
	 *            日期字符串
	 * @param fmt
	 *            日期格式
	 * @param days
	 * @return
	 */
	public static String dateIncreaseByDay(String date, String fmt, int days) {
		return dateIncrease(date, fmt, Calendar.DATE, days);
	}

	/**
	 * 日期字符串格式转换
	 * 
	 * @param src
	 *            日期字符串
	 * @param desfmt
	 *            目标日期格式
	 * @return
	 */
	public static String stringToString(String dateString, String desfmt) {
		// ISO_DATE_FORMAT = "yyyyMMdd";
		if (dateString.trim().length() == 8) {
			return stringToString(dateString, ISO_DATE_FORMAT, desfmt);
		} else if (dateString.trim().length() == 10) {
			// ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
			return stringToString(dateString, ISO_EXPANDED_DATE_FORMAT, desfmt);
		} else if (dateString.trim().length() == 19) {
			// DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
			return stringToString(dateString.substring(0, 10),
					ISO_EXPANDED_DATE_FORMAT, desfmt);
		} else if (dateString.trim().length() == 11) {
			// CHINESE_EXPANDED_DATE_FORMAT = "yyyy年MM月dd日";
			return stringToString(dateString, CHINESE_EXPANDED_DATE_FORMAT,
					desfmt);
		}
		return null;
	}

	/**
	 * 日期字符串格式转换
	 * 
	 * @param src
	 *            日期字符串
	 * @param srcfmt
	 *            源日期格式
	 * @param desfmt
	 *            目标日期格式
	 * @return
	 */
	public static String stringToString(String src, String srcfmt, String desfmt) {
		return dateToString(stringToDate(src, srcfmt), desfmt);
	}

	/**
	 * yyyy年MM月dd日至yyyy年MM月dd日 获取止期
	 * 
	 * @param src
	 *            日期字符串
	 * @param desfmt
	 *            目标日期格式
	 * @return
	 */
	public static String getPolicyEndDate(String src, String srcfmt,
			String desfmt) {
		if (src == null || src.trim().equals("") || src.length() < 23)
			return null;
		src = src.substring(12, 23);
		return stringToString(src, srcfmt, desfmt);
	}

	/**
	 * yyyy年MM月dd日至yyyy年MM月dd日 获取止期
	 * 
	 * @param src
	 *            日期字符串
	 * @param desfmt
	 *            目标日期格式
	 * @return
	 */
	public static String getPolicyEffDate(String src, String srcfmt,
			String desfmt) {
		if (src == null || src.trim().equals("") || src.length() < 23)
			return null;
		src = src.substring(0, 11);
		return stringToString(src, srcfmt, desfmt);
	}

	/**
	 * yyyy年MM月dd日
	 * 
	 * @param src
	 *            日期字符串
	 * @param desfmt
	 *            目标日期格式
	 * @return
	 */
	public static String getChineseDate(String src) {
		String renStr = null;
		if (!"".equals(src) && src != null) {
			renStr = src.substring(0, 4) + "年" + src.substring(5, 6) + "月"
					+ src.substring(6, 8) + "日";
		}
		return renStr;
	}

	/**
	 * 两个日期之间的天数
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getDateQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime() - date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	/**
	 * 在日期上加指定的年数
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static Date addYear(Date date1, int addYear) {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date1); // 设置当前日期
		c.add(Calendar.YEAR, addYear); // 日期加1
		resultDate = c.getTime(); // 结果
		return resultDate;
	}

	/**
	 * 在日期上加指定的月数
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static Date addMonth(Date date1, int addMonth) {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date1); // 设置当前日期
		c.add(Calendar.MONTH, addMonth); // 日期加1
		resultDate = c.getTime(); // 结果
		return resultDate;
	}

	/**
	 * 在日期上加指定的天数,返回Date类型
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static Date addDay(Date date1, int addDay) {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date1); // 设置当前日期
		c.add(Calendar.DATE, addDay); // 日期加1
		resultDate = c.getTime(); // 结果
		return resultDate;
	}

	/**
	 * 在日期上加指定的天数,返回String类型
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static String addDayByString(String date, int addDay) {
		String retDate = "";
		// 空值校验
		if (date == null || "".equals(date)
				|| "".equals(date.replaceAll("-", "").trim())) {
			return retDate;
		}
		Date date1 = stringToDate(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date1); // 设置当前日期
		c.add(Calendar.DATE, addDay); // 日期加1
		Date resultDate = c.getTime(); // 结果
		return dateToString(resultDate);
	}
	
	/**
	 * 获取月份的第一天
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(Date date){
		Calendar   ca   =   Calendar.getInstance();
		ca.setTime(date);                            
		ca.set(Calendar.DAY_OF_MONTH,   1);
		Date   firstDate   =   ca.getTime();
		return dateToString(firstDate);
	}

	 /** 
     * 获取日期最早时间，如传入2014-12-26，返回2014-12-26 0:00:00 
     * @param date 
     * @return 
     */  
    public static Date getDateStart(Date d){ 
    	Calendar   ca   =   Calendar.getInstance();
        ca.setTime(d);  
        ca.set(Calendar.HOUR, 0);  
        ca.set(Calendar.MINUTE, 0);  
        ca.set(Calendar.SECOND, 0);  
        return ca.getTime();  
    }  
      
    /** 
     * 获取日期最晚时间，如传入2014-12-26，返回2014-12-26 23:59:59 
     * @param date 
     * @return 
     */  
    public static Date getDateEnd(Date d){
    	Calendar   ca   =   Calendar.getInstance();
        ca.setTime(d);  
        ca.set(Calendar.HOUR, 23);  
        ca.set(Calendar.MINUTE, 59);  
        ca.set(Calendar.SECOND, 59);  
        return  ca.getTime();  
    }  
	
	
	
	public ArrayList<Date> getDateList(Date startTime, Date endTime) {
		ArrayList<Date> timeList = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		timeList.add(cal.getTime());
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startTime);
		endCalendar.setTime(endTime);
		long startMill = startCalendar.getTimeInMillis();
		long endMill = endCalendar.getTimeInMillis();
		while (startMill < endMill) {
			startMill += 1 * 24 * 3600 * 1000;
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(startMill);
			timeList.add(calendar.getTime());
		}
		return timeList;
	}
	
	public static void main(String[] args) {
		int mnt = -1;
		Date date2 = stringToDate("2017-03-30 15:57:22");
		System.out.println("dddd:"+date2);
		Date d = dateIncreaseByMonth(date2, mnt);
		System.out.println(d);
	}
	
	
	
}





