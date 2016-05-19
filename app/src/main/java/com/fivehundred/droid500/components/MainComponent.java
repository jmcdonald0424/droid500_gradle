package com.fivehundred.droid500.components;

import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.game.Auction;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.controllers.GameControllerImpl;
import com.fivehundred.droid500.modules.MainModule;
import com.fivehundred.droid500.view.GLRenderer;
import com.fivehundred.droid500.view.animations.DealerAnimation;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.controllers.ViewController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={MainModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
    void inject(MainGame game);
    void inject(GLRenderer renderer);
    void inject(DealerAnimation dealerAnimation);
    void inject(Auction auction);
    AnimationController getAnimationController();
    GameControllerImpl getGameController();
    ViewController getViewController();
}
