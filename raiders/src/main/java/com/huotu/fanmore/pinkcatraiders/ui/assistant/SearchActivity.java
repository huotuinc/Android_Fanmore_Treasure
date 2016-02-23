package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.SearchHistoryModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查询界面
 */
public
class SearchActivity extends BaseActivity implements Handler.Callback, View.OnClickListener {

    public
    Resources resources;

    //handler对象
    public Handler mHandler;

    public WindowManager   wManager;

    public BaseApplication application;

    View emptyView = null;

    public String key;

    //title
    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind ( R.id.titleRightImage )
    ImageView titleRightImage;

    @Bind ( R.id.stubSearchBar )
    ViewStub stubSearchBar;

    /**
     * 搜索结构列表
     */
    @Bind ( R.id.searchList )
    PullToRefreshListView searchList;

    @Bind ( R.id.searchHistoryTag )
    TextView searchHistoryTag;

    @Bind ( R.id.commentDetailLL )
    RelativeLayout commentDetailLL;

    @Bind ( R.id.commentDetailL )
    LinearLayout commentDetailL;

    public Bundle bundle;

    public OperateTypeEnum operateType = OperateTypeEnum.REFRESH;

    public LayoutInflater inflate;
    public EditText searchL;

    @Override
    public
    boolean handleMessage ( Message msg ) {

        return false;
    }

    @Override
    public
    void onClick ( View v ) {

    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        this.setContentView ( R.layout.ri_search );
        ButterKnife.bind ( this );
        inflate = LayoutInflater.from(SearchActivity.this);
        application = ( BaseApplication ) this.getApplication ( );
        application.mFragManager = FragManager.getIns ( this, R.id.fragment_container );
        resources = this.getResources ( );
        bundle = this.getIntent ( ).getExtras ( );
        wManager = this.getWindowManager ( );
        mHandler = new Handler ( this );
        emptyView = inflate.inflate ( R.layout.empty, null );
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无搜索结果");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initTitle ( );
        initView();
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        Drawable rightDraw = resources.getDrawable ( R.mipmap.search_in_result );
        SystemTools.loadBackground ( titleRightImage, rightDraw );
        stubSearchBar.inflate ( );
        searchL = (EditText) this.findViewById(R.id.titleSearchBar);
    }

    private void initView()
    {
        //查询搜索历史
        Iterator<SearchHistoryModel > searchHistorys = SearchHistoryModel.findAll(SearchHistoryModel.class);
        if(!searchHistorys.hasNext())
        {
            searchList.setVisibility(View.GONE);
            commentDetailLL.setVisibility(View.GONE);
            searchHistoryTag.setVisibility(View.VISIBLE);
        }
        else
        {
            searchList.setVisibility(View.GONE);
            commentDetailLL.setVisibility(View.VISIBLE);
            searchHistoryTag.setVisibility(View.GONE);
            //动态添加搜索历史
            while (searchHistorys.hasNext())
            {
                SearchHistoryModel searchHistory = searchHistorys.next();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup
                                                                                     .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RelativeLayout searchLayout = (RelativeLayout) LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_base, null);
                TextView searchHistoryText = (TextView) searchLayout.findViewById(R.id.searchHistoryText);
                searchHistoryText.setText(searchHistory.getSearchKey());
                TextView searchHistoryTime = (TextView) searchLayout.findViewById(R.id.searchHistoryTime);
                searchHistoryTime.setText(searchHistory.getTime());
                searchLayout.setLayoutParams(lp);
                commentDetailL.addView(searchLayout);
            }
        }
    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        ButterKnife.unbind ( this );
        VolleyUtil.cancelAllRequest ( );
    }

    @OnClick (R.id.titleLeftImage)
    void searchBack()
    {

        this.closeSelf ( SearchActivity.this );
    }

    @OnClick ( R.id.titleRightImage )
    void doSearch ( ) {

        searchList.setVisibility ( View.VISIBLE );
        commentDetailLL.setVisibility ( View.GONE );
        searchHistoryTag.setVisibility ( View.GONE );
        key = searchL.getText ( ).toString ( );
        if ( TextUtils.isEmpty ( key ) ) {
            ToastUtils.showShortToast ( SearchActivity.this, "请输入搜索关键字" );
            return;
        }
        else {
            int count = 0;
            //执行搜索
            //将搜索关键字加入数据库
            //历史最多10条，重复不计入
            //添加方式，加入第一条
            Iterator<SearchHistoryModel> searchHistorys = SearchHistoryModel.findAll(SearchHistoryModel.class);
            if(!searchHistorys.hasNext())
            {
                SearchHistoryModel searchHistory = new SearchHistoryModel();
                searchHistory.setSearchKey(key);
                searchHistory.setTime( DateUtils.transformDataformat2 ( System.currentTimeMillis ( ) ));
                SearchHistoryModel.save(searchHistory);
            }
            else
            {
                boolean isRepeat = false;
                //判断key有没有重复
                while (searchHistorys.hasNext())
                {
                    count ++;
                    SearchHistoryModel searchHistory = searchHistorys.next();
                    if(key.equals(searchHistory.getSearchKey()))
                    {
                        isRepeat = true;
                        break;
                    }
                    else
                    {
                        isRepeat = false;
                        continue;
                    }
                }
                if(!isRepeat)
                {
                    if(count<10)
                    {
                        //保存实体
                        SearchHistoryModel searchHistory = new SearchHistoryModel();
                        searchHistory.setSearchKey(key);
                        searchHistory.setTime(DateUtils.transformDataformat2(System.currentTimeMillis()));
                        SearchHistoryModel.save(searchHistory);
                    }
                    else {
                        //删除第一个
                        SearchHistoryModel first = SearchHistoryModel.first(SearchHistoryModel.class);
                        SearchHistoryModel.delete(first);
                        //保存实体
                        SearchHistoryModel searchHistory = new SearchHistoryModel();
                        searchHistory.setSearchKey(key);
                        searchHistory.setTime(DateUtils.transformDataformat2(System.currentTimeMillis()));
                        SearchHistoryModel.save(searchHistory);
                    }
                }
            }
        }
        try
        {
            key = URLEncoder.encode ( searchL.getText ( ).toString ( ), "UTF-8" );
        } catch (UnsupportedEncodingException e)
        {
            ToastUtils.showShortToast(SearchActivity.this, "参数转码出错");
            return;
        }
        //搜索类型
        int type = (int) bundle.get("searchType");
        switch (type)
        {
            case 1:
            {

            }
            break;
            case 2:
            {

            }
            break;
            default:
                break;
        }

    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     if (SearchActivity.this.isFinishing()) {
                                         return;
                                     }
                                     operateType = OperateTypeEnum.REFRESH;
                                     searchList.setRefreshing(true);
                                 }
                             }, 1000);
    }

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(SearchActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}
