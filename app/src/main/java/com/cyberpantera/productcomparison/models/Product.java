package com.cyberpantera.productcomparison.models;

import android.graphics.drawable.Drawable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Product {

    private final String name;
    private final Drawable src;
    private final List<Data> dataList;
}
