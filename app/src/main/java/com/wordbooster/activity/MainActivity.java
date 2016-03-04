package com.wordbooster.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wordbooster.activity.fragment.WordListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.app_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            WordListFragment wordFragment = new WordListFragment();
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .add(R.id.app_container, wordFragment).commit();
        }
    }


}
