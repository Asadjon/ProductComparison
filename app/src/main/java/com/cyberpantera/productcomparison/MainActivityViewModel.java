package com.cyberpantera.productcomparison;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.Data;
import com.cyberpantera.productcomparison.models.Product;
import com.cyberpantera.productcomparison.models.ProductData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;

public class MainActivityViewModel extends ViewModel {

    private final AssetManager assetManager;
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

    private final MutableLiveData<Comparables> comparables = new MutableLiveData<>();

    public LiveData<Comparables> getComparablesLiveData() {
        return comparables;
    }

    public Comparables getComparables() {
        return comparables.getValue();
    }

    public void setComparables(Data model_1, Data model_2) {
        Comparables c = getComparables();
        if (c == null) c = new Comparables();
        c.setModel_1(model_1);
        c.setModel_2(model_2);
        comparables.setValue(c);
    }

    private MainActivityViewModel(AssetManager assetManager) {
        this.assetManager = assetManager;

        this.productListLiveData.observeForever(products -> {
            productList.clear();
            productList.addAll(products);
        });

        this.comparableProduct.observeForever(product -> {
            List<Data> dataList = product.getDataList();
            Comparables comparables = getComparables();
            if (comparables == null || !dataList.isEmpty())
                setComparables(dataList.get(0), dataList.get(1));
        });

        loadData();
    }

    private void loadData() {
        String productsJsonData = getJson("products.json");
        Gson gson = new Gson();
        List<ProductData> productDataList = gson.fromJson(productsJsonData, new TypeToken<ArrayList<ProductData>>() {
        }.getType());

        productListLiveData.setValue(productDataList.stream().map(this::getProduct).collect(Collectors.toList()));
    }

    private Product<Data> getProduct(ProductData productData) {
        ProductData.Data data = productData.getData();

        List<Data> dataList = data == null ? new ArrayList<>()
                : new Gson().fromJson(getJson(data.getPath()), TypeToken.getParameterized(ArrayList.class, data.getDataType()).getType());
        return new Product<>(
                productData.getName(),
                productData.getStringIndex(),
                Drawable.createFromStream(openFile(productData.getDrawablePath()), null),
                dataList);
    }

    private String getJson(String path) {
        // Open file
        InputStream data = openFile(path);

        // Read the JSON data as a string
        try {
            byte[] buffer = new byte[data.available()];
            data.read(buffer);
            data.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream openFile(String path) {
        try {
            return assetManager.open(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MainActivityViewModel getInstance(MainActivity activity) {
        return new ViewModelProvider(activity, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
                return (T) new MainActivityViewModel(activity.getAssets());
            }
        }).get(MainActivityViewModel.class);
    }
}
