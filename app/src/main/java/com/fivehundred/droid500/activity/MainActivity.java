package com.fivehundred.droid500.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fivehundred.droid500.activity.fragments.MainFragment;
import com.fivehundred.droid500.application.MainApplication;
import com.fivehundred.droid500.BuildConfig;
import com.fivehundred.droid500.R;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.game.controllers.GameControllerImpl;
import com.fivehundred.droid500.utils.ApplicationConstants;
import com.fivehundred.droid500.utils.GameConstants;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.GLSurf;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.controllers.ViewController;
import com.fivehundred.droid500.view.utils.BidDisplayAdapter;
import com.fivehundred.droid500.view.utils.ViewConstants;
import com.fivehundred.droid500.view.utils.ViewListenerConstants;
import javax.inject.Inject;

public class MainActivity extends Activity {
   
    private MainGame game; // Main game will be held and referenced here    
    private MainFragment fragment; // MainFragment allows persistence 
    
    // Views
    private RelativeLayout gameLayout;
    private View bidView;
    private LinearLayout bidValueButtonLayout;
    private LinearLayout bidLayout;
    private GridView scoreGrid;
    private GridView bidDisplay;
    private LinearLayout bidDisplayLayout;
    private TextView bidValue;
    private GridView bidButtons;
    private RelativeLayout bidHand;
    private GLSurf bidHandView;
    private View kittyView;
    private TextView winningBid;
    
    private int playerCount = 4;

    // Controllers
    @Inject GameController gameController;
    @Inject ViewController viewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Super
        super.onCreate(savedInstanceState);
        
        // Inject into object graph
        MainApplication app = (MainApplication)getApplication();
        app.getMainComponent().inject(this);

        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create new game or pull existing game from fragment
        handleGameFragment();
        
        // TODO: Preload all available view dependents and start rendering (i.e. cards and other images available after constructor)
        buildMainView();
        
        // Set our view.
        setContentView(R.layout.main);

