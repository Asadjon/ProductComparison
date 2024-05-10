package com.cyberpantera.productcomparison.fragments.select_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.models.Product;

public class SelectModelViewModel extends ViewModel {

    private final MutableLiveData<Product> product = new MutableLiveData<>();

    public Product getProduct() {
        return product.getValue();
    }

    public LiveData<Product> getProductLiveData() {
        return product;
    }

    public void setProduct(Product product) {
        this.product.setValue(product);
    }

    private final MutableLiveData<Void> onClickCompare = new MutableLiveData<>();

    public LiveData<Void> getOnClickCompareLiveData() {
        return onClickCompare;
    }

    public void onClickCompare() {
        onClickCompare.setValue(null);
    }

    private final MutableLiveData<Void> onValueChange = new MutableLiveData<>();

    public LiveData<Void> getOnValueChangeLiveData() {
        return onValueChange;
    }

    public void onValueChange() {
        onValueChange.setValue(null);
    }

    private final MutableLiveData<Boolean> compareButtonEnabled = new MutableLiveData<>(false);

    public LiveData<Boolean> getCompareButtonEnabledLiveData() {
        return compareButtonEnabled;
    }

    public boolean getCompareButtonEnabled() {
        return Boolean.TRUE.equals(compareButtonEnabled.getValue());
    }

    public void setCompareButtonEnabled(boolean enabled) {
        if (Boolean.TRUE.equals(compareButtonEnabled.getValue()) != enabled)
            compareButtonEnabled.setValue(enabled);
    }

    public static SelectModelViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @androidx.annotation.NonNull
            @Override
            public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
                return (T) new SelectModelViewModel();
            }
        }).get(SelectModelViewModel.class);
    }
}