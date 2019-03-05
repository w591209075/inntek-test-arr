package com.example.innotek.logger.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.innotek.log.R;
import com.example.innotek.logger.LoggerManager;
import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.protocol.data.LogModel;
import com.example.innotek.logger.sqlite.SqliteDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class MainActivity extends Activity {

	private MyAdapter mAdapter;
	private SqliteDao sqliteDao;
	/** 显示数据的ListView控件 */
	private ListView mListView;
	/** 显示选择条数的控件 */
	private TextView mShow;
	/** 判断是否全选 */
	private CheckBox mCheckAllBox;
	/** 查询数据库中所有数据集合 */
	private List<LogModel> mLogList=new ArrayList<LogModel>();
	 private final Handler handler = new Handler();  
	  /**
	   * 每隔几秒刷新数据
	   */
	    private final Runnable task = new Runnable() {  
	  
	        @Override  
	        public void run() {  
	            // TODO Auto-generated method stub  	           
	            handler.postDelayed(this, 5000);  
	            reFreshData();
	        }  
	    };  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LoggerManager.init(getBaseContext());
		sqliteDao = new SqliteDao(this);
		mListView=(ListView)findViewById(R.id.listview);
		mShow=(TextView)findViewById(R.id.txt_show);
		 mCheckAllBox=(CheckBox)findViewById(R.id.cb_all);
		mCheckAllBox.setEnabled(true);
		mCheckAllBox.setClickable(true);
		try {		
			handler.post(task);  
			reFreshData();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "查询失败或者无数据", 0).show();
			return;
		}	
		mCheckAllBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked==true)
				{
					for (int i = 0; i < mLogList.size(); i++) {
						MyAdapter.getIsSelected().put(i, true);
					}
				}
				else {
					for (int i = 0; i < mLogList.size(); i++) {
						MyAdapter.getIsSelected().put(i, false);
					}
				}
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	/**
	 * 将查询到的数据转化为List<HashMap<String, Object>>类型
	 * @param Loglist
	 * @return
	 */
	public List<HashMap<String, Object>> ModelToMap(List<LogModel> Loglist) {
		final List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			for (LogModel item : Loglist) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("_id", item.get_id());
				map.put("Meidcode", item.getmeidCode());
				map.put("Call_Class", item.getcallClass());
				map.put("Platform", item.getplatform());
				map.put("Version_No", item.getversionNo());

				if (item.getlogLevel()==LogParam.DEBUG) {
					map.put("LogLevel", "正常调试");
				} else if (item.getlogLevel()==LogParam.ERROR) {
					map.put("LogLevel", "错误异常");
				} else if (item.getlogLevel()==LogParam.INFO) {
					map.put("LogLevel", "正常信息");
				} else if (item.getlogLevel()==LogParam.WARN) {
					map.put("LogLevel", "警告异常");
				} else {
					map.put("LogLevel", "未知错误");
				}
				map.put("LogContent", item.getlogContent());
				map.put("LogMessage", item.getlogMessage());
				map.put("ExceptionType", item.getexceptionType());
				map.put("CreatDate", item.getcreateDate());
				map.put("MD5", item.getmd5());
				if (item.getsyncStatus().equals(LogParam.Up_No)) {
					map.put("SyncStatus", "未同步");
				} else {
					map.put("SyncStatus", "已同步");
				}
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;

	}

	/**
	 * 自动刷新ListView控件
	 */
	public void reFreshData()
	{
		mCheckAllBox.setChecked(false);
		mShow.setText("已选中0项");
		final List<HashMap<String, Object>> list;
		List<LogModel> Loglist = null;
		try {
			Loglist = sqliteDao.QueryList(null, null);
			if (Loglist.size() <= 0) {
				Toast.makeText(this, "查询失败或者无数据", Toast.LENGTH_SHORT).show();
				return;
			}
		    list = ModelToMap(Loglist);
		    mLogList=Loglist;
			mAdapter=new MyAdapter(Loglist, this);
			mAdapter.notifyDataSetChanged();
			mListView.setAdapter(mAdapter);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "查询失败或者无数据", Toast.LENGTH_SHORT).show();
			return;
		}
		mListView.setOnItemClickListener(new OnItemClickListener() {

			long checkNum=0;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
				MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) arg1.getTag();
				// 改变CheckBox的状态
				holder.cb.toggle();
				// 将CheckBox的选中状况记录下来
				MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked());
				// 调整选定条目
				if (holder.cb.isChecked() == true) 
				{
					checkNum++;
				}
				else
				{
					checkNum--;
				}
				// 用TextView显示
				mShow.setText("已选中" + checkNum + "项");
			}	
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				HashMap<String, Object> map = (HashMap) list.get(arg2);			
				Intent intent = new Intent(MainActivity.this,
						detail_manager.class);
				intent.putExtra("data", map);
				startActivity(intent);
				return true;
			}
		});
	}
	public void ADD(View v)
	{
		LogModel item=null;
		try
		{
			long a = item.get_id();
		} catch (Exception e) {
			// TODO: handle exception
			String messageString="";
			
			
			try {
				item=new LogModel("payform", LogParam.ERROR, e);
				
				//if(sqliteDao.addExceptionLog("payform", LogParam.ERROR, e))
				if(LoggerManager.getInstance().sqlDao().e("payfom", e))
				//if(LoggerManager.getInstance(this).e("payfom", e))
				{
					messageString="成功";
				}
				else
				{
					messageString="失败";
				}
				Toast.makeText(this, "增加数据"+messageString,Toast.LENGTH_SHORT).show();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		finally
		{
		//	reFreshData();
		}
	}
	public void UPLOAD(View v) 
	{
		List<LogModel> checkedlist=new ArrayList<>();
		for (int i = 0; i < mLogList.size(); i++) 
		{
			if(mLogList.get(i).getsyncStatus().equals(LogParam.Up_No))
			{
				if (MyAdapter.getIsSelected().get(i))
				{
					checkedlist.add((LogModel)mLogList.get(i));
				} 
			}
		}
		if(checkedlist.size()>0)
		{
			try
			{
				for (int i = 0; i < checkedlist.size(); i++)
				{
					LogModel itemLogModel=checkedlist.get(i);
					itemLogModel.setsyncStatus(LogParam.Up_Yes);		
					boolean flag=sqliteDao.update(itemLogModel);
				}
			} 
			catch (Exception e) {
				// TODO: handle exception
			}		
		//	reFreshData();
		}
		else
		{
			Toast.makeText(MainActivity.this, "没有选择可以同步的数据", Toast.LENGTH_SHORT).show();
		}
	}

	public void DELETE(View v)
	{
		List<LogModel> checkedlist=new ArrayList<>();
		for (int i = 0; i < mLogList.size(); i++) 
		{
			if(mLogList.get(i).getsyncStatus().equals(LogParam.Up_Yes)
					&&mLogList.get(i).getlogContent().equals(""))
			{
				if (MyAdapter.getIsSelected().get(i))
				{
					checkedlist.add((LogModel)mLogList.get(i));
				} 
			}
		}
		if(checkedlist.size()>0)
		{
			try
			{
				for (int i = 0; i < checkedlist.size(); i++)
				{
					LogModel itemLogModel=checkedlist.get(i);
					itemLogModel.setsyncStatus(LogParam.Up_Yes);		
					boolean flag=sqliteDao.clearDataByLogcontent(itemLogModel.get_id());
				}
			} 
			catch (Exception e) {
				// TODO: handle exception
			}		
		//	reFreshData();
		}
		else
		{
			Toast.makeText(MainActivity.this, "没有选择可以删除的数据", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
