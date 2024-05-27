package com.cyberpantera.productcomparison.fragments.energy_calculation;

import androidx.annotation.Size;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.models.ParameterRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public final class EnergyCalculatorService {

    private static final int daysOfYear = 365;
    private static final int monthsOfYear = 12;

    private static final List<Values<Integer>> tariffs = Arrays.asList(
            new Values<>(0, 450),
            new Values<>(200, 900),
            new Values<>(1000, 1350),
            new Values<>(5000, 1575),
            new Values<>(10000, 1800)
    );

    @Getter
    @Setter
    private static int period = 12;

    private static float getMonthly(int value, int dailyWorkingHours) {
        return (value / 1000f) * dailyWorkingHours * ((float) daysOfYear / monthsOfYear);
    }

    private static float getYearly(int value, int dailyWorkingHours) {
        return (value / 1000f) * dailyWorkingHours * daysOfYear;
    }

    private static Values<Float> getEnergyConsumption(int value, int dailyWorkingHours) {
        return new Values<>(getMonthly(value, dailyWorkingHours), getYearly(value, dailyWorkingHours));
    }

    public static Values<Values<Float>> getModelsEnergyConsumption(int value1, int value2, int dailyWorkingHours) {
        return new Values<>(getEnergyConsumption(value1, dailyWorkingHours), getEnergyConsumption(value2, dailyWorkingHours));
    }

    public static List<ParameterRow> getParameters(Values<Values<Float>> modelsEnergyConsumption) {
        List<ParameterRow> parameters = new ArrayList<>();
        parameters.add(new ParameterRow(App.getInstance().getString(R.string.unit_energy_kw), modelsEnergyConsumption.value1.getParam(), modelsEnergyConsumption.value2.getParam()));

        float max = Math.max(modelsEnergyConsumption.value1.value2, modelsEnergyConsumption.value2.value2);

        for (Values<Integer> tariff : tariffs) {
                parameters.add(getParameter(tariff, modelsEnergyConsumption.value1, modelsEnergyConsumption.value2));
            if (max < tariff.value1) break;
        }

        return parameters;
    }

    @SafeVarargs
    private static ParameterRow getParameter(Values<Integer> tariff, @Size(min = 2, max = 2, value = 2) Values<Float>... modelsValue) {
        Values<Integer>[] calculationPrices = calculatePrice(modelsValue, tariff.value2);
        return new ParameterRow(tariff.value2 + " " + App.getInstance().getString(R.string.currency), calculationPrices[0].getParam(), calculationPrices[1].getParam());
    }

    @Size(min = 2, max = 2, value = 2)
    private static Values<Integer>[] calculatePrice(@Size(min = 2, max = 2, value = 2) Values<Float>[] modelsValue, int price) {
        return new Values[] {
                new Values<>((int) (modelsValue[0].value1 * price), (int) (modelsValue[0].value2 * price)),
                new Values<>((int) (modelsValue[1].value1 * price), (int) (modelsValue[1].value2 * price))
        };
    }

    @AllArgsConstructor
    @Getter
    @ToString
    public static final class Values<T> {
        private final T value1;
        private final T value2;

        public ParameterRow.Param getParam() {
            return new ParameterRow.Param(String.valueOf(value1), String.valueOf(value2));
        }
    }
}
