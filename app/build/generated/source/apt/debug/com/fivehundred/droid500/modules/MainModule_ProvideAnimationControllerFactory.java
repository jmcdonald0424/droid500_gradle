package com.fivehundred.droid500.modules;

import com.fivehundred.droid500.view.controllers.AnimationController;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvideAnimationControllerFactory
    implements Factory<AnimationController> {
  private final MainModule module;

  public MainModule_ProvideAnimationControllerFactory(MainModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public AnimationController get() {
    return Preconditions.checkNotNull(
        module.provideAnimationController(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AnimationController> create(MainModule module) {
    return new MainModule_ProvideAnimationControllerFactory(module);
  }
}
