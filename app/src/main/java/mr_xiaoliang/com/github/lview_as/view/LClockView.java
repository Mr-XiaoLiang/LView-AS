package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.R;

/**
 * 表盘样式的时间选择器
 *
 * @author Liuj
 *
 */
public class LClockView extends View {

	/*-------------------------常量声明开始-------------------------------*/
	/**
	 * 小时
	 */
	public static final int TYPE_HOURS = 0;
	/**
	 * 分钟(秒同用)
	 */
	public static final int TYPE_MINUTES = 1;
	/*-------------------------常量声明结束-------------------------------*/

	/*-------------------------变量声明开始-------------------------------*/
	/**
	 * 宽度
	 */
	private float width;
	/**
	 * 高度
	 */
	private float height;
	/**
	 * 半径
	 */
	private float radius;
	/**
	 * 时钟类型
	 */
	private int type;
	/**
	 * 小时数组
	 */
	private int[] hoursItems;
	/**
	 * 分钟数组
	 */
	private int[] minutesItems;
	/**
	 * 边框颜色
	 */
	private int borderColor;
	/**
	 * 背景色
	 */
	private int backgroundColor;
	/**
	 * 表盘颜色
	 */
	private int dialColor;
	/**
	 * 边框宽度
	 */
	private float borderWidth;
	/**
	 * 字体大小
	 */
	private float textSize;
	/**
	 * 字体颜色
	 */
	private int textColor;
	/**
	 * 背景图片
	 */
	private int dialImage;
	/**
	 * 指针颜色
	 */
	private int pointerColor;
	/**
	 * 不可用字体颜色
	 */
	private int textColorForUn;
	/**
	 * 刻度颜色
	 */
	private int scaleColor;
	/**
	 * 指针角度
	 */
	private double pointerAngle = 0f;
	/**
	 * 已选中
	 */
	private int selected = 0;
	/**
	 * 已选中颜色
	 */
	private int selectedColor;
	/**
	 * 已选中
	 */
	private int realSelected = 0;
	/**
	 * 指针角度
	 */
	private double realPointerAngle = 0f;
	/**
	 * 以选中画笔
	 */
	private Paint selectedPaint;
	/**
	 * 文字画笔
	 */
	private Paint textPaint;
	/**
	 * 内圈文字画笔
	 */
	private Paint smallTextPaint;
	/**
	 * 文字画笔-- 灰色
	 */
	private Paint textGaryPaint;
	/**
	 * 内圈文字画笔-- 灰色
	 */
	private Paint smallTextGaryPaint;
	/**
	 * 边框画笔
	 */
	private Paint borderPaint;
	/**
	 * 指针画笔
	 */
	private Paint pointerPaint;
	/**
	 * 背景画笔
	 */
	private Paint backgroundPaint;
	/**
	 * 表盘画笔
	 */
	private Paint dialPaint;
	/**
	 * 大刻度画笔
	 */
	private Paint scaleBigPaint;
	/**
	 * 小刻度画笔
	 */
	private Paint scaleSmallPaint;

	/**
	 * 选中文字画笔
	 */
	private Paint selectedTextPaint;
	/**
	 * 选中文字画笔
	 */
	private Paint selectedSmallTextPaint;

