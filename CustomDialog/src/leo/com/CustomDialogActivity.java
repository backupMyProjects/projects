package leo.com;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

  
    
public class CustomDialogActivity extends Activity {    
    /** Called when the activity is first created. */  
	Button button1, button2, button3;
	CustomizeDialog customizeDialog;
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);  
        
        setContentView(R.layout.main);
        
        button1 = (Button) findViewById(R.id.Button1);
        button2 = (Button) findViewById(R.id.Button2);
        button3 = (Button) findViewById(R.id.Button3);
        setButton();
        /** Display Custom Dialog */
        
        ImageGetter imgGetterHTML = new Html.ImageGetter() {
        		public Drawable getDrawable(String source) {
        			Drawable drawable = null;
        			drawable = Drawable.createFromPath(source);
        			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        			return drawable;
        		}
        	};
        	ImageGetter imgGetterRES = new ImageGetter() {  
                @Override
               public Drawable getDrawable(String source) {
	               int id = Integer.parseInt(source);
	               Drawable d = getResources().getDrawable(id);
	               d.setBounds(0, 0, d.getIntrinsicWidth(),d.getIntrinsicHeight());
	                return d;
               }
        };

        TextView text = (TextView) findViewById(R.id.test);
        text.append(Html.fromHtml("<img src='"+R.drawable.start+"'/>", imgGetterRES, null)); 
        System.out.println(text.getText());

		

		customizeDialog = new CustomizeDialog(this); 
        //CustomizeDialog customizeDialog = new CustomizeDialog(this);    
        //customizeDialog.show();    
    }
    
    private void setButton(){
    		button1.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				// --------------------------------//
    				//setContentView(R.layout.main);  
    				
    				Button yesB = customizeDialog.getYesButton();
    				yesB.setBackgroundResource(R.drawable.ic_launcher);
    		        customizeDialog.show();
    				// --------------------------------//
    			}
    		});
    		button2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				// --------------------------------//
    				customizeDialog.setTitle("main1");
    				setContentView(R.layout.main1);
    				// --------------------------------//
    			}
    		});
    		
    		button3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				// --------------------------------//
    				   
    		        /*
    				TextView test = (TextView) findViewById(R.id.test);
    				String str = "[smile]";
    		        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);  
    		        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());  
    		        //需要处理的文本，[smile]是需要被替代的文本  
    		        SpannableString spannable = new SpannableString(str);  
    		        //要让图片替代指定的文字就要用ImageSpan  
    		        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);  
    		        //开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）  
    		       //最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12  
    		        spannable.setSpan(span, 0,str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);   
    		        test.append(spannable);
    		        System.out.println(test.getText());
				*/

    				//customizeDialog.setTitle("main");
    				//setContentView(R.layout.main);
    				// --------------------------------//
    			}
    		});
    }

	@Override
	public void onContentChanged() {
		button1 = (Button) findViewById(R.id.Button1);
        button2 = (Button) findViewById(R.id.Button2);
        button3 = (Button) findViewById(R.id.Button3);
        setButton();
	}
    
}  