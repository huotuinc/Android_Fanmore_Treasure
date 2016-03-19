package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.AddressListActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

import java.util.List;

/**
 * 跳转提示信息
 */
public class JumpToolsPopWin extends PopupWindow {


    private Context context;
    private Activity aty;
    private WindowManager wManager;
    private Handler mHandler;
    private
    LinearLayout allAddressL;
    private List<MyAddressListModel> addressList;

    public
    JumpToolsPopWin ( Context context, Activity aty, WindowManager wManager,  List<MyAddressListModel> addressList, Handler mHandler ) {
        this.context = context;
        this.aty = aty;
        this.wManager = wManager;
        this.addressList = addressList;
        this.mHandler = mHandler;
    }

    public
    void showWin ( ) {

        Resources resources = context.getResources();
        View view = LayoutInflater.from(context).inflate (
                R.layout.jump_ui,
                null
        );
        allAddressL = (LinearLayout) view.findViewById(R.id.allAddressL);
        allAddressL.removeAllViews();
        if(null != addressList&&!addressList.isEmpty())
        {
            int size = addressList.size();
            for (int i = 0; i < size; i++) {
                final MyAddressListModel address = addressList.get(i);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup
                        .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RelativeLayout addressL = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.address_select, null);
                TextView isSelect = (TextView) addressL.findViewById(R.id.isSelect);
                SystemTools.loadBackground(isSelect, resources.getDrawable(R.mipmap.unselect));
                TextView addressName = (TextView) addressL.findViewById(R.id.addressName);
                addressName.setText(address.getReceiver());
                TextView details = (TextView) addressL.findViewById(R.id.details);
                details.setText(address.getDetails());
                lp.setMargins(0, 0, 0, 0);
                addressL.setLayoutParams(lp);
                allAddressL.addView(addressL);
                allAddressL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissView();
                        Message message = mHandler.obtainMessage();
                        message.what = Contant.ADDRESS_SELECT;
                        message.obj = address;
                        mHandler.sendMessage(message);
                    }
                });
            }
        }
        this.setAnimationStyle(R.style.AnimationPop);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth ( LinearLayout.LayoutParams.WRAP_CONTENT );
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight ( LinearLayout.LayoutParams.WRAP_CONTENT );
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
