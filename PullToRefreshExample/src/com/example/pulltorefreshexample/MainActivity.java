package com.example.pulltorefreshexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.risky.library.component.XListView;
import me.risky.library.component.XListView.IXListViewListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
	private static String TAG = "MainActivity";
	private XListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("item " + i);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		
		//=======================
		
		
		mListView = (XListView) findViewById(R.id.listview);
		mListView.setAdapter(adapter);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
//		mListView.setRefreshTime(getTime());
		mListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				Handler mHandler = new Handler();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mListView.setRefreshTime(getTime());
						mListView.stopRefresh();
					}
				}, 2500);
			}

			@Override
			public void onLoadMore() {
				Log.d(TAG, "load");

				Handler mHandler = new Handler();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mListView.setRefreshTime(getTime());
						mListView.stopLoadMore();
					}
				}, 2500);

			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mListView.startRefresh();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
				.format(new Date());
	}

}
