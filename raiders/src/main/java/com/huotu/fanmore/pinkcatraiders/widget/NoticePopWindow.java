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
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

/**
 * 消息显示框
 */
public class NoticePopWindow extends PopupWindow {

    private Context context;
    private Activity aty;
    private WindowManager wManager;
    private
    ImageView closeImg;
    private
    TextView notice;
    private String msg;

    public NoticePopWindow(Context context, Activity aty, WindowManager wManager, String msg) {
        this.context = context;
        this.aty = aty;
        this.wManager = wManager;
        this.msg = msg;
    }

    public void showNotice() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.notice_ui,
                null
        );
        closeImg = (ImageView) view.findViewById(R.id.notice_close);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissView();
            }
        });
        notice = (TextView) view.findViewById(R.id.notice_text);
        notice.setText(msg);


        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth((wManager.getDefaultDisplay().getWidth() / 4) * 3);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight((wManager.getDefaultDisplay().getWidth() / 6) * 2);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        WindowUtils.backgroundAlpha(aty, 0.4f);
    }

    public void dismissView() {
        setOnDismissListener(new PoponDismissListener(aty));
        dismiss();

    }
}
