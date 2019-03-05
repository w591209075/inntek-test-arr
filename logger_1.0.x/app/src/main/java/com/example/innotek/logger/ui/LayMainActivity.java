/**
 * 
 */
package com.example.innotek.logger.ui;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.innotek.log.R;
import com.example.innotek.logger.enums.LogParam;

import java.util.ArrayList;

/**
 * 这是通过ViewPager和ActivityGroup，实现滑动切换Activity，但是谷歌和网上不推荐使用这个
 * 由于之前创建的界面是继承Activity不得以使用这个方法，如以后要实现“滑动”效果建议使用ViewPager和fragment这个组合
 * @author wuhao
 *2016-5-19
 */
public class LayMainActivity extends ActivityGroup {

	private ViewPager viewPager;
	private ArrayList<View> pageview;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_main);
		LogParam.versionNo=String.valueOf(getCurrentVersionCode(LayMainActivity.this));
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		//查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater =getLayoutInflater();
        View view1 = getLocalActivityManager().startActivity("activity01",new Intent(LayMainActivity.this, MainActivity.class)).getDecorView();
        View view2 = getLocalActivityManager().startActivity("activity02",new Intent(LayMainActivity.this, lay1_main.class)).getDecorView();  
        View view3 = getLocalActivityManager().startActivity("activity03",new Intent(LayMainActivity.this, lay2_main.class)).getDecorView();
//        View view0=inflater.inflate(R.layout.activity_main, null);
//        View view1 = inflater.inflate(R.layout.lay1, null);
//        View view2 = inflater.inflate(R.layout.lay2, null);
   //     View view3 = inflater.inflate(R.layout.lay_main, null);
        
        //将view装入数组
        pageview =new ArrayList<View>();
      //  pageview.add(view0);
        pageview.add(view1);
        pageview.add(view2);
        pageview.add(view3);
        PagerAdapter myPagerAdapter=new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return pageview.size();
			}
			//是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {  
                   ((ViewPager) arg0).removeView(pageview.get(arg1));  
               }  
           
           //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
           public Object instantiateItem(View arg0, int arg1){
               ((ViewPager)arg0).addView(pageview.get(arg1));
               return pageview.get(arg1);                
           }
		};
		//绑定适配器
        viewPager.setAdapter(myPagerAdapter);
	}
	public static int getCurrentVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager()
				.getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.w(LayMainActivity.class.getSimpleName(),
				"versionName not defined in AndroidManifest.xml");
		}
		return versionCode;
	}

}
