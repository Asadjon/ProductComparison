package com.cyberpantera.productcomparison.models;

import com.cyberpantera.productcomparison.App;
import com.cyberpantera.productcomparison.R;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ParameterRow {
    @Getter
    private final String param;
    private final Param[] products = new Param[2];

    public ParameterRow(String param, Param product1, Param product2) {
        this.param = param;
        products[0] = product1;
        products[1] = product2;
    }

    public Param getProduct(int index) {
        return  products[index];
    }

    @ToString
    @EqualsAndHashCode
    public static final class Param {
        private final String[] values = new String[2];

        public Param(String val1, String val2) {
            values[0] = val1;
            values[1] = val2;

            String[] aboutsName = App.getInstance().getResources().getStringArray(R.array.about_names);

            for (int i = 0; i < 2; i++) {
                Data.About about = Data.About.about(values[i]);
                values[i] = about != null ? aboutsName[about.getOrder()] : values[i];
            }
        }

        public String getValue(int index) {
            return  values[index];
        }

        public Data.About getAbout(int index) {
            return Data.About.about(getValue(index));
        }

        public boolean isHaveAbout(int index) {
            return getAbout(index) != null;
        }
    }
}
