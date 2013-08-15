package com.twgan.adapter;

import com.twgan.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import com.twgan.activity.*;
import com.twgan.component.CustomizeDialog;
import com.twgan.utils.AsyncImageLoader;
import com.twgan.utils.CommonFunctions;
import com.twgan.utils.Constants;
import com.twgan.utils.HttpClientConnector;
import com.twgan.utils.Toolets;
import com.twgan.utils.AsyncImageLoader.ImageCallback;

import static com.twgan.utils.Toolets.*;
import static com.twgan.utils.Constants.*;

public class ChatListAdapter extends BaseAdapter {
	private String TAG = "ChatListAdapter";
	
	private Context activity;
	private List<HashMap<String, String>> data;
	private CommonFunctions cf;

	public ChatListAdapter(ChatList activity, List<HashMap<String, String>> data) {
		this.activity = activity;
		this.cf = new CommonFunctions(activity);
		this.data = data;
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

	AsyncImageLoader imageLoader = new AsyncImageLoader();
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//System.out.println("position : " + position);
		LayoutInflater factory = LayoutInflater.from(activity);
		final View v = (View) factory.inflate(R.layout.chatlist_itemview, null);
		final RelativeLayout itemviewRL = (RelativeLayout) v.findViewById(R.id.itemviewRL);
		
		final TextView _idTV = (TextView) v.findViewById(R.id._idTV);
		final TextView fuidstrTV = (TextView) v.findViewById(R.id.fuidstrTV);
		final TextView fuidTV = (TextView) v.findViewById(R.id.fuidTV);
		final TextView typeTV = (TextView) v.findViewById(R.id.typeTV);
		
		final TextView titleTV = (TextView) v.findViewById(R.id.titleTV);
		final RelativeLayout textRL = (RelativeLayout) v.findViewById(R.id.textRL);
		final RelativeLayout voiceRL = (RelativeLayout) v.findViewById(R.id.voiceRL);
		final RelativeLayout photoRL = (RelativeLayout) v.findViewById(R.id.photoRL);
		final TextView datelineTV = (TextView) v.findViewById(R.id.datelineTV);

		final String typeRecord = data.get(position).get("type");
		String fuidstrRecord = data.get(position).get("fuidstr");
		
		_idTV.setText(data.get(position).get("_id"));
		fuidstrTV.setText(data.get(position).get("fuidstr"));
		fuidTV.setText(data.get(position).get("fuid"));
		typeTV.setText(data.get(position).get("type"));
		titleTV.setText(data.get(position).get("username"));
		
		if ( "text".equals(typeRecord) ){
			textRL.setVisibility(VISIBLE);
			voiceRL.setVisibility(GONE);
			photoRL.setVisibility(GONE);
			final TextView textTV = (TextView) v.findViewById(R.id.textTV);
			textTV.setText(data.get(position).get("said"));
			
		}else if ( "emotion".equals(typeRecord) ){
			textRL.setVisibility(GONE);
			voiceRL.setVisibility(GONE);
			photoRL.setVisibility(VISIBLE);
			final TextView textTV = (TextView) v.findViewById(R.id.textTV);
			final ImageView photoIV = (ImageView) v.findViewById(R.id.photoIV);
			int resid = Integer.parseInt(data.get(position).get("said"));
			photoIV.setBackgroundResource(resid);
			/*
			ImageGetter imgGetterRES = new ImageGetter() {  
                @Override
               public Drawable getDrawable(String source) {
	               int id = Integer.parseInt(source);
	               Drawable d = activity.getResources().getDrawable(id);
	               d.setBounds(0, 0, 85,85);
	                return d;
               }
	        };
	        String resIDstr = data.get(position).get("said");
	        textTV.append(Html.fromHtml("<img src='"+resIDstr+"'/>", imgGetterRES, null)); 
			*/
		}else if ( "voice".equals(typeRecord) ){
			textRL.setVisibility(GONE);
			voiceRL.setVisibility(VISIBLE);
			photoRL.setVisibility(GONE);
		}else if ( "photo".equals(typeRecord) ){
			textRL.setVisibility(GONE);
			voiceRL.setVisibility(GONE);
			photoRL.setVisibility(VISIBLE);
			final ImageView photoIV = (ImageView) v.findViewById(R.id.photoIV);
			
			//Uri uri = Uri.parse(Constants.SERVER+Constants.URLPATH_LOCATION+data.get(position).get("said"));
			//Log.e(TAG, uri.getPath());
			//photoIV.setImageURI(uri);
			
			imageLoader.loadDrawable( 
					SERVER+URLPATH_LOCATION+data.get(position).get("said") , 
					new ImageCallback() {
						public void imageLoaded(Drawable imageDrawable, String imageUrl) {
							// the implement of this interface.
							if( !Toolets.isNull(imageDrawable) ){
								//photoIV.setImageDrawable(imageDrawable);
								photoIV.setBackgroundDrawable(imageDrawable);
							}
						}
					}
			);
			
		}
		//--
		Date date = new Date();
		Long now = date.getTime();
		Long at = Long.parseLong(data.get(position).get("dateline"));
		Long deltaTime = now - at;
		Long aDay = (long) 86400000;
		String format = "yyyy/MM/dd a hh:mm:ss";
		if ( deltaTime < aDay){
			format = "a hh:mm:ss";
			datelineTV.setText( getDateTime(format, at ) );
		}else if( aDay <= deltaTime && deltaTime < aDay*2   ){
			format = "a hh:mm:ss";
			datelineTV.setText( "昨天 " + getDateTime(format, at ) );
		}else if( aDay*2 <= deltaTime && deltaTime < aDay*7   ){
			format = "E a hh:mm:ss";
			datelineTV.setText( getDateTime(format, at ) );
		}else{
			datelineTV.setText( getDateTime(format, at ) );
		}
		//--
		
		final CustomizeDialog customizeDialog = new CustomizeDialog(activity);
		customizeDialog.setTitle(R.string.chatroom_deltitle);
		customizeDialog.setMessage(R.string.chatroom_delmsg);
		customizeDialog.setNoButtonText(R.string.AlertCalcel);
		customizeDialog.setYesButton(R.string.AlertConfirm, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("action", "deleteChat"));
				nameValuePairs.add(new BasicNameValuePair("_id",(data.get(position)).get("_id")));

				DeleteChatTask task = new DeleteChatTask(nameValuePairs);
				
		        String xmlURL = Constants.SERVER + Constants.URLPATH_SETDATA;
		        Log.d(TAG, xmlURL);
				task.execute(new String[] { xmlURL });
				customizeDialog.dismiss();
			}
		});
		itemviewRL.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				customizeDialog.show();
				return false;
			}
		});
		

		itemviewRL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*
				 * Action
				 */
				// --------------------------------//
				//Bundle bundle = new Bundle();
				//bundle.putString("_id", (data.get(position)).get("_id") );
				//bundle.putString("fuidstr", (data.get(position)).get("fuidstr") );
				//cf.jump2Activity(ChatRoomList.class, new Bundle());
				if ( "voice".equals(typeRecord) ){
					MediaPlayer player = new MediaPlayer();
				    cf.mediaPlay(player, Constants.SERVER+Constants.URLPATH_LOCATION+data.get(position).get("said"));
				}else if( "photo".equals(typeRecord) ){
					final ImageView photoIV = (ImageView) v.findViewById(R.id.photoIV);
					showdialog();
				}
				// --------------------------------//

			}
			
			private void showdialog(){
	    			
				Dialog listDialog = new Dialog(cf.activity);
				listDialog.setTitle("檢視圖片");
				LayoutInflater li = (LayoutInflater) cf.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v = li.inflate(R.layout.image_view, null, false);
				listDialog.setContentView(v);
				final ImageView iv = (ImageView) listDialog.findViewById(R.id.iv);
				imageLoader.loadDrawable( 
    					SERVER+URLPATH_LOCATION+data.get(position).get("said") , 
    					new ImageCallback() {
    						public void imageLoaded(Drawable imageDrawable, String imageUrl) {
    							// the implement of this interface.
    							if( !Toolets.isNull(imageDrawable) ){
    								//iv.setImageDrawable(imageDrawable);
    								iv.setBackgroundDrawable(imageDrawable);
    							}
    						}
    					}
				);
				listDialog.show();
		     }
		});

		return v;

	}
	
	private class DeleteChatTask extends AsyncTask< String, Void, String > {
		ProgressDialog dialog;
		List<NameValuePair> nameValuePairs;
		public DeleteChatTask(List<NameValuePair> nameValuePairs){
			this.nameValuePairs = nameValuePairs;
		}

	@Override
	protected void onPostExecute(String result) {
		Log.d(TAG, result);
		dialog.cancel();
		//super.onPostExecute(result);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		dialog.cancel();
		super.onCancelled();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
        dialog = ProgressDialog.show(activity, "", "Loading... Please wait.", true);
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(String... urls) {
		String result = null;
		for (String url : urls) {
			result = HttpClientConnector.postData(url, nameValuePairs);
		}
	
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
	

}
