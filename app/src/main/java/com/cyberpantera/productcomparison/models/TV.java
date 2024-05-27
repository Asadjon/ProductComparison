package com.cyberpantera.productcomparison.models;

import com.cyberpantera.productcomparison.annotations.ParamsNameResId;
import com.google.gson.annotations.SerializedName;

@ParamsNameResId(ParamsNameResId.Values.TV)
public final class TV extends Data {
    @SerializedName("diagonal_size")
    private Data.Values<Integer> diagonalSize;
    @SerializedName("os_type")
    private Data.Values<String> osType;
    @SerializedName("os_version")
    private Data.Values<String> osVersion;
    @SerializedName("screen_resolution")
    private Data.Values<String> screenResolution;
    @SerializedName("voice_control")
    private Data.Values<String> voiceControl;
    @SerializedName("remote_control_voice_control")
    private Data.Values<String> remoteControl;
    @SerializedName("ram")
    private Data.Values<String> ram;
    @SerializedName("flash_memory")
    private Data.Values<String> flashMemory;

    @Override
    public Data.Values<?>[] getParams() {
        return new Data.Values<?>[] { diagonalSize, osType, osVersion, screenResolution, voiceControl, remoteControl, ram, flashMemory, energyConsumption };
    }
}
