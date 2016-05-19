package com.fivehundred.droid500.view.controllers;

import com.fivehundred.droid500.game.Card;
import javax.inject.Inject;

public class ViewController{

    @Inject
    public ViewController(){}

    public void flip(Card card){
        card.flip();
    }
}