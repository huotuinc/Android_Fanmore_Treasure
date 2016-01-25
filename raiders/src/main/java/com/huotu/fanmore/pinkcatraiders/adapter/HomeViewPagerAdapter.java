package com.huotu.fanmore.pinkcatraiders.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;

import java.util.List;

/**
 * 首页无线滚动切换图片适配器
 */
public class HomeViewPagerAdapter extends PagerAdapter {

    private List<AdEntity> datas;
    private Context mContext;
    public HomeViewPagerAdapter(List<AdEntity> datas, Context mContext)
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
        AdEntity adImage = datas.get(position%datas.size());
        View view=View.inflate(mContext, R.layout.fillview,null);
        ImageView image=(ImageView) view.findViewById(R.id.image);
        image.setImageResource(adImage.getImage());

        container.addView(view);
        return view;
    }
}
