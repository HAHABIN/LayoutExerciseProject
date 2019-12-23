package com.example.habin.shopcar.cutleryRecycling.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.habin.shopcar.R;


/**
 * 定制SwipeRefreshLayout实现自动刷新，加载更多
 * Created by sam on 17/2/22.
 */
public class SwipeView extends SwipeRefreshLayout {
    public static final int PULL_STATUS_NOMAL = 0;//正常狀態
    public static final int PULL_STATUS_REFRESHING = 1;//正在刷新
    public static final int PULL_STATUS_LOADMORE = 2;//正在加载更多
    public static final int PULL_STATUS_LOADMORE_STOP = 3;//已经加载完更多
    Context mContext;
    ListView mListView;
    OnReLoadListener mOnReLoadListener;
    View mFooterView;
    View mLoreMoreView;//加载更多的view
    LinearLayout mFooterLayout;//整个footer的布局
    LinearLayout mFooterLayoutGroup;//自定义footerView的父布局
    SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    TextView mLoadMoreTextView;
    ImageView mLoadMoreImage;
    private int mStatus;
    String mReLoadingText, mNoMoreText;
    boolean mReLoadAble = false;
    ListView mSelfLV;//自定义ListView

    public SwipeView(Context context) {
        super(context);
        init(context);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化样式
     */
    private void initRefreshStyle(){
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环
        setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright
//                android.R.color.holo_orange_light,
//                android.R.color.holo_purple
        );
    }

    private void initLoadTextRes(){
        mReLoadingText = mContext.getString(R.string.swipe_loading_more);
        mNoMoreText = mContext.getString(R.string.swipe_load_finish);
    }

    public ListView getListView(){
        return mListView;
    }

