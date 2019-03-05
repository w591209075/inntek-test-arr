/**
 * 
 */
package com.example.innotek.logger.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.protocol.data.BaseData;
import com.example.innotek.logger.protocol.data.LogModel;
import com.example.innotek.logger.sqlite.SqliteDao;
import com.example.innotek.logger.utils.RestletUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * @author wuhao
 *2016-5-19
 */
public class DataService extends Service {
	private static final String TAG = "DataService";
	public static final String ACTION_DATASERVICE = "com.example.Logger.ACTION_DATASERVICE";
	/** 创建上传日志线程*/
	private UpLoadLoggerThread upLoadLoggerThread=null;
	/** 数据上传日志线程运行状态 */
	private boolean runStatus = false;
	/** DataService 退出状态 */
	private boolean exitFlag = false;
	/** 数据库操作方法 */
	private SqliteDao sqliteDao=null;
	/** 查询需要上传日志的集合 */
	private List<LogModel> loggerlist=new ArrayList<LogModel>();
	private final JSONSerializer serializer = new JSONSerializer();
	private final JSONDeserializer<Object> deserializer = new JSONDeserializer<Object>();
	
	/** 上传失败累计计数 ,用于调整上传休眠时间 */
	private int count = 0;
	private int sleepInterval = 1000;


	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start IBinder"); 
		return null;
	}


	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.e(TAG, "start onCreate");
		upLoadLoggerThread=new UpLoadLoggerThread();
		sqliteDao=new SqliteDao(getBaseContext());
		super.onCreate();
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		exitFlag=true;
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start Command");  
		if(upLoadLoggerThread!=null&&!upLoadLoggerThread.isAlive())
		{
			runStatus=true;
			try {
				upLoadLoggerThread.start();//开启上传机制
				Log.e(TAG, "upLoadLoggerThread start");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	 class UpLoadLoggerThread extends Thread
	{

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override	
		public void run() {
			// TODO Auto-generated method stub
			//super.run();
			Log.e(TAG, "start Thread");
			while (runStatus) {
				try {
					sqliteDao.clearDatabydays(LogParam.DeleteDay);//删除对应天数的数据
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if(loggerlist!=null&&loggerlist.size()<=0)    //判断存在需要上传的数据
					{
						do
						{
							try 
							{
								loggerlist=sqliteDao.queryUpdata();   //查询数据
							} 
							catch (Exception e)
							{
								// TODO: handle exception
								e.printStackTrace();
							} 
						}while (loggerlist == null);// 列表为空时循环读取
						// 标识退出并且数据已经上传完成，允许DataService退出
						if (exitFlag && loggerlist.size() <= 0)
						{
							Log.i("error", "exit and no recordDatas");
							runStatus = false;
						}

						if (loggerlist.size() <= 0)
						{
							try
							{
								Log.i("SpmsDroidx", "无数据，休眠 5 秒");
								Thread.sleep(1000 * 5);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							for (int i = 0; i < loggerlist.size(); i++) {     //数据上传
								switch (count)                                //根据重复上传次数设置睡眠时间
								{
								case 0:
									sleepInterval = 1;
									break;
								case 100:
									sleepInterval = 1000 * 5;
									break;
								case 500:
									sleepInterval = 1000 * 10;
									break;
								case 1000:
									sleepInterval = 1000 * 30;
								default:
									break;
								}
								try
								{
									Log.i("SpmsDroidx", "有数据，休眠" + sleepInterval / 1000.0 + "秒");
									Thread.sleep(sleepInterval);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								LogModel data=(LogModel)loggerlist.get(i);   //获取相应的数据
								String request = serializer.exclude("class").exclude("_id").exclude("syncStatus").serialize(data);
							    Log.e("request:", request);//生成发生请求的request 
							//	String result = RestletUtils.post(LogParam.ServiceIP, request);	   //像后台发送请求数据并且获取相应的回复					
								String result=RestletUtils.httpUrlConnection(LogParam.ServiceIP, request);
								// 解析处理结果
								Map<String, Object> map = null;
								try
								{
									map = BaseData.getMap(result);     //解析返回数据
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
								if(map!=null)
								{
									if("0".equals(String.valueOf(map.get("errCode"))))      //操作成功
									{
										Log.e(TAG, "sucess");
										long id=data.get_id();
										data.setsyncStatus(LogParam.Up_Yes);     //将同步状态改为已上传
										if(sqliteDao.update(data))
										{
											if(data.getlogContent().equals(""))
											{
												sqliteDao.clearDataByLogcontent(id);//删除重复数据
											}
											data=null;
										}
										else {
											Log.i("ifno","修改失败");
										}
										count = 0;
									}
									else 
									{
										try
										{
											/**设置上传次数*/
											sqliteDao.setSyncTimes(loggerlist.get(i).get_id());
										}
										catch (Exception e)
										{
											e.printStackTrace();
										}
										count++;                   //上传次数累计
										i--;						//重新获取该数据进行上传
										Log.i("SpmsDroidx", "ERROR i =" + i);
									}
								}
								else 
								{
									try
									{
										/**设置上传次数*/
										sqliteDao.setSyncTimes(loggerlist.get(i).get_id());
									}
									catch (Exception e)
									{
										e.printStackTrace();
									}
									count++;				//上传次数
									i--;					//重新获取该数据进行上传
									Log.i("SpmsDroidx", "ERROR i =" + i);	
								}
							}
							loggerlist.clear();				//本次数据处理完成，清除数据
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
	}

}
