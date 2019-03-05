package com.example.innotek.logger.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.innotek.log.R;

import java.util.HashMap;

public class detail_manager extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		TextView tv_loglevel=(TextView)findViewById(R.id.Log_level); 
		TextView tv_exceptiontype=(TextView)findViewById(R.id.Exception_type); 
		TextView tv_logmessage=(TextView)findViewById(R.id.Log_message); 
		TextView tv_logcontent=(TextView)findViewById(R.id.Log_content); 
		TextView tv_UploadState=(TextView)findViewById(R.id.SyncStatus); 
		TextView tv_CreatDate=(TextView)findViewById(R.id.CreatDate); 
		TextView tv_MD5=(TextView)findViewById(R.id.MD5); 
		Intent intent=getIntent();
		HashMap<String, Object> map = (HashMap<String, Object>)intent.getSerializableExtra("data");
		tv_loglevel.setText(map.get("LogLevel").toString());
		tv_exceptiontype.setText(map.get("ExceptionType").toString());
		if(map.get("LogMessage")==null)
		{
			tv_logmessage.setText("值为null");
		}
		else {
			tv_logmessage.setText(map.get("LogMessage").toString());
		}		
		tv_logcontent.setText(map.get("LogContent").toString());
		tv_UploadState.setText(map.get("SyncStatus").toString());
		tv_CreatDate.setText(map.get("CreatDate").toString());
		tv_MD5.setText(map.get("MD5").toString());
	}
}
