package com.twgan.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import static com.twgan.utils.Constants.*;

import com.twgan.R;
import com.twgan.utils.*;

public class Setting extends Activity {
	private final static String TAG = "Setting";
	public final static String ACTIVITY_NAME = "com.twgan.activity." + TAG;
	/** Called when the activity is first created. */
	CommonFunctions cf = new CommonFunctions(this);

	String usernameLogin;
	String uidLogin;

	Setting setting;

	// ListView listViewLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usernameLogin = getIntent().getStringExtra("usernameLogin");
		uidLogin = getIntent().getStringExtra("uidLogin");
		cf.setLoginInfo(usernameLogin, uidLogin);

		cf.setInvisibleStatusbar_Titlebar();
		setContentView(R.layout.template_list);

		setting = this;
		// listViewLayout = (ListView) this.findViewById(R.id.listViewLayout);

		setBannerItem();
		setFunctionsItem();
		setContentItem();

	}

	/*--------------------------------------------*/
	// layout setting
	/*--------------------------------------------*/
	private void setBannerItem() {
		final ImageButton bannerBack = (ImageButton) findViewById(R.id.bannerBack);
		bannerBack.setVisibility(INVISIBLE);

		final TextView bannerTitle = (TextView) findViewById(R.id.bannerTitle);
		bannerTitle.setText(R.string.item_setting);
		//bannerTitle.setVisibility(INVISIBLE);

		final ImageButton bannerFunction = (ImageButton) findViewById(R.id.bannerFunction);
		bannerFunction.setVisibility(INVISIBLE);
	}

	private void setFunctionsItem() {

		final ImageButton messageB = (ImageButton) findViewById(R.id.messageB);
		messageB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.jump2Activity(ChatRoomList.class, new Bundle());
				// --------------------------------//
			}
		});

		final ImageButton contactB = (ImageButton) findViewById(R.id.contactB);
		contactB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.jump2Activity(Contact.class, new Bundle());
				// --------------------------------//
			}
		});

		final ImageButton findB = (ImageButton) findViewById(R.id.findB);
		findB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.jump2Activity(FindFriend.class, new Bundle());
				// --------------------------------//
			}
		});

		final ImageButton settingB = (ImageButton) findViewById(R.id.settingB);
		settingB.setBackgroundResource(R.drawable.setting_act);
	}

	private void setContentItem() {
		final RelativeLayout settingLayout = (RelativeLayout) findViewById(R.id.settingLayout);
		settingLayout.setVisibility(VISIBLE);

		final Button logout = (Button) findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				openLogoutCheckDialog();
				// --------------------------------//
			}

			private void openLogoutCheckDialog() {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						Setting.this);

				alert.setTitle(R.string.settingLogoutCheckTitle);
				alert.setMessage(R.string.settingLogoutCheckMsg);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// --
								cf.writeProp("", LOGIN_FILE);
								cf.jump2Activity(Login.class, new Bundle());
								// --
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();

			}
		});

		final Button setSpacenote = (Button) findViewById(R.id.setSpacenote);
		setSpacenote.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				openSetSpacenoteDialog();
				// --------------------------------//
			}

			private void openSetSpacenoteDialog() {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						Setting.this);

				alert.setTitle(R.string.setting_Spacenote_Title);

				// Set an EditText view to get user input
				final EditText input = new EditText(Setting.this);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String spacenote = input.getText().toString();
								cf.log(TAG, spacenote);
								// --
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
								nameValuePairs.add(new BasicNameValuePair(
										"action", "updateSpacenote"));

								nameValuePairs.add(new BasicNameValuePair(
										"uid", uidLogin));
								nameValuePairs.add(new BasicNameValuePair(
										"spacenote", spacenote));
								DownloadFriendTask task = new DownloadFriendTask(
										"updateSpacenote");
								task.execute(nameValuePairs);
								// --
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();

			}
		});

		final Button getGreat = (Button) findViewById(R.id.getGreat);
		getGreat.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				Uri uri = Uri.parse(GREAT_URL);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				// --------------------------------//
			}
		});
	}

	private class DownloadFriendTask extends
			AsyncTask<Object, Void, ArrayList<HashMap<String, String>>> {

		boolean resultCheck = false;
		ProgressDialog dialog;

		String action;

		public DownloadFriendTask(String action) {
			this.action = action;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			if (dialog.isShowing()) {
				dialog.cancel();
			}

			resultCheck = Toolets.notEmpList(result);

			if (Toolets.checkTwoStr(action, "addContact")) {
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
			dialog = ProgressDialog.show(Setting.this, "",
					"Loading... Please wait.", true, true);
			// super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Object... inputs) {
			ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

			for (Object input : inputs) {
				if (Toolets.checkTwoStr(action, "updateSpacenote")) {
					String result = HttpClientConnector.postData(
							Constants.SERVER + Constants.URLPATH_SETDATA,
							(List<NameValuePair>) input);
					HashMap<String, String> tempHM = new HashMap<String, String>();
					tempHM.put("result", result);
					response.add(tempHM);
				}
			}

			System.out.println("response : " + response);

			return response;
		}
	}

}