	/*-------------------------变量声明结束-------------------------------*/
	/*-------------------------回调函数声明开始-------------------------------*/
	public interface ClockViewListener {
		public void onClockChange(int t);
	}
	/**
	 * 回调函数
	 */
	private ClockViewListener lis;
	/*-------------------------回调函数声明开始-------------------------------*/
	/*-------------------------构造方法开始-------------------------------*/
	public LClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		/**
		 * 变量初始化
		 */
		type = TYPE_HOURS;
		hoursItems = new int[24];
		minutesItems = new int[60];
		borderColor = Color.GRAY;
		dialColor = Color.WHITE;
		backgroundColor = Color.TRANSPARENT;
		textColor = Color.BLACK;
		pointerColor = Color.RED;
		textColorForUn = Color.parseColor("#50000000");
		scaleColor = Color.BLACK;
		selectedColor = Color.parseColor("#99c9f2");
		dialImage = 0;
		for (int i = 0; i < hoursItems.length; i++) {
			hoursItems[i] = i;
		}
		for (int i = 0; i < minutesItems.length; i++) {
			minutesItems[i] = i;
		}
		/**
		 * 获得我们所定义的自定义样式属性
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.clockview, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.clockview_background_color:
					backgroundColor = a.getColor(attr, Color.TRANSPARENT);
					break;
				case R.styleable.clockview_background_image:
					dialImage = a.getInt(attr, 0);
					break;
				case R.styleable.clockview_border_color:
					borderColor = a.getColor(attr, Color.GRAY);
					break;
				case R.styleable.clockview_border_width:
					borderWidth = a.getDimension(attr, 0);
					break;
				case R.styleable.clockview_dial_color:
					dialColor = a.getColor(attr, Color.WHITE);
					break;
				case R.styleable.clockview_pointer_color:
					pointerColor = a.getColor(attr, Color.RED);
					break;
				case R.styleable.clockview_scale_color:
					scaleColor = a.getColor(attr, Color.BLACK);
					break;
				case R.styleable.clockview_text_color:
					textColor = a.getColor(attr, Color.BLACK);
					break;
				case R.styleable.clockview_text_color_un:
					textColorForUn = a.getColor(attr, Color.GRAY);
					break;
				case R.styleable.clockview_type:
					type = a.getInt(attr, TYPE_HOURS);
					break;

			}
		}
		a.recycle();
		init();
	}

	public LClockView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LClockView(Context context) {
		this(context, null);
	}

	/*-------------------------构造方法结束-------------------------------*/

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		height = getHeight();
		width = getWidth();
		if (width > height) {
			radius = height / 2 - borderWidth;
		} else {
			radius = width / 2 - borderWidth;
		}

		if (textSize == 0) {
			textSize = radius / 8;
			textPaint.setTextSize(textSize);
			smallTextPaint.setTextSize(textSize / 2);
			textGaryPaint.setTextSize(textSize);
			smallTextGaryPaint.setTextSize(textSize / 2);
			selectedTextPaint.setTextSize(textSize);
			selectedSmallTextPaint.setTextSize(textSize / 2);
		}
		/**
		 * 画视图背景
		 */
		canvas.drawRect(0, 0, width, height, backgroundPaint);
		/**
		 * 画背景色
		 */
		canvas.drawCircle(width / 2, height / 2, radius, dialPaint);
		/**
		 * 画背景图
		 */
		if (dialImage > 0) {
			Bitmap roundBitmap = getCroppedRoundBitmap();
			canvas.drawBitmap(roundBitmap, width / 2 - radius, height / 2
					- radius, null);
		}

