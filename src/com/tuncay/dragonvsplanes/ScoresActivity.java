package com.tuncay.dragonvsplanes;

import com.google.analytics.tracking.android.EasyTracker;
import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.actionbar.GlobalScoresFragment;
import com.tuncay.dragonvsplanes.actionbar.LocalScoresFragment;
import com.tuncay.dragonvsplanes.actionbar.TabListenerClass;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class ScoresActivity extends ActionBarActivity {
	
	private static final String TAG = ScoresActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
     
     // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
            	        
        //setContentView(R.layout.scores);
        
        //super.onCreate(savedInstanceState);
        // Notice that setContentView() is not used, because we use the root
        // android.R.id.content as the container for each fragment

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
                           .setText(R.string.local)
                           .setTabListener(new TabListenerClass<LocalScoresFragment>(
                                   this, "local", LocalScoresFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                       .setText(R.string.global)
                       .setTabListener(new TabListenerClass<GlobalScoresFragment>(
                               this, "global", GlobalScoresFragment.class));
        actionBar.addTab(tab);       
        
        Log.d(TAG, "ScoresActivity viewed");             
	}
	
	/*private void FillScores() {
		CustomDBAdapter dbAdapter = new CustomDBAdapter(getApplicationContext());
		try
		{
			Cursor cur = dbAdapter.getScores(); 
			String[] from = {dbAdapter.getNameColumn(), dbAdapter.getScoreColumn()};
			int[] to = {R.id.txtScoreCursorName, R.id.txtScoreCursorScore};
			
			 
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
					R.layout.score_cursor, cur, from, to);
			 
			ListView list = (ListView)this.findViewById(R.id.lstScores);
			list.setAdapter(adapter);
			 
			dbAdapter.close();
		} catch(Exception e)
		{
			Log.d("FILL SCORES", e.toString());
		}
		
	}*/

	public void onClickBtnScores (View view){
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
