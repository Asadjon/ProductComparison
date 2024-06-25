package com.cyberpantera.productcomparison.json;


import android.graphics.drawable.Drawable;

import com.cyberpantera.productcomparison.generated.DataAnnotationsGenerated;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;
import com.cyberpantera.productcomparison.statics.AssetManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public final class ProductRepository {

    private List<Product<Data>> productList;

    public ProductRepository() {
        loadData();
    }

    private void loadData() {
        productList = new ArrayList<>();

        DataAnnotationsGenerated.dataAnnotationList.forEach(dataAnnotation -> productList.add(Product.builder()
                .nameIndex(dataAnnotation.getNameIndex())
                .dailyWorkingHours(dataAnnotation.getDailyWorkingHours())
                .src(Drawable.createFromStream(AssetManager.getInstance().openFile(dataAnnotation.getDrawablePath()), null))
                .dataList(new Gson().fromJson(ProductRepository.getJson(dataAnnotation.getJsonPath()), TypeToken.getParameterized(ArrayList.class, dataAnnotation.getDataType()).getType()))
                .paramsResId(dataAnnotation.getParamResId())
                .build()));
    }

    public static String getJson(String path) {
        // Open file
        InputStream data = AssetManager.getInstance().openFile(path);
        if (data == null) return "[]";

        // Read the JSON data as a string
        try {
            byte[] buffer = new byte[data.available()];
            data.read(buffer);
            data.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
