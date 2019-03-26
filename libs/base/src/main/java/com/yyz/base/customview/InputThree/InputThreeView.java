package com.yyz.base.customview.InputThree;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yyz.base.R;
import com.yyz.base.utils.GraphicUtils;

import java.util.ArrayList;
import java.util.List;

import static com.yyz.base.customview.InputThree.InputThreeAdapter.EDIT_TEXT;
import static com.yyz.base.customview.InputThree.InputThreeAdapter.TEXT_VIEW;

/**
 * Created by yyz on 12/15/2017
 */

public class InputThreeView extends FrameLayout {
    private String hintText;
    private String titleText;
    private AppCompatTextView title;
    private ImageView add;
    private Context context;
    private RecyclerView recyclerView;
    private InputThreeAdapter inputThreeAdapter;
    List<InputDeleteInfo> inputDeleteInfos = new ArrayList<>();
    private int itemCount;
    private int showType;
    private InputThreeAdapter.OnTextChangeListener onTextChangeListener;

    public void addTextChangedListener(InputThreeAdapter.OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    public InputThreeView(@NonNull Context context) {
        this(context, null);
    }

    public InputThreeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputThreeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);

        LayoutInflater.from(getContext()).inflate(R.layout.view_three_input, InputThreeView.this, true);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {

        this.context = context;
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.InputThree);
        if (mTypedArray != null) {
            this.hintText = mTypedArray.getString(R.styleable.InputThree_hint_text);
            this.titleText = mTypedArray.getString(R.styleable.InputThree_title);
            this.showType = mTypedArray.getInt(R.styleable.InputThree_show_type, 0);

        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initAdapter();
        initListener();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //初始化View
        title = findViewById(R.id.inputThreeTitle);
        add = findViewById(R.id.inputThreeAdd);
        recyclerView = findViewById(R.id.inputThreeRecycler);
        //设置标题内容
        title.setText(titleText);
        switch (showType) {
            case EDIT_TEXT:
                //初始化显示状态
                add.setVisibility(VISIBLE);
                int i = GraphicUtils.dip2px(context, 12);
                title.setPadding(i,0,0,0);
                break;
            case TEXT_VIEW:
                //初始化显示状态
                add.setVisibility(GONE);
                break;
        }

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {

        inputThreeAdapter = new InputThreeAdapter(context, inputDeleteInfos, showType);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(inputThreeAdapter);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * 初始化监听
     */
    private void initListener() {
        switch (showType) {
            case EDIT_TEXT:
                add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //取得添加前的数据数目
                        itemCount = inputThreeAdapter.getItemCount();
                        //添加新数据
                        InputDeleteInfo inputDeleteInfo = new InputDeleteInfo();
                        inputDeleteInfo.setHintText(hintText);
                        inputDeleteInfo.setContent("");
                        inputThreeAdapter.addData(inputDeleteInfo, itemCount);
                        //加入Recycler中
                        judgeAddShow();
                    }
                });

                inputThreeAdapter.setOnDeleteClickListener(new InputThreeAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(View v, int position) {
                        judgeAddShow();
                    }
                });

                inputThreeAdapter.addTextChangedListener(new InputThreeAdapter.OnTextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence s, int position) {
                        onTextChangeListener.onTextChanged(s, position);
                    }
                });
                break;
        }

    }

    /**
     * 判断是否可以点击
     */
    private void judgeAddShow() {
        itemCount = inputThreeAdapter.getItemCount();
        if (showType == EDIT_TEXT && itemCount < 3) {
            add.setVisibility(VISIBLE);
            add.setClickable(true);
        } else {
            add.setVisibility(GONE);
            add.setClickable(false);
        }
    }

    /**
     * 返回内容集合
     *
     * @return
     */
    public String[] getContents() {
        return inputThreeAdapter.getStrings();
    }


    public void setContents(List<String> contents) {
        if (contents != null) {
            List<InputDeleteInfo> datas = new ArrayList<>();
            for (int i = 0; i < contents.size(); i++) {
                InputDeleteInfo inputDeleteInfo = new InputDeleteInfo();
                inputDeleteInfo.setHintText(hintText);
                inputDeleteInfo.setContent(contents.get(i));
                datas.add(inputDeleteInfo);
            }
            //清楚数据
            inputThreeAdapter.removes();
            //添加数据
            inputThreeAdapter.addDatas(datas);
            //判断加号能否显示
            if (showType == EDIT_TEXT) {
                judgeAddShow();
            }
        }
    }
}
