package com.twgan.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import serialize.SerializationUtils;

import com.twgan.R;
import com.twgan.activity.ChatList;
import com.twgan.activity.ChatRoomList;
import com.twgan.adapter.ChatroomListAdapter;
import com.twgan.utils.Constants;
import com.twgan.utils.GeneralXmlPullParser;
import com.twgan.utils.HttpClientConnector;
import com.twgan.utils.Toolets;

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

public class ChatService extends Service {
	// Init Var
	private final static String TAG = "ChatService";
	public final static String SERVICE_NAME = "com.twgan.service." + TAG;

	public boolean ChatRunning = false;
	public boolean ChatroomRunning = false;
	ServiceReceiver serviceReceiver;

	String usernameLogin;
	String uidLogin;

	String fuidstr;
	
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

		serviceReceiver = new ServiceReceiver();

		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "Services onStartCommand");

		usernameLogin = intent.getStringExtra("usernameLogin");
		uidLogin = intent.getStringExtra("uidLogin");
		fuidstr = intent.getStringExtra("fuidstr");

		ChatRunning = true;
		new ChatServiceThread().start();
		//ChatroomRunning = true;
		//new ChatroomServiceThread().start();

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
		ChatRunning = false;
		ChatroomRunning = false;

		// unregisterReceiver(serviceReceiver);

		super.onDestroy();

	}

	// Inner Class : Receiver
	// @Self-Define
	public class ServiceReceiver extends BroadcastReceiver {
		private final String TAG = "ChatService.ServiceReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "OnReceiver");
			Bundle bundle = intent.getExtras();
		}

		public ServiceReceiver() {
			Log.i(TAG, "init ServiceReceiver");
		}
	}

	HashMap<String, String> savedOne;
	HashMap<String, String> lastOne;

	// Inner Class : Thread
	// @Self-Define
	public class ChatServiceThread extends Thread {

		int originalSize;
		
		public void run() {
			while (ChatRunning) {

				// do job
				List<HashMap<String, String>> chatList = getChatList(fuidstr);

				if (chatList != null & chatList.size() > 0) {
					// Toolets.printHashMapList(chatList);
					lastOne = chatList.get(chatList.size() - 1);
					// Toolets.printHashMap(lastOne);
					String datelineLastOne = lastOne.get("dateline");
					String datelineSavedOne = (savedOne != null) ? savedOne.get("dateline") : "";
					// System.out.println(datelineLastOne + ":" +
					// datelineSavedOne);
					if (savedOne == null || !datelineLastOne.equals(datelineSavedOne) || originalSize != chatList.size()) {
						originalSize = chatList.size();
						Object obj = null;
						try {
							obj = SerializationUtils.clone((Serializable) chatList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// send data to UI thread
						Intent intent = new Intent();
						intent.putExtra("chatList", (Serializable) obj); // message From Service
						intent.setAction(SERVICE_NAME);// action與接收器相同
						sendBroadcast(intent);

						savedOne = chatList.get(chatList.size() - 1); // init or update save it
					}

				}

				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	/*--------------------------------------------*/
	// special functions
	@SuppressWarnings("unchecked")
	private List<HashMap<String, String>> getChatList(String fuidstr) {
		List<HashMap<String, String>> chatList = new ArrayList<HashMap<String, String>>();

		String xmlURL = Constants.SERVER + Constants.URLPATH_GETDATA
				+ "?table=chat&fuidstr=" + fuidstr;
		Log.d(TAG, xmlURL);
		// String xmlData = HttpClientConnector.getStringByUrl(xmlURL);
		chatList = GeneralXmlPullParser.parse(xmlURL);
		// Toolets.printHashMapList(chatList);

		return chatList;
	}

	// @Self-Define
	public class ChatroomServiceThread extends Thread {

		String savedDateLine;
		String lastDateLine;

		public void run() {
			while (ChatroomRunning) {

				// do job
				List<HashMap<String, String>> chatroomList = getChatRoomList();
				if (chatroomList != null & chatroomList.size() > 0) {
					HashMap<String, String> lastOne = chatroomList.get(0);
					lastDateLine = lastOne.get("dateline");
					Log.d(TAG, savedDateLine + ":" + lastDateLine);
					if (savedDateLine != null
							&& !savedDateLine.equals(lastDateLine)) {
						Log.d(TAG, savedDateLine + "!=" + lastDateLine);
						notifier = true;
					} else {
						notifier = false;
					}
					savedDateLine = lastDateLine;
				}

				//Object obj = SerializationUtils.clone((Serializable) chatroomList);
				//Intent intent = new Intent();
				//intent.putExtra("chatroomList", (Serializable) obj);
				//intent.putExtra("notifier", notifier);
				//intent.putExtra("savedDateLine", savedDateLine);
				//intent.putExtra("lastDateLine", lastDateLine);
				//intent.setAction(SERVICE_NAME);// action與接收器相同
				//sendBroadcast(intent);

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