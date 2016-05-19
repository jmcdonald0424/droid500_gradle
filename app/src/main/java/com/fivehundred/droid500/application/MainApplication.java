package com.fivehundred.droid500.application;

import android.app.Application;

import com.fivehundred.droid500.components.DaggerMainComponent;
import com.fivehundred.droid500.components.MainComponent;
import com.fivehundred.droid500.modules.MainModule;

public class MainApplication extends Application{

    private MainComponent mainComponent;
    
    @Override
    public void onCreate(){
        super.onCreate();

        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(this)).build();
    }

    public MainComponent getMainComponent(){
        return mainComponent;
    }
}