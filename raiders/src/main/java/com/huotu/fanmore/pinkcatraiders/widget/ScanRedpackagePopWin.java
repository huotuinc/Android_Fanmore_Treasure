package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;

import java.util.List;

/**
 * 首页添加红包界面
 */
public class ScanRedpackagePopWin extends PopupWindow{

    private Context context;
    private Handler mHandler;
    private Activity aty;
    private WindowManager wManager;
    private LinearLayout moneyL;
    private TextView money;
    private TextView redpackageTag;
    private TextView redpackageBtn;
    private List<Double> data;
    private int type;
    private String msg;

    public ScanRedpackagePopWin(Context context, Activity aty, WindowManager wManager, Handler mHandler, int type, String msg)
    {
        this.context = context;
        this.aty = aty;
        this.wManager = wManager;
        this.mHandler = mHandler;
        this.type = type;
        this.msg = msg;
    }

    public void showWin()
    {
        Resources resources = context.getResources();
        View view = LayoutInflater.from(context).inflate(
                R.layout.redpackage_scan,
                null
        );
        moneyL = (LinearLayout) view.findViewById(R.id.moneyL);
        money = (TextView) view.findViewById(R.id.money);
        redpackageTag = (TextView) view.findViewById(R.id.redpackageTag);
        redpackageBtn = (TextView) view.findViewById(R.id.redpackageBtn);
        if(0==type)
        {
            if(1==data.size())
            {
                moneyL.setVisibility(View.VISIBLE);
                money.setText(String.valueOf(data.get(0)));
                redpackageTag.setText("您有一个金币红包");
            }
            else
            {
                moneyL.setVisibility(View.GONE);
                redpackageTag.setText("您有"+data.size()+"个金币红包");
            }
            redpackageBtn.setText("我知道了");
            redpackageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissView();
                }
            });
        }
        else if(1==type)
        {
            moneyL.setVisibility(View.VISIBLE);
            money.setText(String.valueOf(1));
            redpackageTag.setText(msg);
            redpackageBtn.setText("我要发红包");
            redpackageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message = mHandler.obtainMessage();
                    message.what = 0x33110090;
                    mHandler.sendMessage(message);
                    dismissView();
                }
            });
        }

        this.setAnimationStyle(R.style.AnimationPop);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(wManager.getDefaultDisplay().getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(wManager.getDefaultDisplay().getHeight());
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
    }



    public void dismissView()
    {
        setOnDismissListener (new PoponDismissListener(aty));
        dismiss();

    }

    public void addData(List<Double> data)
    {
          this.data = data;
    }
}
