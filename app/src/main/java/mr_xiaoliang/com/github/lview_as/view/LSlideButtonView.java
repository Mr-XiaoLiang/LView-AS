package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 滑动开关
 *
 * @author LiuJ 一个监听点击和滑动时间的按钮 滑动时,背景颜色随着滑动的距离变深
 */
public class LSlideButtonView extends View {
	/**
	 * 关闭状态的背景色
	 */
	private int offColor = Color.parseColor("#d7f6e5");
	/**
	 * 打开状态的背景色
	 */
	private int onColor = Color.parseColor("#6eda9e");
	/**
	 * 按钮颜色
	 */
	private int btnColor = Color.parseColor("#3fc279");
	/**
	 * 文字颜色
	 */
	private int textColor = Color.WHITE;
	/**
	 * 阴影颜色
	 */
	private int shadowColor = Color.GRAY;
	/**
	 * 阴影画笔
	 */
	private Paint shadowPaint;

	/**
	 * 单位颜色的色值(用来计算颜色变化)
	 */
	private float unitRed;
	/**
	 * 单位颜色的色值(用来计算颜色变化)
	 */
	private float unitBlue;
	/**
	 * 单位颜色的色值(用来计算颜色变化)
	 */
	private float unitGreen;
	/**
	 * 颜色的色值(用来计算颜色变化)
	 */
	private int red;
	/**
	 * 颜色的色值(用来计算颜色变化)
	 */
	private int blue;
	/**
	 * 颜色的色值(用来计算颜色变化)
	 */
	private int green;
	/**
	 * 按钮的状态
	 */
	private boolean type = false;
	/**
	 * 是否手指松开
	 */
	private boolean isTouchUp = true;
	/**
	 * 手指按下时间 用来判断手指事件类型
	 */
	private long downTime = 0;
	/**
	 * 手指松开时间 用来判断手指事件类型
	 */
	private long upTime = 0;
	/**
	 * X坐标,用来让按钮粘手
	 */
	private int X = 0;
	/**
	 * 文字画笔
	 */
	private Paint textPaint;
	/**
	 * 按钮画笔
	 */
	private Paint btnPaint;
	/**
	 * 背景画笔
	 */
	private Paint bgPaint;
	/**
	 * 画布宽度 用来让按钮居中
	 */
	private int width;
	/**
	 * 画布高度, 用来让按钮居中
	 */
	private int height;
	/**
	 * 按钮半径
	 */
	private int radius;
	/**
	 * 单位跨度
	 */
	private int index = 0;
	/**
	 * 回调监听
	 */
	private SlideButtonViewListener lis;
	/**
	 * 角度
	 */
	private float angle = 0;
	/**
	 * 按钮样式
	 */
	private boolean btnType = true;

