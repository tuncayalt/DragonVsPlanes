package com.tuncay.dragonvsplanes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public abstract class MainMenuActivity extends Activity{	
	
	
	public void onClickOptions(View view) {
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra("ilkekran", false);
		startActivity(intent);
	}
	public void onClickGameRules(View view) {
		Intent intent = new Intent(this, GameInfoActivity.class);
		startActivity(intent);
	}
	public void onClickAbout(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}	
	public void onClickScores(View view) {
		startActivity(new Intent(this,ScoresActivity.class));
	}	
/*	public void onClickExit(View view) {			
		Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		this.finish();	
	}*/
}
