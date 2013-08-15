package com.twgan.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.twgan.R;
import com.twgan.activity.ChatRoomList.ActReceiver;
import com.twgan.adapter.ChatListAdapter;
import com.twgan.adapter.ChatroomListAdapter;
import com.twgan.component.CustomizeDialog;
import com.twgan.component.ImageGridDialog;
import com.twgan.service.ChatRoomService;
import com.twgan.service.ChatService;
import com.twgan.utils.*;

import static com.twgan.utils.Constants.*;

public class ChatList extends Activity{
	/** Called when the activity is first created. */
	private final static String TAG = "ChatList";
	public final static String ACTIVITY_NAME = "com.twgan.activity." + TAG;

	String xmlURL = Constants.SERVER + Constants.URLPATH_SETCHAT;

	private CommonFunctions cf = new CommonFunctions(this);
	String usernameLogin;
	String uidLogin;
	String fuidstr;
	String room;
	String typeNow;
	
	private static final int REQ_CODE = 1;

	ChatList cl;
	ListView listViewLayout;
	ActReceiver receiver;
	List<HashMap<String, String>> chatList;

	ProgressDialog dialog;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterRec(ChatService.SERVICE_NAME);
		Log.d(TAG, "destroy it");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		usernameLogin = getIntent().getStringExtra("usernameLogin");
		uidLogin = getIntent().getStringExtra("uidLogin");
		fuidstr = getIntent().getStringExtra("fuidstr");
		room = getIntent().getStringExtra("room");
		Log.d(TAG, "fuidstr:" + fuidstr);
		cf.setLoginInfo(usernameLogin, uidLogin);
		cf.logLv = Constants.DEBUG;

		cf.setInvisibleStatusbar_Titlebar();

		
		setContentView(R.layout.chatlist_list);
		
		listViewLayout = (ListView) this.findViewById(R.id.listViewLayout);
		
		setupBannerLayoutAction();
		setupTextFunctionsLayoutAction();
		setupVoiceFunctionsLayoutAction();
		changeType();

		

		Intent intent = new Intent(ChatList.this,
				com.twgan.service.ChatService.class);
		intent.setAction(this.ACTIVITY_NAME);
		intent.putExtra("usernameLogin", usernameLogin); // message From
															// Activity
		intent.putExtra("uidLogin", uidLogin); // message From Activity
		intent.putExtra("fuidstr", fuidstr); // message From Activity
		intent.putExtra("room", room); // message From Activity
		if (cf.isMyServiceRunning(ChatService.SERVICE_NAME)) {
			stopService(intent);
		}
		startService(intent);
		registerRec(ChatService.SERVICE_NAME);

