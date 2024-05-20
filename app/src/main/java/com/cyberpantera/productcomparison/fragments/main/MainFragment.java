package com.cyberpantera.productcomparison.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberpantera.productcomparison.MainActivity;
import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.adapters.SelectProductAdapter;
import com.cyberpantera.productcomparison.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainActivityViewModel mainActivityVM;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityVM = MainActivityViewModel.getInstance((MainActivity) requireActivity());

        mainActivityVM.getProductListLD().observe(getViewLifecycleOwner(), products ->
                binding.setAdapter(new SelectProductAdapter(products, this::onClickItem)));
    }

    private void onClickItem(int index) {
        mainActivityVM.setComparableProductIndex(0);

        NavHostFragment.findNavController(MainFragment.this)
                .navigate(R.id.action_MainFragment_to_SelectModelFragment);
    }
}