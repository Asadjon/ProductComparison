package com.cyberpantera.productcomparison.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cyberpantera.productcomparison.R;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class MyViewPager extends ViewPager {

    private Orientation orientation = Orientation.Horizontal;

    @AllArgsConstructor
    @Getter
    @ToString
    public enum Orientation {
        Horizontal(0), Vertical(1);
        private final int order;

        public static Orientation getOrientation(int order) {
            return order == 1 ? Vertical : Horizontal;
        }
    }

    public MyViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable")
            TypedArray a = context.obtainStyledAttributes(attrs, com.google.android.material.R.styleable.Layout);
            setOrientation(Orientation.getOrientation(a.getInt(com.google.android.material.R.styleable.Layout_android_orientation, 0)));
            a.recycle();
        }
    }

    private void updatePageTransformer() {
        if (orientation == Orientation.Vertical) {
            setPageTransformer(true, new VerticalPageTransformer());
            setOverScrollMode(OVER_SCROLL_NEVER);
        } else {
            setPageTransformer(true, null);
            setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // Return touch coordinates back to normal
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    private MotionEvent swapXY(MotionEvent ev) {
        if (orientation == Orientation.Vertical) {
            float width = getWidth();
            float height = getHeight();

            float newX = (ev.getY() / height) * width;
            float newY = (ev.getX() / width) * height;

            ev.setLocation(newX, newY);
        }
        return ev;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        updatePageTransformer();
    }

    private static class VerticalPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position < -1) view.setAlpha(0);
            else if (position <= 1) {
                view.setAlpha(1);
                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);
                // Set Y position to swipe in from top
                view.setTranslationY(position * view.getHeight());
            } else view.setAlpha(0);
        }
    }

    public static class BindingAdapters {

        @BindingAdapter("selectedItemPosition")
        public static void setSelectedItemPosition(ViewPager viewPager, int position) {
            if (position >= 0)
                viewPager.setCurrentItem(position);
        }

        @BindingAdapter("onPageChange")
        public static void setOnPageChangeListener(ViewPager viewPager, ViewPager.OnPageChangeListener listener) {
            if (listener != null)
                viewPager.addOnPageChangeListener(listener);
        }
    }
}
