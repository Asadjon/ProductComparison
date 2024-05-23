package com.cyberpantera.productcomparison.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.cyberpantera.productcomparison.databinding.CompareParamItemBinding;
import com.cyberpantera.productcomparison.fragments.compare.CompareViewModel;
import com.cyberpantera.productcomparison.models.ParameterRow;

public class CompareProductAdapter extends RecyclerView.Adapter<CompareProductAdapter.MyViewHolder> {

    private ParameterRow[] parameters = new ParameterRow[0];

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CompareParamItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding(parameters[position]);
    }

    @Override
    public int getItemCount() {
        return parameters.length;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setParameters(ParameterRow[] parameters) {
        this.parameters = parameters;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CompareParamItemBinding binding;

        public MyViewHolder(@NonNull CompareParamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void binding(ParameterRow parameter) {
            this.binding.setParameter(parameter);
        }
    }
}
