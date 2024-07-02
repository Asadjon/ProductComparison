package com.cyberpantera.productcomparison.fragments.select_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.adapters.SelectProductAdapter;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class SelectModelViewModel extends AndroidViewModel {

    // ViewPager -----------------------------------------------------------------------------------------------

    private final MutableLiveData<SelectProductAdapter> productAdapter = new MutableLiveData<>();

    public LiveData<SelectProductAdapter> getAdapter() {
        return productAdapter;
    }

    public void setAdapter(SelectProductAdapter adapter) {
        productAdapter.setValue(adapter);
    }

    private final MutableLiveData<Integer> selectedProductIndex = new MutableLiveData<>(0);

    public void setSelectedProductIndex(int index) {
        this.selectedProductIndex.setValue(index);
    }

    public LiveData<Integer> getSelectedProductIndexLiveData() {
        return selectedProductIndex;
    }

    public int getSelectedProductIndex() {
        return 0;
    }

    //Compare FloatActionButton -----------------------------------------------------------------------------------------------

    @Setter
    @Getter
    private Runnable onClickCompare;

    private final MediatorLiveData<Boolean> enabledOfCompareButton = new MediatorLiveData<>(true);

    public LiveData<Boolean> getCompareButtonEnabledLiveData() {
        return enabledOfCompareButton;
    }

    // NumberPickers -----------------------------------------------------------------------------------------------

    private static final int numberPickerCount = 2;
    @SuppressWarnings("unchecked")
    private final MutableLiveData<Integer>[] valuesOfNumPicker = new MutableLiveData[numberPickerCount];

    public LiveData<Integer> getValueOfNumPicker(int index) {
        return valuesOfNumPicker[index];
    }

    public void setValuesOfNumPickers(@Size(min = 1, max = numberPickerCount) int... vals) {
        for (int i = 0; i < vals.length; i++)
            setValueOfNumPickers(i, vals[i]);
    }

    public void setValueOfNumPickers(int index, int val) {
            if (!Objects.equals(valuesOfNumPicker[index].getValue(), val))
                valuesOfNumPicker[index].setValue(val);
    }

    private final MediatorLiveData<Integer[]> onChangeValuesOfNumPicker = new MediatorLiveData<>();

    public LiveData<Integer[]> onChangeValuesOfNumPicker() {
        return onChangeValuesOfNumPicker;
    }

    private final MutableLiveData<List<String>> displayedValuesOfNumPicker = new MutableLiveData<>();

    public LiveData<List<String>> getDisplayedValuesOfNumPicker() {
        return displayedValuesOfNumPicker;
    }

    public void setDisplayedValuesOfNumPicker(Product<Data> selectedProduct) {
        displayedValuesOfNumPicker.setValue(selectedProduct.getModelNameList());
    }

    public SelectModelViewModel(@NonNull Application application) {
        super(application);
        enabledOfCompareButton.addSource(onChangeValuesOfNumPicker, values ->
                enabledOfCompareButton.setValue(Arrays.stream(values).collect(Collectors.toSet()).size() == valuesOfNumPicker.length));

        for (int i = 0; i < valuesOfNumPicker.length; i++)
            onChangeValuesOfNumPicker.addSource(valuesOfNumPicker[i] = new MutableLiveData<>(), value ->
                    onChangeValuesOfNumPicker.setValue(Arrays.stream(valuesOfNumPicker).map(LiveData::getValue).toArray(Integer[]::new)));
    }

    public static SelectModelViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(SelectModelViewModel.class);
    }
}