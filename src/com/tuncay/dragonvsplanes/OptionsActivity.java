package com.tuncay.dragonvsplanes;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.parameter.Parameter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;

public class OptionsActivity extends Activity{
	
	private static final String TAG = OptionsActivity.class.getSimpleName();
	boolean ilkEkran;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
            	        
        setContentView(R.layout.options);
        
        Intent intent = getIntent();
        ilkEkran = intent.getBooleanExtra("ilkekran", false);  
        if (ilkEkran){
        	Button btnOptions = (Button)this.findViewById(R.id.btnOptions);
        	btnOptions.setText("Continue");
        }
        
        /*RadioButton soundOn = (RadioButton) this.findViewById(R.id.radSoundOn);
        soundOn.setChecked(true);
        RadioButton medium = (RadioButton) this.findViewById(R.id.radMedium);
        medium.setChecked(true);*/
        
     // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("D40614808A0D116B440AFCD00348185F")
        .build();
        adView.loadAd(adRequest);
        Log.d(TAG, "adview viewed");
        
        Log.d(TAG, "OptionsActivity viewed");             
	}
	
	public void onSoundRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radSoundOn:
	            if (checked)
	                Parameter.soundOn = true;
	            break;
	        case R.id.radSoundOff:
	            if (checked)
	                Parameter.soundOn = false;
	            break;	      
	        default:
	        	Parameter.soundOn = true;
	        	break;
	    }
	}
	
	public void onSpeedRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radSlow:
	            if (checked)
	            	Parameter.MAX_FPS = 25;
	            break;
	        case R.id.radMedium:
	            if (checked)
	                Parameter.MAX_FPS = 35;
	            break;
	        case R.id.radFast:
	            if (checked)
	            	Parameter.MAX_FPS = 45;
	            break;
	        default:
	        	Parameter.MAX_FPS = 35;
	        	break;
	    }
	}
	public void onClickBtnOptions (View view){
		if (ilkEkran){
			Intent intent = new Intent(this, GameInfoActivity.class);	
			intent.putExtra("ilkekran", true);
			startActivity(intent);
		}
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
