package com.twgan.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.twgan.R;
import com.twgan.utils.*;
import static com.twgan.utils.Toolets.*;
import static com.twgan.utils.Constants.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
    /** Called when the activity is first created. */
	private final static String TAG = "Login";
	public final static String ACTIVITY_NAME = "com.twgan.activity."+TAG;
	
	String usernameLogin;
	String uidLogin;
	private CommonFunctions cf = new CommonFunctions(this);
	
	boolean hasLogin = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cf.setInvisibleStatusbar_Titlebar();
        
        setContentView(R.layout.login);

    		String result = cf.readProp(LOGIN_FILE);
    		Log.d(TAG, result);
    		hasLogin = loginHandler(result);
        if(hasLogin){
			cf.jump2Activity(ChatRoomList.class, new Bundle());
        }else{
            setupLoginAction();
        }

        
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        //if(event.getRepeatCount() == 0){
	        switch(keyCode){
	        	case KeyEvent.KEYCODE_BACK :
	        		cf.log(TAG,"press back");
	        }
        //}
        

        return super.onKeyDown(keyCode, event);  
    }
    
    private void setupLoginAction(){
    	
    	final Button loginB = (Button)findViewById(R.id.loginB);
    	loginB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
		        
		        String username = ((EditText)findViewById(R.id.account_ET)).getText().toString();
		        String password = ((EditText)findViewById(R.id.password_ET)).getText().toString();
		        
		        if (cf.hasNetwork()){
		        	//--------
		        		LoginTask task = new LoginTask();
					task.execute(new String[] { SERVER + URLPATH_LOGIN + "?username="+username+"&password="+password });
					
			        //--------
		        }

		        
				//--------------------------------//
			}
		});
    	
    	final Button registerB = (Button)findViewById(R.id.registerB);
    	registerB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				Uri uri = Uri.parse(REGISTER_URL);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);

				//--------------------------------//
			}
		});
    	
    }
    
    
	
	
	private boolean loginHandler(String result){
		if ( !isNull(result) || !isEmpStr(result) ){
	    	cf.writeProp(result, LOGIN_FILE);
			String[] tempA = result.split(",");
			if ( tempA.length == 2 ){
				uidLogin = tempA[0];
				usernameLogin = tempA[1];
				//loginCheck = true;
				if ( uidLogin == "" ){
					uidLogin = "-3";
					usernameLogin = null;
					return false;
				}

				
		        cf.setLoginInfo(usernameLogin, uidLogin);
				//cf.jump2Activity(ChatRoomList.class, new Bundle());
				return true;
			}else{
				uidLogin = "-2";
				usernameLogin = null;
			}
		}else{
			uidLogin = "-1";
			usernameLogin = null;
		}
		return false;
	}
    
    
    private class LoginTask extends AsyncTask<String, Void, String> {
    	
    	//boolean loginCheck = false;
    	ProgressDialog dialog;
    	
		@Override
		protected void onPostExecute(String result) {
			if ( dialog.isShowing() ){dialog.cancel();}
			System.out.println("result : "+result);
			
			hasLogin = loginHandler(result);
			cf.jump2Activity(ChatRoomList.class, new Bundle());
			//super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(Login.this, "", "Login... Please wait.", true, true);
			//super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader( new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//System.out.println("response : "+response);
			
			return response;
		}
	}
    
    
}