package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 专区产品数据适配器
 */
public class AreaProductAdapter extends BaseAdapter {

    private List<ProductModel> products;
    private Context mContext;
    private
    Handler mHandler;
    public AreaProductAdapter(Handler mHandler,List<ProductModel> products, Context mContext)
    {
        this.mHandler = mHandler;
        this.products = products;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return null==products?0:products.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==products||products.isEmpty())?null:products.get(position);
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
            convertView = View.inflate(mContext, R.layout.area_product_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=products&&!products.isEmpty()&&null!=products.get(position))
        {
            final ProductModel product = products.get(position);
            BitmapLoader.create().displayUrl(mContext, holder.icon, product.getPictureUrl(), R.mipmap.defluat_logo);
            if(0!=product.getAreaAmount())
            {
                holder.productTag.setText("专区\n商品");
                SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_1));
            }

            holder.productName.setText(product.getTitle());
            holder.lotteryScheduleProgress.setMax((int) product.getToAmount());
            holder.lotteryScheduleProgress.setProgress((int) (product.getToAmount() - product.getRemainAmount()));
            holder.totalRequired.setText("总需：" + product.getToAmount());
            holder.surplus.setText("剩余："+ product.getRemainAmount());
            holder.addBtn.setOnClickListener (new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Message message = mHandler.obtainMessage();
                    message.what = Contant.ADD_LIST;
                    message.obj = product;
                    mHandler.sendMessage(message);
                }
            });
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
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.productTag)
        TextView productTag;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.totalRequired)
        TextView totalRequired;
        @Bind(R.id.surplus)
        TextView surplus;
        @Bind(R.id.lotteryScheduleProgress)
        ProgressBar lotteryScheduleProgress;
        @Bind(R.id.addBtn)
        TextView addBtn;
    }
}
