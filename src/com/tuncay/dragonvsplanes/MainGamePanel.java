package com.tuncay.dragonvsplanes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.factory.BubbleFactory;
import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.abstracts.Explosion;
import com.tuncay.dragonvsplanes.model.abstracts.Flag;
import com.tuncay.dragonvsplanes.model.bubble.GoodBubble;
import com.tuncay.dragonvsplanes.model.components.SoundManager;
import com.tuncay.dragonvsplanes.parameter.Parameter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{
	
	private static final int NO_BUBBLE_EMERGE = -1;

	private static final String TAG = MainGamePanel.class.getSimpleName();

	public MainThread thread;
	
	public GoodBubble goodBubble;
	public static List<Bubble> BubbleList = new ArrayList<Bubble>();
	public static List<Bullet> BulletList = new ArrayList<Bullet>();
	public static List<Flag> FlagList = new ArrayList<Flag>();
	public static List<Explosion> ExplosionList = new ArrayList<Explosion>();
	
	Random random;
	Bubble evilBubble;
	int emergeX;
	int gameLevel;
	
	Dialog gameOverDialog;	
	BubbleFactory bubbleFactory;
	int initialAge = 0;
	
	public Display display;
	// the fps to be displayed
	//private String avgFps;
	SoundManager soundManager;

	private int mediaPausePosition = 0;

	public MainGamePanel(Context context) {
			
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//get dimensions of screen (display)
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		
		addSounds(context);	
    	
    	addGoodBubble(context);				
		
    	Tools.points = 0;
		gameLevel = 1;			
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	private void addGoodBubble(Context context) {
		goodBubble = GoodBubble.getInstance(context, display.getWidth()/2, (display.getHeight()*3)/4, soundManager);
    	goodBubble.initialize(context, display.getWidth()/2, (display.getHeight()*3)/4, soundManager);
		BubbleList.add(goodBubble);
	}

	private void addSounds(Context context) {
		soundManager = new SoundManager();
    	soundManager.initSounds(context);
    	soundManager.addMediaSound(1, R.raw.masterofdarkness);
    	soundManager.addPoolSound(1, R.raw.fireball);
    	soundManager.addPoolSound(2, R.raw.bell);
    	soundManager.addPoolSound(3, R.raw.explosion3);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {			
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being created");
		/*thread.setRunning(true);
		thread.start();*/		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		/*soundManager.stopMediaSound(1);*/		
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {			
			// delegating event handling to the Bubble
			 goodBubble.handleActionDown((int)event.getX(), (int)event.getY());			 	
		} 
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			int x = (int)event.getX();
			int y = (int)event.getY();
			if (x < 0) x = 0;
			if (y < 0) y = 0;
			if (x > this.getWidth()) x = this.getWidth();
			if (y > this.getHeight()) y = this.getHeight();
			goodBubble.handleActionMove(x, y);			
		} 
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (goodBubble.isTouched()) {
				goodBubble.setTouched(false);
			}
			if (goodBubble.isLongTouched()) {
				goodBubble.setLongTouched(false);
			}
		}				
		
		return true;
	}
	
	public boolean onLongTouchEvent(MotionEvent event) {
		goodBubble.handleActionLongDown((int)event.getX(), (int)event.getY());
		
		return true;		
	}
	
/*	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "key down");
		if ((keyCode == KeyEvent.KEYCODE_HOME)) {
	    	GameOver();
	    }
	    return super.onKeyDown(keyCode, event);
	}*/

	public void setAvgFps(String avgFps) {
		//this.avgFps = avgFps;
	}

	public void render(Canvas canvas) {				
		if (!thread.isRunning())
			return;
		
		
		Log.d(TAG, "render start");
		
		drawBackground(canvas);
		
		if (Parameter.getReadyTime > initialAge){
			displayGetReady(canvas);
			initialAge++;
		}
		
		Log.d(TAG, "painted background");
		
		for (int i = 0; i < BubbleList.size(); i++){		
			BubbleList.get(i).draw(canvas);
		}
		Log.d(TAG, "Bubble drawn");
		
		for (int i = 0; i < BulletList.size(); i++){		
			BulletList.get(i).draw(canvas);
		}
		Log.d(TAG, "bullets drawn");
		
		for (int i = 0; i < FlagList.size(); i++){					
			FlagList.get(i).draw(canvas);
		}
		Log.d(TAG, "flags drawn");
		for (int i = 0; i < ExplosionList.size(); i++){					
			ExplosionList.get(i).draw(canvas);
		}
		Log.d(TAG, "Explosions drawn");
		
		//displayFps(canvas, avgFps);		
		//Log.d(TAG, "fps displayed");
		
		displayLife(canvas, goodBubble);
		Log.d(TAG, "life displayed");
		
		displayPoints(canvas);
		Log.d(TAG, "points displayed");
		
		displayLevel(canvas);		
	}

	private void drawBackground(Canvas canvas) {
		//canvas.drawColor(Color.BLACK);
		
		Bitmap backGroundBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background);
		Rect backgroundSrc = new Rect (0, 0, backGroundBitmap.getWidth(), backGroundBitmap.getHeight());
		//Rect backgroundSrc = new Rect (0, 0, canvas.getWidth(), canvas.getHeight());
		Rect backgroundDst = new Rect (0, 0, canvas.getWidth(), canvas.getHeight());
		canvas.drawBitmap(backGroundBitmap, backgroundSrc, backgroundDst, null);
		//canvas.drawBitmap(backGroundBitmap, 0, 0, null);		
	}	

	private void displayLevel(Canvas canvas) {
		if (canvas != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			paint.setTextSize(20.0f);
			String leveltext = "Level " + Integer.toString(this.gameLevel);
			canvas.drawText(leveltext, this.getWidth()/2 - 30, 20, paint);
		}		
	}

	private void displayPoints(Canvas canvas) {
		if (canvas != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			paint.setTextSize(20.0f);
			String pointtext = "Points: " + Float.toString(Tools.points);
			canvas.drawText(pointtext, 20, 20, paint);
		}		
	}

	private void displayLife(Canvas canvas, Bubble bubble) {
		if (canvas != null && bubble != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			paint.setTextSize(20.0f);
			String lifetext = "Life: " + Float.toString(bubble.getBody().getLife());			
			canvas.drawText(lifetext, this.getWidth() - 100, 20, paint);			
		}		
	}
	
	private void displayGetReady(Canvas canvas) {
		if (canvas != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			paint.setTextSize(60.0f);
			paint.setTextAlign(Paint.Align.CENTER);
			String getReadyText = "Get Ready!";
			canvas.drawText(getReadyText, this.getWidth() / 2, this.getHeight() / 2, paint);
		}		
	}

	/*private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}*/
		
	public void update() {
		
		if (!thread.isRunning())
			return;
		if (Parameter.getReadyTime > initialAge)
			return;
		
		createEvilBubble();
		
		setGameLevel();		
		
		if (BubbleList.size() > 0){
			for (int i = 0; i < BubbleList.size(); i++){				
				BubbleList.get(i).update(this, this, getContext(), goodBubble, gameLevel);			
				Log.d(TAG, "end of bubble update");
			}
		}
		if (BulletList.size() > 0){
			for (int i = 0; i < BulletList.size(); i++){			
				BulletList.get(i).update(this, goodBubble);									
				Log.d(TAG, "end of bullet update");
			}
		}
		
		if (FlagList.size() > 0){
			for (int i = 0; i < FlagList.size(); i++){
				FlagList.get(i).update(goodBubble, soundManager);					
				Log.d(TAG, "end of flag update");
			}						
		}		
		if (ExplosionList.size() > 0){
			for (int i = 0; i < ExplosionList.size(); i++){
				ExplosionList.get(i).update(goodBubble, soundManager);				
				Log.d(TAG, "end of explosion update");
			}						
		}		
		if (goodBubble.isDied()){
			GameOver();			
		}
	}

	private void setGameLevel() {
		
		if (goodBubble.getAge() % 1000 == 999){
			gameLevel++;
		}			
	}

	public void GameOver(){		
		
		Log.d(TAG, "Game Over1");
		destroy();
		GameOverScreen();
		thread.setRunning(false);
		((Activity)getContext()).finish();
		Log.d(TAG, "Game Over2");
	}
	private void GameOverScreen() {
		Intent intent = new Intent(getContext(), GameOverActivity.class);		
		getContext().startActivity(intent);		
	}		

	public void createEvilBubble() {
		//TODO bunu parametre yap		
		Log.d(TAG, "createEvilBubble start");
		random = new Random();
		evilBubble = null;
		emergeX = getEmergeX(random);
		Log.d(TAG, "emergeX: " + emergeX);
		if (emergeX > NO_BUBBLE_EMERGE){	
			bubbleFactory = new BubbleFactory();
			evilBubble = bubbleFactory.bubbleSelection(gameLevel, getContext(), emergeX, goodBubble);
			if (evilBubble!=null && !BubbleList.contains(evilBubble) && evilBubble.isDied()==false){				
				BubbleList.add(evilBubble);
			}	
			Log.d(TAG, "EvilBubble created");	
		}				
	}	

	private int getEmergeX(Random random) {		
		int emergeX = 0;
		if (random.nextInt(100) <= Parameter.probEvilBubble){		
			switch(random.nextInt(100) % Parameter.bubbleEmergePointNumber){
			case 0:
				emergeX = 0;
				break;
			case 1:
				emergeX = display.getWidth()/(Parameter.bubbleEmergePointNumber - 1);
				break;
			case 2:
				emergeX = 2 * display.getWidth()/(Parameter.bubbleEmergePointNumber - 1);
				break;
			case 3:
				emergeX = 3 * display.getWidth()/(Parameter.bubbleEmergePointNumber - 1);
				break;
			case 4:
				emergeX = 4 * display.getWidth()/(Parameter.bubbleEmergePointNumber - 1);
				break;
			case 5:
				emergeX = 5 * display.getWidth()/(Parameter.bubbleEmergePointNumber - 1);
				break;
			case 6:                
				emergeX = 6 * display.getWidth()/(Parameter.bubbleEmergePointNumber - 1);
				break;
			default:				
				emergeX = NO_BUBBLE_EMERGE;
				break;
			}
		}
		else {
			emergeX = NO_BUBBLE_EMERGE;
		}
		
		return emergeX;
	}

	public void pause() {		
		thread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			}
			catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		mediaPausePosition  = soundManager.pauseMediaSound(1);
	}

	public void resume() {		
		thread = new MainThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();		
		soundManager.playMediaSound(1, true, mediaPausePosition);
		// create the game loop thread		
	}

	public void destroy() {
		BubbleList.clear();
		BulletList.clear();
		FlagList.clear();	
		ExplosionList.clear();
	}

/*	public void stop() {
		soundManager.stopMediaSound(1);		
	}	*/
}


