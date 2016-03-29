package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.BottomModel;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;


import java.util.List;
import java.util.Map;

/**
 * 通用底部弹出功能框
 */
public class CommonPopWin extends PopupWindow {

    private
    Activity context;

    private View popView;

    private
    List< BottomModel > bottoms;

    private
    BaseApplication application;

    private WindowManager wManager;

    private
    Handler mHandler;

    private View view;

    private Map< String, String > params;

    private Activity aty;

    public
    CommonPopWin (
            Activity context, List< BottomModel > bottoms, BaseApplication application,
            WindowManager wManager, Handler mHandler, View view, Map< String, String > params
                 ) {

        this.context = context;
        this.bottoms = bottoms;
        this.application = application;
        this.wManager = wManager;
        this.mHandler = mHandler;
        this.view = view;
        this.params = params;
    }

    public
    void initView ( ) {

        LayoutInflater inflater = ( LayoutInflater ) context.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
        popView = inflater.inflate ( R.layout.bottom_func_layout, null );
        LinearLayout userLayout = ( LinearLayout ) popView.findViewById ( R.id.funcL );
        if ( null == bottoms || bottoms.isEmpty ( ) ) {

        }
        else {
            for(int i = 0 ; i < bottoms.size () ; i++)
            {
                final BottomModel bottom = bottoms.get ( i );
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( ViewGroup
                        .LayoutParams.MATCH_PARENT, (wManager.getDefaultDisplay ().getHeight ()/12));
                LinearLayout bottomItem = ( LinearLayout ) LayoutInflater.from ( context ).inflate(R.layout.bottom_func_item, null);
                final TextView funcName = ( TextView ) bottomItem.findViewById(R.id.funcName);
                LinearLayout clickL = ( LinearLayout ) bottomItem.findViewById ( R.id.bottomLDtails );
                funcName.setText ( bottom.getBottomName( ) );
                bottomItem.setLayoutParams ( lp );
                clickL.setOnClickListener (
                        new View.OnClickListener ( ) {
                            @Override
                            public
                            void onClick ( View v ) {
                                //根据tag来判断func点击事件
                                String tag = bottom.getBottomTag();
                                switch (tag)
                                {
                                    case "cancel":
                                    {
                                        dismissView();
                                    }
                                    break;
                                    case "camera":
                                    {
                                        //拍照
                                        Message msg = new Message();
                                        msg.what = Contant.UPLOAD_IMAGE;
                                        msg.arg1 = 1;
                                        mHandler.sendMessage(msg);
                                        dismissView();
                                    }
                                    break;
                                    case "fromSD":
                                    {
                                        //从SD卡上获取
                                        Message msg = new Message();
                                        msg.what = Contant.UPLOAD_IMAGE;
                                        msg.arg1 = 2;
                                        mHandler.sendMessage(msg);
                                        dismissView();
                                    }
                                    break;
                                    default:
                                        break;
                                }
                            }
                        }
                );
                userLayout.addView ( bottomItem );

            }
        }

        // 设置SelectPicPopupWindow的View
        this.setContentView ( popView );
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth ( wManager.getDefaultDisplay ().getWidth () );
        // 设置SelectPicPopupWindow弹出窗体的高
        switch ( bottoms.size () )
        {
            case 0:
            {
                this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/15) * 1 );
            }
            break;
            case 1:
            {
                this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/15) * 3 );
            }
            break;
            case 2:
            {
                this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/15) * 4 );
            }
            break;
            case 3:
            {
                this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/15) * 6 );
            }
            break;
            case 4:
            {
                this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/16) * 9 );
            }
            break;
            case 5:
            {
                this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/15) * 8 );
            }
            break;
            default:
                break;
        }

        this.setFocusable(true);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPop);
        WindowUtils.backgroundAlpha ( context, 0.4f );
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        this.setBackgroundDrawable(dw);
    }

    public void dismissView()
    {
        setOnDismissListener(new PoponDismissListener (context ) );
        dismiss ();
    }

}
