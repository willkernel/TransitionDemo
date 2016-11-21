package com.willkernel.app.transitiondemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by admin on 2016/9/24.
 * mail:willkerneljc@gmail.com
 */

public class ClearEditText extends LinearLayout {
    EditText editText;
    ImageView clearIv, searchIv;
    SearchListener searchListener;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_clear_edittext, this, true);
        editText = (EditText) findViewById(R.id.et);
        searchIv = (ImageView) findViewById(R.id.searchIv);
        clearIv = (ImageView) findViewById(R.id.clearIv);
        setListener();
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clearIv.setVisibility(TextUtils.isEmpty(s) ? INVISIBLE : VISIBLE);
                if (!TextUtils.isEmpty(s) && searchListener != null) {
                    searchListener.onSearch();
                }
            }
        });
        searchIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText()) && searchListener != null) {
                    searchListener.onSearch();
                }
            }
        });
        clearIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    interface SearchListener {
        void onSearch();
    }
}
