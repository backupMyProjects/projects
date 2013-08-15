package com.twgan.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import static com.twgan.utils.Constants.*;

import com.twgan.R;
import com.twgan.adapter.ChatroomListAdapter;
import com.twgan.service.*;
import com.twgan.utils.*;
import static com.twgan.utils.Toolets.*;

import android.app.*;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ChatRoomList extends Activity implements Serializable {
    /** Called when the activity is first created. */
	private final static String TAG = "ChatRoomList";
	public final static String ACTIVITY_NAME = "com.twgan.activity."+TAG;
	
	CommonFunctions cf = new CommonFunctions(this);
	String usernameLogin;
	String uidLogin;
	boolean notifier;
	
	ChatRoomList crl;
	ListView listViewLayout;
	ActReceiver receiver;
	List<HashMap<String, String>> chatroomList;
	
	ProgressDialog dialog;
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterRec(ChatRoomService.SERVICE_NAME);
    	Log.d(TAG, "destroy it");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        usernameLogin = getIntent().getStringExtra("usernameLogin");
        uidLogin = getIntent().getStringExtra("uidLogin");
        cf.setLoginInfo(usernameLogin, uidLogin);
        
        cf.setInvisibleStatusbar_Titlebar();
        setContentView(R.layout.template_list);
        setBannerItem();
    		setFunctionsItem();
        
        listViewLayout = (ListView) this.findViewById(R.id.listViewLayout);
        
        Intent intent = new Intent(ChatRoomList.this, com.twgan.service.ChatRoomService.class);
		intent.setAction(this.ACTIVITY_NAME);
		intent.putExtra("uidLogin", uidLogin); // message From Activity
		intent.putExtra("usernameLogin", usernameLogin); // message From Activity
		if (isMyServiceRunning(ChatRoomService.SERVICE_NAME)){
		    stopService(intent);
		}
	    startService(intent);
	    registerRec(ChatRoomService.SERVICE_NAME);
        
        crl = this;
        
        dialog = ProgressDialog.show(ChatRoomList.this, "", "Loading... Please wait.", true, true);
        
        /* move to service
		List<HashMap<String, String>> chatroomList = getChatRoomList();
		ChatroomListAdapter adapter = new ChatroomListAdapter(this, chatroomList);
		listViewLayout.setAdapter(adapter);
		 */
        
    }
    
    /*--------------------------------------------*/
    // components setting
    
    private void setBannerItem(){
	    	final ImageButton bannerFunction = (ImageButton)findViewById(R.id.bannerFunction);
	    	//bannerFunction.setBackgroundResource(R.drawable.people);
	    	bannerFunction.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					//--------------------------------//
					cf.jump2Activity(Contact.class, new Bundle());
					//--------------------------------//
				}
			});
	    	
	
	    	TextView bannerTitle = (TextView)findViewById(R.id.bannerTitle);
	    	bannerTitle.setText(R.string.item_chatroom);
	
	    	final ImageButton bannerBack = (ImageButton)findViewById(R.id.bannerBack);
	    	bannerBack.setVisibility(INVISIBLE);
    	
    }

    private void setFunctionsItem(){

    	final ImageButton messageB = (ImageButton)findViewById(R.id.messageB);
    	messageB.setBackgroundResource(R.drawable.chatroomlist_act);
    	

    	final ImageButton contactB = (ImageButton)findViewById(R.id.contactB);
    	contactB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				cf.jump2Activity(Contact.class, new Bundle());
				//--------------------------------//
			}
		});

    	
    	final ImageButton findB = (ImageButton)findViewById(R.id.findB);
    	findB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				cf.jump2Activity(FindFriend.class, new Bundle());
				//--------------------------------//
			}
		});

    	
    	final ImageButton settingB = (ImageButton)findViewById(R.id.settingB);
    	settingB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				cf.jump2Activity(Setting.class, new Bundle());
				//--------------------------------//
			}
		});
    	
    	
    }
    
    
 // Inner Class : Receiver
    // @Self-Define
	public class ActReceiver extends BroadcastReceiver {
		private final String TAG = "ActReceiver";

		//String savedDateLine;
		//String lastDateLine;

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "OnReceiver");

			Object obj = intent.getSerializableExtra("chatroomList");// message From Service
			chatroomList = (List<HashMap<String, String>>) obj;
			//Toolets.printHashMapList(chatroomList);
			
			//HashMap<String, String> lastOne = null;
			//HashMap<String, String> savedOne = null;
			if( chatroomList.size() == 0 ){
				//Toast.makeText(cf.activity, "您沒有任何對話", Toast.LENGTH_SHORT).show();
			}else{
				//HashMap<String, String> lastOne = chatroomList.get(0);
				//lastDateLine = lastOne.get("dateline");
				//Log.d(TAG, savedDateLine + ":" + lastDateLine);
				//if ( savedDateLine != null && !savedDateLine.equals( lastDateLine ) ){
				//	Log.d(TAG, savedDateLine + "!=" + lastDateLine);
				//	cf.notifier(12345, "New message here!", "ChatRoomList" , "You got new message.", R.drawable.add, System.currentTimeMillis());
				//}
				//savedDateLine = lastDateLine;
				String lastDateLine = intent.getStringExtra("lastDateLine");
				String savedDateLine = intent.getStringExtra("savedDateLine");
				notifier = intent.getBooleanExtra("notifier", false);
				Log.d(TAG, lastDateLine + " ======================= " + savedDateLine + "========" + notifier);
				if (notifier){ 
					cf.notifier(12345, crl.getString(R.string.notifier_alarm), crl.getString(R.string.app_name) , crl.getString(R.string.notifier_msg), R.drawable.ic_launcher, System.currentTimeMillis()); 
					notifier = false;
				}
				
				ChatroomListAdapter adapter = new ChatroomListAdapter(crl, chatroomList);
				listViewLayout.setAdapter(adapter);
			}
			dialog.cancel();
			
			//cf.notifier(12345, "New message here!", "ChatRoomList" , "You got new message wthin a new chat room.", R.drawable.add, System.currentTimeMillis());
		}

		public ActReceiver() {
			Log.i(TAG, "init ActReceiver");

		}
	}
	
// Other Functions
	// @Self-Define
	private void registerRec(String actionName){
		if( receiver == null){
			Log.d(TAG, "on registerRec");
			// 注册接收器
			receiver = new ActReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(actionName);
			this.registerReceiver(receiver, filter);
		}else{
			Log.d(TAG, "Receiver registed"); // without check : no error
		}
	}
	
	// @Self-Define
	private void unregisterRec(String serviceName){
		if( !isMyServiceRunning(serviceName) || receiver==null ){
			Log.d(TAG, "Receiver not registed"); // without check : runtime exception
		}else{
			Log.d(TAG, "on unregisterRec");
			this.unregisterReceiver(receiver);
			receiver = null;
			// 解除注册接收器
		}
	}
	
	private boolean isMyServiceRunning(String serviceName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        	//Log.d(TAG, service.service.getClassName());
            if (serviceName.equals( service.service.getClassName() )) {
                return true;
            }
        }
        return false;
    }


}
