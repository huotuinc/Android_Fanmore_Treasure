package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.ui.redpackage.ReadPackageActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

/**
 * 引导登陆界面
 */
public class GuideLoginPopWin extends PopupWindow {

    private Context context;
    private Handler mHandler;
    private Activity aty;
    private WindowManager wManager;

    public GuideLoginPopWin(Context context, Handler mHandler, Activity aty, WindowManager wManager)
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
                R.layout.guide_login,
                null
        );
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView goLogin = (TextView) view.findViewById(R.id.goLogin);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissView();
            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHandler.sendEmptyMessage(Contant.GO_LOGIN);
                dismissView();
            }
        });

        this.setAnimationStyle(R.style.AnimationPop);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(wManager.getDefaultDisplay().getWidth()*2/3);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(wManager.getDefaultDisplay().getHeight()/3);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        WindowUtils.backgroundAlpha(aty, 0.4f);
    }

    public void dismissView()
    {
        setOnDismissListener ( new PoponDismissListener( aty ) );
        dismiss ();
    }
}
