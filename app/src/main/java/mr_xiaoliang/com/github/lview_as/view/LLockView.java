package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import mr_xiaoliang.com.github.lview_as.option.LLockViewOption;

/**
 * Created by LiuJ on 2016/6/23.
 * 一个密码锁的自定义view
 * 支持类型：
 * 1, 连线锁，3*3，4*4，5*5，6*6，x*x
 * 2, 点按锁，3*3，4*4，5*5，6*6，x*x
 */
public class LLockView extends ImageView {

    private LLockViewOption o;
    private int squareHeight = -1,squareWidth = -1;
    private Paint borderPaint,btnBgPaint,centerPaint,pathPaint;
    private int offsetY = 0,offsetX = 0;
    private int width = -1,height = -1;
    private ArrayList<Integer> password;
    private int[][] map;
    private Path path;
    private int lastX,lasyY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(o==null||o.rowSize<1||o.colSize<1){
            return;
        }
        if(o.typeLine&&o.showLine){
            canvas.drawPath(path,pathPaint);
        }
        for(int i = 0;i<o.rowSize;i++){
            for(int j = 0;j<o.colSize;j++){
                canvas.save();
                canvas.clipRect(j*(squareWidth+o.interval)+offsetX,
                        i*(squareHeight+o.interval)+offsetY,
                        (j+1)*(squareWidth+o.interval)+offsetX,
                        (i+1)*(squareHeight+o.interval)+offsetY);
                canvas.translate(j*(squareWidth+o.interval)+offsetX,
                        i*(squareHeight+o.interval)+offsetY);
                onDrowSquare(canvas,squareWidth+o.interval,squareHeight+o.interval,i,j,isPress(i,j));
                canvas.restore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 绘制一个单元格
     * 因为是从整个画布截取的，
     * 所以如果要获取可绘制实际尺寸，
     * 请使用传入的width，height
     * @param canvas 画布
     * @param width 可绘制宽度
     * @param height 可绘制高度
     * @param row 行数
     * @param col 列数
     * @param onPress 是否被按下状态
     */
    protected void onDrowSquare(Canvas canvas,int width,int height,int row,int col,boolean onPress){
        Path p = new Path();
        switch (o.shapeType){
            case Circle:
                p.moveTo(width/2,0);
                p.cubicTo(width-o.interval,o.interval,width-o.interval,o.interval,width-o.interval,height/2);
                p.cubicTo(width-o.interval,height-o.interval,width-o.interval,height-o.interval,width/2,height-o.interval);
                p.cubicTo(o.interval,height-o.interval,o.interval,height-o.interval,o.interval,height/2);
                p.cubicTo(o.interval,o.interval,o.interval,o.interval,width-o.interval,o.interval);
                break;
            case Square:
                p.moveTo(o.interval,o.interval);
                p.lineTo(width-o.interval,height-o.interval);
                p.lineTo(o.interval,height-o.interval);
                p.close();
                break;
        }
        if(onPress){
            borderPaint.setColor(o.borderColorPress);
            btnBgPaint.setColor(o.btnBgColorPress);
            centerPaint.setColor(o.centerCircleColorPress);
        }else{
            borderPaint.setColor(o.borderColor);
            btnBgPaint.setColor(o.btnBgColor);
            centerPaint.setColor(o.centerCircleColor);
        }
        canvas.drawPath(path,btnBgPaint);
        canvas.drawPath(path,borderPaint);
        canvas.drawCircle(width/2,height/2,o.centerRadius,centerPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init();

    }

    /**
     * 居中布局
     * 获取可用区域范围，分别计算纵向和横向的单元格尺寸
     * 取小的单元格，然后重新计算消耗尺寸
     * 得到新的有效区域，得到新的偏移量
     */
    private void onCenterLayout(){
        width = getWidth()-getPaddingBottom()-getPaddingTop();
        height = getHeight()-getPaddingBottom()-getPaddingTop();
        squareHeight = (height)/o.rowSize-o.interval;
        squareWidth = (width)/o.colSize-o.interval;
        squareHeight = squareWidth = Math.min(squareHeight,squareWidth);
        width = (squareWidth+o.interval)*o.colSize;
        height = (squareHeight+o.interval)*o.rowSize;
        offsetX = (getWidth()-width)/2;
        offsetY = (getHeight()-height)/2;
    }

    /**
     * 检查当前块是否被选中了
     * @param row 行
     * @param col 列
     * @return
     */
    private boolean isPress(int row,int col){
        return map[row][col]==1;
    }

    /**
     * 选中一个单元格
     * @param row 行
     * @param col 列
     * @return
     */
    private boolean onPress(int row,int col){
        if(isPress(row,col))//已经选中，那就返回false
            return false;
        password.add(row*o.colSize+col);
        map[row][col] = 1;
        return true;
    }

    /**
     * 平铺布局
     * 得出可用区域后，直接除以单元格数量
     * 得到单元格尺寸
     */
    private void onFitXYLayout(){
        offsetX = getPaddingLeft();
        offsetY = getPaddingTop();
        width = getWidth()-getPaddingBottom()-getPaddingTop();
        height = getHeight()-getPaddingBottom()-getPaddingTop();
        squareHeight = (height)/o.rowSize-o.interval;
        squareWidth = (width)/o.colSize-o.interval;
    }

    private void init(){
        borderPaint = new Paint();
        btnBgPaint = new Paint();
        centerPaint = new Paint();
        pathPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        btnBgPaint.setAntiAlias(true);
        btnBgPaint.setDither(true);
        btnBgPaint.setStyle(Paint.Style.FILL);
        centerPaint.setAntiAlias(true);
        centerPaint.setDither(true);
        centerPaint.setStyle(Paint.Style.FILL);
        pathPaint.setAntiAlias(true);
        pathPaint.setDither(true);
        pathPaint.setStyle(Paint.Style.STROKE);
        initOption();
    }

    private void initOption(){
        if(o==null||width<1||height<1){
            return;
        }
        if(o.rowSize<1||o.colSize<1){
            return;
        }
        switch (o.layoutType){
            case FitXY:
                onFitXYLayout();
                break;
            case Center:
                onCenterLayout();
                break;
        }
        if(borderPaint!=null){
            borderPaint.setStrokeWidth(o.borderWidth);
        }
        if(pathPaint!=null){
            pathPaint.setStrokeWidth(o.lineWidth);
        }
        map = new int[o.rowSize][o.colSize];
    }

    public LLockView(Context context) {
        super(context);
        init();
    }
    public LLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public LLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /**
     * 密码解锁监听器
     */
    public interface OnDeblockingListener{
        void onDeblocking(ArrayList<Integer> password,int[][] map);
    }

    /**
     * 检查密码是否正确
     * @param right 正确密码
     * @param newPwd 待检测密码
     * @return
     */
    public static boolean checkPassword(ArrayList<Integer> right,ArrayList<Integer> newPwd){
        if(right.size()!=newPwd.size())
            return false;
        for(int i = 0;i<right.size();i++){
            if(!right.get(i).equals(newPwd.get(i)))
                return false;
        }
        return true;
    }

    /**
     * 检查密码是否正确
     * @param right 正确密码
     * @param newPwd 待检测密码
     * @return
     */
    public static boolean checkPassword(String right,ArrayList<Integer> newPwd){
        ArrayList<Integer> rightPwd = new ArrayList<>();
        String format = "^\\[([0-9]|,|/s)*\\]$";
        String str = "[1,2,3,4,5,6,7,8,9]";
        if(!str.matches(format)){
            throw new RuntimeException("传入密码不正确，要求格式为：[1,2,3,4,5,6,7,8,9]");
        }
        String[] strArray = str.replaceAll("\\s|\\[|\\]", "").split(",");
        for(String s : strArray){
            rightPwd.add(Integer.valueOf(s));
        }
        return checkPassword(rightPwd,newPwd);
    }

    public String getPassword(){
        return password.toString();
    }

    public void setO(LLockViewOption o) {
        this.o = o;
        initOption();
        invalidate();
    }

    /**
     * 点按模式与拖拽模式
     共同点：
     1，外观相同
     2，返回方式相同（每次连接新的点即返回）
     不同点 ：
     点按模式：
     1，手指移动出点击单元格，本次点击失效。
     2，密码区域可以存在间隔单元格
     3，允许手离开屏幕
     拖拽模式：
     1，手指离开屏幕，完成一次输入。再次按下重新计算。
     2，密码记录手指经过所有单元格
     3，不允许手离开屏幕
     */
}
