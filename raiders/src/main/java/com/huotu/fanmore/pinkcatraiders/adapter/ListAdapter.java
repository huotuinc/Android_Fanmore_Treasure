package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.AddAndSubView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 清单列表数据适配器
 */
public class ListAdapter extends BaseAdapter {

    private List<ListModel> lists;
    private Context context;
    //0 编辑模式
    //1 结算模式
    private int type;

    public ListAdapter(List<ListModel> lists, Context context, int type)
    {
        this.lists = lists;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return null==lists?0:lists.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==lists||lists.isEmpty())?null:lists.get(position);
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
        if(null!=lists&&!lists.isEmpty()&&null!=lists.get(position))
        {
            ProductModel list = lists.get(position);
            BitmapLoader.create().displayUrl(context, holder.listProductIcon, list.getPictureUrl(), R.mipmap.ic_launcher);
            if(10==list.getAreaAmount())
            {
                holder.productTag.setText("十元\n专区");
                SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_1));
            }
            else if(5==list.getAreaAmount())
            {
                holder.productTag.setText("五元\n专区");
                SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_2));
            }

            holder.listProductName.setText(list.getTitle());
            if(0==type)
            {
                //编辑模式
                SystemTools.loadBackground(holder.editBtn, resources.getDrawable(R.mipmap.unselect));
            }
            else if(1==type)
            {
                holder.editBtn.setVisibility(View.GONE);
            }
            holder.totalRequired.setText("总需" + list.getToAmount() + "人次");
            holder.surplusRequired.setText("剩余" + list.getRemainAmount() + "人次");
            holder.addAndSub.setViewsLayoutParm((int) resources.getDimension(R.dimen.add_sub_width), (int) resources.getDimension(R.dimen.add_sub_height));
            holder.addAndSub.setButtonBgDrawable(resources.getDrawable(R.drawable.add_sub_bg), resources.getDrawable(R.drawable.add_sub_bg));
            holder.addAndSub.setNum((int)list.getStepAmount());
            if(1==list.getStepAmount())
            {
                holder.stepTag.setVisibility(View.GONE);
            }
            else
            {
                holder.stepTag.setText("参与人次需是"+list.getStepAmount()+"的倍数");
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
        @Bind(R.id.editBtn)
        TextView editBtn;
        @Bind(R.id.listProductIcon)
        ImageView listProductIcon;
        @Bind(R.id.productTag)
        TextView productTag;
        @Bind(R.id.listProductName)
        TextView listProductName;
        @Bind(R.id.totalRequired)
        TextView totalRequired;
        @Bind(R.id.surplusRequired)
        TextView surplusRequired;
        @Bind(R.id.partnerTag)
        TextView partnerTag;
        @Bind(R.id.addAndSub)
        AddAndSubView addAndSub;
        @Bind(R.id.stepTag)
        TextView stepTag;
    }
}
