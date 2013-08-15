package com.golive.service;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import serialize.SerializationUtils;

import com.golive.R;
import com.golive.GoLiveActivity;
import com.golive.adapter.ChatroomListAdapter;
import com.golive.util.*;

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
import android.widget.*;


public class TalkRoomService extends Service {
// Init Var
	public final static String TAG = "TalkRoomService";
	public final static String SERVICE_NAME = "com.golive.service."+TAG;
	
	public boolean isRunning = true;
	TalkroomServiceReceiver serviceReceiver;

	String usernameLogin;
	String uidLogin;
	

// Ref extends : Service
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return null;
	}
	
	@Override
	public void onCreate() {

		Log.i(TAG, "Services onCreate");

		serviceReceiver = new TalkroomServiceReceiver();
	    
		super.onCreate();

	}
	
	@Override
	 public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "Services onStartCommand");
		
		usernameLogin = intent.getStringExtra("usernameLogin");
		uidLogin = intent.getStringExtra("uidLogin");
		 
		//isRunning = true;
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
	public class TalkroomServiceReceiver extends BroadcastReceiver {
		private final String TAG = "TalkRoomService.ServiceReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "OnReceiver");
		}

		public TalkroomServiceReceiver() {
			Log.i(TAG, "init ServiceReceiver");
		}
	}

// Inner Class : Thread	
	// @Self-Define
	public class ChatroomServiceThread extends Thread {

		String savedDateLine;
		String lastDateLine;
		int originalSize;
		boolean newmessage_notifier = false;
		boolean send_notifier = true; // for first time
		
		public void run() {
			while (isRunning) {
				
				// do job
				List<HashMap<String, String>> talkroomList = getTalkRoomList();
				
				Intent intent = new Intent();
				intent.setAction(SERVICE_NAME);
				if (talkroomList != null && talkroomList.size() > 0){
					HashMap<String, String> lastOne = talkroomList.get(0);
					lastDateLine = lastOne.get("dateline");
					Log.d(TAG, savedDateLine + ":" + lastDateLine);
					if ( savedDateLine != null && !savedDateLine.equals( lastDateLine ) || originalSize != talkroomList.size() ){
						newmessage_notifier = true;
						send_notifier = true;
					}else{newmessage_notifier = false;}
					
					originalSize = talkroomList.size();
					savedDateLine = lastDateLine;
					
					Object obj = null;
					try {
						obj = SerializationUtils.clone((Serializable) talkroomList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					intent.putExtra("talkroomList", (Serializable)obj); // message From Service
					intent.putExtra("newmessage_notifier", newmessage_notifier);
					intent.putExtra("savedDateLine", savedDateLine);
					intent.putExtra("lastDateLine", lastDateLine);
				}
				if(send_notifier){
					sendBroadcast(intent);
					send_notifier = false;
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
	private List<HashMap<String, String>> getTalkRoomList() {
		List<HashMap<String, String>> talkroomList = new ArrayList<HashMap<String, String>>();

		String xmlURL = Constants.SERVER + Constants.URLPATH_GETDATA
				+ "?table=chat&uid=" + uidLogin;
		// String xmlData = HttpClientConnector.getStringByUrl(xmlURL);
		Log.d(TAG, xmlURL);
		talkroomList = GeneralXmlPullParser.parse(xmlURL);

		return talkroomList;
	}
	


}