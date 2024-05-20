package com.cyberpantera.productcomparison.annotations;

import com.cyberpantera.productcomparison.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Retention(RetentionPolicy.RUNTIME) // The annotation will be available at runtime
@Target(ElementType.TYPE) // This annotation can be applied to methods
public @interface ParamsNameResId {
    Values value();

    @Getter
    @AllArgsConstructor
    enum Values {
        TV(R.array.tv_data);

        private final int order;
    }
}
