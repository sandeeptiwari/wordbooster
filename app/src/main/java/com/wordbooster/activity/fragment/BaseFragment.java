package com.wordbooster.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Sandeep Tiwari on 3/4/2016.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        onStartActivity();
    }

    protected void onStartActivity() {
    }

    protected void initUI() {

    }


}
