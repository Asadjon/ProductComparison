package com.cyberpantera.productcomparison.fragments.select_model;

import android.widget.NumberPicker;

import androidx.databinding.BindingAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("value")
    public static void setNumberPickerValue(NumberPicker view, int value) {
        if (view.getValue() != value)
            view.setValue(value);
    }

    @BindingAdapter("valueChanged")
    public static void setNumberPickerListeners(NumberPicker view, final OnValueChangeListener onValueChange) {
        if (onValueChange != null)
            view.setOnValueChangedListener((picker, oldVal, newVal) -> onValueChange.onValueChange(newVal));
    }

    @BindingAdapter("displayedValues")
    public static void setNumberPickerDisplayedValues(NumberPicker view, List<String> values) {
        if (values != null)
            view.setDisplayedValues(values.toArray(new String[0]));
    }

    @BindingAdapter("minValue")
    public static void setNumberPickerMinValue(NumberPicker view, int minValue) {
        if (view.getMinValue() != minValue)
            view.setMinValue(minValue);
    }

    @BindingAdapter("maxValue")
    public static void setNumberPickerMaxValue(NumberPicker view, int maxValue) {
        if (maxValue > 0 && view.getMaxValue() != maxValue)
            view.setMaxValue(maxValue);
    }

    @BindingAdapter("enabled")
    public static void setNumberPickerMaxValue(FloatingActionButton view, boolean enabled) {
        view.setEnabled(enabled);
    }

    public interface OnValueChangeListener {
        void onValueChange(int value);
    }
}
