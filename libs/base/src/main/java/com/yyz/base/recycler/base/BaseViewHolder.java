package com.yyz.base.recycler.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyz.base.app.AppManager;

/**
 * Created by Administrator on 2017/8/11.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViewArray;
    protected Context mContext;

    public BaseViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
        mViewArray = new SparseArray<>();
        this.mContext = context;
    }


    /**
     * 获取 ItemView
     *
     * @return
     */
    public View getItemView() {
        return itemView;
    }

    /**
     * 获取布局中View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置控件隐藏or显示
     *
     * @param viewId
     * @param visibility
     */
    public void setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
    }

    /**
     * 设置 TextView 内容
     *
     * @param viewId
     * @param text
     */
    public void setText(@IdRes int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text != null ? text : "");
    }

    /**
     * 设置 TextView 内容
     *
     * @param viewId
     * @param textId
     */
    public void setText(@IdRes int viewId, @StringRes int textId) {
        setText(viewId, mContext.getString(textId));
    }

    /**
     * 设置 TextView 内容颜色
     *
     * @param viewId
     * @param colorId
     */
    public void setTextColor(@IdRes int viewId, @ColorRes int colorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(ContextCompat.getColor(mContext, colorId));
    }

    /**
     * 设置背景颜色
     *
     * @param viewId
     * @param resId
     */
    public void setBackground(@IdRes int viewId, @DrawableRes int resId) {
        View view = getView(viewId);
        if (view != null)
            view.setBackgroundResource(resId);
    }

    /**
     * 设置 ImageView 图片
     *
     * @param viewId
     * @param resId
     */
    public void setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView imageView = getView(viewId);
        imageView.setBackgroundResource(resId);
    }

    /**
     * Glide 加载本地资源图片
     *
     * @param viewId
     * @param resId
     */
    public void setImageNetwork(@IdRes int viewId, @DrawableRes int resId) {
        ImageView imageView = getView(viewId);
        AppManager.image().imageStatic(mContext, imageView, resId);
    }

    /**
     * 设置 ImageView 网络图片
     *
     * @param viewId
     * @param imageUrl
     */
    public void setImageNetwork(@IdRes int viewId, String imageUrl) {
        ImageView imageView = getView(viewId);
        AppManager.image().imageStatic(mContext, imageView, imageUrl);

    }

    /**
     * 设置网络图片
     *
     * @param viewId        控件id
     * @param imageUrl      图片地址
     * @param placeholderId 加载中图片
     * @param errorId       加载错误展示图
     */
    public void setImageNetwork(@IdRes int viewId, String imageUrl, @DrawableRes int placeholderId, @DrawableRes int errorId) {
        ImageView imageView = getView(viewId);
        AppManager.image().imageStatic(mContext, imageView, imageUrl, placeholderId, errorId);
    }

    /**
     * 设置加载圆角图片
     *
     * @param viewId
     * @param imageUrl
     * @param round
     */
    public void setImageRound(@IdRes int viewId, String imageUrl, int round) {
        ImageView imageView = getView(viewId);
        AppManager.image().imageStatic(mContext, imageView, imageUrl, round);
    }

    /**
     * 设置 view 监听
     *
     * @param vewId
     * @param onClickListener
     */
    public void setOnClickListener(@IdRes int vewId, View.OnClickListener onClickListener) {
        getView(vewId).setOnClickListener(onClickListener);
    }


//    /**
//     * 设置 ratingBar 评分等级
//     * @param ratingBarId
//     * @param grade
//     */
//    public void setRatingBar(@IdRes int ratingBarId, int grade) {
//        AppCompatRatingBar ratingBar = getView(ratingBarId);
//        ratingBar.setRating(grade);
//    }
}
