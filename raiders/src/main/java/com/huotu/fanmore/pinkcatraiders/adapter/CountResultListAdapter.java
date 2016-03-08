package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.UserNumberModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CountResultListAdapter extends BaseAdapter {

    private List<UserNumberModel> numlist;
    private Context context;
    private
    Handler mHandler;
    public CountResultListAdapter(List<UserNumberModel> numlist, Context context, Handler mHandler)
    {
        this.numlist = numlist;
        this.context = context;
        this.mHandler = mHandler;

    }

    @Override
    public int getCount() {
        return null==numlist?0:numlist.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==numlist||numlist.isEmpty())?null:numlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = context.getResources();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.countresult_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=numlist&&!numlist.isEmpty()&&null!=numlist.get(position)){
            final UserNumberModel list = numlist.get(position);
            holder.buyTime.setText(list.getBuyTime());
            holder.nickName.setText(list.getNickName());
            holder.number.setText(list.getNumber());
        }
        return convertView;
    }
    class ViewHolder {
        public ViewHolder(View view) {ButterKnife.bind(this, view);}
        @Bind(R.id.nickName)
        TextView nickName;
        @Bind(R.id.buyTime)
        TextView buyTime;
        @Bind(R.id.number)
        TextView number;
    }
}
