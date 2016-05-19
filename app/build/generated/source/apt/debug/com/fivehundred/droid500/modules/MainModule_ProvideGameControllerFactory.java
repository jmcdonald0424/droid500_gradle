package com.fivehundred.droid500.modules;

import com.fivehundred.droid500.game.controllers.GameController;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvideGameControllerFactory implements Factory<GameController> {
  private final MainModule module;

  public MainModule_ProvideGameControllerFactory(MainModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public GameController get() {
    return Preconditions.checkNotNull(
        module.provideGameController(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GameController> create(MainModule module) {
    return new MainModule_ProvideGameControllerFactory(module);
  }
}
