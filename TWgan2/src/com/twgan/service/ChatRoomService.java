package com.twgan.service;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import serialize.SerializationUtils;

import com.twgan.R;
import com.twgan.activity.ChatRoomList;
import com.twgan.activity.Login;
import com.twgan.adapter.ChatroomListAdapter;
import com.twgan.utils.*;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ChatRoomService extends Service {
// Init Var
	private final static String TAG = "ChatRoomService";
	public final static String SERVICE_NAME = "com.twgan.service."+TAG;
	
	boolean isRunning = false;
	ChatroomServiceReceiver serviceReceiver;

	String usernameLogin;
	String uidLogin;
	
	boolean notifier;

// Ref extends : Service
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return null;
	}
	
	@Override
	public void onCreate() {

		Log.i(TAG, "Services onCreate");

		serviceReceiver = new ChatroomServiceReceiver();
	    
		super.onCreate();

	}
	
	@Override
	 public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "Services onStartCommand");
		
		usernameLogin = intent.getStringExtra("usernameLogin");
		uidLogin = intent.getStringExtra("uidLogin");
		//System.out.println("uidLogin:"+uidLogin);
		 
		isRunning = true;
		new ChatroomServiceThread().start();
		
 
		  
		return super.onStartCommand(intent, flags, startId);
	 }

	@Override
	public void onStart(Intent intent, int startId) {

		Log.i(TAG, "Services onStart");
		
		super.onStart(intent, startId);

	}

	@Override
	public void onDestroy() {
		
		Log.i(TAG, "Service onDestory");
		isRunning = false;
		
	    //unregisterReceiver(serviceReceiver);
	    
		super.onDestroy();

	}
	
// Inner Class : Receiver
	// @Self-Define
	public class ChatroomServiceReceiver extends BroadcastReceiver {
		private final String TAG = "ChatRoomService.ServiceReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "OnReceiver");
		}

		public ChatroomServiceReceiver() {
			Log.i(TAG, "init ServiceReceiver");
		}
	}

// Inner Class : Thread	
	// @Self-Define
	public class ChatroomServiceThread extends Thread {

		String savedDateLine;
		String lastDateLine;
		int originalSize;
		
		public void run() {
			while (isRunning) {
				
				// do job
				List<HashMap<String, String>> chatroomList = getChatRoomList();
				if (chatroomList != null & chatroomList.size() > 0){
					HashMap<String, String> lastOne = chatroomList.get(0);
					lastDateLine = lastOne.get("dateline");
					Log.d(TAG, savedDateLine + ":" + lastDateLine);
					if ( savedDateLine != null && !savedDateLine.equals( lastDateLine ) || originalSize != chatroomList.size() ){
						Log.d(TAG, savedDateLine + "!=" + lastDateLine);
						notifier = (originalSize != chatroomList.size()) ? false : true;
						Object obj = null;
						try {
							obj = SerializationUtils.clone((Serializable) chatroomList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent intent = new Intent();
						intent.putExtra("chatroomList", (Serializable)obj); // message From Service
						intent.putExtra("notifier", notifier);
						intent.putExtra("savedDateLine", savedDateLine);
						intent.putExtra("lastDateLine", lastDateLine);
						intent.setAction(SERVICE_NAME);// action與接收器相同
						sendBroadcast(intent);
					}else if( savedDateLine == null ){
						Object obj = null;
						try {
							obj = SerializationUtils.clone((Serializable) chatroomList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent intent = new Intent();
						intent.putExtra("chatroomList", (Serializable)obj); // message From Service
						intent.putExtra("notifier", notifier);
						intent.putExtra("savedDateLine", savedDateLine);
						intent.putExtra("lastDateLine", lastDateLine);
						intent.setAction(SERVICE_NAME);// action與接收器相同
						sendBroadcast(intent);
					}else{
						//notifier = false;
					}
					originalSize = chatroomList.size();
					savedDateLine = lastDateLine;
				}else{
					// no data found.
					Object obj = null;
					try {
						obj = SerializationUtils.clone((Serializable) chatroomList);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent = new Intent();
					intent.putExtra("chatroomList", (Serializable)obj); // message From Service
					intent.putExtra("notifier", notifier);
					intent.putExtra("savedDateLine", savedDateLine);
					intent.putExtra("lastDateLine", lastDateLine);
					intent.setAction(SERVICE_NAME);// action與接收器相同
					sendBroadcast(intent);
				}
				

				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	};

	/*--------------------------------------------*/
    // special functions
	private List<HashMap<String, String>> getChatRoomList() {
		List<HashMap<String, String>> chatroomList = new ArrayList<HashMap<String, String>>();

		String xmlURL = Constants.SERVER + Constants.URLPATH_GETDATA
				+ "?table=chat&uid=" + uidLogin;
		// String xmlData = HttpClientConnector.getStringByUrl(xmlURL);
		Log.d(TAG, xmlURL);
		chatroomList = GeneralXmlPullParser.parse(xmlURL);

		return chatroomList;
	}
	


}