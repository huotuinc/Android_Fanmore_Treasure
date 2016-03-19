package com.huotu.fanmore.pinkcatraiders.uitls;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.CartCountModel;
import com.huotu.fanmore.pinkcatraiders.model.CartDataModel;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.ListOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.LocalCartOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车操作类
 */
public class CartUtils {

    public static void addCartDone(final BaseModel entiy, String issueId, final ProgressPopupWindow progress, BaseApplication application, final Context context, Handler mHandler)
    {
        //判断是否登陆
        if(!application.isLogin())
        {
            ListModel listModel = new ListModel();
            String data = new String();
            LocalCartOutputModel localCartOutput = null;
            CartDataModel cartData = CartDataModel.findById(CartDataModel.class, 1000l);
            //本地维护购物车信息
            if(entiy instanceof ProductModel)
            {

                ProductModel p = (ProductModel) entiy;

                //product 转 listModel
                listModel.setUserBuyAmount(p.getStepAmount());
                listModel.setStepAmount(p.getStepAmount());
                listModel.setPictureUrl(p.getPictureUrl());
                listModel.setTitle(p.getTitle());
                listModel.setToAmount(p.getToAmount());
                listModel.setRemainAmount(p.getRemainAmount());
                listModel.setAreaAmount(p.getAreaAmount());
                listModel.setIssueId(p.getIssueId());
                listModel.setPricePercentAmount(p.getPricePercentAmount());
                listModel.setSid(p.getPid());

                //产品加入购物车
                if(null==cartData)
                {
                    //直接加入购物车
                    localCartOutput = new LocalCartOutputModel();
                    List<ListModel> ls = new ArrayList<ListModel>();
                    LocalCartOutputModel.LocalCartInnerModel innerModel = localCartOutput.new LocalCartInnerModel();
                    ls.add(listModel);
                    innerModel.setLists(ls);
                    localCartOutput.setResultData(innerModel);
                    //
                    JSONUtil<LocalCartOutputModel> jsonUtil = new JSONUtil<LocalCartOutputModel>();
                    data = jsonUtil.toJson(localCartOutput);
                    //保存数据库
                    CartDataModel cartDataModel = new CartDataModel(data);
                    cartDataModel.setId(1000l);
                    CartDataModel.save(cartDataModel);
                    progress.dismissView();
                    CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 0l);
                    if(null==cartCountIt)
                    {
                        CartCountModel cartCount = new CartCountModel();
                        cartCount.setId(0l);
                        cartCount.setCount(listModel.getUserBuyAmount());
                        CartCountModel.save(cartCount);
                    }
                    else
                    {
                        cartCountIt.setCount(cartCountIt.getCount() + listModel.getUserBuyAmount());
                        CartCountModel.save(cartCountIt);
                    }
                    ToastUtils.showMomentToast((Activity) context, context, "添加清单成功");
                }
                else
                {
                    boolean tag = false;
                    //比较
                    //转换成JSON
                    data = cartData.getCartData();
                    JSONUtil<LocalCartOutputModel> jsonUtil = new JSONUtil<LocalCartOutputModel>();
                    localCartOutput = new LocalCartOutputModel();
                    localCartOutput = jsonUtil.toBean(data, localCartOutput);
                    List<ListModel> lists = localCartOutput.getResultData().getLists();
                    for(int i=0; i<lists.size(); i++)
                    {
                        if(listModel.getIssueId() == lists.get(i).getIssueId())
                        {
                            tag = true;
                            lists.get(i).setUserBuyAmount(listModel.getUserBuyAmount() + lists.get(i).getUserBuyAmount());
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    }
                    if(!tag)
                    {
                        lists.add(listModel);

                    }

                    //localCartOutput写入数据库
                    String str = jsonUtil.toJson(localCartOutput);
                    cartData.setCartData(str);
                    CartDataModel.save(cartData);
                    progress.dismissView();
                    CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 0l);
                    if(null==cartCountIt)
                    {
                        CartCountModel cartCount = new CartCountModel();
                        cartCount.setId(0l);
                        cartCount.setCount(listModel.getUserBuyAmount());
                        CartCountModel.save(cartCount);
                    }
                    else
                    {
                        cartCountIt.setCount(cartCountIt.getCount()+listModel.getUserBuyAmount());
                        CartCountModel.save(cartCountIt);
                    }
                    ToastUtils.showMomentToast((Activity) context, context, "添加清单成功");
                }
            }
        }
        else
        {
            String url = Contant.REQUEST_URL + Contant.JOIN_SHOPPING_CART;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), context);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put ( "issueId", issueId );
            Map<String, Object> param = params.obtainPostParam(maps);
            BaseModel base = new BaseModel ();
            HttpUtils<BaseModel> httpUtils = new HttpUtils<BaseModel> ();
            httpUtils.doVolleyPost (
                    base, url, param, new Response.Listener< BaseModel > ( ) {
                        @Override
                        public
                        void onResponse ( BaseModel response ) {
                            progress.dismissView ();
                            BaseModel base = response;
                            if(1==base.getResultCode ())
                            {
                                ProductModel p = (ProductModel) entiy;
                                CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 0l);
                                if(null==cartCountIt)
                                {
                                    CartCountModel cartCount = new CartCountModel();
                                    cartCount.setId(0l);
                                    cartCount.setCount(p.getUserBuyAmount());
                                    CartCountModel.save(cartCount);
                                }
                                else
                                {
                                    cartCountIt.setCount(cartCountIt.getCount()+p.getUserBuyAmount());
                                    CartCountModel.save(cartCountIt);
                                }
                                //上传成功
                                ToastUtils.showMomentToast((Activity) context, context, "添加清单成功");
                            }
                            else
                            {
                                //上传失败
                                ToastUtils.showMomentToast((Activity) context, context, "添加清单失败");
                            }
                        }
                    }, new Response.ErrorListener ( ) {

                        @Override
                        public
                        void onErrorResponse ( VolleyError error ) {
                            progress.dismissView ( );
                            //系统级别错误
                            ToastUtils.showMomentToast((Activity) context, context, "添加清单失败");
                        }
                    }
            );
        }
        CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 0l);
        Message message = mHandler.obtainMessage();
        message.what = Contant.CART_AMOUNT;
        message.obj = cartCountIt.getCount();
        mHandler.sendMessage(message);
    }
}
