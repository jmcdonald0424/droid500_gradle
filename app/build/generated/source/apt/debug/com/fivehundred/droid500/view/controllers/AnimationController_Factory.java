package com.fivehundred.droid500.view.controllers;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public enum AnimationController_Factory implements Factory<AnimationController> {
  INSTANCE;

  @Override
  public AnimationController get() {
    return new AnimationController();
  }

  public static Factory<AnimationController> create() {
    return INSTANCE;
  }
}
