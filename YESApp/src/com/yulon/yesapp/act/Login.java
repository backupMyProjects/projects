package com.yulon.yesapp.act;

import static com.yulon.yesapp.util.Constants.GET_CAR_STATUS;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.yulon.yesapp.R;
import com.leo.android.util.*;
import com.leo.android.util.interfaces.*;
import com.leo.android.util.task.HTTPPostTask;
import static com.leo.android.util.Toolets.*;
import static com.yulon.yesapp.util.Constants.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements UtilActivity {
	CommonFunction cf = new CommonFunction(this);
	
	String accountString;
	String passwordString;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(R.layout.login);
	}

	@Override
	public void init(int setLayoutId) {
		setContentView(setLayoutId);
		findViews();
		setupViews();
		
	}

	Button btn_login_login;
	EditText et_login_account, et_login_password;
	@Override
	public void findViews() {
		btn_login_login  = (Button) findViewById(R.id.btn_login_login);
		et_login_account = (EditText) findViewById(R.id.et_login_account);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
	}
	@Override
	public void setupViews() {
		btn_login_login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				getInput();
				loginCheck();
				//cf.slide2Activity(Main.class, new Bundle(), false);
				// --------------------------------//
			}
		});
		
	}
	
	private void getInput(){
		accountString = et_login_account.getText().toString();
		passwordString = et_login_password.getText().toString();
	}
	
	@SuppressWarnings("unchecked")
	private void loginCheck(){
		if (cf.hasNetwork(true)) {
			// --------
			/**
			 * ToDo : link to UI
			 */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("carno", accountString));
			nameValuePairs.add(new BasicNameValuePair("pswd", passwordString));
			(new HTTPPostTask(this, cf, LOGIN){
				ProgressDialog dialog;
				@Override
				protected void onPostExecute(String result) {
					this.resultCheck = !Toolets.isEmpStr(result);
					if ( dialog.isShowing() ){ dialog.cancel(); }
					
					//cf.log("Get Result String : " + result);
					//-- UI update
					if ( true || result.indexOf(":0") != -1 ){
						cf.slide2Activity(Main.class, new Bundle(), false);
					}else{
						Toast.makeText(this.activity, R.string.fail_login, Toast.LENGTH_SHORT).show();
					}
					//--
				}
				@Override
				protected void onPreExecute() {
					dialog = ProgressDialog.show(this.activity, "", this.activity.getString(R.string.login) , true, true);
				}
				
			}).execute(nameValuePairs);
			// --------
		}
	}

	

}
