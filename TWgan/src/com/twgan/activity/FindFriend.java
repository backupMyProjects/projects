package com.twgan.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mapView.overlay.CustomItemizedOverlay;
import mapView.overlay.CustomOverlayItem;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.twgan.utils.Constants.*;
import static com.twgan.utils.Toolets.*;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.twgan.R;
import com.twgan.adapter.ContactAdapter;
import com.twgan.adapter.FindFriendAdapter;
import com.twgan.utils.CommonFunctions;
import com.twgan.utils.Constants;
import com.twgan.utils.GeneralXmlPullParser;
import com.twgan.utils.HttpClientConnector;
import com.twgan.utils.Toolets;

public class FindFriend extends MapActivity {
	private final static String TAG = "FindFriend";
	public final static String ACTIVITY_NAME = "com.twgan.activity." + TAG;
	/** Called when the activity is first created. */
	CommonFunctions cf = new CommonFunctions(this);

	String usernameLogin;
	String uidLogin;

	FindFriend findfriend;
	ListView listViewLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usernameLogin = getIntent().getStringExtra("usernameLogin");
		uidLogin = getIntent().getStringExtra("uidLogin");
		cf.setLoginInfo(usernameLogin, uidLogin);

		cf.setInvisibleStatusbar_Titlebar();
		setContentView(R.layout.findfriend);

		findfriend = this;
		listViewLayout = (ListView) this.findViewById(R.id.listViewLayout);

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
		bannerTitle.setText(R.string.item_findfriend);
		//bannerTitle.setVisibility(INVISIBLE);

