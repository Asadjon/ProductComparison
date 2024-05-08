package com.example.productcomparison.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productcomparison.databinding.ProductItemBinding;
import com.example.productcomparison.models.Product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SelectProductAdapter extends RecyclerView.Adapter<SelectProductAdapter.MyViewHolder> {

    private List<Product> products;
    private ItemOnClickListener onClickListener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ProductItemBinding binding;

        public MyViewHolder(@NonNull ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.setProduct(product);
            binding.getRoot().setOnClickListener(view -> {
                if (onClickListener != null)
                    onClickListener.onClickItem(products.indexOf(product));
            });
        }
    }

    public interface ItemOnClickListener {
        void onClickItem(int index);
    }
}
