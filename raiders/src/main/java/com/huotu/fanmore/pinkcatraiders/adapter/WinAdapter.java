package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.AppUserBuyFlowModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketsModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.ShareOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.WinLogDetailActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 中奖记录数据适配
 */
public class WinAdapter extends BaseAdapter {

    private List< AppUserBuyFlowModel> winners;

    private Context                     mContext;
    private Activity aty;

    public
    WinAdapter ( List< AppUserBuyFlowModel > winners, Context mContext,Activity aty ) {
        this.aty=aty;

        this.winners = winners;
        this.mContext = mContext;
    }

    @Override
    public
    int getCount ( ) {

        return (null==winners || winners.isEmpty())?0:winners.size ();
    }

    @Override
    public
    Object getItem ( int position ) {

        return (null==winners || winners.isEmpty())?null:winners.get(position);
    }

    @Override
    public
    long getItemId ( int position ) {

        return position;
    }

    @Override
    public
    View getView ( final int position, View convertView, ViewGroup parent ) {

        ViewHolder holder = null;
        Resources resources = mContext.getResources();
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.win_log_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=winners&&!winners.isEmpty()&&null!=winners.get(position))
        {
            BitmapLoader.create().displayUrl(
                    mContext, holder.pictureUrl, winners.get(position)
                            .getDefaultPictureUrl(), R.mipmap.defluat_logo
            );
            holder.title.setText(winners.get(position).getTitle());
            holder.issueId.setText("参与期号：" + winners.get(position).getIssueId());
            holder.toAmount.setText ( "总需："+winners.get(position).getToAmount() );
            holder.lunkyNumber.setText ( String.valueOf ( winners.get(position).getLuckyNumber() ));
            holder.attendAmount.setText ( "本期参与人数："+ winners.get(position).getAmount() );
            holder.awardingDate.setText("揭晓时间：" + DateUtils.transformDataformat6(winners.get(position).getAwardingDate()));
            //根据状态获取操作凭据
            if(0==winners.get(position).getDeliveryStatus()) {
                //获取商品
                holder.addBtn.setText("确认收货地址");
                holder.addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("winner", winners.get(position));
                        ActivityUtils.getInstance().showActivity(aty, WinLogDetailActivity.class, bundle);
                    }
                });
            }
            else if(1==winners.get(position).getDeliveryStatus())
            {
                holder.addBtn.setText("查看奖品派发状态");
                holder.addBtn.setTextColor(resources.getColor(R.color.color_white));
                SystemTools.loadBackground(holder.addBtn, resources.getDrawable(R.drawable.button_common_4));
                holder.addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("winner", winners.get(position));
                        ActivityUtils.getInstance().showActivity(aty, WinLogDetailActivity.class, bundle);
                    }
                });
            }
            else if(2==winners.get(position).getDeliveryStatus())
            {
                holder.addBtn.setText("确认收货");
                holder.addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("winner", winners.get(position));
                        ActivityUtils.getInstance().showActivity(aty, WinLogDetailActivity.class, bundle);
                    }
                });
            }
            else if(5==winners.get(position).getDeliveryStatus())
            {
                holder.addBtn.setText("去晒单");
                holder.addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("winner", winners.get(position));
                        ActivityUtils.getInstance().showActivity(aty, ShareOrderActivity.class, bundle);
                    }
                });
            }
            else if(6==winners.get(position).getDeliveryStatus())
            {
                holder.addBtn.setText("已晒单");
                holder.addBtn.setTextColor(resources.getColor(R.color.color_white));
                SystemTools.loadBackground(holder.addBtn, resources.getDrawable(R.drawable.button_common_4));
            }
        }
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view)
        {
           ButterKnife.bind(this, view);
        }

        @Bind ( R.id.pictureUrl )
        ImageView pictureUrl;
        @Bind ( R.id.title )
        TextView title;
        @Bind ( R.id.issueId )
        TextView issueId;
        @Bind ( R.id.toAmount )
        TextView toAmount;
        @Bind ( R.id.addBtn )
        TextView addBtn;
        @Bind ( R.id.lunkyNumber )
        TextView lunkyNumber;
        @Bind ( R.id.attendAmount )
        TextView attendAmount;
        @Bind ( R.id.awardingDate )
        TextView awardingDate;
    }
}
