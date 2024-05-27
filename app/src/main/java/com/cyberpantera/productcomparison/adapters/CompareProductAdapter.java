package com.cyberpantera.productcomparison.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyberpantera.productcomparison.databinding.CompareParamItemBinding;
import com.cyberpantera.productcomparison.models.ParameterRow;

import java.util.function.Consumer;

import lombok.Setter;

public class CompareProductAdapter extends RecyclerView.Adapter<CompareProductAdapter.MyViewHolder> {

    @Setter
    private Consumer<ParameterRow> onClickListener;
    private ParameterRow[] parameters = new ParameterRow[0];

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MyViewHolder.getInstance(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding(parameters[position], onClickListener);
    }

    @Override
    public int getItemCount() {
        return parameters.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= (parameters.length - 1) ? 1 : super.getItemViewType(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setParameters(ParameterRow[] parameters) {
        this.parameters = parameters;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CompareParamItemBinding binding;
        private final int viewType;

        public static MyViewHolder getInstance(ViewGroup parent, int viewType) {
            return new MyViewHolder(CompareParamItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), viewType);
        }

        private MyViewHolder(@NonNull CompareParamItemBinding binding, int viewType) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewType = viewType;
        }

        public void binding(ParameterRow parameter, Consumer<ParameterRow> onClickListener) {
            this.binding.setParameter(parameter);
            if (viewType == 1)
                this.binding.getRoot().setOnClickListener(v -> {
                    if (onClickListener != null) onClickListener.accept(parameter);
                });
        }
    }
}
