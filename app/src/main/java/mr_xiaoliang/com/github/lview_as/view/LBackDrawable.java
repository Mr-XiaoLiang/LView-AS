package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.util.Log;

/**
 * Created by liuj on 2016/10/28.
 * 动态返回键的drawable实现
 * 可以加入到ToolBar中显示,或者其他View
 */

public class LBackDrawable extends Drawable {

    private Paint paint;
    private float progress = 1;
    private int size = 0;
    private Rect bundle;
    private BackType backType = BackType.WINGS;
    private boolean spin = true;
    private Path path;
    private float strokeWidthFloat = 0.13F;

    public LBackDrawable(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        size = dip2px(context,18);
        paint.setStrokeWidth(size*strokeWidthFloat);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }



    public float getProgress() {
        return progress;
    }

    public void setProgress(@FloatRange(from = 0.0, to = 1.0) float progress) {
        if (this.progress != progress) {
            this.progress = progress;
            invalidateSelf();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        return size;
    }

    @Override
    public int getIntrinsicWidth() {
        return size;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bundle = getBounds();
        size = Math.min(bundle.width(),bundle.height());
        paint.setStrokeWidth(size*strokeWidthFloat);
//        Log.d("onBoundsChange",""+paint.getStrokeWidth());
    }

    @Override
    public void draw(Canvas canvas) {
        if(path==null)
            path = new Path();
        path.rewind();
        float wingsLength = (float) Math.sqrt((size*0.8f)*(size*0.8f)/2);
        PointF start = new PointF(bundle.centerX()-size*0.4f,bundle.centerY());
        float topY,topX;
        switch (backType){
            case WINGS://翼展
                if(progress<0.5){
                    path.moveTo(start.x,start.y);
                    path.rLineTo(size*0.8f*progress*2,0);//相对坐标
                }else{
                    //中间的线条
                    path.moveTo(start.x,start.y);
                    path.rLineTo(size*0.8f,0);
                    //下面的线条
                    float pF = (progress-0.5f)/0.5f;
                    topY = (float) (Math.sin(Math.PI*45*(pF)/180)*wingsLength);
                    topX = (float) (Math.cos(Math.PI*45*(pF)/180)*wingsLength);
                    path.moveTo(start.x,start.y);
                    path.rLineTo(topX,topY);
                    //上面的线条
                    path.moveTo(start.x,start.y);
                    path.rLineTo(topX,-topY);
                }
                break;
            case GROWTH://生长
                float lineLength = size*0.8f*progress;
                //中间的线条
                path.moveTo(start.x,start.y);
                path.rLineTo(lineLength,0);
                //下面的线条
                float l = lineLength<wingsLength?lineLength:wingsLength;
                topY = (float) (Math.sin(Math.PI*45/180)*l);
                topX = (float) (Math.cos(Math.PI*45/180)*l);
                path.moveTo(start.x,start.y);
                path.rLineTo(topX,topY);
                //上面的线条
                path.moveTo(start.x,start.y);
                path.rLineTo(topX,-topY);
                break;
            case SWIPING://划动
                if(progress<0.5){
                    path.moveTo(start.x,start.y);
                    path.rLineTo(size*0.8f*progress*2,0);//相对坐标
                }else{
                    float line = size*0.8f;
                    //中间的线条
                    path.moveTo(start.x,start.y);
                    path.rLineTo(line,0);
                    //下面的线条
                    float pF = (progress-0.5f)/0.5f;
                    line = (line-wingsLength)*(1-pF)+wingsLength;
                    topY = (float) (Math.sin(Math.PI*45*(pF)/180)*line);
                    topX = (float) (Math.cos(Math.PI*45*(pF)/180)*line);
                    path.moveTo(start.x,start.y);
                    path.rLineTo(topX,topY);
                    //上面的线条
                    path.moveTo(start.x,start.y);
                    path.rLineTo(topX,-topY);
                }
                break;
        }
//        path.close();
        if(spin){
            canvas.save();
            canvas.rotate(180*(progress-1),bundle.centerX(),bundle.centerY());
        }
        canvas.drawPath(path,paint);
        if(spin){
            canvas.restore();
        }
    }
    public void setAntiAlias(boolean antiAlias){
        paint.setAntiAlias(antiAlias);
        invalidateSelf();
    }
    @Override
    public void setDither(boolean Dither){
        paint.setDither(Dither);
        invalidateSelf();
    }
    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
        invalidateSelf();
    }
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public BackType getBackType() {
        return backType;
    }

    public void setBackType(BackType backType) {
        this.backType = backType;
        invalidateSelf();
    }

    public boolean isSpin() {
        return spin;
    }

    public void setColor(int color){
        paint.setColor(color);
        invalidateSelf();
    }

    public void setSpinEnabled(boolean enabled) {
        if (this.spin != enabled) {
            this.spin = enabled;
            invalidateSelf();
        }
    }

    public void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            invalidateSelf();
        }
    }

    public enum BackType{
        /**
         * 直接像翅膀一样展开
         */
        WINGS,
        /**
         * 增长出来
         */
        GROWTH,
        /**
         * 划动出现
         */
        SWIPING

    }
}
