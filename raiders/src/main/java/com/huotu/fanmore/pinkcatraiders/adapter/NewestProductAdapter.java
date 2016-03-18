package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.NewOpenListModel;

import com.huotu.fanmore.pinkcatraiders.ui.product.ProductDetailActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.TimeCount;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.widget.CountDownTimerButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 最新揭晓列表适配
 */
public class NewestProductAdapter extends BaseAdapter {

    private GridView gv;
    private List<NewOpenListModel> newestProducts;
    private Context context;
    private Activity aty;
    private TimeCount tc;
    public NewestProductAdapter(GridView gv, List<NewOpenListModel> newestProducts,Activity aty,Context context, TimeCount tc)
    {
        this.newestProducts = newestProducts;
        this.context = context;
        this.aty = aty;
        this.tc = tc;
        this.gv = gv;
    }

    @Override
    public int getCount() {
        return null==newestProducts?0:newestProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==newestProducts||newestProducts.isEmpty())?null:newestProducts.get(position);
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
            convertView = View.inflate(context, R.layout.newest_product_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            // 绑定listener监听器，检测convertview的height
            holder.update();
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nickName.setTag(position);
        holder.luckyNumber.setTag(convertView);
        if(null!=newestProducts&&!newestProducts.isEmpty()&&null!=newestProducts.get(position))
        {
            final NewOpenListModel product = newestProducts.get(position);
            BitmapLoader.create().displayUrl(context, holder.newestProductIcon, product.getPictureUrl(), R.mipmap.error);

            if(0!=product.getAreaAmount().intValue())
            {
                holder.newestProductTag.setText("专区\n商品");
                SystemTools.loadBackground(holder.newestProductTag, resources.getDrawable(R.mipmap.area_1));
            }
            else {

                holder.newestProductTag.setVisibility(View.GONE);

            }
            if (1==product.getStatus()) {
                holder.Rl1.setVisibility(View.VISIBLE);
                holder.Rl2.setVisibility(View.GONE);
                holder.announcedTag.setText("即将揭晓");

                long millSec = product.getToAwardingTime();

                tc = new TimeCount(millSec*1000, 100, holder.countdown);
                tc.start();
            }  else if (2==product.getStatus()){
                holder.Rl1.setVisibility(View.GONE);
                holder.Rl2.setVisibility(View.VISIBLE);
                holder.nickName.setText(product.getNickName());
                holder.attendAmount.setText("参与人次:"+String.valueOf(product.getAttendAmount()));
                holder.luckyNumber.setText(String.valueOf(product.getLuckyNumber()));
                holder.time.setText("揭晓时间:" + DateUtils.transformDataformat2(product.getTime()));

            }else {
            }
            holder.newestProductName.setText(product.getTitle());
            holder.newestIssue.setText("期号:" + product.getIssueId());
            holder.newestProductLL.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle ( );
                    bundle.putInt("tip",2);
                    bundle.putLong("issueId", product.getIssueId());
                    bundle.putSerializable("newopenlist", product);
                    ActivityUtils.getInstance().showActivity ( aty, ProductDetailActivity.class, bundle );
                }
            });

        }
        return convertView;
    }
//    class CountDownFinish  implements CountDownTimerButton.CountDownFinishListener {
//
//        @Override
//        public void finish()
//        {
//            if( String.valueOf(product.getToAwardingTime()=="0")){
//                // 刷新获取按钮状态，设置为可获取语音
//                holder.countdown.setText("即将揭晓...");
//
//            }
//        }
//
//    }
    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.newestProductIcon)
        ImageView newestProductIcon;
        @Bind(R.id.newestProductTag)
        TextView newestProductTag;
        @Bind(R.id.newestProductName)
        TextView newestProductName;
        @Bind(R.id.newestIssue)
        TextView newestIssue;
        @Bind(R.id.announcedIcon)
        ImageView announcedIcon;
        @Bind(R.id.announcedTag)
        TextView announcedTag;
        @Bind(R.id.countdown)
        TextView countdown;
        @Bind(R.id.Rl1)
        RelativeLayout Rl1;
        @Bind(R.id.Rl2)
        RelativeLayout Rl2;
        @Bind(R.id.nickName)
        TextView nickName;
        @Bind(R.id.attendAmount)
        TextView attendAmount;
        @Bind(R.id.luckyNumber)
        TextView luckyNumber;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.newestProductLL)
        RelativeLayout newestProductLL;

        public void update() {
            nickName.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int position = (int) nickName.getTag();
                    // 这里是保证同一行的item高度是相同的！！也就是同一行是齐整的 height相等
                    if (position > 0 && position % 2 == 1) {
                        View v = (View) luckyNumber.getTag();
                        int height = v.getHeight();
                        View view = gv.getChildAt(position - 1);
                        int lastheight = view.getHeight();
                        // 得到同一行的最后一个item和前一个item想比较，把谁的height大，就把两者中                                                                // height小的item的高度设定为height较大的item的高度一致，也就是保证同一                                                                 // 行高度相等即可
                        if (height > lastheight) {
                            view.setLayoutParams(new GridView.LayoutParams(
                                    GridView.LayoutParams.FILL_PARENT,
                                    height));
                        } else if (height < lastheight) {
                            v.setLayoutParams(new GridView.LayoutParams(
                                    GridView.LayoutParams.FILL_PARENT,
                                    lastheight));
                        }
                    }
                }
            });
        }
    }
}
