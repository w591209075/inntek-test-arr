package com.example.innotek.logger;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.service.DataService;
import com.example.innotek.logger.sqlite.DataBaseHelper;
import com.example.innotek.logger.sqlite.SqliteDao;


/**
 * 单例模式管理数据库和service操作
 * 
 * @author wuhao 2016-5-26
 */
public class LoggerManager {
	
	private static Context context;
	private static LoggerManager loggerManager;
	private static SqliteDao sqliteDao;
	private static DataService dataService;
	public static DataBaseHelper dataBaseHelper;
	private LoggerManager() {
		super();
	}

	/**
	 * 初始化函数
	 * @param context
	 */
	public static void init(Context context) {
		synchronized (LoggerManager.class) {
		if (loggerManager == null) {
			loggerManager = new LoggerManager();
		}
		}
		if (context == null) {
			throw new NullPointerException("context为空");
		} else {
			loggerManager.context = context;
		
		// dataBaseHelper=DataBaseHelper.getInstance(context);
		sqliteDao = new SqliteDao(context);
		LogParam.versionNo=String.valueOf(getCurrentVersionCode(context));
		}
	}

	public static  LoggerManager getInstance() {
		if(loggerManager==null)
		{
			throw new NullPointerException("loggerManager为空，调用该方法时需先调用init");
		}
		if(loggerManager.context==null)
		{
			throw new NullPointerException("loggerManager.context为空，调用该方法时需先调用init");
		}
		return loggerManager;
	}

	/**
	 * 数据库操作
	 * @return
	 */
	public SqliteDao sqlDao() {	
		return sqliteDao;
	}

	/**
	 * dataservice操作
	 * @return
	 */
	public DataService dataServiceDao() {
		if (dataService == null) {
			dataService = new DataService();
		}
		return dataService;
	}


	/**
	 * 获取当前系统的版本号
	 * @param context
	 * @return
	 */
	 private static int getCurrentVersionCode(Context context) {

		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {

		}
		return versionCode;
	}

	/**
	 * 设置平台信息
	 * @param value
	 */
	public static void setPlatForm(String value) {
		LogParam.PlatForm = value;
	}

	/**
	 * 设置设备编号
	 * @param value
	 */
	public static void setMeidCode(String value) {
		LogParam.MeidCode = value;
	}

	/**
	 * 设置上传的IP地址
	 * @param value
	 */
	public static void setServiceIP(String value) {
		LogParam.ServiceIP = value;
	}

	/**
	 * 设置删除天数
	 * @param value
	 *            删除天数负数为几天前
	 */
	public static void setDeleteDay(int value) {
		LogParam.DeleteDay = value;
	}
	
}
