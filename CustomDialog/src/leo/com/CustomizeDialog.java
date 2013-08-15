package leo.com;

import android.app.Dialog;    
import android.content.Context;    
import android.view.View;    
import android.view.Window;    
import android.view.View.OnClickListener;    
import android.widget.*; 
    
/** Class Must extends with Dialog */    
/** Implement onClickListener to dismiss dialog when OK Button is pressed */    
public class CustomizeDialog extends Dialog implements OnClickListener {    
    Button yesB;
    Button noB;
    
    public CustomizeDialog(Context context) {    
        super(context);    
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the title */    
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        /** Design the dialog in main.xml file */    
        setContentView(R.layout.dialog);
        yesB = (Button) findViewById(R.id.Yes);
        yesB.setOnClickListener(this);
        noB = (Button) findViewById(R.id.No);
        noB.setOnClickListener(this);
    }    
    
    public void setContent(String input){
    		TextView content = (TextView) findViewById(R.id.Content);
    		content.setText(input);
    }
    
    public void setTitle(String input){
		TextView title = (TextView) findViewById(R.id.Title);
		title.setText(input);
    }
    
    public Button getYesButton(){
    		return yesB;
    }
    public Button getNoButton(){
		return noB;
    }
    
    public void setYesButton(String content, View.OnClickListener listener){
    		yesB.setOnClickListener(listener);
    		yesB.setText(content);
    }
    
    public void setNoButton(String content, View.OnClickListener listener){
		noB.setOnClickListener(listener);
		noB.setText(content);
    }
    
    public void setYesButton(int resID, View.OnClickListener listener){
		yesB.setOnClickListener(listener);
		yesB.setText(resID);
	}
	
	public void setNoButton(int resID, View.OnClickListener listener){
		noB.setOnClickListener(listener);
		noB.setText(resID);
	}
    
    @Override    
    public void onClick(View v) { 
        if (v == yesB){
        		dismiss();
        }else if( v == noB ){
        		dismiss();
        }
    }    
    
}    