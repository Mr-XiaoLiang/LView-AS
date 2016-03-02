package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.option.LProgressButtonOption;

public class LProgressButton extends View {
	/**
	 * 参数
	 */
	private LProgressButtonOption option;
	/**
	 * 按钮宽度
	 */
	private int width;
	/**
	 * 按钮高度
	 */
	private int height;
	/**
	 * 开始时的背景画笔
	 */
	private Paint startBgPaint;
	/**
	 * 开始时的前进画笔
	 */
	private Paint startPaint;
	/**
	 * 进度条背景画笔
	 */
	private Paint progressBgPaint;
	/**
	 * 进度条中心块的画笔
	 */
	private Paint progressCenterPaint;
	/**
	 * 进度条前景画笔
	 */
	private Paint progressPaint;
	/**
	 * 进度条前景文字画笔
	 */
	private Paint progressTextPaint;
	/**
	 * 结束背景画笔
	 */
	private Paint endBgPaint;
	/**
	 * 结束前景画笔
	 */
	private Paint endPaint;
	/**
	 * 异常背景画笔
	 */
	private Paint errorBgPaint;
	/**
	 * 异常前景画笔
	 */
	private Paint errorPaint;
	/**
	 * 当前状态
	 */
	private int type = TYPE_NONE;
	/**
	 * 上一个状态
	 */
	private int oldType = type;
	/**
	 * 未初始化
	 */
	public final static int TYPE_NONE = -1;
	/**
	 * 开始状态
	 */
	public final static int TYPE_START = 0;
	/**
	 * 准备加载中
	 */
	public final static int TYPE_PREPARE = 4;
	/**
	 * 加载中
	 */
	public final static int TYPE_LOADING = 1;
	/**
	 * 结束
	 */
	public final static int TYPE_END = 2;
	/**
	 * 异常
	 */
	public final static int TYPE_ERROR = 3;
	/**
	 * 总进度
	 */
	private float allNum = 0;
	/**
	 * 已加载
	 */
	private float proNum = 0;
	/**
	 * 显示的已有进度
	 */
	private float proIndex = 0;
	/**
	 * 点击回调事件
	 */
	private LProgressButtonOnClickListener clickListener;
	/**
	 * 动画的帧数
	 */
	private int index = 0;
	/**
	 * 最大帧数
	 */
	private int maxIndex = 30;
	/**
	 * 就绪状态的下标
	 */
	private float prepareIndex = 0;
	/**
	 * 半径
	 */
	private int radius = -1;
	/**
	 * 绘制状态
	 */
	private boolean drawType = true;
	/**
	 * 确定动画是否完毕
	 */
	private boolean isEnd = false;

	public LProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LProgressButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LProgressButton(Context context) {
		super(context);
	}

	public LProgressButton(Context context, LProgressButtonOption option) {
		super(context);
		this.option = option;
		init();
	}

	/**
	 * 基本初始化
	 */
	private void init() {
		if (option == null) {
			throw new RuntimeException("LProgressButtonOption is null");
		}
		startBgPaint = new Paint();
		startBgPaint.setAntiAlias(true);
		startBgPaint.setColor(option.getBtnStartBgColor());
		startPaint = new Paint();
		startPaint.setAntiAlias(true);
		startPaint.setColor(option.getBtnStartTextColor());
		startPaint.setTextAlign(Align.CENTER);
		progressBgPaint = new Paint();
		progressBgPaint.setAntiAlias(true);
		progressBgPaint.setColor(option.getBtnProgressBgColor());
		progressBgPaint.setStyle(Paint.Style.STROKE);
		progressCenterPaint = new Paint();
		progressCenterPaint.setAntiAlias(true);
		progressCenterPaint.setColor(option.getBtnProgressCenterColor());
		progressCenterPaint.setStyle(Paint.Style.FILL);
		progressPaint = new Paint();
		progressPaint.setAntiAlias(true);
		progressPaint.setColor(option.getBtnProgressColor());
		progressPaint.setStyle(Paint.Style.STROKE);
		progressTextPaint = new Paint();
		progressTextPaint.setAntiAlias(true);
		progressTextPaint.setColor(option.getBtnProgressPercentColor());
		progressTextPaint.setTextAlign(Align.CENTER);
		endBgPaint = new Paint();
		endBgPaint.setAntiAlias(true);
		endBgPaint.setColor(option.getBtnEndBgColor());
		endPaint = new Paint();
		endPaint.setAntiAlias(true);
		endPaint.setColor(option.getBtnEndTextColor());
		endPaint.setTextAlign(Align.CENTER);
		errorBgPaint = new Paint();
		errorBgPaint.setAntiAlias(true);
		errorBgPaint.setColor(option.getBtnErrorBgColor());
		errorPaint = new Paint();
		errorPaint.setAntiAlias(true);
		errorPaint.setColor(option.getBtnErrorTextColor());
		errorPaint.setTextAlign(Align.CENTER);
		type = TYPE_START;
		allNum = 0;
		proNum = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		width = getWidth();
		height = getHeight();
		if (type == TYPE_NONE) {// 没有初始化的按钮不画
			return;
		}
		drawType = true;

		drawType(oldType, canvas);
		if (drawType)
			drawType(type, canvas);
	}

