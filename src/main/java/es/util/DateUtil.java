package es.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 1.��ȡ��ǰʱ���Date��Calendar��Timestamp
 * 2.��Ĭ�ϸ�ʽ
 * 3.��ȡ��ǰʱ����ַ�����201611,20161101,20161114052923007
 * 4.��ǰʱ�䰴��ʽת�ַ���
 * 5.��ȡ��ǰ�µĵ�һ��
 * 6.��ȡ��ǰ�µ����һ��
 * 7.��ǰʱ��Ӽ����죬�ܣ��£���
 * 8.��������ʱ��������
 * */


public class DateUtil {
	
	private static final String DATE_PATTERN_10 = "yyyy-MM-dd";
	private static final String DATE_PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
	private static final long MINUTE_OF_MSEL = 60 * 1000;
	private static final long HOUR_OF_MSEL = 60 * MINUTE_OF_MSEL;
	private static final long DAY_OF_MSEL = 24 * HOUR_OF_MSEL;
	
	
	
	/**----------------��ʼ����ǰʱ���Date��Calendar��Timestamp-----------------**/
	/**
	 * ��ǰʱ��DATE
	 * */
	public static Date getCurrDate(){
		return new Date();
	}
	/**
	 * ��ǰʱ��Timestamp
	 * */
	public static Date getCurrTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	/**
	 * ��ǰʱ��Calendar
	 * */
	public static Calendar getCurrCalendar(){
		return Calendar.getInstance();
	}
	
	/**----------------�ַ���ת���ڣ�������ʱ���-----------------**/
	
	/**
	 * @���� Ĭ��תyyyy-MM-dd hh:mm:ss��ʽ
	 * @author ��
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
	 * @���� Ĭ��ת�Զ����ʽ
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
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
	 * @����  ���ַ���תΪtimestamp
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @createTime 20161114
	 * */
	public static Timestamp str2Timestamp(String str,String pattern){
		return new Timestamp(str2Date(str,pattern).getTime());
	}
	
	/**
	 * @����  ���ַ���תΪCalendar
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @createTime 20161114
	 * */
	public static Calendar str2Calendar(String str,String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(str2Date(str,pattern));
		return calendar;
	}
	
	/**
	 * @����  ��DateתΪ�ַ���(����Ϊtimestamp����)
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @createTime 20161114
	 * */
	public static String date2Str(Date date,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * @����  ��calendarתΪ�ַ���
	 * @author ��
	 * @param calendar Ҫת����������
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @createTime 20161114
	 * */
	public static String calendar2Str(Calendar calendar,String pattern){
		return date2Str(calendar.getTime(), pattern);
	}
	
	/**
	 * @����  ��ǰ�·�תΪ�ַ�������ʽyyyyMM
	 * @author ��
	 * @createTime 20161114
	 * */
	public static String currMonth2Str(){
		return date2Str(getCurrTimestamp(),"yyyyMM");
	}
	
	/**
	 * @����  ��ǰ����תΪ�ַ�������ʽyyyyMMdd
	 * @author ��
	 * @createTime 20161114
	 * */
	public static String currDay2Str(){
		return date2Str(getCurrTimestamp(),"yyyyMMdd");
	}
	
	/**
	 * @����  ��ǰtimestampתΪ�ַ�������ʽyyyyMMddhhmmssSSS
	 * @author ��
	 * @createTime 20161114
	 * */
	public static String currTimestamp2Str(){
		return date2Str(getCurrTimestamp(),"yyyyMMddHHmmssSSS");
	}
	
	/**
	 * @����  ��ǰʱ��תΪ�ַ�������ʽyyyyMMddhhmmssSSS
	 * @author ��
	 * @createTime 20161114
	 * */
	public static String currTime2Str(){
		return date2Str(getCurrTimestamp(),"HHmmss");
	}
	
	/**
	 * @����  ��ǰ�µ�һ��
	 * @author ��
	 * @createTime 20161114
	 * */
	public static Date currMonthFirstDay(){
		Calendar calendar = getCurrCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * @����  ���µ�һ��
	 * @author ��
	 * @createTime 20161114
	 * */
	public static Date preMonthFirstDay(){
		Calendar calendar = getCurrCalendar();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * @����  ĳ�µ�һ�죬��ʽyyyyMMdd
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @createTime 20161114
	 * */
	public static Date monthFirstDay(String str,String pattern){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * @����  ʱ��Ӽ���
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @param interval �Ӽ�����
	 * @createTime 20161115
	 * */
	public static Date addDay(String str, String pattern, int interval){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.add(Calendar.DAY_OF_YEAR, interval);
		return calendar.getTime();
	}
	/**
	 * @����  ��ǰʱ��Ӽ���
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @param interval �Ӽ�����
	 * @createTime 20161115
	 * */
	public static Date addWeek(String str, String pattern,int interval){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.add(Calendar.WEEK_OF_YEAR, interval);
		return calendar.getTime();
	}
	/**
	 * @����  ��ǰʱ��Ӽ���
	 * @author ��
	 * @param str Ҫת�����ַ���
	 * @param pattern Ҫת�����ַ�����ʽ
	 * @param interval �Ӽ�����
	 * @createTime 20161115
	 * */
	public static Date addMonth(String str, String pattern,int interval){
		Calendar calendar = str2Calendar(str, pattern);
		calendar.add(Calendar.MONTH, interval);
		return calendar.getTime();
	}
	
	/**
	 * @����  ��������ʱ��������beginDate-endDate
	 * @author ��
	 * @param beginDate ��������
	 * @param endDate ������
	 * @createTime 20161115
	 * */
	public static int diffDay(Date beginDate,Date endDate){
		return (int) Math.ceil((double)(endDate.getTime()-beginDate.getTime())/DAY_OF_MSEL);
	}
	
	public static void main(String[] args) {
		System.out.println(diffDay(str2Date("20161115220001","yyyyMMddHHmmss"),str2Date("20161115","yyyyMMdd")));
	}
	
}
