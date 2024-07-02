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

@SuppressLint("NotifyDataSetChanged")
public class CompareProductAdapter extends RecyclerView.Adapter<CompareProductAdapter.MyViewHolder> {

    @Setter
    private Consumer<ParameterRow> onClickListener;
    private ParameterRow[] parameters = new ParameterRow[0];
    private boolean isHideCorresponds = false;

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

    public void setParameters(ParameterRow[] parameters) {
        this.parameters = parameters;
        notifyDataSetChanged();
    }

    public void setHideCorresponds(boolean isHideCorresponds) {
        this.isHideCorresponds = isHideCorresponds;
        notifyDataSetChanged();
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        private final CompareParamItemBinding binding;

        private MyViewHolder(@NonNull CompareParamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void binding(ParameterRow parameter) {
            this.binding.setParameter(parameter);
            this.binding.setIsHideCorresponds(isHideCorresponds);
            this.binding.setContext(binding.getContext());
            this.binding.getRoot().setOnClickListener(v -> {
                if (onClickListener != null) onClickListener.accept(parameter);
            });
        }
    }
}
