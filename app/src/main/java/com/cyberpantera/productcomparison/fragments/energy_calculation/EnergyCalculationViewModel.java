package com.cyberpantera.productcomparison.fragments.energy_calculation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.fragments.compare.TableViewModel;
import com.cyberpantera.productcomparison.models.ParameterRow;

import java.util.Objects;

public class EnergyCalculationViewModel extends TableViewModel {

    private EnergyCalculationViewModel() {
        super();
        getComparablesLiveData().observeForever(comparables -> {
        EnergyCalculatorService.Values<EnergyCalculatorService.Values<Float>> modelsEnergyConsumption = EnergyCalculatorService.getModelsEnergyConsumption(
                comparables.getModel_1().getEnergyConsumption().getActual(),
                comparables.getModel_2().getEnergyConsumption().getActual(),
                comparables.getProduct().getDailyWorkingHours());

        Objects.requireNonNull(getAdapterLiveData().getValue())
                .setParameters(EnergyCalculatorService.getParameters(modelsEnergyConsumption).toArray(new ParameterRow[0]));
    });
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