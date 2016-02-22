package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;




import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.MsgAdapter;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.uitls.WindowUtils;

import java.util.List;

/**
 * 信息选择弹框
 */
public class SelectPopWin extends PopupWindow {

    private Activity aty;
    private Handler mHandler;
    private Context mContext;
    private WindowManager wManager;
    private TextView textView;
    public SelectPopWin(Context mContext, Activity aty, WindowManager wManager, Handler mHandler, TextView textView)
    {
        this.mContext = mContext;
        this.aty = aty;
        this.wManager = wManager;
        this.mHandler = mHandler;
        this.textView = textView;
    }

    public void showWin(String titleTxt, final List<String> datas)
    {
        View view = LayoutInflater.from(mContext).inflate ( R.layout.msg_select_ui, null );
        TextView title = (TextView) view.findViewById(R.id.msgTitle);
        title.setText(titleTxt);
        ListView list = (ListView) view.findViewById(R.id.msgList);
        ImageView emptyImage = (ImageView) view.findViewById(R.id.emptyImage);

        if(null == datas || datas.isEmpty())
        {
            emptyImage.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);

        }
        else
        {
            emptyImage.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            list.setAdapter(new MsgAdapter(datas, mContext));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    textView.setText(datas.get(position));
                    dismissView();
                }
            });
        }

        // 设置SelectPicPopupWindow的View
        this.setContentView ( view );
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth (wManager.getDefaultDisplay ().getWidth ());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight ( (wManager.getDefaultDisplay ().getWidth ()/2) );
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        WindowUtils.backgroundAlpha(aty, 0.4f);
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        this.setBackgroundDrawable(dw);
    }

    public void dismissView()
    {
        setOnDismissListener(new PoponDismissListener(aty ) );
        dismiss ();
    }
}
