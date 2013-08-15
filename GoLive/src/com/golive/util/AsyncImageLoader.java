package com.golive.util;


import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {
	final String TAG = "AsyncImageLoader";
	private Map<String, SoftReference<Drawable>> imageCache=new HashMap<String, SoftReference<Drawable>>();
	
	public Drawable loadDrawable(final String imageUrl,final ImageCallback callback){
		//if(imageCache.containsKey(imageUrl)){
		//	SoftReference<Drawable> softReference = imageCache.get(imageUrl);
		//	if(softReference.get() != null){
		//		return softReference.get();
		//	}
		//}
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Drawable) msg.obj, imageUrl);
			}
		};
		new Thread(){
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				handler.sendMessage(handler.obtainMessage(0,drawable));
			};
		}.start();
		return null;
	}
	
	protected Drawable loadImageFromUrl(String imageUrl) {
		try {
			Log.d(TAG, "loadImageFromUrl:"+imageUrl);
			return Drawable.createFromStream(new URL(imageUrl).openStream(), "src");
		} catch (java.io.FileNotFoundException e){
			// ToDo : 
			// prepare a default image object to return.
			return null;
		} catch (Exception e) {
			// ToDo : 
			// prepare a default image object to return.
			return null;
			//throw new RuntimeException(e);
		}
	}

	public interface ImageCallback{
		// setup the callback function for users requirement.
		public void imageLoaded(Drawable imageDrawable,String imageUrl);
	}
}
