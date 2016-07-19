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
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import mr_xiaoliang.com.github.lview_as.option.LLoadView2Option;

/**
 * Created by LiuJ on 2016/3/11.
 * 加载动画，水滴状的
 */
public class LLoadView2 extends View {
    /**
     * view的参数
     */
    private LLoadView2Option option;
    /**
     * 波浪的节点
     */
    private ArrayList<MyPoint> wavePoint;
    /**
     * 水滴的集合
     */
    private ArrayList<MyPoint> dropPoint;
    /**
     * 水滴笔
     */
    private Paint waterPaint;
    /**
     * 边框画笔
     */
    private Paint borderPaint;
    /**
     * 随机数，用来随机波浪和水滴的位置
     */
    private Random random;
    /**
     * 长度和宽度
     */
    private int width = 0,height = 0;
    /**
     * 波浪步长
     */
    private int waveStep = 0;
    /**
     * 水滴步长
     */
    private int dropStep = 0;
    /**
     * 波浪最大起伏空间
     */
    private int maxWave = 0;
    /**
     * 最大水滴尺寸
     */
    private int maxDrop = 0;
    /**
     * 波浪高度
     */
    private int waveHeight = 0;
    /**
     * 波浪最大高度
     */
    private int waveMaxHeight = 0;
    /**
     * XY偏移量
     */
    private int XOS = 0,YOS = 0;
    /**
     * 是否是波峰
     */
    private boolean crest = true;
    /**
     * 波浪直径的浮点数
     */
    private float waveRF = 0.5F;
    /**
     * 拉伸矩阵
     */
    private Matrix matrix;
    /**
     * 临时画布载体
     */
    private Bitmap bitmap;
    /**
     * 临时画布
     */
    private Canvas c;
    /**
     * 图片绘制画笔
     */
    private Paint bmpPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap==null){
            bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            c = new Canvas(bitmap);
        }else{
            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }
        //画水面
        drawWave(c);
        //画水滴
        drawDrop(c);
        //剪切图型
        drawXfermode(canvas);
        //画包边
        drawBorder(canvas);
        //动起来
        run();
    }

    private void run(){
        Iterator<MyPoint> ip = wavePoint.iterator();
        while(ip.hasNext()){
            MyPoint p = ip.next();
            if(p.X<-width*(waveRF+0.1)){
                ip.remove();
            }
        }
        for(MyPoint p : dropPoint){
            if(p.Y>getHeight()-waveHeight+p.R){
                getDrop(1,p);
            }else{
                if(p.Y>0){
                    p.setY((int) (dropStep+p.Y*1.03));
                }else{
                    p.setY(dropStep+p.Y);
                }
            }
        }
        if(waveHeight<waveMaxHeight){
            waveHeight++;
        }
        if(waveHeight>waveMaxHeight){
            waveHeight--;
        }
        for(int i = 0;i<5-wavePoint.size();i++){
            getWave();
        }
        for(MyPoint p : wavePoint){
            p.setX(p.X - waveStep);
            p.setXz(p.Xz - waveStep);
            int y = p.Y;
            p.setY(waveHeight);
            p.setYz(p.Y-y+p.Yz);
        }
        if(option!=null)
            invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawXfermode(Canvas canvas){
//        waterPaint.setXfermode(null);
//        waterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

//        canvas.drawBitmap(bitmap,0,0,null);

        Shader mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bmpPaint.setShader(mBitmapShader);
        int b = (int) (Math.min(width,height)*option.getBorderWidth());
        if(option.isFitXY()){
            canvas.drawOval(b, b, width - b, height - b, bmpPaint);
        }else{
            canvas.drawCircle(width / 2, height / 2, width / 2 - b, bmpPaint);
        }
//        waterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        waterPaint.setXfermode(null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawBorder(Canvas canvas){
        int b = (int) (Math.min(width,height)*option.getBorderWidth()/2);
        if(option.isFitXY()){
            canvas.drawOval(b,b,width-b,height-b,borderPaint);
        }else{
            canvas.drawCircle(width/2,height/2,width/2-b,borderPaint);
        }
    }

    private void drawDrop(Canvas canvas){
        if(dropPoint!=null&&dropPoint.size()>0){
            Path dropPath;
            for(MyPoint p : dropPoint){
//                canvas.drawCircle(p.X,p.Y,p.R,waterPaint);
                dropPath = new Path();
                dropPath.moveTo(p.X,p.Y-p.R);//移动到水滴的上顶点
                dropPath.cubicTo(p.X,p.Y-p.R*2/3,p.X+p.R*2/3,p.Y-p.R/6,p.X+p.R*2/3,p.Y+p.R/3);//右侧顶点
                dropPath.cubicTo(p.X+p.R*2/3,p.Y+p.R*2/3,p.X+p.R/3,p.Y+p.R,p.X,p.R+p.Y);//底部顶点
                dropPath.cubicTo(p.X-p.R/3,p.Y+p.R,p.X-p.R*2/3,p.Y+p.R*2/3,p.X-p.R*2/3,p.Y+p.R/3);//左侧顶点
                dropPath.cubicTo(p.X-p.R*2/3,p.Y-p.R/6,p.X,p.Y-p.R*2/3,p.X,p.Y-p.R);//连接回上顶点
                canvas.drawPath(dropPath,waterPaint);
            }
        }
    }

    private void drawWave(Canvas canvas){
        if(wavePoint!=null&&wavePoint.size()>0){
            Path path = new Path();
            int x,y,x1,y1,x2,y2;
            for(int i = 0;i<wavePoint.size();i++){
                MyPoint p = wavePoint.get(i);
                MyPoint p2;
                x = p.X;
                y = getHeight()-p.Y-YOS-p.W;
                if(i>0){
                    p2 = wavePoint.get(i-1);
                    x2 = x1 = (p.X+p2.X)/2;
                    y1 = getHeight()-p2.Y-YOS-p2.W;
                    y2 = y;
                    path.cubicTo(x1, y1, x2, y2, x, y);
                }else{
                    path.moveTo(x,y);
                }
//                path.quadTo(p.Xz,getHeight()-p.Yz,p.X,getHeight()-p.Y-YOS);
            }
            path.lineTo(getWidth(),getHeight());
            path.lineTo(0,getHeight());
//            path.close();
            canvas.drawPath(path, waterPaint);
        }
    }

    private void getDrop(int i){
        int r,x,y;
        r = Math.abs(random.nextInt())%maxDrop;
        x = Math.abs(random.nextInt())%width/3+width/3+XOS;
        y = -r-(height-waveHeight)/option.getDropNum()*i+YOS;
        dropPoint.add(new MyPoint(x, y, r));
    }

    private void getDrop(int i,MyPoint p){
        int r,x,y;
        r = Math.abs(random.nextInt())%maxDrop;
        x = Math.abs(random.nextInt())%width/3+width/3+XOS;
        y = -r-(height-waveHeight)/option.getDropNum()*i+YOS;
        p.setX(x);
        p.setY(y);
        p.setR(r);
    }

    private void getWave(){
        int x,y,xz,yz,w;
        if(wavePoint.size()>0){
            x = (int) (wavePoint.get(wavePoint.size()-1).X+width*waveRF);
            y = waveHeight;
            w = random.nextInt()%maxWave+YOS;
//            w = maxWave;
            if(crest){
                w = Math.abs(w);
                crest = false;
            }else{
                w = -Math.abs(w);
                crest = true;
            }
//            int lX = wavePoint.get(wavePoint.size()-1).X;
            xz = (int) (x-width*0.2);
//            yz = (int) ((lY-y-w)*0.3+y+w);
            yz = y+w;
        }else{
            x = XOS;
            y = waveHeight;
            w = random.nextInt()%maxWave+YOS;
            if(crest){
                w = Math.abs(w);
                crest = false;
            }else{
                w = -Math.abs(w);
                crest = true;
            }
            xz = x;
            yz = y+w;
        }
        wavePoint.add(new MyPoint(x, y, xz, yz, w));
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        width = getWidth();
        height = getHeight();
        dataSet();
    }

    private void init(){
        waterPaint = new Paint();
        borderPaint = new Paint();
        bmpPaint = new Paint();
        waterPaint.setStyle(Paint.Style.FILL);
        waterPaint.setAntiAlias(true);
        bmpPaint.setStyle(Paint.Style.FILL);
        bmpPaint.setAntiAlias(true);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        wavePoint = new ArrayList<>();
        dropPoint = new ArrayList<>();
        random = new Random();
    }

    private void dataSet(){
        if(option!=null){
            waterPaint.setColor(option.getWaterColor());
            borderPaint.setColor(option.getBorderColor());
            if(width!=0&&height!=0){
                if(!option.isFitXY()){
                    width = height = Math.min(width,height);
                    XOS = (getWidth()-width)/2;
                    YOS = (getHeight()-height)/2;
                }
                waveStep = (int) (width*option.getWaveStep());
                dropStep = (int) (height*option.getDropStep());
                maxWave = (int) (height*option.getWaveRange());
                maxDrop = (int) (width*option.getMaxDropSize());
                waveMaxHeight = (int) (height*option.getProgress());
                borderPaint.setStrokeWidth(Math.min(width,height)*option.getBorderWidth());
                dropPoint.clear();
                for(int i = 0;i<option.getDropNum();i++){
                    getDrop(i+1);
                }
                wavePoint.clear();
                for(int i = 0;i<10;i++){
                    getWave();
                }
            }
            invalidate();
        }
    }

    public LLoadView2(Context context,LLoadView2Option option) {
        this(context);
        this.option = option;
        dataSet();
    }
    public LLoadView2(Context context) {
        this(context, null, 0);
    }
    public LLoadView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LLoadView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private class MyPoint{
        public int X,Y,R,Xz,Yz,W;
        public MyPoint(int x, int y, int xz, int yz,int w) {
            X = x;
            Y = y;
            Xz = xz;
            Yz = yz;
            W = w;
        }

        public MyPoint(int x, int y, int r) {
            X = x;
            Y = y;
            R = r;
        }

        public void setXz(int xz) {
            Xz = xz;
        }

        public void setYz(int yz) {
            Yz = yz;
        }

        public void setX(int x) {
            X = x;
        }

        public void setY(int y) {
            Y = y;
        }

        public void setR(int r) {
            R = r;
        }

        public void setW(int w) {
            W = w;
        }
    }
    public void setOption(LLoadView2Option option) {
        this.option = option;
        dataSet();
    }
    public void setProgress(float f){
        waveMaxHeight = (int) (height*f);
    }
}
