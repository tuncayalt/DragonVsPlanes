package com.tuncay.dragonvsplanes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.analytics.tracking.android.EasyTracker;
import com.tuncay.dragonvsplanes.R;

public class BubblesEraActivity extends Activity implements OnClickListener {
	
	private static final String TAG = BubblesEraActivity.class.getSimpleName();
	
	MainGamePanel gamePanel;
	LinearLayout mainGameLayout;	
	Button btnMenu;	
	MainThread thread;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//keep screen bright
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
    	    	
        super.onCreate(savedInstanceState);
        
     // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
           	
    	mainGameLayout = new LinearLayout(this);  
    	gamePanel = new MainGamePanel(this);  
    	SetGameLayout();
    	
        // set our MainGamePanel as the View
        setContentView(mainGameLayout);
        //setContentView(R.layout.game_play);        
        
        Log.d(TAG, "View added");       
    }

	private void SetGameLayout() {
		
		mainGameLayout.setOrientation(LinearLayout.VERTICAL);
    	
		LayoutParams param1 = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 0.0f);		
    	LayoutParams param2 = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, 1.0f);    	    	
    	
    	btnMenu = new Button(this);    
    	btnMenu.setLayoutParams(param1);    	    	
    	btnMenu.setText(R.string.menu);    	    	
    	btnMenu.setOnClickListener(this); 
    	
    	gamePanel.setLayoutParams(param2);
    	
    	mainGameLayout.addView(gamePanel);
    	mainGameLayout.addView(btnMenu);    	    				
	}

	@Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        //gamePanel.GameOver();
        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
        gamePanel.destroy();
        
    }

	public void onClick(View v) {		
		if (v.getId()==mainGameLayout.getChildAt(mainGameLayout.indexOfChild((View)btnMenu)).getId()){
			Log.d(TAG, "bottom menu clicked");			
			
			Intent intent = new Intent(this, MainMenuResumeActivity.class);
			//intent.putExtra("panel", gamePanel);
			startActivity(intent);
		}		
	}
	
	/*public void onClickBottomMenu(View v){
		Log.d(TAG, "bottom menu clicked");			
		
		Intent intent = new Intent(this, MainMenuResumeActivity.class);
		//intent.putExtra("panel", gamePanel);
		startActivity(intent);
	}*/

	@Override
	protected void onPause() {
		super.onPause();
		gamePanel.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		gamePanel.resume();
	}


/*	@Override
	protected void onStop() {
		super.onStop();
		gamePanel.stop();
	}	*/
	
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