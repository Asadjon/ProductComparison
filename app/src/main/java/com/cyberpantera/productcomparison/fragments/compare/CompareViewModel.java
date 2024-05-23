package com.cyberpantera.productcomparison.fragments.compare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.adapters.CompareProductAdapter;
import com.cyberpantera.productcomparison.models.Comparables;

import java.util.Objects;

public class CompareViewModel extends ViewModel {

    private final MutableLiveData<Comparables> comparables = new MutableLiveData<>();

    public LiveData<Comparables> getComparablesLiveData() {
        return comparables;
    }

    public void setComparables(Comparables comparables) {
        this.comparables.setValue(comparables);
    }

    private final MutableLiveData<CompareProductAdapter> compareProductAdapter = new MutableLiveData<>(new CompareProductAdapter());

    public LiveData<CompareProductAdapter> getAdapterLiveData() {
        return compareProductAdapter;
    }

    private CompareViewModel() {
        this.comparables.observeForever(comparables -> Objects.requireNonNull(compareProductAdapter.getValue()).setParameters(comparables.getParameters()));
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