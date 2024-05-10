package com.cyberpantera.productcomparison.fragments.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class MainViewModel extends ViewModel {


    public static MainViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel();
            }
        }).get(MainViewModel.class);
    }
}