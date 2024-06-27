package com.cyberpantera.productcomparison.fragments.compare;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Objects;

public class CompareViewModel extends TableViewModel {

    private CompareViewModel() {
        super();
        getComparablesLiveData().observeForever(comparables -> Objects.requireNonNull(getAdapterLiveData().getValue()).setParameters(comparables.getParameters()));
    }

    public static CompareViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @androidx.annotation.NonNull
            @Override
            public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
                return (T) new CompareViewModel();
            }
        }).get(CompareViewModel.class);
    }
}