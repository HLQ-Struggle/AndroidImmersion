package com.hlq.androidimmersion;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * 沉浸式 Translucent：谷歌推荐所述的沉浸式 乃是让整个APP充满在屏幕中，此时没有状态栏，也没有底部导航栏
 * 而我们实际开发过程中所谓的沉浸式 乃是 类似QQ 中标题栏和状态栏颜色一致
 * 需要考虑兼容性 一般基于5.x API 最多兼容到4.4 API 低于4.4 API 无法兼容
 * ROM兼容 需要根据厂商以及当前版本做特定的修改 就好比相机一样 不同的手机型号 可能结果不一样
 * 5.0 以上api自动实现沉浸式 colorPrimaryDark
 * 方式一：
 * <item name="colorPrimary">@color/colorPrimary</item>
 * <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
 * <item name="colorAccent">@color/colorAccent</item>
 * 方式二：
 * <item name="android:navigationBarColor">@color/system_bottom_nav_color</item>
 * 方式三：5.0以上直接通过api修改
 * getWindow().setStatusBarColor(Color.YELLOW);
 * <p>
 * 4.4 兼容
 * 方式一：使用属性样式解决 设置为透明 只能给api 19 使用 兼容性不是很好
 * <item name="android:windowTranslucentStatus">true</item>
 * 方式二：直接通过api进行修改
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
 * Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
 * getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
 * }
 * 这样做的结果就是 APP内容顶到最上面，也就是状态栏遮挡了界面
 * 解决办法一，ToolBar设置属性即可
 * android:fitsSystemWindows="true"
 * 该属性的作用：设置布局是，是否考虑当前系统窗口，如果为true就会调整整个系统窗口布局(包括状态栏的view)以适应你的布局
 * 现在有个问题就是 ScrollView中包含EditText 输入法键盘会顶上去
 * 方法二:根布局设置以上属性 通过 设置双层背景色实现
 * <p>
 * create by heliquan at 2017年11月17日
 */
public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏的透明属性 兼容4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.YELLOW);
        }
    }

    public void goTwo(View view) {
        startActivity(new Intent(this, TwoActivity.class));
    }

}
