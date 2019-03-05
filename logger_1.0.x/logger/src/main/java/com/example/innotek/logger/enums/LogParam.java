package com.example.innotek.logger.enums;

import java.util.ArrayList;


/**
 * @author wuhao
 *2016-5-16
 */
public class LogParam {
	/**错误异常*/
	public static final int ERROR=0;
	/**警告异常*/
	public static final int WARN=1;
	/**正常信息*/
	public static final int INFO=2;
	/**正常调试*/
	public static final int DEBUG=3;
	/**使用Logcat时显示全部*/
	public static ArrayList<String> ShowLogLevel=new ArrayList<String>()
	{
		{
			add("0");
			add("1");
			add("2");
			add("3");		
		}
	};
		/**使用LogFlie记录日志*/
	public static ArrayList<String> FileLogLevel=new ArrayList<String>()
	{
		{
			add("0");
			add("1");
			add("2");
			add("3");		
		}
	};
	/**已同步*/
	public static final String Up_Yes="1";
	/**未同步*/
	public static final String Up_No="0";
	/**设备编号*/
	public static String MeidCode="BWBSB250";
	/**平台*/
	public static String PlatForm="1";
	/**版本号*/
	public static String versionNo="1";
	
	/**测试IP*/
	public static String ServiceIP="http://192.168.10.19:8080/WebSite/api/log/upload";
	/**默认删除一天前的数据*/
	public static int DeleteDay=-1;
	
	 public static final String PatternYMD = "yyyy-MM-dd";
	 public static final String PatternHM = "HH:mm";
	 public static final String PatternHMS = "HH:mm:ss";
	 public static final String PatternYMDHM = PatternYMD + " " + PatternHM;
	 public static final String PatternYMDHMS = PatternYMD + " " + PatternHMS;
	 public static final String PatternYMDHMSS = PatternYMD + " " + PatternHMS + ".SSS";
	
}
