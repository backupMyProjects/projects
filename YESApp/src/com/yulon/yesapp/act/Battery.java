package com.yulon.yesapp.act;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class Battery extends TabActivity implements UtilActivity {
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

		updateBatteryState();
		updateCarState();
		
	}

	TextView tv_car_state, tv_car_charge_state;
	Button btn_update;
	public void findViews() {
		btn_update = (Button)findViewById(R.id.btn_update);
		tv_car_state = (TextView) findViewById(R.id.tv_car_state);
		tv_car_charge_state = (TextView) findViewById(R.id.tv_car_charge_state);
		
	}
	public void setupViews(){
		btn_update.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				updateBatteryState();
				// --------------------------------//
			}
		});
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
		LayoutInflater.from(this).inflate(R.layout.inlayout_battery,
				getTabHost().getTabContentView(), true);

		// 設定各tab頁面by指定View id
		getTabHost().addTab(getTabHost().newTabSpec("battery_tab1")
				.setIndicator(getString(R.string.battery_tab1))
				.setContent(R.id.rl_one));

		getTabHost().addTab(getTabHost().newTabSpec("battery_tab2")
				.setIndicator(getString(R.string.battery_tab2))
				.setContent(R.id.rl_two));

	}

	/**
	 * ToDo : Need to update the job
	 */
	private void updateBatteryState(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : 
			 * 1. link to UI
			 * 2. Upgrade HTTPGetTask -> HttpClientConnector.getData(URL, Parms)
			 */
			(new HTTPGetTask(this, cf, php_GET_CAR_STATE+"?carno="+"9753e2"){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					
					ArrayList<HashMap<String, String>> resultList = GeneralXmlPullParser.parse( result );
					tv_car_charge_state.setText(result);
				}
				@Override
				protected void onPreExecute() {
					dialog = ProgressDialog.show(this.activity, "", this.activity.getString(R.string.loading) , true, true);
				}
				
			}).execute(new ArrayList<NameValuePair>());

			// --------
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateCarState(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : link to UI
			 */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			//nameValuePairs.add(new BasicNameValuePair("from", "24.97785,121.545095"));
			nameValuePairs.add(new BasicNameValuePair("carno", "0030E2"));
			nameValuePairs.add(new BasicNameValuePair("pswd", "27932239"));
			(new HTTPPostTask(this, cf, GET_CAR_STATUS){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					
					//cf.log("Get Result String : " + result);
					//-- UI update
					tv_car_state.setText(result);
					//--
				}
				@Override
				protected void onPreExecute() {
					dialog = ProgressDialog.show(this.activity, "", this.activity.getString(R.string.loading) , true, true);
				}
				
			}).execute(nameValuePairs);
			// --------
		}
	}
}
