package com.cyberpantera.productcomparison.models;

import com.google.gson.annotations.SerializedName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ProductData {

    @SerializedName("name") private String name;
    @SerializedName("string_index") private int stringIndex;
    @SerializedName("drawable") private String drawablePath;
    @SerializedName("data") private String dataPath;
}
