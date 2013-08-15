package com.golive.component;

import com.golive.R;
import com.golive.GoLiveActivity;
import static com.golive.util.Constants.*;

import android.app.Dialog;    
import android.content.Context;    
import android.text.Editable;
import android.util.Log;
import android.view.View;    
import android.view.ViewGroup;
import android.view.Window;    
import android.view.View.OnClickListener;    
import android.widget.*; 
import android.widget.AdapterView.OnItemClickListener;
    
/** Class Must extends with Dialog */    
/** Implement onClickListener to dismiss dialog when OK Button is pressed */    
public class ImageGridDialog extends Dialog {    
    TextView title;
    
    
    
    public ImageGridDialog(final GoLiveActivity context) {    
        super(context);    
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the title */    
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        /** Design the dialog in main.xml file */    
        setContentView(R.layout.list_dialog);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(context));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        		Log.d("IMAGEGRID", "" + mThumbIds[position]);
	        		// To-Do
	        		//context.imageGridItemHandler(mThumbIds[position]);
	        }
	    });
        title = (TextView) findViewById(R.id.Title);
    }
    
    public void setTitle(String input){
		title.setText(input);
    }
    
    public void setTitle(int resID){
		title.setText(resID);
    }
    
    
 // Inner Class : Grid View Adapter
 	// @Self-Define
 	public class ImageAdapter extends BaseAdapter {
 	    private Context mContext;

 	    public ImageAdapter(Context c) {
 	        mContext = c;
 	    }

 	    public int getCount() {
 	        return mThumbIds.length;
 	    }

 	    public Object getItem(int position) {
 	        return null;
 	    }

 	    public long getItemId(int position) {
 	        return 0;
 	    }

 	    // create a new ImageView for each item referenced by the Adapter
 	    public View getView(int position, View convertView, ViewGroup parent) {
 	        ImageView imageView;
 	        if (convertView == null) {  // if it's not recycled, initialize some attributes
 	            imageView = new ImageView(mContext);
 	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
 	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
 	            imageView.setPadding(4, 4, 4, 4);
 	        } else {
 	            imageView = (ImageView) convertView;
 	        }

 	        imageView.setImageResource(mThumbIds[position]);
 	        return imageView;
 	    }

 	    // references to our images
 	    
 	}
    
}    