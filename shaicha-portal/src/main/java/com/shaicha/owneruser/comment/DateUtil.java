package com.shaicha.owneruser.comment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 * @author tmn
 *
 */
public class DateUtil {
	/**
	 * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		if(null == date)
			return "";

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String dateToString1(Date date){
		if(null == date)
			return "";
		
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String dateToString2(Date date){
		if(null == date)
			return "";
		
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}
	public static String dateToString3(Date date){
		if(null == date)
			return "";
		
		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}
	
	public static String simple(Date date) {
		if(null == date)
			return "";
		
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static String simple2(Date date) {
		if(null == date)
			return "";
		
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	public static Date strToDate(String dateString){
		if(null == dateString)
			return new Date();
		
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static Date strToYYMMDDDate(String dateString){
		if(null == dateString)
			return new Date();
		
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	//获取系统时间并返回时间格式
	public static Date currentDate(){
		DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			String currentDates = YYYY_MM_DD_MM_HH_SS.format(new Date());
			
			return YYYY_MM_DD_MM_HH_SS.parse(currentDates);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 得到当前时间距2013-11-01 00:00:00的小时数
	 * @return
	 * @throws ParseException
	 */
	public long getHours(){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simple.parse("2013-11-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long millisecond=System.currentTimeMillis()-date.getTime();
		long temp = 1000*60*60 ;
		return  millisecond/temp;
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate)    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			smdate=sdf.parse(sdf.format(smdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        try {
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }  
	
	/**
	 * 计算两个时间之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDays(Date startDate,Date endDate){
		long days = 0;
		long start = startDate.getTime();
		long end = endDate.getTime();
		//一天的毫秒数1000 * 60 * 60 * 24=86400000
		days = (end - start) / 86400000;
		return days;
	}
	
	/**
	 * 日期加上月数的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddMonth(Date date,int month){
		return add(date,Calendar.MONTH,month);
	}
	
	/**
	 * 日期加上天数的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddDay(Date date,int day){
		return add(date,Calendar.DAY_OF_YEAR,day);
	}
	/**
	 * 日期加上分钟的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddMINUTE(Date date,int minute){
		return add(date,Calendar.MINUTE,minute);
	}
	/**
	 * 获取当天最早一刻时间 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeOfDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);  
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
	}
	
	/**
	 * 获取当月最后一刻时间
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取当月最后一刻时间
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		Date time = new Date(calendar.getTimeInMillis()-1000);
		return time;
	}
	/**
	 * 获取当天最早时间即 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getFirstDate(Date date){
		
		return new Date();
	}
	/**
	 * 获取当天最后一刻时间即 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfDate(Date date){
		return getEndTimeOfDate(date,0);
	}
	/**
	 * 获取 最后一刻时间即 23:59:59
	 * @param date
	 * @param day 增加天数
	 * @return
	 */
	public static Date getEndTimeOfDate(Date date, int day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(day>0)
			calendar.add(Calendar.DAY_OF_MONTH, day);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(calendar.getTime());
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat2.parse(time+" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 日期加上年数的时间
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date,int year){
		return add(date,Calendar.YEAR,year);
	}
	
	 /** 
     * 计算剩余时间 (多少天多少时多少分)
     * @param startDateStr 
     * @param endDateStr 
     * @return 
     */  
    public static String remainDateToString(Date startDate, Date endDate){  
    	StringBuilder result = new StringBuilder();
    	if(endDate == null ){
    		return "过期";
    	}
    	long times = endDate.getTime() - startDate.getTime();
    	if(times < -1){
    		result.append("过期");
    	}else{
    		long temp = 1000 * 60 * 60 *24;
    		//天数
    		long d = times / temp;

    		//小时数
    		times %= temp;
    		temp  /= 24;
    		long m = times /temp;
    		//分钟数
    		times %= temp;
    		temp  /= 60;
    		long s = times /temp;
    		
    		result.append(d);
    		result.append("天");
    		result.append(m);
    		result.append("小时");
    		result.append(s);
    		result.append("分");
    	}
    	return result.toString();
    }  
    /** 
     * 计算剩余时间 (多少天多少时多少分多少秒)
     * @param startDateStr 
     * @param endDateStr 
     * @return 
     */  
    public static String remainDateToStringYYMMDDHHMMSS(Date startDate, Date endDate){  
    	StringBuilder result = new StringBuilder();
    	if(endDate == null ){
    		return "过期";
    	}
    	long times = endDate.getTime() - startDate.getTime();
    	if(times < -1){
    		result.append("过期");
    	}else{
    		long temp = 1000 * 60 * 60 *24;
    		//天数
    		long d = times / temp;
    		
    		//小时数
    		times %= temp;
    		temp  /= 24;
    		long m = times /temp;
    		//分钟数
    		times %= temp;
    		temp  /= 60;
    		long s = times /temp;
    		//秒数
    		times %= temp;
    		temp  /= 60;
    		long ss = times /temp;
    		
    		result.append(d);
    		result.append("天");
    		result.append(m);
    		result.append("小时");
    		result.append(s);
    		result.append("分");
    		result.append(ss);
    		result.append("秒");
    	}
    	return result.toString();
    }  
    
	private static Date add(Date date,int type,int value){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}
	
	
	/**   
	 * @MethodName: getLinkUrl  
	 * @Param: DateUtil  
	 * flag ： true 转换  false 不转换
	 * @Author: gang.lv
	 * @Date: 2013-5-8 下午02:52:44
	 * @Return:    
	 * @Descb: 
	 * @Throws:
	*/
	public static String getLinkUrl(boolean flag,String content,String id){
		if(flag){
			content = "<a href='finance.do?id="+id+"'>"+content+"</a>";
		}
		return content;
	}
	
	/**
	 * 时间转换为时间戳
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format,String date) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return  sf.parse(sf.format(date)).getTime();
	}
	/**
	 * 时间转换为时间戳
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format,Date date) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return  sf.parse(sf.format(date)).getTime();
	}
	
	/**
	 * 将时间戳转为字符串 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) { 
	 String re_StrTime = null; 
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss"); 
	 long lcc_time = Long.valueOf(cc_time); 
	 re_StrTime = sdf.format(new Date(lcc_time * 1000L)); 
	 return re_StrTime; 
	 } 
	
	public static int getAge(Date birthday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		Calendar nowDate = Calendar.getInstance();
		
		return nowDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
	}
	/**
	 * 当前时间减去分钟数得到的时间
	 * @param minutes
	 * @return
	 * @throws ParseException
	 */
	public static String getDateMinusMinutes(int minutes) throws ParseException{
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date nowTime = new Date(); 
    	String datetest=formatter.format(nowTime);
    	Date date=formatter.parse(datetest);
    	long Time1=(date.getTime()/1000)-60*minutes;//减去30分
    	date.setTime(Time1*1000);
    	return formatter.format(date);
	}
	
	/**
	 * 和当前时间比较是否在给定的时长内
	 * @param validTime 给定的时间
	 * @param time 给定的时长（s）
	 * @return true 有效 false 无效
	 */
	public static boolean inValidTime(Date validTime, int time){
		long currTime = System.currentTimeMillis();
		long valid = validTime.getTime();
		
		return ((currTime - valid) < time*1000);
	}
	
	/**
	 * 获取年
	 * @param time
	 * @return
	 */
	public static int getYear(Date time) {
		if (time == null) {
			return -1;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取月
	 * @param time
	 * @return
	 */
	public static int getMonth(Date time) {
		if (time == null) {
			return -1;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取日
	 * @param time
	 * @return
	 */
	public static int getDay(Date time) {
		if (time == null) {
			return -1;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取年
	 * @return
	 */
	public static int getYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取月
	 * @return
	 */
	public static int getMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取日
	 * @return
	 */
	public static int getDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 将yyyy-MM-dd拼接成yyyy-MM-dd :HH:mm:ss
	 * @param startDateStr
	 * @return
	 */
	public static Date strDateToStartDate(String startDateStr){
		
		return DateUtil.strToDate(startDateStr + " 00:00:00");
	}
	
	/**
	 * 将yyyy-MM-dd拼接成yyyy-MM-dd :HH:mm:ss
	 * @param startDateStr
	 * @return
	 */
	public static Date strDateToEndDate(String endDateStr){
		
		return DateUtil.strToDate(endDateStr + " 23:59:59");
	}
	
	   /**
     * 当月第一天
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }
    
    /**
	 * 时间比大小
	 * 
	 * @param DATE1
	 *            大的时间
	 * @param DATE2
	 *            小的时间
	 * @return 1大于，-1小于，0未知错误
	 */
	public static int compare_date(Date DATE1, Date DATE2) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String date1 = df.format(DATE1);
		String date2 = df.format(DATE2);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {

				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {

				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 判断一个时间是否在两个时间之内
	 * 
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return true 在   false不在
	 * @throws ParseException
	 */
	public static Boolean checkStartAndEndDate(String startTime,String endTime) {

		Date date = new Date();

		Date startDate = strToDate(startTime);
		Date endDate = strToDate(endTime);

		if (DoBetweenStartAndEndDate(date, startDate, endDate)) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 判断一个时间是否在两个时间之内
	 * 
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Boolean DoBetweenStartAndEndDate(Date date, Date startDate,
			Date endDate) {

		Boolean start = false;
		Boolean end = false;
		if (compare_date(date, startDate) == 1) {
			start = true;
		}
		if (compare_date(endDate, date) == 1) {
			end = true;
		}
		if (start && end) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
