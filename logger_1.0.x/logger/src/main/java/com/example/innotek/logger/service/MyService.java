/**
 * 
 */
package com.example.innotek.logger.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

/**
 * @author wuhao
 *2016-5-20
 */
public class MyService extends Service {

	private static final String TAG = "MySerice";
	private MyBinder mBinder = new MyBinder();
	@Override
	public IBinder onBind(Intent intent) {
	Log.e(TAG, "start IBinder"); 
	return mBinder;
	}
	@Override
	public void onCreate() {
	Log.e(TAG, "start onCreate");
	super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
	Log.e(TAG, "start onStart");
	super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
	Log.e(TAG, "start onDestroy");
	super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
	Log.e(TAG, "start onUnbind");
	return super.onUnbind(intent);
	}

	// 返回时间
	public String getSystemTime() {
	Time t = new Time();
	t.setToNow();
	return t.toString();
	}
	public class MyBinder extends Binder {
	public MyService getService() {
	return MyService.this;
	}
	}
}
