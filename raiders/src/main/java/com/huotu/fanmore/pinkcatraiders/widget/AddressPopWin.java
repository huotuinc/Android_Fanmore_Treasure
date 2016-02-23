package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.SelectAddressAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.AddressModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.AddAddressActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

import java.util.List;

/**
 * 地址选择弹出框
 */
public
class AddressPopWin extends PopupWindow {

    private
    Handler mHandler;

    private
    BaseApplication application;

    private
    Context  context;

    private
    Activity aty;

    private
    List< AddressModel > addresses;

    private int type;

    private View popView;
    View emptyView = null;

    private WindowManager wManager;

    public
    AddressPopWin (
            Handler mHandler, BaseApplication application, Context context, List<
            AddressModel > addresses, int type, WindowManager wManager, Activity aty
                  ) {

        this.mHandler = mHandler;
        this.application = application;
        this.addresses = addresses;
        this.context = context;
        this.type = type;
        this.wManager = wManager;
        this.aty = aty;
    }

    public
    void initView ( ) {

        LayoutInflater inflater = ( LayoutInflater ) context.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
        popView = inflater.inflate ( R.layout.address_item, null );
        SystemTools.loadBackground ( popView, context.getResources ().getDrawable ( R.color.color_white ) );
        PullToRefreshListView addressList = ( PullToRefreshListView ) popView.findViewById ( R.id.addressItemList );
        if(null!=addresses && !addresses.isEmpty ())
        {
            addressList.setAdapter ( new SelectAddressAdapter ( context, mHandler, addresses, type, application, ((AddAddressActivity)aty).addressPopWin ) );
        }
        else
        {
            emptyView = inflater.inflate(R.layout.empty, null);
            TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
            emptyTag.setText("暂无地址信息");
            TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
            emptyBtn.setVisibility(View.GONE);
            addressList.setEmptyView ( emptyView );
        }
        // 设置SelectPicPopupWindow的View
        this.setContentView ( popView );
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth ( (wManager.getDefaultDisplay ().getWidth ()/3)*2 );
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/6) * 5 );

        this.setFocusable(true);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPop);
        WindowUtils.backgroundAlpha ( aty, 0.4f );
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        this.setBackgroundDrawable(dw);
    }

    public void dismissView()
    {
        setOnDismissListener(new PoponDismissListener (aty ) );
        dismiss ( );
    }


}
