package com.example.innotek.logger;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.sqlite.Method;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 设置记录logCat的记录方法
 * @author wuhao
 *2016-5-30
 */
public class Logger {
	
	private static Logger instance = null;
	public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();

        }
        return instance;
    }
	
	/**
	 * 是否记录Logcat的标识(默认关闭)
	 */
	private static boolean isLog = false;
	/**
	 * 是否记录LogFile的标识(默认关闭)
	 */
	private static boolean isLogFile=false;
	/**
	 * 在调用setislog的时候如已调用judgeislog方法则抛出异常
	 */
	private static boolean judgeislog=false;
	/**
	 * 在调用setshowislogfile时候如已调用judgeislogfile则抛出异常
	 */
	private static boolean judgeislogfile=false;
	/**
	 * 在调用setShowLogLevel时候如已调用judgeLogLevel则抛出异常
	 */
	private static boolean judgeshowloglevel=false;
	/**
	 * 在调用setIsLogfileloglevel时候如已调用judgeFlieLogLevel则抛出异常
	 */
	private static boolean judgefileloglevel=false;
	/**
	 * log文件中默认删除文件天数为1
	 */
	private static int fileDeleteDay = 1;
	/**单个文件删除大小（M）*/
	private static long fileDeleteSize = 2;
	/**文件夹删除大小（M）*/
	private static long directoryDelete = 10;
	
	private static String timePattern = LogParam.PatternYMDHMS;
	
	private static String logPath = "log";
	
	/**
	 * 设置isLog
	 * @param value
	 * @throws Exception 
	 */
	public static void setIsLog(boolean value) 
	{
		if(judgeislog)
		{
			throw new IllegalArgumentException("已调用judgeIsLog（）来判断IsLog，不能使用该方法设置IsLog！");
		}
		isLog=value;
	}
	/**
	 * 获取isLog
	 * @return
	 */
	public static boolean getIsLog()
	{
		return isLog;
	}
	public void setIsLogFile(boolean value)
	{
		if(judgeislogfile){
			throw new IllegalArgumentException("已调用judgeIsFileLog()来判断IsFileLog，不能使用该方法设置isLogFile！");
		}
		isLogFile=value;
	}
	public boolean getIsLogFile()
	{
		return isLogFile;
	}
	/**
	 * 设置记录哪些等级的日志
	 * @param value
	 */
	public static void setshowLogLevel(ArrayList<String> value)
	{
		LogParam.ShowLogLevel=value;
	}
	/**
	 * Logcat根据输入的值显示比该值严重的等级(包括输入的等级)
	 * @param LogLevel
	 */
	public static void setShowLogLevel(int ShowLogLevel) 
	{
		if(judgeshowloglevel)
		{
			throw new IllegalArgumentException("已调用judgeLogLevel()来判断IsFileLog，不能使用该方法设置isLogFile！");
		}
		try {
			ArrayList<String> list=new ArrayList<String>();
			for (int i = 0; i < ShowLogLevel+1; i++) {
				list.add(String.valueOf(i));
			}
			LogParam.ShowLogLevel=list;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("error", "设置ShowLogLevel出错");
		}	
	}
	/**
	 * 设置记录到txt文件的日志等级
	 * @param FileLogLevel
	 */
	public void setFileLogLevel(int FileLogLevel)
	{
		if(judgefileloglevel)
		{
			throw new IllegalArgumentException("已调用judgeFlieLogLevel()来判断IsFileLog，不能使用该方法设置isLogFile！");
		}
		try {
			ArrayList<String> list=new ArrayList<String>();
			for (int i = 0; i < FileLogLevel+1; i++) {
				list.add(String.valueOf(i));
			}
			LogParam.FileLogLevel=list;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("error", "设置FileLogLevel出错");
		}	
	}
	
	
	public  void setFileDeleteDay(int day)
	{
		fileDeleteDay = day; 
	}
	public  int getFileDeleteDay()
	{
		return fileDeleteDay;
	}
	
	public  void setFileDeleteSize(long size)
	{
		fileDeleteSize = size; 
	}
	public  long getFileDeleteSize()
	{
		return fileDeleteSize;
	}
	
	public  void setDirectoryDelete(long size)
	{
		directoryDelete = size; 
	}
	public  long getDirectoryDelete()
	{
		return directoryDelete;
	}		
	
	public  String getLogPath() {
		return logPath;
	}
	
	
	public static String getTimePattern() {
		return timePattern;
	}
	public static void setTimePattern(String timePattern) {
		Logger.timePattern = timePattern;
	}
	public void setLogPath(String logPath) {
		Logger.logPath = logPath;
	}
	public static void i(Class<?> cls, String msg) {
		i(cls.getSimpleName(), msg);
	}

	public static void e(Class<?> cls, String msg) {
		e(cls.getSimpleName(), msg);
	}
	public static void w(Class<?> cls, String msg) {
		w(cls.getSimpleName(), msg);
	}
	public static void d(Class<?> cls, String msg) {
		d(cls.getSimpleName(), msg);
	}

	public static void i(String tag, String msg) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.INFO))) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg,Throwable tr) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.INFO))) {
			Log.i(tag,msg,tr);
		}
	}
	public static void d(String tag, String msg) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.DEBUG))) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg,Throwable tr) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.DEBUG))) {
			Log.d(tag, msg,tr);
		}
	}
	public static void e(String tag, String msg) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.ERROR))) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg,Throwable tr) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.ERROR))) {
			Log.e(tag, msg,tr);
		}
	}

	public static void w(String tag, String msg) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.WARN))) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg,Throwable tr) {
		if (isLog && checkshowLogLevel(String.valueOf(LogParam.WARN))) {
			Log.w(tag, msg,tr);
		}
	}
	/**
	 * 检测是否显示日志,如能对sd卡进行操作则在logconfig的文件夹中读取是否有showlog的文件，有的设置为true
	 * 没有则设置为false
	 */
	public static void judgeIsLog() {
		String dirPath = Method.getPreferredDir("/logconfig");
		Log.i(Method.DEBUG_TAG, "dirPath: " + dirPath);
		if(dirPath==null)
		{
			return;
		}
		judgeislog=true;
		File dir = new File(dirPath);
		Log.i(Method.DEBUG_TAG, " file  is " + dir.exists());
		File[] files = dir.listFiles();
		for (File file : files) {
			String nameString = file.getName();
			if (nameString.startsWith("showlog")) {
				isLog = true;
				return;
			}
		}
	}
	/**
	 * 检测是否将日志记录到txt,如能对sd卡进行操作则在logconfig的文件夹中读取是否有filelog的文件，有的设置为true
	 * 没有则设置为false
	 */
	public static void judgeIsFileLog() {	
		String dirPath = Method.getPreferredDir("/logconfig");
		Log.i(Method.DEBUG_TAG, "dirPath: " + dirPath);
		if(dirPath==null)
		{
			return;
		}
		judgeislogfile=true;
		File dir = new File(dirPath);
		Log.i(Method.DEBUG_TAG, " file  is " + dir.exists());
		File[] files = dir.listFiles();
		for (File file : files) {
			String nameString = file.getName();
			if (nameString.startsWith("filelog")) {
				isLogFile=true;
				return;
			}
			if(nameString.startsWith("nofilelog"))
			{
				isLogFile=false;
				return;
			}
		}
	}
	/**
	 * 根据配置文件中的level文件设置显示日志的等级（level0，level1，level2，level3）
	 */
	public static void judgeLogLevel() {
		judgeshowloglevel=true;
		String dirPath = Method.getPreferredDir("/logconfig");
		Log.i(Method.DEBUG_TAG, "dirPath: " + dirPath);
		if(dirPath==null)
		{
			return;
		}
		File dir = new File(dirPath);
		Log.i(Method.DEBUG_TAG, " file  is " + dir.exists());
		File[] files = dir.listFiles();
		for (File file : files) {
			String nameString = file.getName();
			if (nameString.startsWith("level")) {
				try {
					String endString=nameString.substring(5,6);//读取level后面的值
					setShowLogLevel(Integer.valueOf(endString));
					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		}
	}
	
	/**
	 * 根据配置文件中的level文件设置显示日志的等级（filelevel0，filelevel1，filelevel2，filelevel3）
	 */
	public void judgeFlieLogLevel() {
		judgefileloglevel=true;
		String dirPath = Method.getPreferredDir("/logconfig");
		Log.i(Method.DEBUG_TAG, "dirPath: " + dirPath);
		if(dirPath==null)
		{
			return;
		}
		File dir = new File(dirPath);
		Log.i(Method.DEBUG_TAG, " file  is " + dir.exists());
		File[] files = dir.listFiles();
		for (File file : files) {
			String nameString = file.getName();
			if (nameString.startsWith("filelevel")) {
				try {
					String endString=nameString.substring(9,10);//读取level后面的值
					setFileLogLevel(Integer.valueOf(endString));
					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		}
	}

	/**
	 * 检查是否需要显示该等级的日志
	 * @param LogLevel
	 * @return
	 */
	private static boolean checkshowLogLevel(String ShowLogLevel) {
		boolean flag = false;
		for (int i = 0; i < LogParam.ShowLogLevel.size(); i++) {
			if (LogParam.ShowLogLevel.get(i).equals(ShowLogLevel)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * 检查该等级的日志是否需要记录到txt文件
	 * @param FileLogLevel
	 * @return
	 */
	private static boolean checkFileLogLevel(String FileLogLevel) {
		boolean flag = false;
		for (int i = 0; i < LogParam.FileLogLevel.size(); i++) {
			if (LogParam.FileLogLevel.get(i).equals(FileLogLevel)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 通过XML获取显示哪些日志等级)
	 * @param xmlFile
	 * @return
	 */
	private static String xmlShowLogLevel(File xmlFile) {
		ArrayList<String> showLogLevelList=new ArrayList<String>();
		String valueString = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
		      builder = factory .newDocumentBuilder();   
		      Document document = builder.parse(xmlFile);
		      NodeList list = document.getElementsByTagName("ShowLogLevel");
		      for (int i = 0, size = list.getLength(); i < size; i++)  
		        {  
		    	  Element element = (Element) list.item(i);	
		            // 后面类似处理即可：
		           String content = element.getElementsByTagName("ERROR").item(0)
		                    .getFirstChild().getNodeValue();
		            System.out.println("ERROR: " + content);
		            if(content.equals("true"))
		            {
		            	showLogLevelList.add(String.valueOf(LogParam.ERROR));
		            }
		            content = element.getElementsByTagName("WARN").item(0)
		                    .getFirstChild().getNodeValue();
		          //  System.out.println("WARN: " + content);
		            if(content.equals("true"))
		            {
		            	showLogLevelList.add(String.valueOf(LogParam.WARN));
		            }
		            content = element.getElementsByTagName("INFO").item(0)
		                    .getFirstChild().getNodeValue();
		            System.out.println("INFO: " + content);
		            if(content.equals("true"))
		            {
		            	showLogLevelList.add(String.valueOf(LogParam.INFO));
		            }
		            content = element.getElementsByTagName("DEBUG").item(0)
		                    .getFirstChild().getNodeValue();
		            System.out.println("DEBUG: " + content);
		            if(content.equals("true"))
		            {
		            	showLogLevelList.add(String.valueOf(LogParam.DEBUG));
		            }
		        }
		      LogParam.ShowLogLevel=showLogLevelList;		 
		  } catch (Exception e) {
		      System.out.println("exception:" + e.getMessage());
		  }
		return valueString;
	}
	/**
	 * 通过日志等级直接记录消息
	 * @param loglevel
	 * @param message
	 */
	public void logtoFlie(int loglevel,String message,Context context)
	{
		String valueString="";
		String showLogLevel="";
		switch (loglevel) {
		case LogParam.DEBUG:
			valueString=String.valueOf(loglevel);
			showLogLevel="debug";
			break;
		case LogParam.ERROR:
			valueString=String.valueOf(loglevel);
			showLogLevel="error";
			break;
		case LogParam.INFO:
			valueString=String.valueOf(loglevel);
			showLogLevel="information";
			break;
		case LogParam.WARN:
			valueString=String.valueOf(loglevel);
			showLogLevel="warn";
			break;
		default:
			break;
		}
		if(isLogFile&&checkFileLogLevel(valueString))
		{
			writefile(message,showLogLevel,context);
		}
	}
	/**
	 * 通过日志的等级直接记录异常
	 * @param loglevel
	 * @param e
	 */
	public void logtoFlie(int loglevel,Throwable tr,Context context)
	{
		String valueString="";
		String showLogLevel="";
		switch (loglevel) {
		case LogParam.DEBUG:
			valueString=String.valueOf(loglevel);
			showLogLevel="debug";
			break;
		case LogParam.ERROR:
			valueString=String.valueOf(loglevel);
			showLogLevel="error";
			break;
		case LogParam.INFO:
			valueString=String.valueOf(loglevel);
			showLogLevel="information";
			break;
		case LogParam.WARN:
			valueString=String.valueOf(loglevel);
			showLogLevel="warn";
			break;
		default:
			break;
		}
		if(isLogFile&&checkFileLogLevel(valueString))
		{
			writefile(getExceptionAllinformation(tr), showLogLevel,context);
		}
	}
	/**
	 * 将数据写入文件中
	 * @param message
	 * @param showLogLevel
	 */
	private void writefile(String message,String showLogLevel,Context context)
	{
		SimpleDateFormat formatFileName = new SimpleDateFormat(LogParam.PatternYMD);
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try
		{
			Date today = new Date();
			String path = Method.getPreferredDir("/"+logPath) + File.separator + formatFileName.format(today) + ".txt";
			Log.i(Method.DEBUG_TAG, "dirPath: " + path);
			if(Method.getPreferredDir("/"+logPath)==null)
			{
				return;
			}
			File file = new File(path);
			fos = new FileOutputStream(file, true);
			writer = new OutputStreamWriter(fos, "utf-8");
			writer.write("date:" + format.format(new Date()));
			writer.write("\r\n");
			writer.write("loglevel:" + showLogLevel);
			writer.write("\r\n");
			if(context!=null)
			{
				writer.write("version:" + getCurrentVersion(context));
				writer.write("\r\n");
			}

			writer.write("message:" + message);
			writer.write("\r\n");
			writer.write("\r\n");
		}
		catch (Exception e)
		{
			Logger.e(Method.DEBUG_TAG, Method.class.getSimpleName() + "log() ERROR");
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					Logger.e(Method.DEBUG_TAG, Method.class.getSimpleName() + "log() ERROR",e);
					e.printStackTrace();
				}
			}

			if (fos != null)
			{
				try
				{
					fos.close();
				}
				catch (IOException e)
				{
					Logger.e(Method.DEBUG_TAG, Method.class.getSimpleName() + "log() ERROR",e);
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 根据异常获取详细的异常信息
	 * @param e
	 * @return
	 */
	private static String getExceptionAllinformation(Throwable tr)
	{
		String sOut = "";
		if (tr != null)
		{
			sOut += tr.getMessage() + "\r\n";
			StackTraceElement[] trace = tr.getStackTrace();
			if (trace != null)
			{
				for (StackTraceElement s : trace)
				{
					sOut += "\tat " + s + "\r\n";
				}
			}
		}
		return sOut;
	}
	/**
	 * 获取当前系统的版本名称和版本号显示为V2.1.0（210）
	 * @param context
	 * @return
	 */
	private static String getCurrentVersion(Context context)
	{
		String message="";
		String versionname="";
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
			versionname=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			message="V"+versionname+"("+versionCode+")";
		} catch (NameNotFoundException e) {
			message="获取版本号以及版本名称失败！";
		}
		return message;
	}
	/**
     * 获取文件夹大小  
     * @param file
     * @return
     */
   public long getFolderSize(File file)
   {    
       long size = 0;    
       try {  
           File[] fileList = file.listFiles();
           for (int i = 0; i < fileList.length; i++)     
           {     
               if (fileList[i].isDirectory())     
               {     
                   size = size + getFolderSize(fileList[i]);    
               }else
               { 
            	   //文件大小
            	   long fileLength= fileList[i].length();
                   //单个文件大于相应大小删除或者大于相应时间删除 
                   if(fileLength> fileDeleteSize * 1048576|| fileLastModifie(fileList[i])>fileDeleteDay * 24)
                   {
                	   fileList[i].delete();
                   }
                   else 
                   {
                	   size = size + fileLength; 
                   }               
               }     
           }  
       } catch (Exception e) {  	
           e.printStackTrace();  
       }     
      //return size/1048576;    
       return size;    
   }    
   /**
    	* 获取上次修改的时间（返回单位为小时）
    * @param file
    * @return
    */
   private long fileLastModifie(File file)
   {
	   try
	   {
		   Date now = new Date();
		   Date fdtm = new Date(file.lastModified());
		   long elapsed =now.getTime()-fdtm.getTime();
		   return  (long) (elapsed * 1.0 / (1000 * 60 * 60));
	   } catch (Exception e) {
		   return 0;
	   }	  
   }
   /**
     * 删除日志
    * @param file
    * @return
    */
   public void deleteLogFile()
	{
		long filelength = 0;
		try {
			String path = Method.getPreferredDir("/"+logPath);
			Log.i(Method.DEBUG_TAG, "dirPath: " + path);
			if(Method.getPreferredDir("/"+logPath)==null)
			{
				return;
			}
			File dirFile = new File(path);
			if(dirFile.isDirectory()&&dirFile.exists())
			{
				filelength = getFolderSize(dirFile);
				//文件夹大于一定大小删除
				if(filelength>directoryDelete * 1048576)
				{
					for (File filedata : dirFile.listFiles()) {
							filedata.delete();
			        }
				}				
			}
			
		} catch (Exception e) {
			return;
		}
		
	}
}
