package com.fivehundred.droid500.game;

import com.fivehundred.droid500.game.controllers.GameController;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Auction_MembersInjector implements MembersInjector<Auction> {
  private final Provider<GameController> gameControllerProvider;

  public Auction_MembersInjector(Provider<GameController> gameControllerProvider) {
    assert gameControllerProvider != null;
    this.gameControllerProvider = gameControllerProvider;
  }

  public static MembersInjector<Auction> create(Provider<GameController> gameControllerProvider) {
    return new Auction_MembersInjector(gameControllerProvider);
  }

  @Override
  public void injectMembers(Auction instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.gameController = gameControllerProvider.get();
  }

  public static void injectGameController(
      Auction instance, Provider<GameController> gameControllerProvider) {
    instance.gameController = gameControllerProvider.get();
  }
}
