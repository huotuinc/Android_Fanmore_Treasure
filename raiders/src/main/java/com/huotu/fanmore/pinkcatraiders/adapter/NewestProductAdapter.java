package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    private List<NewOpenListModel> newestProducts;
    private Context context;
    private Activity aty;
    private TimeCount tc;
    public NewestProductAdapter(List<NewOpenListModel> newestProducts,Activity aty,Context context, TimeCount tc)
    {
        this.newestProducts = newestProducts;
        this.context = context;
        this.aty = aty;
        this.tc = tc;
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
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=newestProducts&&!newestProducts.isEmpty()&&null!=newestProducts.get(position))
        {
            final NewOpenListModel product = newestProducts.get(position);
            BitmapLoader.create().displayUrl(context, holder.newestProductIcon, product.getPictureUrl(), R.mipmap.ic_launcher);

            if(0==product.getAreaAmount().intValue())
            {
                holder.newestProductTag.setText("十元\n专区");
                SystemTools.loadBackground(holder.newestProductTag, resources.getDrawable(R.mipmap.area_1));
            }
            else if(1==product.getAreaAmount().intValue())
            {
                holder.newestProductTag.setText("五元\n专区");
                SystemTools.loadBackground(holder.newestProductTag, resources.getDrawable(R.mipmap.area_2));
            }
            else {

                holder.newestProductTag.setVisibility(View.GONE);

            }
            if (1==product.getStatus()) {
                holder.Rl1.setVisibility(View.VISIBLE);
                holder.Rl2.setVisibility(View.GONE);
                holder.announcedTag.setText("即将揭晓");

                long millSec = product.getToAwardingTime();

                TimeCount tc = new TimeCount(millSec*1000, 100, holder.countdown);
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
                    bundle.putStringArrayList("imgs", (ArrayList<String>) product.getImgs());
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
    }
}
