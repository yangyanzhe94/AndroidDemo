package com.yyz.base.recycler.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yyz.base.recycler.RecyclerViewHolder;
import com.yyz.base.recycler.interfaces.OnItemClickListener;
import com.yyz.base.recycler.interfaces.OnItemLongListener;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter 基类
 * Created by Administrator on 2017/8/16.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    //上下文
    protected Context mContext;
    //点击事件监听
    protected OnItemClickListener mOnItemClickListener;
    //长按事件监听
    protected OnItemLongListener mOnItemLongListener;
    //初始化 List 防止传参数时，adapter和引用adapter两个参数指向同一内存地址。
    protected List<T> mDatas = new ArrayList<>();


    /**
     * 构造方法
     *
     * @param context 上下文
     * @param datas   数据
     */
    public BaseAdapter(Context context, List<T> datas) {
        this.mContext = context;
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }


    /**
     * 设置数据
     *
     * @param datas
     */
    public void setDatas(@NonNull List<T> datas) {
        mDatas.clear();
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据
     *
     * @param data
     * @param position
     */
    public void addData(T data, int position) {
        if (data != null) {
            mDatas.add(position, data);
            notifyItemChanged(position);
        }

    }


    /**
     * 添加 多条数据
     *
     * @param datas
     */
    public void addDatas(@NonNull List<T> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取单个数据
     *
     * @param position
     * @return
     */
    public T getData(int position) {
        if (position < mDatas.size()){
            return mDatas.get(position);
        }
      return null;
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 删除单个数据
     *
     * @param data
     */
    public void remove(T data) {
        if (data != null) {
            int position = mDatas.indexOf(data);
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }

    /**
     * 删除所有数据
     */
    public void removes() {
        mDatas.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 获取item layout
     *
     * @param viewType
     * @return
     */
    protected abstract
    @LayoutRes
    int getLayoutId(int viewType);


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(mContext,parent, getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindHolder(holder, position, getItemViewType(position));
    }

    /**
     * 设置数据
     *
     * @param holder
     * @param position
     */
    protected abstract void bindHolder(BaseViewHolder holder, int position, int viewType);


    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按监听
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongListener onItemLongClickListener) {
        this.mOnItemLongListener = onItemLongClickListener;
    }


}
