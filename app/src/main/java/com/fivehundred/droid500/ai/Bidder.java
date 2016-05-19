package com.fivehundred.droid500.ai;

import android.util.SparseArray;
import com.fivehundred.droid500.game.Card;
import com.fivehundred.droid500.utils.ConversionUtils;
import com.fivehundred.droid500.utils.GameConstants;
import com.fivehundred.droid500.utils.GameUtils;
import com.fivehundred.droid500.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class Bidder{
    
    private List<Card> cards = new ArrayList<>();
    private Integer bid = 0;
    private String strongSuit = "";
    private int highCount = 0;
    private int complimentarySuitBid = 0;
    private int lowOffsuitBid = 0;
    private int highOffsuitBid = 0;
    private int suitStrength[] = {0, 0, 0, 0, 0};
    private List<Card> strongSuitCards = new ArrayList<>();
    private List<Card> spades = new ArrayList<>();
    private List<Card> clubs = new ArrayList<>();
    private List<Card> diamonds = new ArrayList<>();
    private List<Card> hearts = new ArrayList<>();
    private Card joker;
    /*private int spadeCount = 0;
    private int clubCount = 0;
    private int diamondCount = 0;
    private int heartCount = 0;
    private int jokerCount = 0;*/
    private final int SPADE = GameConstants.SPADES_POWER;
    private final int CLUB = GameConstants.CLUBS_POWER;
    private final int DIAMOND = GameConstants.DIAMONDS_POWER;
    private final int HEART = GameConstants.HEARTS_POWER;
    private final int JOKER = 4;
    
    private final int NO_TRUMP = 0;
    private final int COMPLIMENTARY_SUIT = 1; // Represents suit of same color as trump suit
    private final int TRUMP = 2;
    
    public Bidder(List<Card> cards){
        this.cards = cards;
    }
    
    public SparseArray<String> bid(){
        bid = 0;
        //countSuits();
        divideSuits();
        determineStrongSuit();
        updateJackPower();
        bidStrongSuit();
        bidOffSuits();
        Logger.log("The final bid is " + bid + " of " + strongSuit);
        if(bid > 10){
            Logger.log("Final bid reduced to 10");
            bid = 10;
        }else if(bid < 6){
            Logger.log("Final bid increased to 6");
            bid = 6;
        }
        SparseArray<String> bidArray = new SparseArray<String>();
        bidArray.put(bid, strongSuit);
        resetJackPower();
        return bidArray;
    }
    
    /*private void countSuits(){
        for(Card card : cards){
            String suit = card.getSuit();
            switch(suit){
                case GameConstants.SPADES:
                    suitCount[SPADE]++;
                    if(card.getPower() > 10){suitCount[SPADE]++;} // Give bonus points for face cards
                    if(card.getPower().equals(GameConstants.JACK_POWER)){suitCount[CLUB]+=1;} // Jack is beneficial for both suits of it's color
                    break;
                case GameConstants.CLUBS:
                    suitCount[CLUB]++;
                    if(card.getPower().equals(GameConstants.JACK_POWER)){suitCount[SPADE]++;}
                    break;
                case GameConstants.DIAMONDS:
                    suitCount[DIAMOND]++;
                    if(card.getPower().equals(GameConstants.JACK_POWER)){suitCount[HEART]++;}
                    break;
                case GameConstants.HEARTS:
                    suitCount[HEART]++;
                    if(card.getPower().equals(GameConstants.JACK_POWER)){suitCount[DIAMOND]++;}
                    break;
                case GameConstants.JOKER:
                    suitCount[JOKER]++;
                    break;
                default:
                    Logger.log(suit + " not found.");
            }
        }
    }*/
    
    private void divideSuits(){
        for(Card card : cards){
            String suit = card.getSuit();
            switch(suit){
                case GameConstants.SPADES:
                    spades.add(card);
                    if(card.getPower().equals(GameConstants.JACK_POWER)){clubs.add(card);}
                    break;
                case GameConstants.CLUBS:
                    clubs.add(card);
                    if(card.getPower().equals(GameConstants.JACK_POWER)){spades.add(card);}
                    break;
                case GameConstants.DIAMONDS:
                    diamonds.add(card);
                    if(card.getPower().equals(GameConstants.JACK_POWER)){hearts.add(card);}
                    break;
                case GameConstants.HEARTS:
                    hearts.add(card);
                    if(card.getPower().equals(GameConstants.JACK_POWER)){diamonds.add(card);}
                    break;
                case GameConstants.JOKER:
                    joker = card;
                    break;
                default:
                    Logger.log(suit + " not found.");
            }
        }
    }
    
    /*private void determineStrongSuit(){
        highCount = 0;
        for(int i=0; i<suitCount.length; i++){
            if(suitCount[i] > highCount){
                highCount = suitCount[i];
                strongSuit = findSuit(i);
            }
        }
        Logger.log("The strong suit is " + strongSuit + " with " + highCount + " cards");
    }*/
    
    private void determineStrongSuit(){
        highCount = 0;
        
        // Count spades
        for(Card spade : spades){
            suitStrength[SPADE]++;   
            if(spade.getPower() > 10){suitStrength[SPADE]++;} // Give bonus to face cards
        }
        // Check if high suit
        if(!(highCount > suitStrength[SPADE])){
            strongSuitCards.clear();
            strongSuitCards.addAll(spades);
            strongSuit = GameConstants.SPADES;
            highCount = suitStrength[SPADE];
        }
        
        // Count clubs
        for(Card club : clubs){
            suitStrength[CLUB]++;   
            if(club.getPower() > 10){suitStrength[CLUB]++;}
        }
        // Check if high suit
        if(!(highCount > suitStrength[CLUB])){
            strongSuitCards.clear();
            strongSuitCards.addAll(clubs);
            strongSuit = GameConstants.CLUBS;
            highCount = suitStrength[CLUB];
        }
        
        // Cound diamonds
        for(Card diamond : diamonds){
            suitStrength[DIAMOND]++;   
            if(diamond.getPower() > 10){suitStrength[DIAMOND]++;}
        }
        // Check if high suit
        if(!(highCount > suitStrength[DIAMOND])){
            strongSuitCards.clear();
            strongSuitCards.addAll(diamonds);
            strongSuit = GameConstants.DIAMONDS;
            highCount = suitStrength[DIAMOND];
        }
        
        // Count hearts
        for(Card heart : hearts){
            suitStrength[HEART]++;   
            if(heart.getPower() > 10){suitStrength[HEART]++;}
        }
        // Check if high suit
        if(!(highCount > suitStrength[HEART])){
            strongSuitCards.clear();
            strongSuitCards.addAll(hearts);
            strongSuit = GameConstants.HEARTS;
            highCount = suitStrength[HEART];
        }
        
        // Check for joker
        if(joker != null){
            highCount++;
            strongSuitCards.add(joker);
        }
        Logger.log("The strong suit is " + strongSuit + " with " + strongSuitCards.size() + " cards");
        
        // Handle offsuit jacks
        for(Card card : cards){
            if(card.isJack()){
                switch(card.getSuit()){
                    case GameConstants.SPADES:
                        updateJacks(spades, card.getSuit());
                        break;
                    case GameConstants.CLUBS:
                        updateJacks(clubs, card.getSuit());
                        break;
                    case GameConstants.DIAMONDS:
                        updateJacks(diamonds, card.getSuit());
                        break;
                    case GameConstants.HEARTS:
                        updateJacks(hearts, card.getSuit());
                        break;
                }
            }
        }
    }
    
    private void updateJacks(List<Card> cardList, String suit){
        if(strongSuit.equals(ConversionUtils.getComplimentarySuit(suit))){
            removeAllJacks(cardList);
        }else if(!strongSuit.equals(suit)){
            removeOffsuitJack(cardList, suit);
        }
    }
    
    private void removeOffsuitJack(List<Card> cardList, String suit){
        for(Card card : new ArrayList<>(cardList)){
            if(card.isJack() && suit.equals(card.getComplimentarySuit())){
                Logger.log("Remove Off-suit Only: Removing " + card.toString() + " from " + GameUtils.getSuit(cardList));
                cardList.remove(card);
                break;
            }
        }
    }
    
    private void removeAllJacks(List<Card> cardList){
        for(Card card : new ArrayList<>(cardList)){
            if(card.isJack()){
                Logger.log("Remove All: Removing " + card.toString() + " from " + GameUtils.getSuit(cardList));
                cardList.remove(card);
            }
        }
    }
    
    private void updateJackPower(){
        for(Card card : strongSuitCards){
            if(card.isJack()){
                String cardLabel = card.toString();
                if(strongSuit.equals(card.getSuit())){
                    card.setPower(GameConstants.TRUMP_JACK_POWER);
                }else{
                    card.setPower(GameConstants.COMPLIMENTARY_JACK_POWER);
                }
                Logger.log(cardLabel + " is now power " + card.getPower());
            }
        }
    }
    
    private void resetJackPower(){
        for(Card card : strongSuitCards){
            if(card.isJack()){
                String cardLabel = card.toString();
                card.setPower(GameConstants.JACK_POWER);
                Logger.log(cardLabel + " is now power " + card.getPower());
            }
        }
    }
    
    private void bidStrongSuit(){
        int missingTrumpCount = GameConstants.TRUMP_COUNT - strongSuitCards.size();
        int bidWeight = (int)Math.ceil(missingTrumpCount/3);  // TODO: make 3 (opponent count) dynamic
        if(strongSuitCards.size() > bidWeight){
            bid = highCount - bidWeight;
        }
        int thisBid = bidSuit(new ArrayList<>(strongSuitCards), TRUMP);
        Logger.log("The strong suit (" + strongSuit + ") bid is " + thisBid);
    }
    
    private void bidOffSuits(){
        List<Card> complimentarySuit = new ArrayList<>();
        List<Card> offSuit1 = new ArrayList<>();
        List<Card> offSuit2 = new ArrayList<>();
        switch(strongSuit){
            case GameConstants.SPADES:
                complimentarySuit.addAll(clubs);
                offSuit1.addAll(diamonds);
                offSuit2.addAll(hearts);
                break;
            case GameConstants.CLUBS:
                complimentarySuit.addAll(spades);
                offSuit1.addAll(diamonds);
                offSuit2.addAll(hearts);
                break;
            case GameConstants.DIAMONDS:
                complimentarySuit.addAll(hearts);
                offSuit1.addAll(spades);
                offSuit2.addAll(clubs);
                break;
            case GameConstants.HEARTS:
                complimentarySuit.addAll(diamonds);
                offSuit1.addAll(spades);
                offSuit2.addAll(clubs);
                break;
        }
        /*String offSuit1Value = null;
        for(Card card : cards){
            if(!(strongSuit.equals(card.getSuit())
                    && card.getSuit().equals(GameConstants.JOKER))){
                if(strongSuit.equals(card.getComplimentarySuit())
                        && !card.getPower().equals(GameConstants.JACK_POWER)){
                    complimentarySuit.add(card);
                    continue;
                }
                if(offSuit1Value == null){
                    offSuit1Value = card.getSuit();
                }
                if(offSuit1Value.equals(card.getSuit())){
                    offSuit1.add(card);
                }else{
                    offSuit2.add(card);
                }
            }
        }*/
        if(complimentarySuit.isEmpty()){
            Logger.log("The complimentary suit is empty");
        }else{
            refreshJacks(complimentarySuit, COMPLIMENTARY_SUIT);
            complimentarySuitBid = bidSuit(complimentarySuit, COMPLIMENTARY_SUIT);
            Logger.log("The complimentary suit bid is " + complimentarySuitBid);            
        }
        if(offSuit1.isEmpty()){
            Logger.log("The low off-suit is empty");
        }else{
            refreshJacks(offSuit1, NO_TRUMP);
            lowOffsuitBid = bidSuit(offSuit1, NO_TRUMP);
            Logger.log("The low off-suit bid is " + lowOffsuitBid);            
        }
        if(offSuit2.isEmpty()){
            Logger.log("The high off-suit is empty");
        }else{
            refreshJacks(offSuit2, NO_TRUMP);
            highOffsuitBid = bidSuit(offSuit2, NO_TRUMP);
            Logger.log("The high off-suit bid is " + highOffsuitBid);             
        }       
    }
    
    private int bidSuit(List<Card> cardList, int suitType){
        int cardCount = cardList.size();
        if(cardCount < 1){
            // Return if list is empty
            return 0;
        }
        int missingCardCount = 13 - cardCount;
        int thisBid = 0;
        int lastPower = 30;
        String suit = GameUtils.getSuit(cardList);
        if(suitType != TRUMP){
            // Subtract for Joker
            missingCardCount--;
            if(suitType == COMPLIMENTARY_SUIT){
                // Subtract for missing Jack
                missingCardCount--;
            }
        }else{
            suit = strongSuit;
        }
        GameUtils.descSort(cardList);
        Logger.log("Bidding on suit: " + suit);
        for(Card card : cardList){
            Logger.log(card.toString());
        }
        for(int i=0; i<cardCount; i++){
            if(!(i < cardList.size())){
                Logger.log("Deck empty - Breaking out");
                break;
            }
            Card currentCard = cardList.get(i);
            if(missingCardCount < i+1){
                // All trumps will be clear by this point, so can assume remaining cards will win                
                thisBid++;
                Logger.log(currentCard.toString() + " gets auto-bid");
                cardList.remove(currentCard);
                continue;
            }
            if(suitType == TRUMP){
                if(lastPower > GameConstants.JOKER_POWER){
                    lastPower = GameConstants.JOKER_POWER;
                    if(currentCard.getPower().equals(GameConstants.JOKER_POWER)){
                        thisBid++;
                        Logger.log(currentCard.toString() + " gets bid");
                        continue;
                    }else{
                        // Remove last (weakest) card. This allows the player to account for the missing Joker in case partner doesn't have it
                        int lastIndex = cardList.size()-1;
                        Logger.log(GameConstants.JOKER_LABEL + " not found. Removing card at index " + lastIndex);
                        Logger.log("Removing " + cardList.get(lastIndex).toString());
                        if(cardList.get(lastIndex).equals(currentCard)){
                            Logger.log("Current card trashed -- breaking out");
                            break;
                        }
                        cardList.remove(lastIndex);  
                        if(cardList.size() < 1){
                            Logger.log("Deck empty");
                            break;
                        }
                    }
                }
                if(lastPower > GameConstants.TRUMP_JACK_POWER){
                    lastPower = GameConstants.TRUMP_JACK_POWER;
                    if(currentCard.isJack()
                            && currentCard.getSuit().equals(strongSuit)){
                        thisBid++;
                        Logger.log(currentCard.toString() + " gets bid");
                        continue;
                    }else{
                        int lastIndex = cardList.size()-1;
                        Logger.log(GameConstants.JACK_LABEL + " not found. Removing card at index " + lastIndex);
                        Logger.log("Removing " + cardList.get(lastIndex).toString());
                        if(cardList.get(lastIndex).equals(currentCard)){
                            Logger.log("Current card trashed -- breaking out");
                            break;
                        }
                        cardList.remove(lastIndex);  
                        if(cardList.size() < 1){
                            Logger.log("Deck empty");
                            break;
                        }
                    }
                }
                if(lastPower > GameConstants.COMPLIMENTARY_JACK_POWER){
                    lastPower = GameConstants.COMPLIMENTARY_JACK_POWER;
                    if(currentCard.isJack()
                            && currentCard.getComplimentarySuit().equals(strongSuit)){
                        thisBid++;
                        Logger.log(currentCard.toString() + " gets bid");
                        continue;
                    }else{
                        int lastIndex = cardList.size()-1;
                        Logger.log(GameConstants.JACK_LABEL + " not found. Removing card at index " + lastIndex);
                        Logger.log("Removing " + cardList.get(lastIndex).toString());
                        if(cardList.get(lastIndex).equals(currentCard)){
                            Logger.log("Current card trashed -- breaking out");
                            break;
                        }
                        cardList.remove(lastIndex);  
                        if(cardList.size() < 1){
                            Logger.log("Deck empty");
                            break;
                        }
                    }
                }
            }
            if(lastPower > GameConstants.ACE_POWER){
                lastPower = GameConstants.ACE_POWER;
                if(currentCard.getPower().equals(GameConstants.ACE_POWER)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log(GameConstants.ACE_LABEL + " not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex);  
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(lastPower > GameConstants.KING_POWER){
                lastPower = GameConstants.KING_POWER;
                if(currentCard.getPower().equals(GameConstants.KING_POWER)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log(GameConstants.KING_LABEL + " not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(lastPower > GameConstants.QUEEN_POWER){
                lastPower = GameConstants.QUEEN_POWER;
                if(currentCard.getPower().equals(GameConstants.QUEEN_POWER)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log(GameConstants.QUEEN_LABEL + " not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(suitType == NO_TRUMP
                    && lastPower > GameConstants.JACK_POWER){
                lastPower = GameConstants.JACK_POWER;
                if(currentCard.getPower().equals(GameConstants.JACK_POWER)){
                    if(strongSuit.equals(currentCard.getComplimentarySuit())){
                        // Ignore offsuit jack
                        Logger.log("Ignoring offsuit jack: " + currentCard.toString());
                        continue;
                    }
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log(GameConstants.JACK_LABEL + " not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(lastPower > 10){
                lastPower = 10;
                if(currentCard.getPower().equals(10)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log("Ten not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(lastPower > 9){
                lastPower = 9;
                if(currentCard.getPower().equals(9)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log("9 not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(lastPower > 8){
                lastPower = 8;
                if(currentCard.getPower().equals(8)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                    continue;
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log("8 not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
            if(lastPower > 7){
                lastPower = 7;
                if(currentCard.getPower().equals(7)){
                    thisBid++;
                    Logger.log(currentCard.toString() + " gets bid");
                }else{
                    int lastIndex = cardList.size()-1;
                    Logger.log("7 not found. Removing card at index " + lastIndex);
                    Logger.log("Removing " + cardList.get(lastIndex).toString());
                    if(cardList.get(lastIndex).equals(currentCard)){
                        Logger.log("Current card trashed -- breaking out");
                        break;
                    }
                    cardList.remove(lastIndex); 
                    if(cardList.size() < 1){
                        Logger.log("Deck empty");
                        break;
                    }
                }
            }
        }
        bid += thisBid;
        return thisBid;
    }
    
    private String findSuit(int suitIndex){
        switch(suitIndex){
            case SPADE:
                return GameConstants.SPADES;
            case CLUB:
                return GameConstants.CLUBS;
            case DIAMOND:
                return GameConstants.DIAMONDS;
            case HEART:
                return GameConstants.HEARTS;
            case JOKER:
                return GameConstants.JOKER;
            default:
                Logger.logError(suitIndex + " does not match a suit index");
                return null;
        }
    }
    
    private String findSuit(List<Card> cards){
        int suitCounter[] = {0,0,0,0};
        for(Card card : cards){
            switch(card.getSuit()){
                case GameConstants.SPADES:
                    suitCounter[SPADE]++;
                    break;
                case GameConstants.CLUBS:
                    suitCounter[CLUB]++;
                    break;
                case GameConstants.DIAMONDS:
                    suitCounter[DIAMOND]++;
                    break;
                case GameConstants.HEARTS:
                    suitCounter[HEART]++;
                    break;
                default:
                    Logger.log(card.getSuit() + " not found");
                    break;
            }
        }
        int highSuitCount = 0;
        int highSuit = 0;
        for(int i=0; i<suitCounter.length; i++){
            if(suitCounter[i] > highSuitCount){
                highSuitCount = suitCounter[i];
                highSuit = i;
            }
        }
        return findSuit(highSuit);
    }
    
    private void refreshJacks(List<Card> cardList, int suitType){
        // TODO: check if this method is used anymore
        List<Card> tempCards = new ArrayList<>(cardList);
        if(suitType == COMPLIMENTARY_SUIT){
            for(Card card : tempCards){
                // Only Trump Jacks and Jokers are greater than Ace at this point
                if(card.getPower() > GameConstants.ACE_POWER){
                    cardList.remove(card);
                }
            }
        }else{
            for(Card card : tempCards){
                if(!card.getSuit().equals(findSuit(tempCards))){
                    cardList.remove(card);
                }
            }
        }
    }
    
    /*private List<Card> buildStrongSuit(){
    List<Card> strongSuitCards = new ArrayList<>();
    for(Card card : cards){
    if(card.getSuit().equals(strongSuit)
    || card.getSuit().equals(GameConstants.JOKER)){
    strongSuitCards.add(card);
    }else if(card.getComplimentarySuit().equals(strongSuit)
    && card.getPower().equals(GameConstants.JACK_POWER)){
    strongSuitCards.add(card);
    }
    }
    return strongSuitCards;
    }*/
    
    public String getStrongSuit() {
        return strongSuit;
    }

    public void setStrongSuit(String strongSuit) {
        this.strongSuit = strongSuit;
    }

    public List<Card> getStrongSuitCards() {
        return strongSuitCards;
    }

    public void setStrongSuitCards(List<Card> strongSuitCards) {
        this.strongSuitCards = strongSuitCards;
    }
    
    public List<Card> getComplimentarySuitCards(){
        switch(strongSuit){
            case GameConstants.SPADES:
                return clubs;
            case GameConstants.CLUBS:
                return spades;
            case GameConstants.DIAMONDS:
                return hearts;
            case GameConstants.HEARTS:
                return diamonds;
            default:
                Logger.log("Invalid strong suit: " + strongSuit);
                return null;
        }
    }
    
    public List<Card> getOffsuit(int index){
        switch(strongSuit){
            case GameConstants.SPADES:
            case GameConstants.CLUBS:
                if(index == 0){
                    return diamonds;
                }else{
                    return hearts;
                }
            case GameConstants.DIAMONDS:
            case GameConstants.HEARTS:
                if(index == 0){
                    return spades;
                }else{
                    return clubs;
                }
            default:
                Logger.log("Invalid strong suit: " + strongSuit);
                return null;
        }
    }
    
    public List<Card> getNoTrumpCards(){
        List<Card> noTrumpCards = new ArrayList<>(cards);
        switch(strongSuit){
            case GameConstants.SPADES:
                noTrumpCards.removeAll(spades);
                break;
            case GameConstants.CLUBS:
                noTrumpCards.removeAll(clubs);
                break;
            case GameConstants.DIAMONDS:
                noTrumpCards.removeAll(diamonds);
                break;
            case GameConstants.HEARTS:
                noTrumpCards.removeAll(hearts);
                break;
            default:
                Logger.log("Invalid strong suit: " + strongSuit);
        }
        return noTrumpCards;
    }

    public List<Card> getSpades() {
        return spades;
    }

    public void setSpades(List<Card> spades) {
        this.spades = spades;
    }

    public List<Card> getClubs() {
        return clubs;
    }

    public void setClubs(List<Card> clubs) {
        this.clubs = clubs;
    }

    public List<Card> getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(List<Card> diamonds) {
        this.diamonds = diamonds;
    }

    public List<Card> getHearts() {
        return hearts;
    }

    public void setHearts(List<Card> hearts) {
        this.hearts = hearts;
    }

    public Card getJoker() {
        return joker;
    }

    public void setJoker(Card joker) {
        this.joker = joker;
    }
}