package com.cyberpantera.productcomparison.fragments.energy_calculation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cyberpantera.productcomparison.fragments.compare.TableViewModel;
import com.cyberpantera.productcomparison.models.ParameterRow;

import static com.cyberpantera.productcomparison.fragments.energy_calculation.EnergyCalculatorService.*;

import android.app.Application;

public final class EnergyCalculationViewModel extends TableViewModel {

    public EnergyCalculationViewModel(@NonNull Application application) {
        super(application);
        getComparablesLiveData().observeForever(comparables -> {
            Values<Values<Float>> modelsEnergyConsumption = getModelsEnergyConsumption(
                    comparables.getModel_1().getEnergyConsumption().getActual(),
                    comparables.getModel_2().getEnergyConsumption().getActual(),
                    comparables.getProduct().getDailyWorkingHours());

            getAdapter().setParameters(getParameters(modelsEnergyConsumption, application.getResources()).toArray(new ParameterRow[0]));
        });
    }

    public static EnergyCalculationViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(EnergyCalculationViewModel.class);
    }
}