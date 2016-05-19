package com.fivehundred.droid500.ai;

import com.fivehundred.droid500.game.Card;
import com.fivehundred.droid500.game.Hand;
import com.fivehundred.droid500.game.Player;

public class ActionUtils{
    
    public static Card playHand(Hand hand, Player player){
        String leadSuit = hand.getLeadSuit();
        Integer highPower = hand.getHighPower();
        if(hand.isNew()){
            return player.playHighCard();
        }else{
            if(player.has(leadSuit)){
                if(hand.getWinnerIndex() == player.getPartnerIndex()){
                    return player.throwOff(leadSuit);
                }else{
                    return player.playLowestWinner(highPower, leadSuit);
                }                  
            }else{
                if(hand.getWinnerIndex() == player.getPartnerIndex()){
                    return player.throwOff();
                }else{
                    return player.playLowestWinner(highPower);
                } 
            }
        }
    }
}