package com.twgan.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.twgan.component.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import static com.twgan.utils.Constants.*;

import com.twgan.R;
import com.twgan.adapter.ChatroomListAdapter;
import com.twgan.adapter.ContactAdapter;
import com.twgan.utils.CommonFunctions;
import com.twgan.utils.Constants;
import com.twgan.utils.GeneralXmlPullParser;
import com.twgan.utils.HttpClientConnector;
import com.twgan.utils.Toolets;

public class Contact extends Activity{
    /** Called when the activity is first created. */
	private final static String TAG = "Contact";
	public final static String ACTIVITY_NAME = "com.twgan.activity."+TAG;
	String usernameLogin;
	String uidLogin;
	CommonFunctions cf = new CommonFunctions(this);
	
	Contact contact;
	ListView listViewLayout;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cf.logLv = Constants.DEBUG;
        
        usernameLogin = getIntent().getStringExtra("usernameLogin");
        uidLogin = getIntent().getStringExtra("uidLogin");
        cf.setLoginInfo(usernameLogin, uidLogin);
        
        cf.setInvisibleStatusbar_Titlebar();

        setContentView(R.layout.template_list);
    	setBannerItem();
    	setFunctionsItem();
        
        contact = this;
        listViewLayout = (ListView) this.findViewById(R.id.listViewLayout);
        
