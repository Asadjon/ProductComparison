package com.cyberpantera.productcomparison.models;

import androidx.annotation.Size;
import androidx.databinding.ObservableArrayList;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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

        public Param(@Size(min = 1, max = 2, value = 2) String... values) {
            String[] aboutsName = App.getInstance().getResources().getStringArray(R.array.about_names);

            for (int i = 0; i < 2; i++) {
                Data.About about = Data.About.about(values[i]);
                values[i] = about != null ? aboutsName[about.getOrder()] : values[i];
            }

            this.values.addAll(Arrays.stream(values).collect(Collectors.toList()));
        }
    }
}
