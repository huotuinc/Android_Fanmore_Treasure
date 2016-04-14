package com.huotu.fanmore.pinkcatraiders.uitls;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;

/**
 * 
 * @类名称：BitmapLoader
 * @类描述：加载图片
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月2日 上午9:26:12
 * @修改备注：
 * @version:
 */
public class BitmapLoader
{

    private static BitmapLoader instance;

    public synchronized static BitmapLoader create()
    {
        if (instance == null)
        {
            instance = new BitmapLoader();
        }
        return instance;
    }

    private BitmapLoader()
    {
        
    }

    /**
     * 
     * @方法描述：采用volly加载网络图片
     * @方法名：displayUrl
     * @参数：@param context 上下文环境
     * @参数：@param imageView 图片空间
     * @参数：@param imageUrl url地址
     * @参数：@param initImg 初始化图片
     * @参数：@param errorImg 错误图片
     * @返回：void
     * @exception
     * @since
     */
    public void displayUrl(Context context, NetworkImageView imageView,
            String imageUrl)
    {
        displayUrl(context, imageView, imageUrl , R.mipmap.defluat_logo, R.mipmap.defluat_logo);
    }

    //加载圆形图片
    public void loadRoundImage(Context context, final CircleImageView imageView, String logoUrl, final int errorImg)
    {
        VolleyUtil.getImageLoader(context).get(logoUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null && imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(errorImg);
            }
        });
    }

    public void displayUrl(Context context, final ImageView imageView, String logoUrl, final int errorImg)
    {
        VolleyUtil.getImageLoader(context).get(logoUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null && imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(errorImg);
            }
        }, 0, 0 , ImageView.ScaleType.FIT_XY);
    }

    public void displayUrlNewest(Context context, final ImageView imageView, String logoUrl, final int errorImg)
    {
        VolleyUtil.getImageLoader(context).get(logoUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null && imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(errorImg);
            }
        }, 0, 0 , ImageView.ScaleType.CENTER_INSIDE);
    }

    public void displayUrlProductGride(Context context, final ImageView imageView, String logoUrl, final int errorImg)
    {
        VolleyUtil.getImageLoader(context).get(logoUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null && imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(errorImg);
            }
        }, 0, 0 , ImageView.ScaleType.CENTER_INSIDE);
    }

    public void displayUrlBanner(final Context context, final ImageView imageView, String logoUrl, final int errorImg)
    {
        VolleyUtil.getImageLoader(context).get(logoUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null && imageContainer.getBitmap() != null) {

                    imageView.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(errorImg);
            }
        }, 0, 0 , ImageView.ScaleType.CENTER_INSIDE);
    }

    /**
     * 
     * @方法描述：采用volly加载网络图片
     * @方法名：displayUrl
     * @参数：@param context 上下文环境
     * @参数：@param imageView 图片空间
     * @参数：@param imageUrl url地址
     * @参数：@param initImg 初始化图片
     * @参数：@param errorImg 错误图片
     * @返回：void
     * @exception
     * @since
     */
    public void displayUrl(Context context, NetworkImageView imageView,
            String imageUrl, int initImg, int errorImg)
    {        
        
        if( null == imageUrl || imageUrl.length()<1 ||  Uri.parse(imageUrl).getHost()==null){
            imageView.setDefaultImageResId(initImg);
            imageView.setImageUrl("",null);
            imageView.setBackgroundResource(initImg);
            return;
        } 

        imageView.setBackgroundResource(0);
        ImageLoader imageLoader = VolleyUtil.getImageLoader(context);
        imageView.setErrorImageResId(errorImg);
        imageView.setImageUrl(imageUrl, imageLoader);
    }

    public void displayOrderUrl(Context context, final ImageView imageView, String logoUrl, final int errorImg)
    {
        VolleyUtil.getImageLoader(context).get(logoUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null && imageContainer.getBitmap() != null) {
                    SystemTools.loadBackground(imageView, new BitmapDrawable(imageContainer.getBitmap()));
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(errorImg);
            }
        }, 0, 0 , ImageView.ScaleType.FIT_XY);
    }
}
