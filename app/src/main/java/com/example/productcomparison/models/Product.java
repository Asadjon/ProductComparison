package com.example.productcomparison.models;

import android.graphics.drawable.Drawable;

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
}
