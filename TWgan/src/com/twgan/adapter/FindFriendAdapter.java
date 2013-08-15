package com.twgan.adapter;

import com.twgan.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.twgan.activity.*;
import com.twgan.task.*;
import com.twgan.utils.*;
import com.twgan.utils.AsyncImageLoader.ImageCallback;
import static com.twgan.utils.Constants.*;

public class FindFriendAdapter extends BaseAdapter {
	private String TAG = "FindFriendAdapter";
	
	private Context activity;
	private List<HashMap<String, String>> data;
	private CommonFunctions cf;
	
	String usernameLogin;
	String uidLogin;
	
	public HashMap<String, String> map;
	
	private AsyncImageLoader imageLoader = new AsyncImageLoader();

	public FindFriendAdapter(FindFriend activity, List<HashMap<String, String>> data) {
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
		return Integer.parseInt(itemHM.get("uid"));
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//System.out.println("position : " + position);
		LayoutInflater factory = LayoutInflater.from(activity);
		View v = (View) factory.inflate(R.layout.findfriend_itemview, null);
		
		final LinearLayout dataLayout = (LinearLayout) v.findViewById(R.id.dataLayout);
		dataLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//--
				Bundle bundle = new Bundle();
				bundle.putString("uid", data.get(position).get("uid") );
				final String fuidstr = Toolets.sortedString(",", data.get(position).get("uid"), uidLogin);
				bundle.putString("fuidstr", fuidstr );
				final String chatroomName = data.get(position).get("username") + "," + usernameLogin;
				bundle.putString("room", chatroomName );
				bundle.putString("username", data.get(position).get("username") );
				bundle.putString("resideprovince", data.get(position).get("resideprovince") );
				bundle.putString("residecity", data.get(position).get("residecity") );
				bundle.putString("spacenote", data.get(position).get("spacenote") );
				bundle.putString("backTarget", "FindFriend" );
				cf.jump2Activity( Person.class, bundle);
				//--
			}
		});
		
		final TextView findfriend_username = (TextView) v.findViewById(R.id.findfriend_username);
		findfriend_username.setText((String) data.get(position).get("username"));

		final TextView findfriend_spacenote = (TextView) v.findViewById(R.id.findfriend_spacenote);
		String temp =  Toolets.isNull(data.get(position).get("spacenote")) | Toolets.isEmpStr(data.get(position).get("spacenote")) ? 
				"" : Toolets.stripHTMLTag((String) data.get(position).get("spacenote"));
		findfriend_spacenote.setText(temp);
		
		final ImageView avatar = (ImageView) v.findViewById(R.id.avatar);
		imageLoader.loadDrawable( 
				cf.getAvatarPath_M( data.get(position).get("uid") ) , 
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable, String imageUrl) {
						// the implement of this interface.
						if( !Toolets.isNull(imageDrawable) ){avatar.setImageDrawable(imageDrawable);}
					}
				}
		);
		
		//new CanvasImageTask().execute(avatar);
		
		final Button addFriendB = (Button) v.findViewById(R.id.addFriendB);
		addFriendB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				openDialog((String) data.get(position).get("username"));
				
				//--------------------------------//
			}
			
			private void openDialog(String msg){
				AlertDialog.Builder alert = new AlertDialog.Builder(activity);
				alert.setTitle(R.string.findfriendAddAlert);
				alert.setMessage(msg);
                alert.setPositiveButton(R.string.AlertConfirm, new DialogInterface.OnClickListener(){
                	public void onClick(DialogInterface dialoginterface, int i){
		    			//--
                		// add to contact
                		if (cf.hasNetwork()){
    			        	//--------
                			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        			        nameValuePairs.add(new BasicNameValuePair("action", "addContact"));
        			        
        			        nameValuePairs.add(new BasicNameValuePair("uid", uidLogin));
        			        nameValuePairs.add(new BasicNameValuePair("fuid", (String) data.get(position).get("uid") ));
        			        nameValuePairs.add(new BasicNameValuePair("status", "1" ));
        			        System.out.println((String) data.get(position).get("username"));
        			        nameValuePairs.add(new BasicNameValuePair("fusername", (String) data.get(position).get("username") ));
        			        nameValuePairs.add(new BasicNameValuePair("gid", "0" ));
        			        nameValuePairs.add(new BasicNameValuePair("dateline", ""+Calendar.getInstance().getTimeInMillis() ));
        			        FindFriendTask task = new FindFriendTask("addContact", activity, cf);
    						task.execute( nameValuePairs );
    				        //--------
    			        }
		    			//--
                	}
                });

    			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    			  public void onClick(DialogInterface dialog, int whichButton) {
    			    // Canceled.
    			  }
    			});

    			alert.show();
    			
    		}
		});
		

		


		return v;

	}
	

/*
	public class FindFriendTask extends AsyncTask<Object, Void, ArrayList<HashMap<String, String>>> {
		
		boolean resultCheck = false;
		ProgressDialog dialog;
		CommonFunctions cf;
		Context activity;
		
		String action;
		public FindFriendTask(String action, Context activity, CommonFunctions cf){
			this.action = action;
			this.activity = activity;
			this.cf = cf;
		}
		
		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			if ( dialog.isShowing() ){dialog.cancel();}
			
			resultCheck = Toolets.notEmpList(result);
			
			if( Toolets.checkTwoStr(action, "addContact") ){
				// nothing have to do
				cf.jump2Activity(Contact.class, new Bundle());
			}
			
			
			
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(activity, "", "Loading... Please wait.", true, true);
			//super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(Object... inputs) {
			ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
			
			
			for (Object input : inputs) {
				if(Toolets.checkTwoStr(action, "addContact")){
			        String result = HttpClientConnector.postData(Constants.SERVER + Constants.URLPATH_SETDATA, (List<NameValuePair>)input);
			        HashMap<String, String> tempHM = new HashMap();
			        tempHM.put("result", result);
			        response.add(tempHM);
				}
			}
			
			
			System.out.println("response : "+response);
			
			return response;
		}
	}
	*/

}
