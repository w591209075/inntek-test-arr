package com.example.innotek.logger.sqlite;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;

/***
 * 日志部分方法
 * @author wuhao
 *2016-5-17
 */
public class Method extends Application {
	public static final String DEBUG_TAG = "LOG";
	private static Method sInstance = null;
	public Method()
	{
		super();
		sInstance = this;
	}
	/***
	 * 加密MD5
	 * @param str
	 * @return
	 */
    public static String MD5(String str) { 
        MessageDigest md5 = null; 
        try { 
            md5 = MessageDigest.getInstance("MD5"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
            return ""; 
        } 
        char[] charArray = str.toCharArray(); 
        byte[] byteArray = new byte[charArray.length]; 
        for (int i = 0; i < charArray.length; i++) { 
            byteArray[i] = (byte) charArray[i]; 
        } 
        byte[] md5Bytes = md5.digest(byteArray); 
        StringBuffer hexValue = new StringBuffer(); 
        for (int i = 0; i < md5Bytes.length; i++) { 
            int val = ((int) md5Bytes[i]) & 0xff; 
            if (val < 16) { 
                hexValue.append("0"); 
            } 
            hexValue.append(Integer.toHexString(val)); 
        } 
        return hexValue.toString(); 
    }   
	/**
	 * 获取异常的堆栈信息
	 * @param ex
	 * @return
	 */
	public static String getExceptionAllinformation(Exception ex) {
		// TODO Auto-generated method stub
		String sOut = "";
		if (ex != null)
		{
		//	sOut += e.getMessage() + "\r\n";
			StackTraceElement[] trace = ex.getStackTrace();
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
	public static String getSdPath()
	{
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();// 获取根目录
		}
		else
		{
			return null;
		}
		return sdDir.toString();

	}
	public static String getPreferredDir(String dirName)
	{
		try {
			if(dirName==null)
			{
				dirName="";
			}
			StringBuilder dirPath = new StringBuilder();
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				String a=Environment.getExternalStorageDirectory().toString();
				dirPath.append(Environment.getExternalStorageDirectory());			
			}
			else
			{
				return null;
			}
			dirPath.append("/").append(dirName);
			File dir = new File(dirPath.toString());
			Log.i(Method.DEBUG_TAG + "x", "dir=" + dir.toString());
			if (dir.exists() && !dir.isDirectory())
			{
				return null;
			}
			else
			{
				if (dir.exists())
				{
					return dir.getAbsolutePath();
				}
				else if (dir.mkdirs())
				{
					return dir.getAbsolutePath();
				}
				else
				{
					return null;
				}
			}
		} catch (Exception e) {
			return null;
		}
		
	}
}
