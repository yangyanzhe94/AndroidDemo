package com.yyz.base.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.yyz.base.R;

/**
 * Created by Administrator on 12/15/2017.
 */

public class InputBoxView extends FrameLayout {

    //输入文字的内容
    private AppCompatEditText content;
    //右下角数字变化
    private AppCompatTextView number;
    //最大数字输入长度
    private int maxInputLength = 150;
    //hint文字
    private String hintText;

    boolean isHaveContent = false;
    private ContentListener contentListener;

    public InputBoxView(@NonNull Context context) {
        this(context, null);
    }

    public InputBoxView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBoxView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.InputBoxView);
        if (mTypedArray != null) {
            this.hintText = mTypedArray.getString(R.styleable.InputBoxView_ibv_hint_text);
        }
        LayoutInflater.from(getContext()).inflate(R.layout.view_input_box, InputBoxView.this, true);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        content = findViewById(R.id.input_box_et_content);
        number = findViewById(R.id.input_box_tv_number);
        number.setText(String.valueOf(maxInputLength));
        content.setHint(hintText);
    }

    public interface ContentListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }

    public void addHaveContentListener(ContentListener contentListener) {
        this.contentListener = contentListener;
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                number.setText(150 - s.length());
//                LogUtils.d("beforeTextChanged : s = " + s.toString() + " ; start= " + start + " ; count = " + count + " ; after:" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                number.setText(150 - s.length());
//                LogUtils.d("onTextChanged : s =" + s.toString() + " ; start = " + start + " ; before=" + before + " ; count= " + count);
//                inputBoxViewTextChangedListener.onTextChanged(s, start, before, count);
                if(number!=null){
                    number.setText(String.valueOf(maxInputLength - s.length()));
                }
                if(contentListener!=null){
                    contentListener.onTextChanged(s,start,before,count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


//                LogUtils.d("afterTextChanged : s = " + s.toString());
            }
        });
    }

    /**
     * 获取输入内容
     *
     * @return 输入框内的内容
     */
    public String getContent() {
        return content.getText().toString();
    }

    public void setContent(String instruction){
        this.content.setText(instruction);
    }

}
