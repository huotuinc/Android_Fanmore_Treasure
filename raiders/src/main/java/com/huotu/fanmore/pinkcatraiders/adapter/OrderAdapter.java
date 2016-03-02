package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.AddAndSubView;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单数据适配器
 */
public class OrderAdapter extends BaseAdapter {

    private List<OrderModel> orders;
    private Context context;
    private WindowManager wManager;

    public OrderAdapter(List<OrderModel> orders, Context context, WindowManager wManager)
    {
        this.orders = orders;
        this.context = context;
        this.wManager = wManager;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==orders || orders.isEmpty())?null:orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = context.getResources();
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.order_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=orders&&!orders.isEmpty()&&null!=orders.get(position))
        {
            OrderModel order = orders.get(position);
            BitmapLoader.create().loadRoundImage(context, holder.shareUserLogo, order.getPictureUrl (), R.mipmap.error);
            holder.shareUserName.setText(order.getNickName());
            holder.orderTime.setText(DateUtils.transformDataformat3(order.getTime()));
            holder.showTitle.setText(order.getShareOrderTitle());
            holder.productSummy.setText(order.getTitle());
            holder.orderIssue.setText("期号："+order.getIssueNo ());
            holder.orderDetail.setText(order.getCharacters ());
            holder.orderImgs.removeAllViews();
            //动态添加晒单图片
            if(null!=order.getPictureUrls() && !order.getPictureUrls().isEmpty())
            {
                //动态加载图片
                int size = order.getPictureUrls().size();
                size = size >= 4 ? 4 : size;
                for(int i = 0 ; i < size ; i++)
                {
                    ViewGroup.LayoutParams pl = holder.orderImgs.getLayoutParams();

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((pl.height*2)/3, (pl.height*2)/3);
                    ImageView orderImg = (ImageView) LayoutInflater.from(context).inflate ( R.layout.order_img, null );
                    BitmapLoader.create().displayUrl(context, orderImg,
                            order.getPictureUrls().get(i), R.mipmap.error);
                    orderImg.setLayoutParams(lp);
                    holder.orderImgs.addView(orderImg);
                }
            }
            else
            {
                //设置图片为空的界面
                TextView empty = new TextView(context);
                empty.setTextSize(R.dimen.text_size_16);
                empty.setText("晒单图片不存在");
                holder.orderImgs.addView(empty);
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
        @Bind(R.id.shareUserLogo)
        CircleImageView shareUserLogo;
        @Bind(R.id.shareUserName)
        TextView shareUserName;
        @Bind(R.id.orderTime)
        TextView orderTime;
        @Bind(R.id.showTitle)
        TextView showTitle;
        @Bind(R.id.productSummy)
        TextView productSummy;
        @Bind(R.id.orderIssue)
        TextView orderIssue;
        @Bind(R.id.orderDetail)
        TextView orderDetail;
        @Bind(R.id.orderImgs)
        LinearLayout orderImgs;
    }
}
