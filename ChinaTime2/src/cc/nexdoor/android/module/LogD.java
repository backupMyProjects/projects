package cc.nexdoor.android.module;

import android.util.Log;

public class LogD {
	String tag;
	boolean print = false;
	
	public LogD (String tag, boolean print){
		this.tag = tag;
		this.print = print;
	}
	
	public void d (Object info) {
		if(print){
			Log.d(tag, info.toString());
		}
	}
	
	public void d (String TAG ,Object info) {
		if(print){
			Log.d(TAG, info.toString());
		}
	}
	
}
