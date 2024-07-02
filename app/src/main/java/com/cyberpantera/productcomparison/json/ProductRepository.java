package com.cyberpantera.productcomparison.json;

import android.graphics.drawable.Drawable;

import com.cyberpantera.productcomparison.generated.DataAnnotationsGenerated;
import com.cyberpantera.productcomparison.models.data.Data;
import com.cyberpantera.productcomparison.models.Product;
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
    private final android.content.res.AssetManager assetManager;

    public ProductRepository(android.content.res.AssetManager assetManager) {
        this.assetManager = assetManager;
        loadData();
    }

    private void loadData() {
        productList = new ArrayList<>();

        DataAnnotationsGenerated.dataAnnotationList.forEach(dataAnnotation -> productList.add(Product.builder()
                .nameIndex(dataAnnotation.getNameIndex())
                .dailyWorkingHours(dataAnnotation.getDailyWorkingHours())
                .src(Drawable.createFromStream(openFile(dataAnnotation.getDrawablePath()), null))
                .dataList(new Gson().fromJson(getJson(dataAnnotation.getJsonPath()), TypeToken.getParameterized(ArrayList.class, dataAnnotation.getDataType()).getType()))
                .paramsResId(dataAnnotation.getParamResId())
                .build()));
    }

    private String getJson(String path) {
        // Open file
        InputStream inputStream = openFile(path);
        if (inputStream == null) return "[]";
        String data = "[]";

        // Read the JSON inputStream as a string
        try {
            byte[] buffer = new byte[inputStream.available()];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1)
                data = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.fillInStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }

        return data;
    }

    private InputStream openFile(String path) {
        try {
            return assetManager.open(path);
        } catch (IOException e) {
            e.fillInStackTrace();
            return null;
        }
    }
}
