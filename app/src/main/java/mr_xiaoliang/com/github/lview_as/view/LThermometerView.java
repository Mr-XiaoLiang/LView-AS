package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * 温度计视图,用来显示进度
 * @author LiuJ
 *
 */
public class LThermometerView extends View {
	/**
	 * 外壳画笔
	 */
	private Paint shellPaint;
	/**
	 * 液体画笔
	 */
	private Paint liquidPaint;
	/**
	 * 外壳颜色
	 */
	private int shellColor = Color.parseColor("#70d29c");
	/**
	 * 液体颜色
	 */
	private int liquidColor = Color.parseColor("#70d29c");
	/**
	 * 步长
	 */
	private float step;
	/**
	 * 宽度
	 */
	private int width;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 外壳宽度
	 */
	private float shellWidth;
	/**
	 * 下标
	 */
	private int index = 0;
	/**
	 * 当前最大量
	 */
	private int max = 0;

	public LThermometerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		shellPaint = new Paint();
		shellPaint.setAntiAlias(true);
		shellPaint.setStyle(Paint.Style.STROKE);
		shellPaint.setColor(shellColor);
		liquidPaint = new Paint();
		liquidPaint.setAntiAlias(true);
		liquidPaint.setStyle(Paint.Style.FILL);
		liquidPaint.setColor(liquidColor);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		shellWidth = (float) (width * 0.15);
		shellPaint.setStrokeWidth(shellWidth);
		step = (height-width)/100;
		/**
		 * 计算液体高度
		 */
		float liquidHeight = shellWidth / 2
				+ (100 - index) * step;
		if(liquidHeight<shellWidth / 2){
			liquidHeight=shellWidth / 2;
		}
		if(liquidHeight>height-width/2){
			liquidHeight=height-width/2;
		}
		/**
		 * 画液体
		 */
		RectF rect = new RectF(width * 0.2f+shellWidth / 2, liquidHeight, width * 0.8f-shellWidth / 2, height - width / 2);
		canvas.drawRoundRect(rect, width*0.3f, width*0.3f, liquidPaint);
		canvas.drawCircle(width / 2, height-width / 2, (width-shellWidth)/2, liquidPaint);
		/**
		 * 画外壳
		 */
		rect = new RectF(width * 0.2f+shellWidth / 2, shellWidth / 2, width * 0.8f-shellWidth / 2, height - width / 2);
		canvas.drawRoundRect(rect, width*0.3f, width*0.3f, shellPaint);
		canvas.drawCircle(width / 2, height-width / 2, (width-shellWidth)/2, shellPaint);
		if(index>max){
			index--;
			invalidate();
		}
		if(index<max){
			index++;
			invalidate();
		}
	}

	public LThermometerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LThermometerView(Context context) {
		this(context, null);
	}
	/**
	 * 设置液体最高位置
	 * 每一份代表温度计高度的百分之一
	 * 理论最高值为100
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
		invalidate();
	}
}
