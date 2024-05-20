package com.cyberpantera.productcomparison.fragments.compare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.ParameterRow;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class CompareViewModel extends ViewModel {

    private final List<MutableLiveData<ParameterRow>> parameters;

    public LiveData<ParameterRow> getParameter(int index) {
        return parameters.get(index);
    }

    public int getParametersCount() {
        return parameters.size();
    }

    @Getter
    private final String[] modelsName;

    @Getter
    private final String[] parametersName;

    private CompareViewModel(Comparables comparables, String[] parametersName) {
        this.parametersName = parametersName;
        this.modelsName = new String[] { comparables.getModel_1().getName(), comparables.getModel_2().getName() };
        this.parameters = new ArrayList<>();
        comparables.getParameters().forEach(param -> this.parameters.add(new MutableLiveData<>(param)));
    }

    public static CompareViewModel getInstance(ViewModelStoreOwner owner, Comparables comparables, String[] parametersName) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @androidx.annotation.NonNull
            @Override
            public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
                return (T) new CompareViewModel(comparables, parametersName);
            }
        }).get(CompareViewModel.class);
    }
}