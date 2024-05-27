package com.cyberpantera.productcomparison.json;

import android.graphics.drawable.Drawable;

import com.cyberpantera.productcomparison.models.Data;
import com.cyberpantera.productcomparison.models.Product;
import com.cyberpantera.productcomparison.models.TV;
import com.cyberpantera.productcomparison.statics.AssetManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public final class ProductRepository implements JsonDeserializer<Product<Data>> {

    private List<Product<Data>> productList;

    public ProductRepository() {
        loadData();
    }

    private void loadData() {
        String productsJsonData = ProductRepository.getJson("products.json");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Product.class, this)
                .create();
        productList = gson.fromJson(productsJsonData, new TypeToken<ArrayList<Product<Data>>>() {
        }.getType());
    }

    @Override
    public Product<Data> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject productJson = json.getAsJsonObject();

        String name = productJson.get("name").getAsString();
        int stringIndex = productJson.has("string_index") ? productJson.get("string_index").getAsInt() : -1;
        String drawablePath = productJson.get("drawable").getAsString();
        int dailyWorkingHours = productJson.has("daily_working_hours") ? productJson.get("daily_working_hours").getAsInt() : 0;
        String dataPath = productJson.has("data_path") ? productJson.get("data_path").getAsString() : null;

        return new Product<>(
                name,
                stringIndex,
                Drawable.createFromStream(AssetManager.getInstance().openFile(drawablePath), null),
                dailyWorkingHours,
                new Gson().fromJson(dataPath != null ? getJson(dataPath) : "[]", getDataType(name))
        );
    }

    private Type getDataType(String name) {
        if (name.toLowerCase().trim().equals("tv")) return TypeToken.getParameterized(ArrayList.class, TV.class).getType();
        return TypeToken.getParameterized(ArrayList.class, Data.class).getType();
    }

    public static String getJson(String path) {
        // Open file
        InputStream data = AssetManager.getInstance().openFile(path);
        if (data == null) return "";

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
