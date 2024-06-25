package com.cyberpantera.productcomparison.models.data;

import com.cyberpantera.lib.DataAnnotation;
import com.google.gson.annotations.SerializedName;

public final class DataClasses {
    @DataAnnotation(
            dailyWorkingHours = 10,
            drawablePath = "products/tv.png",
            jsonPath = "products/tv.json",
            paramsResId = "R.array.tv_data")
    public static final class TV extends Data {
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

    @DataAnnotation(
            nameIndex = 1,
            dailyWorkingHours = 10,
            drawablePath = "products/air_conditioner.png")
    public static final class AirConditioner extends Data {
    }

    @DataAnnotation(
            nameIndex = 2,
            dailyWorkingHours = 10,
            drawablePath = "products/built_in_oven.png")
    public static final class Oven extends Data {
    }

    @DataAnnotation(
            nameIndex = 3,
            dailyWorkingHours = 10,
            drawablePath = "products/dishwasher.jpg")
    public static final class Dishwasher extends Data {
    }

    @DataAnnotation(
            nameIndex = 4,
            dailyWorkingHours = 10,
            drawablePath = "products/freezer.jpg")
    public static final class Freezer extends Data {
    }

    @DataAnnotation(
            nameIndex = 5,
            dailyWorkingHours = 10,
            drawablePath = "products/kitchen_hood.jpg")
    public static final class KitchenHood extends Data {
    }

    @DataAnnotation(
            nameIndex = 6,
            dailyWorkingHours = 10,
            drawablePath = "products/microwave.png")
    public static final class Microwave extends Data {
    }

    @DataAnnotation(
            nameIndex = 7,
            dailyWorkingHours = 10,
            drawablePath = "products/mini_oven.jpg")
    public static final class MiniOven extends Data {
    }

    @DataAnnotation(
            nameIndex = 8,
            dailyWorkingHours = 10,
            drawablePath = "products/monitor.png")
    public static final class Monitor extends Data {
    }

    @DataAnnotation(
            nameIndex = 9,
            dailyWorkingHours = 10,
            drawablePath = "products/range.jpg")
    public static final class KitchenRange extends Data {
    }

    @DataAnnotation(
            nameIndex = 10,
            dailyWorkingHours = 10,
            drawablePath = "products/refrigerator.jpg")
    public static final class Refrigerator extends Data {
    }

    @DataAnnotation(
            nameIndex = 11,
            dailyWorkingHours = 10,
            drawablePath = "products/small_household_appliance.jpg")
    public static final class Tefal extends Data {
    }

    @DataAnnotation(
            nameIndex = 12,
            dailyWorkingHours = 10,
            drawablePath = "products/vacuum_cleaner.png")
    public static final class VacuumCleaner extends Data {
    }

    @DataAnnotation(
            nameIndex = 13,
            dailyWorkingHours = 10,
            drawablePath = "products/washing_machine.png")
    public static final class WashingMachine extends Data {
    }
}
