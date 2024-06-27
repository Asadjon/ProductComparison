package com.cyberpantera.productcomparison.fragments.compare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cyberpantera.productcomparison.adapters.CompareProductAdapter;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.data.Data;

import java.util.Objects;

public abstract class TableViewModel  extends ViewModel {

    protected final MutableLiveData<Comparables<Data>> comparables = new MutableLiveData<>();

    public LiveData<Comparables<Data>> getComparablesLiveData() {
        return comparables;
    }

    public void setComparables(Comparables<Data> comparables) {
        this.comparables.setValue(comparables);
    }

    protected final MutableLiveData<CompareProductAdapter> compareProductAdapter = new MutableLiveData<>(new CompareProductAdapter());

    public LiveData<CompareProductAdapter> getAdapterLiveData() {
        return compareProductAdapter;
    }

    protected final MutableLiveData<Boolean> isHideCorresponds = new MutableLiveData<>(false);

    public LiveData<Boolean> isHideCorresponds() {
        return isHideCorresponds;
    }

    public void setIsHideCorresponds(boolean isHideCorresponds) {
        this.isHideCorresponds.setValue(isHideCorresponds);
    }

    public TableViewModel() {
        isHideCorresponds().observeForever(isHideCorresponds -> Objects.requireNonNull(getAdapterLiveData().getValue()).setHideCorresponds(isHideCorresponds));
    }
}
