package com.fivehundred.droid500.activity.fragments;

import android.app.Fragment;
import android.os.Bundle;
import com.fivehundred.droid500.game.MainGame;

public class MainFragment extends Fragment {

    private MainGame game;

    // onCreate is only called once
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain the fragment
    }

    public MainGame getGame() {
        return game;
    }

    public void setGame(MainGame game) {
        this.game = game;
    }
}
