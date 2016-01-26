package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;

import org.w3c.dom.Text;

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
            try {
                RaidersModel raider = raiders.get(position);
                BitmapLoader.create().displayUrl(mContext, holder.raidersIcon, raider.getProductIcon(), R.mipmap.ic_launcher);
                if (0 == raider.getRaidersType()) {
                    //进行中
                    holder.lotteryScheduleProgress.setVisibility(View.VISIBLE);
                    holder.winnerL.setVisibility(View.GONE);
                    holder.addBtn.setVisibility(View.VISIBLE);
                    holder.raidersName.setText(raider.getTitle());
                    holder.partnerNo.setText("参与期号：" + raider.getIssueId());
                    holder.lotteryScheduleProgress.setMax((int) raider.getToAmount());
                    holder.lotteryScheduleProgress.setProgress((int) (raider.getToAmount() - raider.getRemainAmount()));
                    holder.totalRequired.setText("总需" + raider.getToAmount());
                    holder.surplus.setText("剩余：" + raider.getRemainAmount());
                    holder.partnerCount.setText("本期参与：" + raider.getAttendAmount() + "人次");
                } else if (1 == raider.getRaidersType()) {
                    holder.winnerL.setVisibility(View.VISIBLE);
                    holder.lotteryScheduleProgress.setVisibility(View.GONE);
                    holder.addBtn.setVisibility(View.GONE);
                    //已完成
                    holder.raidersName.setText(raider.getTitle());
                    holder.partnerNo.setText("参与期号：" + raider.getIssueId());
                    holder.totalRequired.setText("总需" + raider.getToAmount());
                    holder.partnerCount.setText("本期参与：" + raider.getAttendAmount() + "人次");
                    holder.winnerName.setText(raider.getWinner());
                    holder.winnerNo.setText("本期参与：" + raider.getWinnerAttendAmount() + "人次");
                    holder.luckyNo.setText("幸运号：" + raider.getLunkyNumber());
                    holder.announcedTime.setText("揭晓时间：" + DateUtils.transformDataformat1(raider.getAwardingDate()));
                }
            }catch (Exception e)
            {
                ToastUtils.showLongToast(mContext, "列表数据加载失败");
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
        @Bind(R.id.raidersName)
        TextView raidersName;
        @Bind(R.id.partnerNo)
        TextView partnerNo;
        @Bind(R.id.lotteryScheduleProgress)
        ProgressBar lotteryScheduleProgress;
        @Bind(R.id.totalRequired)
        TextView totalRequired;
        @Bind(R.id.surplus)
        TextView surplus;
        @Bind(R.id.addBtn)
        TextView addBtn;
        @Bind(R.id.partnerCount)
        TextView partnerCount;
        @Bind(R.id.showPhone)
        TextView showPhone;
        @Bind(R.id.winnerName)
        TextView winnerName;
        @Bind(R.id.winnerL)
        RelativeLayout winnerL;
        @Bind(R.id.winnerNo)
        TextView winnerNo;
        @Bind(R.id.luckyNo)
        TextView luckyNo;
        @Bind(R.id.announcedTime)
        TextView announcedTime;
    }
}
