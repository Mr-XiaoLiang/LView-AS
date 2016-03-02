package mr_xiaoliang.com.github.lview_as.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.option.LWheelViewOption;

/**
 * 滚轮控件
 * 
 * @开始时间 2015-12-08
 * @author LiuJ
 *
 */
public class LWheelView extends View{
	/**
	 * 参数集合
	 */
	private LWheelViewOption option = null;
	/**
	 * 回掉监听
	 */
	private LWheelViewListener listener = null;
	/**
	 * Y轴偏移量
	 */
	private int offsetY = 0;
	/**
	 * 当前选中项
	 */
	private int position = 0;
	/**
	 * 宽度
	 */
	private int width = 0;
	/**
	 * 高度
	 */
	private int height = 0;
	/**
	 * 抬起来的时刻
	 */
	private long upTime = 0;
	/**
	 * 滑动时刻
	 */
	private long moveTime = 0;
	/**
	 * 上一个时刻
	 */
	private long lastTime = 0;
	/**
	 * 速度
	 */
	private float speed = 0;
	/**
	 * 按下位置
	 */
	private int downLocation = -1;
	/**
	 * 上次坐标
	 */
	private int lastLocation = 0;
	/**
	 * 当前的位置
	 */
	private int thisLocation = 0;
	/**
	 * 阻力
	 */
	private float resistance = 0;
	/**
	 * 文字的画笔
	 */
	private Paint paint;
	/**
	 * 文字大小
	 */
	private int textSize = 0;
	/**
	 * 间隔高度
	 */
	private int intervalHeight = 0;
	/**
	 * 初始化状态
	 */
	private boolean initType = false;
	/**
	 * 数据集
	 */
	private List<String> list = null;
	/**
	 * 手指是否离开
	 */
	private boolean touchUp = true;
	/**
	 * 选中背景
	 */
	private Paint bgPaint = null;
	/**
	 * 速度跟踪
	 */
	private VelocityTracker mVelocityTracker;
	/**
	 * 触摸点的ID
	 */
	private int mPointerId;
	/**
	 * 放大镜画笔
	 */
	private Paint magnifierPaint;
	/**
	 * 放大镜背景画笔
	 */
	private Paint magnifierBgPaint;
	
