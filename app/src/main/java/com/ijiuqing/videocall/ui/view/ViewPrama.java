package com.ijiuqing.videocall.ui.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * Porject CallVideo
 * Author by SongJiYuan on 2017/8/25.
 */

public class ViewPrama {

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
