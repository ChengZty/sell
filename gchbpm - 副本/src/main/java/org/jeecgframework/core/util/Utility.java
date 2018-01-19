package org.jeecgframework.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Utility {

	public static final String REVISION = "$Revision: 1.17 $";

	private static final Log log = LogFactory.getLog(Utility.class);

	public static final String UNIT_YEAR = "y";
	public static final String UNIT_MONTH = "M";
	public static final String UNIT_DAY = "d";
	public static final String UNIT_HOUR = "H";
	public static final String UNIT_MINUTE = "m";
	public static final String UNIT_SECOND = "s";

	private Utility() {
	}
	
	public static BigDecimal convertToNumber(BigDecimal quantity){
		if(1 == quantity.setScale(1,RoundingMode.FLOOR).compareTo(quantity.setScale(0,RoundingMode.FLOOR))){
        	quantity = quantity.setScale(1,RoundingMode.FLOOR);
        }else{
        	quantity = quantity.setScale(0,RoundingMode.FLOOR);
        }
		return quantity;
	}
	  
	public static boolean isNotEmpty(Object obj) {
		if (obj instanceof String) {
			return !isEmpty((String) obj);
		} else if (obj instanceof Long) {
			return !isEmpty((Long) obj);
		} else if (obj instanceof Integer) {
			return !isEmpty((Integer) obj);
		} else if (obj instanceof StringBuffer) {
			return !isEmpty((StringBuffer) obj);
		} else if (obj instanceof List) {
			return !isEmpty((List) obj);
		} else if (obj instanceof Set) {
			return !isEmpty((Set) obj);
		} else if (obj instanceof String[]) {
			return !isEmpty((String[]) obj);
		} else if (obj instanceof Object[]) {
			return !isEmpty((Object[]) obj);
		} else {
			return !isEmpty(obj);
		}
	}

	public static boolean equal(String s, String t) {
		if (s == null && t == null)
			return true;
		if (s != null && t != null)
			return s.trim().equals(t.trim());
		if (s != null && s.equals(t))
			return true;
		return false;
	}

	public static boolean equal(Integer s, Integer t) {
		if (s == null && t == null)
			return true;
		if (s != null && s.equals(t))
			return true;
		return false;
	}

	public static boolean equal(Long s, Long t) {
		if (s == null && t == null)
			return true;
		if (s != null && s.equals(t))
			return true;
		return false;
	}

	/**
	 * isEmpty
	 * 
	 * Test to see whether input is representing a NULL value.
	 * 
	 * @param val
	 *            An Object
	 * @return True if it represents NULL; false if it is not.
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return isEmpty((String) obj);
		} else if (obj instanceof Long) {
			return isEmpty((Long) obj);
		} else if (obj instanceof Integer) {
			return isEmpty((Integer) obj);
		} else if (obj instanceof StringBuffer) {
			return isEmpty((StringBuffer) obj);
		} else if (obj instanceof List) {
			return isEmpty((List) obj);
		} else if (obj instanceof Set) {
			return isEmpty((Set) obj);
		} else if (obj instanceof String[]) {
			return isEmpty((String[]) obj);
		} else if (obj instanceof Object[]) {
			return isEmpty((Object[]) obj);
		} else {
			return obj == null;
		}
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0 || str.trim().equals("")||str.equals("null"));
	}

	public static boolean isEmpty(Long lng) {
		return (lng == null);
	}

	public static boolean isEmpty(Integer inte) {
		return (inte == null);
	}

	/**
	 * isEmpty
	 * 
	 * Test to see whether input string buffer is empty.
	 * 
	 * @param str
	 *            A StringBuffer
	 * @return True if it is empty; false if it is not.
	 */
	public static boolean isEmpty(StringBuffer stringBuffer) {
		return (stringBuffer == null || stringBuffer.length() == 0 || stringBuffer
				.toString().trim().equals(""));
	}

	/**
	 * isEmpty
	 * 
	 * Test to see whether input string is empty.
	 * 
	 * @param str
	 *            A String
	 * @return True if it is empty; false if it is not.
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * isEmpty
	 * 
	 * Test to see whether input is empty.
	 * 
	 * @param StringArray
	 *            A array
	 * @return True if it is empty; false if it is not.
	 */
	public static boolean isEmpty(String[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * isEmpty
	 * 
	 * Test to see whether input is empty.
	 * 
	 * @param list
	 *            A List
	 * @return True if it is empty; false if it is not.
	 */
	public static boolean isEmpty(java.util.List list) {
		
		return (list == null || list.isEmpty()||list.get(0)==null);
	}

	/**
	 * Test to see whether input is empty.
	 * 
	 * @param set
	 * @return
	 */
	public static boolean isEmpty(java.util.Set set) {
		return (set == null || set.isEmpty());
	}

	/**
	 * getCurrentTimestamp
	 * 
	 * Returns current time in Timestamp object.
	 * 
	 * @return Timestamp object which representing the current time.
	 */
	public static java.sql.Timestamp getCurrentTimestamp() {
		java.util.Calendar tmp = java.util.Calendar.getInstance();
		tmp.clear(java.util.Calendar.MILLISECOND);
		return (new java.sql.Timestamp(tmp.getTime().getTime()));
	}

	/**
	 * getPropertyList
	 * 
	 * Extract a list of properties of javabeans thro. using reflection
	 * 
	 * @param col
	 *            the collection of javabeans to be extract
	 * @param property
	 *            the property value of the javabean to be extracted
	 * @throws IllegalAccessException
	 *             if error found in PropertyUtil
	 * @throws InvocationTargetException
	 *             if error found in PropertyUtil
	 * @throws NoSuchMethodException
	 *             if no such property
	 * @return A list of extracted properties
	 */
	public static java.util.List getPropertyList(Collection col, String property)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List propertyList = new ArrayList();
		if (col == null) {
			return propertyList;
		}
		Iterator itemIter = col.iterator();
		while (itemIter.hasNext()) {
			Object item = itemIter.next();
			if (item != null) {
				propertyList.add(PropertyUtils.getProperty(item, property));
			}
		}
		return propertyList;// ((Object[]) values.toArray(new
							// Object[values.size()]));
	}

	/**
	 * getPropertyArray
	 * 
	 * Retrive an array of object base on the collection thro. reflection e.g
	 * groupList is a ArrayList of Group, then getArrayProperty(groupList,"ID")
	 * will return an List of ID values.
	 * 
	 * @param col
	 *            The collection of javabeans to be extract
	 * @param propertyKey
	 *            The property value key of the javabean to be extracted
	 * @throws IllegalAccessException
	 *             If error found in PropertyUtil
	 * @throws InvocationTargetException
	 *             If error found in PropertyUtil
	 * @throws NoSuchMethodException
	 *             If no such property
	 * @return Properties in array format
	 */
	public static Object[] getPropertyArray(Collection value,
			String propertyKey, Object[] target) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return getPropertyList(value, propertyKey).toArray(target);// ((Object[])
																	// values.toArray(new
																	// Object[values.size()]));
	}

	/**
	 * getPropertyMap
	 * 
	 * Retrive an map of object base on the collection thro. reflection e.g
	 * groupList is a ArrayList of Group, then getArrayProperty(groupList,"ID")
	 * will return an List of ID values.
	 * 
	 * @param col
	 *            The collection of javabeans to be extract
	 * @param propertyKey
	 *            The property value key of the javabean to be extracted
	 * @throws IllegalAccessException
	 *             If error found in PropertyUtil
	 * @throws InvocationTargetException
	 *             If error found in PropertyUtil
	 * @throws NoSuchMethodException
	 *             If no such property
	 * @return Properties in map format
	 */
	public static java.util.Map getPropertyMap(Collection col,
			String propertyKey) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		java.util.HashMap values = new java.util.HashMap();
		if (col != null) {
			Iterator itemIter = (col).iterator();
			while (itemIter.hasNext()) {
				Object value = itemIter.next();
				if (value != null) {
					Object key = PropertyUtils.getProperty(value, propertyKey);
					if (key != null) {
						values.put(key, value);
					}
				}
			}
		}
		return values;
	}

	/**
	 * getPropertyMapList
	 * 
	 * Retrive an map of arraylist base on the collection thro. reflection e.g
	 * groupList is a ArrayList of Group, then
	 * getPropertyMapList(groupList,"ID") will return an Map of ArrayList where
	 * which list contains objects with the same key.
	 * 
	 * @param col
	 *            The collection of javabeans to be extract
	 * @param propertyKey
	 *            The property value key of the javabean to be extracted
	 * @throws IllegalAccessException
	 *             If error found in PropertyUtil
	 * @throws InvocationTargetException
	 *             If error found in PropertyUtil
	 * @throws NoSuchMethodException
	 *             If no such property
	 * @return Properties in map format with arraylist as item
	 */
	public static java.util.Map getPropertyMapList(Collection col,
			String propertyKey) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		java.util.HashMap values = new java.util.HashMap();
		if (col != null) {
			Iterator itemIter = (col).iterator();
			while (itemIter.hasNext()) {
				Object value = itemIter.next();
				if (value != null) {
					Object key = PropertyUtils.getProperty(value, propertyKey);
					if (key != null) {
						List objList = (List) values.get(key);
						if (objList == null) {
							objList = new ArrayList();
						}
						objList.add(value);
						values.put(key, objList);
					}
				}
			}
		}
		return values;
	}

	/**
	 * Convert an Object Array into String Array by calling obj.toString()
	 * 
	 * @param obj
	 *            The object array.
	 * @return The String Array representing that object array
	 */
	public static String[] getStringArray(Object[] obj) {
		if (obj == null) {
			return null;
		}

		String[] strArray = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			strArray[i] = obj[i].toString();
		}
		return strArray;
	}

	/**
	 * 
	 * @param String
	 *            : Query
	 * @return String[]: Array of logical param
	 */
	public static String[] getLogicalString(String query) {
		String andString[] = query.toUpperCase().split("\\s(AND|\\+)\\s");
		String orString[] = query.toUpperCase().split("\\sOR\\s");
		String notString[] = query.toUpperCase().split("\\s(\\-|NOT)\\s");
		String resultString[] = null;
	
		if (andString.length > 1) {
			for (int i = 0; i < andString.length; i++) {
				andString[i] = "AND";
			}
			resultString = andString;
		} else if (orString.length > 1) {
			for (int i = 0; i < orString.length; i++) {
				orString[i] = "OR";
			}
			resultString = orString;
		} else if (notString.length > 1) {
			for (int i = 0; i < notString.length; i++) {
				notString[i] = "NOT";
			}
			resultString = notString;
		}

		return resultString;
	}

	/**
	 * splitLogicalString
	 * 
	 * @param query
	 *            Query
	 * @return String[] result splited array
	 */
	public static String[] splitLogicalString(String query) {
		String andString[] = query.toUpperCase().split("\\s(AND|\\+)\\s");
		String orString[] = query.toUpperCase().split("\\sOR\\s");
		String notString[] = query.toUpperCase().split("\\s(\\-|NOT)\\s");
		String resultString[] = null;
	
		if (andString.length > 1) {
			resultString = andString;
		} else if (orString.length > 1) {
			resultString = orString;
		} else if (notString.length > 1) {
			resultString = notString;
		} else {
			resultString = new String[1];
			resultString[0] = query;
		}
		return resultString;
	}

	/**
	 * getList
	 * 
	 * @param ary
	 *            The object array.
	 * @return The list contains all the object array elements.
	 */
	public static List getList(Object[] ary) {
		if (ary == null) {
			return (null);
		}
		List result = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			result.add(ary[i]);
		}
		return (result);
	}

	/**
	 * getStringArray
	 * 
	 * @param ary
	 *            The object array.
	 * @return The list contains all the object array elements.
	 */
	public static String[] getStringArray(List list) {
		if (list == null) {
			return (null);
		}
		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			try {
				result[i] = (String) list.get(i);
			} catch (ClassCastException ce) {
				result[i] = ((Integer) list.get(i)).toString();
			}
		}
		return (result);
	}

	/**
	 * Returns Calendar converted from Timestamp.
	 * 
	 * @param inTime
	 *            Source timestamp which to be converted.
	 * @return Calendar object which converted from input.
	 */
	public static java.util.Calendar timestampToCalendar(
			java.sql.Timestamp inTime) {
		if (inTime == null) {
			return (null);
		}
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(inTime);
		return (cal);
	}

	/**
	 * Returns Timestamp converted from Calendar.
	 * 
	 * @param inCal
	 *            Source calendar which to be converted.
	 * @return Timestamp object which converted from input.
	 */
	public static java.sql.Timestamp calendarToTimestamp(
			java.util.Calendar inCal) {
		if (inCal == null) {
			return (null);
		}
		java.sql.Timestamp time = new java.sql.Timestamp(inCal.getTime()
				.getTime());
		return (time);
	}

	public static java.sql.Timestamp addTime(java.sql.Timestamp src, int val,
			String unit) {
		if (UNIT_YEAR.equals(unit)) {
			return addYear(src, val);
		} else if (UNIT_MONTH.equals(unit)) {
			return addMonth(src, val);
		} else if (UNIT_DAY.equals(unit)) {
			return addDay(src, val);
		} else if (UNIT_HOUR.equals(unit)) {
			return addHour(src, val);
		} else if (UNIT_MINUTE.equals(unit)) {
			return addMinute(src, val);
		} else if (UNIT_SECOND.equals(unit)) {
			return addSecond(src, val);
		} else {
			return null;
		}
	}

	/**
	 * addYear - Returns the timestamp after adding certain amount of Year.
	 * 
	 * @param src
	 *            Source timestamp.
	 * @param val
	 *            Number of years going to add, can be negative number.
	 * @return Timestamp after adding certain amount of years.
	 */
	public static java.sql.Timestamp addYear(java.sql.Timestamp src, int val) {
		java.util.Calendar tmpCal = timestampToCalendar(src);
		if (tmpCal == null) {
			return (null);
		}
		tmpCal.add(java.util.Calendar.YEAR, val);
		return (calendarToTimestamp(tmpCal));
	}

	/**
	 * addMonth - Returns the timestamp after adding certain amount of Month.
	 * 
	 * @param src
	 *            Source timestamp.
	 * @param val
	 *            Number of months going to add, can be negative number.
	 * @return Timestamp after adding certain amount of months.
	 */
	public static java.sql.Timestamp addMonth(java.sql.Timestamp src, int val) {
		java.util.Calendar tmpCal = timestampToCalendar(src);
		if (tmpCal == null) {
			return (null);
		}
		tmpCal.add(java.util.Calendar.MONTH, val);
		return (calendarToTimestamp(tmpCal));
	}

	/**
	 * addDay - Returns the timestamp after adding certain amount of day.
	 * 
	 * @param src
	 *            Source timestamp.
	 * @param val
	 *            Number of days going to add, can be negative number.
	 * @return Timestamp after adding certain amount of days.
	 */
	public static java.sql.Timestamp addDay(java.sql.Timestamp src, int val) {
		java.util.Calendar tmpCal = timestampToCalendar(src);
		if (tmpCal == null) {
			return (null);
		}
		tmpCal.add(java.util.Calendar.DAY_OF_MONTH, val);
		return (calendarToTimestamp(tmpCal));
	}

	/**
	 * addHour - Returns the timestamp after adding certain amount of hours.
	 * 
	 * @param src
	 *            Source timestamp.
	 * @param val
	 *            Number of hours going to add, can be negative number.
	 * @return Timestamp after adding certain amount of hours.
	 */
	public static java.sql.Timestamp addHour(java.sql.Timestamp src, int val) {
		java.util.Calendar tmpCal = timestampToCalendar(src);
		if (tmpCal == null) {
			return (null);
		}
		tmpCal.add(java.util.Calendar.HOUR, val);
		return (calendarToTimestamp(tmpCal));
	}

	/**
	 * addMinute - Returns the timestamp after adding certain amount of minutes.
	 * 
	 * @param src
	 *            Source timestamp.
	 * @param val
	 *            Number of minutes going to add, can be negative number.
	 * @return Timestamp after adding certain amount of minutes.
	 */
	public static java.sql.Timestamp addMinute(java.sql.Timestamp src, int val) {
		java.util.Calendar tmpCal = timestampToCalendar(src);
		if (tmpCal == null) {
			return (null);
		}
		tmpCal.add(java.util.Calendar.MINUTE, val);
		return (calendarToTimestamp(tmpCal));
	}

	/**
	 * addSecond - Returns the timestamp after adding certain amount of seconds.
	 * 
	 * @param src
	 *            Source timestamp.
	 * @param val
	 *            Number of seconds going to add, can be negative number.
	 * @return Timestamp after adding certain amount of seconds.
	 */
	public static java.sql.Timestamp addSecond(java.sql.Timestamp src, int val) {
		java.util.Calendar tmpCal = timestampToCalendar(src);
		if (tmpCal == null) {
			return (null);
		}
		tmpCal.add(java.util.Calendar.SECOND, val);
		return (calendarToTimestamp(tmpCal));
	}

	/**
	 * Call this function to set the time section of a calendar
	 * 
	 * @param inCalendar
	 * @param hr
	 * @param min
	 * @param sec
	 * @param milliSec
	 */
	public static void setCalendarTime(Calendar inCalendar, int hr, int min,
			int sec, int milliSec) {
		if (inCalendar != null) {
			if (hr != -1) {
				inCalendar.set(Calendar.HOUR_OF_DAY, hr);
			}
			if (min != -1) {
				inCalendar.set(Calendar.MINUTE, min);
			}
			if (sec != -1) {
				inCalendar.set(Calendar.SECOND, sec);
			}
			if (milliSec != -1) {
				inCalendar.set(Calendar.MILLISECOND, milliSec);
			}
		}
	}

	/**
	 * 1 day = 1*24*60*60*1000 millisecond.
	 * 
	 * @param dayValue
	 * @return
	 */
	public static int dayValue2Millisecond(int dayValue) {
		return (dayValue * 24 * 60 * 60 * 1000);
	}

	/**
	 * Call this function to round off the second and millisecond of time.
	 * 
	 * @param timestamp
	 *            The timestamp value to be strip to minutes
	 * @return
	 */

	public static java.sql.Timestamp stripToMinutes(java.sql.Timestamp timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp.getTime());
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int time = cal.get(Calendar.MINUTE);

		cal.clear();
		cal.set(year, month, day, hour, time);
		timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
		return timestamp;
	}

	/**
	 * Function to convert a resource bundle into java.util.Map
	 * 
	 * @param bundleName
	 *            The bundle name to parse
	 * @return the key / value pair in java.util.Map format
	 */
	public static Map parseResourceBundle(String bundleName) {
		Map paraMap = new HashMap();
		ResourceBundle resourceBundle = null;
		try {
			resourceBundle = ResourceBundle.getBundle(bundleName);
			Enumeration resKeys = resourceBundle.getKeys();
			while (resKeys.hasMoreElements()) {
				String key = (String) resKeys.nextElement();
				String value = resourceBundle.getString(key);
				if (value == null) {
					value = "";
				}
				paraMap.put(key, value);
			}
		} catch (java.util.MissingResourceException e) {
			// ignore the exception and return empty map
		}
		return paraMap;
	}

	/**
	 * 获得从指定日期后第i天的日期
	 * @param i   整数往后推,负数往前移动
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getNextDate(Date date,int i){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,i);//
		 date = calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 return formatter.format(date);
	
	}
	/**
	 * 
	 * @param stringArray
	 *            A String Array
	 * @param logic
	 *            The value is "AND" or "OR"
	 * @return the String /
	 */
	public static String genericLogicSql(String[] stringArray, String logic) {
		String returnTo = null;
		if (!Utility.isEmpty(stringArray)) {
			int count = 0;
			for (int i = 0; i < stringArray.length; i++) {
				if (count == 0) {
					returnTo = stringArray[i];
				} else {
					returnTo = returnTo + " " + logic + " " + stringArray[i];
				}
				count = 1;
			}
		}
		return returnTo;

	}

	public static String getUTF8(String str) {
		String ret = null;
		if (Utility.isNotEmpty(str)) {
			try {
				ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			ret = "";
		}
		return ret;
	}

	/**
	 * the method's result was used decsion isCurrentDate by startDate and
	 * endDate
	 * 
	 * @param startDate
	 *            is a specifically Timestamp
	 * @param endDate
	 *            is a specifically Timestamp
	 * @return
	 */
	public static boolean isCurrentDataByStartdataAndEnddata(
			Timestamp startDate, Timestamp endDate) {
		boolean result = false;
		Timestamp currentDate = getCurrentTimestamp();
		if (endDate == null) {
			if (currentDate.compareTo(startDate) >= 0) {
				result = true;
			}
		} else {
			if ((currentDate.compareTo(startDate)) >= 0
					&& (currentDate.compareTo(endDate)) <= 0) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * delete this repeat character
	 * 
	 * @param repeatStr
	 *            The String contain the repeat character
	 * @param separator
	 *            The list separator
	 * @return noRepeatStr The String has not contain the repeat character
	 */
	public static String deleteRepeatStr(String repeatStr, String separator) {
		if (repeatStr == null) {
			return null;
		}
		StringBuffer noRepeatStr = new StringBuffer();
		String[] strElement = repeatStr.split(separator);
		List strlist = new ArrayList();
		for (int i = 0; i < strElement.length; i++) {
			if (!strlist.contains(strElement[i])) {
				strlist.add(strElement[i]);
			}
		}
		for (int i = 0; i < strlist.size(); i++) {
			noRepeatStr.append(strlist.get(i));
			if (i != strlist.size() - 1) {
				noRepeatStr.append(separator);
			}
		}
		return noRepeatStr.toString();
	}

	/**
	 * remove the repeat element in the list
	 * 
	 * @param list
	 * @return newList
	 */
	public static List removeDuplicate(List list) {
		if (Utility.isEmpty(list)) {
			return null;
		}
		HashSet h = new HashSet(list);
		List newList = new ArrayList();
		newList.addAll(h);
		return newList;
	}

//	public static void setBaseObjectBaseFieldValue(AbstractBaseObject obj,
//			Long operatorId) {
//		obj.setRecordStatus(GlobalConstant.RECORD_STATUS_ACTIVE);
//		obj.setUpdateCount(0l);
//		obj.setCreatorId(operatorId);
//		obj.setCreateDate(Utility.getCurrentTimestamp());
//		obj.setUpdaterId(operatorId);
//		obj.setUpdateDate(Utility.getCurrentTimestamp());
//		obj.setOperatorId(operatorId);
//	}


	public static int getMonthDiff(Date startDate, Date endDate) {
		Calendar myCal1 = Calendar.getInstance();
		Calendar myCal2 = Calendar.getInstance();

		myCal1.setTime(startDate);
		myCal2.setTime(endDate);
		int year1 = myCal1.get(Calendar.YEAR);
		int year2 = myCal2.get(Calendar.YEAR);
		int month1 = myCal1.get(Calendar.MONTH);
		int month2 = myCal2.get(Calendar.MONTH);

		return ((year2 - year1) * 12 - (month1 - month2));
	}
	/**
	 * model属性转为数据库字段
	 * @param field model属性
	 * @return 数据库字段
	 */
	public static String  getFieldString(String field) {
		StringBuffer sb = new StringBuffer();
		if(Utility.isNotEmpty(field)){
			for (int i = 0; i < field.length(); i++) {
				char c = field.charAt(i);
				String c1 = ""+c;
				if (Character.isUpperCase(c)) {
					c1 = c1.replace(c1, "_"+c1);
				}
				sb.append(c1);
			}
		}
		return sb.toString();
	}
	
	// 将汉字转换为全拼  
    public static String getPingYin(String src) {  
  
        char[] t1 = null;  
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length];  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
          
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断是否为汉字字符  
                if (java.lang.Character.toString(t1[i]).matches(  
                        "[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);  
                    t4 += t2[0];  
                } else  
                    t4 += java.lang.Character.toString(t1[i]);  
            }  
            // System.out.println(t4);  
            return t4;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return t4;  
    }  
  
    // 返回每个中文的首字母  
    public static String getPinYinHeadChar(String str) {  
  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
 // 返回第一个中文的首字母的大写
    public static String getPinYinHeadUpperChar(String str) {  
        String convert = "";  
        if (Utility.isNotEmpty(str)) {  
            char word = str.charAt(0);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) { //如果是中文 
                convert = String.valueOf(pinyinArray[0].charAt(0)).toUpperCase();  
            } else {  
                convert = String.valueOf(word).toUpperCase();  
            }  
        }  
        return convert;  
    }  
  
    // 将字符串转移为ASCII码  
    public static String getCnASCII(String cnStr) {  
        StringBuffer strBuf = new StringBuffer();  
        byte[] bGBK = cnStr.getBytes();  
        for (int i = 0; i < bGBK.length; i++) {  
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));  
        }  
        return strBuf.toString();  
    }  
  
    /**
     * 获取生成主键的uuid
     * @return
     */
    public static String getUUID(){
    	String uuid = UUID.randomUUID().toString();
    	if(uuid.indexOf("-") != -1){
    		uuid = uuid.replaceAll("-", "");
    	}
    	return uuid;
    }
    
	/**
	 * 删除html标签    	
	 * @param htmlStr
	 * @return
	 */
    public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
    
    /** 
     * 手机号验证 
     * @author ：shijing 
     * 2017年4月7日下午4:34:46 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(final String str) {  
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false; 
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();  
        return b;  
    } 
    /**
     * 将实体属性转为数据库字段名
     * @param fieldName
     * @return
     */
    public static String getTableColumnName(String fieldName){
    	StringBuffer columnName = new StringBuffer();
    	char chr;
    	for (int i=0; i < fieldName.length(); i ++) {
    		chr = fieldName.charAt(i);
    		if(Character.isUpperCase(chr)){//大写
    			columnName.append("_" + Character.toLowerCase(chr));
    		}else{
    			columnName.append(chr);//小写 
    		}
		}
    	return columnName.toString();
    }
    
    /**返回单引号被替换后的html转义符
     * @param s
     * @return
     */
    public static String getReplacedSingleQuotes(String s){
    	if(s!=null){
    		return s.replaceAll("'", "&#39;");
    	}
    	return null;
    }
    
    
    public static void main(String[] args) {  
        System.out.println(getPingYin("綦江qq县"));  
        System.out.println(getPinYinHeadUpperChar("a"));  
        System.out.println(getCnASCII("綦江县"));  
        
        System.out.println(getTableColumnName("isLikeStr"));
    }  
}
