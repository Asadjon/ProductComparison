package com.cyberpantera.productcomparison.fragments.select_model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberpantera.productcomparison.MainActivity;
import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.adapters.SelectProductAdapter;
import com.cyberpantera.productcomparison.databinding.FragmentSelectModelBinding;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.Data;

import java.util.List;

public class SelectModelFragment extends Fragment {
    private SelectModelViewModel viewModel;
    private MainActivityViewModel mainActivityVM;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainActivityVM = MainActivityViewModel.getInstance((MainActivity) requireActivity());
        viewModel = SelectModelViewModel.getInstance(this);

        FragmentSelectModelBinding binding = FragmentSelectModelBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LifecycleOwner owner = getViewLifecycleOwner();

        mainActivityVM.getProductListLD().observe(owner, products -> viewModel.setProductList(products));

        mainActivityVM.getComparablesLiveData().observe(owner, comparables -> {
            List<Data> dataList = viewModel.getSelectedProduct().getDataList();
            viewModel.setValuesOfNumPickers(dataList.indexOf(comparables.getModel_1()), dataList.indexOf(comparables.getModel_2()));
        });

        viewModel.setSelectProductAdapter(new SelectProductAdapter(requireContext(), viewModel, owner));

        viewModel.getSelectedProductLiveData().observe(owner, product -> mainActivityVM.setComparableProduct(viewModel.getSelectedProduct()));

        viewModel.getSelectedProductLiveData().observe(owner, product -> {
            Comparables comparables = mainActivityVM.getComparables();
            List<Data> dataList = viewModel.getSelectedProduct().getDataList();
            viewModel.setValueOfNumPicker(0, dataList.indexOf(comparables.getModel_1()));
            viewModel.setValueOfNumPicker(1, dataList.indexOf(comparables.getModel_2()));
        });

        viewModel.getOnValueChangeOfNumPicker(0).observe(owner, value ->
                mainActivityVM.setComparables(getModel(value), mainActivityVM.getComparables().getModel_2()));

        viewModel.getOnValueChangeOfNumPicker(1).observe(owner, value ->
                mainActivityVM.setComparables(mainActivityVM.getComparables().getModel_1(), getModel(value)));

        viewModel.getOnClickCompareLiveData().observe(owner, clicked -> {
            if (clicked) {
                NavHostFragment.findNavController(SelectModelFragment.this)
                        .navigate(R.id.action_SelectModelFragment_to_CompareFragment);
                viewModel.onClickCompareComplete();
            }
        });
    }

    private Data getModel(int index) {
        return viewModel.getSelectedProduct().getDataList().get(index);
    }
}