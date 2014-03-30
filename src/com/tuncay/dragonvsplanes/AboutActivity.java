package com.tuncay.dragonvsplanes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.analytics.tracking.android.EasyTracker;
import com.tuncay.dragonvsplanes.R;

public class AboutActivity extends Activity{
	
	private static final String TAG = AboutActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
            	        
        setContentView(R.layout.about);
        Log.d(TAG, "about activity viewed");             
	}
	
	public void onClickBtnAbout (View view){
		this.finish();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//for google analytics
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//for google analytics
		EasyTracker.getInstance(this).activityStop(this);
	}
}
