/**
 * 
 */
package com.example.innotek.logger.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.innotek.log.R;
import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.protocol.data.LogModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 自己构造Adapter存在数据
 * @author wuhao
 *2016-5-18
 */
public class MyAdapter extends BaseAdapter {
	// 填充数据的list
	    private List<LogModel> list=new ArrayList<>();
		// 用来控制CheckBox的选中状况
		private static HashMap<Integer, Boolean> isSelected;
		// 上下文
		private Context context;
		// 用来导入布局
		private LayoutInflater inflater = null;
		// 构造器
		public MyAdapter(List<LogModel> list, Context context) {
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
			isSelected = new HashMap<Integer, Boolean>();
			// 初始化数据
			initDate();
		}
		// 初始化isSelected的数据
		private void initDate() {
			for (int i = 0; i < list.size(); i++) {
				getIsSelected().put(i, false);
			}
		}
		public static HashMap<Integer, Boolean> getIsSelected() {
			return isSelected;
		}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView=inflater.inflate(R.layout.list_view_detail, null);
		//	convertView = inflater.inflate(R.layout.listviewitem, null);
			holder.tv_Meidcode=(TextView) convertView.findViewById(R.id.Meidcode);
			holder.tv_LogLevel=(TextView) convertView.findViewById(R.id.LogLevel);
			holder.tv_Platform=(TextView) convertView.findViewById(R.id.Platform);
			holder.tv_SyncStatus=(TextView) convertView.findViewById(R.id.SyncStatus);
			holder.tv_VersionNo=(TextView) convertView.findViewById(R.id.VersionNo);
			holder.cb = (CheckBox) convertView.findViewById(R.id.Chb);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置list中TextView的显示
		//holder.tv.setText(list.get(position));
		holder.tv_Meidcode.setText(list.get(position).getmeidCode());
		holder.tv_Platform.setText(list.get(position).getplatform());
		holder.tv_VersionNo.setText(list.get(position).getversionNo());
		holder.tv_LogLevel.setText(String.valueOf(list.get(position).getlogLevel()));
		if(list.get(position).getsyncStatus().equalsIgnoreCase(LogParam.Up_Yes))
		{
			holder.tv_SyncStatus.setText("已同步");
		}
		else {
			holder.tv_SyncStatus.setText("未同步");
		}
		// 根据isSelected来设置checkbox的选中状况
		holder.cb.setChecked(getIsSelected().get(position));
		return convertView;
	}
	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyAdapter.isSelected = isSelected;
	}

	/**
	 * Adapter中存储的数据
	 * @author wuhao
	 *2016-5-23
	 */
	public static class ViewHolder {
		TextView tv_SyncStatus;
		TextView tv_LogLevel;
		TextView tv_VersionNo;
		TextView tv_Platform;
		TextView tv_Meidcode;
		CheckBox cb;
	}

}
