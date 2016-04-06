package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.MallPayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.uitls.AliPayUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PayFunc;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;


/**
 * 支付弹出框
 */
public
class PayPopWindow extends PopupWindow {

    private
    Button wxPayBtn;
    private Button alipayBtn;
    private
    Button cancelBtn;
    private View     payView;
    private Activity aty;
    private Handler  mHandler;
    private BaseApplication application;
    private PayModel payModel;
    private Context context;
    public ProgressPopupWindow progress;
    public MallPayModel mallPay;


    public
    PayPopWindow ( final Activity aty, final Context context, final Handler mHandler, final BaseApplication application, final PayModel payModel, String tag1, String tag2 ) {
        super ( );
        this.aty = aty;
        this.mHandler = mHandler;
        this.application = application;
        this.payModel = payModel;
        this.context = context;
        progress = new ProgressPopupWindow ( context, aty, aty.getWindowManager () );
        LayoutInflater inflater = ( LayoutInflater ) aty.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
        mallPay = (MallPayModel)payModel;
        payView = inflater.inflate ( R.layout.pop_pay_ui, null );
        wxPayBtn = ( Button ) payView.findViewById ( R.id.wxPayBtn );
        wxPayBtn.setText(tag1);
        alipayBtn = ( Button ) payView.findViewById ( R.id.alipayBtn );
        alipayBtn.setText(tag2);
        cancelBtn = ( Button ) payView.findViewById ( R.id.cancelBtn );

        wxPayBtn.setOnClickListener (
                new View.OnClickListener ( ) {
                    @Override
                    public
                    void onClick ( View v ) {
                        dismissView();
                        progress.showProgress("等待微信支付跳转");
                        progress.showAtLocation(
                                aty.findViewById(R.id.titleText),
                                Gravity.CENTER, 0, 0
                        );
                        payModel.setAttach ( payModel.getOrderNo ( ) + "_0" );
                        //添加微信回调路径
                        PayFunc payFunc = new PayFunc ( context, payModel, application, mHandler, aty, progress );
                        payFunc.wxPay();
                    }
                } );
        alipayBtn.setOnClickListener ( new View.OnClickListener ( ) {
                                           @Override
                                           public
                                           void onClick ( View v ) {
                                               Message msg = new Message();
                                               msg.what = 0x12131422;
                                               mallPay.setPaymentType ( "1" );
                                               msg.obj = mallPay;
                                               mHandler.sendMessage(msg);
                                               dismissView();
                                           }
                                       } );
        cancelBtn.setOnClickListener ( new View.OnClickListener ( ) {
                                           @Override
                                           public
                                           void onClick ( View v ) {
                                               dismissView();
                                           }
                                       } );

        //设置SelectPicPopupWindow的View
        this.setContentView ( payView );
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth ( LinearLayout.LayoutParams.MATCH_PARENT );
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight ( LinearLayout.LayoutParams.WRAP_CONTENT );
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        WindowUtils.backgroundAlpha ( aty, 0.4f );

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框

        payView.setOnTouchListener (
                new View.OnTouchListener ( ) {
                    @Override
                    public
                    boolean onTouch ( View v, MotionEvent event ) {
                        int height = payView.findViewById ( R.id.popLayout ).getTop ( );
                        int y      = ( int ) event.getY ( );
                        if ( event.getAction ( ) == MotionEvent.ACTION_UP ) {
                            if ( y < height ) {
                                dismissView ();
                            }
                        }
                        return true;
                    }
                }
                                   );
    }

    public void dismissView()
    {
        setOnDismissListener ( new PoponDismissListener ( aty ) );
        dismiss ();

    }
}
