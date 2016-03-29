package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.ui.redpackage.ReadPackageActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

import java.util.Objects;

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

    public void showWin(int tag, Object o)
    {
        Resources resources = context.getResources();
        View view = LayoutInflater.from(context).inflate(
                R.layout.redpackage_wait,
                null
        );
        TextView waitTag = (TextView) view.findViewById(R.id.waitTag);
        LinearLayout waitTimeL = (LinearLayout) view.findViewById(R.id.waitTimeL);
        TextView hour = (TextView) view.findViewById(R.id.hour);
        TextView min = (TextView) view.findViewById(R.id.min);

        if(0==tag)
        {
            //活动等待
            waitTimeL.setVisibility(View.VISIBLE);
            waitTag.setText("距离下次活动开始还有");
            DateUtils.getTime(hour, min, o);
        }
        if(1==tag || 2==tag)
        {
            //无活动
            waitTimeL.setVisibility(View.GONE);
            waitTag.setText("暂无活动，敬请等待下一期活动");
        }
        closeImg = (TextView) view.findViewById ( R.id.closeImg );
        int statusBarHeight = getStatusBarHeight(context);
        view.setPadding(10, statusBarHeight + 10, 10, 0);
        SystemTools.loadBackground(closeImg, resources.getDrawable(R.mipmap.delete_btn));
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissView();
                mHandler.sendEmptyMessage(ReadPackageActivity.REDPACKAGE_CLOSED);
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
