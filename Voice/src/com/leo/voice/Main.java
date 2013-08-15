package com.leo.voice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.leo.android.util.*;
import com.leo.android.util.task.*;
import com.leo.android.util.interfaces.UtilActivity;
import com.leo.service.MusicService;

import static com.leo.android.util.Constants.*;
import static com.leo.util.Constants.*;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Main extends TabActivity implements UtilActivity {
	CommonFunction cf = new CommonFunction(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void init(int setLayoutId) {
		setContentView(setLayoutId);
		cf.logLv = DEBUG;
		
		setTabBar();
		
		findViews();
		setupViews();
		
		getVoiceDataByGroup();
	}
	
	private void setTabBar() {
		// 取得Tabhost參考
		//TabHost tabHost = getTabHost();

		// 將xml轉換為View且指定給TabContentView
		LayoutInflater.from(this).inflate(R.layout.inside_layout,
				getTabHost().getTabContentView(), true);

		getTabHost().addTab(getTabHost().newTabSpec("battery_tab2")
				.setIndicator("Play")
				.setContent(R.id.rl_two));

		// 設定各tab頁面by指定View id
		getTabHost().addTab(getTabHost().newTabSpec("battery_tab1")
				.setIndicator("Record")
				.setContent(R.id.rl_one));

	}

	Button btn_record, btn_play;
	TextView tv_play;
	@Override
	public void findViews() {
		btn_record = (Button) findViewById(R.id.btn_record);
		btn_play = (Button) findViewById(R.id.btn_play);
		tv_play = (TextView) findViewById(R.id.tv_play);
	}
	
	int state = 0;
	@Override
	public void setupViews() {
		btn_record.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/* -------------------------------- */
				if ( state == 0 ){
					startRecording();
					btn_record.setText("Stop Recording & Upload");
					state = 1;
				}else if( state == 1 ){
					stopRecording();
					String uploadInfo = voiceUri.getPath();
					uploadFile(true);
					setVoiceData(uploadInfo);
					btn_record.setText("Start Record Voice");
					
					state = 0;
				}
				/* -------------------------------- */
			}
		});
		
		btn_play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/* -------------------------------- */
				getVoiceDataByGroup();
				/* -------------------------------- */
			}
		});
		
	}
	
	ProgressDialog dialogUpload;
	void uploadFile(final boolean removeFile){
		(new HTTPPostFileTask(this, cf, UPLOAD_URL, voiceUri.getPath()){
			//protected ProgressDialog dialog;
			@Override
			protected void onPreExecute() {
				dialogUpload = ProgressDialog.show(this.activity, "", "Uploading..." , true, true);
			}
			@Override
			protected void onPostExecute(String result) {
				//if ( dialog.isShowing() ){dialog.cancel();}
				cf.log("Result : "+result);
				
				if ( removeFile ){
					File file = new File(voiceUri.getPath());
					boolean deleted = file.delete();
				}
			}
		}).execute(new ArrayList<NameValuePair>());
		
	}
	@SuppressWarnings("unchecked")
	private void setVoiceData(String uploadInfo){
		if (cf.hasNetwork()) {
			// --------
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("action", "addRecord"));
			nameValuePairs.add(new BasicNameValuePair("group_id", "1"));
			nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
			nameValuePairs.add(new BasicNameValuePair("file_name", (!Toolets.isEmpStr(uploadInfo) ? uploadInfo.substring(uploadInfo.lastIndexOf("/")+1, uploadInfo.length()) : "") ));
			(new HTTPPostTask(this, cf, SETDATA_URL){
				@Override
				protected void onPostExecute(String result) {
					if ( dialogUpload.isShowing() ){ dialogUpload.cancel(); }
					//cf.log("Get Result String : " + result);
					//-- UI update
					//--
				}
				
			}).execute(nameValuePairs);
			// --------
		}
	}
	
	ProgressDialog dialogDownload;
	ArrayList<HashMap<String, String>> resultList;
	@SuppressWarnings("unchecked")
	private void getVoiceDataByGroup(){
		if (cf.hasNetwork()) {
			// --------
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("action", "getRecordbyGroup"));
			nameValuePairs.add(new BasicNameValuePair("group_id", "1"));
			nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
			(new HTTPPostTask(this, cf, GETDATA_URL){
				@Override
				protected void onPreExecute() {
					dialogDownload = ProgressDialog.show(this.activity, "", "Loading..." , true, true);
				}
				@Override
				protected void onPostExecute(String result) {
					if ( dialogDownload.isShowing() ){ dialogDownload.cancel(); }
					
					//cf.log("Get Result String : " + result);
					resultList = GeneralXmlPullParser.parse( result );
					//Toolets.printHashMapList(resultList);
					if ( resultList != null ){
						downloadFile(resultList);
					}
				}
				
			}).execute(nameValuePairs);
			// --------
		}
	}
	
	ProgressDialog dialogDownloadFile;
	void downloadFile(ArrayList<HashMap<String, String>> resultList){
		
		(new BatchDownloadFileTask(this, cf, resultList, "file_name", MEDIA_URL, MEDIA_LOCAL){
			//protected ProgressDialog dialog;
			@Override
			protected void onPreExecute() {
				dialogDownloadFile = ProgressDialog.show(this.activity, "", "Downloading File..." , true, true);
			}
			@Override
			protected void onPostExecute(Boolean result) {
				if ( dialogDownloadFile.isShowing() ){dialogDownloadFile.cancel();}
				if ( result ){
					//-- UI Update
					//--
					System.out.println("Done it.");
					playFile_Service(resultList);
				}
			}
		}).execute(new ArrayList<NameValuePair>());
	}
	
	void playFile_Service(final ArrayList<HashMap<String, String>> resultList){
		final String[] strA = new String[resultList.size()];
		Iterator<HashMap<String, String>> ita = resultList.iterator();
		String tempStr = "";
		for ( int i = 0 ; ita.hasNext() ; i++ ){
			String fileNameString = ita.next().get("file_name");
			strA[i] = MEDIA_LOCAL + fileNameString;
			
			tempStr += fileNameString + "\n";
		}
		tv_play.setText(tempStr);
		
		Intent intent = new Intent();  
        intent.setClass(getApplicationContext(), MusicService.class);
        intent.putExtra("strA", strA);
        startService(intent);  
        
	}
	

	private MediaRecorder mRecorder = null;
	private static String mFileName = null;
	private Uri voiceUri = null;
	private void startRecording() {
		voiceUri = this.customizedFilePath("3gp");
		mFileName = voiceUri.getLastPathSegment();

		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setOutputFile(voiceUri.getPath());
		//cf.log("File Local Path : "+voiceUri.getPath());

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			cf.log("prepare() failed");
		}
		mRecorder.start();

	}
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.reset();
		mRecorder.release();
		mRecorder = null;
	}
	
	String LOCAL_FILE_PATH = "voice";
	private Uri customizedFilePath(String subName) {
        String fileName = System.currentTimeMillis() + "." + subName;
        File sdDir = Environment.getExternalStorageDirectory();
        File theDir = new File(sdDir, LOCAL_FILE_PATH);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        File picFile = new File(theDir, fileName);
        Uri uri = Uri.fromFile(picFile);
		cf.log("位置：" + uri.getPath());
        return uri;
    }



	

}
