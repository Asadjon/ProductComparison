package com.cyberpantera.productcomparison.models.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cyberpantera.productcomparison.models.ParameterRow;
import com.google.gson.annotations.SerializedName;

import java.util.Iterator;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public abstract class Data implements Iterable<ParameterRow.Param>, IParam {

    @SerializedName("name") private String name;

    @SerializedName("energy_consumption")
    protected Data.Values<Integer> energyConsumption;

    @Override
    public Data.Values<?>[] getParams() {
        return new Data.Values<?>[] { energyConsumption };
    }

    @Override
    public final void forEach(@NonNull Consumer<? super ParameterRow.Param> action) {
        for (ParameterRow.Param param : this)
            action.accept(param);
    }

    @NonNull
    @Override
    public final  Iterator<ParameterRow.Param> iterator() {
        return new DataIterator(getParamsSize());
    }

    protected final class DataIterator implements Iterator<ParameterRow.Param> {
        private int currentIndex = -1;
        private final int size;

        DataIterator(int size) {
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return ++currentIndex > size;
        }

        @Override
        public ParameterRow.Param next() {
            return getParam(currentIndex);
        }
    }

    public final ParameterRow.Param getParam(int index) {
        return getParams()[index].getParam();
    }

    public final int getParamsSize() {
        return getParams().length;
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Values<T> {
        @SerializedName("stated_values") private T stated;
        @SerializedName("actual_values") private T actual;

        protected ParameterRow.Param getParam() {
            return new ParameterRow.Param(String.valueOf(stated), String.valueOf(actual));
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
