package cc.nexdoor.ct.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import cc.nexdoor.ct.activity.CTHome;
import cc.nexdoor.ct.activity.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/* 自定義Adapter，繼承BaseAdapter */
public class ModuleMenuGridViewAdapter extends BaseAdapter 
{ 
  private Context activity;
  private int[] items;
  private int[] icons;
  private ModuleMenuGridAction myGridAction;

  public ModuleMenuGridViewAdapter(CTHome activity,int[] items,int[] icons) {
	  this.activity = activity;
	  this.items = items;
	  this.icons = icons;
	  this.myGridAction = new ModuleMenuGridAction(activity);
  }
  
//  public MyGridViewAdapter(CTHome activity,int[] items,int[] icons, MyGridAction myGridAction) {
//	  this.activity = activity;
//	  this.items = items;
//	  this.icons = icons;
////	  this.myGridAction = myGridAction;
//  }

  @Override
  public int getCount()
  {
    return items.length;
  }

  @Override
  public Object getItem(int arg0)
  {
    return items[arg0];
  }

  @Override
  public long getItemId(int position)
  {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent)
  {
    LayoutInflater factory = LayoutInflater.from(activity);
    /* 使用grid.xml為每一個item的Layout */
    View v = (View) factory.inflate(R.layout.grid4modulemenu, null);
    /* 取得View */
    final ImageButton ib = (ImageButton) v.findViewById(R.id.icon);
    TextView tv = (TextView) v.findViewById(R.id.text);
    /* 設定顯示的Image與文字 */
    ib.setBackgroundColor(0);
    ib.setImageResource(icons[position]);
    tv.setText(items[position]);
    
    
    ib.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        	/*
        	 * Action
        	 */
        	// --------------------------------//
			HashMap<String, Object> args = new HashMap();
        	args.put("logInfo", position);
        	args.put("module", items[position]);
        	myGridAction.actionRegister(args);
			// --------------------------------//
        	
        }
      }
    );
    
    return v;


  } 
} 