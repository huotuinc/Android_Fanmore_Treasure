package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;

/**
 * 红包咻咻咻成功界面
 */
public class RedpackageSuccessPopWin extends PopupWindow {

    private Context context;
    private Handler mHandler;
    private Activity aty;
    private WindowManager wManager;
    private TextView successTag;
    private ImageView successIcon;

    public RedpackageSuccessPopWin(Context context, Activity aty, WindowManager wManager, Handler mHandler)
    {
        this.context = context;
        this.aty = aty;
        this.wManager = wManager;
        this.mHandler = mHandler;
    }

    public void showWin()
    {
        Resources resources = context.getResources();
        View view = LayoutInflater.from(context).inflate(
                R.layout.redpackage_success,
                null
        );
        successTag = (TextView) view.findViewById(R.id.successTag);
        successIcon = (ImageView) view.findViewById(R.id.successIcon);



        this.setAnimationStyle(R.style.AnimationPop);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(wManager.getDefaultDisplay().getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(wManager.getDefaultDisplay().getHeight());
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
    }

    public void dismissView()
    {
        setOnDismissListener (new PoponDismissListener(aty));
        dismiss();

    }
}
