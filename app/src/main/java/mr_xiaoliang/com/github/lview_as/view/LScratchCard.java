package mr_xiaoliang.com.github.lview_as.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import mr_xiaoliang.com.github.lview_as.option.LScratchCardOption;

/**
 * Created by Administrator on 2016/5/30.
 * 刮刮卡View
 * 由于View本身的功能偏向，
 * 所以本View没有对padding进行处理
 */
public class LScratchCard extends View {
    //参数类
    private LScratchCardOption option;
    //覆盖的画笔
    private Paint mulchPaint;
    //内容的画笔
    private Paint valuePaint;
    //手指所在画笔
    private Paint touchPaint;
    //覆盖的图像
    private Bitmap mulchBmp;
    //覆盖层画板
    private Canvas mulchCanvas;
    //内容图像
    private Bitmap valueBmp;
    //内容层画板
    private Canvas valueCanvas;
    //手指绘制的路径
    private Bitmap touchBmp;
    //手指路径的画板
    private Canvas touchCanvas;
    //最终的画布（做一个最终的画布是为了统计已经刮开的大小）
    private Bitmap finishBmp;
    //最终画布的画板
    private Canvas finishCanvas;
    //内容的文字
    private String valueText;
    //覆盖的文字
    private String mulchText;
    //画布的尺寸
    private int width = -1,height = -1;
    //变形矩阵
    private Matrix matrix;
    //矩形所在的外圆（颜色渐变渲染）
    private int radius;
    //连接路径
    private ArrayList<Path> path;
    //上一个点
    private float lastX,lastY;
    //是否显示遮罩
    private boolean noMulch = false;
    //传入一个线程池对象，用来给自动清理异步检测
    private ThreadPoolExecutor threadPool;

