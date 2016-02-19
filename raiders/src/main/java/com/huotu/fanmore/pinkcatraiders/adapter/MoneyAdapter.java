package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 充值面额适配
 */
public class MoneyAdapter extends BaseAdapter {

    private List<String> moneys;
    private Context context;
    private Activity aty;

    public MoneyAdapter(List<String> moneys, Context context, Activity aty)
    {
        this.moneys = moneys;
        this.context = context;
        this.aty = aty;
    }

    @Override
    public int getCount() {
        return moneys.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==moneys || moneys.isEmpty())?null:moneys.get(position);
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
            convertView = View.inflate(context, R.layout.money_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=moneys&&!moneys.isEmpty()&&null!=moneys.get(position))
        {
            holder.moneyTag.setText(moneys.get(position));
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
        @Bind(R.id.moneyL)
        RelativeLayout moneyL;
        @Bind(R.id.moneyTag)
        TextView moneyTag;
    }
}
