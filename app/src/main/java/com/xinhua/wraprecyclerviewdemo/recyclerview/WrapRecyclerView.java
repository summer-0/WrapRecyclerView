package com.xinhua.wraprecyclerviewdemo.recyclerview;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by lxh
 * time : 2019/11/20
 * des : 包裹型RecyclerView 可添加删除头部底部
 */
public class WrapRecyclerView extends RecyclerView {

    private static final String TAG = "xh_tag_WrapRecyclerView";
    private List<View> mHeaderViews;
    private ArrayMap<Integer, View> mHeaderViewResMap;
    private List<View> mFooterViews;
    private ArrayMap<Integer, View> mFooterViewResMap;
    private WrapRecyclerAdapter mWrapRecycleAdapter;
    private Context mContext;

    public WrapRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
        mHeaderViewResMap = new ArrayMap<>();
        mFooterViewResMap = new ArrayMap<>();
        mContext = context;
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (mWrapRecycleAdapter != null) {
            mWrapRecycleAdapter.getOriginAdapter().unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        if (adapter == null) {
            mWrapRecycleAdapter = null;
        } else {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
            mWrapRecycleAdapter = new WrapRecyclerAdapter(getContext(), adapter);
            for (int i = 0; i < mHeaderViews.size(); i++) {
                mWrapRecycleAdapter.addHeaderView(mHeaderViews.get(i));
            }
            if (mFooterViews.size() > 0) {
                for (View footerView : mFooterViews) {
                    mWrapRecycleAdapter.addFooterView(footerView);
                }
            }
        }
        super.setAdapter(mWrapRecycleAdapter);
    }


//    @Override
//    public void setLayoutManager(LayoutManager layoutManager) {
//        if (layoutManager instanceof GridLayoutManager) {
//            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
//            final GridLayoutManager.SpanSizeLookup spanSizeLookupHolder = gridLayoutManager.getSpanSizeLookup();
//
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    if (mWrapRecycleAdapter.isHeaderView(position) || mWrapRecycleAdapter.isFooterView(position)) {
//                        return gridLayoutManager.getSpanCount();
//                    }
//                    if (spanSizeLookupHolder != null)
//                        return spanSizeLookupHolder.getSpanSize(position - getHeaderItemCount());
//                    return 1;
//                }
//            });
//        }
//        super.setLayoutManager(layoutManager);
//    }

    public Adapter getOriginAdapter() {
        if (mWrapRecycleAdapter == null) {
            return null;
        }
        return mWrapRecycleAdapter.getOriginAdapter();
    }

    /**
     * recyclerView添加头部view
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view is null on addHeaderView(View view)");
        }
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(view);
            if (mWrapRecycleAdapter != null) {
                mWrapRecycleAdapter.addHeaderViewAndNotify(view);
            }
        }
    }

    public void addHeaderView(View view, int index) {
        if (index < 0 || index > mHeaderViews.size()) {
            throw new IllegalArgumentException("index out of bounds on addHeaderView(View view, int index)");
        }
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(index, view);
            if (mWrapRecycleAdapter != null) {
                mWrapRecycleAdapter.addHeaderViewAndNotify(view, index);
                if (index == 0) {
                    scrollToPosition(index);
                }
            }
        }
    }

    /**
     * recyclerView添加头部view
     *
     * @param layoutId 布局文件
     */
    public void addHeaderView(@LayoutRes int layoutId) {
        Log.d(TAG, "addHeaderView: " + layoutId);
        if (layoutId == 0) {
            throw new IllegalArgumentException("layoutId is invalid on addHeaderView(int layoutId)");
        }
        if (!mHeaderViewResMap.containsKey(layoutId)) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, null, false);
            if (view == null) {
                throw new IllegalArgumentException("view is null on addHeaderView(View view)");
            }
            mHeaderViewResMap.put(layoutId, view);
            addHeaderView(view);
        }
    }

    public void addHeaderView(@LayoutRes int layoutId, int index) {
        if (layoutId == 0) {
            throw new IllegalArgumentException("layoutId is invalid on addHeaderView(int layoutId)");
        }
        if (!mHeaderViewResMap.containsKey(layoutId)) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, null, false);
            if (view == null) {
                throw new IllegalArgumentException("view is null on addHeaderView(View view)");
            }
            mHeaderViewResMap.put(layoutId, view);
            addHeaderView(view, index);
        }
    }

    public void addFooterView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view is null on addFooterView(View view)");
        }
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(view);
            if (mWrapRecycleAdapter != null) {
                mWrapRecycleAdapter.addFooterViewAndNotify(view);
            }
        }
    }

    public void addFooterView(View view, int index) {
        if (index < 0 || index > mFooterViews.size()) {
            throw new IllegalArgumentException("index out of bounds on addHeaderView(View view, int index)");
        }
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(index, view);
            if (mWrapRecycleAdapter != null) {
                mWrapRecycleAdapter.addFooterViewAndNotify(view, index);
            }
        }
    }

    public void addFooterView(@LayoutRes int layoutId) {
        if (layoutId == 0) {
            throw new IllegalArgumentException("layoutId is invalid on addHeaderView(int layoutId)");
        }
        if (!mFooterViewResMap.containsKey(layoutId)) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, null, false);
            if (view == null) {
                throw new IllegalArgumentException("view is null on addFooterView(View view)");
            }
            mFooterViewResMap.put(layoutId, view);
            addFooterView(view);
        }
    }

    public void addFooterView(@LayoutRes int layoutId, int index) {
        if (layoutId == 0) {
            throw new IllegalArgumentException("layoutId is invalid on addFooterView(int layoutId, int index)");
        }
        if (!mFooterViewResMap.containsKey(layoutId)) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, null, false);
            if (view == null) {
                throw new IllegalArgumentException("view is null on addFooterView(View view)");
            }
            mFooterViewResMap.put(layoutId, view);
            addFooterView(view, index);
        }
    }

    public void removeHeaderView(View view) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view);
            if (mWrapRecycleAdapter != null) {
                LayoutManager layoutManager = getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.removeView(view);
                    mWrapRecycleAdapter.removeHeaderViewAndNotify(view);
                } else {
                    throw new NullPointerException("the LayoutManager in RecyclerView is null");
                }
            }
        }
    }

    public void removeHeaderViewWithIndex(int index) {
        if (index < 0 && index >= getHeaderItemCount()) {
            throw new IllegalArgumentException("index out of bounds on removeHeaderViewWithIndex(int index)");
        }
        View view = mHeaderViews.remove(index);
        if (mWrapRecycleAdapter != null) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager != null) {
                layoutManager.removeView(view);
                mWrapRecycleAdapter.removeHeaderViewAndNotify(view);
            } else {
                throw new NullPointerException("the LayoutManager in RecyclerView is null");
            }
        }
    }

    public void removeHeaderView(@LayoutRes int layoutId) {
        View view = mHeaderViewResMap.get(layoutId);
        if (view != null) {
            mHeaderViewResMap.remove(layoutId);
            removeHeaderView(view);
        }
    }


    public void removeFooterView(View view) {
        if (mFooterViews.contains(view)) {
            mFooterViews.remove(view);
            if (mWrapRecycleAdapter != null) {
                LayoutManager layoutManager = getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.removeView(view);
                    mWrapRecycleAdapter.removeFooterViewAndNotify(view);
                } else {
                    throw new NullPointerException("the LayoutManager in RecyclerView is null");
                }
            }
        }
    }

    public void removeFooterViewWithIndex(int index) {
        if (index < 0 && index >= getFooterItemCount()) {
            throw new IllegalArgumentException("index out of bounds on removeFooterViewWithIndex(int index)");
        }
        View view = mFooterViews.remove(index);
        if (mWrapRecycleAdapter != null) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager != null) {
                layoutManager.removeView(view);
                mWrapRecycleAdapter.removeFooterViewAndNotify(view);
            } else {
                throw new NullPointerException("the LayoutManager in RecyclerView is null");
            }
        }
    }

    public void removeFooterView(@LayoutRes int layoutId) {
        View view = mFooterViewResMap.get(layoutId);
        if (view != null) {
            mFooterViewResMap.remove(layoutId);
            removeFooterView(view);
        }
    }

    /**
     * RecyclerView Adapter 数据观察者
     */
    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapRecycleAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            mWrapRecycleAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            positionStart += getHeaderItemCount();
            mWrapRecycleAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            mWrapRecycleAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            mWrapRecycleAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            fromPosition += getHeaderItemCount();
            toPosition += getHeaderItemCount();
            mWrapRecycleAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    /**
     * 获取头部数量
     *
     * @return
     */
    public int getHeaderItemCount() {
        if (mWrapRecycleAdapter == null) {
            return 0;
        }
        return mWrapRecycleAdapter.getHeaderItemCount();
    }

    public int getFooterItemCount() {
        if (mWrapRecycleAdapter == null) {
            return 0;
        }
        return mWrapRecycleAdapter.getFooterItemCount();
    }

}
