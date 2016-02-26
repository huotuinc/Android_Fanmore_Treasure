package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.CategoryListModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.product.AreaActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.CateGoryGoodsListActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/23.
 */
public class CategoryAdapter extends BaseAdapter {

    private List<CategoryListModel> category;
    private Context mContext;

    public CategoryAdapter(List<CategoryListModel> category, Context mContext) {
        this.category = category;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return null == category ? 0 : category.size();
    }

    @Override
    public Object getItem(int position) {
        return (null == category || category.isEmpty()) ? null : category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = mContext.getResources();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.cate_gory_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (null != category && !category.isEmpty() && null != category.get(position)) {
            final CategoryListModel CategoryList = category.get(position);

            holder.title.setText(CategoryList.getTitle());
            holder.categoryL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("categoryId", CategoryList.getPid());
                    bundle.putString("title",CategoryList.getTitle());
                    ActivityUtils.getInstance().showActivity((Activity)mContext, CateGoryGoodsListActivity.class, bundle);
                }
            });
        } else {

        }
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.categoryL)
        LinearLayout categoryL;
        @Bind(R.id.title)
        TextView title;
    }
}