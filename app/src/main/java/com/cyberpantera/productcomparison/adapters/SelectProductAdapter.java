package com.cyberpantera.productcomparison.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cyberpantera.productcomparison.databinding.ProductItemBinding;
import com.cyberpantera.productcomparison.fragments.select_model.SelectModelViewModel;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SelectProductAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private final Context context;
    private final SelectModelViewModel viewModel;
    private List<Product<Data>> productList = new ArrayList<>();

    public SelectProductAdapter(Context context, SelectModelViewModel viewModel, LifecycleOwner owner) {
        this.context = context;
        this.viewModel = viewModel;
        this.viewModel.getProductListLiveData().observe(owner, products -> {
            productList = products;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ProductItemBinding binding = ProductItemBinding.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        binding.setProduct(productList.get(position));
        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        viewModel.setSelectedProduct(productList.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
}
