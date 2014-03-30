package com.tuncay.dragonvsplanes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tuncay.dragonvsplanes.R;

public class MainMenuResumeActivity extends MainMenuActivity{

	private static final String TAG = MainMenuResumeActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
            	        
        setContentView(R.layout.main_menu_resume);
        Log.d(TAG, "main menu viewed");
        
     // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("D40614808A0D116B440AFCD00348185F")
        .build();
        adView.loadAd(adRequest);
        Log.d(TAG, "adview viewed");
	}
	
	public void onClickResume(View view) {
		Log.d(TAG, "resume basla");  		
		this.finish();				
		Log.d(TAG, "resume bitti");  
	}	

	@Override
	protected void onStart() {
		super.onStart();
		//for google analytics
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//for google analytics
		EasyTracker.getInstance(this).activityStop(this);
	}
}
