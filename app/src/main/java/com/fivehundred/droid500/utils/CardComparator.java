package com.fivehundred.droid500.utils;

import com.fivehundred.droid500.game.Card;
import java.util.Comparator;

public class CardComparator implements Comparator<Card>{
    
    private final int order;
    
    public CardComparator(int order){
        this.order = order;
    }
    
    @Override
    public int compare(Card card1, Card card2){
        if(order < 2){
            // Sort by power
            int comparison = compare(card1.getPower(), card2.getPower());
            return comparison == 0 ? compare(card1.getSuit(), card2.getSuit()) : comparison;
        }else{
            // Sort by suit
            int comparison = compare(card1.getSuit(), card2.getSuit());
            return comparison == 0 ? compare(card1.getPower(), card2.getPower()) : comparison;
        }
    }
    
    private int compare(Integer a, Integer b){
        int power1 = order % 2 == 1 ? a : b;
        int power2 = order % 2 == 1 ? b : a;
        return power1 < power2 ? 1
             : power1 > power2 ? -1
             : 0;
    }
    
    private int compare(String a, String b){
        int power1 = order % 2 == 1 ? GameUtils.valueSuit(a) : GameUtils.valueSuit(b);
        int power2 = order % 2 == 1 ? GameUtils.valueSuit(b) : GameUtils.valueSuit(a);
        return power1 < power2 ? 1
             : power1 > power2 ? -1
             : 0;
    }
}