    public void setAdapter(final BaseAdapter adapter){
        this.post(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(adapter);
            }
        });
    }

    public void setSelfListView(ListView listView){
        this.mSelfLV = listView;
    }

    private void init(Context context) {
        this.mContext = context;
        initRefreshStyle();
        initLoadTextRes();

        mLoreMoreView = createLoadView(context);
        mLoadMoreTextView = (TextView) mLoreMoreView.findViewWithTag("tv_loadmoer");
        mLoadMoreImage = (ImageView) mLoreMoreView.findViewWithTag("image_loading");
        mLoreMoreView.setVisibility(View.GONE);
        AnimationDrawable anim = (AnimationDrawable) mLoadMoreImage.getBackground();
        anim.start();

        mFooterLayoutGroup = new LinearLayout(mContext);
        mFooterLayoutGroup.setOrientation(LinearLayout.VERTICAL);

        mFooterLayout = new LinearLayout(mContext);
        mFooterLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                dipToPixels(context, 45));
        footerParams.gravity = Gravity.CENTER;

        mFooterLayout.addView(mFooterLayoutGroup);//外部添加的footerView
        mFooterLayout.addView(mLoreMoreView, footerParams);//加载更多view
        mFooterLayout.setOnClickListener(null);

        if(null != mSelfLV){
            mListView = mSelfLV;
        }else{
            mListView = new ListView(context);
            mListView.setVerticalScrollBarEnabled(false);
            mListView.setDividerHeight(0);
            mListView.setCacheColorHint(getResources().getColor(R.color.transparent));
            mListView.setSelector(getResources().getDrawable(R.drawable.bg_white_gray_selector));
        }


        mListView.addFooterView(mFooterLayout);
        addView(mListView, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mStatus == PULL_STATUS_LOADMORE_STOP || !mReLoadAble) {
                    return;
                }
                // 当不滚动时
                if (mStatus != PULL_STATUS_LOADMORE
                        && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (null != mListView && mListView.getFooterViewsCount() == 0) {
                            mLoreMoreView.setVisibility(View.GONE);
                            return;
                        }

                        //加载更多佈局的代码
                        if(null != mOnReLoadListener){
                            mOnReLoadListener.onLoad();
                            setStatus(PULL_STATUS_LOADMORE);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这里要减去footerview
                //
                int defaultViewCount = mListView.getHeaderViewsCount() + mListView.getFooterViewsCount();

                if (null != mOnReLoadListener && mListView.getCount() > defaultViewCount) {
                    mLoreMoreView.setVisibility(View.GONE);
                }

                mLoreMoreView.setVisibility(mReLoadAble ? View.VISIBLE : View.GONE);
            }
        });

    }

    private View createLoadView(Context context){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView = new ImageView(context);
        imageView.setTag("image_loading");
        imageView.setVisibility(View.GONE);
        TextView textView = new TextView(context);
        textView.setTag("tv_loadmoer");
        //以下两行设置点击时不出现颜色变化
        textView.setClickable(true);
        textView.setTextColor(Color.parseColor("#cccccc"));

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(dipToPixels(context, 40),
                dipToPixels(context, 40));
        imageParams.gravity = Gravity.CENTER;
        imageView.setBackgroundResource(R.drawable.progressbar);
        layout.addView(imageView, imageParams);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER;
        layout.addView(textView, textParams);
        return layout;
    }


    private int dipToPixels(Context context, float dip){
        return (int)(context.getResources().getDisplayMetrics().density * dip);
    }

    private void setStatus(int status){
        mStatus = status;
        switch (status){
            case PULL_STATUS_NOMAL:
                mLoadMoreTextView.setText(mReLoadingText);
                break;
            case PULL_STATUS_LOADMORE:
                mLoreMoreView.setVisibility(View.VISIBLE);
                mLoadMoreImage.setVisibility(View.VISIBLE);
                mLoadMoreTextView.setText(mReLoadingText);

                break;
            case PULL_STATUS_LOADMORE_STOP:
                mLoadMoreTextView.setText(mNoMoreText);
                mLoadMoreImage.setVisibility(View.GONE);
                break;
            case PULL_STATUS_REFRESHING:

                break;
        }
    }

    /**
     * 更多加载完成
     */
    public void stopReLoad(){
        setStatus(PULL_STATUS_NOMAL);
    }

    /**
     * 没有更多了～
     */
    public void ReLoadComplete(){
        this.mReLoadAble = false;
        setStatus(PULL_STATUS_LOADMORE_STOP);
    }

    /**
     * 这里说一下，一般listview加载更多是没有footerview的，
     * 但我这样把它加进去了，mFooterLayout是整个footerview的容器，是线性布局的，当然你也可以改
     * 加载更多的view会拼在addFooterView这个传进来的view下面
     * @param footerView
     */
    public void addFooterView(View footerView){
        if(null == mFooterLayoutGroup || mFooterLayoutGroup.getChildCount() > 0){
            return;
        }

        mFooterLayoutGroup.addView(footerView,
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void removeFooterView(){
        if(null != mFooterLayoutGroup && mFooterLayoutGroup.getChildCount() > 0){
            mFooterLayoutGroup.removeAllViews();
        }
    }

    /**
     * 自动刷新
     */
    public void startRefresh(){
        this.post(new Runnable() {

            @Override
            public void run() {
                setStatus(PULL_STATUS_REFRESHING);
                setRefreshing(true);
                mOnRefreshListener.onRefresh();
                mListView.setSelection(0);
            }
        });
    }

    public void stopFreshing(){
        setStatus(PULL_STATUS_NOMAL);

        if(null != mListView && null != mListView.getAdapter()){
            //为了避免调用都先调stopFreshing再给adpater设置数据导致adapter.getCount()获取到的数量有问题，这里做个延迟处理
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int count = mListView.getAdapter().getCount();
                    if(count <= (mListView.getHeaderViewsCount() + mListView.getFooterViewsCount())){
                        //没有数据时隐藏加载更多
                        ReLoadComplete();
                    }
                }
            }, 500);
        }

        super.setRefreshing(false);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        mOnRefreshListener = listener;
        super.setOnRefreshListener(mOnRefreshListener);
    }

    public void setOnReLoadListener(OnReLoadListener listener){
        this.mOnReLoadListener = listener;
    }

    public interface OnReLoadListener{
        public void onLoad();
    }

    public void setReLoadAble(boolean reLoadAble){
        this.mReLoadAble = reLoadAble;
        if(!reLoadAble){
            ReLoadComplete();
        }
    }

    public void setHorizontalAble(boolean horizontalAble){
        this.mHorizontalAble = horizontalAble;
    }

    int mX, mY;
    boolean mIs = false;
    boolean mHorizontalAble = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = (int) ev.getX();
                mY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(mIs) return false;

                int tempY = (int) ev.getY();
                int tempX = (int) ev.getX();
                if(mHorizontalAble && Math.abs(tempY - mY) < Math.abs(tempX - mX)){
                    mIs = true;
                    mX = tempX;
                    mY = tempY;
                    return false;
                }
                else{
                    mX = tempX;
                    mY = tempY;
                    break;
                }
            case MotionEvent.ACTION_UP:
                mIs = false;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

}
