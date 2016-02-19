package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketsModel;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/18.
 */
public class RedAdapter extends BaseAdapter {
    private List<RedPacketsModel> redPacketsModels;
    private Context mContext;
    public RedAdapter(List<RedPacketsModel> redPacketsModels, Context mContext)
    {
        this.redPacketsModels = redPacketsModels;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
         return  null==redPacketsModels?0:redPacketsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==redPacketsModels||redPacketsModels.isEmpty())?null:redPacketsModels.get(position);
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
            convertView = View.inflate(mContext, R.layout.red_packet_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        return convertView;
    }
    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
