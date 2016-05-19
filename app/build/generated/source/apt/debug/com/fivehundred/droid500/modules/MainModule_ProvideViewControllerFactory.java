package com.fivehundred.droid500.modules;

import com.fivehundred.droid500.view.controllers.ViewController;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvideViewControllerFactory implements Factory<ViewController> {
  private final MainModule module;

  public MainModule_ProvideViewControllerFactory(MainModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public ViewController get() {
    return Preconditions.checkNotNull(
        module.provideViewController(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<ViewController> create(MainModule module) {
    return new MainModule_ProvideViewControllerFactory(module);
  }
}
