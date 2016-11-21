package com.willkernel.app.transitiondemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2016/9/23.
 * mail:willkerneljc@gmail.com
 */
public class Fragment1 extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private OnInteractionListener interactionListener;
    private RecyclerView rv_frag;
    private LinearLayoutManager mLayoutManager;
    private String[] myDataset = {"http://s2.buzzhand.net/uploads/3a/f/729017/14343242346387.jpg", "http://www.people.com.cn/mediafile/pic/20160401/9/3422151562367216409.jpg", "http://img.chinatimes.com/newsphoto/2016-04-26/656/20160426002930.jpg", "http://img.chinatimes.com/newsphoto/2015-06-17/656/20150617002415.jpg"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
        try {
            interactionListener = (OnInteractionListener) context;
        } catch (Exception e) {
            throw new ClassCastException("must implement OnInteractionListener");
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Log.e(TAG, "args=" + args.getInt("position"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        return inflater.inflate(R.layout.layout_fragment, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");
        rv_frag = (RecyclerView) view.findViewById(R.id.rv_frag);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated");
        rv_frag.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
        rv_frag.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MyAdapter mAdapter = new MyAdapter(myDataset);
        rv_frag.setAdapter(mAdapter);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        Log.e(TAG, "setInitialSavedState");
        super.setInitialSavedState(state);
    }

    @Override
    public void setRetainInstance(boolean retain) {
        Log.e(TAG, "setRetainInstance=" + retain);
        super.setRetainInstance(retain);
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
        setState(1);
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPictureInPictureModeChanged");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Fragment1() {
        super();
        Log.e(TAG, "Fragment1");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.e(TAG, "onAttachFragment");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        Log.e(TAG, "onPictureInPictureModeChanged");
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory");
        super.onLowMemory();
    }

    interface OnInteractionListener {
        void onRefresh(int state);
    }

    private void setState(int state) {
        Log.e(TAG, "setState=" + state);
        interactionListener.onRefresh(state);
    }
}