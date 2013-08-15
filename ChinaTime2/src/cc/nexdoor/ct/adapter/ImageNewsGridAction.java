package cc.nexdoor.ct.adapter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;
import cc.nexdoor.ct.activity.CTHome;
import cc.nexdoor.ct.activity.GenerNewsItem;
import cc.nexdoor.ct.activity.GenerNewsList;
import cc.nexdoor.ct.activity.ImageNewsFrame;
import cc.nexdoor.ct.activity.ImageNewsList;
import cc.nexdoor.ct.activity.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

public class ImageNewsGridAction {
	// ===========================================================
	// Fields
	// ===========================================================
	String TAG = "CTHome:ImageNewsGridAction";
	LogD log = new LogD(TAG, true);
	private ImageNewsList activity;


	public ImageNewsGridAction(ImageNewsList activity){
		this.activity = activity;
	}

	public void actionRegister(Map<String, Object> args) {
		if ( null != args ) {
			
			News news = (News) args.get("module");
			
			Bundle bundle = new Bundle();
			bundle.putString("title", news.getTitle());
			bundle.putString("desc", news.getDesc());
			bundle.putString("link", news.getLink());
			bundle.putString("date", news.getDate());
			bundle.putString("url", news.getEnclosureURL());
			if ( !"".equals(news.getEnclosureURL()) ) {
				bundle.putByteArray("bitmap", convertBitmapToByteArray(news.getBitmap()) );
			}
			
			jump2Activity( ImageNewsFrame.class, bundle);
			
		}else {
			log.d("null value");
		}
	}
	

	// ===========================================================
	// Methods
	// =========================================================== 

    private byte[] convertBitmapToByteArray(Bitmap src) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		src.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, (OutputStream) os);
		return os.toByteArray();
	}

	private void jump2Activity(Class target, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(activity, target);

		activity.startActivityForResult(intent,0);
	}
	
	private Bundle getSource(String itemKey){
		Bundle bundle = new Bundle();

		
		return bundle;
		
	}
	
	
}
