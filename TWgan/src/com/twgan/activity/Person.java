package com.twgan.activity;

import static com.twgan.utils.Constants.*;
import static com.twgan.utils.Toolets.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.*;
import android.widget.*;
import android.content.*;
import android.graphics.drawable.Drawable;
import android.util.*;
import android.view.*;
import android.os.*;

import com.twgan.task.*;
import com.twgan.utils.*;
import com.twgan.utils.AsyncImageLoader.ImageCallback;
import com.twgan.R;

public class Person extends Activity {
	private final static String TAG = "FindFriend";
	public final static String ACTIVITY_NAME = "com.twgan.activity." + TAG;
	String usernameLogin;
	String uidLogin;
	CommonFunctions cf = new CommonFunctions(this);
	String backTarget;
	String username, uid, resideprovince, residecity, spacenote, fuidstr, chatroomName;
	
	Person person;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cf.logLv = DEBUG;
        
        usernameLogin = getIntent().getStringExtra("usernameLogin");
        uidLogin = getIntent().getStringExtra("uidLogin");
        cf.setLoginInfo(usernameLogin, uidLogin);
        backTarget = getIntent().getStringExtra("backTarget");
        uid = getIntent().getStringExtra("uid");
        username = getIntent().getStringExtra("username");
        resideprovince = getIntent().getStringExtra("resideprovince");
        residecity = getIntent().getStringExtra("residecity");
        spacenote = getIntent().getStringExtra("spacenote");
        fuidstr = getIntent().getStringExtra("fuidstr");
        chatroomName = getIntent().getStringExtra("chatroomName");
        
        Log.d(TAG, fuidstr+":"+chatroomName);
        
        person = this;
        
        setContentView(R.layout.person);
        setBannerLayout();
        setContentLayout();
	}
	
	void setBannerLayout(){
		final ImageButton bannerBack = (ImageButton)findViewById(R.id.bannerBack);
		bannerBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				bannerBack.setBackgroundResource(R.drawable.back_button_clicked);
				if ( checkTwoStr("Contact", backTarget) ){
					Log.d(TAG, "go to Contact");
					Bundle bundle = new Bundle();
					bundle.putString("uidLogin", uidLogin );
					bundle.putString("usernameLogin", usernameLogin );
					cf.jump2Activity(Contact.class, bundle);
				}else if( checkTwoStr("FindFriend", backTarget) ){
					Log.d(TAG, "go to FindFriend");
					Bundle bundle = new Bundle();
					bundle.putString("uidLogin", uidLogin );
					bundle.putString("usernameLogin", usernameLogin );
					cf.jump2Activity(FindFriend.class, bundle);
					//person.onBackPressed();

				}
				// cf.back2Activity();
				// --------------------------------//
			}
		});

		//final TextView bannerTitle = (TextView)findViewById(R.id.bannerTitle);
	}
	
	void setContentLayout(){
		
		final ImageView avatar = (ImageView)findViewById(R.id.avatar);
		AsyncImageLoader imageLoader = new AsyncImageLoader();
		imageLoader.loadDrawable( 
				cf.getAvatarPath_M( uid ) , 
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable, String imageUrl) {
						// the implement of this interface.
						if( !Toolets.isNull(imageDrawable) ){avatar.setImageDrawable(imageDrawable);}
					}
				}
		);
		
		final TextView account = (TextView)findViewById(R.id.account);
		account.setText(username);

		final TextView areaTV = (TextView)findViewById(R.id.areaTV);
		areaTV.setText(resideprovince + " " + residecity);
		final TextView spacenoteTV = (TextView)findViewById(R.id.spacenoteTV);
		String temp =  Toolets.isNull(spacenote) |  Toolets.isEmpStr(spacenote) ? "" : Toolets.stripHTMLTag(spacenote);
		spacenoteTV.setText(temp);
		
		final Button functionB = (Button)findViewById(R.id.functionB);
		if( checkTwoStr("Contact", backTarget) ){
			functionB.setText(R.string.person_sendmsg_bt);
			functionB.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// --------------------------------//
			        //--
					Bundle bundle = new Bundle();
					bundle.putString("_id", uidLogin );
					bundle.putString("fuidstr", fuidstr );
					bundle.putString("room", chatroomName );
					cf.jump2Activity( ChatList.class, bundle );
					//--
					// --------------------------------//
				}
			});
			
		}else if( checkTwoStr("FindFriend", backTarget) ){
			functionB.setText(R.string.person_addfriend_bt);
			functionB.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// --------------------------------//
					//--
	            		// add to contact
	            		if (cf.hasNetwork()){
				        	//--------
	            			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    			        nameValuePairs.add(new BasicNameValuePair("action", "addContact"));
	    			        
	    			        nameValuePairs.add(new BasicNameValuePair("uid", uidLogin));
	    			        nameValuePairs.add(new BasicNameValuePair("fuid", uid ));
	    			        nameValuePairs.add(new BasicNameValuePair("status", "1" ));
	    			        nameValuePairs.add(new BasicNameValuePair("fusername", username ));
	    			        nameValuePairs.add(new BasicNameValuePair("gid", "0" ));
	    			        nameValuePairs.add(new BasicNameValuePair("dateline", ""+Calendar.getInstance().getTimeInMillis() ));
	    			        FindFriendTask task = new FindFriendTask("addContact", person, cf);
						task.execute( nameValuePairs );
					    //--------
				    }
		    			//--
					// --------------------------------//
				}
			});

		}
		
		
	}
}
