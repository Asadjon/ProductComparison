package com.cyberpantera.productcomparison.fragments.compare;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberpantera.productcomparison.MainActivity;
import com.cyberpantera.productcomparison.MainActivityViewModel;
import com.cyberpantera.productcomparison.databinding.FragmentCompareBinding;

public class CompareFragment extends Fragment {
    private static final int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private int lastOrientation;

    private MainActivityViewModel mainActivityVM;
    private CompareViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = CompareViewModel.getInstance(this);
        mainActivityVM = MainActivityViewModel.getInstance((MainActivity) requireActivity());

        FragmentCompareBinding binding = FragmentCompareBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = requireActivity();
        activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        lastOrientation = activity.getRequestedOrientation();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        LifecycleOwner owner = getViewLifecycleOwner();

        mainActivityVM.getComparablesLiveData().observe(owner, viewModel::setComparables);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FragmentActivity activity = requireActivity();
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        activity.setRequestedOrientation(lastOrientation);
    }
}