		float[] scale;
		for (int i = 0; i < 60; i++) {
			/**
			 * 画小刻度
			 */
			scale = getSmallScale(i);
			canvas.drawLine(scale[0], scale[1], scale[2], scale[3],
					scaleSmallPaint);
			if (i % 5 == 0) {
				/**
				 * 画大刻度
				 */
				scale = getBigScale(i);
				canvas.drawLine(scale[0], scale[1], scale[2], scale[3],
						scaleBigPaint);
			}
		}
		/**
		 * 画文字
		 */
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		if (type == TYPE_HOURS) {
			FontMetrics fm2 = smallTextPaint.getFontMetrics();
			float textY2 = -fm2.descent + (fm2.descent - fm2.ascent) / 2;
			for (int i = 0; i < hoursItems.length; i++) {
				scale = getTextLocation(i);
				if (i < 12) {
					if (selected == i) {
						canvas.drawCircle(scale[0], scale[1], textSize * 0.7f,
								selectedPaint);
						canvas.drawText(i + "", scale[0], scale[1] + textY,
								selectedTextPaint);
					}
					if (hoursItems[i] >= 0) {
						canvas.drawText(i + "", scale[0], scale[1] + textY,
								textPaint);
					} else {
						canvas.drawText(i + "", scale[0], scale[1] + textY,
								textGaryPaint);
					}
				} else {
					if (selected == i) {
						canvas.drawCircle(scale[0], scale[1], textSize * 0.7f,
								selectedPaint);
						canvas.drawText(i + "", scale[0], scale[1] + textY2,
								selectedSmallTextPaint);
					}
					if (hoursItems[i] >= 0) {
						canvas.drawText(i + "", scale[0], scale[1] + textY2,
								smallTextPaint);
					} else {
						canvas.drawText(i + "", scale[0], scale[1] + textY2,
								smallTextGaryPaint);
					}
				}
			}
		} else {
			for (int i = 0; i < minutesItems.length; i++) {
				scale = getTextLocation(i);
				if (selected == i) {
					canvas.drawCircle(scale[0], scale[1], textSize * 0.7f,
							selectedPaint);
					canvas.drawText(i + "", scale[0], scale[1] + textY,
							selectedTextPaint);
				}
				if (i % 5 != 0) {
					continue;
				}
				if (minutesItems[i] >= 0) {
					canvas.drawText(i + "", scale[0], scale[1] + textY,
							textPaint);
				} else {
					canvas.drawText(i + "", scale[0], scale[1] + textY,
							textGaryPaint);
				}
			}
		}
		/**
		 * 画指针小红点
		 */
		canvas.drawCircle(width / 2, height / 2, radius/20,pointerPaint );
		/**
		 * 画指针
		 */
		canvas.drawPath(getPointerLocation(), pointerPaint);
		// scale = getPointerLocation();
		// canvas.drawLine(width / 2, height / 2, scale[0], scale[1],
		// pointerPaint);
	}

	/*-------------------------参数设置方法开始-------------------------------*/
	/**
	 * 设置选择项 你需要先确定type类型 传入一个选择项的数组 type 为 hours时,最大值为23,最小值为0 type 为
	 * minutes时,最大值为59,最小值为0 传入非法值则跳过
	 */
	public void setItem(int[] items) {
		switch (type) {
			case TYPE_HOURS:
				// 清空小时数组
				hoursItems = new int[24];
				for (int i = 0; i < hoursItems.length; i++) {
					hoursItems[i] = -1;
				}
				// 设置小时数组
				for (int i = 0; i < items.length; i++) {
					if (items[i] < 24 && items[i] >= 0) {
						hoursItems[items[i]] = 1;
					}
				}
				for (int i = 0; i < hoursItems.length; i++) {
					if (hoursItems[i] > 0) {
						selected = i;
						realSelected = i;
						realPointerAngle = i * 30;
						pointerAngle = i * 30;
						break;
					}
				}
				break;
			case TYPE_MINUTES:
				// 清空分钟数组
				minutesItems = new int[60];
				for (int i = 0; i < minutesItems.length; i++) {
					minutesItems[i] = -1;
				}
				// 设置分钟数组
				for (int i = 0; i < items.length; i++) {
					if (items[i] < 60 && items[i] >= 0) {
						minutesItems[items[i]] = 1;
					}
				}
				for (int i = 0; i < minutesItems.length; i++) {
					if (minutesItems[i] > 0) {
						selected = i;
						realSelected = i;
						realPointerAngle = i * 6;
						pointerAngle = i * 6;
						break;
					}
				}
				break;
		}
		invalidate();
	}

	/**
	 * 设置选择项 你需要先确定type类型 type 为 hours时,最大值为23,最小值为0 type 为
	 * minutes时,最大值为59,最小值为0 传入选择项的区间
	 *
	 * @param min
	 * @param max
	 */
	public void setItem(int min, int max) {
		if (min < 0) {
			min = 0;
		}
		switch (type) {
			case TYPE_HOURS:
				if (max > 23) {
					max = 23;
				}
				// 清空小时数组
				hoursItems = new int[24];
				for (int i = 0; i < hoursItems.length; i++) {
					hoursItems[i] = -1;
				}
				for (int i = min; i <= max; i++) {
					hoursItems[i] = 1;
				}
				for (int i = 0; i < hoursItems.length; i++) {
					if (hoursItems[i] > 0) {
						selected = i;
						realSelected = i;
						realPointerAngle = i * 30;
						pointerAngle = i * 30;
						break;
					}
				}
				break;
			case TYPE_MINUTES:
				if (max > 59) {
					max = 59;
				}
				// 清空分钟数组
				minutesItems = new int[60];
				for (int i = 0; i < minutesItems.length; i++) {
					minutesItems[i] = -1;
				}
				for (int i = min; i <= max; i++) {
					minutesItems[i] = 1;
				}
				for (int i = 0; i < minutesItems.length; i++) {
					if (minutesItems[i] > 0) {
						selected = i;
						realSelected = i;
						realPointerAngle = i * 6;
						pointerAngle = i * 6;
						break;
					}
				}
				break;
		}
		invalidate();
	}

	/**
	 * 获取类型
	 *
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置类型(小时制,分钟制)
	 *
	 * @param type
	 *            小时 TYPE_HOURS 分钟(秒同用) TYPE_MINUTES
	 */
	public void setType(int type) {
		this.type = type;
		invalidate();
	}

	/**
	 * 获取边框颜色
	 *
	 * @return
	 */
	public int getBorderColor() {
		return borderColor;
	}

	/**
	 * 设置边框颜色
	 *
	 * @param borderColor
	 */
	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
		invalidate();
	}

	/**
	 * 获取背景色
	 *
	 * @return
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * 设置背景色
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		invalidate();
	}

	/**
	 * 获取表盘颜色
	 *
	 * @return
	 */
	public int getDialColor() {
		return dialColor;
	}

	/**
	 * 设置表盘颜色
	 *
	 * @param dialColor
	 */
	public void setDialColor(int dialColor) {
		this.dialColor = dialColor;
		invalidate();
	}

	/**
	 * 获取表盘描边宽度
	 *
	 * @return
	 */
	public float getDialWidth() {
		return borderWidth;
	}

	/**
	 * 设置表盘描边宽度
	 *
	 * @param dialWidth
	 */
	public void setDialWidth(float dialWidth) {
		this.borderWidth = dialWidth;
		invalidate();
	}

	/**
	 * 获取字体大小
	 *
	 * @return
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * 设置字体大小,此处设定为标准字体,两匝的时候内圈字体会自动缩小
	 *
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
		invalidate();
	}

	/**
	 * 获取字体颜色
	 *
	 * @return
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * 设置字体颜色
	 *
	 * @param textColor
	 *            当数字不可用表现为灰色,如果设置灰色会导致区分不清
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		invalidate();
	}

	/**
	 * 获取背景颜色
	 *
	 * @return
	 */
	public int getBackgroundImage() {
		return dialImage;
	}

	/**
	 * 设置背景颜色
	 *
	 * @param backgroundImage
	 */
	public void setBackgroundImage(int backgroundImage) {
		this.dialImage = backgroundImage;
		invalidate();
	}

	/**
	 * 获取不可用的字体的颜色
	 *
	 * @return
	 */
	public int getTextColorForUn() {
		return textColorForUn;
	}

	/**
	 * 设置不可用字体的颜色
	 *
	 * @param textColorForUn
	 */
	public void setTextColorForUn(int textColorForUn) {
		this.textColorForUn = textColorForUn;
	}
	/**
	 * 获取回调监听
	 * @return
	 */
	public ClockViewListener getClockViewListener() {
		return lis;
	}
	/**
	 * 设置回调监听
	 * @param lis
	 */
	public void setClockViewListener(ClockViewListener lis) {
		this.lis = lis;
	}

	/*-------------------------参数设置方法结束-------------------------------*/
	/**
	 * 获取裁剪后的圆形图片
	 */
	private Bitmap getCroppedRoundBitmap() {
		Bitmap scaledSrcBmp;
		int diameter = (int) (radius * 2);
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, dialImage);
		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布局文件XML看不到效果)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	private void init() {
		/**
		 * 文字画笔
		 */
		textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		/**
		 * 小文字画笔
		 */
		smallTextPaint = new Paint();
		smallTextPaint.setColor(textColor);
		smallTextPaint.setTextSize(textSize - 10);
		smallTextPaint.setAntiAlias(true);
		smallTextPaint.setTextAlign(Align.CENTER);
		/**
		 * 文字画笔-- 灰色
		 */
		textGaryPaint = new Paint();
		textGaryPaint.setColor(textColorForUn);
		textGaryPaint.setTextSize(textSize);
		textGaryPaint.setAntiAlias(true);
		textGaryPaint.setTextAlign(Align.CENTER);
		/**
		 * 内圈文字画笔-- 灰色
		 */
		smallTextGaryPaint = new Paint();
		smallTextGaryPaint.setColor(textColorForUn);
		smallTextGaryPaint.setTextSize(textSize - 10);
		smallTextGaryPaint.setAntiAlias(true);
		smallTextGaryPaint.setTextAlign(Align.CENTER);
		/**
		 * 边框画笔
		 */
		borderPaint = new Paint();
		borderPaint.setColor(borderColor);
		borderPaint.setAntiAlias(true);
		borderPaint.setStrokeWidth(borderWidth);
		/**
		 * 指针画笔
		 */
		pointerPaint = new Paint();
		pointerPaint.setAntiAlias(true);
		pointerPaint.setColor(pointerColor);
		pointerPaint.setStyle(Paint.Style.FILL);
		/**
		 * 背景画笔
		 */
		backgroundPaint = new Paint();
		backgroundPaint.setAntiAlias(true);
		backgroundPaint.setColor(backgroundColor);
		/**
		 * 表盘画笔
		 */
		dialPaint = new Paint();
		dialPaint.setAntiAlias(true);
		dialPaint.setColor(dialColor);
		/**
		 * 大刻度画笔
		 */
		scaleBigPaint = new Paint();
		scaleBigPaint.setAntiAlias(true);
		scaleBigPaint.setColor(scaleColor);
		scaleBigPaint.setStrokeWidth(10);
		/**
		 * 小刻度画笔
		 */
		scaleSmallPaint = new Paint();
		scaleSmallPaint.setAntiAlias(true);
		scaleSmallPaint.setColor(scaleColor);
		scaleSmallPaint.setStrokeWidth(5);
		/**
		 * 以选中画笔
		 */
		selectedPaint = new Paint();
		selectedPaint.setAntiAlias(true);
		selectedPaint.setColor(selectedColor);
		/**
		 * 选中的文字的画笔
		 */
		selectedTextPaint = new Paint();
		selectedTextPaint.setAntiAlias(true);
		selectedTextPaint.setTextSize(textSize);
		selectedTextPaint.setColor(Color.WHITE);
		selectedTextPaint.setTextAlign(Align.CENTER);
		/**
		 * 选中的小文字的画笔
		 */
		selectedSmallTextPaint = new Paint();
		selectedSmallTextPaint.setAntiAlias(true);
		selectedSmallTextPaint.setTextSize(textSize);
		selectedSmallTextPaint.setColor(Color.WHITE);
		selectedSmallTextPaint.setTextAlign(Align.CENTER);
	}

	/**
	 * 获取大刻度的位置坐标
	 *
	 * @param index
	 * @return
	 */
	private float[] getBigScale(int index) {
		double length = radius * 0.04f;
		float x1 = 0f;
		float x2 = 0f;
		float y1 = 0f;
		float y2 = 0f;
		x1 = (float) (Math.sin(2 * Math.PI / 360 * 30 * index) * radius + (width / 2));
		y1 = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index) * radius + (height / 2));
		x2 = (float) (Math.sin(2 * Math.PI / 360 * 30 * index)
				* (radius - length) + (width / 2));
		y2 = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index)
				* (radius - length) + (height / 2));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * 获取小刻度的位置坐标
	 *
	 * @param index
	 * @return
	 */
	private float[] getSmallScale(int index) {
		double length = radius * 0.02f;
		float x1 = 0f;
		float x2 = 0f;
		float y1 = 0f;
		float y2 = 0f;
		x1 = (float) (Math.sin(2 * Math.PI / 360 * 6 * index) * radius + (width / 2));
		y1 = (float) (-Math.cos(2 * Math.PI / 360 * 6 * index) * radius + (height / 2));
		x2 = (float) (Math.sin(2 * Math.PI / 360 * 6 * index)
				* (radius - length) + (width / 2));
		y2 = (float) (-Math.cos(2 * Math.PI / 360 * 6 * index)
				* (radius - length) + (height / 2));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * 获取数字的位置坐标
	 *
	 * @param index
	 * @return
	 */
	private float[] getTextLocation(int index) {
		double length = (radius * 0.1f) + (textSize / 3);
		float x = 0f;
		float y = 0f;
		if (type == TYPE_HOURS) {
			if (index < 12) {
				x = (float) (Math.sin(2 * Math.PI / 360 * 30 * index)
						* (radius - length) + (width / 2));
				y = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index)
						* (radius - length) + (height / 2));
			} else {
				x = (float) (Math.sin(2 * Math.PI / 360 * 30 * index)
						* (radius - length - (textSize * 1.5)) + (width / 2));
				y = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index)
						* (radius - length - (textSize * 1.5)) + (height / 2));
			}
		} else {
			x = (float) (Math.sin(2 * Math.PI / 360 * 6 * index)
					* (radius - length) + (width / 2));
			y = (float) (-Math.cos(2 * Math.PI / 360 * 6 * index)
					* (radius - length) + (height / 2));
		}
		return new float[] { x, y };
	}

	/**
	 * 获取指针位置
	 *
	 * @return
	 */
	private Path getPointerLocation() {

		float x1 = (float) (Math.sin(2 * Math.PI / 360 * pointerAngle)
				* (radius * 0.6) + (width / 2));
		float y1 = (float) (-Math.cos(2 * Math.PI / 360 * pointerAngle)
				* (radius * 0.6) + (height / 2));
		float x2 = (float) (Math.sin(2 * Math.PI / 360 * (pointerAngle + 90))
				* (radius * 0.05) + (width / 2));
		float y2 = (float) (-Math.cos(2 * Math.PI / 360 * (pointerAngle + 90))
				* (radius * 0.05) + (height / 2));
		float x3 = (float) (Math.sin(2 * Math.PI / 360 * (pointerAngle - 90))
				* (radius * 0.05) + (width / 2));
		float y3 = (float) (-Math.cos(2 * Math.PI / 360 * (pointerAngle - 90))
				* (radius * 0.05) + (height / 2));
		float x4 = (float) (Math.sin(2 * Math.PI / 360 * (pointerAngle + 180))
				* (radius * 0.2) + (width / 2));
		float y4 = (float) (-Math.cos(2 * Math.PI / 360 * (pointerAngle + 180))
				* (radius * 0.2) + (height / 2));

		Path path = new Path();
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x4, y4);
		path.lineTo(x3, y3);
		path.close();
		return path;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/**
		 * 获取度数
		 */
		float x = event.getX() - (width / 2);
		float y = -(event.getY() - (height / 2));
		double r = Math.sqrt((x * x) + (y * y));
		double pa = Math.asin(x / r) / (2 * Math.PI) * 360;
		/**
		 * 度数矫正
		 */
		if (y < 0) {
			if (x < 0) {
				pa = -pa - 180;
			} else {
				pa = 180 - pa;
			}
		}
		if (pa < 0) {
			pa += 360;
		}
		onTouch(pa, r);
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				switch (type) {
					case TYPE_HOURS:
						if (hoursItems[selected] < 0) {
							selected = realSelected;
							pointerAngle = realPointerAngle;
						} else {
							realSelected = selected;
							realPointerAngle = pointerAngle;
						}
						break;
					case TYPE_MINUTES:
						if (minutesItems[selected] < 0) {
							selected = realSelected;
							pointerAngle = realPointerAngle;
						} else {
							realSelected = selected;
							realPointerAngle = pointerAngle;
						}
						break;
				}
				if(lis!=null){
					lis.onClockChange(selected);
				}
				break;
		}
		invalidate();
		return true;
	};

	private void onTouch(double pa, double r) {
		if (type == TYPE_HOURS) {
			int i = (int) (pa / 30);
			if (pa % 30 > 15) {
				i++;
				if (i > 11) {
					i = 0;
				}
			}
			if (r > radius * 0.65) {
				selected = i;
			} else {
				selected = i + 12;
			}
			pa = i * 30;
		} else {
			int i = (int) (pa / 6);
			if (pa % 6 > 3) {
				i++;
				if (i > 59) {
					i = 0;
				}
			}
			selected = i;
			pa = i * 6;
		}
		pointerAngle = pa;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}
