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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AddressModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.AddressPopWin;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择地址数据适配器
 */
public
class SelectAddressAdapter extends BaseAdapter {

    private
    Handler mHandler;

    private
    BaseApplication application;

    private
    Context context;

    private
    List< AddressModel > addresses;

    private int type;
    private
    AddressPopWin popWin;


    public
    SelectAddressAdapter ( Context context, Handler mHandler, List< AddressModel > addresses, int type, BaseApplication application, AddressPopWin popWin ) {

        this.addresses = addresses;
        this.mHandler = mHandler;
        this.context = context;
        this.type = type;
        this.application = application;
        this.popWin = popWin;
    }

    @Override
    public
    int getCount ( ) {

        return null==addresses?0:addresses.size();
    }

    @Override
    public
    Object getItem ( int position ) {

        return (null==addresses||addresses.isEmpty())?null:addresses.get(position);
    }

    @Override
    public
    long getItemId ( int position ) {

        return position;
    }

    @Override
    public
    View getView ( int position, View convertView, ViewGroup parent ) {

        ViewHolder holder = null;
        Resources resources = context.getResources();
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.select_address_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=addresses&&!addresses.isEmpty()&&null!=addresses.get(position))
        {
            final AddressModel address = addresses.get(position);
            holder.addressName.setText ( address.getAddressName () );
            holder.addressL.setOnClickListener ( new View.OnClickListener ( ) {

                                                     @Override
                                                     public
                                                     void onClick ( View v ) {
                                                         popWin.dismissView ();
                                                         Message msg = new Message ( );
                                                         msg.what = Contant.SELECT_ADDRESS;
                                                         msg.obj = address.getAddressName ();
                                                         msg.arg1 = type;
                                                         mHandler.sendMessage ( msg );
                                                     }
                                                 } );
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
            ButterKnife.bind ( this, view );
        }

        @Bind ( R.id.addressL )
        RelativeLayout addressL;
        @Bind ( R.id.addressName )
        TextView addressName;
    }
}
