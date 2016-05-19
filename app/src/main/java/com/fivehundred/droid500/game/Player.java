package com.fivehundred.droid500.game;

import android.util.SparseArray;
import com.fivehundred.droid500.ai.ActionUtils;
import com.fivehundred.droid500.ai.Bidder;
import com.fivehundred.droid500.utils.GameUtils;
import com.fivehundred.droid500.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Card> cards = new ArrayList<>();
    private final int playerIndex;
    
    private Bidder bidder;
    
    public Player(int playerIndex){
        this.playerIndex = playerIndex;
    }
    
    public SparseArray<String> bid(){
        bidder = new Bidder(cards);
        return bidder.bid();
    }
    
    public void reduceHand(){
        int excess = cards.size() - 10;
        GameUtils.descSort(cards);
        for(int i=0; i<excess; i++){
            Logger.log("Removing " + cards.get(cards.size() - 1).toString());
            cards.remove(cards.size() - 1);
        }
    }
    
    public boolean has(String suit){
        for(Card card : cards){
            if(suit.equals(card.getSuit())){
                return true;
            }
        }
        return false;
    }
    
    public int play(Hand hand){
        Card playCard = ActionUtils.playHand(hand, this);
        Logger.log("Player " + playerIndex + " plays " + playCard.toString());
        return hand.play(playCard, playerIndex);
    }
    
    public Card playHighCard(){
        Card card = null;
        if(cards.isEmpty()){
            Logger.log("Player " + playerIndex + " has no more cards.");
            return card;
        }
        GameUtils.descSort(cards);
        card = cards.get(0);
        remove(card);
        return card;
    }
    
    public Card playLowestWinner(Integer highPower){
        Card card = null;
        if(cards.isEmpty()){
            Logger.log("Player " + playerIndex + " has no more cards.");
            return card;
        }
        GameUtils.ascSort(cards);
        for(Card c : cards){
            if(c.getPower() > highPower){
                card = c;
                break;
            }
        }
        if(card != null){
            remove(card);
            return card;
        }
        return throwOff();
    }
    
    public Card playLowestWinner(Integer highPower, String suit){
        Card card = null;
        if(cards.isEmpty()){
            Logger.log("Player " + playerIndex + " has no more cards.");
            return card;
        }
        GameUtils.ascSort(cards);
        for(Card c : cards){
            if(c.getSuit().equals(suit)
                    && c.getPower() > highPower){
                card = c;
                break;
            }
        }
        if(card != null){
            remove(card);
            return card;
        }
        return throwOff(suit);
    }
    
    public Card throwOff(){
        Card card = null;
        if(cards.isEmpty()){
            Logger.log("Player " + playerIndex + " has no more cards.");
            return card;
        }
        GameUtils.ascSort(cards);
        card = cards.get(0);
        remove(card);
        return card;
    }
    
    public Card throwOff(String suit){
        Card card = null;
        if(cards.isEmpty()){
            Logger.log("Player " + playerIndex + " has no more cards.");
            return card;
        }
        GameUtils.ascSort(cards);
        for(Card c : cards){
            if(c.getSuit().equals(suit)){
                card = c;
                break;
            }
        }
        if(card != null){
            remove(card);
            return card;
        }
        Logger.log("Throw off failed: Player " + playerIndex + " has no " + suit);
        return card;
    }
    
    private void remove(Card card){
        cards.remove(card);
    }
    
    public void clearCards(){
        cards.clear();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
    
    public int getPartnerIndex(){
        return playerIndex > 1 ? playerIndex - 2 : playerIndex + 2;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }
    
    public boolean isComputer(){
        return playerIndex != 0; // TODO: This will need to change when multiple players are added
    }
}
