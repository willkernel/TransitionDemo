package com.willkernel.app.transitiondemo.gradienttab;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.willkernel.app.transitiondemo.R;
import com.willkernel.app.transitiondemo.gradienttab.model.TabValue;

/**
 * Created by tudou
 * on 15-3-28.
 */
public class GradientTabView extends LinearLayout {
    private GradientImageView mGradientIcon;
    private GradientTextView mGradientText;

    public GradientTabView(Context context) {
        this(context, null);
    }

    public GradientTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_gradient_tab, this);
        mGradientIcon = (GradientImageView) view.findViewById(R.id.icon);
        mGradientText = (GradientTextView) view.findViewById(R.id.text1);
    }

    public void setData(TabValue tabValue) {
        if (tabValue == null) return;
        mGradientIcon.setDrawables(tabValue.selectIcon, tabValue.normalIcon);
        mGradientText.setData(tabValue.selectColor, tabValue.normalColor, tabValue.title);
    }

    public void updateOffset(float offset) {
        mGradientIcon.setPaintData(offset);
        mGradientText.setPaintData(offset);
    }
}
