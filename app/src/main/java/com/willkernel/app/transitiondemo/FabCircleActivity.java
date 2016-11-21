package com.willkernel.app.transitiondemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;

/**
 * Created by willkernel on 2016/11/18.
 * mail:willkerneljc@gmail.com
 */
public class FabCircleActivity extends Activity {
    @BindView(R.id.iv_fab)
    ImageView iv_fab;
    private Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
        unbind = ButterKnife.bind(this);
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(android.R.color.darker_gray, android.R.color.holo_red_light) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(2*duration).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(targetView, "rotation", 180, 0),
                                ObjectAnimator.ofFloat(targetView, "scaleX", 0, 1),
                                ObjectAnimator.ofFloat(targetView, "scaleY", 0, 1)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(2*duration).start();
                    }
                }).show(this, iv_fab);
    }

    @OnClick(R.id.iv_fab)
    void exit() {
        TransitionsHeleper.startActivity(this, ButterKnifeActivity.class, iv_fab);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TransitionsHeleper.unBind(this);
        unbind.unbind();
    }
}
