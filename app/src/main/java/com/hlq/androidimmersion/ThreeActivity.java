package com.hlq.androidimmersion;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ThreeActivity extends BaseActivity {

    private Toolbar mToolBar;
    private View mNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        mToolBar = findViewById(R.id.id_toolbar);
        mNavBar = findViewById(R.id.id_nav);

        setOrChangeTranslucentColor(mToolBar, mNavBar, getResources().getColor(R.color.colorPrimary));
    }
}
