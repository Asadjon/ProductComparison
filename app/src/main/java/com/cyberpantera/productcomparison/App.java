package com.cyberpantera.productcomparison;

import android.app.Application;

import lombok.Getter;

public class App extends Application {

    @Getter
    private static App instance;

    public App() {
        instance = this;
    }
}
