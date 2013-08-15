package com.yulon.yesapp.act;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import com.leo.android.util.*;
import com.leo.android.util.task.*;
import com.leo.android.util.interfaces.UtilActivity;
import static com.leo.android.util.Toolets.*;
import static com.yulon.yesapp.util.Constants.*;
import com.yulon.yesapp.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ChargingHistory extends TabActivity implements UtilActivity {
	CommonFunction cf = new CommonFunction(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(R.layout.tabbed_layout) ;
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	

	@Override
	public void init(int setLayoutId) {
		setContentView(setLayoutId);

		setTitleBar();
		setTabBar();
		
		findViews();
		setupViews();

		getChargingHistory();
		getBookingHistory();
		
	}

	TextView tv_charginghistory, tv_bookinghistory;
	public void findViews() {
		tv_charginghistory = (TextView) findViewById(R.id.tv_charginghistory);
		tv_bookinghistory = (TextView) findViewById(R.id.tv_bookinghistory);
		
	}
	public void setupViews(){
	}

	private void setTitleBar() {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(R.string.main_battery);
		Button btn_info = (Button) findViewById(R.id.btn_info);
		btn_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setTabBar() {
		// 將xml轉換為View且指定給TabContentView
		LayoutInflater.from(this).inflate(R.layout.inlayout_charginghistory,
				getTabHost().getTabContentView(), true);

		// 設定各tab頁面by指定View id
		getTabHost().addTab(getTabHost().newTabSpec("chargingHistory_tab1")
				.setIndicator(getString(R.string.charginghistory_tab1))
				.setContent(R.id.rl_one));

		getTabHost().addTab(getTabHost().newTabSpec("chargingHistory_tab2")
				.setIndicator(getString(R.string.charginghistory_tab2))
				.setContent(R.id.rl_two));

	}

	/**
	 * ToDo : Need to update the job
	 */
	private void getChargingHistory(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : 
			 * 1. link to UI
			 * 2. Upgrade HTTPGetTask -> HttpClientConnector.getData(URL, Parms)
			 */
			(new HTTPGetTask(this, cf, php_GET_CHARGING_HISTORY+"?carno="+"9753e2"+"&page=1&pagesize=10"){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					
					ArrayList<HashMap<String, String>> resultList = GeneralXmlPullParser.parse( result );
					tv_charginghistory.setText(result);
				}
				@Override	
				protected void onPreExecute() {
					dialog = ProgressDialog.show(this.activity, "", this.activity.getString(R.string.loading) , true, true);
				}
				
			}).execute(new ArrayList<NameValuePair>());

			// --------
		}
	}
	

	private void getBookingHistory(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : 
			 * 1. link to UI
			 * 2. Upgrade HTTPGetTask -> HttpClientConnector.getData(URL, Parms)
			 */
			(new HTTPGetTask(this, cf, php_GET_BOOKING_HISTORY+"?carno="+"9753e2"+"&page=1&pagesize=10"){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					
					ArrayList<HashMap<String, String>> resultList = GeneralXmlPullParser.parse( result );
					tv_bookinghistory.setText(result);
				}
				@Override	
				protected void onPreExecute() {
					dialog = ProgressDialog.show(this.activity, "", this.activity.getString(R.string.loading) , true, true);
				}
				
			}).execute(new ArrayList<NameValuePair>());

			// --------
		}
	}
	
	
}
