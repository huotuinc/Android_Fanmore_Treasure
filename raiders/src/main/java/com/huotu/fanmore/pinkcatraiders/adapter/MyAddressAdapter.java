package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/20.
 */
public class MyAddressAdapter extends BaseAdapter {

    private List<MyAddressListModel> lists;
    private Context context;
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

    public MyAddressAdapter(List<MyAddressListModel> lists, Context context, int type) {
        this.lists = lists;
        this.context = context;

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
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }
    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
