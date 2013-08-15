package cc.nexdoor.ct.activity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.RSSSourceXMLHandler;

public class CTHome extends Activity {
    /** Called when the activity is first created. */
	
	// ===========================================================
	// Fields
	// ===========================================================
	String TAG = "CTHome";
	LogD log = new LogD(TAG, true);
	

	// ===========================================================
	// Override Methods
	// ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInvisibleStatusbar_Titlebar();
//        setContentView(R.layout.modulemenu);
        setContentView(R.layout.loading);
        
        DownloadJob();
        
//        setupChannel("http://24.chinatimes.com/newsrss/chinatimes.xml");
//        setupGrid();
        

    }
    
    // ===========================================================
	// Vision Methods
	// ===========================================================
    private void setupGrid(){
    	int[] icons = { R.drawable.focusnews, R.drawable.camera, R.drawable.video
    			, R.drawable.ctitv, R.drawable.news, R.drawable.showbiz
    			, R.drawable.life, R.drawable.money, R.drawable.blog, R.drawable.blog};
    	final int[] items = { R.string.focusnews, R.string.camera, R.string.video
    			, R.string.ctitv, R.string.news, R.string.showbiz
    			, R.string.life, R.string.money, R.string.blog, R.string.ctv};
    	
    	GridView gv;
        gv = (GridView) findViewById(R.id.moduleContent);
    	ModuleMenuGridViewAdapter adapter = new ModuleMenuGridViewAdapter(this, items, icons);
    	gv.setAdapter(adapter);
    	
    }
    
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
    					
    					//old
    					//setupChannel("http://24.chinatimes.com/newsrss/chinatimes.xml");
    					//new 20100813
    					setupChannel("http://24.chinatimes.com/newsrss/channel.xml");
        				
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
    					
    					setContentView(R.layout.modulemenu);
    					setupGrid();
    					
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
    public ArrayList channelAttributesList;
    public ArrayList channelContentsList;
    private void setupChannel(String url){
    	RSSSourceXMLHandler rssSource = new RSSSourceXMLHandler();
        rssSource.readRSSSourceXML(url);
        channelAttributesList = rssSource.channelAttributesList;
        channelContentsList = rssSource.channelContentsList;
        
		for (int i = 0; i < channelContentsList.size(); i++) {
			HashMap<String, String> hm = (HashMap) channelContentsList.get(i);
			for (String key : hm.keySet()) {
				log.d(key + " : " + hm.get(key));
			}
		}
        
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