package com.fivehundred.droid500.utils;

import android.content.Context;
import android.util.SparseArray;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.application.MainApplication;
import com.fivehundred.droid500.game.Auction;
import com.fivehundred.droid500.game.Card;
import java.util.Collections;
import java.util.List;

public class GameUtils{
        
    public static String highestSuit(String a, String b){
        if(a.equals(b)){
            return b;
        }
        if(valueSuit(a) > valueSuit(b)){
            return a;
        }else{
            return b;
        }
    }
    
    public static int valueSuit(String suit){
        int value = 0;
        switch(suit){
            case GameConstants.JOKER:
                value++;
            case GameConstants.HEARTS:
                value++;
            case GameConstants.DIAMONDS:
                value++;
            case GameConstants.CLUBS:
                value++;
            case GameConstants.SPADES:
                value++;
                break;
            default:
                Logger.logError(suit + " cannot be valued");
                break;
        }
        return value;
    }
    
    public static void ascSort(List<Card> cards){
        Collections.sort(cards, new CardComparator(0));
    }
    
    public static void ascHandSort(List<Card> cards){
        Collections.sort(cards, new CardComparator(2));
    }
    
    public static void descSort(List<Card> cards){
        Collections.sort(cards, new CardComparator(1));
    }
    
    public static int getBidValue(SparseArray<String> bid){
        int baseValue = getBaseValue(bid.valueAt(0));
        int multiplier = bid.keyAt(0) - 6;
        int bidValue = multiplier * 100 + baseValue;
        Logger.log("The bid " + bid.keyAt(0) + " " + bid.valueAt(0) + " is worth " + bidValue + " points");
        return bidValue; 
    }
    
    private static int getBaseValue(String bidSuit){
        switch(bidSuit){
            case GameConstants.SPADES:
                return GameConstants.SPADES_BASE_VALUE;
            case GameConstants.CLUBS:
                return GameConstants.CLUBS_BASE_VALUE;
            case GameConstants.DIAMONDS:
                return GameConstants.DIAMONDS_BASE_VALUE;
            case GameConstants.HEARTS:
                return GameConstants.HEARTS_BASE_VALUE;
            default:
                Logger.logError("getBaseValue failed - - " + bidSuit + " not found");
                return 0;
        }
    }
    
    public static String getSuit(List<Card> cards){
        int suitCount = 0;
        String suit = "";
        if(cards.size() == 1){
            Logger.log("One card found. Suit is " + cards.get(0).getSuit());
            return cards.get(0).getSuit();
        }
        for(Card card : cards){
            if(!suit.equals(card.getSuit())){
                Logger.log(suit + " does not match " + card.getSuit() + ". Starting over");
                suit = card.getSuit();
                suitCount = 0;
            }
            suitCount++;
            Logger.log(suitCount + " " + suit + " has been found");
            if(suitCount > 1){
                Logger.log("Suit is " + suit);
                return suit;
            }
        }
        if(suitCount == 1){
            Logger.log("2 non-matching suits found. Returning " + cards.get(0).getSuit());
            return cards.get(0).getSuit();
        }
        Logger.log("getSuit failed - - cards did not match a single suit");
        return null;
    }
    
    public static void injectIntoObjectGraph(Auction object, Context context){
        MainApplication app = (MainApplication)((MainActivity)context).getApplication();
        app.getMainComponent().inject(object);
    }
}