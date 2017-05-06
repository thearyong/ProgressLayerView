package com.thearyong.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by thearyong on 15/4/11.
 * 进度控件
 */
public class ProgressView extends View {

    public enum DIRECT {
        UP(1),
        DOWN(2),
        LEFT(3),
        RIGHT(4);

        private int index;

        DIRECT(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static DIRECT getDirection(int index) {
            for (DIRECT d : DIRECT.values()) {
                if (d.getIndex() == index) {
                    return d;
                }
            }
            return null;
        }

    }

    //默认的图片覆盖颜色
    private int layerColor;
    private int errorLayerColor;
    //错误提示
    private String errorTips;
    private DIRECT direct;
    private Paint textPaint, layerPaint;
    private int progress;
    private int textColor;
    private float textSize;
    private boolean hasText = true;
    private boolean isReverse;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressView);
        int dir = ta.getInt(R.styleable.ProgressView_pv_direction, 1);
        direct = DIRECT.getDirection(dir);
        progress = ta.getInteger(R.styleable.ProgressView_pv_progress, 0);
        layerColor = ta.getColor(R.styleable.ProgressView_pv_layer_color, 0x33333333);
        textColor = ta.getColor(R.styleable.ProgressView_pv_text_color, 0xffffffff);
        textSize = ta.getDimensionPixelOffset(R.styleable.ProgressView_pv_text_size, 13);
        hasText = ta.getBoolean(R.styleable.ProgressView_pv_text_able, true);
        errorTips = ta.getString(R.styleable.ProgressView_pv_error_info);
        errorLayerColor = ta.getColor(R.styleable.ProgressView_pv_error_color, 0x33fa0202);
        ta.recycle();

    }

    void init(AttributeSet attrs) {

        //文字画笔
        textPaint = new Paint();
        textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        //阴影遮盖层画笔
        layerPaint = new Paint();
        layerPaint.setStyle(Paint.Style.FILL);//设置填满

        obtainAttributes(attrs);

        setTextColor(textColor);
        setTextSize(textSize);
        setLayerColor(layerColor);

        if (errorTips!=null) setErrorTips(errorTips);

    }

    public ProgressView setDirect(DIRECT direct) {
        this.direct = direct;
        postInvalidate();
        return this;
    }

    public ProgressView setTextSize(float size) {
        textPaint.setTextSize(size);
        postInvalidate();
        return this;
    }

    public ProgressView setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
        postInvalidate();
        return this;
    }

    public ProgressView setLayerColor(int recoverColor) {
        this.layerColor = recoverColor;
        layerPaint.setColor(recoverColor);
        postInvalidate();
        return this;
    }

    public ProgressView setHasText(boolean hasText) {
        this.hasText = hasText;
        postInvalidate();
        return this;
    }

    public ProgressView setReverse(boolean isReverse) {
        this.isReverse = isReverse;
        postInvalidate();
        return this;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0)
            throw new IllegalArgumentException("progress not less than 0");

        if (progress >= 100)
            progress = 100;

        if (isReverse) progress = 100 - progress;

        this.progress = progress;
        setLayerColor(layerColor);

        postInvalidate();
    }


    public void reset() {
        this.progress = 0;
        layerPaint.setColor(layerColor);
        postInvalidate();
    }

    public void setComplete() {
        this.progress = 100;
        postInvalidate();
    }

    public boolean isError() {
        return progress < 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (isError()) {//error
            canvas.drawRect(0, 0, getWidth(), getHeight(), layerPaint);
            canvas.drawText(errorTips, (getWidth() - textPaint.measureText(errorTips)) / 2,
                    (getHeight() - (textPaint.descent() + textPaint.ascent())) / 2, textPaint);
        } else {
            if (progress > 100) return;
            //图片上覆盖的背景进度
            float percent = progress / 100f;
            switch (direct) {
                case UP:
                    canvas.drawRect(0, 0, getWidth(), percent * getHeight(), layerPaint);
                    break;
                case DOWN:
                    canvas.drawRect(0, (1 - percent) * getHeight(), getWidth(), getHeight(), layerPaint);
                    break;
                case LEFT:
                    canvas.drawRect(0, 0, percent * getWidth(), getHeight(), layerPaint);
                    break;
                case RIGHT:
                    canvas.drawRect((1 - percent) * getWidth(), 0, getWidth(), getHeight(), layerPaint);
                    break;
            }
            //文字百分比
            if (hasText)
                canvas.drawText(progress + "%", (getWidth() - textPaint.measureText(progress + "%")) / 2,
                        (getHeight() - (textPaint.descent() + textPaint.ascent())) / 2, textPaint);
        }
    }

    public ProgressView setErrorTips(String errorTips) {
        this.progress = -1;
        layerPaint.setColor(errorLayerColor);
        this.errorTips = errorTips;
        postInvalidate();
        return this;
    }
}
