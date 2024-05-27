package com.cyberpantera.productcomparison.statics;

import java.io.IOException;
import java.io.InputStream;

import lombok.Getter;

public final class AssetManager {

    @Getter
    private final android.content.res.AssetManager assetManager;

    @Getter
    private static AssetManager instance;
    public static void initialize(android.content.res.AssetManager assetManager) {
        if (instance == null)
            instance = new AssetManager(assetManager);
    }

    private AssetManager(android.content.res.AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public  InputStream openFile(String path) {
        try {
            return assetManager.open(path);
        } catch (IOException e) {
            e.fillInStackTrace();
            return null;
        }
    }
}
