package com.tuncay.dragonvsplanes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainMenuStartActivity extends MainMenuActivity{

	private static final String TAG = MainMenuStartActivity.class.getSimpleName();
	
	private InterstitialAd interstitial;

	private AdRequest adRequest;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
            	        
        setContentView(R.layout.main_menu_start);
        Log.d(TAG, "main menu viewed");             
        
     // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder()
        	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        	.addTestDevice("D40614808A0D116B440AFCD00348185F")
        	.build();
        adView.loadAd(adRequest1);

        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-5819132225601729/4499233098");

        // Create ad request.
        adRequest = new AdRequest.Builder()
        	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("D40614808A0D116B440AFCD00348185F")
            .build();

        // Set an AdListener.
        interstitial.setAdListener(new AdListener() {
        	private Button adButton;

			@Override
        	public void onAdLoaded(){
        		adButton = (Button)findViewById(R.id.btnInterstitial);
        		adButton.setVisibility(View.VISIBLE);
        	}
        	
        	@Override
        	public void onAdClosed() {
        		// TODO Auto-generated method stub
        		super.onAdClosed();
        		adButton = (Button)findViewById(R.id.btnInterstitial);
        		adButton.setVisibility(View.GONE);
        		interstitial.loadAd(adRequest);
                Log.d(TAG, "interstitial loaded");
        	}
        	
		});
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
        Log.d(TAG, "interstitial loaded");
	}

	public void onClickInterstitialAd(View view) {		
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}

	public void onClickNewGame(View view) {		
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra("ilkekran", true);
		startActivity(intent);
		
		//Intent intent = new Intent(this, BubblesEraActivity.class);	
		//startActivity(intent);
	}
	public void onClickExit(View view) {
		this.finish();		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//this.finish();
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
