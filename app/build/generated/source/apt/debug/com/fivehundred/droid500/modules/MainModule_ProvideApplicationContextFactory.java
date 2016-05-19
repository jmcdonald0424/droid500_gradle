package com.fivehundred.droid500.modules;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvideApplicationContextFactory implements Factory<Context> {
  private final MainModule module;

  public MainModule_ProvideApplicationContextFactory(MainModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Context get() {
    return Preconditions.checkNotNull(
        module.provideApplicationContext(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Context> create(MainModule module) {
    return new MainModule_ProvideApplicationContextFactory(module);
  }
}
