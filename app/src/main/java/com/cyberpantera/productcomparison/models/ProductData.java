package com.cyberpantera.productcomparison.models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class ProductData {

    @SerializedName("name") private String name;
    @SerializedName("string_index") private int stringIndex;
    @SerializedName("drawable") private String drawablePath;
    @SerializedName("data") private Data data;

    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class Data {
        @SerializedName("path") private String path;
        @SerializedName("type") private String type;

        public Type getDataType() {
            try {
                return Class.forName(type);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
