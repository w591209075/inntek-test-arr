package com.example.innotek.logger.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.protocol.data.LogModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author wuhao
 *2016-5-13
 */
public class SqliteDao extends DbManager {

	/**标识是否记录日志*/
	private static boolean isLogDb = true;
	public SqliteDao(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		try {
			/**查询是否存在该数据库表**/
			helper.queryCursor("select count(1) from sqlite_master  where name = "+"'"+DataBaseHelper.TABLE_LOG+"'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设置是否记录日志
	 * @param value
	 */
	public void setIsLogDb(boolean value) 
	{
		isLogDb=value;
	}
	/**
	 * 添加数据,其中MD5在该函数计算
	 * @param item：LogModel类
	 * @return 是否成功
	 */
	public boolean Add_Data(LogModel item)throws Exception
	{
		try 
		{
			ContentValues values = new ContentValues();
			values.put("meidcode", item.getmeidCode());
			values.put("call_class", item.getcallClass());
			values.put("platform", item.getplatform());
			values.put("version_no", item.getversionNo());
			values.put("log_level", item.getlogLevel());
			values.put("exception_type", item.getexceptionType());
			values.put("log_message", item.getlogMessage());
			values.put("create_date",item.getcreateDate() );
			String MD5=Method.MD5(item.getlogContent());
			values.put("md5", MD5);
			if(!CheckMD5(MD5))   //当该数据存数据库时其内容无需添加
			{
				values.put("log_content", item.getlogContent());
			}
			else
			{
				values.put("log_content", "");
			}
			values.put("sync_times", item.getsyncTimes());
			values.put("sync_status", item.getsyncStatus());
			long row = helper.insert(DataBaseHelper.TABLE_LOG, values);
			if (row > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	/**
	 * 检查是否存在该MD5值
	 * @param MD5
	 * @return 是否成功
	 * @throws Exception 
	 */
	public boolean CheckMD5(String MD5) throws Exception
	{
		Cursor cursor=null;
		try
		{
			int num=0;
			 String sql="select * from "+DataBaseHelper.TABLE_LOG+" where md5 ='"+MD5+"'";
			 cursor=helper.queryCursor(sql);
			 num=cursor.getCount();
			if(num>0)
			{
				return true;
			}
			else 
			{
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
	}

	/**
	 * 根据条件查询数据
	 * @param where 条件语句
	 * @param whereValue 条件内容
	 * @return LogModel的list
	 * @throws Exception
	 */
	public List<LogModel> QueryList(String where,String[] whereValue) throws Exception
	{
		Cursor cursor = null;
		LogModel item=null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM " + DataBaseHelper.TABLE_LOG + " WHERE 1==1");
		List<LogModel> list = new ArrayList<LogModel>();
		if (where != null) {
			sql.append(" AND " + where +" order by _id ASC");
		}
		try {
			cursor = helper.queryCursor(sql.toString(), whereValue);
			while (cursor.moveToNext()) {
				item = ConverToObj(cursor);
				list.add(item);
			}
			cursor.close();
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	/**
	 * 查询需要上传的数据
	 * @return
	 * @throws Exception
	 */
	public List<LogModel> queryUpdata() throws Exception
	{
		List<LogModel> list = new ArrayList<LogModel>();
		try {
		    String where="sync_status = ?";
		    String []whereValue={LogParam.Up_No};  		
			list=QueryList(where, whereValue);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list	;
	}	
	
	/**
	 * 根据条件修改
	 * @param item
	 * @return
	 */
	public boolean update(LogModel item) {
		ContentValues values = new ContentValues();
		values.put("_id", item.get_id());
		values.put("meidcode", item.getmeidCode());
		values.put("call_class", item.getcallClass());
		values.put("platform", item.getplatform());
		values.put("version_no", item.getversionNo());
		values.put("log_level", item.getlogLevel());
		values.put("exception_type", item.getexceptionType());
		values.put("log_message", item.getlogMessage());
		values.put("create_date",item.getcreateDate());
		values.put("md5", item.getmd5());
		values.put("log_content", item.getlogContent());
		values.put("sync_times", item.getsyncTimes());
		values.put("sync_status", item.getsyncStatus());
		return  helper.update(DataBaseHelper.TABLE_LOG, values, "_id=?", new String[] { String.valueOf(item.get_id()) });
	}
	/**
	 * 设置同步次数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean setSyncTimes(long id) throws Exception
	{
		String sql = "UPDATE " + DataBaseHelper.TABLE_LOG + " SET sync_times=sync_times+1 where _id=" + id;
		return helper.executeSQL(sql, null);
	}
	/**
	 * 将Cursor转化为LogModel
	 * @param cursor
	 * @return
	 */
	public LogModel ConverToObj(Cursor cursor) 
	{
		LogModel item=new LogModel();
		item.set_id(cursor.getLong(cursor.getColumnIndex("_id")));
		item.setmeidCode(cursor.getString(cursor.getColumnIndex("meidcode")));
		item.setcallClass(cursor.getString(cursor.getColumnIndex("call_class")));
		item.setplatform(cursor.getString(cursor.getColumnIndex("platform")));
		item.setversionNo(cursor.getString(cursor.getColumnIndex("version_no")));
		item.setlogLevel(cursor.getInt(cursor.getColumnIndex("log_level")));
		item.setexceptionType(cursor.getString(cursor.getColumnIndex("exception_type")));
		item.setlogMessage(cursor.getString(cursor.getColumnIndex("log_message")));
		item.setlogContent(cursor.getString(cursor.getColumnIndex("log_content")));
		item.setcreateDate(cursor.getString(cursor.getColumnIndex("create_date")));
		item.setmd5(cursor.getString(cursor.getColumnIndex("md5")));
		item.setsyncTimes(cursor.getLong(cursor.getColumnIndex("sync_times")));
		item.setsyncStatus(cursor.getString(cursor.getColumnIndex("sync_status")));	
		return item;
	}
	/**
	 * 删除几天前已上传的数据
	 * @param days 天数 负值为几天前正为几天后
	 * @return
	 * @throws Exception
	 */
	public Boolean clearDatabydays(int days) throws Exception {
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE,days);
	    String deleteday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime()); 
	    String where="sync_status = ? and create_date < ?";
		String [] values={LogParam.Up_Yes,deleteday};
		return helper.delete(DataBaseHelper.TABLE_LOG,where,values);
	}
	/**
	 * 重复数据上传完之后即删除（根据ID删除）
	 * @param id
	 * @return
	 */
	public  boolean  clearDataByLogcontent(long id) 
	{
		String where="_id = ? ";
		String[] values	={String.valueOf(id)};
		return helper.delete(DataBaseHelper.TABLE_LOG, where, values);
	}


	/**
	 * 录入错误日志数据
	 * @param callClass 调用类
	 * @param ex        异常
	 * @return
	 */
	public boolean e(String callClass,Exception ex)
	{
		
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(callClass,LogParam.ERROR,ex);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入错误日志数据
	 * @param callClass         调用类
	 * @param exceptionType     日志类型
	 * @param logMessage        日志描述
	 * @param logContent    	日志内容
	 * @return
	 */
	public boolean e(String callClass,String exceptionType,	String logMessage,String logContent)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(LogParam.ERROR, callClass, exceptionType, logMessage, logContent);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入调试日志
	 * @param callClass 	调用类
	 * @param ex			异常
	 * @return
	 */
	public boolean d(String callClass,Exception ex)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(callClass,LogParam.DEBUG,ex);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入调试日志
	 * @param callClass			调用类
	 * @param exceptionType		日志类型
	 * @param logMessage		日志描述
	 * @param logContent		日志内容
	 * @return
	 */
	public boolean d(String callClass,String exceptionType,	String logMessage,String logContent)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(LogParam.DEBUG, callClass, exceptionType, logMessage, logContent);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入警告日志
	 * @param callClass		调用类
	 * @param ex			异常
	 * @return
	 */
	public boolean w(String callClass,Exception ex)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(callClass,LogParam.WARN,ex);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入警告日志
	 * @param callClass		调用类
	 * @param exceptionType	日志类型
	 * @param logMessage	日志描述	
	 * @param logContent	日志内容
	 * @return
	 */
	public boolean w(String callClass,String exceptionType,	String logMessage,String logContent)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(LogParam.WARN, callClass, exceptionType, logMessage, logContent);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入信息日志
	 * @param callClass	调用类
	 * @param ex		异常
	 * @return
	 */
	public boolean i(String callClass,Exception ex)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(callClass,LogParam.INFO,ex);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
	/**
	 * 录入信息日志
	 * @param callClass		调用类
	 * @param exceptionType 日志类型
	 * @param logMessage	日志描述
	 * @param logContent	日志内容
	 * @return
	 */
	public boolean i(String callClass,String exceptionType,	String logMessage,String logContent)
	{
		if(!isLogDb)
		{
			Log.i("SqliteDao","isLogDb is false");
			return false;
		}
		LogModel item=null;
			// TODO: handle exception
		try {
			item=new LogModel(LogParam.INFO, callClass, exceptionType, logMessage, logContent);
			return Add_Data(item);			
		} catch (Exception e2) {
				// TODO: handle exception
			return false;
		}
	}
}
