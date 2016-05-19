package com.fivehundred.droid500.view;

import com.fivehundred.droid500.view.controllers.AnimationController;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GLRenderer_MembersInjector implements MembersInjector<GLRenderer> {
  private final Provider<AnimationController> animationProvider;

  public GLRenderer_MembersInjector(Provider<AnimationController> animationProvider) {
    assert animationProvider != null;
    this.animationProvider = animationProvider;
  }

  public static MembersInjector<GLRenderer> create(
      Provider<AnimationController> animationProvider) {
    return new GLRenderer_MembersInjector(animationProvider);
  }

  @Override
  public void injectMembers(GLRenderer instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.animation = animationProvider.get();
  }

  public static void injectAnimation(
      GLRenderer instance, Provider<AnimationController> animationProvider) {
    instance.animation = animationProvider.get();
  }
}
