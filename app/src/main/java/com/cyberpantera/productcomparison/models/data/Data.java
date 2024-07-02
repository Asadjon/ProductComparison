package com.cyberpantera.productcomparison.models.data;

import android.content.res.Resources;

import androidx.annotation.Nullable;

import com.cyberpantera.productcomparison.models.ParameterRow;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public abstract class Data implements IParam {

    @SerializedName("name") private String name;

    @SerializedName("energy_consumption")
    protected Data.Values<Integer> energyConsumption;

    @Override
    public Data.Values<?>[] getParams() {
        return new Data.Values<?>[] { energyConsumption };
    }

    public final ParameterRow.Param getParam(Resources resources, int index) {
        return getParams()[index].getParam(resources);
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Values<T> {
        @SerializedName("stated_values") private T stated;
        @SerializedName("actual_values") private T actual;

        protected ParameterRow.Param getParam(Resources resources) {
            return new ParameterRow.Param(resources, String.valueOf(stated), String.valueOf(actual));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum About {
        NO_DATA(0),
        NOT_SPECIFIED(1),
        DOES_NOT_WORK(2),
        YES(3),
        NO(4);

        private final int order;

        @Nullable
        public static About about(String orderName) {
            if (orderName == null || orderName.isEmpty() || orderName.equals("no_data")) return NO_DATA;
            else if (orderName.equals("not_specified")) return NOT_SPECIFIED;
            else if (orderName.equals("does_not_work")) return DOES_NOT_WORK;
            else if (orderName.equals("yes")) return YES;
            else if (orderName.equals("no")) return NO;
            else return null;
        }
    }
}
