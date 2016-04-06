package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.MsgData;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 系统消息适配
 */
public class SysMsgAdapter extends BaseAdapter {

    List< MsgData > msgs;

    Context         mContext;

    public
    SysMsgAdapter ( Context mContext, List< MsgData > msgs ) {

        this.mContext = mContext;
        this.msgs = msgs;
    }

    @Override
    public
    int getCount ( ) {

        return msgs.size ( );
    }

    @Override
    public
    Object getItem ( int position ) {

        return msgs.get ( position );
    }

    @Override
    public
    long getItemId ( int position ) {

        return position;
    }

    @Override
    public
    View getView ( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        Resources res = mContext.getResources();
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.sysmsg_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=msgs && msgs.size() > 0)
        {
            holder.msgTime.setText(DateUtils.transformDataformat6(msgs.get(position).getDate()));
            holder.msgCon.setText(msgs.get(position).getContext());
        }
        return convertView;
    }

    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.msgTime)
        TextView msgTime;
        @Bind(R.id.msgCon)
        TextView msgCon;

    }
}
