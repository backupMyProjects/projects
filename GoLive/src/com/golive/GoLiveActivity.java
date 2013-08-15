package com.golive;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;

import java.util.*;

import com.golive.adapter.*;
import com.golive.service.ChatService;
import com.golive.service.TalkRoomService;
import com.golive.util.CommonFunctions;
import com.golive.widget.TabContainer;


public class GoLiveActivity extends Activity {
	private final static String TAG = "GoLiveActivity";
	public final static String ACTIVITY_NAME = "com.golive."+TAG;
	
	TabContainer tc;
	private Button talkIB, contactIB, findIB, settingIB;
	private LinearLayout talkLL, contactLL, findLL, settingLL;
	
	public int layoutID;
	String usernameLogin, uidLogin;
	ProgressDialog dialog;
	CommonFunctions cf = new CommonFunctions(this);
	GoLiveActivity ga;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int mainlayoutID = R.layout.main;
        setContentView(mainlayoutID);
        
        ga = this;

        tc = (TabContainer) findViewById(R.id.TabContainer);
        if( mainlayoutID == R.layout.main ){initComponent();}
        
    }
    
    
    // Level 1 : init Component
    void initComponent(){
    	usernameLogin = getIntent().getStringExtra("usernameLogin");
        uidLogin = getIntent().getStringExtra("uidLogin");
        
        usernameLogin = "maleo";
        uidLogin = "19983";
		
		findComponent();
		setComponent();
		
		talkRoomService();
        
	}
    
    
    // Level 2 : find & set Component
    void findComponent(){
		talkIB    = tc.talkIB;
		contactIB = tc.contactIB;
		findIB    = tc.findIB;
		settingIB = tc.settingIB;
		
		talkLL    = tc.talkLL;
		contactLL = tc.contactLL;
		findLL    = tc.findLL;
		settingLL = tc.settingLL;
	}
    
    void setComponent(){
    		TabContainerController tcc = new TabContainerController(this);
		// OnClick
		talkIB.setOnClickListener(tcc);
		contactIB.setOnClickListener(tcc);
		findIB.setOnClickListener(tcc);
		settingIB.setOnClickListener(tcc);
		talkLL.setOnClickListener(tcc);
		contactLL.setOnClickListener(tcc);
		findLL.setOnClickListener(tcc);
		settingLL.setOnClickListener(tcc);

		setLayout(R.layout.layout_talkroom);

		talkIB.setBackgroundResource(R.drawable.ico_talk_act);
		talkLL.setBackgroundResource(R.drawable.back_tab_click);
		
		
		
	}

    void setLayout(int layoutID){
    		this.layoutID = layoutID;
    		
    		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
		
		contentLayout.removeAllViews();
		contentLayout.addView( layoutInflater.inflate(layoutID, new android.widget.RelativeLayout(this) ) );
			
    }

	// Level 3 : view / action Controller
    class TabContainerController implements OnClickListener{
    	GoLiveActivity activity;
    		
    		public TabContainerController(GoLiveActivity activity){
    			this.activity = activity;
    		}

		@Override
		public void onClick(View v) {
			viewController(v);
			actionController();
		}
		
	    void viewController(View v){
	    		// button view
			tc.onClick(v);
			// layout view
			if ( v.getId() == talkIB.getId() || v.getId() == talkLL.getId() ){
				setLayout(R.layout.layout_talkroom);
			}else if(  v.getId() == contactIB.getId() || v.getId() == contactLL.getId()  ){
				setLayout(R.layout.layout_contact);
			}else if(  v.getId() == findIB.getId() || v.getId() == contactLL.getId()  ){
				setLayout(R.layout.layout_find);
			}else if(  v.getId() == settingIB.getId() || v.getId() == settingLL.getId()  ){
				setLayout(R.layout.layout_setting);
			}
	    	
	    }
	    
	    void actionController(){
			talkRoomService();
			talkService();
	    }
		
		
	}
    

    void talkRoomService(){
    		if( layoutID == R.layout.layout_talkroom ){
    			//dialog = ProgressDialog.show(GoLiveActivity.this, this.getString(R.string.dialog_loading_title), this.getString(R.string.dialog_loading_msg), true, true);
			Intent intent = new Intent(GoLiveActivity.this, com.golive.service.TalkRoomService.class);
			intent.setAction(this.ACTIVITY_NAME);
			intent.putExtra("uidLogin", uidLogin); // message From Activity
			intent.putExtra("usernameLogin", usernameLogin); // message From Activity
			if (cf.isMyServiceRunning(TalkRoomService.SERVICE_NAME)){
			    stopService(intent);
			}
		    startService(intent);
		    cf.registerRec(TalkRoomService.SERVICE_NAME, new TalkroomReceiver());
		}
    }
    
    void talkService(){
		if( layoutID == R.layout.layout_talk ){
			Intent intent = new Intent(GoLiveActivity.this, com.golive.service.ChatService.class);
			intent.setAction(this.ACTIVITY_NAME);
			intent.putExtra("usernameLogin", usernameLogin); // message From
																// Activity
			intent.putExtra("uidLogin", uidLogin); // message From Activity
			//intent.putExtra("fuidstr", fuidstr); // message From Activity
			//intent.putExtra("room", room); // message From Activity
			intent.putExtra("fuidstr", "19983"); // message From Activity
			intent.putExtra("room", "19983"); // message From Activity
			if (cf.isMyServiceRunning(ChatService.SERVICE_NAME)) {
				stopService(intent);
			}
			startService(intent);
			cf.registerRec(ChatService.SERVICE_NAME, new ActReceiver());
		}
    }
    
 // Inner Class : Receiver

	List<HashMap<String, String>> talkroomList;
    // @Self-Define
	public class TalkroomReceiver extends BroadcastReceiver {
		public final static String TAG = "ActReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "OnReceiver");
			
			Object obj = intent.getSerializableExtra("talkroomList");// message From Service
			talkroomList = (List<HashMap<String, String>>) obj;
			if (talkroomList != null && talkroomList.size() > 0){
				String lastDateLine = intent.getStringExtra("lastDateLine");
				String savedDateLine = intent.getStringExtra("savedDateLine");
				boolean newmessage_notifier = intent.getBooleanExtra("newmessage_notifier", false);
				Log.d(TAG, lastDateLine + "========" + savedDateLine + "========" + newmessage_notifier);
				if (newmessage_notifier){ 
					cf.notifier(12345, ga.getString(R.string.notifier_alarm), ga.getString(R.string.app_name) , ga.getString(R.string.notifier_msg), R.drawable.ic_launcher, System.currentTimeMillis()); 
					newmessage_notifier = false;
				}
				
				if ( layoutID == R.layout.layout_talkroom  ){
					ChatroomListAdapter adapter = new ChatroomListAdapter(ga, talkroomList);
					ListView listViewLayout = (ListView) findViewById(R.id.listViewLayout);
					listViewLayout.setAdapter(adapter);
				}
				
				
			}else{
				if ( layoutID == R.layout.layout_talkroom  ){
					Toast.makeText(cf.activity, "您沒有任何對話", Toast.LENGTH_SHORT).show();
				}
			}
			//dialog.cancel();
			
			//cf.notifier(12345, "New message here!", "ChatRoomList" , "You got new message wthin a new chat room.", R.drawable.add, System.currentTimeMillis());
		}

		public TalkroomReceiver() {
			Log.d(TAG, "init TalkroomReceiver");
		}
	}
	
	// Inner Class : Receiver
	List<HashMap<String, String>> talkList;
		// @Self-Define
		public class ActReceiver extends BroadcastReceiver {
			private final String TAG = "ActReceiver";

			@Override
			public void onReceive(Context context, Intent intent) {
				Log.i(TAG, "OnReceiver");

				Object obj = intent.getSerializableExtra("chatList");// message From Service
				talkList = (List<HashMap<String, String>>) obj;
				// Toolets.printHashMapList(chatList);
				ChatListAdapter adapter = new ChatListAdapter(ga, talkList);
				ListView listViewLayout = (ListView) findViewById(R.id.listViewLayout);
				listViewLayout.setAdapter(adapter);

				// dialog.cancel();
			}

			public ActReceiver() {
				Log.i(TAG, "init ActReceiver");

			}
		}
	

	

    
    
	
}