        // Retrieve our Relative layout from our main layout we just set to our view.
        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.gamelayout);

        // Attach our surfaceview to our relative layout from our main layout.
        //RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //layout.addView(glSurfaceView, glParams);
    }
    
    /*
    *  Called when user clicks the start button.
    *  This is only temporary for testing purposes.
    */
    public void startGame(View startButton){
        if(BuildConfig.DEBUG){
            Log.d(ApplicationConstants.TAG, "starting game");
        }
        gameController.startGame(game);
        loadGameGraphics();
        dealCards();
    }

    private void dealCards(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                viewController.animateDealCards(game);
            }
        });
    }

    public void startBids(MainGame game){
        loadLayout(ViewListenerConstants.BID_LAYOUT);
        gameController.startBids(game);
    }
    
    public void confirmBid(View confirmBidButton){
        gameController.processPlayerBid(game);
    }
    
    public void passBid(View passBidButton){
        gameController.passBid(game);
    }

    public void confirmKitty(View confirmKittyButton){
        boolean kittyProcessed = gameController.processKitty(game);
        if(kittyProcessed){
            //Start game
            loadLayout(ViewListenerConstants.GAME_LAYOUT);
        }
    }
    
    public void setBidPower(View bidPowerButton){
        resetBidPowerButtons(bidPowerButton);
        gameController.setPlayerBidPower(((Button)bidPowerButton).getText().toString(), game);
    }
    
    public void setBidSuit(View bidSuitButton){
        String bidSuit = "";
        switch(bidSuitButton.getId()){
            case R.id.button_bidspades:
                bidSuit = GameConstants.SPADES;
                break;
            case R.id.button_bidclubs:
                bidSuit = GameConstants.CLUBS;
                break;
            case R.id.button_biddiamonds:
                bidSuit = GameConstants.DIAMONDS;
                break;
            case R.id.button_bidhearts:
                bidSuit = GameConstants.HEARTS;
                break;
            case R.id.button_bidnotrump:
                bidSuit = GameConstants.NO_TRUMP;
                break;
            default:
                Logger.logError("setBidSuit failed -- no suit found for " + bidSuitButton.getId());
        }
        gameController.setPlayerBidSuit(bidSuit, game);
    }

    public void processKitty(MainGame game){
        loadLayout(ViewListenerConstants.KITTY_LAYOUT);
    }
    
    private void loadGameGraphics(){
        // Retrieve our Relative layout from our main layout we just set to our view.
        gameLayout = (RelativeLayout) findViewById(R.id.gamelayout);

        // Attach our surfaceview to our relative layout from our main layout.
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //gameLayout.removeAllViews();
        clearMainView();
        viewController.setLayoutParams(glParams);
        //gameLayout.addView(viewController.getGlSurfaceView(), glParams);
        updateMainView();
    }
    
    public void loadLayout(int layout){
        switch(layout){
            case ViewListenerConstants.GAME_LAYOUT:
                loadGameLayout();
                break;
            case ViewListenerConstants.BID_LAYOUT:
                loadBidLayout();
                break;
            case ViewListenerConstants.KITTY_LAYOUT:
                loadKittyLayout();
                break;
            default:
                Logger.logError("loadLayout failed -- cannot find layout: " + layout);
                
        }
    }

    private void loadGameLayout(){
        gameLayout = (RelativeLayout) findViewById(R.id.gamelayout);
        clearMainView();
        buildGameView();
        updateMainView();
    }

    private void loadBidLayout(){
        LayoutInflater inflater = getLayoutInflater();
        gameLayout = (RelativeLayout) findViewById(R.id.gamelayout);
        bidView = inflater.inflate(R.layout.bid_portrait, null);
        bidLayout = (LinearLayout)bidView.findViewById(R.id.bidlayout);
        scoreGrid = (GridView)bidView.findViewById(R.id.scoregrid);
        bidDisplay = (GridView)bidView.findViewById(R.id.biddisplay);
        //bidButtons = (GridView)view.findViewById(R.id.bidbuttons);
        bidHand = (RelativeLayout)bidView.findViewById(R.id.bidhand);
        clearMainView();
        buildBidView();
        updateMainView(bidLayout);
    }

    private void loadKittyLayout(){
        LayoutInflater inflater = getLayoutInflater();
        gameLayout = (RelativeLayout) findViewById(R.id.gamelayout);
        kittyView = inflater.inflate(R.layout.kitty_portrait, null);
        bidHand = (RelativeLayout)kittyView.findViewById(R.id.kittyview);
        winningBid = (TextView)kittyView.findViewById(R.id.winningbid);
        clearMainView();
        buildKittyView();
        updateMainView(kittyView);
    }

    private void updateMainView(){
        updateMainView(viewController.getGlSurfaceView());
    }

    private void updateMainView(final View view){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(view != null){
                    // Check if the view has already been assigned to a parent. If so, remove it
                    ViewGroup parentViewGroup = (ViewGroup)view.getParent();
                    if(parentViewGroup != null){
                        parentViewGroup.removeAllViews();
                    }
                    gameLayout.addView(view);
                }
            }
        });
    }

    private void clearMainView(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                gameLayout.removeAllViews();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewController.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewController.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment.setGame(game);
    }

    private void handleGameFragment() {
        FragmentManager manager = getFragmentManager();
        fragment = (MainFragment) manager.findFragmentByTag("game");
        if (fragment == null) {
            fragment = new MainFragment();
            manager.beginTransaction().add(fragment, "game").commit();
            game = new MainGame(playerCount, this);//gameController.createNewGame(playerCount, this);
            fragment.setGame(game);
            // Inject into object graph
            MainApplication app = (MainApplication)getApplication();
            app.getMainComponent().inject(game);
        } else {
            game = fragment.getGame();
        }
    }

    public MainGame getGame() {
        return game;
    }
    
    public int getNumberOfPlayers(){
        return playerCount;
    }
    
    private LayoutInflater getLayoutInflator(){
        return (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    private void buildMainView(){
        viewController.buildMainView(this);
    }

    private void buildGameView(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                viewController.buildGameView(game);
            }
        });
    }
    
    private void buildBidView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ViewConstants.SCORE_GRID);
        scoreGrid.setAdapter(adapter);
        
        bidDisplay.setAdapter(new BidDisplayAdapter(this, ViewConstants.PLAYER_VALUES));
        
        //bidButtons.setAdapter(new BidButtonsAdapter(this, ViewConstants.BID_VALUES));
        //bidHandView = new GLSurf(this);
        //bidHandView.getRenderer().buildCardSprites(game.getMyHand());
        bidHand.addView(viewController.buildBidView(game));
    }

    private void buildKittyView(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                GLSurf kittyView = viewController.buildKittyView(game);
                if(kittyView != null){
                    // Check if the view has already been assigned to a parent. If so, remove it
                    ViewGroup parentViewGroup = (ViewGroup)kittyView.getParent();
                    if(parentViewGroup != null){
                        parentViewGroup.removeAllViews();
                    }
                    bidHand.addView(kittyView);
                }
                winningBid.setText(game.getWinningBid().keyAt(0) + game.getWinningBid().valueAt(0));
            }
        });
        //viewController.buildKittyView(game);
    }
    
    public void updateBidDisplay(String bidSuit, int bidPower, int bidIndex){
        final int power = bidPower;
        final String suit = bidSuit;
        final int index = bidIndex;
        String bid = Integer.toString(power) + suit;
        BidDisplayAdapter adapter = (BidDisplayAdapter)bidDisplay.getAdapter();
        adapter.setBid(index, bid); 
    }
    
    private void resetBidPowerButtons(View pressedBidPowerButton){
        /*((Button)pressedBidPowerButton).setBackgroundResource(R.color.blue);
        bidView = getLayoutInflater().inflate(R.layout.bid_portrait, null);
        bidValueButtonLayout = (LinearLayout)bidView.findViewById(R.id.bidvaluebuttonlayout);
        int count = 5;
        View v = null;
        for(int i=0; i<count; i++){
            v = bidValueButtonLayout.getChildAt(i);
            if(v.getId() != pressedBidPowerButton.getId()){
                ((Button)v).setBackgroundResource(R.color.default_gray);
            }
        }*/
    }
}
