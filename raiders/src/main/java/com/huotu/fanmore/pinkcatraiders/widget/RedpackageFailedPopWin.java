package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

/**
 * 抢红包失败提示框
 */
public class RedpackageFailedPopWin extends PopupWindow {

    private Context context;
    private Handler mHandler;
    private Activity aty;
    private WindowManager wManager;
    private TextView closeImg;

    public RedpackageFailedPopWin(Context context, Activity aty, WindowManager wManager, Handler mHandler)
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
                R.layout.redpackage_failed,
                null
        );
        TextView btn = (TextView) view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissView();
            }
        });

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
