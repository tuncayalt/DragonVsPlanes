package com.tuncay.dragonvsplanes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tuncay.dragonvsplanes.model.components.GameSpeed;
import com.tuncay.dragonvsplanes.parameter.Parameter;

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

        setRadioButtons();
        
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
	
	private void setRadioButtons() {
		RadioButton radSlow = (RadioButton)findViewById(R.id.radSlow);
		RadioButton radMedium = (RadioButton)findViewById(R.id.radMedium);
		RadioButton radFast = (RadioButton)findViewById(R.id.radFast);
		
		RadioButton radSoundOn = (RadioButton)findViewById(R.id.radSoundOn);
		RadioButton radSoundOff = (RadioButton)findViewById(R.id.radSoundOff);
		
		switch (Parameter.gameSpeed){
		case Slow:
			radSlow.setChecked(true);
			radMedium.setChecked(false);
			radFast.setChecked(false);
			break;
		case Medium:
			radSlow.setChecked(false);
			radMedium.setChecked(true);
			radFast.setChecked(false);
			break;
		case Fast:
			radSlow.setChecked(false);
			radMedium.setChecked(false);
			radFast.setChecked(true);
			break;
		default:
			radSlow.setChecked(false);
			radMedium.setChecked(true);
			radFast.setChecked(false);
			break;
		}
		
		if (Parameter.soundOn){
			radSoundOn.setChecked(true);
			radSoundOff.setChecked(false);
		}else{
			radSoundOn.setChecked(false);
			radSoundOff.setChecked(true);
		}
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
	            	Parameter.gameSpeed = GameSpeed.Slow;
	            break;
	        case R.id.radMedium:
	            if (checked)
	            	Parameter.gameSpeed = GameSpeed.Medium;
	            break;
	        case R.id.radFast:
	            if (checked)
	            	Parameter.gameSpeed = GameSpeed.Fast;
	            break;
	        default:
	        	Parameter.gameSpeed = GameSpeed.Medium;
	        	break;
	    }
	    Parameter.MAX_FPS = Parameter.gameSpeed.getFps();
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