        DownloadWebPageTask task = new DownloadWebPageTask();
        String xmlURL = Constants.SERVER + Constants.URLPATH_GETDATA + "?table=friend&uid="+uidLogin;
        Log.d(TAG, xmlURL);
		task.execute(new String[] { xmlURL });
        
    }
    
    /*--------------------------------------------*/
    // components setting
    
    private void setBannerItem(){
    	final TextView bannerTitle = (TextView)findViewById(R.id.bannerTitle);
    	Bundle bundle = this.getIntent().getExtras();
    	bannerTitle.setText(R.string.item_contact);
    	
    	final CustomizeDialog customizeDialog = new CustomizeDialog(this);  
    	
    	final ImageButton bannerFunction = (ImageButton)findViewById(R.id.bannerFunction);
    	//bannerFunction.setBackgroundResource(R.drawable.add);
    	bannerFunction.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
				//--------------------------------//
				ArrayList<String> fuidstrAL = new ArrayList<String>();
				String namestr = "";
				if ( map != null ){
					Toolets.printHashMap(map);
					Iterator<String> KeyIt = map.keySet().iterator();
					while( KeyIt.hasNext() ){
						String id = KeyIt.next();
						fuidstrAL.add(id);
						String name = map.get(id);
						namestr += ","+name;
					}
					fuidstrAL.add(uidLogin);
					final String fuidstr = Toolets.sortedString(",", (String[])fuidstrAL.toArray(new String[0]));
					namestr = namestr.replaceFirst(",", "");
					
					if ( map.size() == 0 ){
						customizeDialog.setTitle("");
						customizeDialog.setMessage(R.string.contactNoneAlertTitle);
						Button yesB = customizeDialog.getYesButton();
						yesB.setText(R.string.AlertConfirm);
						customizeDialog.show();
						
						//openNoneOptionsDialog();
					}else if( map.size() == 1 ){

						customizeDialog.setTitle(R.string.contactOneAlertTitle);
						customizeDialog.setMessage(namestr);
						final String chatroomName = namestr + "," + usernameLogin;
						customizeDialog.setYesButton(R.string.AlertConfirm, new View.OnClickListener(){
							@Override
							public void onClick(View v) {
								cf.log(TAG, chatroomName);
					    			//--
					    			Bundle bundle = new Bundle();
					    			bundle.putString("_id", uidLogin );
					    			bundle.putString("fuidstr", fuidstr );
					    			bundle.putString("room", chatroomName );
					    			cf.jump2Activity( ChatList.class, bundle);
					    			//--
							}
		                });
						customizeDialog.show();
						
						//openOneOptionsDialog(namestr, fuidstr);
					}else if( map.size() >= 2 ){
						customizeDialog.setTitle(R.string.contactMultiAlertTitle);
						customizeDialog.setMessage(namestr);
						final EditText inputET = customizeDialog.getInputET();
						inputET.setVisibility(VISIBLE);
						//final String chatroomName = inputET.getText().toString();
						customizeDialog.setYesButton(R.string.AlertConfirm, new View.OnClickListener(){
							@Override
							public void onClick(View v) {
								cf.log(TAG, inputET.getText().toString());
					    			//--
								Bundle bundle = new Bundle();
					    			bundle.putString("_id", uidLogin );
					    			bundle.putString("fuidstr", fuidstr );
					    			bundle.putString("room", inputET.getText().toString() );
					    			cf.jump2Activity( ChatList.class, bundle);
					    			//--
							}
		                });
						customizeDialog.show();
						
						//openMultiChatroomDialog(namestr, fuidstr);
					}
					
					//openOptionsDialog(namestr);
					//openMultiChatroomDialog(namestr, fuidstr);
				}
				//--------------------------------//
			}
    		/*
    		private void openNoneOptionsDialog() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Contact.this);
                dialog.setTitle(R.string.contactNoneAlertTitle);
                dialog.setMessage(R.string.contactNoneAlertMsg);
                dialog.setPositiveButton(R.string.AlertConfirm,
                		new DialogInterface.OnClickListener(){
                        	public void onClick(DialogInterface dialoginterface, int i){
                        	}
                });
                dialog.show();
    		}
    		*/
		/*
    		private void openOneOptionsDialog(String msg, final String fuidstr) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Contact.this);
                dialog.setTitle(R.string.contactOneAlertTitle);
                dialog.setMessage(msg);
                final String chatroomName = msg + "," + usernameLogin;
                dialog.setPositiveButton(R.string.AlertConfirm, new DialogInterface.OnClickListener(){
                	public void onClick(DialogInterface dialoginterface, int i){
		    			cf.log(TAG, chatroomName);
		    			//--
		    			Bundle bundle = new Bundle();
		    			bundle.putString("_id", uidLogin );
		    			bundle.putString("fuidstr", fuidstr );
		    			bundle.putString("room", chatroomName );
		    			cf.jump2Activity( ChatList.class, bundle);
		    			//--
                	}
                });
                dialog.show();
    		}
    		*/
    		
    		/*
    		private void openMultiChatroomDialog(String msg, final String fuidstr){
    			AlertDialog.Builder alert = new AlertDialog.Builder(Contact.this);

    			alert.setTitle(R.string.contactMultiAlertTitle);
    			alert.setMessage(msg);

    			// Set an EditText view to get user input 
    			final EditText input = new EditText(Contact.this);
    			alert.setView(input);

    			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) {
		    			String chatroomName = input.getText().toString();
		    			cf.log(TAG, chatroomName);
		    			//--
		    			Bundle bundle = new Bundle();
		    			bundle.putString("_id", uidLogin );
		    			bundle.putString("fuidstr", fuidstr );
		    			bundle.putString("room", chatroomName );
		    			cf.jump2Activity( ChatList.class, bundle);
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
    		*/

		});
    	
    		final ImageButton bannerBack = (ImageButton)findViewById(R.id.bannerBack);
    		bannerBack.setVisibility(INVISIBLE);
    }
    
    private void setFunctionsItem(){

    	final ImageButton messageB = (ImageButton)findViewById(R.id.messageB);
    	messageB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				cf.jump2Activity(ChatRoomList.class, new Bundle());
				//--------------------------------//
			}
		});

    	final ImageButton contactB = (ImageButton)findViewById(R.id.contactB);
    	contactB.setBackgroundResource(R.drawable.contact_act);
    	
    	final ImageButton findB = (ImageButton)findViewById(R.id.findB);
    	findB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				cf.jump2Activity(FindFriend.class, new Bundle());
				//--------------------------------//
			}
		});
    	
    	final ImageButton settingB = (ImageButton)findViewById(R.id.settingB);
    	settingB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				cf.jump2Activity(Setting.class, new Bundle());
				//--------------------------------//
			}
		});
    	
    	
    }
    
    
    
    //List<HashMap<String, String>> contactList;
    HashMap<String, String> map;
    private class DownloadWebPageTask extends AsyncTask< String, Void, List<HashMap<String, String>> > {
    	ProgressDialog dialog;

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			final ContactAdapter adapter = new ContactAdapter(contact, result);
			listViewLayout.setAdapter(adapter);
			
			/*
			listViewLayout.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					CheckBox checkBox = (CheckBox) view.findViewById(R.id.multiple_checkbox);
					checkBox.toggle();
					adapter.map.put(position, checkBox.isChecked());

				}
				
			});
			*/
			
			//contactList = result;
			map = adapter.map;
			
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
	        dialog = ProgressDialog.show(Contact.this, "", "Loading... Please wait.", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected List<HashMap<String, String>> doInBackground(String... urls) {
			List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
			for (String url : urls) {
		        //String xmlData = HttpClientConnector.getStringByUrl(url);
		        resultList = GeneralXmlPullParser.parse(url);
		        //Toolets.printHashMapList(resultList);
			}
		
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return resultList;
		}
	}
    
    

}
