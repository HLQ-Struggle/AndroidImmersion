package com.hlq.androidimmersion;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 通过反射拿到状态栏高度
 * 修改状态栏高度
 * 也可以修改toolbar的padding
 */
public class TwoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏的透明属性 兼容4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_two);

        mToolbar = findViewById(R.id.id_toolbar);
        // 方式一：动态获取toolbar高度
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
        int statusBarHeight = getStatusBarHeight(this);
        params.height += statusBarHeight;
        mToolbar.setLayoutParams(params);

        mToolbar.setPadding(mToolbar.getPaddingLeft(),
                mToolbar.getTop() + getStatusBarHeight(this),
                mToolbar.getPaddingRight(),
                mToolbar.getPaddingBottom());
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        int statuHeight = 0;
        // 反射R类
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("status_bar_height").get(object).toString();
            int height = Integer.parseInt(heightStr);
            // dp --- px
            statuHeight = context.getResources().getDimensionPixelOffset(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statuHeight;
    }
}
