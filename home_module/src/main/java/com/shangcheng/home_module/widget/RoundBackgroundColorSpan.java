package com.shangcheng.home_module.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：
 * 创建人：niuyunwang
 * 创建时间：2019/4/11 17:42
 * 修改人：niuyunwang
 * 修改时间：2019/4/11 17:42
 * 修改备注：暂无
 */
public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    public RoundBackgroundColorSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
    }
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return ((int)paint.measureText(text, start, end)+30);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color1 = paint.getColor();
        paint.setColor(this.bgColor);
        canvas.drawRoundRect(new RectF(x, top+1, x + ((int) paint.measureText(text, start, end)+20), bottom-1), 15, 15, paint);
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x+10, y, paint);
        paint.setColor(color1);
    }
}

