package com.golive.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import android.app.Notification;
import android.app.NotificationManager;

import com.golive.R;
import com.golive.GoLiveActivity.TalkroomReceiver;

import static com.golive.util.Constants.*;

public class CommonFunctions implements Serializable {
	static final String TAG = "CommonFunctions";
	
	public Activity activity;
	String usernameLogin;
	String uidLogin;
	
	public void setLoginInfo(String usernameLogin, String uidLogin){
		this.usernameLogin = usernameLogin;
		this.uidLogin = uidLogin;
	}
	
	public CommonFunctions(Activity activity){
		this.activity = activity;
	}
	

    MediaPlayer player;
	public void mediaPlay(MediaPlayer player, String input){
		this.player = player;
		if ( !player.isPlaying() ){
			try {
				player.setDataSource(input);
				player.prepare();
				player.start();
	        } catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void mediaStop(){
		if ( player != null ){
			if ( player.isPlaying() ){
				player.stop();
				player.reset();
			}
		}else{
			System.out.println("NullPoint : MediaPlayer is null. Calling mediaPlay function first.");
		}
	}
	
	public void jump2Activity(Class target, Bundle bundle) {
		Intent intent = new Intent();
    	if( !"".equals(usernameLogin) ){bundle.putString("usernameLogin", usernameLogin);}
    	if( !"".equals(uidLogin) ){bundle.putString("uidLogin", uidLogin);}
		intent.putExtras(bundle);
		intent.setClass(this.activity, target);
		
		this.activity.startActivityForResult(intent,0);
		this.activity.finish();
	}
	
	public void back2Activity() {
		this.activity.setResult(this.activity.RESULT_OK, this.activity.getIntent());
		this.activity.finish();
	}
	public void setInvisibleStatusbar_Titlebar(){
//		final Window win = getWindow();
//		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	public void registerRec(String serviceName, BroadcastReceiver receiver){
		/*
		if( !isMyServiceRunning(serviceName) ){
			Log.d(TAG, "on registerRec");

			IntentFilter filter = new IntentFilter();
			filter.addAction(serviceName);
			this.activity.registerReceiver(receiver, filter);
		}else{
			Log.d(TAG, "Receiver registed"); // without check : no error
		}
		*/

		IntentFilter filter = new IntentFilter();
		filter.addAction(serviceName);
		this.activity.registerReceiver(receiver, filter);
	}
	
	// @Self-Define
	public void unregisterRec(String serviceName, BroadcastReceiver receiver){
		//if( !isMyServiceRunning("com.leo.service.TestService") || receiver==null){
		if( !isMyServiceRunning(serviceName) || receiver==null ){
			Log.d(TAG, "Receiver not registed"); // without check : runtime exception
		}else{
			Log.d(TAG, "on unregisterRec");
			this.activity.unregisterReceiver(receiver);
			receiver = null;

		}
	}
	
	public boolean isMyServiceRunning(String serviceName) {
        ActivityManager manager = (ActivityManager) this.activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        	//Log.d(TAG, service.service.getClassName());
            if (serviceName.equals( service.service.getClassName() )) {
                return true;
            }
        }
        return false;
    }
	
	public void notifier(int NOTIFY_ID,String alarmInfo, String contentTitle, String contentText, int drawable, long when){

        PendingIntent pendingIntent = PendingIntent.getActivity(this.activity, 0, this.activity.getIntent(), Intent.FLAG_ACTIVITY_CLEAR_TOP); 
        Notification notification = new Notification(drawable, alarmInfo, when);
        notification.setLatestEventInfo(this.activity, contentTitle, contentText,  pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)this.activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFY_ID, notification); 
	}
	
	public boolean checkWiFi() {
		WifiManager wifi = (WifiManager) this.activity.getSystemService(Context.WIFI_SERVICE);

		if (wifi.getConnectionInfo().getSSID() != null) {
			Log.d(TAG, "WiFi is enabled AND active !");
			Log.d(TAG, "SSID = " + wifi.getConnectionInfo().getSSID());
			return true;
		} else {
			Log.d(TAG, "NO WiFi");
			return false;
		}
	}
	
	public boolean isConnected() {
		ConnectivityManager conMan = (ConnectivityManager) this.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;

		if (conMan != null) {
			networkInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}
	
	public boolean hasNetwork(){
		ConnectivityManager conMan = (ConnectivityManager) this.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//mobile
		State mobile = conMan.getNetworkInfo(0).getState();
		//wifi
		State wifi = conMan.getNetworkInfo(1).getState();
		
		if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
		    //mobile
			Toast.makeText(this.activity, R.string.three_g, Toast.LENGTH_SHORT).show();
			log(TAG, "mobile");
			return true;
		}else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
		    //wifi
			Toast.makeText(this.activity, R.string.wifi, Toast.LENGTH_SHORT).show();
			log(TAG, "wifi");
			return true;
		}else{
			// no network
			Toast.makeText(this.activity, R.string.nonetwork, Toast.LENGTH_SHORT).show();
			return false;
		}

	}
	
	public int logLv = Constants.NONE;
	public void log(String tag, String msg){
		switch(logLv){
			case Constants.ERROR :
				Log.e(tag, msg);
				break;
			case Constants.INFO : 
				Log.i(tag, msg);
				break;
			case Constants.WARNING : 
				Log.w(tag, msg);
				break;
			case Constants.DEBUG : 
				Log.d(tag, msg);
				break;
			case Constants.VERBOSE : 
				Log.v(tag, msg);
				break;
		}
	}
	

	public boolean writeProp(String input, String file) {
		try {
			// open myfilename.txt for writing
			OutputStreamWriter out = new OutputStreamWriter(activity.openFileOutput(file, 0));
			// write the contents on mySettings to the file
			out.write(input);
			// close the file
			out.close();
			return true;
		} catch (java.io.IOException e) {
			// do something if an IOException occurs.
		}
		return false;
	}

	public String readProp(String file) {
		// try opening the myfilename.txt
		try {
			// open the file for reading
			InputStream instream = activity.openFileInput(file);


			String line;
			StringBuilder text = new StringBuilder();
			// if file the available for reading
			if (instream != null ) {
				// prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);

				while (( line = buffreader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                //System.out.println(text.toString());

			}

			instream.close();
            return text.toString();
			// close the file again
		} catch (java.io.FileNotFoundException e) {
			// do something if the myfilename.txt does not exits
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	

	public String getAvatarPath_L(String input){
		return getAvatarPath(input, "big");
	}
	public String getAvatarPath_M(String input){
		return getAvatarPath(input, "middle");
	}
	public String getAvatarPath_S(String input){
		return getAvatarPath(input, "small");
	}
	private String getAvatarPath(String input,String size){// size : "big", "middle", "small"
		
		Integer intInput = Toolets.getNumber(input);
		if( intInput != null ){
			String ori = String.format("%09d", intInput );
			StringBuilder sb = new StringBuilder();
	        sb.append(ori.substring(0, 3) + "/");
	        sb.append(ori.substring(3, 5) + "/");
	        sb.append(ori.substring(5, 7) + "/");
	        sb.append(ori.substring(7, 9) + "_avatar_"+size+".jpg");
			
			return SERVER + URLPATH_AVATAR + sb.toString();
		}else{
			return null;
		}
	} 

	
}
