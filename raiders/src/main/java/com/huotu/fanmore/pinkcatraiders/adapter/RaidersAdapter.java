package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 夺宝记录数据适配
 */
public class RaidersAdapter extends BaseAdapter {

    private List<RaidersModel> raiders;
    private Context mContext;

    public RaidersAdapter(List<RaidersModel> raiders, Context mContext)
    {
        this.raiders = raiders;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return  null==raiders?0:raiders.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==raiders||raiders.isEmpty())?null:raiders.get(position);
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
            convertView = View.inflate(mContext, R.layout.raiders_log_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=raiders&&!raiders.isEmpty()&&null!=raiders.get(position))
        {
            RaidersModel raider = raiders.get(position);
            BitmapLoader.create().displayUrl(mContext, holder.raidersIcon, raider.getProductIcon(), R.mipmap.ic_launcher);
            if(0==raider.getRaidersType())
            {
                //进行中
                holder.doingL.inflate();
                TextView raidersName = (TextView) convertView.findViewById(R.id.raidersName);
                raidersName.setText(raider.getProductName());
                TextView partnerNo = (TextView) convertView.findViewById(R.id.partnerNo);
                partnerNo.setText("参与期号：" + raider.getPartnerNo());
                ProgressBar lotteryScheduleProgress = (ProgressBar) convertView.findViewById(R.id.lotteryScheduleProgress);
                lotteryScheduleProgress.setMax((int)raider.getTotal());
                lotteryScheduleProgress.setProgress((int) raider.getLotterySchedule());
                TextView totalRequired = (TextView) convertView.findViewById(R.id.totalRequired);
                totalRequired.setText("总需"+raider.getTotal());
                TextView surplus = (TextView) convertView.findViewById(R.id.surplus);
                surplus.setText("剩余："+raider.getSurplus());
                TextView partnerCount = (TextView) convertView.findViewById(R.id.partnerCount);
                partnerCount.setText("本期参与："+raider.getPartnerCount() + "人次");
            }
            else if(1==raider.getRaidersType())
            {
                //已完成
                holder.doneL.inflate();
                TextView raidersName = (TextView) convertView.findViewById(R.id.raidersName);
                raidersName.setText(raider.getProductName());
                TextView partnerNo = (TextView) convertView.findViewById(R.id.partnerNo);
                partnerNo.setText("参与期号：" + raider.getPartnerNo());
                TextView totalRequired = (TextView) convertView.findViewById(R.id.totalRequired);
                totalRequired.setText("总需"+raider.getTotal());
                TextView partnerCount = (TextView) convertView.findViewById(R.id.partnerCount);
                partnerCount.setText("本期参与："+raider.getPartnerCount() + "人次");
                TextView winnerName = (TextView) convertView.findViewById(R.id.winnerName);
                winnerName.setText(raider.getWinner().getWinnerName());
                TextView winnerNo = (TextView) convertView.findViewById(R.id.winnerNo);
                winnerNo.setText("本期参与："+raider.getWinner().getPeriod()+"人次");
                TextView luckyNo = (TextView) convertView.findViewById(R.id.luckyNo);
                luckyNo.setText("幸运号："+raider.getWinner().getLuckyNo());
                TextView announcedTime = (TextView) convertView.findViewById(R.id.announcedTime);
                announcedTime.setText("揭晓时间："+raider.getWinner().getAnnouncedTime());
            }
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
        @Bind(R.id.raidersIcon)
        ImageView raidersIcon;
        @Bind(R.id.doingL)
        ViewStub doingL;
        @Bind(R.id.doneL)
        ViewStub doneL;
    }
}
