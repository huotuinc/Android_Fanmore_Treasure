package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.AddressListActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

/**
 * 跳转提示信息
 */
public class JumpToolsPopWin extends PopupWindow {


    private Context context;
    private Activity aty;
    private WindowManager wManager;
    private
    TextView jumpNotice;
    private
    TextView jumpBtn;

    public
    JumpToolsPopWin ( Context context, Activity aty, WindowManager wManager ) {
        this.context = context;
        this.aty = aty;
        this.wManager = wManager;
    }

    public
    void showWin ( String msg, final Class clazz ) {
        View view = LayoutInflater.from(context).inflate (
                R.layout.jump_ui,
                null
        );
        jumpBtn = ( TextView ) view.findViewById ( R.id.jumpBtn );
        jumpBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                dismissView ();
                ActivityUtils.getInstance().showActivity(aty, clazz);
            }
        } );
        jumpNotice = ( TextView ) view.findViewById ( R.id.jumpNotice );
        jumpNotice.setText ( msg );


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
