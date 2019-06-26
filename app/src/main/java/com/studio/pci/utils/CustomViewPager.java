package com.studio.pci.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean enable;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enable = true;
        setOffscreenPageLimit(5);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enable) return super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enable) return super.onInterceptTouchEvent(event);
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enable = enabled;
    }
}
