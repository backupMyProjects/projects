package com.leo.service;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
	private MediaPlayer mMediaPlayer;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (mMediaPlayer != null) {  
            mMediaPlayer.reset();  
            mMediaPlayer.release();  
            mMediaPlayer = null;  
        }  
        mMediaPlayer = new MediaPlayer(); 
        mMediaPlayer.setOnCompletionListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}

	String[] strA;
	int index = 0;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		strA = intent.getStringArrayExtra("strA");
		index = 0;
		playMusic(strA[index]);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if ( index < strA.length-1 ){
			index++;
		}else{
			index = -1;
		}
		if ( index != -1 ){
			playMusic(strA[index]);
		}
		
	}
	
	public void playMusic(String fileUrl) {  
        try {   
        	System.out.println(fileUrl);
            mMediaPlayer.reset();  
            mMediaPlayer.setDataSource(fileUrl);  
            mMediaPlayer.prepare();  
            mMediaPlayer.start(); 
        } catch (IOException e) {
        	e.printStackTrace();
        }  
  
    }  

}
