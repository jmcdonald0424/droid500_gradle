package com.fivehundred.droid500.game.controllers;

import android.content.Context;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.game.MainGame;

public interface GameController {

    //public MainGame createNewGame(int playerCount, Context context);
    public void startGame(MainGame game);
    public void startNewHand(MainGame game);
    public void sortCards(MainGame game, float ssu);
    public void startBids(MainGame game);
    public boolean processKitty(MainGame game);
    public void updateBidDisplay(String bidSuit, int bidPower, int bidIndex, MainActivity mainActivity);
    public void setPlayerBidPower(String bidPower, MainGame game);
    public void setPlayerBidSuit(String bidSuit, MainGame game);
    public void passBid(MainGame game);
    public void processPlayerBid(MainGame game);
}
