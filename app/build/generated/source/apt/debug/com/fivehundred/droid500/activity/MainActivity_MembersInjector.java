package com.fivehundred.droid500.activity;

import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.view.controllers.ViewController;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<GameController> gameControllerProvider;

  private final Provider<ViewController> viewControllerProvider;

  public MainActivity_MembersInjector(
      Provider<GameController> gameControllerProvider,
      Provider<ViewController> viewControllerProvider) {
    assert gameControllerProvider != null;
    this.gameControllerProvider = gameControllerProvider;
    assert viewControllerProvider != null;
    this.viewControllerProvider = viewControllerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<GameController> gameControllerProvider,
      Provider<ViewController> viewControllerProvider) {
    return new MainActivity_MembersInjector(gameControllerProvider, viewControllerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.gameController = gameControllerProvider.get();
    instance.viewController = viewControllerProvider.get();
  }

  public static void injectGameController(
      MainActivity instance, Provider<GameController> gameControllerProvider) {
    instance.gameController = gameControllerProvider.get();
  }

  public static void injectViewController(
      MainActivity instance, Provider<ViewController> viewControllerProvider) {
    instance.viewController = viewControllerProvider.get();
  }
}
