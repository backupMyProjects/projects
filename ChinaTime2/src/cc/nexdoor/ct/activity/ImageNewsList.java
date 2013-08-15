package cc.nexdoor.ct.activity;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cc.nexdoor.ct.activity.R;
import cc.nexdoor.ct.adapter.ImageNewsGridViewAdapter;
import cc.nexdoor.ct.adapter.ModuleMenuGridViewAdapter;
import cc.nexdoor.ct.adapter.NewsAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.VideoView;
import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;
import cc.nexdoor.android.module.RSSSourceXMLHandler;
import cc.nexdoor.android.module.RSSXMLHandler;

public class ImageNewsList extends Activity {
//public class FocusnewsList extends Activity implements TabContentFactory {
    /** Called when the activity is first created. */
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String TAG = "CTHome:ImageNewsList";
	private LogD log = new LogD(TAG, true);
	private final int TRUE = -1;
	private final int FALSE = 0;
	private final int fill_parent = -1;
	private final int wrap_content = -2;
	private ProgressBar pb;
	

	// ===========================================================
	// Override Methods
	// ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInvisibleStatusbar_Titlebar();
        setContentView(R.layout.imagenewslist);
        

        //--
//        pb = (ProgressBar)findViewById(R.id.progress_small_title);
        //--
        
        setupBannerAction();
    	setRSSList();
    	
        setupButtonItems();
        initSelectButton();
        

    }
    
    // ===========================================================
	// Vision Methods
	// ===========================================================
    
    private void setupBannerAction(){
    	ImageButton modulemenu = (ImageButton)findViewById(R.id.modulemenu);
    	
    	modulemenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//--------------------------------//
				back2Activity();
				//--------------------------------//
			}
		});
    	
    	Bundle bundle = this.getIntent().getExtras();
    	TextView moduletitle = (TextView)findViewById(R.id.moduletitle);
    	moduletitle.setText(bundle.getInt("targetTitle"));
    	
    }
    


	private List<Button> buttonList = new ArrayList();
    private void setupButtonItems(){
    	
    	LinearLayout ll = (LinearLayout)findViewById(R.id.itemInnerLayout);
    	
    	for( int i = 0 ; i < titleList.size(); i++){
    		final int tempFlag = i;
    		Button bt = new Button(this);
    		bt.setText(titleList.get(i));
    		bt.setTextColor(Color.WHITE);
    		bt.setBackgroundColor(0);
    		RelativeLayout.LayoutParams paramButton = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
//    		paramButton.setMargins(5, 5, 5, 5);
//    		bt.setPadding(5, 5, 5, 5);
    		ll.addView(bt, paramButton);
    		
    		bt.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				//--------------------------------//
    				DownloadNewsListJob(tempFlag);
    				//--------------------------------//
    			}
    		});
    		
    		buttonList.add(bt);
    	}
    	
    	
    }
    
    private List newsList;
    private void setNewsList(int flag){
    	
    	final String[] item = { "newsIcon", "newsTitle", "newsContent", "newsMediaThumb"};
    	ListView cl = (ListView)findViewById(R.id.contentList);
    	cl.setCacheColorHint(0);
    	cl.setBackgroundColor(0);
    	
//    	newsData = getNewsItemList(flag);
    	NewsAdapter adapter = new NewsAdapter(
    			this,
    			newsList,// Data Source
                R.layout.row4news,//item 
                null,// item map
                new int[] { R.id.newsIcon, R.id.newsTitle, R.id.newsContent, R.id.newsMediaThumb }
    			);
    	cl.setAdapter(adapter);
    	
		cl.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// --------------------------------//
				News news = (News)newsList.get(arg2);
				
				if ( "video/mp4".equals(news.getEnclosureTYPE()) ) {
					// TODO : Play video
//					Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse( news.getEnclosureURL() ) );
//					Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("http://www.dubblogs.cc:8751/Android/Test/Media/mp4/test.mp4") );
					Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("http://192.168.1.23:8080/docs/convert.mp4") );
					startActivity(intent);
					
//		    		Bundle bundle = new Bundle();
//					bundle.putString("url", news.getEnclosureURL());
//					jump2Activity( video.class, bundle);
					
		    	} else {
		    		Bundle bundle = new Bundle();
					bundle.putString("title", news.getTitle());
					bundle.putString("desc", news.getDesc());
					bundle.putString("link", news.getLink());
					bundle.putString("date", news.getDate());
					if ( !"".equals(news.getEnclosureURL()) ) {
//						log.d("news.getEnclosureURL() " + news.getEnclosureURL());
						bundle.putByteArray("bitmap", convertBitmapToByteArray(news.getBitmap()) );
					}
					jump2Activity( GenerNewsItem.class, bundle);
		    	}
				
				// --------------------------------//

			}
		});
    	
    }
    
