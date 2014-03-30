package com.tuncay.dragonvsplanes.model.components;

import java.util.HashMap;

import com.tuncay.dragonvsplanes.parameter.Parameter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

@SuppressLint("UseSparseArrays")
public class SoundManager {
	
	protected static final String TAG = SoundManager.class.getSimpleName();
	
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private HashMap<Integer, MediaPlayer> soundMediaMap;
	private AudioManager  audioManager;	 
	private Context context;	
	private MediaPlayer mediaPlayer;
	
	boolean loaded;
	
	public SoundManager(){
		
	}	
	
	public void initSounds(Context context) {
		this.loaded = false;
		this.context = context;
		this.soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
		this.soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                    int status) {
                loaded = true;
            }
        });
		this.soundPoolMap = new HashMap<Integer, Integer>();
		this.soundMediaMap = new HashMap<Integer, MediaPlayer>();
		this.audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public void addPoolSound(int index, int soundID)
	{
	    soundPoolMap.put(index, soundPool.load(context, soundID, 1));
	}
	public void addMediaSound(int index, int soundID){
		soundMediaMap.put(index, MediaPlayer.create(context, soundID));
	}
	
	public void playPoolSound(int index, boolean loop)
	{
		if (loaded){
			float volume;
			if (Parameter.soundOn){
				float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				volume = streamVolume;
			}
			else{
				volume = 0;
			}
			if (loop){
				soundPool.play(soundPoolMap.get(index), volume, volume, 1, -1, 1f);
			}else{
				soundPool.play(soundPoolMap.get(index), volume, volume, 1, 0, 1f);
			}
			Log.d(TAG, "sound played");			
		}		
	}	
	
	public void playMediaSound(int index, boolean loop, int mediaPausePosition){
		float volume;
		if (Parameter.soundOn){
			float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volume = streamVolume;
		}else{
			volume = 0;
		}
	    mediaPlayer = soundMediaMap.get(index);
	    
	    if (mediaPlayer != null){
	    	mediaPlayer.setVolume(volume, volume);

	    	if (mediaPausePosition != 0){
	    		mediaPlayer.seekTo(mediaPausePosition);
	    	}
	    	if (loop){
	    		mediaPlayer.setLooping(true);
	    	}
	    	mediaPlayer.start();
	    }
		Log.d(TAG, "media sound started");		
	}
	
	public void stopMediaSound(int index){
		mediaPlayer = soundMediaMap.get(index);
		if (mediaPlayer != null){
			mediaPlayer.stop();
		}
		Log.d(TAG, "media sound stopped");		
	}

	public int pauseMediaSound(int index) {
		int length = 0;
		mediaPlayer = soundMediaMap.get(index);
		if (mediaPlayer != null){
			length=mediaPlayer.getCurrentPosition();
			mediaPlayer.pause();
		}
		Log.d(TAG, "media sound paused");	
		return length;				
	}

	
	
}
