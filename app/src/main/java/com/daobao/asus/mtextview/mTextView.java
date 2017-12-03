package com.daobao.asus.mtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by db on 2017/12/3.
 */

public class mTextView extends View{
    private String mText;
    private int mTextSize = 15;//默认字体大小
    private int mTextColor = Color.BLACK;//默认字体颜色
    private Paint mPaint;//画笔
    public mTextView(Context context) {
        this(context,null);
    }

    public mTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public mTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.mTextView);

        mText = array.getString(R.styleable.mTextView_text);
        mTextSize = array.getDimensionPixelSize(R.styleable.mTextView_textSize,SptoPx(mTextSize));//第二个参数为默认值
        mTextColor = array.getColor(R.styleable.mTextView_textColor,mTextColor);//第二个参数为默认值

        //回收
        array.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //  默认给一个背景
        // setBackgroundColor(Color.TRANSPARENT);
    }

    private int SptoPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //1.确定的值，这个时候不用计算  获取宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.wrap_content需要计算
        if(widthMode == MeasureSpec.AT_MOST)
        {
            //计算宽度 用画笔测量
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width()+getPaddingLeft()+getPaddingRight();
        }

        //1.确定的值，这个时候不用计算  获取高度
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //2.wrap_content需要计算
        if(heightMode == MeasureSpec.AT_MOST)
        {
            //计算宽度 用画笔测量
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        //设置控件的宽高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // x 就是开始的位置   0    y 基线 baseLine 需要运算
        //dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        int baseLine = getHeight()/2 + dy+getPaddingTop()/2-getPaddingBottom()/2;
        int x = getPaddingLeft();

        canvas.drawText(mText,x,baseLine,mPaint);
    }
    /**
     * 处理跟用户交互的，手指触摸等等
     * @param event 事件分发事件拦截
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 手指按下
                Log.e("TAG","手指按下");
                break;

            case MotionEvent.ACTION_MOVE:
                // 手指移动
                Log.e("TAG","手指移动");
                break;

            case MotionEvent.ACTION_UP:
                // 手指抬起
                Log.e("TAG","手指抬起");
                break;
        }

        invalidate();

        return super.onTouchEvent(event);
    }

}
