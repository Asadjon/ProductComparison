package com.cyberpantera.productcomparison.models;

import com.cyberpantera.productcomparison.App;
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

    public ParameterRow[] getParameters() {
        String[] paramNames = getDataParamNames();
        ParameterRow[] parameters = new ParameterRow[paramNames.length];

        for (int i = 0; i < paramNames.length; i++)
            parameters[i] = new ParameterRow(paramNames[i], model_1.getParam(i), model_2.getParam(i));

        return parameters;
    }

    private String[] getDataParamNames() {
        return App.getInstance().getResources().getStringArray(product.getParamsResId());
    }
}
