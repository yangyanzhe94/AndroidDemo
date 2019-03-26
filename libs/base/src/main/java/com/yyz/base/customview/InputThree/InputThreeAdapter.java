package com.yyz.base.customview.InputThree;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.yyz.base.R;
import com.yyz.base.recycler.base.BaseAdapter;
import com.yyz.base.recycler.base.BaseViewHolder;
import com.yyz.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/15/2017.
 */

public class InputThreeAdapter extends BaseAdapter<InputDeleteInfo> {
    private int showType;
    private ExtendEditText content;
    private View delete;
    private OnDeleteClickListener onDeleteClickListener;
    private long clickTime = 0L;

    protected final static int EDIT_TEXT = 0;
    protected final static int TEXT_VIEW = 1;
    private AppCompatTextView tvContent;
    private OnTextChangeListener onTextChangeListener;


    interface OnDeleteClickListener {
        void onDeleteClick(View v, int position);
    }

    void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnTextChangeListener {
        void onTextChanged(CharSequence s, int position);
    }


    public void addTextChangedListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    /**
     * 点击事件的时间间隔，用于防止频繁操作
     *
     * @return
     */
    public boolean clickInterval() {
        if (System.currentTimeMillis() - clickTime <= 500) {
            return false;
        }
        clickTime = System.currentTimeMillis();
        return true;
    }


    /**
     * 返回内容数据
     *
     * @return
     */
    public String[] getStrings() {
        List<InputDeleteInfo> datas = getDatas();
        List<String> contents = new ArrayList<>();
        for (InputDeleteInfo inputDeleteInfo : datas) {
            //去除首尾空格
            if (StringUtils.isNotEmpty(inputDeleteInfo.getContent().trim())) {
                contents.add(inputDeleteInfo.getContent().trim());
            }
        }
        return contents.toArray(new String[contents.size()]);
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param datas   数据
     */
    public InputThreeAdapter(Context context, List<InputDeleteInfo> datas, int showType) {
        super(context, datas);
        this.showType = showType;
    }

    @Override
    public int getItemViewType(int position) {
        return showType;
    }

    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == TEXT_VIEW) {
            return R.layout.view_display_input_delete;
        } else {
            return R.layout.view_input_delete;
        }
    }


    @Override
    protected void bindHolder(BaseViewHolder holder, int position, int viewType) {

        if (viewType == TEXT_VIEW) {
            initView(holder, position, viewType);
        } else {
            initView(holder, position, viewType);
            initListener(position);
            initData(holder, position, viewType);
        }

    }

    private void initData(BaseViewHolder holder, int position, int viewType) {

        if (viewType == TEXT_VIEW) {
            tvContent.setText(mDatas.get(position).getContent());
        } else {
            InputDeleteInfo inputDeleteInfo = mDatas.get(position);
            setContent(inputDeleteInfo.getContent());
            setHint(inputDeleteInfo.getHintText());
        }
    }

    private void initListener(final int position) {

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickInterval()) {
                    remove(v, position);
                }
            }
        });

        content.clearTextChangedListeners();
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDatas != null) {
                    mDatas.get(position).setContent(s.toString());
                }
                if (onTextChangeListener != null) {
                    onTextChangeListener.onTextChanged(s, position);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initView(BaseViewHolder holder, int position, int viewType) {

        if (mDatas != null && mDatas.get(position) != null) {
            if (viewType == TEXT_VIEW) {
                tvContent = holder.getView(R.id.data_display_tv_content);
                tvContent.setText(mDatas.get(position).getContent());
            } else {
                delete = holder.getView(R.id.input_delete_delete);
                content = (ExtendEditText) holder.getView(R.id.input_delete_content);
            }
        }
    }

    /**
     * 设置Hint文字内容
     *
     * @param hint
     */
    public void setHint(String hint) {
        if (content != null && hint != null) {
            content.setHint(hint);
        }
    }

    public void setContent(String contentText) {
        if (content != null && contentText != null) {
            content.setText(contentText);
        }
    }

    /**
     * 移除特定位置数据
     *
     * @param position
     */
    public void remove(View v, int position) {
        if (mDatas != null && mDatas.size() > position) {
            mDatas.get(position).setContent("");
            mDatas.get(position).setHintText("");
            if (onTextChangeListener != null) {
                onTextChangeListener.onTextChanged("", position);
            }
            mDatas.remove(position);
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(v, position);
            }
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDatas.size() - position);
        }
    }

    public void addData(InputDeleteInfo data, int position) {
        if (data != null) {
            mDatas.add(position, data);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, mDatas.size() - position);
        }
    }
}
