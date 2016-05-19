package com.fivehundred.droid500.game;

import com.fivehundred.droid500.game.controllers.GameController;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainGame_MembersInjector implements MembersInjector<MainGame> {
  private final Provider<GameController> gameControllerProvider;

  public MainGame_MembersInjector(Provider<GameController> gameControllerProvider) {
    assert gameControllerProvider != null;
    this.gameControllerProvider = gameControllerProvider;
  }

  public static MembersInjector<MainGame> create(Provider<GameController> gameControllerProvider) {
    return new MainGame_MembersInjector(gameControllerProvider);
  }

  @Override
  public void injectMembers(MainGame instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.gameController = gameControllerProvider.get();
  }

  public static void injectGameController(
      MainGame instance, Provider<GameController> gameControllerProvider) {
    instance.gameController = gameControllerProvider.get();
  }
}
