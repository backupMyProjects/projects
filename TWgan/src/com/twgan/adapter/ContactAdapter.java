package com.twgan.adapter;

import com.twgan.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.twgan.activity.*;
import com.twgan.utils.AsyncImageLoader;
import com.twgan.utils.CommonFunctions;
import com.twgan.utils.Constants;
import com.twgan.utils.Toolets;
import com.twgan.utils.AsyncImageLoader.ImageCallback;

public class ContactAdapter extends BaseAdapter {
	private String TAG = "ContactAdapter";
	
	private Context activity;
	private List<HashMap<String, String>> data;
	private CommonFunctions cf;
	
	String usernameLogin;
	String uidLogin;
	
	public HashMap<String, String> map;
	
	private AsyncImageLoader imageLoader = new AsyncImageLoader();

	public ContactAdapter(Contact activity, List<HashMap<String, String>> data) {
		this.activity = activity;
		this.cf = new CommonFunctions(activity);
		//this.cf.logLv = Constants.DEBUG;
		this.data = data;

		usernameLogin = activity.getIntent().getStringExtra("usernameLogin");
        uidLogin = activity.getIntent().getStringExtra("uidLogin");
        cf.setLoginInfo(usernameLogin, uidLogin);
        
        map = new HashMap<String, String>();
	}
	

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public HashMap<String, String> getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		HashMap<String, String> itemHM = data.get(position);
		return Integer.parseInt(itemHM.get("_id"));
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//System.out.println("position : " + position);
		LayoutInflater factory = LayoutInflater.from(activity);
		View v = (View) factory.inflate(R.layout.contact_itemview, null);
		
		final LinearLayout dataLayout = (LinearLayout) v.findViewById(R.id.dataLayout);
		dataLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//--
				Bundle bundle = new Bundle();
				bundle.putString("uid", data.get(position).get("fuid") );
				final String fuidstr = Toolets.sortedString(",", data.get(position).get("fuid"), uidLogin);
				bundle.putString("fuidstr", fuidstr );
				final String chatroomName = data.get(position).get("fusername") + "," + usernameLogin;
				bundle.putString("chatroomName", chatroomName );
				bundle.putString("username", data.get(position).get("fusername") );
				bundle.putString("resideprovince", data.get(position).get("resideprovince") );
				bundle.putString("residecity", data.get(position).get("residecity") );
				bundle.putString("spacenote", data.get(position).get("spacenote") );
				bundle.putString("backTarget", "Contact" );
				cf.jump2Activity( Person.class, bundle);
				//--
			}
		});
		
		final TextView multiple_title = (TextView) v.findViewById(R.id.multiple_title);
		multiple_title.setText((String) data.get(position).get("fusername"));
		
		final TextView multiple_spacenote = (TextView) v.findViewById(R.id.multiple_spacenote);
		String temp =  Toolets.isNull(data.get(position).get("spacenote")) | 
					   Toolets.isEmpStr(data.get(position).get("spacenote")) ? 
				"" : Toolets.stripHTMLTag((String) data.get(position).get("spacenote"));
		multiple_spacenote.setText(temp);
		
		final CheckBox checkBox = (CheckBox) v.findViewById(R.id.multiple_checkbox);
		
		final ImageView avatar = (ImageView) v.findViewById(R.id.avatar);
		imageLoader.loadDrawable( 
				cf.getAvatarPath_M( data.get(position).get("fuid") ) , 
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable, String imageUrl) {
						// the implement of this interface.
						if( !Toolets.isNull(imageDrawable) ){avatar.setImageDrawable(imageDrawable);}
					}
				}
		);
		
		checkBox.setOnClickListener(new View.OnClickListener() {
			//Map<Integer, Boolean> map;
			@Override
			public void onClick(View view) {
				/*
				 * Action
				 */
				// --------------------------------//
				checkBox.toggle();
				if(checkBox.isChecked()){
					checkBox.setChecked(false);
					map.remove(data.get(position).get("fuid"));
				}else{
					checkBox.setChecked(true);
					map.put(data.get(position).get("fuid"), data.get(position).get("fusername"));
				}
				
				

				//Log.d(TAG, position+" : "+map.get(position));
				cf.log(TAG, position+" : "+map.get(position));
				
				// --------------------------------//

			}
		});
		

		


		return v;

	}
	
	

}
