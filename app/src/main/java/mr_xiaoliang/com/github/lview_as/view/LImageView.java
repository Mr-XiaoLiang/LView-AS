package mr_xiaoliang.com.github.lview_as.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义形状的图片
 * 各种奇怪形状都可以
 * 哇哈哈哈哈哈哈哈
 * 有一点说明，本View将不再支持设置背景
 */
public class LImageView extends ImageView {
    /**
     * 圆角矩形的圆角是高度的一半
     */
    public static final int ROUNDED_RADIUS_HALF = -1;
    /**
     * 圆角矩形的圆角是高度的1/4
     */
    public static final int ROUNDED_RADIUS_QUARTER = -2;
    /**
     * 任意角图形圆角半径——自动
     */
    public static final int CORNER_ROUNDED_RADIUS = -1;
    //是否是预设状态
    private boolean isPreset = true;
    //矩阵
    private Matrix matrix;
    //画笔
    private Paint bitmapPaint;
    //尺寸
    private int width,height;
    //形状路径
    private Path path;
    //手动
    private boolean isManual = false;
    //角数量
    private int corner = 3;
    //任意角形状是否使用圆角
    private boolean cornerRounded = false;
    //角半径
    private int roundedRadius = ROUNDED_RADIUS_HALF;
    //类型
    private ImageShapeType type = ImageShapeType.CIRCULAR;
    //定义角类型
    private PatterningStyle patterningStyle = PatterningStyle.SharpCorner;
    //是否可以点击
    private boolean clickable = false;
    //按下颜色
    private int pressColor = Color.argb(30,255,255,255);
    //是否按下
    private boolean pressDown = false;
    //按下画笔
    private Paint pressPaint;
    //任意角图形圆角半径
    private int cornerRoundedRadius = CORNER_ROUNDED_RADIUS;
    /**
     * 资源
     */
    private Drawable drawable;

    @Override
    protected void onDraw(Canvas canvas) {
        drawable = getDrawable();
        Bitmap bmp;
        if(drawable.getClass()==BitmapDrawable.class){
            bmp = ((BitmapDrawable) drawable).getBitmap().copy(Bitmap.Config.ARGB_8888, true);
        }else{
            super.onDraw(canvas);
            setDrawingCacheEnabled(true);
            buildDrawingCache();
            //上面两行说的必须加入，原因不理解
            bmp = getDrawingCache();
        }
        getBitmap(bmp);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        if(isPreset){//使用预设项
            drawPreset(canvas);
        }else if(isManual){//全手动，传入Path
            if(path==null)
                return;
            canvas.drawPath(path,bitmapPaint);
            if(clickable&&pressDown){
                canvas.drawPath(path,pressPaint);
            }
        }else{

        }
    }

    /**
     * 绘制多角矩形
     */
    private void drawCorner(Canvas canvas){
        if(corner<3)
            return;
        float angle = 360.0f/corner;//顶点之间的夹角
        int radius = Math.min(width/2,height/2);//半径
        int[] X = new int[corner];//横坐标集合
        int[] Y = new int[corner];//纵坐标集合
        for(int i = 0;i<corner;i++){
            X[i] = (int) (width / 2 + (Math.cos((angle*i)/180 * Math.PI) * radius));
            Y[i] = (int) (height / 2 + (Math.sin((angle*i)/180 * Math.PI) * radius));
        }
        if(cornerRounded){//任意角使用圆角
            getUnCornerRounded(X, Y);
        }else{//任意角不使用圆角
            getCornerRounded(X,Y,radius);
        }
    }

    /**
     * 获取有圆角的绘制路径
     */
    private void getCornerRounded(int[] X,int[] Y,int radius){
        path = new Path();
        int r = cornerRoundedRadius<0?radius/corner:cornerRoundedRadius;
        int startX,startY,endX,endY,Xz1,Yz1,Xz2,Yz2,x,y;
        switch (patterningStyle){
            case ObtuseAngle://钝角
                for(int i = 0;i<corner*2;i++){
                    startX = X[i-1<0?i-1+corner:i-1];
                    startY = Y[i-1<0?i-1+corner:i-1];
                    endX = X[i];
                    endY = Y[i];
                    x = (endX-startX)*(1-r/radius)+startX;
                    y = (startY-endY)*(1-r/radius)+endY;
                    if(i<1){
                        path.moveTo(x,y);
                    }else{
                        path.lineTo(X[i],Y[i]);
                    }
                }
                break;
            case SharpCorner://锐角
                int a = 0;
                for(int i = 0;i<corner;i++){
                    if(i<1){
                        path.moveTo(X[a],Y[a]);
                    }else{
                        path.lineTo(X[a],Y[a]);
                    }
                    a+=corner/2;
                    a%=corner;
                    if(corner%2==0)
                        a--;
                }
                break;
        }
        path.close();
    }

