package com.willkernel.app.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;

/**
 * Created by willkernel on 2016/11/17.
 * mail:willkerneljc@gmail.com
 */
public class ButterKnifeActivity extends Activity {
    @BindView(R.id.avatarIv)
    ImageView avatarIv;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.btn_circle)
    FloatingActionButton fab;
    @BindViews({R.id.et1, R.id.et2})
    List<EditText> mEditTexts;
    @BindString(R.string.app_name)
    String name;
    @BindColor(R.color.colorAccent)
    int accent;
    @BindDrawable(R.mipmap.ic_launcher)
    Drawable icon;
    private Unbinder unbinder;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        unbinder = ButterKnife.bind(this);
        ButterKnife.apply(mEditTexts, DISABLE);
        ButterKnife.apply(mEditTexts, ENABLED, true);
        ButterKnife.apply(mEditTexts, View.ALPHA, 0.8f);
        Intent intent = getIntent();
        if (intent != null) {
            nameTv.setText(intent.getStringExtra("Text"));
            imgUrl = intent.getStringExtra("Text");
        }
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(android.R.color.holo_red_light, android.R.color.darker_gray) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(2 * duration).start();
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
                        set.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                Picasso.with(ButterKnifeActivity.this).load(imgUrl).error(R.mipmap.ic_error_placeholder).into(avatarIv);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        set.setDuration(2 * duration).start();
                    }
                }).show(this, avatarIv);
        Picasso.with(this).load(imgUrl).error(R.mipmap.ic_error_placeholder).into(avatarIv);
    }

    @OnClick(R.id.toastBtn)
    void toast() {
        Toast.makeText(this, getClass().getSimpleName(), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.avatarIv)
    void detail() {
        TransitionsHeleper.startActivity(this, ImageDetailActivity.class, avatarIv, imgUrl);
    }

    @OnClick(R.id.btn_circle)
    void fab() {
        TransitionsHeleper.startActivity(this, FabCircleActivity.class, fab);
        finish();
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };
    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}