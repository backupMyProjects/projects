package cc.nexdoor.ct.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import cc.nexdoor.ct.activity.R;
import cc.nexdoor.ct.adapter.ModuleMenuGridViewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;
import cc.nexdoor.android.module.RSSSourceXMLHandler;
import cc.nexdoor.android.module.RSSXMLHandler;

public class ImageNewsItem extends Activity {
    /** Called when the activity is first created. */
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String TAG = "CTHome:ImageNewsItem";
	private LogD log = new LogD(TAG, true);
	private final int TRUE = -1;
	private final int FALSE = 0;
	private Bundle bundle;
	

	// ===========================================================
	// Override Methods
	// ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInvisibleStatusbar_Titlebar();
        setContentView(R.layout.imagenewsitem);

    	bundle = this.getIntent().getExtras();
        
        setupBannerAction();
        setupViewItems();
        
        DownloadJob();

    }
    
    
    // ===========================================================
	// Vision Methods
	// ===========================================================
    private void setupBannerAction(){
    	ImageButton modulemenu = (ImageButton)findViewById(R.id.back2List);
    	
    	modulemenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				back2Activity();
				//--------------------------------//
			}
		});
    	
    }
    
    private void setupViewItems(){

//    	if ( bundle.getByteArray("bitmap") != null ) {
//    		ImageView ivView = (ImageView)findViewById(R.id.itemView);
//    		RelativeLayout.LayoutParams paramImage = new RelativeLayout.LayoutParams(300, 300);
//    			paramImage.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
//	    		paramImage.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
//    		ivView.setLayoutParams( paramImage );
//        	ivView.setImageBitmap( getBitMapFromByteArray(bundle.getByteArray("bitmap")) );
//    	}
    	
    	TextView tvTitle = (TextView)findViewById(R.id.itemTitle);
    	tvTitle.setText( bundle.getString("title") );

    	TextView tvDate = (TextView)findViewById(R.id.itemDate);
    	tvDate.setText( bundle.getString("date") );
    	
    	TextView tvDesc = (TextView)findViewById(R.id.itemDesc);
    	tvDesc.setText( stripHTMLTag(bundle.getString("desc")) );
    }
    
    
    
    private Bitmap bitmap;
    //-- 0. Thread controller -- Flag
    boolean finished = false;
    private void DownloadJob(){
    	finished = false;
    	
    	Runnable myThread = new Runnable() {
    		
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			log.d("This is Thread");
    			
    			//-- 0. Thread controller -- Continue Condition
    			//--
    				try {
    					

    					//-- 2. handler alert
    					myHandle.sendMessage(myHandle.obtainMessage());
    					//--
    					
    					//-- 1. Do Heave Job
    					log.d("test1 finished : " + finished);
    					
    					bitmap = returnBitMap( bundle.getString("url") );
        				
    					finished = true;
    					//--
    					
    					//-- 2. handler alert
    					myHandle.sendMessage(myHandle.obtainMessage());
    					//--
    					
    				} catch (Throwable t) {
    				}
    			
    			//-- Default method after thread condition
    			log.d("test3 finished : "+finished);
    			
    		}

    		Handler myHandle = new Handler() {
    			@Override
    			public void handleMessage(Message msg) {
    				// TODO Auto-generated method stub
    				log.d("test2 finished : "+finished);
    				//-- 5. Condition UI setup 
    				if ( finished ) {
    					
    			    	ImageView ivView = (ImageView)findViewById(R.id.itemView);
    			    	RelativeLayout.LayoutParams paramImage = new RelativeLayout.LayoutParams(300, 300);
	    	    			paramImage.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
	    		    		paramImage.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
	    	    		ivView.setLayoutParams( paramImage );
    			    	ivView.setImageBitmap( bitmap );
    					
    					log.d("Condition UI setup");
    					
    				}
    				
    				
    				//--
    			}
    		};
    	};
    	
    	Thread job = new Thread(myThread);
    	job.start();
//    	new Thread(myThread).start();
		
    }
    
    
   
    
    
	// ===========================================================
	// Methods
	// ===========================================================
    private Bitmap getBitMapFromByteArray(byte[] src) {
		return BitmapFactory.decodeByteArray(src, 0, src.length);
	}
    
    private Bitmap returnBitMap(String url) { 
    	URL myFileUrl = null;
    	Bitmap bitmap = null;
    	
    	try{
    		Log.d("url", "|"+url+"|");
    		if ( !"".equals(url) ){
        		myFileUrl = new URL(url);
    		}
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}
    	
    	try{
    		if ( null != myFileUrl){
    			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
        		conn.setDoInput(true);
        		conn.connect();
        		InputStream is = conn.getInputStream();
        		bitmap = BitmapFactory.decodeStream(is);
        		is.close();
    		}
    		
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return bitmap;
    }
    
	private static String stripHTMLTag(String srcStr){
		String regex = "<(?![!/]?[ABIU][>\\s])[^>]*>|&nbsp;";
		return srcStr.replaceAll(regex, "");
	}
    
    private void jump2Activity(Class target, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(this, target);

		startActivityForResult(intent,0);
	}
	
	private void back2Activity() {
		this.setResult(RESULT_OK, this.getIntent());
		this.finish();
	}
	private void setInvisibleStatusbar_Titlebar(){
//		final Window win = getWindow();
//		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
    
}