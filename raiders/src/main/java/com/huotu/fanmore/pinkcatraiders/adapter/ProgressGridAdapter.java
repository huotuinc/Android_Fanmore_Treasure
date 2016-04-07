package com.huotu.fanmore.pinkcatraiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.product.ProductDetailActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.MyGridView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 产品进度加载适配器
 */
public class ProgressGridAdapter  extends BaseAdapter {

    private List<ProductModel> productModels;
    private Context mContext;
    private Activity aty;
    private
    Handler mHandler;

    public ProgressGridAdapter(List<ProductModel> productModels, Context mContext, Activity aty, Handler mHandler)
    {
        this.productModels = productModels;
        this.mContext = mContext;
        this.aty = aty;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        return productModels.size();
    }

    @Override
    public Object getItem(int position) {
        return (null==productModels || productModels.isEmpty())?null:productModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(parent instanceof MyGridView) {
            ViewHolder holder = null;
            Resources resources = mContext.getResources();
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.product_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if(((MyGridView)parent).isMeasure())
            {
                //在measure
                return convertView;
            }else {
                if (null != productModels && !productModels.isEmpty() && null != productModels.get(position)) {
                    final ProductModel product = productModels.get(position);
                    BitmapLoader.create().displayUrlProductGride(mContext, holder.productIcon, product.getPictureUrl(), R.mipmap.defluat_logo);
                    if (0 != product.getAreaAmount()) {
                        holder.productTag.setText("专区\n商品");
                        SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_1));
                    } else {
                        holder.productTag.setVisibility(View.GONE);
                    }

                    holder.productName.setText(product.getTitle());
                    BigDecimal decimal = new BigDecimal((product.getToAmount() - product.getRemainAmount()) / (double) product.getToAmount());
                    double value = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    double iValue = 100 * value;
                    DecimalFormat df = new DecimalFormat("#");
                    if (iValue < 1 && iValue > 0) {
                        holder.lotterySchedule.setText("开奖进度 1%");
                    } else if (0 == iValue) {
                        holder.lotterySchedule.setText("开奖进度 0%");
                    } else if (iValue > 100) {
                        holder.lotterySchedule.setText("开奖进度 100%");
                    } else {
                        holder.lotterySchedule.setText("开奖进度" + df.format(iValue) + "%");
                    }
                    holder.lotteryScheduleProgress.setMax((int) product.getToAmount());
                    holder.lotteryScheduleProgress.setProgress((int) (product.getToAmount() -
                            product.getRemainAmount()));

                    holder.iconL.setOnClickListener(
                            new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("tip", 1);
                                    bundle.putSerializable("product", product);
                                    //跳转到商品详情界面
                                    ActivityUtils.getInstance().showActivity(aty, ProductDetailActivity.class, bundle);
                                }
                            }
                    );
                    holder.addBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Message message = mHandler.obtainMessage();
                            message.what = Contant.ADD_LIST;
                            message.obj = product;
                            mHandler.sendMessage(message);
                        }
                    });
                } else {
                    Drawable drawable = resources.getDrawable(R.mipmap.error);
                    SystemTools.loadBackground(holder.productLL, drawable);
                }
                return convertView;
            }
        }
        else
        {
            return null;
        }
    }

    class ViewHolder
    {
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.productLL)
        RelativeLayout productLL;
        @Bind(R.id.productIcon)
        ImageView productIcon;
        @Bind(R.id.productTag)
        TextView productTag;
        @Bind(R.id.iconL)
        RelativeLayout iconL;
        @Bind(R.id.productDetailL)
        RelativeLayout productDetailL;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.lotterySchedule)
        TextView lotterySchedule;
        @Bind(R.id.lotteryScheduleProgress)
        ProgressBar lotteryScheduleProgress;
        @Bind(R.id.addBtn)
        TextView addBtn;
    }
}
