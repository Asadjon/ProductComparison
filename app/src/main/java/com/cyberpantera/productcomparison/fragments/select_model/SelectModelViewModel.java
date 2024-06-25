package com.cyberpantera.productcomparison.fragments.select_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.adapters.SelectProductAdapter;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;

import java.util.List;
import java.util.Objects;

public class SelectModelViewModel extends ViewModel {

    private final MutableLiveData<List<Product<Data>>> productList = new MutableLiveData<>();

    public LiveData<List<Product<Data>>> getProductListLiveData() {
        return productList;
    }

    public List<Product<Data>> getProductList() {
        return productList.getValue();
    }

    public void setProductList(List<Product<Data>> productList) {
        this.productList.setValue(productList);
    }

    private final MutableLiveData<Product<Data>> selectedProduct = new MutableLiveData<>();

    public LiveData<Product<Data>> getSelectedProductLiveData() {
        return selectedProduct;
    }


    public Product<Data> getSelectedProduct() {
        return getProductList().get(0);
    }

    public void setSelectedProduct(Product<Data> selectedProduct) {
        this.selectedProduct.setValue(selectedProduct);
    }

    // ViewPager -----------------------------------------------------------------------------------------------

    private final MutableLiveData<SelectProductAdapter> selectProductAdapter = new MutableLiveData<>();

    public LiveData<SelectProductAdapter> getSelectProductAdapter() {
        return selectProductAdapter;
    }

    public void setSelectProductAdapter(SelectProductAdapter adapter) {
        selectProductAdapter.setValue(adapter);
    }

    private final MutableLiveData<Integer> selectedProductIndex = new MutableLiveData<>(0);

    public LiveData<Integer> getSelectedProductIndex() {
        return selectedProductIndex;
    }

    //Compare FloatActionButton -----------------------------------------------------------------------------------------------

    private final MutableLiveData<Boolean> onClickCompare = new MutableLiveData<>();

    public LiveData<Boolean> getOnClickCompareLiveData() {
        return onClickCompare;
    }

    public void onClickCompare() {
        onClickCompare.setValue(true);
    }

    public void onClickCompareComplete() {
        onClickCompare.setValue(false);
    }

    // -----------------------------------------------------------------------------------------------
    private final MutableLiveData<Integer> maxValueOfNumPicker = new MutableLiveData<>();

    public LiveData<Integer> getMaxValueOfNumPicker() {
        return maxValueOfNumPicker;
    }

    private final MutableLiveData<Integer>[] valuesOfNumPicker = new MutableLiveData[2];

    public LiveData<Integer> getValueOfNumPicker(int index) {
        return valuesOfNumPicker[index];
    }

    public void setValuesOfNumPickers(int val1, int val2) {
        valuesOfNumPicker[0].setValue(val1);
        valuesOfNumPicker[1].setValue(val2);
    }

    private final MutableLiveData<List<String>> displayedValuesOfNumPicker = new MutableLiveData<>();

    public LiveData<List<String>> getDisplayedValuesOfNumPicker() {
        return displayedValuesOfNumPicker;
    }

    private final MutableLiveData<Integer>[] onValueChangeOfNumPickerList = new MutableLiveData[2];

    public LiveData<Integer> getOnValueChangeOfNumPicker(int index) {
        return onValueChangeOfNumPickerList[index];
    }

    public void setValueOfNumPicker(int index, int value) {
        onValueChangeOfNumPickerList[index].setValue(value);
    }

    public BindingAdapters.OnValueChangeListener getOnValueChangeListenerOfNumPicker(int index) {
        return (value) -> setValueOfNumPicker(index, value);
    }

    private final MediatorLiveData<Boolean> onValuesChangeOfNumPickers = new MediatorLiveData<>(true);

    public LiveData<Boolean> getCompareButtonEnabledLiveData() {
        return onValuesChangeOfNumPickers;
    }

    public SelectModelViewModel() {
        productList.observeForever(products -> setSelectedProduct(products.get(selectedProductIndex.getValue())));

        selectedProduct.observeForever(dataProduct -> {
            displayedValuesOfNumPicker.setValue(getSelectedProduct().getModelNameList());
            selectedProductIndex.setValue(getProductList().indexOf(dataProduct));
        });
        displayedValuesOfNumPicker.observeForever(strings -> maxValueOfNumPicker.setValue(strings.size() - 1));

        valuesOfNumPicker[0] = new MutableLiveData<>();
        valuesOfNumPicker[1] = new MutableLiveData<>();

        onValueChangeOfNumPickerList[0] = new MutableLiveData<>();
        onValueChangeOfNumPickerList[1] = new MutableLiveData<>();

        Observer<Integer> observer = valuesOfNumPicker ->
                onValuesChangeOfNumPickers.setValue(!Objects.equals(onValueChangeOfNumPickerList[0].getValue(), onValueChangeOfNumPickerList[1].getValue()));

        onValuesChangeOfNumPickers.addSource(onValueChangeOfNumPickerList[0], observer);
        onValuesChangeOfNumPickers.addSource(onValueChangeOfNumPickerList[1], observer);
    }

    public static SelectModelViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(SelectModelViewModel.class);
    }
}