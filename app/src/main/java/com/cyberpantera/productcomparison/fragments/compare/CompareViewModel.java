package com.cyberpantera.productcomparison.fragments.compare;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public final class CompareViewModel extends TableViewModel {

    public CompareViewModel(@NonNull Application application) {
        super(application);
        getComparablesLiveData().observeForever(comparables ->
                getAdapter().setParameters(comparables.getParameters(application.getResources())));
    }

    public static CompareViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(CompareViewModel.class);
    }
}