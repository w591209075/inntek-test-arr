package com.example.innotek.logger.sqlite;

import android.content.ContentValues;

import java.util.Arrays;

/**
 * SQL封装类<br />
 * <b>用于需要通过事务执行的一类SQL</b>
 *
 * @author sdw
 *
 */
public class SqlPackage
{
	/** 需要执行的类型，INSERT，UPDATE，DELETE */
	public SqlExecType execType = null;
	/** 表名 */
	public String table = null;
	/** 需要新增或修改的列 */
	public ContentValues values = null;
	/** 允许为空的列 */
	public String nullColumnHack = null;
	/** 条件语句 */
	public String whereClause = null;
	/** 条件语句参数 */
	public String[] whereArgs = null;

	public SqlPackage()
	{
	}

	/** 新增的构造函数 */
	public SqlPackage(String table, String nullClumnHack, ContentValues values)
	{
		this.table = table;
		this.values = values;
		this.nullColumnHack = nullClumnHack;
		this.execType = SqlExecType.INSERT;
	}

	/** 修改的构造函数 */
	public SqlPackage(String table, ContentValues values, String whereClause, String[] whereArgs)
	{
		this.table = table;
		this.values = values;
		this.whereClause = whereClause;
		this.whereArgs = whereArgs;
		this.execType = SqlExecType.UPDATE;
	}

	/** 删除的构造函数 */
	public SqlPackage(String table, String whereClause, String[] whereArgs)
	{
		this.table = table;
		this.whereClause = whereClause;
		this.whereArgs = whereArgs;
		this.execType = SqlExecType.DELETE;
	}

	@Override
	public String toString()
	{
		return "SqlPackage [execType=" + execType + ", table=" + table + ", values=" + values.toString() + ", nullColumnHack=" + nullColumnHack + ", whereClause=" + whereClause + ", whereArgs=" + Arrays.toString(whereArgs) + "]";
	}
	
}
