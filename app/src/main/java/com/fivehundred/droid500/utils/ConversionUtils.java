package com.fivehundred.droid500.utils;

import android.util.Log;
import com.fivehundred.droid500.BuildConfig;

public class ConversionUtils{
    
    public static String getSuitLabel(String suit){
        switch(suit){
            case GameConstants.SPADES:
                return GameConstants.SPADES_LABEL;
            case GameConstants.CLUBS:
                return GameConstants.CLUBS_LABEL;
            case GameConstants.DIAMONDS:
                return GameConstants.DIAMONDS_LABEL;
            case GameConstants.HEARTS:
                return GameConstants.HEARTS_LABEL;
            case GameConstants.JOKER:
                return GameConstants.JOKER_LABEL;
            default:                
                if(BuildConfig.DEBUG){
                    String message = suit + " not found";
                    Log.e(ApplicationConstants.TAG, message);
                }
                return null;
        }
    }
    
    public static String getComplimentarySuit(String suit){
        switch(suit){
            case GameConstants.SPADES:
                return GameConstants.CLUBS;
            case GameConstants.CLUBS:
                return GameConstants.SPADES;
            case GameConstants.DIAMONDS:
                return GameConstants.HEARTS;
            case GameConstants.HEARTS:
                return GameConstants.DIAMONDS;
            case GameConstants.JOKER:
                return GameConstants.JOKER;
            default:
                Logger.log("Complimentary suit not found for " + suit);
                return null;
        }
    }
    
    public static String getPowerLabel(Integer power){
        switch(power){
            case 4:
            case 15:
                return GameConstants.FOUR_LABEL;
            case 5:
            case 16:
                return GameConstants.FIVE_LABEL;
            case 6:
            case 17:
                return GameConstants.SIX_LABEL;
            case 7:
            case 18:
                return GameConstants.SEVEN_LABEL;
            case 8:
            case 19:
                return GameConstants.EIGHT_LABEL;
            case 9:
            case 20:
                return GameConstants.NINE_LABEL;
            case 10:
            case 21:
                return GameConstants.TEN_LABEL;
            case 11:
            case 25:
            case 26:
                return GameConstants.JACK_LABEL;
            case 12:
            case 22:
                return GameConstants.QUEEN_LABEL;
            case 13:
            case 23:
                return GameConstants.KING_LABEL;
            case 14:
            case 24:
                return GameConstants.ACE_LABEL;
            case 27:
                return GameConstants.JOKER_LABEL;
            default:
                return power.toString();
        }
    }
}