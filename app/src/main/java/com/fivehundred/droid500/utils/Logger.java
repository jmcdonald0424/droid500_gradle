package com.fivehundred.droid500.utils;

import android.util.Log;
import com.fivehundred.droid500.BuildConfig;
import com.fivehundred.droid500.game.Card;
import java.util.List;

public class Logger{
        
    public static void log(String message){        
        if(BuildConfig.DEBUG){
            Log.d(ApplicationConstants.TAG, message);
        }
    }   
    
    public static void logError(String message){        
        if(BuildConfig.DEBUG){
            Log.e(ApplicationConstants.TAG, message);
        }
    }
    
    public static void log(List<Card> cards){
        lineBreak();
        for(Card card : cards){
            log(cards.indexOf(card) + ": " + card.toString() + " :: " + card.getPower() + " || " + card.getSuit());
        }
        lineBreak();
    }
    
    public static void lineBreak(){
        log("============================================");        
    }
    
    public static void lineBreak(int lines){
        for(int i=0; i<lines; i++){
            lineBreak();
        }
    }
}