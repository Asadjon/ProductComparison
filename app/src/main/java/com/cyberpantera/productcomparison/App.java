package com.cyberpantera.productcomparison;

import android.app.Application;

import com.cyberpantera.productcomparison.statics.AssetManager;

import lombok.Getter;

public class App extends Application {

    @Getter
    private static App instance;

    public App() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AssetManager.initialize(getAssets());
    }
}
