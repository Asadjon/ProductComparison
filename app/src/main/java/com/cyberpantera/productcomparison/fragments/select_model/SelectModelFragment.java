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

import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.adapters.SelectProductAdapter;
import com.cyberpantera.productcomparison.databinding.FragmentSelectModelBinding;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.Product;
import com.cyberpantera.productcomparison.models.data.Data;

import java.util.List;

public class SelectModelFragment extends Fragment {
    private SelectModelViewModel viewModel;
    private MainActivityViewModel mainActivityVM;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainActivityVM = MainActivityViewModel.getInstance(requireActivity());
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

        viewModel.getSelectedProductIndexLiveData().observe(owner, productIndex -> {
            Product<Data> product = mainActivityVM.getProductList().get(viewModel.getSelectedProductIndex());
            viewModel.setDisplayedValuesOfNumPicker(product);
            mainActivityVM.setComparableProduct(product);
        });

        viewModel.onChangeValuesOfNumPicker().observe(owner, values -> {
            List<Data> dataList = mainActivityVM.getComparables().getProduct().getDataList();
            mainActivityVM.setComparables(dataList.get(values[0]), dataList.get(values[1]));
        });

        viewModel.setOnClickCompare(() -> NavHostFragment.findNavController(SelectModelFragment.this)
                .navigate(R.id.action_SelectModelFragment_to_CompareFragment));

        List<Product<Data>> productList = mainActivityVM.getProductList();
        Comparables<Data> comparables = mainActivityVM.getComparables();
        Product<Data> product = comparables.getProduct();
        List<Data> dataList = product.getDataList();

        viewModel.setSelectedProductIndex(productList.indexOf(product));
        viewModel.setDisplayedValuesOfNumPicker(product);
        viewModel.setValuesOfNumPickers(dataList.indexOf(comparables.getModel_1()), dataList.indexOf(comparables.getModel_2()));
        viewModel.setAdapter(new SelectProductAdapter(requireContext(), productList, viewModel::setSelectedProductIndex));
    }
}