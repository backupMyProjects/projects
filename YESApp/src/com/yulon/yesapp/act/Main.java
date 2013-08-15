package com.yulon.yesapp.act;

import static com.leo.android.util.Constants.*;
import com.leo.android.util.*;
import com.leo.android.util.interfaces.UtilActivity;
import com.yulon.yesapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class Main extends Activity implements UtilActivity {
	CommonFunction cf = new CommonFunction(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(R.layout.main);
	}

	void findView() {
	}

	void componentSetup() {
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {

		switch (cf.checkMotion(me)) {
		case Left:
			Log.v("onTouchEvent", "to Left");
			ll_ContentChield_1.setVisibility(View.GONE);
			ll_ContentChield_2.setVisibility(View.GONE);
			ll_ContentChield_3.setVisibility(View.VISIBLE);
			ll_ContentChield_4.setVisibility(View.VISIBLE);
			break;
		case Right:
			Log.v("onTouchEvent", "to Right");
			ll_ContentChield_1.setVisibility(View.VISIBLE);
			ll_ContentChield_2.setVisibility(View.VISIBLE);
			ll_ContentChield_3.setVisibility(View.GONE);
			ll_ContentChield_4.setVisibility(View.GONE);
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void init(int setLayoutId) {
		setContentView(R.layout.main);
		findViews();
		setupViews();
		
	}


	// View:
	Button btn_battery, btn_reservation_navi, btn_fastbooking, btn_booking_list, btn_airconon, btn_recommendation,
	btn_charging_history, btn_history_message, btn_customer_service, btn_qrcode;
	LinearLayout ll_ContentChield_1, ll_ContentChield_2, ll_ContentChield_3, ll_ContentChield_4;
	@Override
	public void findViews() {
		btn_battery = (Button) findViewById(R.id.btn_battery);
		btn_reservation_navi = (Button) findViewById(R.id.btn_reservation_navi);
		btn_fastbooking = (Button) findViewById(R.id.btn_fastbooking);
		btn_booking_list = (Button) findViewById(R.id.btn_booking_list);
		btn_airconon = (Button) findViewById(R.id.btn_airconon);
		btn_recommendation = (Button) findViewById(R.id.btn_recommendation);
		btn_charging_history = (Button) findViewById(R.id.btn_charging_history);
		btn_history_message = (Button) findViewById(R.id.btn_history_message);
		btn_customer_service = (Button) findViewById(R.id.btn_customer_service);
		btn_qrcode = (Button) findViewById(R.id.btn_qrcode);
		ll_ContentChield_1 = (LinearLayout) findViewById(R.id.ll_ContentChield_1);
		ll_ContentChield_2 = (LinearLayout) findViewById(R.id.ll_ContentChield_2);
		ll_ContentChield_3 = (LinearLayout) findViewById(R.id.ll_ContentChield_3);
		ll_ContentChield_4 = (LinearLayout) findViewById(R.id.ll_ContentChield_4);
		
	}

	@Override
	public void setupViews() {
		btn_battery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.slide2Activity(Battery.class, new Bundle(), false);
				// --------------------------------//
			}
		});
		btn_reservation_navi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.slide2Activity(Reservation.class, new Bundle(), false);
				// --------------------------------//
			}
		});
		btn_fastbooking.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.slide2Activity(FastBooking.class, new Bundle(), false);
				// --------------------------------//
			}
		});
		btn_booking_list.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// --------------------------------//
			}
		});
		btn_airconon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// --------------------------------//
			}
		});
		btn_recommendation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// --------------------------------//
			}
		});
		btn_charging_history.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.slide2Activity(ChargingHistory.class, new Bundle(), false);
				// --------------------------------//
			}
		});
		btn_history_message.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				cf.slide2Activity(HistoryMessage.class, new Bundle(), false);
				// --------------------------------//
			}
		});
		btn_customer_service.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// --------------------------------//
			}
		});
		btn_qrcode.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// --------------------------------//
				// --------------------------------//
			}
		});
		
	}

}
