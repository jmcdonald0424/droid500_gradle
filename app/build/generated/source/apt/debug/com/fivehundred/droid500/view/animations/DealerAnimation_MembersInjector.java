package com.fivehundred.droid500.view.animations;

import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.controllers.ViewController;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DealerAnimation_MembersInjector implements MembersInjector<DealerAnimation> {
  private final Provider<AnimationController> animationProvider;

  private final Provider<ViewController> viewControllerProvider;

  private final Provider<GameController> gameControllerProvider;

  public DealerAnimation_MembersInjector(
      Provider<AnimationController> animationProvider,
      Provider<ViewController> viewControllerProvider,
      Provider<GameController> gameControllerProvider) {
    assert animationProvider != null;
    this.animationProvider = animationProvider;
    assert viewControllerProvider != null;
    this.viewControllerProvider = viewControllerProvider;
    assert gameControllerProvider != null;
    this.gameControllerProvider = gameControllerProvider;
  }

  public static MembersInjector<DealerAnimation> create(
      Provider<AnimationController> animationProvider,
      Provider<ViewController> viewControllerProvider,
      Provider<GameController> gameControllerProvider) {
    return new DealerAnimation_MembersInjector(
        animationProvider, viewControllerProvider, gameControllerProvider);
  }

  @Override
  public void injectMembers(DealerAnimation instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.animation = animationProvider.get();
    instance.viewController = viewControllerProvider.get();
    instance.gameController = gameControllerProvider.get();
  }

  public static void injectAnimation(
      DealerAnimation instance, Provider<AnimationController> animationProvider) {
    instance.animation = animationProvider.get();
  }

  public static void injectViewController(
      DealerAnimation instance, Provider<ViewController> viewControllerProvider) {
    instance.viewController = viewControllerProvider.get();
  }

  public static void injectGameController(
      DealerAnimation instance, Provider<GameController> gameControllerProvider) {
    instance.gameController = gameControllerProvider.get();
  }
}
