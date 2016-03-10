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
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

/**
 * 红包倒计时界面
 */
public class RadpackageWaitPopWin extends PopupWindow {

    private Context context;
    private Handler mHandler;
    private Activity aty;
    private WindowManager wManager;
    private TextView closeImg;
    private TextView hour;
    private TextView min;

    public RadpackageWaitPopWin(Context context, Activity aty, WindowManager wManager, Handler mHandler)
    {
        this.context = context;
        this.aty = aty;
        this.wManager = wManager;
        this.mHandler = mHandler;
    }

    public void showWin()
    {
        Resources resources = context.getResources();
        View view = LayoutInflater.from(context).inflate (
                R.layout.redpackage_wait,
                null
        );
        closeImg = (TextView) view.findViewById ( R.id.closeImg );
        int statusBarHeight = getStatusBarHeight(context);
        view.setPadding(10, statusBarHeight+10, 10, 0);
        SystemTools.loadBackground(closeImg, resources.getDrawable(R.mipmap.delete_btn));
        closeImg.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                dismissView ();
                mHandler.sendEmptyMessage(0x66660001);
            }
        } );
        hour = (TextView) view.findViewById ( R.id.hour );
        hour.setText("12");
        min = (TextView) view.findViewById ( R.id.min );
        min.setText("24");

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(wManager.getDefaultDisplay().getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(wManager.getDefaultDisplay().getHeight());
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
    }

    public int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void dismissView()
    {
        setOnDismissListener ( new PoponDismissListener( aty ) );
        dismiss ();

    }
}
