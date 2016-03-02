package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LLoadView extends View {
	/**
	 * 弧度的画笔
	 */
	private Paint arcPaint1;
	/**
	 * 弧度的画笔
	 */
	private Paint arcPaint2;
	/**
	 * 弧度的画笔
	 */
	private Paint arcPaint3;
	/**
	 * 圆头的画笔
	 */
	private Paint circlePaint1;
	/**
	 * 圆头的画笔
	 */
	private Paint circlePaint2;
	/**
	 * 圆头的画笔
	 */
	private Paint circlePaint3;
	/**
	 * 背景的画笔
	 */
	private Paint bgPaint1;
	/**
	 * 背景的画笔
	 */
	private Paint bgPaint2;
	/**
	 * 背景的画笔
	 */
	private Paint bgPaint3;
	/**
	 * 旋转用的下标
	 */
	private int index = 0;
	/**
	 * 1号颜色
	 */
	private int color1;
	/**
	 * 2号颜色
	 */
	private int color2;
	/**
	 * 3号颜色
	 */
	private int color3;
	/**
	 * 宽
	 */
	private float width;
	/**
	 * 高
	 */
	private float height;
	/**
	 * 半径
	 */
	private float radius;
	/**
	 * 停止
	 */
	private boolean stop = false;
	/**
	 * 是否显示背景
	 */
	private boolean isShowBg = false;
	/**
	 * 背景透明度
	 * 0-255
	 */
	private int bgAlpha = 64;
	
	public LLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		color1 = Color.GREEN;
		color2 = Color.RED;
		color3 = Color.BLUE;
		arcPaint1 = new Paint();
		arcPaint1.setAntiAlias(true);
		arcPaint1.setStyle(Paint.Style.STROKE);
		arcPaint2 = new Paint();
		arcPaint2.setAntiAlias(true);
		arcPaint2.setStyle(Paint.Style.STROKE);
		arcPaint3 = new Paint();
		arcPaint3.setAntiAlias(true);
		arcPaint3.setStyle(Paint.Style.STROKE);
		circlePaint1 = new Paint();
		circlePaint1.setAntiAlias(true);
		circlePaint2 = new Paint();
		circlePaint2.setAntiAlias(true);
		bgPaint3 = new Paint();
		bgPaint3.setAntiAlias(true);
		bgPaint3.setStyle(Paint.Style.STROKE);
		circlePaint3 = new Paint();
		circlePaint3.setAntiAlias(true);
		bgPaint1 = new Paint();
		bgPaint1.setAntiAlias(true);
//		bgPaint1.setStrokeWidth(30);
		bgPaint1.setStyle(Paint.Style.STROKE);
		bgPaint2 = new Paint();
		bgPaint2.setAntiAlias(true);
		bgPaint2.setStyle(Paint.Style.STROKE);

		initPaint();
	}

	public LLoadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LLoadView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		width = getWidth();
		height = getHeight();
		if (width > height) {
			radius = height / 7;
		} else {
			radius = width / 7;
		}
		/**
		 * 画笔宽度
		 */
		arcPaint1.setStrokeWidth(radius);
		arcPaint2.setStrokeWidth(radius);
		arcPaint3.setStrokeWidth(radius);
		bgPaint1.setStrokeWidth(radius);
		bgPaint2.setStrokeWidth(radius);
		bgPaint3.setStrokeWidth(radius);
		/**
		 * 画背景
		 */
		if(isShowBg){
		 canvas.drawCircle(width/2, height/2, radius*3f, bgPaint1);
		 canvas.drawCircle(width/2, height/2, radius*2f, bgPaint2);
		 canvas.drawCircle(width/2, height/2, radius*1f, bgPaint3);
		}
		/**
		 * 圆弧位置
		 */
		RectF rectF1 = getRectF1();
		RectF rectF2 = getRectF2();
		RectF rectF3 = getRectF3();
		float[] loc = getLocation1();
		float[] angle = getAngle1();
		canvas.drawArc(rectF1, angle[0], angle[1], false, arcPaint1);
		//这是画圆弧两头的圆点
		canvas.drawCircle(loc[0], loc[1], radius/2, circlePaint1);
		canvas.drawCircle(loc[2], loc[3], radius / 2, circlePaint1);
		angle = getAngle2();
		loc = getLocation2();
		canvas.drawArc(rectF2, angle[0], angle[1], false, arcPaint2);
		//这是画圆弧两头的圆点
		canvas.drawCircle(loc[0], loc[1], radius/2, circlePaint2);
		canvas.drawCircle(loc[2], loc[3], radius/2, circlePaint2);
		angle = getAngle3();
		loc = getLocation3();
		canvas.drawArc(rectF3, angle[0], angle[1], false, arcPaint3);
		//这是画圆弧两头的圆点
		canvas.drawCircle(loc[0], loc[1], radius/2, circlePaint3);
		canvas.drawCircle(loc[2], loc[3], radius/2, circlePaint3);
		if (!stop) {
			index++;
			invalidate();
			if (index == Integer.MAX_VALUE)
				index = 0;
		}
		super.onDraw(canvas);
	}

	private void initPaint() {
		arcPaint1.setColor(color1);
		arcPaint2.setColor(color2);
		arcPaint3.setColor(color3);
		circlePaint1.setColor(color1);
		circlePaint2.setColor(color2);
		circlePaint3.setColor(color3);
		bgPaint1.setColor(color1);
		bgPaint1.setAlpha(bgAlpha);
		bgPaint2.setColor(color2);
		bgPaint2.setAlpha(bgAlpha);
		bgPaint3.setColor(color3);
		bgPaint3.setAlpha(bgAlpha);
	}

	/**
	 * 圆弧位置
	 *
	 * @return
	 */
	private RectF getRectF1() {
		if (width > height) {
			return new RectF((width - height) / 2 + (radius * 0.5f),
					(radius * 0.5f), width - (width - height) / 2
					- (radius * 0.5f), height - (radius * 0.5f));
		} else {
			return new RectF((radius * 0.5f), (height - width) / 2
					+ (radius * 0.5f), width - (radius * 0.5f), height
					- (height - width) / 2 - (radius * 0.5f));
		}
	}

	/**
	 * 圆弧位置
	 *
	 * @return
	 */
	private RectF getRectF2() {
		if (width > height) {
			return new RectF((width - height) / 2 + (radius * 1.5f),
					(radius * 1.5f), width - (width - height) / 2
					- (radius * 1.5f), height - (radius * 1.5f));
		} else {
			return new RectF(radius * 1.5f, (height - width) / 2
					+ (radius * 1.5f), width - (radius * 1.5f), height
					- (height - width) / 2 - (radius * 1.5f));
		}
	}

	/**
	 * 圆弧位置
	 *
	 * @return
	 */
	private RectF getRectF3() {
		if (width > height) {
			return new RectF((width - height) / 2 - (radius * 2.5f),
					(radius * 2.5f), width - (width - height) / 2
					- (radius * 2.5f), height - (radius * 2.5f));
		} else {
			return new RectF((radius * 2.5f), (height - width) / 2
					+ (radius * 2.5f), width - (radius * 2.5f), height
					- (height - width) / 2 - (radius * 2.5f));
		}
	}

	/**
	 * 获取角度
	 *
	 * @param x
	 * @return
	 */
	private float[] getAngle1() {
		double angle = (Math.cos(index * 0.01 - Math.PI) + 1d);
		float top = (float) (angle * 150);
		float botton = (index%180)*2;
		return new float[] { botton, top };
	}

	/**
	 * 获取角度
	 *
	 * @param x
	 * @return
	 */
	private float[] getAngle2() {
		double angle = (Math.cos((index * 0.02) - Math.PI) + 1d);
		float top = (float) (angle * 150);
		float botton = (index%90)*4;
		return new float[] { botton, top };
	}

	/**
	 * 获取角度
	 *
	 * @param x
	 * @return
	 */
	private float[] getAngle3() {
		double angle = (Math.cos((index * 0.03) - Math.PI) + 1d);
		float top = (float) (angle * 150);
		float botton = (index%60)*6;
		return new float[] { botton, top };
	}

	/**
	 * 点坐标获取
	 * @return
	 */
	private float[] getLocation1() {
		float[] ang = getAngle1();
		float x1 = (float) (width / 2 + (Math.cos(ang[0]/180 * Math.PI) * radius * 3));
		float y1 = (float) (height / 2 + (Math.sin(ang[0]/180 * Math.PI) * radius * 3));
		float x2 = (float) (width / 2 + (Math.cos((ang[1]+ang[0])/180 * Math.PI) * radius * 3));
		float y2 = (float) (height / 2 + (Math.sin((ang[1]+ang[0])/180 * Math.PI) * radius * 3));
		return new float[] { x1, y1, x2, y2 };
	}
	
	private float[] getLocation2() {
		float[] ang = getAngle2();
		float x1 = (float) (width / 2 + (Math.cos(ang[0]/180 * Math.PI) * radius * 2));
		float y1 = (float) (height / 2 + (Math.sin(ang[0]/180 * Math.PI) * radius * 2));
		float x2 = (float) (width / 2 + (Math.cos((ang[1]+ang[0])/180 * Math.PI) * radius * 2));
		float y2 = (float) (height / 2 + (Math.sin((ang[1]+ang[0])/180 * Math.PI) * radius * 2));
		return new float[] { x1, y1, x2, y2 };
	}

	private float[] getLocation3() {
		float[] ang = getAngle3();
		float x1 = (float) (width / 2 + (Math.cos(ang[0]/180 * Math.PI) * radius * 1));
		float y1 = (float) (height / 2 + (Math.sin(ang[0]/180 * Math.PI) * radius * 1));
		float x2 = (float) (width / 2 + (Math.cos((ang[1]+ang[0])/180 * Math.PI) * radius * 1));
		float y2 = (float) (height / 2 + (Math.sin((ang[1]+ang[0])/180 * Math.PI) * radius * 1));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * 是否停止
	 *
	 * @param stop
	 */
	public void stop(boolean stop) {
		this.stop = stop;
		invalidate();
	}

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
		initPaint();
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
		initPaint();
	}

	public int getColor3() {
		return color3;
	}

	public void setColor3(int color3) {
		this.color3 = color3;
		initPaint();
	}

	public boolean isShowBg() {
		return isShowBg;
	}

	public void setShowBg(boolean isShowBg) {
		this.isShowBg = isShowBg;
	}

	public int getBgAlpha() {
		return bgAlpha;
	}

	public void setBgAlpha(int bgAlpha) {
		this.bgAlpha = bgAlpha;
		initPaint();
	}
	
}