    /**
     * 对于透明背景模式：
     * 本来我是打算全部用渲染的，剪切也是用混合渲染，但是我想了下流程，
     * 发现代码量太多，变得繁琐，所以决定还是用Xfermode来剪切，成品后，
     * 看看效果，或者再增加一个混合渲染的模式。暂时第一版就用Xfermode来剪切。
     * 对于不透明背景模式：
     * 由于背景不透明，所以我就直接取巧，把覆盖层放在下面，然后把手指轨迹用图片来填充渲染。
     *
     * 但是不管是哪种模式，都是只有两张图片来回折腾，我认为这比传统的刮刮卡模式要简洁，
     * 节省资源，所有的图层绘制全部在初始化的时候合成为两个图片，并且以渲染方式设置
     * 给画笔，后期不修改成熟的情况下，不会增加消耗，仅仅是把两张图片反复绘制而已。
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if(option == null||width<1||height<1)
            return;
        if(noMulch){//如果清除遮罩，那么直接绘制结果
            canvas.drawRect(0,0,width,height,valuePaint);
            return;
        }
        if(option.transparentBg){
            //画背景
            finishCanvas.drawRect(0,0,width,height,valuePaint);
//            canvas.drawRect(0,0,width,height,mulchPaint);
            touchCanvas.drawRect(0,0,width,height,mulchPaint);
            // 我们在绘制路径之前设置我们的mulchPaint
            mulchPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mulchPaint.setStyle(Paint.Style.STROKE);
            for(Path p:path)//使用一个集合的原因是：每次按下抬起都是一个独立的path，不应该让按下抬起的的位置连接起来
                touchCanvas.drawPath(p, mulchPaint);
            mulchPaint.setXfermode(null);//要把笔还原，不然下次会接着再切
            finishCanvas.drawBitmap(touchBmp,0,0,touchPaint);
        }else{
            finishCanvas.drawRect(0,0,width,height,mulchPaint);
            valuePaint.setStyle(Paint.Style.STROKE);
            for(Path p:path)
                finishCanvas.drawPath(p, valuePaint);
        }
        mulchPaint.setStyle(Paint.Style.FILL);
        valuePaint.setStyle(Paint.Style.FILL);
//        canvas.drawBitmap(finishBmp,0,0,null);
        touchPaint.setShader(new BitmapShader(finishBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(0,0,width,height,option.roundRadius,option.roundRadius,touchPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Path p;
        if(option==null||noMulch)
            return false;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(lastX-event.getX())>option.jitter||Math.abs(lastY-event.getY())>option.jitter){
                    p = path.get(path.size()-1);//获取最后一个，也就是当前的轨迹
                    //将上一个点和本点位置进行贝塞尔曲线连接，保证平滑度
                    p.quadTo(lastX,lastY,event.getX(),event.getY());
                    lastX = event.getX();
                    lastY = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(threadPool!=null){
                    threadPool.execute(new CheckAutoClean());
                }else{
                    new Thread(new CheckAutoClean()).start();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                p = new Path();
                p.moveTo(event.getX(),event.getY());
                lastX = event.getX();
                lastY = event.getY();
                path.add(p);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(option == null)//如果option没有，就是用默认的方式测量尺寸
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        else
            setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }
    //宽度测量
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//指定高度
            result = specSize;
        } else {
            //自适应的时候，就找出最大的图片，然后使用最大的尺寸
            if(option.backgroundImg!=null)
                result = Math.max(result,option.backgroundImg.getWidth());
            if(option.mulchImg != null)
                result = Math.max(result,option.mulchImg.getWidth());
            if(option.valueImg != null)
                result = Math.max(result,option.valueImg.getWidth());
            if(!TextUtils.isEmpty(option.text)){
                result = Math.max(result,option.text.length()*option.textSize);
            }
            if(!TextUtils.isEmpty(option.mulchText)){
                result = Math.max(result,option.mulchText.length()*option.mulchTextSize);
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    //高度测量
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if(option.backgroundImg!=null)
                result = Math.max(result,option.backgroundImg.getHeight());
            if(option.mulchImg != null)
                result = Math.max(result,option.mulchImg.getHeight());
            if(option.valueImg != null)
                result = Math.max(result,option.valueImg.getHeight());
            if(!TextUtils.isEmpty(option.text)){
                result = Math.max(result,option.textSize);
            }
            if(!TextUtils.isEmpty(option.mulchText)){
                result = Math.max(result,option.mulchTextSize);
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private void init(){
        if(option == null||width<1||height<1)
            return;
        valueText = option.text;
        mulchText = option.mulchText;
        mulchPaint.setStrokeWidth(option.touchWdth);
        valuePaint.setStrokeWidth(option.touchWdth);
        touchPaint.setStrokeWidth(option.touchWdth);
        if(mulchBmp==null||mulchBmp.getWidth()!=width||mulchBmp.getHeight()!=height){
            //如果覆盖层的画布为空，或者尺寸不等就重新创建画布
            mulchBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            //画布换成新的了，画板也换成新的
            mulchCanvas = new Canvas(mulchBmp);
        }
        if(valueBmp==null||valueBmp.getWidth()!=width||valueBmp.getHeight()!=height){
            valueBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            valueCanvas = new Canvas(valueBmp);
        }
        if(option.transparentBg&&(touchBmp==null||touchBmp.getWidth()!=width||touchBmp.getHeight()!=height)){
            touchBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            touchCanvas = new Canvas(touchBmp);
        }
        if(finishBmp==null||finishBmp.getWidth()!=width||finishBmp.getHeight()!=height){
            finishBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            finishCanvas = new Canvas(finishBmp);
        }
        path.clear();
        noMulch = false;
        initValue();
        initMuich();
    }
    //初始化内容层
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initValue(){
        if(option == null||width<0||height<0)
            return;
        //先画背景层
        //绘制颜色背景，最低优先级
        if(option.backgroundColor!=null&&option.backgroundColor.length>0){
            if(option.backgroundColor.length>1){
                int[] xy = getLinearGradientXY(option.backgroundColorAngle,width,height,0,0);
                Shader bgShader = new LinearGradient(xy[0],xy[1],xy[2],xy[3],option.backgroundColor,null,Shader.TileMode.MIRROR);
                valuePaint.setShader(bgShader);
            }else{
                valuePaint.setColor(option.backgroundColor[0]);
                valuePaint.setShader(null);
            }
            //内容图层绘制背景
            valueCanvas.drawRect(0,0,width,height,valuePaint);
        }
        //绘制图片背景，中等优先级
        if(option.backgroundImg!=null){
            valuePaint.setShader(bitmapChange(option.backgroundImg,option.backgroundImgScaleType));
            //内容图层绘制背景
            drawImg(valueCanvas,option.backgroundImgScaleType,option.backgroundImg,valuePaint);
        }
        //绘制渲染背景
        if(option.backgroundImgShader!=null){
            //绘制渲染，最高优先级
            valuePaint.setShader(option.backgroundImgShader);
            //内容图层绘制背景
            valueCanvas.drawRect(0,0,width,height,valuePaint);
        }
        //再画图片内容层
        if(option.valueImg!=null){
            valuePaint.setShader(bitmapChange(option.valueImg,option.valueImgScaleType));
            //内容图层绘制背景
            drawImg(valueCanvas,option.valueImgScaleType,option.valueImg,valuePaint);
        }
        //绘制文本
        if(!TextUtils.isEmpty(valueText)){
            int valueTextSize = option.textSize;
            if(valueTextSize*valueText.length()>width){
                valueTextSize = width/valueText.length();
            }
            valueTextSize = height>valueTextSize?valueTextSize:height;
            valuePaint.setTextSize(valueTextSize);

            if(option.textColorShader!=null){
                valuePaint.setShader(option.textColorShader);
            }else if(option.textColor!=null&&option.textColor.length>0){
                if(option.textColor.length>1){
                    int[] xy = getLinearGradientXY(option.textColorAngle,valueText.length()*option.textSize,option.textSize, (int) ((width-valueText.length()*option.textSize)*0.5), (int) ((height-option.textSize)*0.5)
                    );
                    Shader bgShader = new LinearGradient(xy[0],xy[1],xy[2],xy[3],option.textColor,null,Shader.TileMode.MIRROR);
                    valuePaint.setShader(bgShader);
                }else{
                    valuePaint.setShader(null);
                    valuePaint.setColor(option.textColor[0]);
                }
            }
            Paint.FontMetrics fm = valuePaint.getFontMetrics();
            //位置矫正
            float textY = height/2 - fm.descent + (fm.descent - fm.ascent) / 2;
            valueCanvas.drawText(valueText,width/2,textY,valuePaint);
        }
        //画笔的渲染设置完成
        valuePaint.setShader(new BitmapShader(valueBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    }

    //初始化覆盖层
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initMuich(){
        if(option == null||width<0||height<0)
            return;
        //绘制颜色背景，最低优先级
        if(option.mulchColor!=null&&option.mulchColor.length>0){
            if(option.mulchColor.length>1){
                int[] xy = getLinearGradientXY(option.mulchColorAngle,width,height,0,0);
                Shader bgShader = new LinearGradient(xy[0],xy[1],xy[2],xy[3],option.mulchColor,null,Shader.TileMode.MIRROR);
                mulchPaint.setShader(bgShader);
            }else{
                mulchPaint.setColor(option.mulchColor[0]);
                mulchPaint.setShader(null);
            }
            //内容图层绘制背景
            mulchCanvas.drawRect(0,0,width,height,mulchPaint);
        }
        //绘制图片背景，中等优先级
        if(option.mulchImg!=null){
            mulchPaint.setShader(bitmapChange(option.mulchImg,option.mulchImgScaleType));
            //内容图层绘制背景
            drawImg(mulchCanvas,option.mulchImgScaleType,option.mulchImg,mulchPaint);
        }
        //绘制文本
        if(!TextUtils.isEmpty(mulchText)){

            int mulchTextSize = option.mulchTextSize;
            if(mulchTextSize*mulchText.length()>width){
                mulchTextSize = width/mulchText.length();
            }
            mulchTextSize = height>mulchTextSize?mulchTextSize:height;
            mulchPaint.setTextSize(mulchTextSize);

            if(option.mulchTextColorShader!=null){
                mulchPaint.setShader(option.mulchTextColorShader);
            }else if(option.mulchTextColor!=null&&option.mulchTextColor.length>0){
                int[] xy = getLinearGradientXY(
                        option.mulchTextColorAngle,
                        mulchText.length()*option.mulchTextSize,
                        option.mulchTextSize,
                        (int)((width-mulchText.length()*option.mulchTextSize)*0.5),
                        (int)((height-option.mulchTextSize)*0.5));
                Shader bgShader = new LinearGradient(xy[0],xy[1],xy[2],xy[3],option.mulchTextColor,null,Shader.TileMode.MIRROR);
                mulchPaint.setShader(bgShader);
            }
            Paint.FontMetrics fm = mulchPaint.getFontMetrics();
            //位置矫正
            float textY = height/2 - fm.descent + (fm.descent - fm.ascent) / 2;
            mulchCanvas.drawText(mulchText,width/2,textY,mulchPaint);
        }
        //画笔的渲染设置完成
        mulchPaint.setShader(new BitmapShader(mulchBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    }

    //绘制图片的方法（把图片的绘制处理集中起来，修改方便。PS：其实是我懒得复制粘贴一大片，不好看，也太麻烦）
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawImg(Canvas canvas, ImageView.ScaleType st, Bitmap bitmap,Paint paint){
        int tx,ty;
        float f;
        int w,h;
        if(st == ImageView.ScaleType.CENTER){
            //如果使用居中模式，那么就把图片移动到中间再来绘制
            //此处及以下，使用乘法而不使用除法的原因是，为了防止图片和View一样大的时候，0/2造成异常
            tx = (int) ((width-bitmap.getWidth())*0.5);
            ty = (int) ((height-bitmap.getHeight())*0.5);
            canvas.save();
            canvas.translate(tx,ty);
            canvas.drawRect(0,0,bitmap.getWidth(),bitmap.getHeight(),paint);
            canvas.restore();
        }else if(st == ImageView.ScaleType.CENTER_INSIDE){
            f = Math.min(1.0f*width/bitmap.getWidth(),1.0f*height/bitmap.getHeight());
            ty = (int) ((height-bitmap.getHeight()*f)*0.5);
            tx = (int) ((width-bitmap.getWidth()*f)*0.5);
            w = (int) (bitmap.getWidth()*f);
            h = (int) (bitmap.getHeight()*f);
            canvas.save();
            canvas.translate(tx,ty);
            canvas.drawRect(0,0,w,h,paint);
            canvas.restore();
        }else if(st == ImageView.ScaleType.CENTER_CROP){
            f = Math.max(1.0f*width/bitmap.getWidth(),1.0f*height/bitmap.getHeight());
            ty = (int) ((height-bitmap.getHeight()*f)*0.5);
            tx = (int) ((width-bitmap.getWidth()*f)*0.5);
            w = (int) (bitmap.getWidth()*f);
            h = (int) (bitmap.getHeight()*f);
            canvas.save();
            canvas.translate(tx,ty);
//            canvas.drawRoundRect(-tx,-ty,width,height,option.roundRadius,option.roundRadius,paint);
            canvas.drawRect(-tx,-ty,w,h,paint);
            canvas.restore();
        }else{
            canvas.drawRect(0,0,width,height,paint);
        }
    }

    //将线性的颜色渐变角度转换为渲染器需要的两个坐标点
    private int[] getLinearGradientXY(float a,int w,int h,int x,int y){
        int[] xy = new int[4];
        int r = (int) (Math.sqrt(w*w+h*h)*0.5);
        if(a%90<1){//垂直于边，则不把坐标点放在外圆上
            switch ((int)a/90%4){//取得角度的朝向
                case 0://角度平行于宽，则直接取两左右边的中点
                    xy[0] = 0;
                    xy[1] = xy[3] = h/2;
                    xy[2] = w;
                    break;
                case 1://角度垂直于宽，则直接取上下两边中点
                    xy[1] = 0;
                    xy[0] = xy[2] = w/2;
                    xy[3] = h;
                    break;
                case 2://角度平行于宽，则直接取两左右边的中点
                    xy[2] = 0;
                    xy[1] = xy[3] = h/2;
                    xy[0] = w;
                    break;
                case 3://角度垂直于宽，则直接取上下两边中点
                    xy[3] = 0;
                    xy[0] = xy[2] = w/2;
                    xy[1] = h;
                    break;
            }
        }else{//根据旋转的角度，计算线段的起始点与终点，这是个三角函数（我讨厌三角函数）
            xy[0] = (int) (Math.cos(Math.toRadians(a))*r+w/2+x);
            xy[1] = (int) (Math.sin(Math.toRadians(a))*r+h/2+y);
            xy[2] = (xy[0]-w/2)*-1+w/2+x;
            xy[3] = (xy[1]-h/2)*-1+h/2+y;
        }
        return xy;
    }

    //将传入的图片剪切成需要的样子（将不合尺寸的图片进行拉伸或者剪切）
    private Shader bitmapChange(Bitmap bmp,ImageView.ScaleType st){
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        Shader mBitmapShader = new BitmapShader(bmp, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        float scaleW = 1.0f,scaleH = 1.0f;
        if(st == ImageView.ScaleType.CENTER_CROP){
            scaleW = scaleH = Math.max(1.0f*width / bmp.getWidth(), 1.0f*height / bmp.getHeight());
        }else if(st == ImageView.ScaleType.CENTER_INSIDE){
            scaleW = scaleH = Math.min(1.0f*width / bmp.getWidth(),1.0f* height / bmp.getHeight());
        }else if(st == ImageView.ScaleType.FIT_XY){
            scaleW = 1.0f*width / bmp.getWidth();
            scaleH = 1.0f*height / bmp.getHeight();
        }else if(st == ImageView.ScaleType.CENTER){
            scaleW = 1;
            scaleH = 1;
        }
        if(matrix==null){
            matrix = new Matrix();
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        matrix.setScale(scaleW, scaleH);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(matrix);
        return mBitmapShader;
    }

    /**
     * 异步计算用户擦除的面积
     */
    private class CheckAutoClean implements Runnable {
        @Override
        public void run() {
            int wipeArea = 0;
            // 控件区域总共的像素值
            float totalArea = width * height;
            // 获取bitmap的所有像素信息
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if(finishBmp.getPixel(i, j)!=mulchBmp.getPixel(i, j)){
                        //看看最终的像素点颜色是否和遮罩对应位置的颜色一样
                        //如果不一样，代表这是刮开的，那么久在刮开统计里++
                        wipeArea++;
                    }
                }
            }
            if(1.0*wipeArea/totalArea>option.autoClean){
                noMulch = true;
                postInvalidate();
            }
        }
    };

    public LScratchCard(Context context) {
        this(context,null);
    }
    public LScratchCard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public LScratchCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mulchPaint = new Paint();
        mulchPaint.setAntiAlias(true);//边缘平滑
        mulchPaint.setDither(true);//防止抖动（表示不理解用途）
        mulchPaint.setTextAlign(Paint.Align.CENTER);
        mulchPaint.setStrokeJoin(Paint.Join.ROUND);// 设置连接方式为圆角
        mulchPaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔笔刷类型
        mulchPaint.setStyle(Paint.Style.FILL);
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setDither(true);
        valuePaint.setTextAlign(Paint.Align.CENTER);
        valuePaint.setStrokeJoin(Paint.Join.ROUND);// 设置连接方式为圆角
        valuePaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔笔刷类型
        valuePaint.setStyle(Paint.Style.FILL);
        touchPaint = new Paint();
        touchPaint.setAntiAlias(true);
        touchPaint.setDither(true);
        touchPaint.setColor(Color.BLACK);
        touchPaint.setStrokeJoin(Paint.Join.ROUND);// 设置连接方式为圆角
        touchPaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔笔刷类型
        touchPaint.setStyle(Paint.Style.FILL);
        path = new ArrayList<>();
    }

    /**
     * 设置覆盖层文本
     * @param mulchText
     */
    public void setMulchText(String mulchText) {
        this.mulchText = mulchText;
        initMuich();
    }

    /**
     * 设置内容层文本
     * @param valueText
     */
    public void setValueText(String valueText) {
        this.valueText = valueText;
        initValue();
    }

    /**
     * 设置参数
     * @param option
     */
    public void setOption(LScratchCardOption option) {
        this.option = option;
        init();
    }

    /**
     * 设置一个线程池，用于检查是否自动清理
     * @param threadPool
     */
    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }
}
