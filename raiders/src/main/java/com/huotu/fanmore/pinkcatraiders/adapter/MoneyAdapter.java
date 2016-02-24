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
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 充值面额适配
 */
public class MoneyAdapter extends BaseAdapter {

    private List<Long> moneys;
    private Context context;
    private Activity aty;
    private int clickTemp = -1;

    public MoneyAdapter(List<Long> moneys, Context context, Activity aty)
    {
        this.moneys = moneys;
        this.context = context;
        this.aty = aty;
    }

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
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
        {if (clickTemp == position) {
            SystemTools.loadBackground ( holder.moneyL, resources.getDrawable ( R.drawable.money_draw2 ) );
            holder.moneyTag.setTextColor ( resources.getColor ( R.color.title_bg ) );
        } else {
            SystemTools.loadBackground ( holder.moneyL, resources.getDrawable ( R.drawable
                                                                                        .money_draw1 ) );
            holder.moneyTag.setTextColor ( resources.getColor ( R.color.text_gray ) );
        }
            holder.moneyTag.setText(String.valueOf ( moneys.get(position)));
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
