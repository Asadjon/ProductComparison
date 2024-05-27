package com.cyberpantera.productcomparison;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cyberpantera.productcomparison.json.ProductRepository;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.Data;
import com.cyberpantera.productcomparison.models.Product;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

public class MainActivityViewModel extends ViewModel {

    private final ProductRepository productRepository = new ProductRepository();

    @Getter
    private final List<Product<Data>> productList = new ArrayList<>();
    private final MutableLiveData<List<Product<Data>>> productListLiveData = new MutableLiveData<>();

    public LiveData<List<Product<Data>>> getProductListLD() {
        return productListLiveData;
    }

    private final MutableLiveData<Product<Data>> comparableProduct = new MutableLiveData<>();

    public LiveData<Product<Data>> getComparableProductLiveData() {
        return comparableProduct;
    }

    public Product<Data> getComparableProduct() {
        return comparableProduct.getValue();
    }

    public void setComparableProduct(Product<Data> product) {
        comparableProduct.setValue(product);
    }

    private final MutableLiveData<Comparables<Data>> comparables = new MutableLiveData<>();

    public LiveData<Comparables<Data>> getComparablesLiveData() {
        return comparables;
    }

    public Comparables<Data> getComparables() {
        return comparables.getValue();
    }

    public void setComparables(Data model_1, Data model_2) {
        Comparables<Data> c = getComparables();
        if (c == null) c = new Comparables<>();
        c.setModel_1(model_1);
        c.setModel_2(model_2);
        comparables.setValue(c);
    }

    private MainActivityViewModel() {
        this.productListLiveData.observeForever(products -> {
            productList.clear();
            productList.addAll(products);
        });

        this.comparableProduct.observeForever(product -> {
            List<Data> dataList = product.getDataList();
            Comparables<Data> comparables = getComparables();
            if (comparables == null || !dataList.isEmpty())
                setComparables(dataList.get(0), dataList.get(1));
        });

        productListLiveData.setValue(productRepository.getProductList());
    }

    public static MainActivityViewModel getInstance(MainActivity activity) {
        return new ViewModelProvider(activity, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
                return (T) new MainActivityViewModel();
            }
        }).get(MainActivityViewModel.class);
    }
}
