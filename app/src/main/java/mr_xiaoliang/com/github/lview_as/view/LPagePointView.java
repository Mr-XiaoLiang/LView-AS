package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LPagePointView extends View {
	/**
	 * 数量
	 */
	private int pointSize = 0;
	/**
	 * 当前变换的点
	 */
	private int pointIndex = 0;
	/**
	 * 点半径
	 */
	private int pointRadius = 5;
	/**
	 * 小点颜色
	 */
	private int pointColor = Color.WHITE;
	/**
	 * 小点的位置定位X
	 */
	private int pointLocationX = 0;
	/**
	 * 小点的位置定位Y
	 */
	private int pointLocationY = 0;
	/**
	 * 小点坐标X
	 */
	private int pointX = 0;
	/**
	 * 小点坐标Y
	 */
	private int pointY = 0;
	/**
	 * 小点画笔
	 */
	private Paint pointPaint;
	/**
	 * 移动进度
	 */
	private float pointPercent = 0;
	/**
	 * 宽度
	 */
	private int width = 0;
	/**
	 * 高度
	 */
	private int height = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		width = getWidth();
		height = getHeight();
		if(pointSize<1){
			return;
		}
		pointPaint.setColor(pointColor);
		pointRadius = Math.min(width/(pointSize*2+2)/2, height/2);
		pointLocationX = width/2-pointRadius*(pointSize*2+2);
		pointLocationY = height/2-pointRadius;
		pointX = pointLocationX;
		pointY = pointLocationY;
		int pointWidth = 0;
		RectF rectF = null;
		for(int i = 0;i<pointSize;i++){
			if(i==pointIndex){
				pointWidth = (int) (pointRadius*6*(1-pointPercent))+pointRadius*2;
			}else if(i==pointIndex+1){
				pointWidth = (int) (pointRadius*6*pointPercent)+pointRadius*2;
			}else{
				pointWidth = pointRadius*2;
			}
			rectF = new RectF(pointX, pointY, pointX+pointWidth, pointY+pointRadius*2);
			canvas.drawRoundRect(rectF, pointRadius, pointRadius, pointPaint);
			pointX += (pointRadius*2+pointWidth);
		}
	}
	/**
	 * 初始化
	 */
	private void init() {
		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);
	}

	public LPagePointView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LPagePointView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LPagePointView(Context context) {
		this(context, null);
	}

	public int getPointSize() {
		return pointSize;
	}

	/**
	 * 设置点数量
	 *
	 * @param pointSize
	 */
	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
		invalidate();
	}

	public int getPointRadius() {
		return pointRadius;
	}

	/**
	 * 设置点颜色
	 *
	 * @param pointColor
	 */
	public void setPointColor(int pointColor) {
		this.pointColor = pointColor;
		invalidate();
	}
	/**
	 * 状态的修改
	 * @param index
	 * @param percent
	 */
	public void onChange(int index,float percent){
		pointIndex = index;
		pointPercent = percent;
		invalidate();
	}

}
