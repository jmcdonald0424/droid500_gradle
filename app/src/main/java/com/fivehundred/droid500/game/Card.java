package com.fivehundred.droid500.game;

import com.fivehundred.droid500.utils.ConversionUtils;
import com.fivehundred.droid500.utils.GameConstants;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.Atlas;
import com.fivehundred.droid500.view.Sprite;
import com.fivehundred.droid500.view.utils.ViewConstants;

public class Card{
	
    private Integer power;
    private String suit;
    private Sprite sprite;
    private boolean faceUp = false;
    private boolean focused = false;
    
    public Card(String suit, Integer power){
        this.suit = suit;
        this.power = power;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(ConversionUtils.getPowerLabel(power));
        if(!power.equals(GameConstants.JOKER_POWER)){
            sb.append(" of ");
            /*if(power.equals(GameConstants.COMPLIMENTARY_JACK_POWER)){
                sb.append(ConversionUtils.getSuitLabel(getComplimentarySuit()));
            }else{                
                sb.append(ConversionUtils.getSuitLabel(suit));
            }*/
            sb.append(ConversionUtils.getSuitLabel(suit));
        }
        return sb.toString();
    }
    
    public String getComplimentarySuit(){
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
                return "NONE";
            default:
                Logger.logError("Complimentary suit not found for " + suit);
                return null;
        }
    }
    
    public boolean isTrump(){
        return power > GameConstants.ACE_POWER+1;
    }
    
    public boolean isJack(){
        switch(power){
            case GameConstants.JACK_POWER:
            case GameConstants.COMPLIMENTARY_JACK_POWER:
            case GameConstants.TRUMP_JACK_POWER:
                return true;
            default:
                return false;
        }
    }
    
    public boolean isJoker(){
        return power.equals(GameConstants.JOKER_POWER);
    }
    
    public int getUvIndex(){
        switch(power){
            case GameConstants.JOKER_POWER:
                return ViewConstants.JOKER_UV_INDEX;
            case GameConstants.ACE_POWER:
                return (ViewConstants.ACE_ROW - 1) * Atlas.MAIN_ATLAS.getColumnCount() + getSuitPower();
            default:
                return Atlas.MAIN_ATLAS.getColumnCount() * getSuitPower() + power - 4;
        }
    }
    
    public int getSuitPower(){
        switch(suit){
            case GameConstants.SPADES:
                return GameConstants.SPADES_POWER;
            case GameConstants.CLUBS:
                return GameConstants.CLUBS_POWER;
            case GameConstants.DIAMONDS:
                return GameConstants.DIAMONDS_POWER;
            case GameConstants.HEARTS:
                return GameConstants.HEARTS_POWER;
            case GameConstants.JOKER:
                return GameConstants.JOKER_POWER;
            default:
                Logger.log("getSuitPower failed -- No power found for suit: " + suit);
                return -1;
        }
    }
    
    public void flip(){
        if(isFaceUp()){
            sprite.generateUvCoords(ViewConstants.CARD_BACK_INDEX);
            sprite.triggerUvUpdate();
            faceUp = false;
        }else{
            sprite.generateUvCoords(sprite.getIndex());
            sprite.triggerUvUpdate();
            faceUp = true;
        }
    }

    public boolean isSelected(float x, float y){
        return sprite.isTouchCollision(x, y);
    }

    public void focus(){
        if(!focused){
            focused = true;
            sprite.focus();
        }
    }

    public void unfocus(){
        if(focused){
            focused = false;
            sprite.unfocus();
        }
    }

    public void toggleFocus(){
        if(!focused){
            focused = true;
            sprite.focus();
        }else{
            focused = false;
            sprite.unfocus();
        }
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public boolean isFaceUp(){
        return faceUp;
    }
    
    public void setFaceUp(boolean faceUp){
        this.faceUp = faceUp;
    }

    public boolean isFocused(){return focused;}
}