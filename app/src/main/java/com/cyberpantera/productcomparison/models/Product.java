package com.cyberpantera.productcomparison.models;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.annotations.ParamsNameResId;

import java.util.List;
import java.util.Objects;
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
    private final Drawable src;
    private final List<T> dataList;

    public List<String> getModelNameList() {
        return dataList.stream().map(Data::getName).collect(Collectors.toList());
    }

    public String[] getDataParamNames() {
        Class<?> dataClass = dataList.get(0).getClass();

        if (dataClass.isAnnotationPresent(ParamsNameResId.class)) {
            Resources resources = App.getInstance().getResources();
            return resources.getStringArray(Objects.requireNonNull(dataClass.getAnnotation(ParamsNameResId.class)).value().getOrder());
        }

        return new String[0];
    }
}
