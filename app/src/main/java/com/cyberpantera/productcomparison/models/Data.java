package com.cyberpantera.productcomparison.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Data {
    @SerializedName("name") private String name;
    @SerializedName("diagonal_size") private Values<Integer> diagonalSize;
    @SerializedName("os_type") private Values<String> osType;
    @SerializedName("os_version") private Values<String> osVersion;
    @SerializedName("screen_resolution") private Values<String> screenResolution;
    @SerializedName("voice_control") private Values<String> voiceControl;
    @SerializedName("remote_control_voice_control") private Values<String> remoteControl;
    @SerializedName("ram") private Values<String> ram;
    @SerializedName("flash_memory") private Values<String> flashMemory;
    @SerializedName("energy_consumption") private Values<String> energyConsumption;

    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Values<T> {
        @SerializedName("stated_values") private T stated;
        @SerializedName("actual_values") private T actual;
    }

    @Getter
    @AllArgsConstructor
    public enum About {
        NO_DATA("no_data"), NOT_SPECIFIED("not_specified"), DOES_NOT_WORK("does_not_work"), YES("yes"), NO("no");

        private final String order;

        public static About about(String order) {
            if (order == null || order.isEmpty() || order.equals(NO_DATA.order)) return NO_DATA;
            else if (order.equals(NOT_SPECIFIED.order)) return NOT_SPECIFIED;
            else if (order.equals(DOES_NOT_WORK.order)) return DOES_NOT_WORK;
            else if (order.equals(YES.order)) return YES;
            else if (order.equals(NO.order)) return NO;
            else return NO_DATA;
        }
    }
}
