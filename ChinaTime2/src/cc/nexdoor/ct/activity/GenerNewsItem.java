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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;
import cc.nexdoor.android.module.RSSSourceXMLHandler;
import cc.nexdoor.android.module.RSSXMLHandler;

public class GenerNewsItem extends Activity {
    /** Called when the activity is first created. */
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String TAG = "CTHome:GenerNewsItem";
	private LogD log = new LogD(TAG, true);
	private final int TRUE = -1;
	private final int FALSE = 0;
	

	// ===========================================================
	// Override Methods
	// ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInvisibleStatusbar_Titlebar();
        setContentView(R.layout.genernewsitem);
        
        setupBannerAction();
        setupViewItems();

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
    	Bundle bundle = this.getIntent().getExtras();

    	if ( bundle.getByteArray("bitmap") != null ) {
    		ImageView ivView = (ImageView)findViewById(R.id.itemView);
    		RelativeLayout.LayoutParams paramImage = new RelativeLayout.LayoutParams(240, 240);
    			paramImage.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
	    		paramImage.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
    		ivView.setLayoutParams( paramImage );
        	ivView.setImageBitmap( getBitMapFromByteArray(bundle.getByteArray("bitmap")) );
    	}
    	
    	TextView tvTitle = (TextView)findViewById(R.id.itemTitle);
    	tvTitle.setText( bundle.getString("title") );

    	TextView tvDate = (TextView)findViewById(R.id.itemDate);
    	tvDate.setText( bundle.getString("date") );
    	
    	TextView tvDesc = (TextView)findViewById(R.id.itemDesc);
    	tvDesc.setText( stripHTMLTag(bundle.getString("desc")) );
    }
   
    
    
	// ===========================================================
	// Methods
	// ===========================================================
    private Bitmap getBitMapFromByteArray(byte[] src) {
		return BitmapFactory.decodeByteArray(src, 0, src.length);
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