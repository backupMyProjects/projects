package com.twgan.utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import android.util.Log;

public class HttpClientConnector {

	public static String getData(String url) {

		String outputString = "";

		// DefaultHttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// HttpGet
		HttpGet httpget = new HttpGet(url);
		// ResponseHandler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			outputString = httpclient.execute(httpget, responseHandler);
			//Log.i("HttpClientConnector", "Connect Success");
		} catch (Exception e) {
			Log.i("HttpClientConnector", "Connect Failed");
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return outputString;

	}
	
	public static String postData(String url, List<NameValuePair> nameValuePairs) {
		String outputString = "";
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);

	    try {
	        // Add your data
	    	/*
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	        */
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        // Execute HTTP Post Request
	        outputString = httpclient.execute(httppost, responseHandler);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }

		httpclient.getConnectionManager().shutdown();
		return outputString;
	} 

}