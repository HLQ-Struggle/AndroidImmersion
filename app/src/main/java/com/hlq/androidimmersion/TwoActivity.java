package com.hlq.androidimmersion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 通过反射拿到状态栏高度
 * 修改状态栏高度
 * 也可以修改toolbar的padding
 */
public class TwoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private View mNavView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏的透明属性 兼容4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.YELLOW);
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

        // 设置底部导航栏
        mNavView = findViewById(R.id.id_nav);
        ViewGroup.LayoutParams navParams = mNavView.getLayoutParams();
        navParams.height += getNavigationBarHeight(this);
        mNavView.setLayoutParams(navParams);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        return getSystemComponentDimen(this, "status_bar_height");
    }

    /**
     * 获取底部导航栏高度
     *
     * @param context
     * @return
     */
    private int getNavigationBarHeight(Context context) {
        return getSystemComponentDimen(this, "navigation_bar_height");
    }

    /**
     * 反射拿到系统属性值
     *
     * @param context
     * @param dimenName
     * @return
     */
    public static int getSystemComponentDimen(Context context, String dimenName) {
        int stateHeight = 0;
        // 反射R类
        try {
            // 指定目标地址
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            // 实例化
            Object object = clazz.newInstance();
            // 获取属性值
            String heightStr = clazz.getField(dimenName).get(object).toString();
            // 转换
            int height = Integer.parseInt(heightStr);
            // dp --- px
            stateHeight = context.getResources().getDimensionPixelOffset(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return stateHeight;
    }

    public void goThree(View view) {
        startActivity(new Intent(this, ThreeActivity.class));
    }

}
