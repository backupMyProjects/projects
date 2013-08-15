package cc.nexdoor.ct.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.nexdoor.android.module.LogD;
import cc.nexdoor.android.module.News;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class NewsAdapter extends SimpleAdapter {
	// ===========================================================
	// Fields
	// ===========================================================
	private String TAG = "CTHome:NewsAdapter";
	private LogD log = new LogD(TAG, true);
	private final int TRUE = -1;
	private final int FALSE = 0;
	private final int fill_parent = -1;
	private final int wrap_content = -2;

	public NewsAdapter(
			Context context, 
			List<? extends Map<String, ?>> data,
			int resource, 
			String[] from, 
			int[] to) {
		super(context, data, resource, from, to);
		
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.from = from;
		this.to = to;
	}
	
	Context context;
	List<? extends Map<String, ?>> data;
	int resource;
	String[] from;
	int[] to;
	
	ImageView ivIcon;
	ImageView ivThumb;
	int pos;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		return super.getView(position, convertView, parent);
		
		LayoutInflater factory = LayoutInflater.from(context);
		
		//--
		/* 使用xml為每一個item的Layout */
		View v = (View) factory.inflate(resource, null);
		/* 取得View */
		ivIcon = (ImageView) v.findViewById(to[0]);
	    TextView tvTitle = (TextView) v.findViewById(to[1]);
	    TextView tvDesc = (TextView) v.findViewById(to[2]);
	    ivThumb = (ImageView) v.findViewById(to[3]);
	    
//	    log.d( data.get(position) );
	    
//	    URI uri = new URI( ((News)data.get(position)).getEnclosureURL() );
//	    ivIcon.setImageURI( Uri.parse(((News)data.get(position)).getEnclosureURL()) );
	    News news = (News)data.get(position);
	    if ( !"".equals( news.getEnclosureURL() ) && "image/jpeg".equals( news.getEnclosureTYPE() ) ){
	    	RelativeLayout.LayoutParams paramIcon = new RelativeLayout.LayoutParams(58, 58);
	    		paramIcon.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
	    		paramIcon.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
	    	
			ivIcon.setLayoutParams(paramIcon);
		    ivIcon.setImageBitmap( ((News)data.get(position)).getBitmap() );
	    } else if ( !"".equals( news.getThumb() ) && "video/mp4".equals( news.getEnclosureTYPE() ) ) {
	    	RelativeLayout.LayoutParams paramIcon = new RelativeLayout.LayoutParams(58, 58);
	    		paramIcon.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
	    		paramIcon.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
	    	
			ivIcon.setLayoutParams(paramIcon);
		    ivIcon.setImageBitmap( ((News)data.get(position)).getBitmap() );
	    }
	    
	    tvTitle.setText( ((News)data.get(position)).getTitle() );
	    
	    String description = stripHTMLTag(((News)data.get(position)).getDesc());
	    if( description.length() > 40 ){
            description = description.substring(0, 40) + "...";
        }
	    tvDesc.setText( description );
		
		return v;
	}
	
	
	//-- function
	
	public static String stripHTMLTag(String srcStr){
		String regex = "<(?![!/]?[ABIU][>\\s])[^>]*>|&nbsp;";
		return srcStr.replaceAll(regex, "");
	}
	
//	public Bitmap returnBitMap(String url) { 
//    	URL myFileUrl = null;
//    	Bitmap bitmap = null;
//    	
//    	try{
//    		if ( !"".equals(url) ){
//        		myFileUrl = new URL(url);
//    		}
//    	}catch(MalformedURLException e){
//    		e.printStackTrace();
//    	}
//    	
//    	try{
//    		if ( null != myFileUrl){
//    			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//        		conn.setDoInput(true);
//        		conn.connect();
//        		InputStream is = conn.getInputStream();
//        		bitmap = BitmapFactory.decodeStream(is);
//        		is.close();
//    		}
//    		
//    	}catch(IOException e){
//    		e.printStackTrace();
//    	}
//    	return bitmap;
//    }
	
	
	//----


}
