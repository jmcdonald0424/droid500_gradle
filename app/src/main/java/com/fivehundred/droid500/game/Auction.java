package com.fivehundred.droid500.game;

import android.util.SparseArray;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.utils.GameUtils;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.utils.ViewConstants;
import java.util.List;
import javax.inject.Inject;

public class Auction{
    
    private final MainGame game;
    private final List<Player> players;
    private final int dealerIndex;
    private int startingBidIndex;
    private Player nextBidder;
    private int winner = 0;
    private int highestBid = 0;
    private String winningSuit = "";
    private int bidPower = 0;
    private String bidSuit = "";
    private SparseArray<SparseArray<String>> allBids = new SparseArray<SparseArray<String>>();
    
    @Inject GameController gameController;
    
    public Auction(MainGame game){
        this.game = game;
        this.players = game.getPlayers();
        this.dealerIndex = game.getDealerIndex();
        nextBidder = players.get((dealerIndex + 1) % (players.size())); // Bids always start with player to left of dealer
    }
    
    public void startBids(){
        allBids.clear();
        rollBids();
    }
    
    public void rollBids(){
        startingBidIndex = allBids.size();
        for(int count = startingBidIndex; count < players.size(); count++){
            int playerIndex = ((dealerIndex + 1 + count) % (players.size()));
            nextBidder = players.get(playerIndex);
            if(nextBidder.isComputerPlayer()){
                bid(playerIndex, nextBidder.bid());                
            }else{
                Logger.log("Waiting on user");
                return;
            }
        }
        Logger.log("Player " + winner + " wins the bid with " + highestBid + " of " + winningSuit);
        game.setWinningBid(winner, allBids.get(winner));
        //gameController.processKitty(game); //moved this call to MainActivity from MainGame
    }
    
    public void bid(int playerIndex, SparseArray<String> bid){
        int bidIndex = allBids.size();
        nextBidder = players.get(playerIndex);
        allBids.put(players.indexOf(nextBidder), bid);
        bidPower = bid.keyAt(0);
        bidSuit = bid.valueAt(0);
        boolean passed = false;
        Logger.lineBreak();
        if(!(highestBid > bidPower)){
            if(highestBid == 0){
                if(bidPower < 6){
                    highestBid = 6;
                }else{
                    highestBid = bidPower;
                }
                winningSuit = bidSuit;
                winner = playerIndex;
                Logger.log("Player " + playerIndex + " starts the bids with " + highestBid + " " + bidSuit);
            }else if(highestBid < bidPower){
                highestBid = bidPower;
                winningSuit = bidSuit;
                winner = playerIndex;
                Logger.log("Player " + playerIndex + " raises bid " + bidPower + " " + bidSuit);
            }else if(bid.valueAt(0).equals(GameUtils.highestSuit(bidSuit, winningSuit))){
                winningSuit = bidSuit;
                winner = playerIndex;
                Logger.log("Player " + playerIndex + " raises bid " + bidPower + " " + bidSuit);
            }else{
                Logger.log("Player " + playerIndex + " passes");
                passed = true;
            }
        }else{
            Logger.log("Player " + playerIndex + " passes");
            passed = true;
        }
        if(passed){
            gameController.updateBidDisplay(ViewConstants.PASSED, 0, bidIndex, game.getMainActivity());
        }else{
            gameController.updateBidDisplay(bidSuit, bidPower, bidIndex, game.getMainActivity());
        }
    }
}