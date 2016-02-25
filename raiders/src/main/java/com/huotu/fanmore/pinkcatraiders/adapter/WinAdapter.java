package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
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
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
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

    public
    WinAdapter ( List< AppUserBuyFlowModel > winners, Context mContext ) {

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
    View getView ( int position, View convertView, ViewGroup parent ) {

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
            AppUserBuyFlowModel winner = winners.get(position);
            BitmapLoader.create().displayUrl (
                    mContext, holder.raidersIcon, winner
                            .getDefaultPictureUrl ( ), R.mipmap.ic_launcher
                                             );
            holder.raidersName.setText ( winner.getTitle ( ) );
            holder.partnerNo.setText ( "参与期号：" + winner.getIssueId ( ) );
            holder.totalRequired.setText ( "总需："+winner.getToAmount ( ) );
            holder.showPhone.setText ( String.valueOf ( winner.getLuckyNumber () ));
            holder.benqicanyu.setText ( "本期参与人数："+ winner.getAmount () );
            holder.jiexiaoshijian.setText ( DateUtils.transformDataformat6 ( winner.getAwardingDate () ) );
            holder.addBtn.setOnClickListener ( new View.OnClickListener ( ) {

                                                   @Override
                                                   public
                                                   void onClick ( View v ) {

                                                   }
                                               } );
        }
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view)
        {
           ButterKnife.bind(this, view);
        }

        @Bind ( R.id.raidersIcon )
        ImageView raidersIcon;
        @Bind ( R.id.raidersName )
        TextView raidersName;
        @Bind ( R.id.partnerNo )
        TextView partnerNo;
        @Bind ( R.id.totalRequired )
        TextView totalRequired;
        @Bind ( R.id.addBtn )
        TextView addBtn;
        @Bind ( R.id.partnerCount )
        TextView partnerCount;
        @Bind ( R.id.showPhone )
        TextView showPhone;
        @Bind ( R.id.benqicanyu )
        TextView benqicanyu;
        @Bind ( R.id.jiexiaoshijian )
        TextView jiexiaoshijian;
    }
}
