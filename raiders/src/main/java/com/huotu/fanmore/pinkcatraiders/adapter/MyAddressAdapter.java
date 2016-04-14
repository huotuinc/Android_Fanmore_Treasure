package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.AddAddressActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/20.
 */
public class MyAddressAdapter extends BaseAdapter {

    private List<MyAddressListModel> lists;
    private Context context;
    private
    Activity aty;
    //0 编辑模式
    //1 结算模式
    private int type;

    public List<MyAddressListModel> getLists() {
        return lists;
    }

    public void setLists(List<MyAddressListModel> lists) {
        this.lists = lists;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MyAddressAdapter(List<MyAddressListModel> lists, Context context, Activity aty, int type) {
        this.lists = lists;
        this.context = context;
        this.aty = aty;
        this.type = type;
    }

    @Override
    public int getCount() {
        return null == lists ? 0 : lists.size();
    }

    @Override
    public Object getItem(int position) {
        return (null == lists || lists.isEmpty()) ? null : lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Resources resources = context.getResources();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.addresslist_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.editBtn.setVisibility(View.GONE);
        SystemTools.loadBackground(holder.editIcon, resources.getDrawable(R.mipmap.editor_icon));
        final MyAddressListModel MyAddressList = lists.get(position);
        if (1 == MyAddressList.getDefaultAddress()) {
            holder.isDefault.setVisibility(View.VISIBLE);
            holder.isDefault.setText("[默认]");
        } else {
            holder.isDefault.setVisibility(View.GONE);
        }
        holder.receiver.setText(MyAddressList.getReceiver());
        holder.mobile.setText(MyAddressList.getMobile());
        holder.details.setText(obtainDetails(MyAddressList.getDetails(), MyAddressList.getCityName()));
        holder.editIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("receiver", MyAddressList.getReceiver());
                bundle.putString("mobile", MyAddressList.getMobile());
                bundle.putString("details", MyAddressList.getDetails());
                bundle.putString("cityName", MyAddressList.getCityName());
                bundle.putLong("addressId", MyAddressList.getAddressId());
                bundle.putInt("defaultAddress", MyAddressList.getDefaultAddress());
                ActivityUtils.getInstance().showActivity(aty, AddAddressActivity.class, bundle);
            }
        });

        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @Bind(R.id.receiver)
        TextView receiver;
        @Bind(R.id.mobile)
        TextView mobile;
        @Bind(R.id.details)
        TextView details;
        @Bind(R.id.editBtn)
        TextView editBtn;
        @Bind(R.id.editIcon)
        TextView editIcon;
        @Bind(R.id.isDefault)
        TextView isDefault;
    }

    private String obtainDetails(String detail, String suffix) {
        String[] suffixs = suffix.split("&");
        if (null == suffix) {
            return detail;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < suffixs.length; i++) {
                builder.append(suffixs[i]);
            }
            return builder.toString() + detail;
        }

    }
}
