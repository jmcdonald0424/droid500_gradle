package com.fivehundred.droid500.components;

import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.activity.MainActivity_MembersInjector;
import com.fivehundred.droid500.game.Auction;
import com.fivehundred.droid500.game.Auction_MembersInjector;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.MainGame_MembersInjector;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.game.controllers.GameControllerImpl;
import com.fivehundred.droid500.game.controllers.GameControllerImpl_Factory;
import com.fivehundred.droid500.modules.MainModule;
import com.fivehundred.droid500.modules.MainModule_ProvideAnimationControllerFactory;
import com.fivehundred.droid500.modules.MainModule_ProvideGameControllerFactory;
import com.fivehundred.droid500.modules.MainModule_ProvideViewControllerFactory;
import com.fivehundred.droid500.view.GLRenderer;
import com.fivehundred.droid500.view.GLRenderer_MembersInjector;
import com.fivehundred.droid500.view.animations.DealerAnimation;
import com.fivehundred.droid500.view.animations.DealerAnimation_MembersInjector;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.controllers.ViewController;
import dagger.MembersInjector;
import dagger.internal.Preconditions;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMainComponent implements MainComponent {
  private Provider<GameController> provideGameControllerProvider;

  private Provider<ViewController> provideViewControllerProvider;

  private MembersInjector<MainActivity> mainActivityMembersInjector;

  private MembersInjector<MainGame> mainGameMembersInjector;

  private Provider<AnimationController> provideAnimationControllerProvider;

  private MembersInjector<GLRenderer> gLRendererMembersInjector;

  private MembersInjector<DealerAnimation> dealerAnimationMembersInjector;

  private MembersInjector<Auction> auctionMembersInjector;

  private DaggerMainComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.provideGameControllerProvider =
        ScopedProvider.create(MainModule_ProvideGameControllerFactory.create(builder.mainModule));

    this.provideViewControllerProvider =
        ScopedProvider.create(MainModule_ProvideViewControllerFactory.create(builder.mainModule));

    this.mainActivityMembersInjector =
        MainActivity_MembersInjector.create(
            provideGameControllerProvider, provideViewControllerProvider);

    this.mainGameMembersInjector = MainGame_MembersInjector.create(provideGameControllerProvider);

    this.provideAnimationControllerProvider =
        ScopedProvider.create(
            MainModule_ProvideAnimationControllerFactory.create(builder.mainModule));

    this.gLRendererMembersInjector =
        GLRenderer_MembersInjector.create(provideAnimationControllerProvider);

    this.dealerAnimationMembersInjector =
        DealerAnimation_MembersInjector.create(
            provideAnimationControllerProvider,
            provideViewControllerProvider,
            provideGameControllerProvider);

    this.auctionMembersInjector = Auction_MembersInjector.create(provideGameControllerProvider);
  }

  @Override
  public void inject(MainActivity activity) {
    mainActivityMembersInjector.injectMembers(activity);
  }

  @Override
  public void inject(MainGame game) {
    mainGameMembersInjector.injectMembers(game);
  }

  @Override
  public void inject(GLRenderer renderer) {
    gLRendererMembersInjector.injectMembers(renderer);
  }

  @Override
  public void inject(DealerAnimation dealerAnimation) {
    dealerAnimationMembersInjector.injectMembers(dealerAnimation);
  }

  @Override
  public void inject(Auction auction) {
    auctionMembersInjector.injectMembers(auction);
  }

  @Override
  public AnimationController getAnimationController() {
    return provideAnimationControllerProvider.get();
  }

  @Override
  public GameControllerImpl getGameController() {
    return GameControllerImpl_Factory.create().get();
  }

  @Override
  public ViewController getViewController() {
    return provideViewControllerProvider.get();
  }

  public static final class Builder {
    private MainModule mainModule;

    private Builder() {}

    public MainComponent build() {
      if (mainModule == null) {
        throw new IllegalStateException(MainModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerMainComponent(this);
    }

    public Builder mainModule(MainModule mainModule) {
      this.mainModule = Preconditions.checkNotNull(mainModule);
      return this;
    }
  }
}
