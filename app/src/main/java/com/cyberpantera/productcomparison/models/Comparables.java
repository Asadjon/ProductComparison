package com.cyberpantera.productcomparison.models;

import android.content.res.Resources;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.annotations.ParamsNameResId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Comparables {
    private Data model_1;
    private Data model_2;

    public ParameterRow[] getParameters() {
        String[] paramNames = getDataParamNames();
        ParameterRow[] parameters = new ParameterRow[paramNames.length];

        for (int i = 0; i < paramNames.length; i++)
            parameters[i] = new ParameterRow(paramNames[i], model_1.getParam(i), model_2.getParam(i));

        return parameters;
    }

    private String[] getDataParamNames() {
        Class<?> dataClass = model_1.getClass();

        if (dataClass.isAnnotationPresent(ParamsNameResId.class)) {
            Resources resources = App.getInstance().getResources();
            return resources.getStringArray(Objects.requireNonNull(dataClass.getAnnotation(ParamsNameResId.class)).value().getOrder());
        }

        return new String[0];
    }
}
