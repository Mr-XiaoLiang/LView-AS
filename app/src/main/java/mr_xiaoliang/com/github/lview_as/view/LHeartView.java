package mr_xiaoliang.com.github.lview_as.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Administrator on 2016/6/4.
 * 心形的View
 * 写一半懒得写了
 */
public class LHeartView extends SurfaceView implements SurfaceHolder.Callback {

    private Path heartPath;
    private Path path;
//    private long startLength = 0,endLength = 0;//开始位置与结束位置所在的长度
    private float[] startLocation,endLocation;//开始与结束的坐标
    private long duration = 3000;//动画持续时间
    private int width,height,border,offetX,offsetY;
    private PathMeasure pathMeasure;//获取Path的中间点
    private MaskFilter maskFilter;//设置绘制样式的
    private static float[] START_LENGTH;
    private int thisProgress = 0;
    private boolean autoAnim = true;
    private Paint pathPaint,pointPaint;
    private SurfaceHolder holder;
    private boolean isRun = true;
    private Thread thread;

    private void init(){
        START_LENGTH = new float[]{border*0.5f+offetX,border+offsetY};
        startLocation = endLocation = START_LENGTH;
        heartPath = new Path();
        heartPath.moveTo(border*0.5f+offetX,border+offsetY);
        heartPath.cubicTo(border*0.5f+offetX,border*2/3+offsetY,
                offetX,border*2/3+offsetY,
                offetX,border/3+offsetY);
        heartPath.cubicTo(offetX,offsetY,
                offetX,offsetY,
                border*0.25f+offetX,offsetY);
        heartPath.cubicTo(border*0.5f+offetX,offsetY,
                border*0.5f+offetX,offsetY,
                border*0.5f+offetX,border*0.3f*offsetY);
        heartPath.cubicTo(border*0.5f+offetX,offsetY,
                border*0.5f+offetX,offsetY,
                border*0.75f+offetX,offsetY);
        heartPath.cubicTo(offetX+border,offsetY,
                offetX+border,offsetY,
                border+offetX,border/3+offsetY);
        heartPath.cubicTo(border+offetX,border*2/3+offsetY,
                border*0.5f+offetX,border*2/3+offsetY,
                border*0.5f+offetX,border+offsetY);
        pathMeasure = new PathMeasure(heartPath,true);
        path = new Path();
        pathPaint = new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        pathPaint.setDither(true);
        BlurMaskFilter bmf = new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID);
        pathPaint.setMaskFilter(bmf);
        pathPaint.setColor(Color.RED);
        pathPaint.setStrokeWidth(20);
        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);
        pointPaint.setDither(true);
        pointPaint.setMaskFilter(bmf);
        pointPaint.setColor(Color.RED);
        if(autoAnim){
//            startStretchPathAnim(duration);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        border = Math.min(width-getPaddingLeft()-getPaddingRight(),height-getPaddingBottom()-getPaddingTop());
        offetX = (int) ((width-border)*0.5);
        offsetY = (int) ((height-border)*0.5);
        init();
    }

    public LHeartView(Context context) {
        this(context,null);
    }
    public LHeartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public LHeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void nextAnim(){
        thisProgress++;
        thisProgress%=3;
        switch (thisProgress){
            case 0 :
                startStretchPathAnim(duration);
                break;
            case 1 :
                startBloomPathAnim(duration);
                break;
            case 2 :
                startShortenPathAnim(duration);
                break;
        }
    }

    // 开启路径动画
    public void startStretchPathAnim(long duration) {
        endLocation = START_LENGTH;
        // 0 － getLength()
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pathMeasure.getPosTan(value, startLocation, null);
                pathMeasure.getSegment(0,value,path,false);
                postInvalidate();
                if(value>pathMeasure.getLength()*0.99&&autoAnim){
                    nextAnim();
                }
            }
        });
        valueAnimator.start();
    }
    // 开启路径动画
    public void startShortenPathAnim(long duration) {
        startLocation = START_LENGTH;
        // 0 － getLength()
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pathMeasure.getPosTan(value, endLocation, null);
                pathMeasure.getSegment(value,pathMeasure.getLength(),path,false);
                postInvalidate();
                if(value>pathMeasure.getLength()*0.99&&autoAnim){
                    nextAnim();
                }
            }
        });
        valueAnimator.start();
    }
    // 开启路径动画
    public void startBloomPathAnim(long duration) {
        endLocation = START_LENGTH;
        // 0 － getLength()
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pathMeasure.getPosTan(value, startLocation, null);
                pathMeasure.getSegment(0,value,path,false);
                postInvalidate();
                if(value>pathMeasure.getLength()*0.99&&autoAnim){
                    nextAnim();
                }
            }
        });
        valueAnimator.start();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }

    /**
     * 爱心的移动算法
     */
    private class MoveEvaluator implements TypeEvaluator<Float>{
        float nowV = 0;
        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {
            if(startValue>endValue){
                throw new RuntimeException("can`t startValue > endValue");
            }
            float v = (endValue-startValue)/duration;
            float g = Math.abs(v*2/duration);//计算加速度
            g = fraction>0.5?-g:g;
            return (nowV+g*duration)*duration*0.5f;
        }
    }
}
