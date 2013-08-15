package cc.nexdoor.ct.activity;

import java.io.IOException;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;
import cc.nexdoor.android.module.RSSSourceXMLHandler;
import cc.nexdoor.android.module.RSSXMLHandler;

public class test extends Activity {
    /** Called when the activity is first created. */
	
	// ===========================================================
	// Fields
	// ===========================================================
	String TAG = "test";
	LogD log = new LogD(TAG, true);
	ProgressBar pb;
	TextView tv;
	

	// ===========================================================
	// Override Methods
	// ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInvisibleStatusbar_Titlebar();
        setContentView(R.layout.main);

		pb = (ProgressBar) findViewById(R.id.progressbar_Horizontal);
		tv = (TextView) findViewById(R.id.text);
        
//        readRSSSourceXML("http://24.chinatimes.com/newsrss/chinatimes.xml");
//        getVerifyNumber();
//        getRss("http://24.chinatimes.com/newsrss/focusrss.aspx?v="+getVerifyNumber());
        
//        new DownloadNews().execute( "http://24.chinatimes.com/newsrss/chinatimes.xml", "http://24.chinatimes.com/newsrss/focusrss.aspx?v="+getVerifyNumber() );
//		new Thread(myThread).start();
		setupButton();

    }
    
    class DownloadNews extends AsyncTask<String, Integer, Long> {
    	@Override
    	protected Long doInBackground(String... urls) {
    		// TODO Auto-generated method stub
    		long result = 100;
    		
    		log.d("This is async.");
    		readRSSSourceXML(urls[0]);
    		getRss(urls[1]);
             return result;
    	}
    	
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
    		pb.setProgress(progress[0]);
    		log.d("progress[0] "+progress[0]);
        }
    	@Override
        protected void onPostExecute(Long result) {
//            showDialog("Downloaded " + result + " bytes");
    		log.d("result "+result);
        }
    }
    
    //-- 0. Thread controller -- Flag
    boolean flag = true;
    //--
    int myProgress = 0;
	private Runnable myThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.d("This is Thread");
			
			//-- 0. Thread controller -- Continue Condition
			while ( flag && myProgress < 50 ) {
			//--
				try {
					//-- 1. handler setup
					myHandle.sendMessage(myHandle.obtainMessage());
					//--
					
					//-- 2. Do Heave Job
					Thread.sleep(100);
					log.d("test1 " + myProgress);
					myProgress++;
					//--
					
					//-- 3. Showing process setup
					pb.setProgress(myProgress);
					//--

					//-- 4. Thread handler -- Stop Flag
					if ( myProgress == 30 ) {
						log.d("Thread handler -- Stop Flag");
						break;
					}
					//--
					
				} catch (Throwable t) {
				}
			}
			
			//-- Default method after thread condition
			log.d("test3");
		}

		Handler myHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				log.d("test2 " + myProgress);
				//-- 5. Condition UI setup 
				if ( myProgress == 10 ) {
					tv.setText("myProgress : "+myProgress);
					log.d("Condition UI setup");
				}
				//--
			}
		};
	};
	
	private void setupButton(){
		Button bt = (Button)findViewById(R.id.button);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//-- 0. Thread controller -- Flag : Start
				flag = true;
				//--
				new Thread(myThread).start();
				
			}
		});
		
		Button cbt = (Button)findViewById(R.id.clearButton);
		cbt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//-- 0. Thread controller -- Flag : Stop
				flag = false;
				//--
				myProgress = 0;
				
				pb.setProgress(myProgress);

				TextView tv = (TextView) findViewById(R.id.text);
				tv.setText("myProgress : "+myProgress);
				
				
			}
		});
	}
    
    // ===========================================================
	// Vision Methods
	// ===========================================================
    private int getVerifyNumber(){
    	int result = 0;
    	
    	Date now = new Date();
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	int today = Integer.parseInt( dateFormat.format( now ) );
//    	log.d("Today : "+today);
    	double original = 1981*(Math.sin(today)+1);
    	result = (int) original;
    	
//    	log.d(result);
    	
    	return result;
    }
    
    ArrayList channelAttributesList;
    ArrayList channelContentsList;
    private void readRSSSourceXML(String input){
        try {
            // init reader
        	SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            // Create DefaultHandler Instance
            RSSSourceXMLHandler xmlHandler = new RSSSourceXMLHandler();
            reader.setContentHandler(xmlHandler);
            reader.setErrorHandler(xmlHandler);
            // Start Analyze
            URL url = new URL(input);
        	InputSource is = new InputSource(url.openStream());
            reader.parse(is);
            
			// get info
			channelAttributesList = xmlHandler.channelAttributesList;
			for (int i = 0; i < channelAttributesList.size(); i++) {
				HashMap<String, String> hm = (HashMap) channelAttributesList
						.get(i);
				for (String key : hm.keySet()) {
//					log.d(key + " : " + hm.get(key));
				}
			}

			// get item
			channelContentsList = xmlHandler.channelContentsList;
			for (int i = 0; i < channelContentsList.size(); i++) {
				HashMap<String, String> hm = (HashMap) channelContentsList
						.get(i);
				for (String key : hm.keySet()) {
//					log.d(key + " : " + hm.get(key));
				}
			}
            
            
        } catch ( IOException e ) {
            log.d("Error on Read : " + e.getMessage());
        } catch ( SAXException e ) {
            log.d("Error on Analyze : " + e.getMessage());
        } catch (ParserConfigurationException e) {
            log.d("Error on Factory : " + e.getMessage());
		} 
    }
    
    private List<News> getRss(String path)
    {
      List<News> data=new ArrayList<News>();
      URL url = null;     
      try
      {  
        url = new URL(path);
        /* 產生SAXParser物件 */ 
        SAXParserFactory spf = SAXParserFactory.newInstance(); 
        SAXParser sp = spf.newSAXParser(); 
        /* 產生XMLReader物件 */ 
        XMLReader xr = sp.getXMLReader(); 
        /* 設定自定義的MyHandler給XMLReader */ 
        RSSXMLHandler handler = new RSSXMLHandler(); 
        xr.setContentHandler(handler);     
        /* 解析XML */
        xr.parse(new InputSource(url.openStream()));
        /* 取得RSS標題與內容列表 */
        data = handler.getParsedData(); 
//        log.d("handler.getRssTitle() "+handler.getRssTitle());
        for(int i = 0 ; i < data.size() ;i++){
//        	log.d(data.get(i));
        	News news = data.get(i);
//        	log.d("news "+news.getTitle());
        }
        
      }
      catch (Exception e)
      { 
        
      }
      return data;
    }
    
    
	// ===========================================================
	// Methods
	// ===========================================================
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