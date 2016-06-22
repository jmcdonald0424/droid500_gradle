package com.fivehundred.droid500.game;

import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.animations.CardAnimation;

import javax.inject.Inject;

public class Hand{
    
    private final int handScore[] = new int[4];
    private final String trumpSuit;
    private String leadSuit;
    private int winnerIndex = 0;
    private Integer highPower = 0;
    private boolean locked = false;
        
    public Hand(String trumpSuit){
        this.trumpSuit = trumpSuit;
    }
    
    public int play(Card card, int playerIndex){
        setLeadSuit(card);
        score(card, playerIndex);
        if(handComplete()){
            lockHand();
            return(winnerIndex);
        }
        int nextPlayerIndex = playerIndex < 3 ? playerIndex + 1 : playerIndex - 3;        
        return nextPlayerIndex;
    }
    
    private void setLeadSuit(Card card){
        if(isNew()){
            if(card.isTrump()){
                leadSuit = trumpSuit;  // In case lead card is Joker which is not assigned a suit
            }else{
                leadSuit = card.getSuit();
            }
        }
    }
    
    private void score(Card card, int playerIndex){
        handScore[playerIndex] = card.getPower();
        if(leadSuit.equals(card.getSuit()) || trumpSuit.equals(card.getSuit())){            
            if(card.getPower() > highPower){
                Logger.log("Player " + playerIndex + " takes the lead with " + card.toString() + "(" + card.getPower() + ")");
                highPower = card.getPower();
                winnerIndex = playerIndex;
            }
        }
    }
    
    private boolean handComplete(){
        for(int score : handScore){
            if(score == 0){
                return false;
            }
        }
        return true;
    }
    
    private void lockHand(){
        Logger.log("Locking Hand: Player " + winnerIndex + " wins");
        locked = true;
    }
    
    public boolean isNew(){
        return leadSuit == null;
    }

    public String getLeadSuit() {
        return leadSuit;
    }

    public int getWinnerIndex() {
        return winnerIndex;
    }

    public void setWinnerIndex(int winnerIndex) {
        this.winnerIndex = winnerIndex;
    }

    public Integer getHighPower() {
        return highPower;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}