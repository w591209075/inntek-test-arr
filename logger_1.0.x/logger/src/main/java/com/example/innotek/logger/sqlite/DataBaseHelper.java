package com.example.innotek.logger.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper
{
	// 数据库版本号
	private static final int DATABASE_VERSION = 1;

	DataBaseHelper(Context context)
	{
		super(context, getDb(), null, DATABASE_VERSION);
	}

	private static volatile DataBaseHelper instance = null;

	public synchronized static DataBaseHelper getInstance(Context context)
	{
		if (instance == null)
		{
			synchronized (DataBaseHelper.class)
			{
				if (instance == null)
				{
					instance = new DataBaseHelper(context);
				}
			}
		}
		return instance;
	}

	public synchronized static void destoryInstance()
	{
		if (instance != null)
		{
			instance.close();
			instance = null;
		}
	}

	/**日志记录表**/
	public static final String TABLE_LOG="TAB_LOG";
	
	/**
	 * 创建数据库并返回数据库地址
	 * @return
	 */
	public static String getDb()
	{
		// 默认数据库名称
		String dbName = "LOG.db";
		String sdPath = Method.getSdPath();
		if (sdPath != null)
		{
			Log.i("DATABASE", "sdPath=" + sdPath);
			String dbPath = sdPath + "/DB";
			String dbFilePath = dbPath + "/" + dbName;

			File fileDbPath = new File(dbPath);
			// File fileDbFilePath = new File(dbFilePath);

			if (!fileDbPath.exists())
			{
				fileDbPath.mkdirs();
			}
			dbName = dbFilePath;
			// return fileDbFilePath.toString();
		}

		Log.i("DATABASE", "dbName=" + dbName);
		return dbName;
	}

	private DataBaseHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer TAB_LOG = new StringBuffer();
		TAB_LOG.append("CREATE TABLE \"" + TABLE_LOG + "\" (").append("\r\n");
		TAB_LOG.append("\"_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,").append("\r\n");
		TAB_LOG.append("\"meidcode\"  VARCHAR(20) NOT NULL,").append("\r\n");
		TAB_LOG.append("\"call_class\"  VARCHAR(20), ").append("\r\n");
		TAB_LOG.append("\"platform\"  VARCHAR(20) NOT NULL, ").append("\r\n");
		TAB_LOG.append("\"version_no\"   VARCHAR(20) NOT NULL, ").append("\r\n");
		TAB_LOG.append("\"log_level\"  INT  NOT NULL,").append("\r\n");
		TAB_LOG.append("\"exception_type\" TEXT ,").append("\r\n");
		TAB_LOG.append("\"log_message\"  TEXT ,").append("\r\n");
		TAB_LOG.append("\"log_content\"  TEXT ,").append("\r\n");
		TAB_LOG.append("\"create_date\"  VARCHAR(20)  NOT NULL,").append("\r\n");
		TAB_LOG.append("\"md5\"  TEXT  NOT NULL,").append("\r\n");
		TAB_LOG.append("\"sync_times\"  int NOT NULL,").append("\r\n");
		TAB_LOG.append("\"sync_status\" VARCHAR(2) NOT NULL").append("\r\n");
		TAB_LOG.append(");");
		Log.i(Method.DEBUG_TAG, TABLE_LOG.toString());
		db.execSQL(TAB_LOG.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (oldVersion <= 2)
		{
			//添加SQL语句
		}
	}

	public synchronized long insert(String table, ContentValues values)
	{
		SQLiteDatabase db = getWritableDatabase();

		while (db.isDbLockedByCurrentThread())
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		//开启事务
		db.beginTransaction();
		try
		{
			long rowId = db.insert(table, null, values);
			if (rowId > 0)
			{
				db.setTransactionSuccessful();				
			}
			return rowId;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

	

	public synchronized boolean update(String table, ContentValues values, String whereClause, String[] whereArgs)
	{
		SQLiteDatabase db = getWritableDatabase();

		while (db.isDbLockedByCurrentThread())
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		//开启事务
		db.beginTransaction();
		try
		{
			int rowId = db.update(table, values, whereClause, whereArgs);
			if (rowId > 0)
			{
				db.setTransactionSuccessful();
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

	public synchronized boolean delete(String table, String whereClause, String[] whereArgs)
	{
		SQLiteDatabase db = getWritableDatabase();

		while (db.isDbLockedByCurrentThread())
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		//开启事务
		db.beginTransaction();
		try
		{
			int rowId = db.delete(table, whereClause, whereArgs);
			if (rowId > 0)
			{
				db.setTransactionSuccessful();
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

	/***
	 * 执行事务
	 *
	 * @param sqls
	 *            需要执行的语句集合
	 * @return 全部执行成功返回 true
	 */
	public synchronized boolean executeTrans(List<SqlPackage> sqlPkgs)
	{
		if (sqlPkgs == null)
		{
			throw new IllegalArgumentException("Argument sqlPkgs is null");
		}
		SQLiteDatabase db = getWritableDatabase();

		while (db.isDbLockedByCurrentThread())
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		db.beginTransaction();
		try
		{
			for (int i = 0; i < sqlPkgs.size(); i++)
			{
				if (SqlExecType.INSERT == sqlPkgs.get(i).execType)
				{
					long rowId = db.insert(sqlPkgs.get(i).table, sqlPkgs.get(i).nullColumnHack, sqlPkgs.get(i).values);
					if (rowId < 0)
					{
						throw new SQLException("insert error.\ntable:" + sqlPkgs.get(i).table + ",values:" + sqlPkgs.get(i).values.toString());
					}
				}

				if (SqlExecType.DELETE == sqlPkgs.get(i).execType)
				{
					int rowId = db.delete(sqlPkgs.get(i).table, sqlPkgs.get(i).whereClause, sqlPkgs.get(i).whereArgs);
					if (rowId < 0)// (rowId <= 0) 暂时不考虑数据不存在的情况
					{
						throw new SQLException("delete error.\ntable:" + sqlPkgs.get(i).table + ",where:" + sqlPkgs.get(i).whereClause + ",args:" + Arrays.toString(sqlPkgs.get(i).whereArgs));
					}
				}

				if (SqlExecType.UPDATE == sqlPkgs.get(i).execType)
				{
					int rowId = db.update(sqlPkgs.get(i).table, sqlPkgs.get(i).values, sqlPkgs.get(i).whereClause, sqlPkgs.get(i).whereArgs);
					if (rowId < 0)// (rowId <= 0) 暂时不考虑数据不存在的情况
					{
						throw new SQLException("update error.\ntable:" + sqlPkgs.get(i).table + ",values:" + sqlPkgs.get(i).values.toString());
					}
				}
			}
			db.setTransactionSuccessful();
			return true;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

	public synchronized boolean executeSQL(String sql, Object[] args) throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		while (db.isDbLockedByCurrentThread())
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		db.beginTransaction();
		try
		{
			if (args != null)
			{
				db.execSQL(sql, args);
			}
			else
			{
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
			return true;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

	public synchronized boolean executeTransSQL(List<SQL> sqls) throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		while (db.isDbLockedByCurrentThread())
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		db.beginTransaction();
		try
		{
			for (int i = 0; i < sqls.size(); i++)
			{
				if (sqls.get(i).args != null)
				{
					db.execSQL(sqls.get(i).sql, sqls.get(i).args);
				}
				else
				{
					db.execSQL(sqls.get(i).sql);
				}
			}

			db.setTransactionSuccessful();
			return true;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

	public Cursor queryCursor(String sql) throws Exception
	{
		return queryCursor(sql, null);
	}

	public Cursor queryCursor(String sql, String[] args) throws Exception
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(sql, args);
		return c;
	}

	class SQL
	{
		String sql;
		Object[] args;

		@Override
		public String toString()
		{
			return sql.toString() + "|" + Arrays.toString(args);
		}
	}
}
