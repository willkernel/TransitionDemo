package com.willkernel.app.transitiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;

/**
 * Created by willkernel on 2016/11/18.
 * mail:willkerneljc@gmail.com
 */
public class ImageDetailActivity extends Activity {
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    private Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_detail);
        unbind = ButterKnife.bind(this);
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(android.R.color.holo_red_light, android.R.color.holo_purple) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        Picasso.with(ImageDetailActivity.this)
                                .load(bean.getImgUrl())
                                .into(copyView);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        Picasso.with(ImageDetailActivity.this)
                                .load(bean.getImgUrl())
                                .into(targetView);
                    }
                }).show(this, ivDetail);
    }

    @Override
    public void onBackPressed() {
        TransitionsHeleper.startActivity(this, ButterKnifeActivity.class, ivDetail);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TransitionsHeleper.unBind(this);
        unbind.unbind();
    }
}
