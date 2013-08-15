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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;
import cc.nexdoor.android.module.RSSSourceXMLHandler;
import cc.nexdoor.android.module.RSSXMLHandler;

public class video extends Activity {
    /** Called when the activity is first created. */
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String TAG = "CTHome:video";
	private LogD log = new LogD(TAG, true);
	

	// ===========================================================
	// Override Methods
	// ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setInvisibleStatusbar_Titlebar();
        
        setContentView(R.layout.video);
		VideoView videoView = (VideoView) findViewById(R.id.VideoView);
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		
		Bundle bundle = this.getIntent().getExtras();
		String url = bundle.getString("url");
		
		Uri video = Uri.parse(url);
//		Uri video = Uri.parse("http://www.dubblogs.cc:8751/Android/Test/Media/mp4/test.mp4");
//		Uri video = Uri.parse("http://192.168.1.23:8080/docs/convert.mp4");
		
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(video);
		videoView.start();
		

    }
    
    
    private void setInvisibleStatusbar_Titlebar(){
		final Window win = getWindow();
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
    
    
    
}