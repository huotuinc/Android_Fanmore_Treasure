package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.os.Handler;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;

import com.huotu.fanmore.pinkcatraiders.model.PastListModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/29.
 */
public class PastListAdapter extends BaseAdapter {
    private List<PastListModel> pastlist;
    private Context mContext;
    private
    Handler mHandler;
    public PastListAdapter(Handler mHandler,List<PastListModel> pastlist, Context mContext)
    {
        this.mHandler = mHandler;
        this.pastlist = pastlist;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return null==pastlist?0:pastlist.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==pastlist||pastlist.isEmpty())?null:pastlist.get(position);
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
            convertView = View.inflate(mContext, R.layout.past_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=pastlist&&!pastlist.isEmpty()&&null!=pastlist.get(position))
        {
            final PastListModel product = pastlist.get(position);

            if(1==product.getStatus())
            {
                holder.LL1.setVisibility(View.GONE);
                holder.issueId.setText("期号"+product.getIssueId()+"请稍后,正在揭晓......");
            }
            else
            {
                if (TextUtils.isEmpty(product.getUserHeadUrl())||null==product.getUserHeadUrl()){
                    BitmapLoader.create().displayUrl(mContext, holder.userlogo, "http://image.baidu.com/search/detail?ct=503316480&tn=baiduimagedetail&statnum=head&ipn=d&z=0&s=0&ic=0&lm=-1&itg=1&cg=head&word=%E5%8F%AF%E7%88%B1%E5%9B%BE%E7%89%87%E5%A4%B4%E5%83%8F&ie=utf-8&in=3354&cl=2&st=&pn=17&rn=1&di=106150154000&ln=1000&&fmq=1378374347070_R&se=&sme=0&tab=&face=&&cs=1381935472,3771048222&simid=3543930211,329439139&adpicid=undefined&pi=0&os=649894240,2600133867&istype=&ist=&jit=&objurl=http%3A%2F%2Fwww.wosoni.com%2Fwww.wosoni.com%2Fpic.wenwen.soso.com%2Fp%2F20111001%2F20111001151539-984477232.jpg", R.mipmap.error);
                }else {
                    BitmapLoader.create().displayUrl(mContext, holder.userlogo, product.getUserHeadUrl(), R.mipmap.error);
                }
                holder.issueId.setText("期号" + product.getIssueId() + "(揭晓时间:" + DateUtils.transformDataformat2(product.getDate()) + ")");
                holder.nickName.setText(product.getNickName());
                holder.ip.setText("(" + product.getIp() + ")");
                holder.userId.setText("用户ID:" + product.getUserId());
                holder.luckyNumber.setText(String.valueOf(product.getLuckyNumber()));
                holder.attendAmount.setText(String.valueOf(product.getAttendAmount()));

            }



        }
        return convertView;
    }
    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.userlogo)
        CircleImageView userlogo;
        @Bind(R.id.issueId)
        TextView issueId;
        @Bind(R.id.nickName)
        TextView nickName;
        @Bind(R.id.ip)
        TextView ip;
        @Bind(R.id.userId)
        TextView userId;
        @Bind(R.id.luckyNumber)
        TextView luckyNumber;
        @Bind(R.id.attendAmount)
        TextView attendAmount;
        @Bind(R.id.LL1)
        LinearLayout LL1;

    }
}
