package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.NewestProduct;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 最新揭晓列表适配
 */
public class NewestProductAdapter extends BaseAdapter {

    private List<NewestProduct> newestProducts;
    private Context context;
    public NewestProductAdapter(List<NewestProduct> newestProducts, Context context)
    {
        this.newestProducts = newestProducts;
        this.context = context;
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
            final NewestProduct product = newestProducts.get(position);
            BitmapLoader.create().displayUrl(context, holder.newestProductIcon, product.getPictureUrl(), R.mipmap.ic_launcher);
            if(0==product.getAreaAmount())
            {
                holder.newestProductTag.setText("十元\n专区");
                SystemTools.loadBackground(holder.newestProductTag, resources.getDrawable(R.mipmap.area_1));
            }
            else if(1==product.getAreaAmount())
            {
                holder.newestProductTag.setText("五元\n专区");
                SystemTools.loadBackground(holder.newestProductTag, resources.getDrawable(R.mipmap.area_2));
            }
            else
            {
                holder.newestProductTag.setVisibility(View.GONE);
            }

            holder.newestProductName.setText(product.getTitle());
            holder.newestIssue.setText("期号："+product.getIssueId());
            holder.announcedTag.setText("即将揭晓");
            holder.countdown.setText("09:32:46");
        }
        return convertView;
    }

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
    }
}
