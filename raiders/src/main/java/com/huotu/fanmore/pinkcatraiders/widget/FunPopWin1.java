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
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

/**
 * 结算弹出框
 */
public
class FunPopWin1 extends PopupWindow {

    private Context       context;

    private Activity      aty;

    private WindowManager wManager;
    TextView msg;
    private
    Handler mHandler;

    public
    FunPopWin1 ( Context context, Activity aty, WindowManager wManager, Handler mHandler ) {

        this.aty = aty;
        this.context = context;
        this.wManager = wManager;
        this.mHandler = mHandler;
    }

    public
    void showLayout ( ) {

        Resources resources = context.getResources ( );
        View view = LayoutInflater.from ( context ).inflate (
                R.layout.func1_pop_ui,
                null
                                                            );
        TextView funOpBtn = (TextView) view.findViewById(R.id.funOpBtn);
        msg = (TextView) view.findViewById(R.id.allSelect);
        funOpBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mHandler.sendEmptyMessage ( Contant.BILLING );
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

    public void setMsg(String num, String amount)
    {
        msg.setText ( "共"+num+"件奖品，总计："+amount+"元" );
    }

    public void dismissView()
    {
        dismiss ();

    }
}