		final ImageButton bannerFunction = (ImageButton) findViewById(R.id.bannerFunction);
		bannerFunction.setVisibility(INVISIBLE);
	}

	private void setFunctionsItem() {
		final LinearLayout normalFunctionLayout = (LinearLayout) findViewById(R.id.normalFunctionLayout);

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
		findB.setBackgroundResource(R.drawable.contactadd_act);

		final ImageButton settingB = (ImageButton) findViewById(R.id.settingB);
		settingB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.jump2Activity(Setting.class, new Bundle());
				// --------------------------------//
			}
		});
	}

	private void setContentItem() {
		// final ListView listViewLayout =
		// (ListView)findViewById(R.id.listViewLayout);
		listViewLayout.setVisibility(GONE);

		final RelativeLayout findFriendLayout = (RelativeLayout) findViewById(R.id.findFriendLayout);
		findFriendLayout.setVisibility(VISIBLE);

		final RelativeLayout findfriendAccountLayout = (RelativeLayout) findViewById(R.id.findfriendAccountLayout);
		final Button viaAccount = (Button) findViewById(R.id.viaAccount);
		viaAccount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				findFriendLayout.setVisibility(GONE);
				findfriendAccountLayout.setVisibility(VISIBLE);

				final ImageButton bannerBack = (ImageButton) findViewById(R.id.bannerBack);
				bannerBack.setVisibility(VISIBLE);
				bannerBack.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// --------------------------------//
						findFriendLayout.setVisibility(VISIBLE);
						findfriendAccountLayout.setVisibility(GONE);
						listViewLayout.setVisibility(GONE);
						bannerBack.setVisibility(INVISIBLE);
						// --------------------------------//
					}
				});
				// --------------------------------//
			}
		});

		final RelativeLayout findfriendAreaLayout = (RelativeLayout) findViewById(R.id.findfriendAreaLayout);
		final Button viaArea = (Button) findViewById(R.id.viaArea);
		viaArea.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				findFriendLayout.setVisibility(GONE);
				findfriendAreaLayout.setVisibility(VISIBLE);

				final ImageButton bannerBack = (ImageButton) findViewById(R.id.bannerBack);
				bannerBack.setVisibility(VISIBLE);
				bannerBack.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// --------------------------------//
						findFriendLayout.setVisibility(VISIBLE);
						findfriendAreaLayout.setVisibility(GONE);
						listViewLayout.setVisibility(GONE);
						bannerBack.setVisibility(INVISIBLE);
						// --------------------------------//
					}
				});
				// --------------------------------//
			}
		});
		
		final MapView mapView = (MapView) findViewById(R.id.mapview);
		final Button viaMap = (Button) findViewById(R.id.viaMap);
		viaMap.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				findFriendLayout.setVisibility(GONE);
				mapView.setVisibility(VISIBLE);

				final ImageButton bannerBack = (ImageButton) findViewById(R.id.bannerBack);
				bannerBack.setVisibility(VISIBLE);
				bannerBack.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// --------------------------------//
						findFriendLayout.setVisibility(VISIBLE);
						mapView.setVisibility(GONE);
						listViewLayout.setVisibility(GONE);
						bannerBack.setVisibility(INVISIBLE);
						// --------------------------------//
					}
				});
				// --------------------------------//
			}
		});

		final EditText searchAcc_ET = (EditText) findViewById(R.id.searchAcc_ET);
		searchAcc_ET.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				searchAcc_ET.setText("");
				// --------------------------------//
			}
		});

		final Button searchAcc_BT = (Button) findViewById(R.id.searchAcc_BT);
		searchAcc_BT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// search via username
				String username = ((EditText) findViewById(R.id.searchAcc_ET))
						.getText().toString();

				if (!Toolets.isEmpStr(username)) {
					if (cf.hasNetwork()) {
						// --------
						DownloadWebPageTask task = new DownloadWebPageTask(
								"searchAccount");
						task.execute(SERVER + URLPATH_GETDATA
								+ "?table=searchAccount" + "&username="
								+ urlEncode(username, "UTF-8"));
						// --------
					}
				} else {
					// please input string
					Toast.makeText(FindFriend.this, R.string.inputStrEmpAlert,
							Toast.LENGTH_SHORT).show();
				}

				// --------------------------------//
			}
		});

		final StringBuffer spinnerSelectedBF = new StringBuffer();
		final Spinner searchArea_Spinner = (Spinner) findViewById(R.id.searchArea_Spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.searchArea_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		searchArea_Spinner.setAdapter(adapter);
		searchArea_Spinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						// --------------------------------//
						// Toast.makeText(parent.getContext(), "The area is " +
						// parent.getItemAtPosition(pos).toString(),
						// Toast.LENGTH_LONG).show();
						spinnerSelectedBF.delete(0, spinnerSelectedBF.length());
						spinnerSelectedBF.append(parent.getItemAtPosition(pos)
								.toString());
						// --------------------------------//
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// --------------------------------//
						// Do nothing.
						// --------------------------------//
					}
				});

		final Button searchArea_BT = (Button) findViewById(R.id.searchArea_BT);
		searchArea_BT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// search via username
				String resideprovince = spinnerSelectedBF.toString();

				if (!Toolets.isEmpStr(resideprovince)) {
					if (cf.hasNetwork()) {
						// --------

						DownloadWebPageTask task = new DownloadWebPageTask(
								"searchAreaCount");
						Gresideprovince = urlEncode(resideprovince, "UTF-8");
						task.execute(SERVER + URLPATH_GETDATA
								+ "?table=searchAreaCount" + "&resideprovince="
								+ Gresideprovince);
						// --------
					}
				} else {
					// please input string
					Toast.makeText(FindFriend.this, R.string.inputStrEmpAlert,
							Toast.LENGTH_SHORT).show();
				}

				// --------------------------------//
			}
		});

		
		mapView.setBuiltInZoomControls(true);
		List<Overlay> mapOverlays = mapView.getOverlays();
		
		// first overlay
		Drawable drawable = getResources().getDrawable(R.drawable.marker2);
		CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
		
		GeoPoint point = new GeoPoint((int)(51.5174723*1E6),(int)(-0.0899537*1E6));
		CustomOverlayItem overlayItem = new CustomOverlayItem(point, "Tomorrow Never Dies (1997)", 
				"(M gives Bond his mission in Daimler car)", 
				"http://ia.media-imdb.com/images/M/MV5BMTM1MTk2ODQxNV5BMl5BanBnXkFtZTcwOTY5MDg0NA@@._V1._SX40_CR0,0,40,54_.jpg");
		itemizedOverlay.addOverlay(overlayItem);
		
		mapOverlays.add(itemizedOverlay);
		
		final MapController mc = mapView.getController();
		mc.animateTo(point);
		mc.setZoom(16);
	}

	String Gresideprovince;

	private void setResult(List<HashMap<String, String>> result) {

		final Button searchAreaNextPage_BT = (Button) findViewById(R.id.searchAreaNextPage_BT);
		if (index + record < searchCount) {
			searchAreaNextPage_BT.setVisibility(VISIBLE);
		} else {
			searchAreaNextPage_BT.setVisibility(INVISIBLE);
		}
		searchAreaNextPage_BT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				index += record;
				// --
				DownloadWebPageTask task = new DownloadWebPageTask("searchArea");
				task.execute(SERVER + URLPATH_GETDATA + "?table=searchArea"
						+ "&resideprovince=" + Gresideprovince + "&index="
						+ index + "&record=" + record);
				// --
				// --------------------------------//
			}
		});

		final Button searchAreaPrePage_BT = (Button) findViewById(R.id.searchAreaPrePage_BT);
		if (index != 0) {
			searchAreaPrePage_BT.setVisibility(VISIBLE);
		} else {
			searchAreaPrePage_BT.setVisibility(INVISIBLE);
		}
		searchAreaPrePage_BT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				index = (index - record >= 0) ? index - record : 0;
				// --
				DownloadWebPageTask task = new DownloadWebPageTask("searchArea");
				task.execute(SERVER + URLPATH_GETDATA + "?table=searchArea"
						+ "&resideprovince=" + Gresideprovince + "&index="
						+ index + "&record=" + record);
				// --
				// --------------------------------//
			}
		});

		listViewLayout.setVisibility(VISIBLE);
		final FindFriendAdapter adapter = new FindFriendAdapter(findfriend, result);
		listViewLayout.setTextFilterEnabled(true);
		listViewLayout.setAdapter(adapter);
	}

	private void noResult() {

	}

	int searchCount;
	int index = 0;
	int record = 10;

	// ArrayList<HashMap<String, String>> findfriendAL = null;
	private class DownloadWebPageTask extends
			AsyncTask<Object, Void, ArrayList<HashMap<String, String>>> {

		boolean resultCheck = false;
		ProgressDialog dialog;

		String action;

		public DownloadWebPageTask(String action) {
			this.action = action;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			if (dialog.isShowing()) {
				dialog.cancel();
			}

			resultCheck = Toolets.notEmpList(result);
			// System.out.println("result : "+result); //result : [{uid=48,
			// email=kevin5277@hotmail.com, username=kevin}]

			if (Toolets.checkTwoStr(action, "searchAccount")) {
				if (resultCheck) {
					findfriend.setResult(result);
				} else {
					Toast.makeText(FindFriend.this, R.string.noResultAlert,
							Toast.LENGTH_SHORT).show();
					findfriend.noResult();
					// System.out.println("empty result");
				}
			} else if (Toolets.checkTwoStr(action, "addContact")) {
				// nothing have to do
				cf.jump2Activity(Contact.class, new Bundle());
			} else if (Toolets.checkTwoStr(action, "searchAreaCount")) {
				if (resultCheck) {
					for (HashMap<String, String> row : result) {
						searchCount = Toolets.getNumber(row.get("count"));
						index = 0;
						// --
						DownloadWebPageTask task = new DownloadWebPageTask(
								"searchArea");
						task.execute(SERVER + URLPATH_GETDATA
								+ "?table=searchArea" + "&resideprovince="
								+ Gresideprovince + "&index=" + index
								+ "&record=" + record);
						// --
					}
					// findfriendAL = result;
				} else {
					Toast.makeText(FindFriend.this, R.string.noResultAlert,
							Toast.LENGTH_SHORT).show();
					findfriend.noResult();
					// System.out.println("empty result");
				}
			} else if (Toolets.checkTwoStr(action, "searchArea")) {
				// System.out.println("done.");
				if (resultCheck) {
					findfriend.setResult(result);
				} else {
					Toast.makeText(FindFriend.this, R.string.noResultAlert,
							Toast.LENGTH_SHORT).show();
					findfriend.noResult();
					// System.out.println("empty result");
				}
			} else if (Toolets.checkTwoStr(action, "searchMap")) {
				// System.out.println("done.");
				if (resultCheck) {
					findfriend.setResult(result);
				} else {
					Toast.makeText(FindFriend.this, R.string.noResultAlert,
							Toast.LENGTH_SHORT).show();
					findfriend.noResult();
					// System.out.println("empty result");
				}
			}

		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(FindFriend.this, "",
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
				if (Toolets.checkTwoStr(action, "searchAccount")) {
					// via GeneralXmlPullParser (urlPath) : send by GET
					response = GeneralXmlPullParser.parse(input.toString());

					// via HttpClientConnector (NameValuePair) : send by POST
					// String result =
					// HttpClientConnector.postData(Constants.SERVER +
					// Constants.URLPATH_GETDATA+"?table=user",
					// (List<NameValuePair>)input);
					// response = GeneralXmlPullParser.parse(result);
				} else if (Toolets.checkTwoStr(action, "addContact")) {
					String result = HttpClientConnector.postData(SERVER
							+ URLPATH_SETDATA, (List<NameValuePair>) input);
					HashMap<String, String> tempHM = new HashMap();
					tempHM.put("result", result);
					response.add(tempHM);
				} else if (Toolets.checkTwoStr(action, "searchAreaCount")) {
					// via GeneralXmlPullParser (urlPath) : send by GET
					response = GeneralXmlPullParser.parse(input.toString());
				} else if (Toolets.checkTwoStr(action, "searchArea")) {
					// System.out.println(input.toString());
					response = GeneralXmlPullParser.parse(input.toString());
				} else if (Toolets.checkTwoStr(action, "searchMap")) {
					// System.out.println(input.toString());
					response = GeneralXmlPullParser.parse(input.toString());
				}
			}

			System.out.println("response : " + response);

			return response;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
