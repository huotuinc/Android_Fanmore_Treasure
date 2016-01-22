package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 夺宝记录数据适配
 */
public class RaidersAdapter extends BaseAdapter {

    private List<RaidersModel> raiders;
    private Context mContext;

    public RaidersAdapter(List<RaidersModel> raiders, Context mContext)
    {
        this.raiders = raiders;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return  null==raiders?0:raiders.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==raiders||raiders.isEmpty())?null:raiders.get(position);
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
            convertView = View.inflate(mContext, R.layout.raiders_log_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=raiders&&!raiders.isEmpty()&&null!=raiders.get(position))
        {
            RaidersModel raider = raiders.get(position);
            BitmapLoader.create().displayUrl(mContext, holder.icon, product.getProductIcon(), R.mipmap.ic_launcher);
            if(0==product.getProductTag())
            {
                holder.productTag.setText("十元\n专区");
                SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_1));
            }
            else if(1==product.getProductTag())
            {
                holder.productTag.setText("五元\n专区");
                SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_2));
            }

            holder.productName.setText(product.getProductName());
            holder.lotteryScheduleProgress.setMax(100);
            holder.lotteryScheduleProgress.setProgress((int) (100 * product.getLotterySchedule()));
            holder.totalRequired.setText("总需："+product.getTotal());
            holder.surplus.setText("剩余："+product.getSurplus());
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
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.productTag)
        TextView productTag;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.totalRequired)
        TextView totalRequired;
        @Bind(R.id.surplus)
        TextView surplus;
        @Bind(R.id.lotteryScheduleProgress)
        ProgressBar lotteryScheduleProgress;
        @Bind(R.id.addBtn)
        TextView addBtn;
    }
}
