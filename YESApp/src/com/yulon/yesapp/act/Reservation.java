package com.yulon.yesapp.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Reservation extends TabActivity implements UtilActivity {
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
		
		getReservationList();
		getFavoriteList();
		
	}

	TextView tv_reservation_navi, tv_favorite;
	@Override
	public void findViews() {
		tv_reservation_navi = (TextView) findViewById(R.id.tv_reservation_navi);
		tv_favorite = (TextView) findViewById(R.id.tv_favorite);
		
	}
	@Override
	public
	void setupViews(){
	}

	private void setTitleBar() {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(R.string.main_reservation_navi);
		Button btn_info = (Button) findViewById(R.id.btn_info);
		btn_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			}
		});
	}

	private void setTabBar() {
		// 將xml轉換為View且指定給TabContentView
		LayoutInflater.from(this).inflate(R.layout.inlayout_reservation,
				getTabHost().getTabContentView(), true);

		// 設定各tab頁面by指定View id
		getTabHost().addTab(getTabHost().newTabSpec("reservation_tab1")
				.setIndicator(getString(R.string.reservation_tab1))
				.setContent(R.id.rl_one));

		getTabHost().addTab(getTabHost().newTabSpec("reservation_tab2")
				.setIndicator(getString(R.string.reservation_tab2))
				.setContent(R.id.rl_two));

		getTabHost().addTab(getTabHost().newTabSpec("reservation_tab3")
				.setIndicator(getString(R.string.reservation_tab3))
				.setContent(R.id.rl_three));

	}
	
	@SuppressWarnings("unchecked")
	private void getReservationList(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : link to UI
			 */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("from", "24.97785,121.545095"));
			(new HTTPPostTask(this, cf, GET_ALL_STATION_LOC){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					//-- UI update
					ArrayList<Map<String, Object>> resultAL = Toolets.json2HMAL(result);
					tv_reservation_navi.setText(result);
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
	
	@SuppressWarnings("unchecked")
	private void getFavoriteList(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : link to UI
			 */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("from", "24.97785,121.545095"));
			nameValuePairs.add(new BasicNameValuePair("carno", "0030E2"));
			(new HTTPPostTask(this, cf, GET_FAV){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					//-- UI update
					ArrayList<Map<String, Object>> resultAL = Toolets.json2HMAL(result);
					tv_favorite.setText(result);
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