	public LWheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setTextAlign(Align.CENTER);
		magnifierPaint = new Paint();
		magnifierPaint.setAntiAlias(true);
		magnifierPaint.setTextAlign(Align.CENTER);
		magnifierBgPaint = new Paint();
		magnifierBgPaint.setAntiAlias(true);
		magnifierBgPaint.setTextAlign(Align.CENTER);
	}

	public LWheelView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LWheelView(Context context) {
		this(context, null);
	}

	private void init() {
		height = getHeight();
		width = getWidth();
		if (option.getTextSize() < 0) {
			textSize = (int) (height * option.getTextFloat());
		} else {
			textSize = option.getTextSize();
		}
		paint.setTextSize(textSize);
		if (option.getIntervalHeight() < 0) {
			intervalHeight = (int) (height * option.getIntervalFloat());
		} else {
			intervalHeight = option.getIntervalHeight();
		}
		float textSizeFloat = 1.0f * textSize / height / 2;
		LinearGradient lg = new LinearGradient(width/2, 0, width/2, height,
				new int[] { Color.TRANSPARENT, option.getTextColor(), option.getTextColor(), Color.TRANSPARENT },
				new float[] { 0f, 0.5f - textSizeFloat, 0.5f + textSizeFloat, 1f }, Shader.TileMode.CLAMP);
		paint.setShader(lg);
		bgPaint.setColor(option.getSelectedBgColor());
		magnifierBgPaint.setColor(option.getMagnifierBgColor());
		magnifierPaint.setColor(option.getMagnifierTextColor());
		magnifierPaint.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		try {
			if (!initType) {
				init();
				initType = true;
			}
			if (list != null && list.size() > 0) {
				//绘制选中行的背景
				canvas.drawRect(0, height/2-(textSize + intervalHeight)/2, width, height/2+(textSize + intervalHeight)/2, bgPaint);
				// 画选中行
				drawText(canvas, position, height / 2 + offsetY);
				// 画上下行
				int drawLines = getDrawLines();
				int centerLine = 0;
				for (int i = 1; i <= drawLines; i++) {
					// 绘制上一行
					if (position - i >= 0) {
						centerLine = (int) ((height / 2 + offsetY)
								- (i * (textSize + intervalHeight)));
						drawText(canvas, position - i, centerLine);
					}else if(option.isCycle()){
						centerLine = (int) ((height / 2 + offsetY)
								- (i * (textSize + intervalHeight)));
						drawText(canvas, position - i + list.size(), centerLine);
					}
					// 绘制下一行
					if (position + i < list.size()) {
						centerLine = (int) ((height / 2 + offsetY)
								+ (i * (textSize + intervalHeight)));
						drawText(canvas, position + i, centerLine);
					}else if(option.isCycle()){
						centerLine = (int) ((height / 2 + offsetY)
								+ (i * (textSize + intervalHeight)));
						drawText(canvas, position + i - list.size(), centerLine);
					}
				}
				if(option.isMagnifier()){
					canvas.drawBitmap(getMagnifier(), 0, (height-textSize-intervalHeight)/2, null);
				}
			}
			inertia();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * 绘制放大镜部分
	 * @return
	 */
	private Bitmap getMagnifier(){
		int centerLine = 0;
		Bitmap output = Bitmap.createBitmap(width,
				textSize+intervalHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		//绘制选中行的背景
		canvas.drawColor(option.getMagnifierBgColor());
//		canvas.drawRect(0, height/2-(textSize + intervalHeight)/2, width, height/2+(textSize + intervalHeight)/2, magnifierBgPaint);
		drawMagnifier(canvas, position, canvas.getHeight() / 2 + offsetY,magnifierPaint);
		// 绘制上一行
		centerLine = (int) ((canvas.getHeight() / 2 + offsetY)
				- ((textSize + intervalHeight)));
		if (position - 1 >= 0) {
			drawMagnifier(canvas, position - 1, centerLine,magnifierPaint);
		}else if(option.isCycle()){
			drawMagnifier(canvas, position - 1 + list.size(), centerLine,magnifierPaint);
		}
		// 绘制下一行
		centerLine = (int) ((canvas.getHeight() / 2 + offsetY)
				+ (textSize + intervalHeight));
		if (position + 1 < list.size()) {
			drawMagnifier(canvas, position + 1, centerLine,magnifierPaint);
		}else if(option.isCycle()){
			drawMagnifier(canvas, position + 1 - list.size(), centerLine,magnifierPaint);
		}
		return output;
	}
	
	private void inertia(){
		if(!touchUp){
			return;
		}
		if(Math.abs(speed)>10){
			offsetY += speed*0.5;
			speed *= (1-option.getResistance());
		}else if(offsetY!=0){
			offsetY *= 0.6;
			speed = 0;
			if(listener!=null){
				listener.onLWheelViewChange(this, list.get(position), position);
			}
		}else{
			if(listener!=null){
				listener.onLWheelViewChange(this, list.get(position), position);
			}
			return;
		}
		offsetYCheck();
		invalidate();
	}
	
	private void offsetYCheck(){
		if(Math.abs(offsetY)>=(textSize+intervalHeight)/2){
			if(offsetY>0){
				position--;
				position-=Math.abs((offsetY-(textSize+intervalHeight)/2)/(textSize+intervalHeight));
				offsetY = offsetY%(textSize+intervalHeight)-(textSize+intervalHeight);
			}else{
				position++;
				position+=Math.abs((offsetY+(textSize+intervalHeight)/2)/(textSize+intervalHeight));
				offsetY = offsetY%(textSize+intervalHeight)+(textSize+intervalHeight);
			}
		}
		positionOut();
	}
	
	private void positionOut(){
		if(position<0){
			if(option.isCycle()){
				position = list.size()+position;
			}else{
				position = 0;
				offsetY = (textSize+intervalHeight)/2;
				speed = 0;
			}
		} 
		if(position>=list.size()){
			if(option.isCycle()){
				position = position-list.size();
			}else{
				position = list.size()-1;
				offsetY = -(textSize+intervalHeight)/2;
				speed = 0;
			}
		}
	}
	
	/**
	 * 绘制字
	 * @param canvas
	 * @param index
	 * @param centerLine
	 */
	private void drawText(Canvas canvas, int index, int centerLine) {
		paint.setTextSize(getTextSize(index));
		FontMetrics fm = paint.getFontMetrics();
		//位置矫正
		float textY = centerLine - fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(list.get(index), width / 2, textY, paint);
	}
	
	private void drawMagnifier(Canvas canvas, int index, int centerLine,Paint paint){
		paint.setTextSize(getMagnifierTextSize(index));
		FontMetrics fm = paint.getFontMetrics();
		//位置矫正
		float textY = centerLine - fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(list.get(index), width / 2, textY, paint);
	}

	/**
	 * 获取字体大小 保证显示完全
	 * 
	 * @param index
	 * @return
	 */
	private float getTextSize(int index) {
		String str = list.get(index);
		if (str.length() * textSize > width) {
			return width / str.length();
		}
		return textSize;
	}
	
	/**
	 * 获取字体大小 保证显示完全
	 * 
	 * @param index
	 * @return
	 */
	private float getMagnifierTextSize(int index) {
		String str = list.get(index);
		if (str.length() * (textSize+intervalHeight) > width) {
			return width / str.length();
		}
		return textSize+intervalHeight;
	}

	/**
	 * 获取绘制的行数 避免绘制过多的无用数据
	 * 
	 * @return
	 */
	private int getDrawLines() {
		return (height / 2 + Math.abs(offsetY)) / (textSize + intervalHeight) + 1;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		acquireVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchUp = false;
			thisLocation = (int) event.getY();
			lastLocation = (int) event.getY();
			//求第一个触点的id， 此时可能有多个触点，但至少一个  
            mPointerId = event.getPointerId(0);
			break;
		case MotionEvent.ACTION_UP:
			releaseVelocityTracker();
			touchUp = true;
			upTime = System.currentTimeMillis();
			lastLocation = thisLocation;
			thisLocation = (int) event.getY();
//			if(upTime==moveTime){
//				upTime++;
//			}
//			speed = (int) ((thisLocation-lastLocation)/(upTime-moveTime));
//			speed*=100;
			break;
		case MotionEvent.ACTION_MOVE:
			lastLocation = thisLocation;
			thisLocation = (int) event.getY();
			moveTime = System.currentTimeMillis();
			 //求伪瞬时速度  
            mVelocityTracker.computeCurrentVelocity(10);
            speed = mVelocityTracker.getYVelocity(mPointerId);  
			break;
		}
		int move = thisLocation-lastLocation;
		offsetY += move;
		offsetYCheck();
		invalidate();
		return true;
	}

	 /**  
     *  
     * @param event 向VelocityTracker添加MotionEvent  
     *  
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */  
    private void acquireVelocityTracker(final MotionEvent event) {  
        if(null == mVelocityTracker) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
        mVelocityTracker.addMovement(event);  
    }  
    
    /**  
     * 释放VelocityTracker  
     *  
     * @see VelocityTracker#clear()
     * @see VelocityTracker#recycle()
     */  
    private void releaseVelocityTracker() {  
        if(null != mVelocityTracker) {  
            mVelocityTracker.clear();  
            mVelocityTracker.recycle();  
            mVelocityTracker = null;  
        }  
    }  
	
	public LWheelViewOption getOption() {
		return option;
	}

	public void setOption(LWheelViewOption option) {
		this.option = option;
		initType = false;
	}

	public interface LWheelViewListener {
		public void onLWheelViewChange(LWheelView view, String value, int position);
	}

	public LWheelViewListener getListener() {
		return listener;
	}

	public void setListener(LWheelViewListener listener) {
		this.listener = listener;
	}

	public List<String> getData() {
		return list;
	}

	public void setData(List<String> list) {
		this.list = list;
		position = 0;
		invalidate();
	}
	public void selected(int position){
		this.position = position;
		invalidate();
	}
}
