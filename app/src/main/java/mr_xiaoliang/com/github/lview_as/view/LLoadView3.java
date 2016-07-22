package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.option.LLoadView3Option;

/**
 * Created by LiuJ on 2016/7/20.
 * 文字跳动的加载动画
 */

public class LLoadView3 extends TextView {
    //文本绘制笔
    private Paint textPaint;
    //影子绘制笔
    private Paint shadowPaint;
    //参数项
    private LLoadView3Option option;
    //字体大小
    private int textSize = 0;
    //当前跳动的字
    private int index = 0;
    //当前跳动的字的高度
    private int Y = 0;
    //Y最大值
    private int[] maxY;
    //当前速度
    private float velocity = 0;
    //初速度
    private float[] initialVelocity;
    //加速度
    private float[] acceleratedVelocity;
    //每个字的大小
    private int[] everySizes;
    //文本
    private String[] textArray;
    //是否已经初始化
    private boolean isInit = false;
    //text的X坐标
    private int[] textX;
    //正序
    private boolean sequence = true;
    //文本内容
    private String str;
    //跑起来
    private boolean running = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(option!=null&&str!=null&&str.length()>0){
            if(option.showType == LLoadView3Option.SHOW_TYPE_ALL){
                int length  = option.values.length();
                for(int i = 0;i<length;i++){//绘制影子，分开的原因是为了防止绘制的时候，第二个的影子覆盖住第一个文字
                    drawShadow(textX[i],i,canvas);
                }
                for(int i = 0;i<length;i++){//绘制文字
                    drawText(textX[i],i,canvas);
                }
            }else{
                drawShadow(textX[0],index,canvas);
                drawText(textX[0],index,canvas);
            }
            if(Y>maxY[index]){
                Y = maxY[index];
                if(option.cycleType==LLoadView3Option.CYCLE_TYPE_FIFO){
                    index++;
                    index %= str.length();
                }else{
                    index = sequence?++index:--index;
                    if(index<0){
                        sequence = !sequence;
                        index=1;
                    }else if(index>=option.values.length()){
                        index=option.values.length()-1;
                        sequence = !sequence;
                    }
                }
                velocity = initialVelocity[index];
            }else{
                if(Y<getPaddingTop()+everySizes[index]/2){
                    //纯速度的计算，加速度存在误差，会导致抛出范围，此处为检测到跑到最高位置时，重置速度及位置
                    Y = getPaddingTop()+everySizes[index]/2;
                    velocity = 0;
                }
                Y += velocity;
                velocity+=acceleratedVelocity[index];
            }
            if(running){
                invalidate();
            }
        }
    }

    /**
     * 获取字体的X坐标
     */
    private int getTextX(int i){
        switch (option.showType){
            case LLoadView3Option.SHOW_TYPE_ALL:
                int w = (getWidth()-textSize)/option.values.length();
                return w*i+textSize;
            case LLoadView3Option.SHOW_TYPE_ONCE:
                return getWidth()/2;
        }
        return 0;
    }

    /**
     * 获取影子的颜色
     */
    private void setShadowColor(int i){
        if(option.shadowColors==null||option.shadowColors.length<1){
            shadowPaint.setColor(Color.BLACK);
            return;
        }
        shadowPaint.setColor(option.shadowColors[i%option.shadowColors.length]);
        if(i==index){//如果为跳动的影子，就动态设定影子透明度
            int a = (int) (255*getTextAltitude());
            a = a<0?0:a;
            a = a>255?255:a;
            shadowPaint.setAlpha(a);
        }else{
            shadowPaint.setAlpha(255);
        }
    }

    private void setColor(int i){
        if(option.colors==null||option.colors.length<1){
            textPaint.setColor(Color.BLACK);
            return;
        }
        textPaint.setColor(option.colors[i%option.colors.length]);
    }

    /**
     * 获取当前跳跃高度
     */
    private float getTextAltitude(){
        int length = maxY[index]-getPaddingTop()-everySizes[index]/2;
        int y = Y-getPaddingTop()-everySizes[index]/2;
        y = y<0?0:y;
        return 1.0f*y/length;
    }

    /**
     * 获取Y最低位置
     */
    private int getTextMaxY(int i){
        return (int) (getHeight()-getPaddingBottom()-textSize*0.125f-textSize*getTextFloat(i)*0.5f);
    }

    /**
     * 获取当前文字的文字比例
     */
    private float getTextFloat(int i){
        if(option.textFloat==null||option.textFloat.length<1)
            return 1;
        else{
            return option.textFloat[i%option.textFloat.length];
        }
    }

    /**
     * 获取字体
     */
    private Typeface getFont(int i){
        if(option.typefaces==null||option.typefaces.length<1){
            return Typeface.DEFAULT ;
        }else{
            return option.typefaces[i%option.typefaces.length];
        }
    }

    //绘制影子
    //x坐标，影子的中心点
    //i序号，影子对应文本的序号
    private void drawShadow(int x,int i,Canvas canvas){
        setShadowColor(i);
        float textAltitude = 2-getTextAltitude();
        //二分之一高度
        int heightHalf;
        //二分之一宽度
        int widthHalf;
        if(i==index){
            heightHalf = (int) (everySizes[i]*textAltitude/16);
            widthHalf= (int) (everySizes[i]*textAltitude/2);
        }else{
            heightHalf = everySizes[i]/16;
            widthHalf= everySizes[i]/2;
        }
        //四分之一高度
        int heightQtr = heightHalf/2;
        //四分之一宽度
        int widthQtr = widthHalf/2;
        int y = (int) (maxY[i]+textSize*getTextFloat(i)/2);
        Path shadow = new Path();
        shadow.moveTo(x-widthHalf,y);
        shadow.cubicTo(x-widthHalf,y-heightQtr,x-widthQtr,y-heightHalf,x,y-heightHalf);
        shadow.cubicTo(x+widthQtr,y-heightHalf,x+widthHalf,y-heightQtr,x+widthHalf,y);
        shadow.cubicTo(x+widthHalf,y+heightQtr,x+widthQtr,y+heightHalf,x,y+heightHalf);
        shadow.cubicTo(x-widthQtr,y+heightHalf,x-widthHalf,y+heightQtr,x-widthHalf,y);
        shadow.close();
        canvas.drawPath(shadow,shadowPaint);
    }
    //绘制文本
    //x，y文本坐标
    //i序号，文本的序号
    private void drawText(int x,int i,Canvas canvas){
        textPaint.setTextSize(everySizes[i]);
        textPaint.setTypeface(getFont(i));
        setColor(i);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
        int y;
        if(i==index){
//            y = (int) (getTextAltitude()*(maxY[i]-getPaddingTop())+getPaddingTop());
            float textAltitude = getTextAltitude();
            float a;
            if(velocity<0){
                a = option.rotationNum*180*(1-textAltitude);
            }else{
                a = option.rotationNum*180*(1+textAltitude);
            }
            canvas.save();
            canvas.rotate(a,x,Y);
            canvas.drawText(textArray[i],x,Y+textY,textPaint);
            canvas.restore();
        }else{
            y = maxY[i];
            canvas.drawText(textArray[i],x,y+textY,textPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (option!=null && !isInit&&str!=null&&str.length()>0){
            int length = option.values.length();
            maxY = new int[length];
            everySizes = new int[length];
            textArray = new String[length];
            textX = new int[length];
            initialVelocity = new float[length];
            acceleratedVelocity = new float[length];
            for(int i = 0;i<maxY.length;i++){
                maxY[i] = getTextMaxY(i);
                everySizes[i] = (int) (textSize*getTextFloat(i));
                textArray[i] = str.substring(i,i+1);
                textX[i] = getTextX(i);
                initialVelocity[i] = -1.0f*(maxY[i]-getPaddingTop()-everySizes[i]/2)/option.velocity*2;
                acceleratedVelocity[i] = -initialVelocity[i]/option.velocity;
            }
            velocity = initialVelocity[0];
            index = 0;
            Y = maxY[0];
            index = 0;
            isInit = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(option!=null||str==null||str.length()<1){
            textSize = option.textSize;
            if(textSize<0)
                textSize = (int) getTextSize();
            setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        }else{
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
    }
    private int measureWidth(int measureSpec) {
        /*
        option.values.length()+1
        因为影子在字体跳动时，影子会有扩散效果，所以需要留出扩散的空间
         */
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            if(option.showType==LLoadView3Option.SHOW_TYPE_ALL)
                textSize = Math.min(textSize,(specSize-getPaddingRight()-getPaddingLeft())/(option.values.length()+1));
            else
                textSize = Math.min(textSize,(specSize-getPaddingRight()-getPaddingLeft())/2);
            result = specSize;
        } else {
            if(option.showType==LLoadView3Option.SHOW_TYPE_ALL)
                result = textSize*(option.values.length()+1)+getPaddingLeft()+getPaddingRight();
            else
                result = textSize+getPaddingLeft()+getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST && result>specSize) {
                result = specSize;
                textSize = Math.min(textSize,(specSize-getPaddingRight()-getPaddingLeft())/(option.values.length()+1));
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
            textSize = (int) Math.min(textSize,(specSize-getPaddingTop()-getPaddingBottom())*option.heightProportion);
        } else {
            result = (int) ((textSize / option.heightProportion) + getPaddingTop() + getPaddingBottom());
            if (specMode == MeasureSpec.AT_MOST && result>specSize) {
                result = specSize;
                textSize = (int) Math.min(textSize,(specSize-getPaddingTop()-getPaddingBottom())*option.heightProportion);
            }
        }
        return result;
    }

    private void init(){
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setDither(true);

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setDither(true);

        str = getText().toString();
    }

    public LLoadView3(Context context) {
        this(context,null);
    }

    public LLoadView3(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LLoadView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        str = text.toString();
        isInit = false;
        requestLayout();
    }

    public void setOption(LLoadView3Option option) {
        this.option = option;
        str = option.values;
        isInit = false;
        requestLayout();
    }
    public void stopRunning(){
        running = false;
    }
    public void startRunning(){
        running = true;
        invalidate();
    }
}
