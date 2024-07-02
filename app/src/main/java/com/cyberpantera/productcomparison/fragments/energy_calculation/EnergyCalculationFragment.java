package com.cyberpantera.productcomparison.fragments.energy_calculation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.databinding.FragmentEnergyCalculationBinding;
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
        mainActivityVM = MainActivityViewModel.getInstance(requireActivity());
        owner = getViewLifecycleOwner();

        FragmentEnergyCalculationBinding binding = FragmentEnergyCalculationBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(owner);
        binding.compare.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityVM.getComparablesLiveData().observe(owner, viewModel::setComparables);

        viewModel.setIsHideCorresponds(true);
    }

    public void show(FragmentManager fragmentManager) {
        showNow(fragmentManager, TAG);
    }
}