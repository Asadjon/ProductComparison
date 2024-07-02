package com.cyberpantera.productcomparison.models;

import android.content.res.Resources;

import com.cyberpantera.productcomparison.models.data.Data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Comparables<T extends Data> {
    private final Product<T> product;
    private T model_1;
    private T model_2;

    public ParameterRow[] getParameters(Resources resources) {
        String[] paramNames = getDataParamNames(resources);
        ParameterRow[] parameters = new ParameterRow[paramNames.length];

        for (int i = 0; i < paramNames.length; i++)
            parameters[i] = new ParameterRow(paramNames[i], model_1.getParam(resources, i), model_2.getParam(resources, i));

        return parameters;
    }

    private String[] getDataParamNames(Resources resources) {
        return resources.getStringArray(product.getParamsResId());
    }
}
