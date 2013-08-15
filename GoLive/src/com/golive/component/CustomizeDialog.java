package com.golive.component;

import com.golive.R;

import android.app.Dialog;    
import android.content.Context;    
import android.text.Editable;
import android.view.View;    
import android.view.Window;    
import android.view.View.OnClickListener;    
import android.widget.*; 
    
/** Class Must extends with Dialog */    
/** Implement onClickListener to dismiss dialog when OK Button is pressed */    
public class CustomizeDialog extends Dialog implements OnClickListener {    
    TextView title;
    TextView message;
    
    EditText inputET;
    ImageView imageIV;
    
    Button yesB;
    Button noB;
    
    public CustomizeDialog(Context context) {    
        super(context);    
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the title */    
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        /** Design the dialog in main.xml file */    
        setContentView(R.layout.dialog);
        
        title = (TextView) findViewById(R.id.Title);
        message = (TextView) findViewById(R.id.Message);
        
        inputET = (EditText) findViewById(R.id.InputET);
        imageIV = (ImageView) findViewById(R.id.ImageIV);

        yesB = (Button) findViewById(R.id.Yes);
        yesB.setOnClickListener(this);
        noB = (Button) findViewById(R.id.No);
        noB.setOnClickListener(this);
    }
    
    public void setTitle(String input){
		title.setText(input);
    }
    
    public void setTitle(int resID){
		title.setText(resID);
    }    
    
    public void setMessage(String input){
    		message.setText(input);
    }
    public void setMessage(int resID){
		message.setText(resID);
    }
    
    public EditText getInputET(){
		return inputET;
	}
    
	public ImageView getImageIV(){
		return imageIV;
	}
    
    public Button getYesButton(){
    		return yesB;
    }
    public Button getNoButton(){
		return noB;
    }
    
    public void setYesButtonText(String content){
    		yesB.setText(content);
    }
    
    public void setNoButtonText(String content){
		noB.setText(content);
    }
    
    public void setYesButtonText(int resID){
		yesB.setText(resID);
	}
	
	public void setNoButtonText(int resID){
		noB.setText(resID);
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