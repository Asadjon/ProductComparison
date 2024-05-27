package com.cyberpantera.productcomparison.fragments.energy_calculation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberpantera.productcomparison.MainActivity;
import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.databinding.FragmentEnergyCalculationBinding;
import com.cyberpantera.productcomparison.models.ParameterRow;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EnergyCalculationFragment extends BottomSheetDialogFragment {

    public static final String TAG = "EnergyCalculationFragment";

    private MainActivityViewModel mainActivityVM;
    private EnergyCalculationViewModel viewModel;
    private LifecycleOwner owner;

    public static EnergyCalculationFragment newInstance() {
        return new EnergyCalculationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = EnergyCalculationViewModel.getInstance(this);
        mainActivityVM = MainActivityViewModel.getInstance((MainActivity) requireActivity());
        owner = getViewLifecycleOwner();

        FragmentEnergyCalculationBinding binding = FragmentEnergyCalculationBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(owner);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityVM.getComparablesLiveData().observe(owner, comparables -> {
            viewModel.setComparablesName(comparables.getModel_1().getName(), comparables.getModel_2().getName());
            EnergyCalculatorService.Values<EnergyCalculatorService.Values<Float>> modelsEnergyConsumption = EnergyCalculatorService.getModelsEnergyConsumption(
                    comparables.getModel_1().getEnergyConsumption().getActual(),
                    comparables.getModel_2().getEnergyConsumption().getActual(),
                    mainActivityVM.getComparableProduct().getDailyWorkingHours());

            viewModel.getAdapterLiveData().getValue().setParameters(EnergyCalculatorService.getParameters(modelsEnergyConsumption).toArray(new ParameterRow[0]));
        });
    }

    public void show(FragmentManager fragmentManager) {
        showNow(fragmentManager, TAG);
    }
}