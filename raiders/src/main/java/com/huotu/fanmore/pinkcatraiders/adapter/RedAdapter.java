package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketsModel;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;

import java.util.List;

import butterknife.Bind;
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
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=redPacketsModels&&!redPacketsModels.isEmpty()&&null!=redPacketsModels.get(position))
        {
            RedPacketsModel redPacket = redPacketsModels.get(position);
            holder.minusMoney.setText ( String.valueOf ( redPacket.getMinusMoney () ) );
            holder.fullMoney.setText ( "满"+redPacket.getFullMoney ()+"元可用" );
            holder.redName.setText ( redPacket.getTitle () );
            holder.startTime.setText ( "生效期："+ DateUtils.transformDataformat6 ( redPacket.getStartTime ( ) ) );
            holder.endTime.setText ( "有效期："+ DateUtils.transformDataformat6 ( redPacket.getEndTime ( ) ) );
            holder.remark.setText ( redPacket.getRemark () );
            if(DateUtils.isExpired ( redPacket.getEndTime ( ) ))
            {
                holder.goneicon.setVisibility ( View.VISIBLE );
            }
            else
            {
                holder.goneicon.setVisibility ( View.GONE );
            }
        }
        return convertView;
    }
    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @Bind ( R.id.minusMoney )
        TextView minusMoney;
        @Bind ( R.id.fullMoney )
        TextView fullMoney;
        @Bind ( R.id.redName )
        TextView redName;
        @Bind ( R.id.startTime )
        TextView startTime;
        @Bind ( R.id.endTime )
        TextView endTime;
        @Bind ( R.id.remark )
        TextView remark;
        @Bind ( R.id.goneicon )
        ImageView goneicon;
    }
}
