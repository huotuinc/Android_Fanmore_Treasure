package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.RechargeModel;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 充值记录数据适配
 */
public class RechargeAdapter extends BaseAdapter {

    private List<RechargeModel> recharges;
    private Context context;
    public RechargeAdapter(List<RechargeModel> recharges, Context context)
    {
        this.recharges = recharges;
        this.context = context;
    }
    @Override
    public int getCount() {
        return null==recharges?0:recharges.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==recharges||recharges.isEmpty())?null:recharges.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = context.getResources();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.recharge_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (null != recharges && !recharges.isEmpty() && null != recharges.get(position)) {
            RechargeModel recharge = recharges.get(position);
            if(0==recharge.getMoneyFlowType ( ))
            {
                holder.payChannel.setText("微信支付");
            }
            else if(1==recharge.getMoneyFlowType())
            {
                holder.payChannel.setText("支付宝支付");
            }
            else if(2==recharge.getMoneyFlowType())
            {
                holder.payChannel.setText("购买");
            }
            else if(3==recharge.getMoneyFlowType())
            {
                holder.payChannel.setText("充值红包");
            }

            holder.payTime.setText(DateUtils.transformDataformat1(recharge.getTime ()));
            holder.payStatus.setText("已付款");
            holder.money.setText(recharge.getMoney()+"元");
        } else {

        }
        return convertView;
    }

    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.payChannel)
        TextView payChannel;
        @Bind(R.id.payTime)
        TextView payTime;
        @Bind(R.id.payStatus)
        TextView payStatus;
        @Bind(R.id.money)
        TextView money;
    }
}
