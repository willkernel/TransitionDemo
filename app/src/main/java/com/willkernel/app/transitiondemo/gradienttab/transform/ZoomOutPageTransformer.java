package com.willkernel.app.transitiondemo.gradienttab.transform;


import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by tudou
 * on 15-3-29.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.8f;
    private static final float MIN_ALPHA = 1f;
    public void transformPage(View view, float position) {

        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            ViewHelper.setAlpha(view, 0);
        } else if (position < 0) {
            // Fade the page relative to its size.
            ViewHelper.setAlpha(view, 1.0f);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            ViewHelper.setTranslationX(view, pageWidth * - position);

            // Scale the page down (between MIN_SCALE and 1)
            ViewHelper.setScaleX(view, scaleFactor);
            ViewHelper.setScaleY(view, scaleFactor);

            // Fade the page relative to its size.
            ViewHelper.setAlpha(view, MIN_ALPHA +
                            (1 - MIN_SCALE) * (1 - Math.abs(position)));

        } else { // (1,+Infinity]
//             This page is way off-screen to the right.
            ViewHelper.setAlpha(view, 0);
        }
    }
}