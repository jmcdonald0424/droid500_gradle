package com.fivehundred.droid500.view.controllers;

import android.widget.RelativeLayout;

import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.game.Card;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.view.GLSurf;

import javax.inject.Inject;

public class ViewController{

    private GLSurf glSurfaceView; // Our OpenGL Surfaceview

    @Inject
    public ViewController(){}

    public void onPause(){
        glSurfaceView.onPause();
    }

    public void onResume(){
        glSurfaceView.onResume();
    }

    public void buildMainView(MainActivity activity){
        // We create our Surfaceview for our OpenGL here.
        // Preloaded objects can be rendered at this time.
        // TODO: Consider second thread in the future
        glSurfaceView = new GLSurf(activity);
        glSurfaceView.getRenderer().loadIntoObjectGraph();
        glSurfaceView.getRenderer().createCardSprites(activity.getGame().getDeck());
    }

    public GLSurf buildBidView(MainGame game){
        glSurfaceView.getRenderer().buildCardSprites(game.getMyHand());
        return glSurfaceView;
    }

    public void animateDealCards(MainGame game){
        glSurfaceView.dealCards(game);
    }

    public void flip(Card card){
        card.flip();
    }

    public void setLayoutParams(RelativeLayout.LayoutParams glParams){
        glSurfaceView.setLayoutParams(glParams);
    }

    public GLSurf getGlSurfaceView() {
        return glSurfaceView;
    }

    public void setGlSurfaceView(GLSurf glSurfaceView) {
        this.glSurfaceView = glSurfaceView;
    }
}