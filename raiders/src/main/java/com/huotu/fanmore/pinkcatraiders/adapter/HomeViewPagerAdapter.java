package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.model.CarouselModel;
import com.huotu.fanmore.pinkcatraiders.model.SlideListModel;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;

import java.util.List;
/**
 * 首页无线滚动切换图片适配器
 */
public class HomeViewPagerAdapter extends PagerAdapter {

    private List< CarouselModel > datas;

    private
    Handler mHandler;

    private Context mContext;

    public
    HomeViewPagerAdapter ( List<CarouselModel> datas, Context mContext, Handler mHandler ) {

        this.datas = datas;
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    @Override
    public
    int getCount ( ) {

        return Integer.MAX_VALUE;
    }

    @Override
    public
    boolean isViewFromObject ( View view, Object object ) {

        return view == object;
    }

    @Override
    public
    void destroyItem ( ViewGroup container, int position, Object object ) {

        container.removeView ( ( View ) object );
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        String String = datas.get(position%datas.size()).getPictureUrl ();
        View view=View.inflate(mContext, R.layout.fillview,null);
        ImageView image=(ImageView) view.findViewById(R.id.image);
        BitmapLoader.create ( ).displayUrl ( mContext, image, String, R.mipmap.banner );
        container.addView ( view );
        image.setOnClickListener ( new View.OnClickListener ( ) {

                                       @Override
                                       public
                                       void onClick ( View v ) {

                                           Message message = mHandler.obtainMessage ();
                                           message.what = Contant.CAROUSE_URL;
                                           message.obj = datas.get ( position%datas.size() ).getPid ();
                                           mHandler.sendMessage ( message );
                                       }
                                   } );
        return view;
    }
}
