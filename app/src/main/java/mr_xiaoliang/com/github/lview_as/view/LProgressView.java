package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LProgressView extends View {
	/**
	 * 进度条颜色
	 */
	private int proColor;
	/**
	 * 文字颜色
	 */
	private int textColor;
	/**
	 * 背景颜色
	 */
	private int bgColor;
	/**
	 * 进度条画笔
	 */
	private Paint proPaint;
	/**
	 * 端点画笔
	 */
	private Paint epPaint;
	/**
	 * 背景画笔
	 */
	private Paint bgPaint;
	/**
	 * 文字画笔
	 */
	private Paint textPaint;
	/**
	 * 进度条背景
	 */
	private Paint proBgPaint;
	/**
	 * 总进度
	 */
	private int allInt = 0;
	/**
	 * 已经过了的进度
	 */
	private int havingInt = 0;
	/**
	 * 进度条是否有背景
	 */
	private boolean proHaveBg = false;
	/**
	 * 显示字符
	 */
	private String showText;
	/**
	 *  宽度
	 */
	private float width;
	/**
	 * 高度
	 */
	private float height;
	/**
	 * 字体大小
	 */
	private float textSize;
	/**
	 * 直径
	 */
	private float diameter;
	/**
	 * 动态进度
	 */
	private float index = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		if(width>height){
			diameter = height/7;
		}else{
			diameter = width/7;
		}
		proPaint.setStrokeWidth(diameter);
		proBgPaint.setStrokeWidth(diameter);
		float pro = getAngle();
		if(proHaveBg){
			//画进度条背景
			canvas.drawCircle(width/2, height/2, diameter*3f, proBgPaint);
		}
		/**
		 * 画进度条
		 */
		canvas.drawArc(getRectF(), -90, pro, false, proPaint);
		float[] loc = getLocation(pro);
		canvas.drawCircle(loc[0], loc[1], diameter/2, epPaint);
		canvas.drawCircle(loc[2], loc[3], diameter/2, epPaint);
		/**
		 * 画背景
		 */
		canvas.drawCircle(width/2, height/2, diameter*2.5f, bgPaint);
		textSize = diameter*5/(showText.length()+1);
		textPaint.setTextSize(textSize);
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = (height/2)-fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(showText, width/2, textY, textPaint);
		if((int)index<havingInt){
			index+=allInt*0.01;
			if(Math.abs(havingInt-index)<allInt*0.01){
				index = havingInt;
			}
			invalidate();
		}else if((int)index>havingInt){
			index-=allInt*0.01;
			if(Math.abs(havingInt-index)<allInt*0.01){
				index = havingInt;
			}
			invalidate();
		}
	}


	/**
	 * 初始化
	 */
	private void init(){
		proColor = Color.parseColor("#46bb7f");
		textColor = Color.BLACK;
		bgColor = Color.WHITE;
		showText = "";
		proPaint = new Paint();
		proPaint.setColor(proColor);
		proPaint.setAntiAlias(true);
		proPaint.setStyle(Paint.Style.STROKE);
		proBgPaint = new Paint();
		proBgPaint.setColor(proColor);
		proBgPaint.setAntiAlias(true);
		proBgPaint.setStyle(Paint.Style.STROKE);
		proBgPaint.setAlpha(128);
		epPaint = new Paint();
		epPaint.setColor(proColor);
		epPaint.setAntiAlias(true);
		epPaint.setStyle(Paint.Style.FILL);
		bgPaint = new Paint();
		bgPaint.setColor(bgColor);
		bgPaint.setAntiAlias(true);
		bgPaint.setStyle(Paint.Style.FILL);
		textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextAlign(Align.CENTER);
	}

	public LProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LProgressView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LProgressView(Context context) {
		this(context,null);
	}

	/**
	 * 获取角度
	 *
	 * @return
	 */
	private float getAngle() {
		if(havingInt == 0 || allInt == 0 || index == 0){
			showText = "0%";
			return 0f;
		}
		double a = 1d*index/allInt;
		showText = ((int)(a*100))+"%";

		return (float) (a*360);
	}
	/**
	 * 圆弧位置
	 *
	 * @return
	 */
	private RectF getRectF() {
		if (width > height) {
			return new RectF((width - height) / 2 + (diameter * 0.5f),
					(diameter * 0.5f), width - (width - height) / 2
					- (diameter * 0.5f), height - (diameter * 0.5f));
		} else {
			return new RectF((diameter * 0.5f), (height - width) / 2
					+ (diameter * 0.5f), width - (diameter * 0.5f), height
					- (height - width) / 2 - (diameter * 0.5f));
		}
	}
	/**
	 * 点坐标获取
	 * @return
	 */
	private float[] getLocation(float a) {
		float x1 = (float) (width / 2);
		float y1 = (float) (height / 2 - (diameter * 3));
		float x2 = (float) (width / 2 + (Math.cos((a-90)/180 * Math.PI) * diameter * 3));
		float y2 = (float) (height / 2 + (Math.sin((a-90)/180 * Math.PI) * diameter * 3));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * 获取进度条颜色
	 * @return
	 */
	public int getProColor() {
		return proColor;
	}

	/**
	 * 设置进度条颜色
	 * @param proColor
	 */
	public void setProColor(int proColor) {
		this.proColor = proColor;
		invalidate();
	}

	/**
	 * 获取文字颜色
	 * @return
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * 设置文字颜色
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		invalidate();
	}

	/**
	 * 获取背景颜色
	 * @return
	 */
	public int getBgColor() {
		return bgColor;
	}

	/**
	 * 设置背景颜色
	 * @param bgColor
	 */
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
		invalidate();
	}

	/**
	 * 获取总数
	 * @return
	 */
	public int getAllInt() {
		return allInt;
	}

	/**
	 * 设置总数
	 * @param allInt
	 */
	public void setAllInt(int allInt) {
		this.allInt = allInt;
		invalidate();
	}

	/**
	 * 获取已经完成进度
	 * @return
	 */
	public int getHavingInt() {
		return havingInt;
	}

	/**
	 * 设置已完成进度
	 * @param havingInt
	 */
	public void setHavingInt(int havingInt) {
		this.havingInt = havingInt;
		invalidate();
	}

	/**
	 * 获取是否显示进度条背景
	 * @return
	 */
	public boolean isProHaveBg() {
		return proHaveBg;
	}

	/**
	 * 设置是否显示进度条背景
	 * @param proHaveBg
	 */
	public void setProHaveBg(boolean proHaveBg) {
		this.proHaveBg = proHaveBg;
		invalidate();
	}

}
