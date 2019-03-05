package com.example.innotek.logger.sqlite;
import android.content.Context;
import android.util.Log;

public class DbManager
{

	public DataBaseHelper helper;

	public DbManager(Context context)
	{		
		helper = DataBaseHelper.getInstance(context);
		Log.i(Method.DEBUG_TAG + "_db", "DataBaseHelper.hashCode()=" + helper.hashCode());
	}
}