	/**
	 * 用于动画衔接的判断方法
	 *
	 * @param t
	 * @param canvas
	 */
	private void drawType(int t, Canvas canvas) {
		switch (t) {
			case TYPE_END:
				// 画结束界面
				drawEndBackgroung(canvas);
				break;
			case TYPE_ERROR:
				// 画异常界面
				drawErrorBackgroung(canvas);
				break;
			case TYPE_LOADING:
				// 画加载界面
				drawProgress(canvas);
				break;
			case TYPE_PREPARE:
				// 画等待界面
				drawProgressPrepare(canvas);
				break;
			case TYPE_START:
				// 画开始界面
				drawStartBackgroung(canvas);
				break;
		}
	}

	/**
	 * 绘制开始界面
	 *
	 * @param canvas
	 */
	private void drawStartBackgroung(Canvas canvas) {
		if (type != TYPE_START && oldType != TYPE_START) {
			return;// 如果与自己不相关就返回
		}
		drawType = false;
		if (type == TYPE_START && oldType != TYPE_START) {// 进入当前状态
			createStart(canvas, TYPE_START);
			if (index > maxIndex) {
				index = maxIndex;
				oldType = type;
			}
			index++;
		} else if (type != TYPE_START && oldType == TYPE_START) {// 退出当前状态
			if (!isEnd) {
				index--;
				createStart(canvas, TYPE_START);
				if (index < 0) {
					isEnd = true;
					index = 0;
				}
			} else {
				drawType = true;
			}
		} else {
			createBtn(canvas, TYPE_START);
			return;
		}
		invalidate();
	}

	/**
	 * 绘制结束界面
	 *
	 * @param canvas
	 */
	private void drawEndBackgroung(Canvas canvas) {
		if (type != TYPE_END && oldType != TYPE_END) {
			return;// 如果与自己不相关就返回
		}
		drawType = false;
		if (type == TYPE_END && oldType != TYPE_END) {// 进入当前状态
			createStart(canvas, TYPE_END);
			if (index > maxIndex) {
				index = maxIndex;
				oldType = type;
			}
			index++;
		} else if (type != TYPE_END && oldType == TYPE_END) {// 退出当前状态
			if (!isEnd) {
				index--;
				createStart(canvas, TYPE_END);
				if (index < 0) {
					isEnd = true;
					index = 0;
				}
			} else {
				drawType = true;
			}
		} else {
			createBtn(canvas, TYPE_END);
			return;
		}
		invalidate();
	}

	/**
	 * 绘制异常界面
	 *
	 * @param canvas
	 */
	private void drawErrorBackgroung(Canvas canvas) {
		if (type != TYPE_ERROR && oldType != TYPE_ERROR) {
			return;// 如果与自己不相关就返回
		}
		drawType = false;
		if (type == TYPE_ERROR && oldType != TYPE_ERROR) {// 进入当前状态
			createStart(canvas, TYPE_ERROR);
			if (index > maxIndex) {
				index = maxIndex;
				oldType = type;
			}
			index++;
		} else if (type != TYPE_ERROR && oldType == TYPE_ERROR) {// 退出当前状态
			if (!isEnd) {
				index--;
				createStart(canvas, TYPE_ERROR);
				if (index < 0) {
					isEnd = true;
					index = 0;
				}
			} else {
				drawType = true;
			}
		} else {
			createBtn(canvas, TYPE_ERROR);
			return;
		}
		invalidate();
	}

