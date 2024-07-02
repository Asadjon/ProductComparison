package com.cyberpantera.productcomparison.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.databinding.ProductItemBinding;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;

import java.util.List;
import java.util.function.Consumer;

public class SelectProductAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private final Context context;
    private final Consumer<Integer> setterSelectedProductIndex;
    private List<Product<Data>> productList;

    public SelectProductAdapter(Context context, List<Product<Data>> productList, Consumer<Integer> setterSelectedProductIndex) {
        this.context = context;
        this.productList = productList;
        this.setterSelectedProductIndex = setterSelectedProductIndex;
    }

    public void setProductList(List<Product<Data>> productList) {
        this.productList = productList;
        notifyDataSetChanged();
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
        binding.setProductCatalogNames(context.getResources().getStringArray(R.array.product_catalog_name));
        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (setterSelectedProductIndex != null)
            setterSelectedProductIndex.accept(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
