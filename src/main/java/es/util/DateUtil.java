package es.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 1.获取当前时间的Date，Calendar，Timestamp
 * 2.按默认格式
 * 3.获取当前时间的字符串，201611,20161101,20161114052923007
 * 4.当前时间按格式转字符串
 * 5.获取当前月的第一天
 * 6.获取当前月的最后一天
 * 7.当前时间加减，天，周，月，年
 * 8.计算两个时间差的天数
 * */


public class DateUtil {
	
	private static final String DATE_PATTERN_10 = "yyyy-MM-dd";
	private static final String DATE_PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
	private static final long MINUTE_OF_MSEL = 60 * 1000;
	private static final long HOUR_OF_MSEL = 60 * MINUTE_OF_MSEL;
	private static final long DAY_OF_MSEL = 24 * HOUR_OF_MSEL;
	
	
	
	/**----------------初始化当前时间的Date，Calendar，Timestamp-----------------**/
	/**
	 * 当前时间DATE
	 * */
	public static Date getCurrDate(){
		return new Date();
	}
	/**
	 * 当前时间Timestamp
	 * */
	public static Date getCurrTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	/**
	 * 当前时间Calendar
	 * */
	public static Calendar getCurrCalendar(){
		return Calendar.getInstance();
	}
	
	/**----------------字符串转日期，日历，时间戳-----------------**/
	
	/**
	 * @描述 默认转yyyy-MM-dd hh:mm:ss格式
	 * @author 炎
	 * **/
	public static Date str2Date(String str){
		try {
			SimpleDateFormat formater = null;
			if(str.length()==19){
				formater = new SimpleDateFormat(DATE_PATTERN_19);
			}else if(str.length()==10){
				formater = new SimpleDateFormat(DATE_PATTERN_10);
			}
			return formater==null?null:formater.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @描述 默认转自定义格式
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @createTime 20161114
	 * */
	public static Date str2Date(String str,String pattern){
		try {
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			return formater.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @描述  将字符串转为timestamp
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @createTime 20161114
	 * */
	public static Timestamp str2Timestamp(String str,String pattern){
		return new Timestamp(str2Date(str,pattern).getTime());
	}
	
	/**
	 * @描述  将字符串转为Calendar
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @createTime 20161114
	 * */
	public static Calendar str2Calendar(String str,String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(str2Date(str,pattern));
		return calendar;
	}
	
	/**
	 * @描述  将Date转为字符串(可以为timestamp类型)
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @createTime 20161114
	 * */
	public static String date2Str(Date date,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * @描述  将calendar转为字符串
	 * @author 炎
	 * @param calendar 要转换的日历类
	 * @param pattern 要转换的字符串格式
	 * @createTime 20161114
	 * */
	public static String calendar2Str(Calendar calendar,String pattern){
		return date2Str(calendar.getTime(), pattern);
	}
	
	/**
	 * @描述  当前月份转为字符串，格式yyyyMM
	 * @author 炎
	 * @createTime 20161114
	 * */
	public static String currMonth2Str(){
		return date2Str(getCurrTimestamp(),"yyyyMM");
	}
	
	/**
	 * @描述  当前日期转为字符串，格式yyyyMMdd
	 * @author 炎
	 * @createTime 20161114
	 * */
	public static String currDay2Str(){
		return date2Str(getCurrTimestamp(),"yyyyMMdd");
	}
	
	/**
	 * @描述  当前timestamp转为字符串，格式yyyyMMddhhmmssSSS
	 * @author 炎
	 * @createTime 20161114
	 * */
	public static String currTimestamp2Str(){
		return date2Str(getCurrTimestamp(),"yyyyMMddHHmmssSSS");
	}
	
	/**
	 * @描述  当前时间转为字符串，格式yyyyMMddhhmmssSSS
	 * @author 炎
	 * @createTime 20161114
	 * */
	public static String currTime2Str(){
		return date2Str(getCurrTimestamp(),"HHmmss");
	}
	
	/**
	 * @描述  当前月第一天
	 * @author 炎
	 * @createTime 20161114
	 * */
	public static Date currMonthFirstDay(){
		Calendar calendar = getCurrCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * @描述  上月第一天
	 * @author 炎
	 * @createTime 20161114
	 * */
	public static Date preMonthFirstDay(){
		Calendar calendar = getCurrCalendar();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * @描述  某月第一天，格式yyyyMMdd
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @createTime 20161114
	 * */
	public static Date monthFirstDay(String str,String pattern){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * @描述  时间加减天
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @param interval 加减周数
	 * @createTime 20161115
	 * */
	public static Date addDay(String str, String pattern, int interval){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.add(Calendar.DAY_OF_YEAR, interval);
		return calendar.getTime();
	}
	/**
	 * @描述  当前时间加减周
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @param interval 加减周数
	 * @createTime 20161115
	 * */
	public static Date addWeek(String str, String pattern,int interval){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.add(Calendar.WEEK_OF_YEAR, interval);
		return calendar.getTime();
	}
	/**
	 * @描述  当前时间加减月
	 * @author 炎
	 * @param str 要转换的字符串
	 * @param pattern 要转换的字符串格式
	 * @param interval 加减周数
	 * @createTime 20161115
	 * */
	public static Date addMonth(String str, String pattern,int interval){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.add(Calendar.MONTH, interval);
		return calendar.getTime();
	}
	
	/**
	 * @描述  计算两个时间差的天数beginDate-endDate
	 * @author 炎
	 * @param beginDate 被减日期
	 * @param endDate 减日期
	 * @createTime 20161115
	 * */
	public static int diffDay(Date beginDate,Date endDate){
		return (int) Math.ceil((double)(endDate.getTime()-beginDate.getTime())/DAY_OF_MSEL);
	}
	
	public static void main(String[] args) {
		System.out.println(diffDay(str2Date("20161115220001","yyyyMMddHHmmss"),str2Date("20161115","yyyyMMdd")));
	}
	
}