		cl = this;

	}

	/*--------------------------------------------*/
	// special functions

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private MediaRecorder mRecorder = null;
	private static String mFileName = null;
	private Uri voiceUri = null;
	private void startRecording() {
		voiceUri = this.customizedFilePath("aac");
		mFileName = voiceUri.getLastPathSegment();

		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		mRecorder.setOutputFile(voiceUri.getPath());

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed");
		}
		mRecorder.start();

	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.reset();
		mRecorder.release();
		mRecorder = null;
	}
	
	//-- Take Photo
	private Uri cameraUri = null;
	public void startCamera(View v) {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 自訂照片位置
        cameraUri = this.customizedFilePath("jpg");
        it.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        this.startActivityForResult(it, ChatList.REQ_CODE);
    }

    private Uri customizedFilePath(String subName) {
        String fileName = uidLogin + "_" + fuidstr + "_" + System.currentTimeMillis() + "." + subName;
        File sdDir = Environment.getExternalStorageDirectory();
        File theDir = new File(sdDir, LOCAL_FILE_PATH);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        File picFile = new File(theDir, fileName);
        Uri uri = Uri.fromFile(picFile);
        Log.d(TAG, "指定照片位置：" + uri.getPath());
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQ_CODE) {
            Log.d(TAG, "不是拍照");
            return;
        }
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "拍照失敗");
            return;
        }
        resizePhoto(cameraUri.getPath());
        
        // send message
		// update list
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("fuidstr", fuidstr));
		nameValuePairs.add(new BasicNameValuePair("fuid", uidLogin));
		//nameValuePairs.add(new BasicNameValuePair("said", uri.getLastPathSegment() ));
		nameValuePairs.add(new BasicNameValuePair("said", cameraUri.getLastPathSegment() ));
		nameValuePairs.add(new BasicNameValuePair("type", typeNow));
		nameValuePairs.add(new BasicNameValuePair("room", room));
		nameValuePairs.add(new BasicNameValuePair("dateline", ""+ Calendar.getInstance().getTimeInMillis()));

		// need async task
		HttpClientConnector.postData(xmlURL, nameValuePairs);

		try {
			// need async task
			//(new PostFile()).postFile(uri.getPath());
			(new PostFile()).postFile(cameraUri.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void resizePhoto(String path) {  
    		BitmapFactory.Options options = new BitmapFactory.Options();  
    		options.inJustDecodeBounds = true;  
    		// 获取这个图片的宽和高  
    		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空  
    		options.inJustDecodeBounds = false;  
    		// 计算缩放比  
    		int be = (int)(options.outHeight / (float)1000);  
    		if (be <= 0){be = 1;}
    		options.inSampleSize = 8; // 图片长宽各缩小二分之一  
    		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦  
    		bitmap = BitmapFactory.decodeFile(path, options);  
    		
    		// output
    		//saveJPGE_After(bitmap, path); 
    		File file = new File(path);  
    		try {  
    			FileOutputStream out = new FileOutputStream(file); 
    			boolean compressCheck = false;
    			if ( path.contains(".jpg") ){
    				compressCheck = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
    			}else if( path.contains(".png") ){
    				compressCheck = bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
    			}
    			out.flush();  
    			out.close();
    			
    			
    		} catch (FileNotFoundException e) {  
    			e.printStackTrace();  
    		} catch (IOException e) {  
    			e.printStackTrace();  
    		}  
    	}
	//--

	/*--------------------------------------------*/
	// components setting

	private void setupBannerLayoutAction() {

		final ImageButton bannerFunction = (ImageButton) findViewById(R.id.bannerFunction);
		//bannerFunction.setBackgroundResource(R.drawable.add);
		bannerFunction.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.jump2Activity(Contact.class, new Bundle());
				// --------------------------------//
			}
		});

		TextView bannerTitle = (TextView) findViewById(R.id.bannerTitle);
		Bundle bundle = this.getIntent().getExtras();
		bannerTitle.setText(room);

		final ImageButton bannerBack = (ImageButton) findViewById(R.id.bannerBack);
		bannerBack.setVisibility(VISIBLE);
		bannerBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				bannerBack.setBackgroundResource(R.drawable.back_button_clicked);
				cf.jump2Activity(ChatRoomList.class, new Bundle());
				// cf.back2Activity();
				// --------------------------------//
			}
		});

	}

	
	
	private void changeType(){
		final ImageButton changeTypeIB = (ImageButton) findViewById(R.id.changeTypeIB);
		
		final RelativeLayout chatText = (RelativeLayout) findViewById(R.id.chatText);
		final RelativeLayout chatVoice = (RelativeLayout) findViewById(R.id.chatVoice);
		final RelativeLayout chatPhoto = (RelativeLayout) findViewById(R.id.chatPhoto);
		
		chatText.setVisibility(VISIBLE);
		typeNow = "text";
		
		changeTypeIB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// change type
				showdialog();
				// --------------------------------//
			}
			
			Dialog listDialog;
		     
		     private void showdialog(){
		      listDialog = new Dialog(ChatList.this);
		      listDialog.setTitle("Select Type");
		       LayoutInflater li = (LayoutInflater) ChatList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		       View v = li.inflate(R.layout.list, null, false);
		       listDialog.setContentView(v);
		       listDialog.setCancelable(true);
		          //there are a lot of settings, for dialog, check them all out!
		  
		          ListView lv = (ListView) listDialog.findViewById(R.id.listview);
		          lv.setOnItemClickListener(new OnItemClickLsr());
		          lv.setAdapter(new ArrayAdapter<String>(ChatList.this,android.R.layout.simple_list_item_1, typeA));
		          //now that the dialog is set up, it's time to show it
		          listDialog.show();
		     }
		     
		     class OnItemClickLsr implements OnItemClickListener{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Log.d(TAG, typeA[arg2]);
					typeNow = typeA[arg2];

					if ( typeNow == "text" ){
						chatText.setVisibility(VISIBLE);
						chatVoice.setVisibility(GONE);
						chatPhoto.setVisibility(GONE);
					}else if ( typeNow == "voice" ){
						chatText.setVisibility(GONE);
						chatVoice.setVisibility(VISIBLE);
						chatPhoto.setVisibility(GONE);
					}else if ( typeNow == "photo" ){
						chatText.setVisibility(GONE);
						chatVoice.setVisibility(GONE);
						chatPhoto.setVisibility(VISIBLE);
					}else if ( typeNow == "emotion" ){
						setupEmotionFunctionLayoutAction();
					}
					listDialog.cancel();
				}
		    	 
		     }     
			
		});
	}
	
	private void setupTextFunctionsLayoutAction() {

		final EditText sayingTV = (EditText) findViewById(R.id.sayingTV);

		ImageButton sendIB = (ImageButton) findViewById(R.id.sendIB);
		sendIB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// send message
				// update list
				typeNow = "text";
				cf.log(TAG, "WHAT YOU SAY " + sayingTV.getText().toString());
				byte[] utf8Bytes = null;
				String roundTrip = null;
				try {
					utf8Bytes = sayingTV.getText().toString().getBytes("UTF8");
					roundTrip = new String(utf8Bytes, "UTF8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				cf.log(TAG, "SAYING : " + roundTrip);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("fuidstr", fuidstr));
				nameValuePairs.add(new BasicNameValuePair("fuid", uidLogin));
				nameValuePairs.add(new BasicNameValuePair("said", sayingTV
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("type", typeNow));
				nameValuePairs.add(new BasicNameValuePair("room", room));
				nameValuePairs.add(new BasicNameValuePair("dateline", ""
						+ Calendar.getInstance().getTimeInMillis()));

				//need async task
				HttpClientConnector.postData(xmlURL, nameValuePairs);
				// clean text
				sayingTV.setText("");
				// --------------------------------//
			}
		});

	}

	private void setupVoiceFunctionsLayoutAction() {

		final Button sendB = (Button) findViewById(R.id.sendB);
		sendB.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// --------------------------------//
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					sendB.setBackgroundResource(R.drawable.logout);
					sendB.setText(R.string.chat_voicebutton_clicked);
					startRecording();
					break;
				case MotionEvent.ACTION_UP:
					sendB.setBackgroundResource(R.drawable.big_button);
					sendB.setText(R.string.chat_voicebutton);
					stopRecording();

					Log.d(TAG, mFileName);

					// send message
					// update list
					typeNow = "voice";
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("fuidstr",
							fuidstr));
					nameValuePairs
							.add(new BasicNameValuePair("fuid", uidLogin));
					nameValuePairs
							.add(new BasicNameValuePair("said", mFileName));
					nameValuePairs.add(new BasicNameValuePair("type", typeNow));
					nameValuePairs.add(new BasicNameValuePair("room", room));
					nameValuePairs.add(new BasicNameValuePair("dateline", ""
							+ Calendar.getInstance().getTimeInMillis()));

					//need async task
					HttpClientConnector.postData(xmlURL, nameValuePairs);

					try {
						//need async task
						(new PostFile()).postFile(voiceUri.getPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				// --------------------------------//
				return false;
			}
		});

	}
	
	ImageGridDialog imageGridDialog;
	private void setupEmotionFunctionLayoutAction(){
		imageGridDialog = new ImageGridDialog(this);
		imageGridDialog.setTitle("");
	    imageGridDialog.show();
	}
	
	public void imageGridItemHandler(int resID){
		Log.d(TAG, "" + resID);
		imageGridDialog.cancel();
		// send message
		// update list
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("fuidstr", fuidstr));
		nameValuePairs.add(new BasicNameValuePair("fuid", uidLogin));
		nameValuePairs.add(new BasicNameValuePair("said", ""+resID));
		nameValuePairs.add(new BasicNameValuePair("type", typeNow));
		nameValuePairs.add(new BasicNameValuePair("room", room));
		nameValuePairs.add(new BasicNameValuePair("dateline", "" + Calendar.getInstance().getTimeInMillis()));

		//need async task
		HttpClientConnector.postData(xmlURL, nameValuePairs);
	}
	
	/*
	private String generateFileName(String uName, String subName) {
		return uName + "_" + (1 + (int) (Math.random() * 100000)) + subName;
	}
	*/
	
	

	// Inner Class : Receiver
	// @Self-Define
	public class ActReceiver extends BroadcastReceiver {
		private final String TAG = "ActReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "OnReceiver");

			Object obj = intent.getSerializableExtra("chatList");// message From Service
			chatList = (List<HashMap<String, String>>) obj;
			// Toolets.printHashMapList(chatList);
			ChatListAdapter adapter = new ChatListAdapter(cl, chatList);
			listViewLayout.setAdapter(adapter);

			// dialog.cancel();
		}

		public ActReceiver() {
			Log.i(TAG, "init ActReceiver");

		}
	}

	// Other Functions
	// @Self-Define
	private void registerRec(String actionName) {
		if (receiver == null) {
			Log.d(TAG, "on registerRec");
			receiver = new ActReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(actionName);
			this.registerReceiver(receiver, filter);
		} else {
			Log.d(TAG, "Receiver registed"); // without check : no error
		}
	}

	// @Self-Define
	private void unregisterRec(String serviceName) {
		if (!cf.isMyServiceRunning(serviceName) || receiver == null) {
			Log.d(TAG, "Receiver not registed"); // without check : runtime
													// exception
		} else {
			Log.d(TAG, "on unregisterRec");
			this.unregisterReceiver(receiver);
			receiver = null;
		}
	}

	/*
	private boolean isMyServiceRunning(String serviceName) {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			// Log.d(TAG, service.service.getClassName());
			if (serviceName.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	*/

}
