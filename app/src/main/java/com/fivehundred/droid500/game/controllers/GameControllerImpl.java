package com.fivehundred.droid500.game.controllers;

import android.content.Context;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.utils.ViewConstants;
import com.fivehundred.droid500.view.utils.ViewListenerConstants;

import javax.inject.Inject;

public class GameControllerImpl implements GameController {

    @Inject
    public GameControllerImpl(){
        
    }

    @Override
    public MainGame createNewGame(int playerCount, Context context) {
        MainGame newGame = new MainGame(playerCount, context);

        return newGame;
    }
    
    @Override
    public void startGame(MainGame game){
        startNewHand(game);
    }
    
    @Override
    public void startNewHand(MainGame game){
        game.shuffleDeck();
        Logger.log("DEALING DECK ::::: Deck size: " + game.getDeck().size());
        game.dealDeck();
        /*Logger.log("STARTING BIDS ::::: Deck size: " + game.getDeck().size());
        int winner = game.bid();
        Logger.log("UPDATING TRUMP CARDS ::::: Deck size: " + game.getDeck().size());
        game.updateTrumpCards();
        Logger.log("OPENING KITTY ::::: Deck size: " + game.getDeck().size());
        game.openKitty(winner);
        Logger.log("STARTING HAND ::::: Deck size: " + game.getDeck().size());
        game.startHand(winner);   */     
    }
    
    @Override
    public void sortCards(MainGame game, float ssu){
        game.sortCards(ssu);
        game.addListener(ViewListenerConstants.REBUILD_SPRITES);
    }
    
    @Override
    public void startBids(MainGame game, MainActivity mainActivity){
        mainActivity.loadLayout(ViewListenerConstants.LOAD_BID_LAYOUT);
        Logger.log("STARTING BIDS ::::: Deck size: " + game.getDeck().size());
        game.startBids();
    }
    
    @Override
    public void processKitty(MainGame game){
        
    }
    
    @Override
    public void updateBidDisplay(String bidSuit, int bidPower, int bidIndex, MainActivity mainActivity){
        mainActivity.updateBidDisplay(bidSuit, bidPower, bidIndex);
    }
    
    @Override
    public void setPlayerBidPower(String bidPower, MainGame game){
        game.setPlayerBidPower(bidPower);
    }
    
    @Override
    public void setPlayerBidSuit(String bidSuit, MainGame game){
        game.setPlayerBidSuit(bidSuit);
    }
    
    @Override
    public void passBid(MainGame game){
        game.setPlayerBidSuit(ViewConstants.PASSED);
        game.setPlayerBidPower(0);
        game.processPlayerBid();
    }
    
    @Override
    public void processPlayerBid(MainGame game){
        game.processPlayerBid();
    }
}
