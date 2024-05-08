package com.example.productcomparison.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.productcomparison.adapters.SelectProductAdapter;
import com.example.productcomparison.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = MainViewModel.getInstance(this, getResources());

        viewModel.getProductListLD().observe(getViewLifecycleOwner(), products ->
                binding.setAdapter(new SelectProductAdapter(products, this::onClickItem)));
    }

    private void onClickItem(int index) {
        System.out.println(index);
    }
}