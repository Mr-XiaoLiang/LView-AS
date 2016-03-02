package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
/**
 * 商品数量修改的自定义view
 * @author LiuJ
 * 用于购物车等商城app
 */
public class LGoodsSizeChangeView extends TextView {
	/**
	 * 当前序号
	 */
	private int thisPosition;
	/**
	 * 当前id
	 */
	private int thisID;
	/**
	 * 画笔
	 */
	private Paint paint;
	/**
	 * 文字颜色
	 */
	private int textColor = Color.parseColor("#666666");
	/**
	 * 边框颜色
	 */
	private int borderColor = Color.parseColor("#DDDDDD");
	/**
	 * 背景颜色
	 */
	private int bgColor = -1;
	/**
	 * 文字大小
	 */
	private float textSize;
	/**
	 * 商品数量
	 */
	private int goodsSize = 2;
	/**
	 * 是否按下
	 */
	private boolean isPressed = false;
	/**
	 * 是否按下左边
	 */
	private boolean isLeft = false;
	/**
	 * 监听器
	 */
	private OnLGoodsSizeChangeViewListener listener;
	/**
	 * 当前长度
	 */
	private int thisLength = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float x = event.getX();
			if (x > 0 && x < textSize * 2) {
				isPressed = true;
				isLeft = true;
				goodsSize--;
				if(listener!=null){
					listener.onGoodSizeChange(thisPosition, thisID+"", goodsSize);
				}
			} else if (x < getWidth() && x > getWidth() - textSize * 2) {
				isPressed = true;
				goodsSize++;
				isLeft = false;
				if(listener!=null){
					listener.onGoodSizeChange(thisPosition, thisID+"", goodsSize);
				}
			}
			if (goodsSize < 1) {
				goodsSize = 1;
			}
			if ((goodsSize + "").length() != thisLength) {
				requestLayout();
				thisLength = (goodsSize + "").length();
			}
			break;
		case MotionEvent.ACTION_UP:
			isPressed = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if(event.getX()<0||event.getX()>getWidth()||event.getY()<0||event.getY()>getHeight()){
				isPressed = false;
			}
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(borderColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		// 画格子
		canvas.drawRect(0, 0, getWidth() - 2, getHeight() - 2, paint);
		canvas.drawLine(getWidth() - textSize * 2, 0, getWidth() - textSize * 2, getHeight(), paint);
		canvas.drawLine(textSize * 2, 0, textSize * 2, getHeight() - 2, paint);
		if (isPressed) {
			paint.setStyle(Paint.Style.FILL);
			if (isLeft) {
				canvas.drawRect(0, 0, textSize * 2, getHeight(), paint);
			} else {
				canvas.drawRect(getWidth() - textSize * 2, 0, getWidth(), getHeight(), paint);
			}
		}
		drawSymbol(canvas);

		// 画字
		FontMetrics fm = paint.getFontMetrics();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(textColor);
		float textY = getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(goodsSize + "", getWidth() / 2, textY, paint);
		
		//某些情况下，点击阴影提示会不自动消失
		//此处强行关闭
		isPressed = false;
	}
	/**
	 * 画符号
	 * @param canvas
	 */
	private void drawSymbol(Canvas canvas) {
		// 减号
		if (goodsSize > 1) {
			canvas.drawLine(textSize + getHeight() / 4, getHeight() / 2, textSize - getHeight() / 4, getHeight() / 2,
					paint);
		}
		// 加号
		canvas.drawLine((getWidth() - textSize) + getHeight() / 4, getHeight() / 2,
				(getWidth() - textSize) - getHeight() / 4, getHeight() / 2, paint);
		canvas.drawLine(getWidth() - textSize, getHeight() / 4, getWidth() - textSize, getHeight() / 4 * 3, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		textSize = getTextSize();
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
		paint.setTextSize(textSize);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
			if (textSize > specSize / ((goodsSize + "").length() + 5))
				textSize = specSize / ((goodsSize + "").length() + 5);
		} else {
			result = (int) (textSize * ((goodsSize + "").length() + 5)) + getPaddingLeft() + getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
			if (textSize > specSize / 2)
				textSize = specSize / 2;
		} else {
			result = (int) (textSize * 2) + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	public LGoodsSizeChangeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
	}

	public LGoodsSizeChangeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LGoodsSizeChangeView(Context context) {
		this(context, null);
	}

	/**
	 * 商品数量修改监听器
	 * 
	 * @author LiuJ
	 *
	 */
	public interface OnLGoodsSizeChangeViewListener {
		public void onGoodSizeChange(int thisPosition, String thisID, int goodsSize);
	}

	public OnLGoodsSizeChangeViewListener getListener() {
		return listener;
	}

	public void setListener(OnLGoodsSizeChangeViewListener listener) {
		this.listener = listener;
	}

	public int getPosition() {
		return thisPosition;
	}

	public void setPosition(int thisPosition) {
		this.thisPosition = thisPosition;
	}

	public int getThisID() {
		return thisID;
	}

	public void setThisID(int thisID) {
		this.thisID = thisID;
	}

	public int getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(int thisPosition,int goodsSize) {
		this.goodsSize = goodsSize;
		this.thisPosition = thisPosition;
		isPressed = false;
		if(thisLength != (goodsSize + "").length()){
			thisLength = (goodsSize + "").length();
			requestLayout();
		}
		invalidate();
	}
	
	public void setGoodsSize(int goodsSize) {
		this.goodsSize = goodsSize;
		if(thisLength != (goodsSize + "").length()){
			thisLength = (goodsSize + "").length();
			requestLayout();
		}
		invalidate();
	}

}