//    ArrayList<Bitmap> imageNews;
    private void setupGrid(int flag){
//    	int[] icons = { R.drawable.focusnews, R.drawable.camera, R.drawable.video
//    			, R.drawable.ctitv, R.drawable.news, R.drawable.showbiz
//    			, R.drawable.life, R.drawable.money, R.drawable.blog};
//    	final int[] items = { R.string.focusnews, R.string.camera, R.string.video
//    			, R.string.ctitv, R.string.news, R.string.showbiz
//    			, R.string.life, R.string.money, R.string.blog};
    	
    	GridView gv;
        gv = (GridView) findViewById(R.id.imagenewsContent);
    	ImageNewsGridViewAdapter adapter = new ImageNewsGridViewAdapter(this, newsList);
    	gv.setAdapter(adapter);
    	
    }
    
//    private 

        
    
    int selectedFlag = -1;
    //-- 0. Thread controller -- Flag
    boolean finished = false;
    private void DownloadNewsListJob(final int tempFlag){
    	finished = false;
    	selectedFlag = tempFlag;
    	pb = (ProgressBar)findViewById(R.id.progress_small_title);
		handleSelectButtonStyle();
    	
    	log.d("selectedFlag : "+selectedFlag);
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
    					
        				newsList = getNewsList(tempFlag);
        				newsList = downloadNewsImages(newsList);
        				
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
//    					setNewsList(tempFlag);
    					setupGrid(tempFlag);
    					log.d("Condition UI setup");
    					
    					if( pb.isShown() ){
    						pb.setVisibility(View.INVISIBLE);
    					}
    					
    				}else{
    					if( !pb.isShown() ){
    						pb.setVisibility(View.VISIBLE);
    					}
    				}
    				
    				
    				//--
    			}
    		};
    	};
    	
    	Thread job = new Thread(myThread);
    	job.start();
//    	new Thread(myThread).start();
		
    }
    
    
    
    
    
    private void handleSelectButtonStyle(){
    	if ( -1 != selectedFlag ){
//    		Button bt = buttonList.get(selectedFlag);
    		
    		for( int i = 0 ; i < buttonList.size() ; i++ ) {
    			Button bt = buttonList.get(i);
    			bt.setBackgroundColor(0);
    			if ( i == selectedFlag ) {
    				bt.setBackgroundColor(R.color.ctheavyblue);
    			}
    		}
    		
    	}
    }
    
    private void initSelectButton(){
    	if ( -1 == selectedFlag ){
    		selectedFlag = 0;
			DownloadNewsListJob(selectedFlag);
    	}
    }
  
    
  
    
    
    
    
    
    
    
	// ===========================================================
	// Methods
	// ===========================================================   
    private byte[] convertBitmapToByteArray(Bitmap src) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		src.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, (OutputStream) os);
		return os.toByteArray();
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
    
    //------ Heavy Job
    private List<News> downloadNewsImages(List newsList){
    	List result = new ArrayList();

    	for(int i = 0 ; i < newsList.size() ; i++){
    		News news = (News)newsList.get(i);
    		log.d("getEnclosureURL |"+news.getEnclosureURL()+"|");
    		log.d("getThumb |"+news.getThumb()+"|");
    		if ( !"".equals(news.getEnclosureURL()) && "image/jpeg".equals(news.getEnclosureTYPE()) ) {
    			Bitmap bitmap = returnBitMap( news.getThumb() );
    			news.setBitmap(bitmap);
    		}
			result.add(news);
    	}
    	
		
    	return result;
    }
    
    private List getNewsList(Integer flag){
    	List<News> data = new ArrayList<News>();
    	RSSXMLHandler rssItem = new RSSXMLHandler();
    	data = rssItem.getRss(linkList.get(flag));
    	log.d("Get : "+linkList.get(flag));
		return data;
    }
  //------ Heavy Job
    
    Bundle bundle;
    ArrayList<String> titleList = new ArrayList();
    ArrayList<String> linkList = new ArrayList();
    private void setRSSList(){
    	Bundle bundle = this.getIntent().getExtras();
        for (String key : bundle.keySet()) {
//        	log.d(key + " : " + bundle.get(key));
//        	log.d("title : "+key.substring(key.indexOf("_")+1));
        	
        	if ( "targetTitle".equals(key) ) {
        		//escape
        	}else{
        		titleList.add(key.substring(key.indexOf("_")+1));
            	
            	String link = bundle.getString(key);
            	if ( link.contains("?") ) {
            		link += "&v="+getVerifyNumber();
            	}else{
            		link += "?v="+getVerifyNumber();
            	}
            	linkList.add(link);
            	log.d("link : "+link);
        	}
        	
        	
        }
    	
    }
    
    private int getVerifyNumber(){
    	int result = 0;
    	
    	Date now = new Date();
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	int today = Integer.parseInt( dateFormat.format( now ) );
//    	log.d("Today : "+today);
    	double original = 1981*(Math.sin(today)+1);
    	result = (int) original;
    	
//    	log.d("check number : "+result);
    	
    	return result;
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