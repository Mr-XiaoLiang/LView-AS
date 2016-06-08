package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 登录界面滑动动画
 *
 * @author yanluxi
 *
 */
public class LChooseBgView extends View {
	/**
	 * 是否是左边被选中
	 */
	private boolean isLeft = true;
	/**
	 * 宽度
	 */
	private int width;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 深色画笔
	 */
	private Paint darkPaint;
	/**
	 * 浅色画笔
	 */
	private Paint lightPaint;
	/**
	 * 用来移动的标记
	 */
	private int index = 0;
	/**
	 * 三角形高度
	 */
	private int triHeight;
	/**
	 * 三角形宽度
	 */
	private int triWidth;
	/**
	 * 灰色线的宽度
	 */
	private int lineWidth = 1;
	/**
	 * 左边的文字
	 */
	private String leftName = "";
	/**
	 * 右边的文字
	 */
	private String rightName = "";
	/**
	 * 字体大小
	 */
	private float textSize = 0;
	/**
	 * 字体大小与高的比例
	 */
	private float textRatio = 0.3F;
	/**
	 * 移动步长
	 */
	private int stepSize = 30;
	/**
	 * 深色文字画笔
	 */
	private Paint darkTextPaint;
	/**
	 * 浅色文字画笔
	 */
	private Paint lightTextPaint;
	/**
	 * 点击回调
	 */
	private ChooseBgViewListener listener;
	/**
	 * 背景画笔
	 */
	private Paint bgPaint;
	/**
	 * 是否是三角形
	 */
	private boolean style = false;

	public LChooseBgView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		darkPaint = new Paint();
		darkPaint.setAntiAlias(true);
		darkPaint.setStyle(Paint.Style.FILL);
		// darkPaint.setColor(Color.parseColor(""));//如果自定义灰色,就用这行代码,注释下面那行
		darkPaint.setColor(Color.GRAY);
		darkPaint.setStrokeWidth(lineWidth);// 这里是灰色线的宽度
		lightPaint = new Paint();
		lightPaint.setAntiAlias(true);
		lightPaint.setStyle(Paint.Style.FILL);
		lightPaint.setColor(Color.WHITE);

		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setColor(Color.parseColor("#e7e7e7"));// 背景颜色

		darkTextPaint = new Paint();
		darkTextPaint.setAntiAlias(true);
		darkTextPaint.setTextAlign(Align.CENTER);
		darkTextPaint.setColor(Color.GRAY);

		lightTextPaint = new Paint();
		lightTextPaint.setAntiAlias(true);
		lightTextPaint.setTextAlign(Align.CENTER);
		lightTextPaint.setColor(Color.BLUE);// 文字颜色
	}

	public LChooseBgView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LChooseBgView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		triHeight = height / 6;// 这是计算三角形高度,三角形高度是宽度的一半
		triWidth = triHeight * 2;
		/**
		 * 如果是画线,index的位置定位在线的左侧,也就是说 线条的起点的位移范围在0~width/2
		 * 线条的终点的位移范围在width/2~width index 的位移范围就在0~width/2
		 */
		if (style) {
			if (index == 0) {
				index = width / 4;// 此时是在左下 index=widtn/4*3 是在右下自己选择

			}
		}
		/**
		 * 画背景
		 */
		canvas.drawRect(0, 0, width, height, bgPaint);

		/**
		 * 这是画下面的灰线.注释后就不画了
		 */
		canvas.drawLine(0, height, width, height, darkPaint);
		/**
		 * 这是画上面的灰线.注释后就不画了
		 */
		canvas.drawLine(0, 1, width, 1, darkPaint);
		/**
		 * 画三角形
		 */
		drawTriangle(canvas);
		drawText(canvas);
		/**
		 * 计算位置,如果不符合位置要求就换
		 */
		if (style) {
			if (isLeft) {
				if (index < (width / 4) / stepSize * stepSize) {
					index += stepSize;
					invalidate();
				} else if (index > (width / 4) / stepSize * stepSize) {
					index -= stepSize;
					invalidate();
				}
			} else {
				if (index < (width / 4 * 3) / stepSize * stepSize) {
					index += stepSize;
					invalidate();
				} else if (index > (width / 4 * 3) / stepSize * stepSize) {
					index -= stepSize;
					invalidate();
				}
			}
		} else {
			if (isLeft) {
				if (index < 0) {
					index += stepSize;
					invalidate();
				} else if (index > 0) {
					index -= stepSize;
					invalidate();
				}
			} else {
				if (index < (width / 2) / stepSize * stepSize) {
					index += stepSize;
					invalidate();
				} else if (index > (width / 2) / stepSize * stepSize) {
					index -= stepSize;
					invalidate();
				}
			}
		}
	}

	/**
	 * 绘制字体
	 */
	private void drawText(Canvas canvas) {
		textSize = height * textRatio;
		darkTextPaint.setTextSize(textSize);
		lightTextPaint.setTextSize(textSize);
		// 文字高度矫正
		FontMetrics fm = lightTextPaint.getFontMetrics();
		float textY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		if (isLeft) {
			canvas.drawText(leftName, width / 4, textY, lightTextPaint);
			canvas.drawText(rightName, width / 4 * 3, textY, darkTextPaint);
		} else {
			canvas.drawText(leftName, width / 4, textY, darkTextPaint);
			canvas.drawText(rightName, width / 4 * 3, textY, lightTextPaint);
		}
		// 画分割线
		canvas.drawLine(width / 2, 0, width / 2, height, darkPaint);
	}

	/**
	 * 三角形的绘制方法
	 *
	 * @param canvas
	 */
	private void drawTriangle(Canvas canvas) {
		if(style){
			/**
			 * 先画出三角形的外边
			 */
			Path path = new Path();
			path.moveTo(index, height-triHeight-lineWidth);
			path.lineTo(index-triWidth/2-lineWidth, height);
			path.lineTo(index+triWidth/2+lineWidth, height);
			path.close();
			canvas.drawPath(path, darkPaint);
			/**
			 * 再画出三角形的里边
			 */
			path = new Path();
			path.moveTo(index, height-triHeight);
			path.lineTo(index-triWidth/2, height);
			path.lineTo(index+triWidth/2, height);
			path.close();
			canvas.drawPath(path, lightPaint);
		}else{
			/**
			 * 画出一条线
			 */
			canvas.drawLine(index, height - lineWidth, index + width / 2, height
					- lineWidth, lightTextPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getX() > width / 2) {
				isLeft = false;
			} else {
				isLeft = true;
			}
			if (listener != null) {
				listener.onChooseViewClick(isLeft);
			}
			invalidate();
		}
		return true;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
		invalidate();
	}

	public String getLeftName() {
		return leftName;
	}

	public void setLeftName(String leftName) {
		this.leftName = leftName;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public interface ChooseBgViewListener {
		public void onChooseViewClick(boolean isLeft);
	}

	public void setChooseBgViewListener(ChooseBgViewListener listener) {
		this.listener = listener;
	}

	public boolean getStyle() {
		return style;
	}

	public void setStyle(boolean style) {
		this.style = style;
	}

}
