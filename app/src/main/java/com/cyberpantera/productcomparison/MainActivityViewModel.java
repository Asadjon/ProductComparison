package com.cyberpantera.productcomparison;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.json.ProductRepository;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;

import java.util.List;

public final class MainActivityViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Product<Data>>> productListLiveData = new MutableLiveData<>();

    public List<Product<Data>> getProductList() {
        return productListLiveData.getValue();
    }

    public LiveData<List<Product<Data>>> getProductListLD() {
        return productListLiveData;
    }

    public void setComparableProduct(Product<Data> product) {
        Comparables<Data> comparables = getComparables();
        if (comparables == null || comparables.getProduct() != product) {
            List<Data> dataList = product.getDataList();
            setComparables(new Comparables<>(product), dataList.get(0), dataList.get(1));
        }
    }

    private final MutableLiveData<Comparables<Data>> comparables = new MutableLiveData<>();

    public LiveData<Comparables<Data>> getComparablesLiveData() {
        return comparables;
    }

    public Comparables<Data> getComparables() {
        return comparables.getValue();
    }

    public void setComparables(Data model_1, Data model_2) {
        this.setComparables(getComparables(), model_1, model_2);
    }

    private void setComparables(Comparables<Data> comparables, Data model_1, Data model_2) {
        if (comparables.getModel_1() != model_1 || comparables.getModel_2() != model_2) {
            comparables.setModel_1(model_1);
            comparables.setModel_2(model_2);
            this.comparables.setValue(comparables);
        }
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        getProductListLD().observeForever(products ->
            setComparableProduct(products.get(0)));

        ProductRepository productRepository = new ProductRepository(application.getAssets());
        productListLiveData.setValue(productRepository.getProductList());
    }

    public static MainActivityViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(MainActivityViewModel.class);
    }
}
