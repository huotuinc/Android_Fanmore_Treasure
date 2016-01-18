package com.huotu.fanmore.pinkcatraiders.listener;

import android.app.Activity;
import android.view.Window;
import android.widget.PopupWindow;

import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;


/**
 * popwin 关闭后取消遮罩层监听器
 */
public
class PoponDismissListener implements PopupWindow.OnDismissListener {

    private
    Activity aty;
    public
    PoponDismissListener ( Activity aty )
    {
        this.aty = aty;
    }
    @Override
    public
    void onDismiss ( )
    {
        WindowUtils.backgroundAlpha(aty, 1.0f);

    }
}
