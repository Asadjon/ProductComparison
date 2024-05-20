package com.cyberpantera.productcomparison.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.databinding.CompareParamItemBinding;
import com.cyberpantera.productcomparison.fragments.compare.CompareViewModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompareProductAdapter extends RecyclerView.Adapter<CompareProductAdapter.MyViewHolder> {

    private final CompareViewModel viewModel;
    private final LifecycleOwner owner;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CompareParamItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIndex(position);
    }

    @Override
    public int getItemCount() {
        return viewModel.getParametersCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CompareParamItemBinding binding;

        public void setIndex(int index) {
            binding.setIndex(index);
        }

        public MyViewHolder(@NonNull CompareParamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setViewModel(viewModel);
            binding.setLifecycleOwner(owner);
        }
    }
}
