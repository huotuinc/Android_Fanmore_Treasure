package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

/**
 * 功能型弹出框
 * 1、删除清单
 * 2、删除订单
 *
 */
public class FuncPopWin extends PopupWindow {

    private Context context;
    private Activity aty;
    private WindowManager wManager;

    public FuncPopWin(Context context, Activity aty, WindowManager wManager)
    {
        this.aty = aty;
        this.context = context;
        this.wManager = wManager;
    }

    public
    void showLayout ( )
    {
        Resources resources = context.getResources();
        View view = LayoutInflater.from(context).inflate (
                R.layout.func_pop_ui,
                null
        );
        TextView funBtn = (TextView) view.findViewById(R.id.funBtn);
        SystemTools.loadBackground(funBtn, resources.getDrawable(R.mipmap.unselect));
        TextView funOpBtn = (TextView) view.findViewById(R.id.funOpBtn);
        funOpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView ( view );
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth ( wManager.getDefaultDisplay ().getWidth () );
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight ((int)resources.getDimension(R.dimen.bottom_height));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
    }

    public void dismissView()
    {
        dismiss ();

    }
}
