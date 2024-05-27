package com.cyberpantera.productcomparison.fragments.energy_calculation;

import androidx.annotation.Size;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.adapters.CompareProductAdapter;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.ParameterRow;

public class EnergyCalculationViewModel extends ViewModel {

    private final MutableLiveData<String[]> comparablesName = new MutableLiveData<>();

    public LiveData<String[]> getComparablesNameLiveData() {
        return comparablesName;
    }

    public void setComparablesName(@Size(min = 2, max = 2, value = 2)  String... comparablesName) {
        this.comparablesName.setValue(comparablesName);
    }

    private final MutableLiveData<CompareProductAdapter> adapter = new MutableLiveData<>(new CompareProductAdapter());

    public LiveData<CompareProductAdapter> getAdapterLiveData() {
        return adapter;
    }

    public void setAdapter(CompareProductAdapter adapter) {
        this.adapter.setValue(adapter);
    }

    public static EnergyCalculationViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @androidx.annotation.NonNull
            @Override
            public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
                return (T) new EnergyCalculationViewModel();
            }
        }).get(EnergyCalculationViewModel.class);
    }
}