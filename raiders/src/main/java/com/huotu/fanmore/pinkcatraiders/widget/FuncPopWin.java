package com.huotu.fanmore.pinkcatraiders.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
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
    public Long deleteCartAmountAll;

    public TextView funBtn;
    public Resources resources;

    public
    FuncPopWin ( Context context, Activity aty, WindowManager wManager, Handler mHandler ) {

        this.aty = aty;
        this.context = context;
        this.wManager = wManager;
        this.mHandler = mHandler;
    }

    public
    void showLayout ( ) {

        resources = context.getResources();
        View view = LayoutInflater.from ( context ).inflate(
                R.layout.func_pop_ui,
                null
        );
        funBtn = ( TextView ) view.findViewById ( R.id.funBtn );
        //未选
        funBtn.setTag(0);
        msg = ( TextView ) view.findViewById ( R.id.totalSelect );
        SystemTools.loadBackground(funBtn, resources.getDrawable(R.mipmap.unselect));
        TextView funOpBtn = (TextView) view.findViewById(R.id.funOpBtn);
        funOpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null==deletesAll || deletesAll.isEmpty())
                {
                    Message message = mHandler.obtainMessage();
                    message.what = Contant.LIST_DELETE;
                    message.arg1 = 1;
                    mHandler.sendMessage(message);
                }
                else
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder (context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    final AlertDialog alertdialog = dialog.create();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.activity_dialog, null);
                    alertdialog.setView(view, 0, 0, 0, 0);
                    TextView titletext = (TextView) view.findViewById(R.id.titletext);
                    TextView messagetext = (TextView) view.findViewById(R.id.messagetext);
                    Button btn_lift = (Button) view.findViewById(R.id.btn_lift);
                    Button btn_right = (Button) view.findViewById(R.id.btn_right);
                    titletext.setTextColor(context.getResources().getColor(R.color.text_black));
                    btn_lift.setTextColor(context.getResources().getColor(R.color.color_blue));
                    btn_right.setTextColor(context.getResources().getColor(R.color.color_blue));
                    titletext.setText("删除清单");
                    messagetext.setText("确定删除勾选的清单记录？");
                    btn_lift.setText("取消");
                    btn_right.setText("确定");
                    btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertdialog.dismiss();
                            DeleteCart deleteCart = new DeleteCart();
                            deleteCart.setDeletesAll(deletesAll);
                            deleteCart.setDeleteCartAmountAll(deleteCartAmountAll);
                            //预处理删除操作
                            Message message = mHandler.obtainMessage();
                            message.what = Contant.LIST_DELETE;
                            message.arg1 = 0;
                            message.obj = deleteCart;
                            mHandler.sendMessage(message);

                        }
                    });
                    btn_lift.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertdialog.dismiss();
                        }
                    });

                    alertdialog.show();
                }

            }
        });
        funBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) funBtn.getTag();
                if(0==tag)
                {
                    funBtn.setTag(1);
                    SystemTools.loadBackground(funBtn, resources.getDrawable(R.mipmap.unselected));
                    //发送全选的广播
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 11);
                    MyBroadcastReceiver.sendBroadcast(context, MyBroadcastReceiver.SHOP_CART, bundle);
                }
                else if(1==tag)
                {
                    funBtn.setTag(0);
                    SystemTools.loadBackground(funBtn, resources.getDrawable(R.mipmap.unselect));
                    //发送全不选的广播
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 12);
                    MyBroadcastReceiver.sendBroadcast(context, MyBroadcastReceiver.SHOP_CART, bundle);
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

    public void setSelectAll()
    {
        funBtn.setTag(1);
        SystemTools.loadBackground(funBtn, resources.getDrawable(R.mipmap.unselected));
    }

    public void setUNSelectAll()
    {
        funBtn.setTag(0);
        SystemTools.loadBackground(funBtn, resources.getDrawable(R.mipmap.unselect));
    }
    public void setMsg(String num)
    {
        if(null!=msg)
        {
            msg.setText("共选择" + num + "件商品");
        }
    }

    public void setDeletes(List<Long> deletes)
    {
        deletesAll = deletes;
    }

    public void setDeleteCartAmount(long deleteCartAmount)
    {
        deleteCartAmountAll = deleteCartAmount;
    }

    public void dismissView()
    {
        dismiss ();

    }

    public class DeleteCart
    {
        private long deleteCartAmountAll;
        private List<Long> deletesAll;

        public long getDeleteCartAmountAll() {
            return deleteCartAmountAll;
        }

        public void setDeleteCartAmountAll(long deleteCartAmountAll) {
            this.deleteCartAmountAll = deleteCartAmountAll;
        }

        public List<Long> getDeletesAll() {
            return deletesAll;
        }

        public void setDeletesAll(List<Long> deletesAll) {
            this.deletesAll = deletesAll;
        }
    }
}
