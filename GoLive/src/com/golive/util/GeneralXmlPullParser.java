package com.golive.util;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class GeneralXmlPullParser {
	
	static final String TAG = "GeneralXmlPullParser";
	static String result;

	public static ArrayList<HashMap<String, String>> parse(String xmlPath) {

		//Log.i(tag, tag+".parse");

		// 回傳物件
		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

		try {

			//使用xmlPull解析xml
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();

			//取得資料

			if ( xmlPath.contains("http://") ){
				parser.setInput(new InputStreamReader( (new URL(xmlPath)).openStream() ));
			}else{
				// xmlPath should be a xml result string.
				parser.setInput(new StringReader(xmlPath));
			}

			//解析xml
			int eventType = parser.getEventType();

			//處理標籤

			String tagName = "";
			HashMap<String, String> resultHashMap = new HashMap<String, String>();
			while (eventType != XmlPullParser.END_DOCUMENT) {
		          
		          switch(eventType){
		          	case XmlPullParser.START_DOCUMENT:
		          		//System.out.println("Start document");
						break;
		          	case XmlPullParser.START_TAG: 
		          		tagName = parser.getName();
		          		
		          		// Define the TAG "||" for separate the tag name and the attribute value 0<tagName>, 1<attribute>, ...
	          			String attrStr = "";
	          			for(int i = 0 ; i < parser.getAttributeCount() ; i++){
	          				attrStr += "||" + parser.getAttributeName(i) + "=\"" +parser.getAttributeValue(i) +"\"";
	          			}
	          			//Log.i( tag, "<"+parser.getName() + attrStr +">" );
	          			
	          			//---do attribute here---
	          			if ( parser.getAttributeCount() > 0 ){
		          			//resultList.add(parser.getName() + attrStr);
	          				resultHashMap.put( parser.getName(), attrStr );
	          			}
	          			//----------------------
	          			
		          		break;
		          	case XmlPullParser.TEXT: 
			            //Log.i( tag, parser.getText() );
			            
			            //---do tag value here---
			            //if ( !"".equals(parser.getText()) ){
		          			//resultList.add(tagName+":"+parser.getText());
	          				resultHashMap.put( tagName, parser.getText() );
			            //}
			            //----------------------
			            
			            break;
		          	case XmlPullParser.END_TAG:
		          		//Log.i( tag, "</"+parser.getName()+">" );
		          		if ( "row".equals(parser.getName()) ){
		          			resultList.add( (HashMap<String, String>)resultHashMap.clone() );
		          		}
		          		break;
		          	case XmlPullParser.END_DOCUMENT:
			            //System.out.println("End document");
			            break;
		          }
		          eventType = parser.next();
		    }
			return resultList;
		} catch (XmlPullParserException e) {
			System.out.println(TAG+":"+"XmlPullParserException !!");
			e.printStackTrace();
			return resultList;
		} catch (IOException e) {
			System.out.println(TAG+":"+"IOException !!");
			e.printStackTrace();
			return resultList;
		} catch (Exception e){
			System.out.println(TAG+":"+"Exception !!");
			e.printStackTrace();
			return resultList;
		}

		//return resultList;
	}
}