	/**
	 * 绘制进度界面
	 *
	 * @param canvas
	 */
	private void drawProgress(Canvas canvas) {
		if (type != TYPE_LOADING & oldType != TYPE_LOADING) {
			return;// 如果与自己不相关就返回
		}
		drawType = false;
		// 画背景色
		if (!option.isProgressCenterLucency()) {
			canvas.drawCircle(width / 2, height / 2, radius, progressCenterPaint);
		}
		// 画背景图
		if (!option.isProgressCenterLucency() && option.getBtnProgressCenterImage() > 0) {
			canvas.drawBitmap(
					createBitmap(BitmapFactory.decodeResource(getResources(), option.getBtnProgressCenterImage()),
							radius * 2, radius * 2, radius),
					width / 2 - radius, height / 2 - radius, null);
		}
		progressPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		progressBgPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		canvas.drawCircle(width / 2, height / 2, radius, progressBgPaint);
		RectF rectF = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
		if (type == TYPE_LOADING && oldType != TYPE_LOADING && oldType != TYPE_PREPARE) {
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.4);
			} else {
				r = (int) (width * 0.4);
			}
			if (radius < r) {
				radius += (r * 0.1);
			} else {
				radius = r;
				oldType = type;
			}
			// invalidate();
		} else if (type == TYPE_LOADING && (oldType == TYPE_LOADING || oldType == TYPE_PREPARE)) {
			oldType = type;
			isEnd = false;
			if (allNum <= 0 || proNum <= 0) {
				return;
			} else {
//				if(Math.abs(proNum - proIndex) < allNum / 100){
//					proIndex = proNum;
//				}
				canvas.drawArc(rectF, -90, 360 * proIndex / allNum, false, progressPaint);
				String text = ((int)(proIndex / allNum * 100))+"%";
				// 画字
				if (text != null && text.length() > 0&&option.isShowPercent()) {
					int textSize;
					textSize = (int) (radius * 2 * 0.3);
					if (textSize * text.length() > (radius * 2)) {
						textSize = (int) ((radius * 2) / text.length() * 0.8);
					}
					progressTextPaint.setTextSize(textSize);
					FontMetrics fm = progressTextPaint.getFontMetrics();
					float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
					canvas.drawText(text, width / 2, height / 2 + textY, progressTextPaint);
				}
				if (proIndex < proNum) {
					proIndex += allNum * 0.01;
					if (Math.abs(proNum - proIndex) < allNum * 0.01) {
						proIndex = proNum;
					}
				} else if (proIndex > proNum) {
					proIndex -= proNum * 0.01;
					if (Math.abs(proNum - proIndex) < allNum * 0.01) {
						proIndex = proNum;
					}
				} else {
					return;
				}
			}
		} else if (oldType == TYPE_LOADING) {
			canvas.drawArc(rectF, -90, 360 * proIndex / allNum, false, progressPaint);
			if(!isEnd){
				int r = 0;
				if (width > height) {
					r = (int) (height * 0.4);
				} else {
					r = (int) (width * 0.4);
				}
				if (radius > 0) {
					radius -= (r * 0.1);
				} else {
					radius = 0;
					index = 0;
					isEnd = true;
				}
			}else{
				drawType = true;
			}
			// invalidate();
		}
		invalidate();
	}

	/**
	 * 画进度等待界面
	 *
	 * @param canvas
	 */
	private void drawProgressPrepare(Canvas canvas) {
		if (type != TYPE_PREPARE && oldType != TYPE_PREPARE) {
			return;// 如果与自己不相关就返回
		}
		drawType = false;
		// 画背景色
		if (!option.isProgressCenterLucency()) {
			canvas.drawCircle(width / 2, height / 2, radius, progressCenterPaint);
		}
		// 画背景图
		if (!option.isProgressCenterLucency() && option.getBtnProgressCenterImage() > 0) {
			canvas.drawBitmap(
					createBitmap(BitmapFactory.decodeResource(getResources(), option.getBtnProgressCenterImage()),
							radius * 2, radius * 2, radius),
					width / 2 - radius, height / 2 - radius, null);
		}
		progressPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		progressBgPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		canvas.drawCircle(width / 2, height / 2, radius, progressBgPaint);
		RectF rectF = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
		if (type == TYPE_PREPARE && oldType != TYPE_PREPARE) {
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.4);
			} else {
				r = (int) (width * 0.4);
			}
			if (radius < r) {
				radius += (r * 0.1);
			} else {
				radius = r;
				oldType = type;
			}
			// invalidate();
		} else if (type == TYPE_PREPARE && oldType == TYPE_PREPARE) {
			if (index < maxIndex) {
				canvas.drawArc(rectF, -90, 360 * index * 0.01f, false, progressPaint);
				index++;
				// invalidate();
			} else {// prepareIndex
				canvas.drawArc(rectF, prepareIndex - 90, 360 * index * 0.01f, false, progressPaint);
				prepareIndex += 5;
				if (prepareIndex > 360) {
					prepareIndex = 0;
				}
				// invalidate();
			}
			isEnd = false;
		} else if (type == TYPE_LOADING && oldType == TYPE_PREPARE) {
			if (!isEnd && index > 0) {
				canvas.drawArc(rectF, prepareIndex - 90, 360 * index * 0.01f, false, progressPaint);
				if (prepareIndex > 285) {
					prepareIndex += 360 * 0.01f;
					index--;
				} else {
					prepareIndex += 5;
					if (prepareIndex > 360) {
						prepareIndex = 0;
					}
				}
				// invalidate();
			}
			if (index <= 0) {
				isEnd = true;
				drawType = true;
				index = 0;
			}
		} else if (oldType == TYPE_PREPARE) {
			canvas.drawArc(rectF, prepareIndex - 90, 360 * index * 0.01f, false, progressPaint);
			prepareIndex += 5;
			if (prepareIndex > 360) {
				prepareIndex = 0;
			}
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.4);
			} else {
				r = (int) (width * 0.4);
			}
			if (radius > 0) {
				radius -= (r * 0.1);
			} else {
				radius = 0;
				// oldType = type;
				index = 0;
			}
			// invalidate();
		}
		invalidate();
	}

	/**
	 * 按钮的完成状态
	 *
	 * @param canvas
	 * @param type
	 */
	private void createBtn(Canvas canvas, int type) {
		Paint paint;
		Paint bgpaint;
		String text;
		int img;
		int icon;
		switch (type) {
			case TYPE_END:
				paint = endPaint;
				bgpaint = endBgPaint;
				text = option.getBtnEndText();
				img = option.getBtnEndBgImage();
				icon = option.getBtnEndIcon();
				break;
			case TYPE_ERROR:
				paint = errorPaint;
				bgpaint = errorBgPaint;
				text = option.getBtnErrorText();
				img = option.getBtnErrorBgImage();
				icon = option.getBtnErrorIcon();
				break;
			case TYPE_START:
				paint = startPaint;
				bgpaint = startBgPaint;
				text = option.getBtnStartText();
				img = option.getBtnStartBgImage();
				icon = option.getBtnStartIcon();
				break;
			default:
				return;
		}
		isEnd = false;
		this.type = oldType;
		switch (option.getBtnRadius()) {
			case LProgressButtonOption.Builder.btnRadius_HORIZONTAL:
				radius = height / 2;
				break;
			case LProgressButtonOption.Builder.btnRadius_SQUARE:
				radius = 0;
				break;
			case LProgressButtonOption.Builder.btnRadius_VERTICAL:
				radius = width / 2;
				break;
			default:
				if (option.getBtnRadius() > 0) {
					radius = option.getBtnRadius();
				} else {
					radius = 0;
				}
				break;
		}
		// 画背景色
		RectF rect = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rect, radius, radius, bgpaint);
		// 画背景图
		if (img > 0) {
			canvas.drawBitmap(createBitmap(BitmapFactory.decodeResource(getResources(), img), height, width, radius),
					width, height, null);
		}
		//绘制前景图片
		if(icon>0){
			int w = 0,h = 0;
			float l = 0;
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icon);
			if(bitmap.getWidth()!=0&&bitmap.getHeight()!=0){
				l = bitmap.getWidth()/bitmap.getHeight();
				switch (option.getBtnIconSizeType()) {
					case LProgressButtonOption.Builder.btnIconSizeType_AUTO:
						if(width/height>l){
							h = (int) (height*option.getBtnIconSize());
							w = (int) (h*l);
						}else{
							w = (int) (width*option.getBtnIconSize());
							h = (int) (w/l);
						}
						break;
					case LProgressButtonOption.Builder.btnIconSizeType_HEIGHT:
						h = (int) (height*option.getBtnIconSize());
						w = (int) (h*l);
						break;
					case LProgressButtonOption.Builder.btnIconSizeType_WIDTH:
						w = (int) (width*option.getBtnIconSize());
						h = (int) (w/l);
						break;
				}
				canvas.drawBitmap(createBitmap(bitmap,
								h, w, option.getBtnIconRadius()), width / 2 - w/2, height / 2 - h/2,
						null);
			}
			bitmap.recycle();
			bitmap = null;
			System.gc();
		}
		// 画字
		if (text != null && text.length() > 0) {
			int textSize;
			if (width > height) {
				textSize = (int) (height * 0.3);
				if (textSize * text.length() > width) {
					textSize = (int) (width / text.length() * 0.8);
				}
			} else {
				textSize = (int) (width * 0.3);
				if (textSize * text.length() > height) {
					textSize = (int) (height / text.length() * 0.8);
				}
			}
			startPaint.setTextSize(textSize);
			FontMetrics fm = startPaint.getFontMetrics();
			float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
			canvas.drawText(text, width / 2, height / 2 + textY, paint);
		}
	}

	/**
	 * 开始的动画
	 *
	 * @param canvas
	 */
	private void createStart(Canvas canvas, int type) {
		Paint paint;
		Paint bgpaint;
		String text;
		int img;
		int icon;
		switch (type) {
			case TYPE_END:
				paint = endPaint;
				bgpaint = endBgPaint;
				text = option.getBtnEndText();
				img = option.getBtnEndBgImage();
				icon = option.getBtnEndIcon();
				break;
			case TYPE_ERROR:
				paint = errorPaint;
				bgpaint = errorBgPaint;
				text = option.getBtnErrorText();
				img = option.getBtnErrorBgImage();
				icon = option.getBtnErrorIcon();
				break;
			case TYPE_START:
				paint = startPaint;
				bgpaint = startBgPaint;
				text = option.getBtnStartText();
				img = option.getBtnStartBgImage();
				icon = option.getBtnStartIcon();
				break;
			default:
				return;
		}
		if (index < (maxIndex / 2)) {// 从点变成圆
			// 画背景色
			canvas.drawCircle(width / 2, height / 2, radius, bgpaint);
			// 画背景图
			if (img > 0) {
				canvas.drawBitmap(
						createBitmap(BitmapFactory.decodeResource(getResources(), img), radius * 2, radius * 2, radius),
						width / 2 - radius, height / 2 - radius, null);
			}
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.5);
			} else {
				r = (int) (width * 0.5);
			}
			radius = r / maxIndex * 2 * index;
		} else {// 从圆变成按钮
			float wr = 0;
			float hr = 0;
			if (width > height) {
				radius = (int) (height * 0.5);
			} else {
				radius = (int) (width * 0.5);
			}
			if (option.getBtnRadius() > radius) {
				radius = option.getBtnRadius();
			}
			wr = (width / 2 - radius) / maxIndex * 2 * (index - maxIndex / 2);
			hr = (height / 2 - radius) / maxIndex * 2 * (index - maxIndex / 2);
			if (wr > (width / 2 - radius)) {
				wr = (width / 2 - radius);
			}
			if (hr > (height / 2 - radius)) {
				hr = (height / 2 - radius);
			}
			// 画背景色
			RectF rect = new RectF(width / 2 - wr - radius, height / 2 - hr - radius, width / 2 + wr + radius,
					height / 2 + hr + radius);
			canvas.drawRoundRect(rect, radius, radius, bgpaint);
			// 画背景图
			if (img > 0) {
				canvas.drawBitmap(createBitmap(BitmapFactory.decodeResource(getResources(), img),
								(int) hr * 2 + radius * 2, (int) wr * 2 + radius * 2, radius), width / 2 - wr, height / 2 - hr,
						null);
			}
			//绘制前景图片
			if(icon>0){
				int w = 0,h = 0;
				float l = 0;
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icon);
				if(bitmap.getWidth()!=0&&bitmap.getHeight()!=0){
					l = bitmap.getWidth()/bitmap.getHeight();
					switch (option.getBtnIconSizeType()) {
						case LProgressButtonOption.Builder.btnIconSizeType_AUTO:
							if(wr/hr>l){
								h = (int) ((hr * 2 + radius * 2)*option.getBtnIconSize());
								w = (int) (h*l);
							}else{
								w = (int) ((wr * 2 + radius * 2)*option.getBtnIconSize());
								h = (int) (w/l);
							}
							break;
						case LProgressButtonOption.Builder.btnIconSizeType_HEIGHT:
							h = (int) ((hr * 2 + radius * 2)*option.getBtnIconSize());
							w = (int) (h*l);
							break;
						case LProgressButtonOption.Builder.btnIconSizeType_WIDTH:
							w = (int) ((wr * 2 + radius * 2)*option.getBtnIconSize());
							h = (int) (w/l);
							break;
					}
					canvas.drawBitmap(createBitmap(bitmap,
									h, w, option.getBtnIconRadius()), width / 2 - w/2, height / 2 - h/2,
							null);
				}
				bitmap.recycle();
				bitmap = null;
				System.gc();
			}
			// 画字
			if (text != null && text.length() > 0) {
				int textSize;
				if (wr > hr) {
					textSize = (int) ((hr * 2 + radius * 2) * 0.3);
					if (textSize * text.length() > (wr * 2 + radius * 2)) {
						textSize = (int) ((wr * 2 + radius * 2) / text.length() * 0.8);
					}
				} else {
					textSize = (int) ((wr * 2 + radius * 2) * 0.3);
					if (textSize * text.length() > (hr * 2 + radius * 2)) {
						textSize = (int) ((hr * 2 + radius * 2) / text.length() * 0.8);
					}
				}
				paint.setTextSize(textSize);
				FontMetrics fm = paint.getFontMetrics();
				float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
				canvas.drawText(text, width / 2, height / 2 + textY, paint);
			}
		}
	}

	/**
	 *
	 * 创建一个符合的bitmap
	 *
	 * @param bitmap
	 * @param height
	 * @param width
	 * @param radius
	 * @return
	 */
	private Bitmap createBitmap(Bitmap bitmap, int height, int width, float radius) {
		Paint imagePaint = new Paint();
		imagePaint.setAntiAlias(true);
		imagePaint.setFilterBitmap(true);
		imagePaint.setDither(true);
		Bitmap outPut = null;
		Bitmap bm = bitmap;
		try {
			// 垂直拉伸
			if (bm.getHeight() < height) {
				float f = 1.0f * bm.getHeight() / height;
				bm = Bitmap.createScaledBitmap(bm, (int) (bm.getWidth() / f), height, true);
			}
			// 水平拉伸
			if (bm.getWidth() < width) {
				float f = 1.0f * bm.getWidth() / width;
				bm = Bitmap.createScaledBitmap(bm, width, (int) (bm.getHeight() / f), true);
			}
			outPut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(outPut);
			Rect rect = new Rect(width / 2 - bm.getWidth() / 2, height / 2 - bm.getHeight() / 2,
					width / 2 + bm.getWidth() / 2, height / 2 + bm.getHeight() / 2);
			canvas.drawARGB(0, 0, 0, 0);
			RectF rectf = new RectF(0, 0, width, height);
			canvas.drawRoundRect(rectf, radius, radius, imagePaint);
			imagePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bm, rect, rect, imagePaint);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return outPut;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (clickListener != null&&type==oldType) {
				clickListener.LPBOnClick(this);
			}
		}
		return true;
	}

	/**
	 * 进度按钮点击事件监听器
	 *
	 * @author LiuJ
	 *
	 */
	public interface LProgressButtonOnClickListener {
		public void LPBOnClick(LProgressButton btn);
	}

	/**
	 * 获取现有参数
	 *
	 * @return
	 */
	public LProgressButtonOption getOption() {
		return option;
	}

	/**
	 * 设置按钮样式参数
	 *
	 * @param option
	 */
	public void setOption(LProgressButtonOption option) {
		this.option = option;
		init();
	}

	/**
	 * 重新开始 重置
	 */
	public void reset() {
		type = TYPE_START;
		invalidate();
	}

	/**
	 * 开始下载
	 *
	 * @param allNum
	 * @param proNum
	 */
	public void onLoad(float allNum, float proNum) {
		oldType = type;
		type = TYPE_LOADING;
		this.allNum = allNum;
		this.proNum = proNum;
		invalidate();
	}

	/**
	 * 更新下载进度
	 *
	 * @param proNum
	 */
	public void updateProgress(float proNum) {
		this.proNum = proNum;
		invalidate();
	}

	/**
	 * 准备加载中
	 */
	public void onPrepare() {
		oldType = type;
		type = TYPE_PREPARE;
		invalidate();
	}

	/**
	 * 下载结束
	 */
	public void onEnd() {
		oldType = type;
		type = TYPE_END;
		invalidate();
	}

	/**
	 * 出错
	 */
	public void onError() {
		oldType = type;
		type = TYPE_ERROR;
		invalidate();
	}

	/**
	 * 获取当前状态
	 *
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 获取按钮的监听器
	 *
	 * @return
	 */
	public LProgressButtonOnClickListener getClickListener() {
		return clickListener;
	}

	/**
	 * 设置按钮的监听器
	 *
	 * @param clickListener
	 */
	public void setClickListener(LProgressButtonOnClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
