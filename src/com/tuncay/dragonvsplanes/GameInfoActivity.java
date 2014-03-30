package com.tuncay.dragonvsplanes;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tuncay.dragonvsplanes.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class GameInfoActivity extends Activity{
	
	private static final String TAG = GameInfoActivity.class.getSimpleName();

	boolean ilkEkran;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
            	        
        setContentView(R.layout.gameinfo);
        
        Intent intent = getIntent();
        ilkEkran = intent.getBooleanExtra("ilkekran", false);  
        if (ilkEkran){
        	Button btGameRules = (Button)this.findViewById(R.id.btnGameInfo);
        	btGameRules.setText("Start Game");
        }
        
     // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
    		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
    		.addTestDevice("D40614808A0D116B440AFCD00348185F")
    		.build();
        adView.loadAd(adRequest);
        Log.d(TAG, "adview viewed");
        
        Log.d(TAG, "game info viewed");             
	}
	
	public void onClickBtnGameInfo (View view){
		if (ilkEkran){
			Intent intent = new Intent(this, BubblesEraActivity.class);	
			startActivity(intent);
		}
		this.finish();
	}
	
	public void onClickBtnDemoGameInfo (View view){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.youtube.com/watch?v=ydBP1lyoNE0"));
		startActivity(intent);

		this.finish();
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
