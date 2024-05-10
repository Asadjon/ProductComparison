package com.cyberpantera.productcomparison.fragments.select_model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberpantera.productcomparison.MainActivity;
import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.databinding.FragmentSelectModelBinding;
import com.cyberpantera.productcomparison.models.Data;

public class SelectModelFragment extends Fragment {
    public static final String PRODUCT_INDEX = "product_index";
    private FragmentSelectModelBinding binding;
    private SelectModelViewModel viewModel;
    private MainActivityViewModel mainActivityVM;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectModelBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = SelectModelViewModel.getInstance(this);
        binding.setViewModel(viewModel);
        mainActivityVM = MainActivityViewModel.getInstance((MainActivity) requireActivity());
        int index = requireActivity().getIntent().getIntExtra(PRODUCT_INDEX, 0);
        viewModel.setProduct(mainActivityVM.getProductList().get(index));

        viewModel.getProductLiveData().observe(getViewLifecycleOwner(), product -> {
            String[] modelsNameList = product.getDataList().stream().map(Data::getName).toArray(String[]::new);

            binding.modelPicker1.setMinValue(0);
            binding.modelPicker1.setMaxValue(modelsNameList.length - 1);
            binding.modelPicker1.setDisplayedValues(modelsNameList);
            binding.modelPicker2.setValue(0);

            binding.modelPicker2.setMinValue(0);
            binding.modelPicker2.setMaxValue(modelsNameList.length - 1);
            binding.modelPicker2.setDisplayedValues(modelsNameList);
            binding.modelPicker2.setValue(1);

            viewModel.setCompareButtonEnabled(binding.modelPicker1.getValue() != binding.modelPicker2.getValue());
        });

        viewModel.getOnValueChangeLiveData().observe(getViewLifecycleOwner(), unused ->
                viewModel.setCompareButtonEnabled(binding.modelPicker1.getValue() != binding.modelPicker2.getValue()));

        viewModel.getOnClickCompareLiveData().observe(getViewLifecycleOwner(), unused ->
                System.out.println("Compare"));
    }
}