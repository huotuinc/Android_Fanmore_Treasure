package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.BuyItemModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 购买订单数据适配
 */
public class BuyItemAdapter extends BaseAdapter {

    private List<BuyItemModel> items;
    private Context mContext;

    public BuyItemAdapter(List<BuyItemModel> items, Context mContext)
    {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return  null==items?0:items.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==items||items.isEmpty())?null:items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = mContext.getResources();
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.buy_log_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=items&&!items.isEmpty()&&null!=items.get(position))
        {
            BuyItemModel item = items.get(position);
            BitmapLoader.create().displayUrl(mContext, holder.buyIcon, item.getPictureUrl(), R.mipmap.ic_launcher);
            holder.productName.setText(item.getTitle());
            holder.price.setText("价格：" + item.getPrice());
            holder.amount.setText("数量：" + item.getAmount());
            holder.payMoney.setText("实付合计："+item.getTotalMoney());
            if(0==item.getStatus()) {
                //待付款
                holder.status.setText("等待买家付款");
                holder.controller1.setText("关闭订单");
                holder.controller2.setText("立即付款");
            }
            else if(1==item.getStatus())
            {
                //待发货
                holder.status.setText("等待商家发货");
                holder.controller1.setText("查看商品");
                holder.controller2.setText("再次购买");
            }
            else if(2==item.getStatus())
            {
                //待发货
                holder.status.setText("等待买家收货");
                holder.controller1.setText("查看物流");
                holder.controller2.setText("立即收货");
            }
        }
        else
        {

        }
        return convertView;
    }

    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.buyIcon)
        ImageView buyIcon;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.amount)
        TextView amount;
        @Bind(R.id.status)
        TextView status;
        @Bind(R.id.payMoney)
        TextView payMoney;
        @Bind(R.id.controller1)
        TextView controller1;
        @Bind(R.id.controller2)
        TextView controller2;
    }
}
