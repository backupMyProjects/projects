package cc.nexdoor.ct.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.nexdoor.android.module.LogD;
import cc.nexdoor.ct.activity.CTHome;
import cc.nexdoor.ct.activity.GenerNewsList;
import cc.nexdoor.ct.activity.ImageNewsList;
import cc.nexdoor.ct.activity.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ModuleMenuGridAction {
	// ===========================================================
	// Fields
	// ===========================================================
	String TAG = "CTHome:GridAction";
	LogD log = new LogD(TAG, true);
	private CTHome activity;
	private ArrayList channelAttributesList;
	private ArrayList channelContentsList;


	public ModuleMenuGridAction(CTHome activity){
		this.activity = activity;
		this.channelAttributesList = activity.channelAttributesList;
		this.channelContentsList = activity.channelContentsList;
	}

	public void actionRegister(Map<String, Object> args) {
		if ( null != args ) {
			
			int target = (Integer) args.get("module");

			switch(target){
			case R.string.focusnews:
				log.d("click focusnews");
				jump2Activity(GenerNewsList.class, "focusnews", target);
				break;
			case R.string.camera:
				log.d("click camera");
//				jump2Activity(GenerNewsList.class, "camera", target);
				jump2Activity(ImageNewsList.class, "camera", target);
				break;
			case R.string.video:
				log.d("click video");
				jump2Activity(GenerNewsList.class, "video", target);
				break;
			case R.string.ctitv:
				log.d("click ctitv");
				jump2Activity(GenerNewsList.class, "ctitv", target);
				break;
			case R.string.news:
				log.d("click news");
				jump2Activity(GenerNewsList.class, "news", target);
				break;
			case R.string.showbiz:
				log.d("click showbiz");
				jump2Activity(GenerNewsList.class, "showbiz", target);
				break;
			case R.string.life:
				log.d("click life");
				jump2Activity(GenerNewsList.class, "life", target);
				break;
			case R.string.money:
				log.d("click money");
				jump2Activity(GenerNewsList.class, "money", target);
				break;
			case R.string.blog:
				log.d("click blog");
				jump2Activity(GenerNewsList.class, "blog", target);
				break;
			case R.string.ctv:
				log.d("click ctv");
				jump2Activity(GenerNewsList.class, "ctv", target);
				break;
			default:
				log.d("position ["+args.get("logInfo")+"] : "+args.get("module"));
			}
			
		}else {
			log.d("null value");
		}
	}
	

	void jump2Activity(Class target, String itemKey, int targetTitle) {
		Intent intent = new Intent();
		intent.setClass(activity, target);

		Bundle bundle = getSource(itemKey);
		log.d("targetTitle "+targetTitle);
		bundle.putInt("targetTitle", targetTitle);
		intent.putExtras(bundle);

		activity.startActivityForResult(intent,0);
	}
	
	private Bundle getSource(String itemKey){
		Bundle bundle = new Bundle();
		for (int i = 0; i < channelContentsList.size(); i++) {
			HashMap<String, String> hm = (HashMap) channelContentsList.get(i);
			for (String key : hm.keySet()) {
//				log.d(key + " : " + hm.get(key));
				if( key.startsWith(itemKey) ){
					bundle.putString(key, hm.get(key));
//					log.d(key + " : " + hm.get(key));
				}
			}
		}
		
		return bundle;
		
	}
	
	
}
