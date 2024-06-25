package com.cyberpantera.productcomparison.models;

import android.graphics.drawable.Drawable;

import androidx.annotation.IdRes;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.models.data.Data;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Product<T extends Data> {

    private final int nameIndex;

    private final Drawable src;

    private final int dailyWorkingHours;

    private final List<T> dataList;

    @IdRes
    private final int paramsResId;

    public String getTranslatableName() {
        return App.getInstance().getResources().getStringArray(R.array.product_catalog_name)[nameIndex];
    }

    public List<String> getModelNameList() {
        return dataList.stream().map(Data::getName).collect(Collectors.toList());
    }
}
