package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public class LoadSwitchImgAdapter extends PagerAdapter {

    private List<String> datas;
    private Context mContext;
    public LoadSwitchImgAdapter(List<String> datas, Context mContext)
    {
        this.datas = datas;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String String = datas.get(position%datas.size());
        View view=View.inflate(mContext, R.layout.fillview,null);
        ImageView image=(ImageView) view.findViewById(R.id.image);
        BitmapLoader.create().displayUrlBanner(mContext, image, String, R.mipmap.banner);
        container.addView(view);
        return view;
    }
}
