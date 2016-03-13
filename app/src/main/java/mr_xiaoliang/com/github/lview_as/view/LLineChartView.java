package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import mr_xiaoliang.com.github.lview_as.option.LLineChartViewOption;

/**
 * Created by LiuJ on 2016/3/9.
 * 折线图
 * |
 * |         /\
 * |    /\  /  \
 * |   /  \/    \
 * |  /          \
 * |_/_____________
 *
 */
public class LLineChartView extends TextView {

    /**
     * 数据
     */
    private ArrayList<LLineChartBean> beans;
    /**
     * 参数类
     */
    private LLineChartViewOption o;

    /**
     * 横坐标偏移量，用来滑动
     */
    private int offset;
    /**
     * 图表的范围
     */
    private int width,height;
    /**
     * 格子宽度，高度
     */
    private int gridW,gridH;
    /**
     * 画笔
     */
    private Paint chartPaint,lablePaint;
    /**
     * 四个方向的预留
     */
    private int left = 0,right = 0,bottom = 0,top = 0;
    /**
     * 移动步长
     */
    private int step;
    /**
     * 当前步数
     */
    private int index = 0;
    /**
     * 最大步数
     */
    private int maxIndex = 100;
    /**
     * 线条宽度
     */
    private int lineWidth = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        drawShell(canvas);
        if(o.isCurve){
            drawScrollLine(canvas);
        }else{
            drawLine(canvas);
        }
        if(index>0){
            invalidate();
            index--;
        }
    }

    /**
     * 画外壳
     * @param canvas
     */
    private void drawShell(Canvas canvas){
        int x,y;
        String s;
        Paint.FontMetrics fm = lablePaint.getFontMetrics();
        lablePaint.setTextSize(getTextSize());
        lablePaint.setColor(o.scaleColor);
        for(int i = 0;i<o.scaleSize;i++){
            s = (o.maxNum/o.scaleSize*i)+"";
            x = (int) (left-(0.5+s.length()/2)*getTextSize());
            y = (int) (height-bottom-gridH*i- fm.descent + (fm.descent - fm.ascent) / 2);
            canvas.drawText(s,x,y,lablePaint);
        }
        y = (int) (height - getTextSize() - getTextSize() - fm.descent + (fm.descent - fm.ascent) / 2);
        for(int i = 0;i<o.lable.length;i++){
            s = o.lable[i];
            x = left+gridW*i;
            canvas.drawText(s,x,y,lablePaint);
        }
        lablePaint.setStrokeWidth(lineWidth);
        canvas.drawLine(left, 0, left, height - bottom, lablePaint);
        canvas.drawLine(left,height-bottom,height-bottom,width,lablePaint);
        lablePaint.setStrokeWidth(lineWidth / 2);
        lablePaint.setColor(o.gridColor);
        for(int i = 0;i<o.scaleSize;i++){
            canvas.drawLine(left,height-bottom-i*gridH,height-bottom-i*gridH,width,lablePaint);
        }
        for(int i = 0;i<o.lable.length;i++){
            canvas.drawLine(left+i*gridW, 0, left+i*gridW, height - bottom, lablePaint);
        }
    }

    /**
     * 画曲线
     * @param canvas 画布
     */
    private void drawScrollLine(Canvas canvas){
        int x = 0,y = 0;
        int x1 = 0,y1 = 0;
        int x2 = 0,y2 = 0;
        chartPaint.setColor(o.lineColor);
        Paint.FontMetrics fm = chartPaint.getFontMetrics();
        chartPaint.setTextSize(getTextSize());
        float textY = 0;
        for(LLineChartBean bean:beans){
            Path path = new Path();
            for(int i = 0;i<bean.lable.length;i++){
                x = left+i*gridW;
                y = (int) ((1-bean.lable[i]/o.maxNum)*(height-top-bottom)+top);
                x1 = x2 = (int) (left+i*gridW+0.5*gridW);
                if(i>0)
                    y1 = (int) ((1-bean.lable[i-1]/o.maxNum)*(height-top-bottom)+top);
                else
                    y1 = y;
                y2 = y;

                y = y<index*step?y:index*step;
                y1 = y1<index*step?y1:index*step;
                y2 = y2<index*step?y2:index*step;
                if(i==0){
                    path.moveTo(x,y);
                }else{
                    path.cubicTo(x1,y1,x2,y2,x,y);
                }
                canvas.drawCircle(x,y,lineWidth,chartPaint);
                textY = y - lineWidth - getTextSize() - fm.descent + (fm.descent - fm.ascent) / 2;
                canvas.drawText(bean.lable[i]+"",x,textY,chartPaint);
            }
            canvas.drawPath(path,chartPaint);
        }
    }

    /**
     * 画直线
     * @param canvas 画布
     */
    private void drawLine(Canvas canvas){
        int x = 0,y = 0;
        chartPaint.setColor(o.lineColor);
        Paint.FontMetrics fm = chartPaint.getFontMetrics();
        chartPaint.setTextSize(getTextSize());
        float textY = 0;
        for(LLineChartBean bean:beans){
            Path path = new Path();
            for(int i = 0;i<bean.lable.length;i++){
                x = left+i*gridW;
                y = (int) ((1-bean.lable[i]/o.maxNum)*(height-top-bottom)+top);
                y = y<index*step?y:index*step;
                if(i==0){
                    path.moveTo(x,y);
                }else{
                    path.lineTo(x, y);
                }
                canvas.drawCircle(x,y,lineWidth,chartPaint);
                textY = y - lineWidth - getTextSize() - fm.descent + (fm.descent - fm.ascent) / 2;
                canvas.drawText(bean.lable[i]+"",x,textY,chartPaint);
            }
            canvas.drawPath(path,chartPaint);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        width = getWidth();
        height = getHeight();
        onDataChange();
        super.onWindowVisibilityChanged(visibility);
    }

    private void onDataChange(){
        if(o!=null&&o.lable!=null){
            int i = 0;
            for(String l:o.lable){
                if(l.length()>i)
                    i = l.length();
            }
            left = (int) ((o.maxNum+"").length()*getTextSize());
            bottom = top = right = (int) (2*getTextSize());
            if(o.canSlide){
                gridW = (int) ((i+1)*getTextSize());
            }else{
                gridW = (width-left-right)/o.lable.length;
            }
            gridH = (height-top-bottom)/o.scaleSize;
        }
        step = (height-top-bottom)/maxIndex;
        if(lineWidth == 0){
            lineWidth = height/20;
        }
        index = maxIndex;
    }


    public void setBeans(ArrayList<LLineChartBean> beans) {
        this.beans = beans;
        index = maxIndex;
        invalidate();
    }

    public void setOption(LLineChartViewOption o) {
        this.o = o;
        onDataChange();
        invalidate();
    }

    public LLineChartView(Context context) {
        this(context, null, 0);
    }

    public LLineChartView(Context context,LLineChartViewOption o) {
        this(context, null,0);
        this.o = o;
    }

    public LLineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartPaint  = new Paint();
        lablePaint = new Paint();
        chartPaint.setAntiAlias(true);
        lablePaint.setAntiAlias(true);
        chartPaint.setTextAlign(Paint.Align.CENTER);
        lablePaint.setTextAlign(Paint.Align.CENTER);
    }
    public class LLineChartBean{
        float[] lable;
        int color;
    }

}
