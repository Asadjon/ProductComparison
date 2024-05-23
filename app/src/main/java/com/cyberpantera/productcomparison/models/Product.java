package com.cyberpantera.productcomparison.models;

import android.graphics.drawable.Drawable;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Product<T extends Data> {

    private final String name;
    private final int nameIndex;
    private final Drawable src;
    private final List<T> dataList;

    public String getTranslatableName() {
        return App.getInstance().getResources().getStringArray(R.array.product_catalog_name)[nameIndex];
    }

    public List<String> getModelNameList() {
        return dataList.stream().map(Data::getName).collect(Collectors.toList());
    }
}
