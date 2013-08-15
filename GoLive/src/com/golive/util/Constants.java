package com.golive.util;


import com.golive.R;

import android.view.View;

public class Constants {
	public static final int VISIBLE = View.VISIBLE;
	public static final int INVISIBLE = View.INVISIBLE;
	public static final int GONE = View.GONE;
	
	public static final String SERVER = "http://twinasia.com";
	//public static final String SERVER = "http://192.168.15.51";
	public static final int PORT = 5000;
	public static final String URLPATH_GETDATA = "/uchome/chat/chat_getData.php";
	public static final String URLPATH_SETCHAT = "/uchome/chat/chat_setChat.php";
	public static final String URLPATH_SETDATA = "/uchome/chat/chat_setData.php";
	public static final String URLPATH_UPLOAD = "/uchome/chat/chat_upload.php";
	public static final String URLPATH_LOGIN = "/uchome/chat/chat_checkuser.php";
	public static final String URLPATH_LOCATION = "/uchome/chat/uploaded/";
	public static final String URLPATH_AVATAR = "/ucenter/data/avatar/";
	
	// print seq : ERROR > INFO > WARNING > DEBUG...
	public static final int VERBOSE = 4;
	public static final int DEBUG = 3;
	public static final int WARNING = 2;
	public static final int INFO = 1;
	public static final int ERROR = 0;
	public static final int NONE = -1;
	
	public static final String LOGIN_FILE = ".com.twgan.login.prop";
	public static final String REGISTER_URL = SERVER + "/uchome/do.php?ac=68e21cf94979787352b83e4e12edae6d";
	public static final String GREAT_URL = SERVER + "/wap";
	
	public static final String[] typeA = {"text", "voice", "photo", "emotion"};
	public static final Integer[] mThumbIds = {
	            R.drawable.e_bouaaaaah, R.drawable.e_confident,
	            R.drawable.e_darkmood, R.drawable.e_high,
	            R.drawable.e_high, R.drawable.e_ignoring,
	            R.drawable.e_indifferent, R.drawable.e_nomnom,
	            R.drawable.e_psychotic, R.drawable.e_scared,
	            R.drawable.e_shy, R.drawable.e_tooserious
	    };
	
}
