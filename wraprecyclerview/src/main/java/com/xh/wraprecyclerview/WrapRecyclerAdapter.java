package com.xh.wraprecyclerview;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by lxh
 * time : 2019/11/20
 * des : 包裹型RecyclerView的Adapter
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView.Adapter mAdapter;
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;
    private List<Integer> mHeaderKey;
    private List<Integer> mFooterKey;


    public WrapRecyclerAdapter(Context context, RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
        mHeaderKey = new ArrayList<>();
        mFooterKey = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new ViewHolder(mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType) != null) {
            return new ViewHolder(mFooterViews.get(viewType));
        }
        return mAdapter.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position) || isFooterView(position)) {
            return;
        }
        mAdapter.onBindViewHolder(holder, position - getHeaderItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return mHeaderKey.get(position);
        } else if (isFooterView(position)) {
            return mFooterKey.get(position - getHeaderItemCount() - getContentItemCount());
        }
        return mAdapter.getItemViewType(position - getHeaderItemCount());
    }

    @Override
    public int getItemCount() {
        return mHeaderViews.size() + mAdapter.getItemCount() + mFooterViews.size();
    }

     RecyclerView.Adapter getOriginAdapter() {
        return mAdapter;
    }

    protected void addHeaderView(View view) {
        mHeaderKey.add(view.hashCode());
        mHeaderViews.put(view.hashCode(), view);
    }

    protected void addHeaderView(View view, int index) {
        mHeaderKey.add(index, view.hashCode());
        mHeaderViews.put(view.hashCode(), view);
    }

    protected void addHeaderViewAndNotify(View view) {
//        mHeaderViewLinkedList.add(view);
        mHeaderKey.add(view.hashCode());
        mHeaderViews.put(view.hashCode(), view);
        notifyItemInserted(getHeaderItemCount() - 1);
    }

    protected void addHeaderViewAndNotify(View view, int index) {
        addHeaderView(view, index);
        notifyItemInserted(index);
    }

    protected void addFooterView(View view) {
        mFooterKey.add(view.hashCode());
        mFooterViews.put(view.hashCode(), view);
    }

    protected void addFooterView(View view, int index) {
        mFooterKey.add(index, view.hashCode());
        mFooterViews.put(view.hashCode(), view);
    }

    protected void addFooterViewAndNotify(View view) {
        mFooterKey.add(view.hashCode());
        mFooterViews.put(view.hashCode(), view);
        notifyItemInserted(getHeaderItemCount() + getContentItemCount() + getFooterItemCount() - 1);
    }

    protected void addFooterViewAndNotify(View view, int index) {
        addFooterView(view, index);
        notifyItemInserted(getHeaderItemCount() + getContentItemCount() + index);
    }


    protected int removeHeaderViewAndNotify(View view) {
        int index = mHeaderKey.indexOf(view.hashCode());
        mHeaderKey.remove(index);
        mHeaderViews.delete(view.hashCode());
        notifyItemRemoved(index);
        return index;
    }

    protected int removeFooterViewAndNotify(View view) {
        int index = mFooterKey.indexOf(view.hashCode());
        mFooterKey.remove(index);
        mFooterViews.delete(view.hashCode());
        notifyItemRemoved(getHeaderItemCount() + getContentItemCount()  + index);
        return index;
    }

    protected int getFooterItemCount() {
        return mFooterViews.size();
    }

    protected int getHeaderItemCount() {
        return mHeaderViews.size();
    }

    /**
     * @return 内容区域的item数
     */
    protected int getContentItemCount() {
        return mAdapter.getItemCount();
    }

    protected boolean isHeaderView(int position) {
        return position >= 0 && position < getHeaderItemCount();
    }

    protected boolean isFooterView(int position) {
        return position >= getHeaderItemCount() + getContentItemCount();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
         ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
