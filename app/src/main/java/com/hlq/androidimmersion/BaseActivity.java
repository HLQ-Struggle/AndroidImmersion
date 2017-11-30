package com.hlq.androidimmersion;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by HLQ on 2017/11/30
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断当前系统版本
        // 分为俩个区间 1. 大于等于4.4 小于5.0 2.大于等于5.0
        // 原因 4.4没有提供api 需要单独设置 而 5.0提供相关api
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏为透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置底部虚拟导航栏为透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 主要针对ToolBar进行设置
     *
     * @param toolbar
     * @param bottomNavigationBar
     * @param translucentPrimaryColor
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar, int translucentPrimaryColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { // 大于等于4.4 小于5.0
            if (toolbar != null) {
                // 动态获取当前系统状态栏高度 累加
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(this);
                params.height += statusBarHeight;
                toolbar.setLayoutParams(params);
                // 设置padding 预防状态栏遮挡ToolBar
                toolbar.setPadding(toolbar.getPaddingLeft(),
                        toolbar.getTop() + getStatusBarHeight(this),
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                // 设置顶部颜色
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
            if (bottomNavigationBar != null) {
                if (hasNavigationBarShow(getWindowManager())) {
                    // 动态获取当前系统虚拟导航栏高度 累加
                    ViewGroup.LayoutParams navParams = bottomNavigationBar.getLayoutParams();
                    navParams.height += getNavigationBarHeight(this);
                    bottomNavigationBar.setLayoutParams(navParams);
                    // 设置底部颜色
                    bottomNavigationBar.setBackgroundColor(translucentPrimaryColor);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 版本以上 包含 5.0
            // 设置顶部颜色
            toolbar.setBackgroundColor(translucentPrimaryColor);
            // 通过api设置状态栏颜色
            getWindow().setStatusBarColor(translucentPrimaryColor);
            // 设置底部虚拟导航栏颜色
            getWindow().setNavigationBarColor(translucentPrimaryColor);
        } else {
            // 小于4.4 不做处理
        }
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
    private int getSystemComponentDimen(Context context, String dimenName) {
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

    /**
     * 判断是否存在NavigationBar
     *
     * @param windowManager
     * @return
     */
    private static boolean hasNavigationBarShow(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getRealMetrics(outMetrics);
        // 获取整个屏幕高度
        int heightPixelsAll = outMetrics.heightPixels;
        // 获取整个屏幕宽度
        int widthPixelsAll = outMetrics.widthPixels;
        // 获取内容展示部分的高度
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        // 获取内容显示区域高度
        int heightPixelsContent = outMetrics.heightPixels;
        // 获取内容显示区域宽度
        int widthPixelsContent = outMetrics.widthPixels;
        int width = widthPixelsAll - widthPixelsContent;
        int height = heightPixelsAll - heightPixelsContent;
        return width > 0 || height > 0; // 兼容横竖屏
    }

}
