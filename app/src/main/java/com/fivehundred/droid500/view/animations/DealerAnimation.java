package com.fivehundred.droid500.view.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.PointF;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.view.Sprite;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.controllers.ViewController;
import com.fivehundred.droid500.view.utils.ViewUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;

public class DealerAnimation{
    
    private final MainGame game;
    private final float ssu;
    private int cardCount;
    private final int dropPointArea = 40;
    private float xCoord;
    private float yCoord;
    private int cardIndex;
    private int playerIndex;
    private int playerCardCount = 0;
    private int kittyCardCount = 0;
    private Sprite currentSprite;
    private final PointF dealerLocation;
    private final Context context;
    
    @Inject AnimationController animation;
    @Inject ViewController viewController;
    @Inject GameController gameController;
    
    public DealerAnimation(MainGame game, float ssu, Context context){
        this.game = game;
        this.ssu = ssu;
        cardCount = 0;
        dealerLocation = ViewUtils.getDealerLocation(game.getDealerIndex());
        this.context = context;
    }
    
    public void dealCards(){
        if(cardCount > 44){
            // Sort each hand to organize suits
            gameController.sortCards(game, ssu);
            gameController.startBids(game, (MainActivity)context);
        }else{
            if(cardCount % 9 == 0){
                playerIndex = 4;
                cardIndex = kittyCardCount++;
                currentSprite = game.getKitty().get(cardIndex).getSprite();
            }else{
                playerIndex = (playerCardCount + game.getDealerIndex() + 1) % 4;   
                cardIndex = (int)Math.floor(playerCardCount++/4);    
                currentSprite = game.getPlayers().get(playerIndex).getCards().get(cardIndex).getSprite();
            }
            xCoord = ViewUtils.getDropPoint(playerIndex).x + dropPointArea / 2;
            yCoord = ViewUtils.getDropPoint(playerIndex).y + dropPointArea / 2;

            currentSprite.setTranslation(dealerLocation.x * ssu, dealerLocation.y * ssu); 
            currentSprite.setScalar(ssu);

            PointF endTranslation = new PointF((xCoord - new Random().nextInt(dropPointArea)) * ssu, (yCoord - new Random().nextInt(dropPointArea)) * ssu);            
            float angle = 990 - (new Random()).nextInt(180); 

            List<Animator> dealAnimations = new ArrayList<>();
            dealAnimations.add(animation.translate(currentSprite, endTranslation));
            dealAnimations.add(animation.rotate(currentSprite, angle));
            dealAnimations.add(animation.scale(currentSprite, 1.35f, 1.00f));
            AnimatorSet dealSet = new AnimatorSet();
            dealSet.setDuration(100);
            dealSet.playTogether(dealAnimations);     
            List<Animator> placeAnimations = new ArrayList<>();
            placeAnimations.add(animation.translate(currentSprite, getPlaceCoordinates()));
            placeAnimations.add(animation.rotate(currentSprite, currentSprite.getAngle() % 360, 90.0f * (playerIndex % 2)));
            AnimatorSet placeSet = new AnimatorSet();
            placeSet.setDuration(40);
            placeSet.playTogether(placeAnimations);
            placeSet.setStartDelay(10);  
            AnimatorSet set = new AnimatorSet();
            set.play(dealSet).before(placeSet);
            set.addListener(new AnimatorListenerAdapter(){
                @Override
                public void onAnimationStart(Animator animation){

                }
                @Override
                public void onAnimationEnd(Animator animation){
                    flipCard();
                    dealCards();
                }
            });  
            set.start();
            cardCount++;
        }
    }
    
    /*private void placeCard(){
        List<Animator> animations = new ArrayList<>();
        animations.add(animation.translate(currentSprite, getPlaceCoordinates()));
        animations.add(animation.rotate(currentSprite, currentSprite.getAngle() % 360, 90.0f * (playerIndex % 2)));
        AnimatorSet placeSet = new AnimatorSet();
        placeSet.setDuration(200);
        placeSet.playTogether(animations);
        placeSet.setStartDelay(100);
        placeSet.start();
        flipCard();
        cardCount++;
    }*/
    
    private void flipCard(){        
        if(playerIndex == 0){
            viewController.flip(game.getPlayers().get(playerIndex).getCards().get(cardIndex));
        }
    }
    
    private PointF getPlaceCoordinates(){
        return ViewUtils.getPlaceCoordinates(playerIndex, cardIndex, ssu);
        /*float padding = (ViewConstants.BASE_SCALE_MIN * ssu / 2) - (ViewConstants.BASE_CARD_WIDTH * 10 / 2 / 2 * ssu);
        float coordOffset = padding + cardIndex * ViewConstants.BASE_CARD_WIDTH / 2 * ssu;
        switch(playerIndex){
            case 0:
                return new PointF(ViewConstants.BASE_CARD_WIDTH / 2 * ssu + coordOffset, 
                                  ViewConstants.BASE_CARD_HEIGHT / 2 * ssu);
            case 1:
                return new PointF(ViewConstants.BASE_CARD_HEIGHT / 2 * ssu,
                                  ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_WIDTH / 2 * ssu - coordOffset);
            case 2:
                return new PointF(ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_WIDTH / 2 * ssu - coordOffset,
                                  ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_HEIGHT / 2 * ssu);
            case 3:
                return new PointF(ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_HEIGHT / 2 * ssu,
                                  ViewConstants.BASE_CARD_WIDTH / 2 * ssu + coordOffset);
            case 4:
                return new PointF(ViewConstants.BASE_SCALE_MIN / 2 * ssu,
                                  ViewConstants.BASE_SCALE_MIN / 2 * ssu);
            default:
                Logger.logError("getCoordinates failed -- No coordinates found for player: " + playerIndex);
                return null;
        }*/
    }
}