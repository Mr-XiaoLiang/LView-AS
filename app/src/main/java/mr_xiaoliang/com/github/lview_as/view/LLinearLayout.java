package mr_xiaoliang.com.github.lview_as.view;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 带波纹的LinearLayout（MD水墨波纹效果）
 * 因为设计中是允许同时多个view带有动画的
 * 但是考虑到事件分发的延迟（用来显示完整的动画）
 * 所以暂时放弃本view的制作
 * @author LiuJ
 *
 */
public class LLinearLayout extends LinearLayout implements Runnable {

	private Paint paint;
	private int color = Color.GRAY;// getSolidColor();
	private ShowType showType = ShowType.BOTH;
	private int width = 0, height = 0;
	private int maxRadius = 0;
	private boolean isUp = true;
	private ArrayList<Point> points;
	private int step = 0;
	private int stepRatio = 5;
	private int downX, DownY;
	private boolean onlyOnClick = false;
	private int paintAlpha = 50;
	private int INVALIDATE_DURATION = 40;

	@SuppressLint("NewApi")
	public LLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		setWillNotDraw(false);
		paint = new Paint();
		paint.setAlpha(paintAlpha);
		paint.setAntiAlias(true);
		points = new ArrayList<Point>();
	}

	@SuppressLint("NewApi")
	public LLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public LLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LLinearLayout(Context context) {
		this(context, null);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		for (Point p : points) {
			setColor(p);
			canvas.drawCircle(p.getX(), p.getY(), p.getR(), paint);
		}
		if (points.size() > 0) {
			onChange();
			for (Point p : points) {//每个控件都更新到
				postInvalidateDelayed(INVALIDATE_DURATION, p.rect.left, p.rect.top, p.rect.right, p.rect.bottom);
			}
		}

	}

	private void setColor(Point p) {
		int alpha = paintAlpha;
		switch (p.getT()) {
		case UP:
			alpha = (int) ((maxRadius - p.getR()) * 1.0 / maxRadius * paintAlpha);
			break;
		case DOWN:
			break;
		default:
			break;
		}
		if (alpha > 255)
			alpha = 255;
		if (alpha < 0)
			alpha = 0;
		paint.setAlpha(alpha);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		width = getWidth();
		height = getHeight();
		maxRadius = Math.max(height, width) / 2;
		if (step == 0)
			step = maxRadius / 100 * stepRatio;
	}

	private void onChange() {
		int downSize = 0;
		int downEndSize = 0;
		Point old = null;
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			Point point = it.next();
			if (point.getT() == ShowType.UP) {
				if (point.isEnd()) {
					it.remove();
				} else {
					point.next(step);
				}
			} else if (point.getT() == ShowType.DOWN) {
				if (isUp) {
					it.remove();
					continue;
				}
				old = point;
				downSize++;
				if (point.isEnd()) {
					downEndSize++;
				} else {
					point.next(step);
				}
			}
		}
		if (!isUp && downSize < 2 && downEndSize > 0) {
			if(old!=null)
				points.add(old.getNew());
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        View touchTarget;
		if (!onlyOnClick||hasOnClickListeners()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				 touchTarget = getTouchTarget(this, x, y);
		            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
		            	if (showType == ShowType.BOTH || showType == ShowType.DOWN) {
		            		points.add(getPointForChild(event, touchTarget));
		            		postInvalidateDelayed(INVALIDATE_DURATION);
		            	}
		            }
				break;
			case MotionEvent.ACTION_UP:
				touchTarget = getTouchTarget(this, x, y);
	            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
	            	if (showType == ShowType.BOTH || showType == ShowType.UP) {
	            		points.add(getPointForChild(event, touchTarget));
	            		postInvalidateDelayed(INVALIDATE_DURATION);
	            	}
	            }
				break;
			}
		}
		return super.dispatchTouchEvent(event);
	}
	
	@Override
    public boolean performClick() {
        postDelayed(this, 400);
        return true;
    }

	private Point getPointForChild(MotionEvent event, View view) {
		ShowType t = ShowType.UP;
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			t = ShowType.DOWN;
		}
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		int targetWidth = view.getMeasuredWidth();
		int targetHeight = view.getMeasuredHeight();
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		Rect rect = new Rect(location[0], location[1], location[0] + targetWidth, location[1] + targetHeight);
		return new Point(x, y, 1, t, rect);
	}

	private View getTouchTarget(View view, int x, int y) {
		View target = null;
		ArrayList<View> TouchableViews = view.getTouchables();
		for (View child : TouchableViews) {
			if (isTouchPointInView(child, x, y)) {
				target = child;
				break;
			}
		}

		return target;
	}

	private boolean isTouchPointInView(View view, int x, int y) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int left = location[0];
		int top = location[1];
		int right = left + view.getMeasuredWidth();
		int bottom = top + view.getMeasuredHeight();
		if (view.isClickable() && y >= top && y <= bottom && x >= left && x <= right) {
			return true;
		}
		return false;
	}

	public enum ShowType {
		DOWN, UP, BOTH
	}

	private class Point {
		// X坐标
		private int x;
		// Y坐标
		private int y;
		// 半径
		private int r;
		// 当前类型
		private ShowType t;
		// 最大限度
		private int max = 0;
		// 绘制范围（所在view的尺寸）
		private Rect rect;

		public boolean isEnd() {
			if (r >= max) {
				return true;
			}
			return false;
		}

		public Point(int x, int y, int r, ShowType t, Rect rect) {
			super();
			this.x = x;
			this.y = y;
			this.r = r;
			this.t = t;
			this.rect = rect;
			switch (t) {
			case DOWN:
				max = getMax(x, y, rect);
				break;
			case UP:
				max = maxRadius;
				break;
			}
		}

		public Point getNew(){
			return new Point(x, y, 1, t, rect);
		}
		
		public void next(int step) {
			r += step;
		}

		public int getR() {
			return r;
		}

		public void setR(int r) {
			this.r = r;
		}

		public ShowType getT() {
			return t;
		}

		public void setT(ShowType t) {
			this.t = t;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Rect getRect() {
			return rect;
		}

		public void setRect(Rect rect) {
			this.rect = rect;
		}

	}

	private int getMax(float x, float y, Rect rect) {
		double xLeftTop = Math.abs(x - rect.left);
		double yLeftTop = Math.abs(y - rect.top);
		double rLeftTop = Math.abs(Math.sqrt(xLeftTop * xLeftTop + yLeftTop * yLeftTop));
		double xRightTop = Math.abs(x - rect.right);
		double yRightTop = Math.abs(y - rect.top);
		double rRightTop = Math.abs(Math.sqrt(xRightTop * xRightTop + yRightTop * yRightTop));
		double xLeftBottom = Math.abs(x - rect.left);
		double yLeftBottom = Math.abs(y - rect.bottom);
		double rLeftBottom = Math.abs(Math.sqrt(xLeftBottom * xLeftBottom + yLeftBottom * yLeftBottom));
		double xRightBottom = Math.abs(x - rect.right);
		double yRightBottom = Math.abs(y - rect.bottom);
		double rRightBottom = Math.abs(Math.sqrt(xRightBottom * xRightBottom + yRightBottom * yRightBottom));
		double left = Math.max(rLeftTop, rLeftBottom);
		double right = Math.max(rRightTop, rRightBottom);
		return (int) Math.max(left, right);
	}

	public int getShowColor() {
		return color;
	}

	public void setShowColor(int color) {
		this.color = color;
	}
	@Override
    public void run() {
        super.performClick();
    }
}
