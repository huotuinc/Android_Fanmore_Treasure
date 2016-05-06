package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.ui.login.ChangePasswordActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.MobileRegActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;


/**
 * 支付弹出框
 */
public
class SetPasswordPopWindow extends PopupWindow {

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
    private WindowManager wManager;
    private TextView old_password;
    private TextView phone_code;
    private TextView tv_cancel;


    public SetPasswordPopWindow(final Activity aty, final Context context, final Handler mHandler, final BaseApplication application, final PayModel payModel,WindowManager wManager) {
        super();
        this.aty = aty;
        this.mHandler = mHandler;
        this.application = application;
        this.payModel = payModel;
        this.context = context;
        this.wManager = wManager;
    }

    public
    void showProgress ( String tag1, String tag2  ) {
        progress = new ProgressPopupWindow ( context, aty, aty.getWindowManager () );
        LayoutInflater inflater = ( LayoutInflater ) aty.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
        payView = inflater.inflate ( R.layout.pop_ui, null );
        old_password = (TextView) payView.findViewById(R.id.old_password);
        phone_code = (TextView) payView.findViewById(R.id.phone_code);
        tv_cancel = (TextView) payView.findViewById(R.id.tv_cancel);

        old_password.setOnClickListener (
                new View.OnClickListener ( ) {
                    @Override
                    public
                    void onClick ( View v ) {
                        dismissView ( );
                        ActivityUtils.getInstance().skipActivity(aty, ChangePasswordActivity.class);
                    }
                } );
        phone_code.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                dismissView();
                Bundle bundle=new Bundle();
                bundle.putInt("type",2);
                ActivityUtils.getInstance().skipActivity(aty, MobileRegActivity.class,bundle);
            }
        } );
        tv_cancel.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                dismissView ( );
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
        this.setAnimationStyle(R.style.AnimationPop);
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
