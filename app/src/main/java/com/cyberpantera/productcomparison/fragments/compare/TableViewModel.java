package com.cyberpantera.productcomparison.fragments.compare;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cyberpantera.productcomparison.adapters.CompareProductAdapter;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.data.Data;

public abstract class TableViewModel  extends AndroidViewModel {

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

    public CompareProductAdapter getAdapter() {
        return compareProductAdapter.getValue();
    }

    protected final MutableLiveData<Boolean> isHideCorresponds = new MutableLiveData<>(false);

    public LiveData<Boolean> isHideCorresponds() {
        return isHideCorresponds;
    }

    public void setIsHideCorresponds(boolean isHideCorresponds) {
        this.isHideCorresponds.setValue(isHideCorresponds);
    }

    public TableViewModel(@NonNull Application application) {
        super(application);

        isHideCorresponds().observeForever(isHideCorresponds -> getAdapter().setHideCorresponds(isHideCorresponds));
    }
}
