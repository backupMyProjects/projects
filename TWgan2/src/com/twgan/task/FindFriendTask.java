package com.twgan.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.*;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;

import com.twgan.activity.Contact;
import com.twgan.utils.CommonFunctions;
import com.twgan.utils.Constants;
import com.twgan.utils.HttpClientConnector;
import com.twgan.utils.Toolets;

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
