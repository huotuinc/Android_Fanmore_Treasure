package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.huotu.fanmore.pinkcatraiders.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 信息选择适配器
 */
public class MsgAdapter extends BaseAdapter {

    List<String> datas;
    Context mContext;

    public MsgAdapter(List<String> datas, Context mContext)
    {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources res = mContext.getResources();
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.msg_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=datas && datas.size() > 0)
        {
            holder.msgItem.setText(datas.get(position).toString());
        }

        return convertView;
    }

    class ViewHolder
    {
        private Animation animation;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }

        @Bind(R.id.msgItem)
        TextView msgItem;
    }
}
