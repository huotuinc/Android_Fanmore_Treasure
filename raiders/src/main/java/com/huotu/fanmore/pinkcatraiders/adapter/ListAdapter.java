package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.CartModel;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
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
    private
    Handler mHandler;
    private int type;
    private long buyNum = 0;

    public ListAdapter(List<ListModel> lists, Context context, Handler mHandler, int type)
    {
        this.lists = lists;
        this.context = context;
        this.mHandler = mHandler;
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
            final ListModel list = lists.get(position);
            //禁止手动输入
            holder.num.setInputType(InputType.TYPE_NULL);
            BitmapLoader.create().displayUrl(context, holder.listProductIcon, list.getPictureUrl(), R.mipmap.error);
            if(0!=list.getAreaAmount())
            {
                holder.productTag.setText("专区\n商品");
                SystemTools.loadBackground(holder.productTag, resources.getDrawable(R.mipmap.area_1));
            }
            else
            {
                holder.productTag.setVisibility(View.GONE);
            }

            holder.listProductName.setText(list.getTitle());
            if(0==type)
            {
                holder.editBtn.setVisibility(View.GONE);
                //结算模式
                //选择项目列表
                Message message = mHandler.obtainMessage ( );
                message.what = Contant.CART_SELECT;
                message.arg1 = 0;
                message.obj = lists;
                mHandler.sendMessage ( message );
            }
            else if(1==type)
            {
                //编辑模式
                final TextView editBtn = holder.editBtn;
                final Drawable draw1 = resources.getDrawable ( R.mipmap.unselect );
                final Drawable draw2 = resources.getDrawable ( R.mipmap.unselected );
                editBtn.setTag ( 0 );
                SystemTools.loadBackground ( holder.editBtn, draw1 );
                editBtn.setOnClickListener (
                        new View.OnClickListener ( ) {

                            @Override
                            public
                            void onClick ( View v ) {

                                Message message = mHandler.obtainMessage ( );
                                if ( 0 == editBtn.getTag ( ) ) {
                                    //添加
                                    message.arg2 = 0;
                                    editBtn.setTag ( 1 );
                                    SystemTools.loadBackground (
                                            editBtn, draw2
                                    );

                                }
                                else if ( 1 == editBtn.getTag ( ) ) {
                                    //删除
                                    message.arg2 = 1;
                                    editBtn.setTag ( 0 );
                                    SystemTools.loadBackground (
                                            editBtn, draw1
                                    );
                                }

                                //选择项目
                                message.what = Contant.CART_SELECT;
                                message.arg1 = 1;
                                message.obj = list;
                                mHandler.sendMessage ( message );
                            }
                        }
                );
            }
            holder.totalRequired.setText("总需" + list.getToAmount() + "人次");
            holder.surplusRequired.setText("剩余" + list.getRemainAmount() + "人次");
            //加减控件
            final EditText numView = holder.num;
            //数量

            buyNum = list.getUserBuyAmount();
            numView.setText(String.valueOf(buyNum));
            //加
            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取数据源
                    String numString = numView.getText().toString();
                    if (numString == null || numString.equals("")) {
                        buyNum = 1;
                        numView.setText("1");
                    } else
                    {
                        if ((buyNum+list.getStepAmount()) < 1) // 先加，再判断
                        {
                            buyNum  = buyNum-list.getStepAmount();
                            ToastUtils.showShortToast(context, "亲，数量至少为" + list.getStepAmount() + "哦~");
                            numView.setText(String.valueOf ( list.getStepAmount()));
                        }
                        else if((buyNum+list.getStepAmount()) > list.getToAmount())
                        {
                            buyNum  = buyNum-list.getStepAmount();
                            ToastUtils.showShortToast(context, "亲，数量不能超过" + list.getToAmount() + "哦~");
                            numView.setText(String.valueOf(buyNum));
                        }
                        else
                        {
                            buyNum=buyNum+list.getStepAmount();
                            numView.setText(String.valueOf(buyNum));
                            list.setUserBuyAmount(buyNum);
                            Message message = mHandler.obtainMessage ( );
                            message.what = Contant.CART_SELECT;
                            message.arg1 = 2;
                            message.obj = lists;
                            mHandler.sendMessage(message);
                        }
                    }
                }
            });
            //减
            holder.subBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //获取数据源
                    String numString = numView.getText().toString();
                    if (numString == null || numString.equals("")) {
                        buyNum = 1;
                        numView.setText("1");
                    }
                    else if((buyNum-list.getStepAmount()) > list.getToAmount())
                    {
                        buyNum  = buyNum+list.getStepAmount();
                        ToastUtils.showShortToast(context, "亲，数量不能超过" + list.getToAmount() + "哦~");
                        numView.setText(String.valueOf(buyNum));
                    }
                    else
                    {

                        if ((buyNum-list.getStepAmount()) < 1) // 先减，再判断
                        {
                            buyNum=buyNum+list.getStepAmount();
                            ToastUtils.showShortToast(context, "亲，数量至少为"+list.getStepAmount()+"哦~");
                            numView.setText(String.valueOf ( list.getStepAmount()));
                        } else
                        {
                            buyNum = buyNum-list.getStepAmount();
                            numView.setText(String.valueOf(buyNum));
                            list.setUserBuyAmount(buyNum);
                            Message message = mHandler.obtainMessage ( );
                            message.what = Contant.CART_SELECT;
                            message.arg1 = 3;
                            message.obj = lists;
                            mHandler.sendMessage(message);
                        }
                    }
                }
            });

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
        @Bind(R.id.stepTag)
        TextView stepTag;
        //购物车加减控件
        @Bind(R.id.addBtn)
        TextView addBtn;
        @Bind(R.id.num)
        EditText num;
        @Bind(R.id.subBtn)
        TextView subBtn;
    }
}
