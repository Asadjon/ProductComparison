package com.example.productcomparison.fragments.main;

import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.productcomparison.R;
import com.example.productcomparison.models.Product;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

public class MainViewModel extends ViewModel {

    private final Resources resources;
    @Getter
    private final List<Product> productList = new ArrayList<>();
    private final MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Product>> getProductListLD() {
        return productListLiveData;
    }

    private MainViewModel(Resources resources) {
        this.resources = resources;
        this.productListLiveData.observeForever(products -> {
            productList.clear();
            productList.addAll(products);
        });
        loadData();
    }

    private void loadData() {
        List<Product> productList = new ArrayList<>();

        TypedArray drawableIdes = resources.obtainTypedArray(R.array.artel_product_catalog);
        String[] nameIdes = resources.getStringArray(R.array.artel_product_catalog_name);

        for (int i = 0; i < nameIdes.length;  i++)
            productList.add(new Product(nameIdes[i], drawableIdes.getDrawable(i)));

        drawableIdes.recycle();
        productListLiveData.setValue(productList);
    }


    public static MainViewModel getInstance(ViewModelStoreOwner owner, Resources resources) {
        return new ViewModelProvider(owner, new MainFragmentFactory(resources))
                .get(MainViewModel.class);
    }

    @AllArgsConstructor
    static class MainFragmentFactory implements ViewModelProvider.Factory {
        private final Resources resources;

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel(resources);
        }
    }
}