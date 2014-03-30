package com.tuncay.dragonvsplanes;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.dblayer.CustomDBAdapter;
import com.tuncay.dragonvsplanes.dblayer.GlobalDbInsertScore;
import com.tuncay.dragonvsplanes.math.Tools;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends Activity{
	
	private static final String TAG = GameOverActivity.class.getSimpleName();
	CustomDBAdapter dbAdapter;
	Cursor cur;
	
	int points;
	private InterstitialAd interstitial;
	private AdRequest adRequest;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
        points = Tools.points;        
        setContentView(R.layout.game_over);
        
        dbAdapter = new CustomDBAdapter(this);
        
        EditText editText = (EditText) this.findViewById(R.id.edtName);
    	//editText.setVisibility(View.GONE);        
        
        StringBuilder text = new StringBuilder("You finished the game with ");	
        text.append(Integer.toString(points));
        text.append(" points.");
        if (dbAdapter.isScoreInTop(points)) {
        	text.append(" One of the best scores! Enter your name:");
        }else{
        	text.append(" Enter your name:");
        }
        //editText.setVisibility(View.VISIBLE);
        
        try {
			cur = dbAdapter.getLastEnteredName();
			if (cur == null || cur.getCount() == 0){
				editText.setText("Player");
			}else{
				if (cur.moveToFirst()){
					Log.d(TAG, "cursor name " + cur.getString(cur.getColumnIndex("name")));
					editText.setText(cur.getString(cur.getColumnIndex("name")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			editText.setText("Player");
		}finally{
			cur.close();
		}
        
		TextView textView = (TextView) this.findViewById(R.id.TextView02);		
		textView.setText(text.toString());
		       
        Log.d(TAG, "GameOverActivity created");             
     
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-5819132225601729/4240051093");

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
        		adButton = (Button)findViewById(R.id.btnInterstitial2);
        		adButton.setVisibility(View.VISIBLE);
        	}
        	
        	@Override
        	public void onAdClosed() {
        		// TODO Auto-generated method stub
        		super.onAdClosed();
        		adButton = (Button)findViewById(R.id.btnInterstitial2);
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
	
	public void onClickOk(View view){
		String str = ((EditText)this.findViewById(R.id.edtName)).getText().toString();
		dbAdapter.insertScore(str, points);				
		new GlobalDbInsertScore(this,str,points).execute();
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