	public LSlideButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextAlign(Align.CENTER);
		btnPaint = new Paint();
		btnPaint.setAntiAlias(true);
		btnPaint.setStyle(Paint.Style.FILL);
		shadowPaint = new Paint();
		shadowPaint.setAntiAlias(true);
		shadowPaint.setStyle(Paint.Style.FILL);
		init();
	}

	private void init(){
		bgPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(textColor);
		btnPaint.setColor(btnColor);
		shadowPaint.setColor(shadowColor);
		shadowPaint.setAlpha(128);
		red = Color.red(offColor);
		blue = Color.blue(offColor);
		green = Color.green(offColor);
		calculateUnitColor();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		/**
		 * 计算尺寸,让按钮在任何情况下不变形
		 */
		if (width > height * 2) {
			radius = height / 2;
		} else {
			radius = width / 4;
		}
		// 这是加阴影的算法矫正
		// radius*=0.9;
		if (X == 0) {
			X = width / 2 - radius;
		}
		index = radius / 10;
		calculateUnitColor();
		drawBg(canvas);
		drawButton(canvas);
		// 把没有跑完的跑完
		if (isTouchUp) {
			if (type) {
				if (X < (width / 2 + radius)) {
					X += index;
					// 防止跑过头
					if (X > (width / 2 + radius)) {
						X = (width / 2 + radius);
					}
					invalidate();
				}
			} else {
				if (X > (width / 2 - radius)) {
					X -= index;
					// 防止跑过头
					if (X < (width / 2 - radius)) {
						X = (width / 2 - radius);
					}
					invalidate();
				}
			}
		}
	}

	private void getAngle() {
		angle = 720f / (radius * 2);
		angle = (X - (width / 2 - radius)) * angle;
		if (X == (width / 2 - radius)) {
			angle = 0;
		}
		if (X == (width / 2 + radius)) {
			angle = 720;
		}
	}

	/**
	 * 计算当前背景色值
	 */
	private void drawBg(Canvas canvas) {
		int index = X - width / 2 + radius;
		bgPaint.setColor(Color.rgb(red - (int) (index * unitRed), green - (int) (index * unitGreen),
				blue - (int) (index * unitBlue)));
		if (btnType) {
			// 按钮突出
			RectF rect = new RectF(width / 2 - radius * 2, height / 2 - radius * 0.7f, width / 2 + radius * 2,
					height / 2 + radius * 0.7f);
			canvas.drawRoundRect(rect, radius * 0.7f, radius * 0.7f, bgPaint);
		} else {
			// 按钮全包
			RectF rect = new RectF(width / 2 - radius * 2, height / 2 - radius, width / 2 + radius * 2,
					height / 2 + radius);
			canvas.drawRoundRect(rect, radius, radius, bgPaint);
		}
	}

	private void drawButton(Canvas canvas) {
		// 画阴影
		// canvas.drawCircle(X+radius*0.1f, height / 2+radius*0.1f, radius,
		// shadowPaint);
		float t = 2 * radius * 0.5f / radius;
		getAngle();
		t = Math.abs(t * (X - radius * 2));
		canvas.drawCircle(X, height / 2, radius, btnPaint);
		String str;
		if (X > width / 2) {
			str = "ON";
		} else {
			str = "OFF";
		}
		if (t * 3 > radius * 2) {
			t = radius * 2 / 3;
		}
		textPaint.setTextSize(t);
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.rotate(angle, X, height / 2);
		canvas.drawText(str, X, height / 2 + textY, textPaint);
		canvas.rotate(-angle, X, height / 2);
	}

	/**
	 * 单位颜色计算 保证颜色渐变的顺滑
	 */
	private void calculateUnitColor() {
		float startRed = Color.red(offColor);
		float startBlue = Color.blue(offColor);
		float startGreen = Color.green(offColor);
		float endRed = Color.red(onColor);
		float endBlue = Color.blue(onColor);
		float endGreen = Color.green(onColor);
		unitRed = (startRed - endRed) / radius / 2;
		unitBlue = (startBlue - endBlue) / radius / 2;
		unitGreen = (startGreen - endGreen) / radius / 2;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		X = (int) event.getX();
		if (X < (width / 2 - radius)) {
			X = (width / 2 - radius);
		}
		if (X > (width / 2 + radius)) {
			X = (width / 2 + radius);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downTime = System.currentTimeMillis();
			isTouchUp = false;
			break;
		case MotionEvent.ACTION_UP:
			upTime = System.currentTimeMillis();
			isTouchUp = true;
			if (upTime - downTime <= 300) {
				if (type) {
					type = false;
				} else {
					type = true;
				}
			} else {
				if (X > width / 2) {
					type = true;
				} else {
					type = false;
				}
			}
			if (lis != null) {
				lis.SlideButtonOnClick(this, type);
			}
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}

	public LSlideButtonView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LSlideButtonView(Context context) {
		this(context, null);
	}

	/**
	 * 滑动按钮的点击事件
	 *
	 * @author LiuJ
	 *
	 */
	public interface SlideButtonViewListener {
		public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen);
	}

	/**
	 * 设置监听器
	 *
	 * @param lis
	 */
	public void setOnSlideListener(SlideButtonViewListener lis) {
		this.lis = lis;
	}

	/**
	 * 设置是否打开
	 *
	 * @param type
	 */
	public void setOpen(boolean type) {
		this.type = type;
	}

	/**
	 * 是否已打开
	 *
	 * @return
	 */
	public boolean isOpen() {
		return type;
	}

	public boolean isBtnType() {
		return btnType;
	}

	/**
	 * 设置btn的样式
	 * 
	 * @param btnType
	 */
	public void setBtnType(boolean btnType) {
		this.btnType = btnType;
		invalidate();
	}

	public int getOffColor() {
		return offColor;
	}

	public void setOffColor(int offColor) {
		this.offColor = offColor;
		init();
	}

	public int getOnColor() {
		return onColor;
	}

	public void setOnColor(int onColor) {
		this.onColor = onColor;
		init();
	}

	public int getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(int btnColor) {
		this.btnColor = btnColor;
		init();
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		init();
	}
	
}
