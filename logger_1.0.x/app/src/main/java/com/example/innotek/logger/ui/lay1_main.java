/**
 * 
 */
package com.example.innotek.logger.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.innotek.log.R;
import com.example.innotek.logger.Logger;
import com.example.innotek.logger.LoggerManager;
import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.protocol.data.LogModel;

import java.util.ArrayList;

/**
 * 
 * @author wuhao
 *2016-5-19
 */
public class lay1_main extends Activity {

	private TextView txt;
	private Intent intent;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//		.detectDiskReads()
//		.detectDiskWrites()
//		.detectNetwork() // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
//		.penaltyLog() //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
//		.build());
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//		.detectLeakedSqlLiteObjects() //探测SQLite数据库操作
//		.penaltyLog() //打印logcat
//		.penaltyDeath()
//		.build()); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay1);
		txt=(TextView)findViewById(R.id.lay1_txt);
	   // DataserviceManager.setServiceIP("http://192.168.10.195:8081/spms/services/queryCityList");
		LoggerManager.setServiceIP("http://192.168.10.195:8081/spms/services/queryCityList");
	}
	
	public void start_thread(View v) 
	{
	   // intent=new Intent(lay1_main.this,DataService.class);
	    intent=new Intent(lay1_main.this,LoggerManager.getInstance().dataServiceDao().getClass());
		this.startService(intent);		
	}
	public void stop_db(View v)
	{
		LoggerManager.getInstance().sqlDao().setIsLogDb(false);
		Toast.makeText(this, "设置不录入数据库", 0).show();	
	}
	
	public void show_islog(View v)
	{
		String mess="";
		//LoggerManager.getInstance().logUtilDao().judgeIsLog();
		Logger.judgeIsLog();
	//	Logger.setIsLog(true);
		if(Logger.getIsLog())
		{
			mess="true";
		}
		else {
			mess="false";
		}
		Toast.makeText(this, mess, 0).show();
	}
	public void Clearloglevel(View v)
	{
		LogParam.ShowLogLevel.clear();
	}
	public void showloglevel(View v)
	{
		String mess="";
		for (int i = 0; i < LogParam.ShowLogLevel.size(); i++) {
			mess=mess+LogParam.ShowLogLevel.get(i);
		}
		Toast.makeText(this, mess, 0).show();
	}
	public void setshowloglevel(View v)
	{
		ArrayList<String> list=new ArrayList<String>();
		list.add(String.valueOf(LogParam.ERROR));
		list.add(String.valueOf(LogParam.INFO));
		//LoggerManager.getInstance().logUtilDao().setshowLogLevel(list);
		Logger.setshowLogLevel(list);
	}
	public void setloglevel(View v) 
	{
		//Logger.setShowLogLevel(LogParam.WARN);
		Logger.judgeLogLevel();
	}
	public void LogCat(View v)
	{
		Logger.e("error","error");
		Logger.e("info","info");
		Logger.e("warn","warn");
		Logger.e("debug","debug");
	}
	public void stopLog(View v)
	{
		Logger.getInstance().setIsLogFile(false);
		//Logger.setIsLogFile(false);
	}
	public void Log(View v)
	{
		LogModel model=null;
		Logger.judgeIsFileLog();
		//Logger.judgeFlieLogLevel();
		Logger.getInstance().judgeFlieLogLevel();
		try {
			String aString=model.getcreateDate();
			android.util.Log.e("error", aString);
		} catch (Exception e) {
			// TODO: handle exception
			//Logger.logtoFlie(LogParam.ERROR,e,getApplicationContext());
			Logger.getInstance().logtoFlie(LogParam.ERROR,e,getApplicationContext());
			//Logger.logtoFlie(LogParam.DEBUG,e,getApplicationContext());
			Logger.getInstance().logtoFlie(LogParam.DEBUG,e,getApplicationContext());
			
		}
	//	Logger.log("aa");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(intent!=null){
		this.stopService(intent);
		}
		super.onDestroy();
	}
}
