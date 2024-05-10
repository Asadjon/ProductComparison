package com.cyberpantera.productcomparison;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
    private final Resources resources;
    @Getter
    private final List<Product> productList = new ArrayList<>();
    private final MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();

    public LiveData<List<Product>> getProductListLD() {
        return productListLiveData;
    }

    private MainActivityViewModel(AssetManager assetManager, Resources resources) {
        this.assetManager = assetManager;
        this.resources = resources;
        this.productListLiveData.observeForever(products -> {
            productList.clear();
            productList.addAll(products);
        });
        loadData();
    }

    private void loadData() {
        String productsJsonData = getJson(openFile("products.json"));
        Gson gson = new Gson();
        List<ProductData> productDataList = gson.fromJson(productsJsonData, new TypeToken<ArrayList<ProductData>>() {}.getType());

        productListLiveData.setValue(productDataList.stream().map(this::getProduct).collect(Collectors.toList()));
    }

    private Product getProduct(ProductData productData) {
        String[] nameIdes = resources.getStringArray(R.array.artel_product_catalog_name);
        List<Data> dataList = productData.getDataPath() == null ? new ArrayList<>()
                : new Gson().fromJson(getJson(openFile(productData.getDataPath())), new TypeToken<ArrayList<Data>>() {}.getType());
        return new Product(
                nameIdes[productData.getStringIndex()],
                Drawable.createFromStream(openFile(productData.getDrawablePath()), null),
                dataList);
    }

    private String getJson(InputStream data) {
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
                return (T) new MainActivityViewModel(activity.getAssets(), activity.getResources());
            }
        }).get(MainActivityViewModel.class);
    }
}
