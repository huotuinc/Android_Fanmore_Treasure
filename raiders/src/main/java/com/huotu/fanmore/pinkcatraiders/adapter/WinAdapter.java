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
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/17.
 */
public class WinAdapter extends BaseAdapter {
    private List<RaidersModel> raiders;
    private Context mContext;

    public WinAdapter(List<RaidersModel> raiders, Context mContext)
    {
        this.raiders = raiders;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = mContext.getResources();
        convertView = View.inflate(mContext, R.layout.win_log_item, null);
        return convertView;
    }
    class ViewHolder
    {
//        public ViewHolder(View view)
//        {
//            ButterKnife.bind(this, view);
//        }
//        @Bind(R.id.raidersIcon)
//        ImageView raidersIcon;
//        @Bind(R.id.raidersName)
//        TextView raidersName;
//        @Bind(R.id.partnerNo)
//        TextView partnerNo;
//        @Bind(R.id.lotteryScheduleProgress)
//        ProgressBar lotteryScheduleProgress;
//        @Bind(R.id.totalRequired)
//        TextView totalRequired;
//        @Bind(R.id.surplus)
//        TextView surplus;
//        @Bind(R.id.addBtn)
//        TextView addBtn;
//        @Bind(R.id.partnerCount)
//        TextView partnerCount;
//        @Bind(R.id.showPhone)
//        TextView showPhone;
//        @Bind(R.id.winnerName)
//        TextView winnerName;
//        @Bind(R.id.winnerL)
//        RelativeLayout winnerL;
//        @Bind(R.id.winnerNo)
//        TextView winnerNo;
//        @Bind(R.id.luckyNo)
//        TextView luckyNo;
//        @Bind(R.id.announcedTime)
//        TextView announcedTime;
    }
}
