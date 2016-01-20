package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义grid数据适配器
 */
public class MyGridAdapter extends BaseAdapter {

    private List<ProductModel> productModels;
    private Context mContext;

    public MyGridAdapter(List<ProductModel> productModels, Context mContext)
    {
        this.productModels = productModels;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return productModels.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==productModels || productModels.isEmpty())?null:productModels.get(position);
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
            convertView = View.inflate(mContext, R.layout.product_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=productModels&&!productModels.isEmpty()&&null!=productModels.get(position))
        {
            ProductModel product = productModels.get(position);
            BitmapLoader.create().displayUrl(mContext, holder.productIcon, product.getProductIcon(), R.mipmap.ic_launcher);
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
            holder.lotterySchedule.setText("开奖进度" + (product.getLotterySchedule() > 1 ? 100 : 100 * product.getLotterySchedule()) + "%");
            holder.lotteryScheduleProgress.setMax(100);
            holder.lotteryScheduleProgress.setProgress((int)(100*product.getLotterySchedule()));
        }
        else
        {
            Drawable drawable = resources.getDrawable(R.mipmap.ic_launcher);
            SystemTools.loadBackground(holder.productLL, drawable);
        }
        return convertView;
    }

    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.productLL)
        RelativeLayout productLL;
        @Bind(R.id.productIcon)
        ImageView productIcon;
        @Bind(R.id.productTag)
        TextView productTag;
        @Bind(R.id.iconL)
        RelativeLayout iconL;
        @Bind(R.id.productDetailL)
        RelativeLayout productDetailL;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.lotterySchedule)
        TextView lotterySchedule;
        @Bind(R.id.lotteryScheduleProgress)
        ProgressBar lotteryScheduleProgress;
        @Bind(R.id.addBtn)
        TextView addBtn;
    }
}