    /**
     * 获取没有圆角的绘制路径
     */
    private void getUnCornerRounded(int[] X,int[]Y){
        path = new Path();
        switch (patterningStyle){
            case ObtuseAngle://钝角
                for(int i = 0;i<corner;i++){
                    if(i<1){
                        path.moveTo(X[i],Y[i]);
                    }else{
                        path.lineTo(X[i],Y[i]);
                    }
                }
                break;
            case SharpCorner://锐角
                int a = 0;
                for(int i = 0;i<corner;i++){
                    if(i<1){
                        path.moveTo(X[a],Y[a]);
                    }else{
                        path.lineTo(X[a],Y[a]);
                    }
                    a+=corner/2;
                    a%=corner;
                    if(corner%2==0)
                        a--;
                }
                break;
        }
        path.close();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawPreset(Canvas canvas){
        int r = 0;
        switch (type){
            case ELLIPSE:
                canvas.drawOval(
                        getPaddingLeft(), getPaddingTop(),
                        width - getPaddingRight(), height - getPaddingBottom(), bitmapPaint);
                if(clickable&&pressDown){
                    canvas.drawOval(
                            getPaddingLeft(), getPaddingTop(),
                            width - getPaddingRight(), height - getPaddingBottom(), pressPaint);
                }
                break;
            case CIRCULAR:
                r = Math.min(width-getPaddingRight()-getPaddingLeft(),height-getPaddingBottom()-getPaddingTop());
                canvas.drawCircle(width / 2, height / 2, r, bitmapPaint);
                if(clickable&&pressDown){
                    canvas.drawCircle(width / 2, height / 2, r, pressPaint);
                }
                break;
            case ROUNDED:
                if(roundedRadius<0){
                    switch (roundedRadius){
                        case ROUNDED_RADIUS_HALF:
                            r = Math.min(width/2,height/2);
                            break;
                        case ROUNDED_RADIUS_QUARTER:
                            r = Math.min(width/4,height/4);
                            break;
                    }
                }else{
                    r = roundedRadius;
                }
                canvas.drawRoundRect(getPaddingLeft(),getPaddingTop(),getPaddingRight(),
                        getPaddingBottom(),r,r,bitmapPaint);
                if(clickable&&pressDown){
                    canvas.drawRoundRect(getPaddingLeft(),getPaddingTop(),getPaddingRight(),
                            getPaddingBottom(),r,r,pressPaint);
                }
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        width = getWidth();
        height = getHeight();
        super.onLayout(changed, left, top, right, bottom);
    }

    public LImageView(Context context) {
        this(context, null);
    }

    public LImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setStyle(Paint.Style.FILL);
        pressPaint = new Paint();
        pressPaint.setAntiAlias(true);
        pressPaint.setStyle(Paint.Style.FILL);
        pressPaint.setColor(pressColor);
    }
    /**
     * 使用渲染来绘制图片
     */
    public void getBitmap(Bitmap bmp) {
//        Bitmap bmp = ((BitmapDrawable) getDrawable()).getBitmap().copy(Bitmap.Config.ARGB_8888, true);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        Shader mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale;
        // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
        scale = Math.max(width / bmp.getWidth(), height / bmp.getHeight());
        if(matrix==null){
            matrix = new Matrix();
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        matrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(matrix);
        // 设置shader
        bitmapPaint.setShader(mBitmapShader);
    }
    /**
     * 图片预设类型
     */
    public enum ImageShapeType {
        /**
         * 圆形
         */
        CIRCULAR,
        /**
         * 椭圆
         */
        ELLIPSE,
        /**
         * 圆角矩形
         */
        ROUNDED
    }
    /**
     * 图形类型
     */
    public enum PatterningStyle{
        /**
         * 锐角
         */
        SharpCorner,
        /**
         * 钝角
         */
        ObtuseAngle
    }

    /**
     * 设置样式
     */
    public void setType(ImageShapeType type) {
        this.type = type;
        isManual = false;
        isPreset = true;
        invalidate();
    }

    /**
     * 图案路径
     */
    public void setPath(Path path) {
        this.path = path;
        isManual = true;
        isPreset = false;
        invalidate();
    }

    /**
     * 定义圆角矩形的圆角半径
     */
    public void setRoundedRadius(int roundedRadius) {
        this.roundedRadius = roundedRadius;
        this.type = ImageShapeType.ROUNDED;
        isManual = false;
        isPreset = true;
        invalidate();
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        if(l!=null){
            clickable = true;
        }else{
            clickable = false;
        }
    }

    /**
     * 设置任意角是否使用圆角
     */
    public void setCornerRounded(boolean cornerRounded) {
        this.cornerRounded = cornerRounded;
        isManual = false;
        isPreset = false;
        invalidate();
    }

    /**
     * 设置任意角数量
     */
    public void setCorner(int corner) {
        this.corner = corner;
        isManual = false;
        isPreset = false;
        invalidate();
    }

    /**
     * 设定任意角类型
     */
    public void setPatterningStyle(PatterningStyle patterningStyle) {
        this.patterningStyle = patterningStyle;
        isManual = false;
        isPreset = false;
        invalidate();
    }
}
