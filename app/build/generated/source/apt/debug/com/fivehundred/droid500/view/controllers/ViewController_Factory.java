package com.fivehundred.droid500.view.controllers;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public enum ViewController_Factory implements Factory<ViewController> {
  INSTANCE;

  @Override
  public ViewController get() {
    return new ViewController();
  }

  public static Factory<ViewController> create() {
    return INSTANCE;
  }
}
