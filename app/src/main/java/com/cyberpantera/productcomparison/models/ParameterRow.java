package com.cyberpantera.productcomparison.models;

import androidx.annotation.Size;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ParameterRow {
    private final String name;
    private final Param[] products;

    public ParameterRow(String name, @Size(min = 1, max = 2, value = 2) Param... products) {
        this.name = name;
        this.products = products;
    }

    @ToString
    @EqualsAndHashCode
    @Getter
    public static final class Param {
        private final String[] values;

        public Param(@Size(min = 1, max = 2, value = 2) String... values) {
            this.values = values;

            String[] aboutsName = App.getInstance().getResources().getStringArray(R.array.about_names);

            for (int i = 0; i < 2; i++) {
                Data.About about = Data.About.about(this.values[i]);
                this.values[i] = about != null ? aboutsName[about.getOrder()] : this.values[i];
            }
        }
    }
}
