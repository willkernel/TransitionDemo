package com.willkernel.app.transitiondemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import immortalz.me.library.TransitionsHeleper;


/**
 * Created by willkernel on 2016/11/16.
 * mail:willkerneljc@gmail.com
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private Picasso picasso;
    private int widthPixels;
    private int heightPixels;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.info_btn)
        TextView mTextView;
        @BindView(R.id.info_image)
        ImageView mImageView;
        @BindView(R.id.frame)
        FrameLayout frame;
        @BindView(R.id.startNextBtn)
        Button startNextBtn;
        @BindString(R.string.main)
        String main;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        picasso = Picasso.with(parent.getContext());
        widthPixels = parent.getContext().getResources().getDisplayMetrics().widthPixels;
        heightPixels = parent.getContext().getResources().getDisplayMetrics().heightPixels;
        //red Network,black Disk,green Memory
        picasso.setIndicatorsEnabled(false);
        picasso.setLoggingEnabled(true);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(cardView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(holder.main);
        holder.startNextBtn.setText(mDataset[position]);
        holder.itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch(v, event, position, holder.mImageView);
                return false;
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch(v, event, position, holder.mImageView);
                return false;
            }
        });
        holder.startNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionsHeleper.startActivity((Activity) holder.itemView.getContext(), new Intent(holder.itemView.getContext(), ButterKnifeActivity.class).putExtra("Text", mDataset[position]), holder.itemView);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }

    private void touch(View view, MotionEvent event, int position, ImageView mImageView) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.getBackground().setHotspot(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                selectButton(view, true, (int) event.getX(), (int) event.getY(), position, mImageView);
                break;
        }
    }

    private void selectButton(View view, boolean reveal, int startX, int startY, int position, final ImageView mImageView) {
        view.setSelected(!view.isSelected());
        if (reveal) {
            ViewAnimationUtils.createCircularReveal(view,
                    startX,
                    startY,
                    0,
                    view.getHeight()).start();
            if (view.isSelected()) {
                ViewAnimationUtils.createCircularReveal(mImageView,
                        startX,
                        startY,
                        0,
                        mImageView.getHeight()).start();
            }
        }
        if (view.isSelected()) {
            mImageView.startAnimation(AnimationUtils.makeInAnimation(mImageView.getContext(), true));
            mImageView.setVisibility(View.VISIBLE);
            picasso.load(mDataset[position])
                    //fit cannot be used with resize
                    .resize(widthPixels / 2, heightPixels / mDataset.length)
                    .centerCrop()
                    //                .memoryPolicy(MemoryPolicy.NO_CACHE)
                    //                .networkPolicy(NetworkPolicy.OFFLINE)
                    //        Center inside/Crop requires calling resize with positive width and height.
                    //                .centerInside()
                    //                .placeholder(R.mipmap.ic_placeholder)
                    //                .error(R.mipmap.ic_error_placeholder)
                    //                .rotate(20)
                    //                .transform(new CropSquareTransformation())
                    .into(mImageView);
        } else {
            Animation animation = AnimationUtils.makeOutAnimation(mImageView.getContext(), true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mImageView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mImageView.startAnimation(animation);
        }
    }
}