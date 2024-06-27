package com.cyberpantera.productcomparison.models;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Size;
import androidx.databinding.ObservableArrayList;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;
import com.cyberpantera.productcomparison.models.data.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ParameterRow {
    private final String name;
    private final ObservableArrayList<Param> products = new ObservableArrayList<>();

    public ParameterRow(String name, @Size(min = 1, max = 2, value = 2) Param... products) {
        this.name = name;
        this.products.addAll(Arrays.stream(products).collect(Collectors.toList()));
    }

    @ToString
    @EqualsAndHashCode
    @Getter
    public static final class Param {
        private final ObservableArrayList<String> values = new ObservableArrayList<>();
        private final Corresponds correspond;

        public Param(@Size(min = 1, max = 2, value = 2) String... values) {
            String[] aboutsName = App.getInstance().getResources().getStringArray(R.array.about_names);

            Object[] objects = new Object[2];

            for (int i = 0; i < 2; i++) {
                Data.About about = Data.About.about(values[i]);
                values[i] = about != null ? aboutsName[about.getOrder()] : values[i];
                objects[i] = about != null ? about : values[i];
            }

            this.correspond = Corresponds.getCorresponds(objects[0], objects[1]);
            this.values.addAll(Arrays.stream(values).collect(Collectors.toList()));
        }
    }

    @RequiredArgsConstructor
    @Getter
    public enum Corresponds {
        Yes(R.color.correspond_yes), No(R.color.correspond_no);

        @ColorRes
        private final int ordinal;

        public static Corresponds getCorresponds(Object value1, Object value2) {
            if ((value1 instanceof Data.About && value1.equals(Data.About.YES) && (value2.equals(value1) || !(value2 instanceof Data.About))) ||
                    (!(value1 instanceof Data.About) && (value1.equals(value2) || value2.equals(Data.About.YES))))
                return Yes;

            return No;
        }

        @ColorInt
        public static int getColor(Corresponds correspond) {
            return App.getInstance().getResources().getColor(correspond.ordinal);
        }
    }
}
