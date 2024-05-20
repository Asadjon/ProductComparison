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
import com.cyberpantera.productcomparison.databinding.FragmentSelectModelBinding;
import com.cyberpantera.productcomparison.models.Comparables;
import com.cyberpantera.productcomparison.models.Data;

import java.util.List;

public class SelectModelFragment extends Fragment {
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
        super.onViewCreated(view, savedInstanceState);

        viewModel = SelectModelViewModel.getInstance(this);
        binding.setViewModel(viewModel);
        mainActivityVM = MainActivityViewModel.getInstance((MainActivity) requireActivity());

        LifecycleOwner owner = getViewLifecycleOwner();

        mainActivityVM.getComparableProductLiveData().observe(owner, viewModel::setProduct);

        mainActivityVM.getComparablesLiveData().observe(owner, comparables -> {
            List<Data> dataList = viewModel.getProduct().getDataList();
            viewModel.setValuesOfNumPickers(dataList.indexOf(comparables.getModel_1()), dataList.indexOf(comparables.getModel_2()));
        });

        Comparables comparables = mainActivityVM.getComparables();
        List<Data> dataList = mainActivityVM.getComparableProduct().getDataList();
        viewModel.getOnValueChangeListenerOfNumPicker(0).onValueChange(dataList.indexOf(comparables.getModel_1()));
        viewModel.getOnValueChangeListenerOfNumPicker(1).onValueChange(dataList.indexOf(comparables.getModel_2()));

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
        return viewModel.getProduct().getDataList().get(index);
    }
}