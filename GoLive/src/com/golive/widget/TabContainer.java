package com.golive.widget;

import com.golive.R;

import android.app.Activity;
import android.content.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.*;

public class TabContainer extends LinearLayout implements OnClickListener, OnTouchListener{

	private Context context;
	private View view;
	public Button talkIB, contactIB, findIB, settingIB;
	public LinearLayout talkLL, contactLL, findLL, settingLL;
	
	public TabContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.view = layoutInflater.inflate(R.layout.tab_container,this); 
		this.context = context;

		initComponent();

	}
	
    // Level 1 : init Component
	void initComponent(){
		findComponent();
		setComponent();
	}

    // Level 2 : find & set Component
	void findComponent(){
		talkIB    = (Button) findViewById(R.id.talkIB);
		contactIB = (Button) findViewById(R.id.contactIB);
		findIB    = (Button) findViewById(R.id.findIB);
		settingIB = (Button) findViewById(R.id.settingIB);
		
		talkLL    = (LinearLayout) findViewById(R.id.talkLL);
		contactLL = (LinearLayout) findViewById(R.id.contactLL);
		findLL    = (LinearLayout) findViewById(R.id.findLL);
		settingLL = (LinearLayout) findViewById(R.id.settingLL);
	}
	
	void setComponent(){
		// OnTouch
		talkIB.setOnTouchListener(this);
		contactIB.setOnTouchListener(this);
		findIB.setOnTouchListener(this);
		settingIB.setOnTouchListener(this);
		talkLL.setOnTouchListener(this);
		contactLL.setOnTouchListener(this);
		findLL.setOnTouchListener(this);
		settingLL.setOnTouchListener(this);
		
		// OnClick
		talkIB.setOnClickListener(this);
		contactIB.setOnClickListener(this);
		findIB.setOnClickListener(this);
		settingIB.setOnClickListener(this);
		talkLL.setOnClickListener(this);
		contactLL.setOnClickListener(this);
		findLL.setOnClickListener(this);
		settingLL.setOnClickListener(this);
	}
	

	// Level 3 : view / action Controller
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		onTouchTab(v);
		return false;
	}

	@Override
	public void onClick(View v) {
		onClickTab(v);
	}
	
	void onTouchTab(View v){
		resetTab();
		
		//Log.d("GoLiveTab", view.getId() +" "+ talkIB.getId());
		if ( v.getId() == talkIB.getId() || v.getId() == talkLL.getId() ){
			talkIB.setBackgroundResource(R.drawable.ico_talk_act);
			talkLL.setBackgroundColor(R.drawable.back_tab_click);
		}else if(  v.getId() == contactIB.getId() || v.getId() == contactLL.getId() ){
			contactIB.setBackgroundResource(R.drawable.ico_contact_act);
			contactLL.setBackgroundColor(R.drawable.back_tab_click);
		}else if(  v.getId() == findIB.getId() || v.getId() == findLL.getId() ){
			findIB.setBackgroundResource(R.drawable.bt_search);
			findLL.setBackgroundColor(R.drawable.back_tab_click);
		}else if(  v.getId() == settingIB.getId() || v.getId() == settingLL.getId() ){
			settingIB.setBackgroundResource(R.drawable.ico_setting_act);
			settingLL.setBackgroundColor(R.drawable.back_tab_click);
		}
	}

	
	void onClickTab(View v){
		resetTab();
		//onTouchTab(view);
		
		//Log.d("GoLiveTab", view.getId() +" "+ talkIB.getId());
		if ( v.getId() == talkIB.getId() || v.getId() == talkLL.getId() ){
			
			talkIB.setBackgroundResource(R.drawable.ico_talk_act);
			talkLL.setBackgroundResource(R.drawable.back_tab_click);
			
		}else if(  v.getId() == contactIB.getId() || v.getId() == contactLL.getId() ){

			contactIB.setBackgroundResource(R.drawable.ico_contact_act);
			contactLL.setBackgroundResource(R.drawable.back_tab_click);
			
		}else if(  v.getId() == findIB.getId() || v.getId() == findLL.getId() ){
			
			findIB.setBackgroundResource(R.drawable.bt_search);
			findLL.setBackgroundResource(R.drawable.back_tab_click);
			
		}else if(  v.getId() == settingIB.getId() || v.getId() == settingLL.getId() ){

			settingIB.setBackgroundResource(R.drawable.ico_setting_act);
			settingLL.setBackgroundResource(R.drawable.back_tab_click);
		}
	}
	
	void resetTab(){
		talkIB.setBackgroundResource(R.drawable.ico_talk);
		contactIB.setBackgroundResource(R.drawable.ico_contact);
		findIB.setBackgroundResource(R.drawable.bt_search);
		settingIB.setBackgroundResource(R.drawable.ico_setting);

		talkLL.setBackgroundColor(0);
		contactLL.setBackgroundColor(0);
		findLL.setBackgroundColor(0);
		settingLL.setBackgroundColor(0);
	}
	

	

}
