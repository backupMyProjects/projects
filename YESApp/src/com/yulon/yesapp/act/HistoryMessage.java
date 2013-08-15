package com.yulon.yesapp.act;

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

public class HistoryMessage extends Activity implements UtilActivity {
	CommonFunction cf = new CommonFunction(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(R.layout.history_message) ;
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
		
		findViews();
		setupViews();
		
		getHistoryMessage();
		
	}

	TextView tv_history_message;
	@Override
	public void findViews() {
		tv_history_message = (TextView) findViewById(R.id.tv_history_message);
		
	}
	@Override
	public
	void setupViews(){
	}

	private void setTitleBar() {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(R.string.main_history_message);
		Button btn_info = (Button) findViewById(R.id.btn_info);
		btn_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			}
		});
	}
	
	private void getHistoryMessage(){
		if (cf.hasNetwork()) {
			// --------
			/**
			 * ToDo : link to UI
			 */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("isDelete", "N"));
			nameValuePairs.add(new BasicNameValuePair("licenseNo", "0030E2")); // car number ???
			(new HTTPPostTask(this, cf, GET_APP_MESSAGE){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					//-- UI update
					tv_history_message.setText(result);
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
