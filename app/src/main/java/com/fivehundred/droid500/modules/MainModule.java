package com.fivehundred.droid500.modules;

import android.content.Context;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.application.MainApplication;
import com.fivehundred.droid500.game.Auction;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.game.controllers.GameControllerImpl;
import com.fivehundred.droid500.view.GLRenderer;
import com.fivehundred.droid500.view.animations.DealerAnimation;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.controllers.ViewController;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class MainModule{
    private final MainApplication app;
    private final Context context;
    
    public MainModule(MainApplication app){
        this.app = app;
        this.context = this.app.getApplicationContext();
    }
    
    @Singleton
    @Provides 
    public Context provideApplicationContext(){
        return this.context;
    }

    @Singleton
    @Provides
    public GameController provideGameController(){
        return new GameControllerImpl();
    }

    @Singleton
    @Provides
    public ViewController provideViewController() { return new ViewController();}

    @Singleton
    @Provides
    public AnimationController provideAnimationController() { return new AnimationController(); }
}