package com.cyberpantera.productcomparison.models;

import java.util.ArrayList;
import java.util.List;

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

    public List<ParameterRow> getParameters() {
        List<ParameterRow> parameters = new ArrayList<>();

        for (int i = 0; i < model_1.getParamsSize(); i++)
            parameters.add(new ParameterRow("", model_1.getParam(i), model_2.getParam(i)));

        return parameters;
    }
}
