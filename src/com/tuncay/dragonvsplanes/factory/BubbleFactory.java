package com.tuncay.dragonvsplanes.factory;

import java.util.Random;

import android.content.Context;

import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.bubble.BossBubble;
import com.tuncay.dragonvsplanes.model.bubble.CaptainBubble;
import com.tuncay.dragonvsplanes.model.bubble.ColonelBubble;
import com.tuncay.dragonvsplanes.model.bubble.CommanderBubble;
import com.tuncay.dragonvsplanes.model.bubble.CorporalBubble;
import com.tuncay.dragonvsplanes.model.bubble.GeneralBubble;
import com.tuncay.dragonvsplanes.model.bubble.GoodBubble;
import com.tuncay.dragonvsplanes.model.bubble.LieutenantBubble;
import com.tuncay.dragonvsplanes.model.bubble.MajorBubble;
import com.tuncay.dragonvsplanes.model.bubble.MarshallBubble;
import com.tuncay.dragonvsplanes.model.bubble.PrivateBubble;
import com.tuncay.dragonvsplanes.model.bubble.SergeantBubble;
import com.tuncay.dragonvsplanes.parameter.Parameter;

public class BubbleFactory {

	public Bubble bubbleSelection(int gameLevel, Context context, int emergeX, GoodBubble goodBubble) {
		Random random = new Random();
		int rnd = random.nextInt(100);		
		int bt = Parameter.bossTurn;
		Bubble evilBubble;
		
		if ((gameLevel % bt == 1) && (rnd < 50)){
			evilBubble = new PrivateBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 1, gameLevel)){
			evilBubble = new CorporalBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 2, gameLevel)){
			evilBubble = new SergeantBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 3, gameLevel)){
			evilBubble = new LieutenantBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 4, gameLevel)){
			evilBubble = new CaptainBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 5, gameLevel)){
			evilBubble = new MajorBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 6, gameLevel)){
			evilBubble = new CommanderBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 7, gameLevel)){
			evilBubble = new ColonelBubble(context, emergeX, 10, goodBubble);
		}else if (gameLevelVariation(rnd, bt, 8, gameLevel)){
			evilBubble = new GeneralBubble(context, emergeX, 10, goodBubble);
		}else if (((gameLevel % bt == 9) && (rnd >= 50)) || (gameLevel % bt == 10)){
			evilBubble = new MarshallBubble(context, emergeX, 10, goodBubble);
		}else {						
			evilBubble = BossBubble.getInstance(context, emergeX, 10, goodBubble);
			if (evilBubble.isDied()){
				((BossBubble) evilBubble).initialize(context, emergeX, 10, goodBubble);
			}
		}		
		return evilBubble;
	}
	
	private boolean gameLevelVariation(int rnd, int bt, int level, int gameLevel) {
		return (gameLevel % bt == level) && (rnd >= 50) || (gameLevel % bt == level + 1) && (rnd < 50);
	}
}
