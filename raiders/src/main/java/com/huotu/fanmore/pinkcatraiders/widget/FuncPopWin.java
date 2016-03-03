package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能型弹出框
 * 1、删除清单
 * 2、删除订单
 *
 */
public class FuncPopWin extends PopupWindow {

    private Context       context;

    private Activity      aty;

    private WindowManager wManager;

    TextView msg;

    private
    Handler mHandler;
    public List<Long> deletesAll = new ArrayList<Long>();

    public
    FuncPopWin ( Context context, Activity aty, WindowManager wManager, Handler mHandler ) {

        this.aty = aty;
        this.context = context;
        this.wManager = wManager;
        this.mHandler = mHandler;
    }

    public
    void showLayout ( ) {

        Resources resources = context.getResources ( );
        View view = LayoutInflater.from ( context ).inflate (
                R.layout.func_pop_ui,
                null
                                                            );
        TextView funBtn = ( TextView ) view.findViewById ( R.id.funBtn );
        msg = ( TextView ) view.findViewById ( R.id.totalSelect );
        SystemTools.loadBackground ( funBtn, resources.getDrawable(R.mipmap.unselect));
        TextView funOpBtn = (TextView) view.findViewById(R.id.funOpBtn);
        funOpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deletesAll.isEmpty())
                {
                    Message message = mHandler.obtainMessage();
                    message.what = Contant.LIST_DELETE;
                    message.arg1 = 1;
                    mHandler.sendMessage(message);
                }
                else
                {
                    //预处理删除操作
                    Message message = mHandler.obtainMessage();
                    message.what = Contant.LIST_DELETE;
                    message.arg1 = 0;
                    message.obj = deletesAll;
                    mHandler.sendMessage(message);
                }

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

    public void setMsg(String num)
    {
        msg.setText ( "共选择"+num+"件商品" );
    }

    public void setDeletes(List<Long> deletes)
    {
        deletesAll = deletes;
    }

    public void dismissView()
    {
        dismiss ();

    }
}
