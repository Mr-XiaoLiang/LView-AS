package mr_xiaoliang.com.github.lview_as.view.phoneBookSuite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 首字母索引
 * @author LiuJ
 * 电话本列表套件之一
 */
public class LInitialsIndexView extends View {

	private int onTouchBgColor = Color.parseColor("#F63375");
	private int unTouchBgColor = Color.TRANSPARENT;
	private int onTouchTextColor = Color.WHITE;
	private int unTouchTextColor = Color.BLACK;
	private int onTouchSelectedColor = Color.GRAY;
	private int unTouchSelectedColor = onTouchBgColor;
	private int textSize = 0;
	private Paint textPaint,selectedPaint,bgPaint;
	private float roundedCorners = 0.3f;
	private String[] letters = null;
	private String selected = "";
	private boolean isTouch = false;
	private LInitialsIndexViewListener listener;
	
	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		FontMetrics fm = textPaint.getFontMetrics();
		int gridHeight = getHeight()/27;
		int topHeight = (27-letters.length)*gridHeight/2;
		float textY = gridHeight/2 - fm.descent + (fm.descent - fm.ascent) / 2;
		if(isTouch){
			bgPaint.setColor(onTouchBgColor);
			textPaint.setColor(onTouchTextColor);
			selectedPaint.setColor(onTouchSelectedColor);
		}else{
			bgPaint.setColor(unTouchBgColor);
			textPaint.setColor(unTouchTextColor);
			selectedPaint.setColor(unTouchSelectedColor);
		}
		canvas.drawRoundRect(0, 0, getWidth(), getHeight(), roundedCorners*getWidth(), roundedCorners*getWidth(), bgPaint);
		for(int i = 0;i<letters.length;i++){
			if(selected.equals(letters[i])){
				canvas.drawText(letters[i], getWidth()/2, gridHeight*i+textY+topHeight, selectedPaint);
			}else{
				canvas.drawText(letters[i], getWidth()/2, gridHeight*i+textY+topHeight, textPaint);
			}
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//只要指头在上面，有触发，就认为是操作
		int gridHeight = getHeight()/27;
		int topHeight = (27-letters.length)*gridHeight/2;
		int y = (int) event.getY();
		y-=topHeight;
		int i = (int) (y/gridHeight);
		if(i<0)
			i = 0;
		if(i>=letters.length)
			i = letters.length-1;
		selected = letters[i];
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(y<0||y>getHeight()-topHeight*2)
				return false;
			isTouch = true;
			if(listener!=null)
				listener.onInitialsIndexDown(this,i, letters[i]);
			break;
		case MotionEvent.ACTION_UP:
			isTouch = false;
			if(listener!=null)
				listener.onInitialsIndexUp(this,i, letters[i]);
			break;
		case MotionEvent.ACTION_MOVE:
			if(listener!=null)
				listener.onInitialsIndexSelected(this,i, letters[i]);
			break;
		}
		invalidate();
		return true;
	}
	
	public interface LInitialsIndexViewListener{
		public void onInitialsIndexSelected(View v, int i, String s);
		public void onInitialsIndexDown(View v, int i, String s);
		public void onInitialsIndexUp(View v, int i, String s);
	}
	
	private void init(){
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		selectedPaint = new Paint();
		selectedPaint.setAntiAlias(true);
		selectedPaint.setTextAlign(Align.CENTER);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		letters = new String[27];
		letters[0] = "#";
		char c = 'A';
		for(int i = 0;i<26;i++){
			letters[i+1] = String.valueOf((char)(c+i));
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		textSize = (int) getTextSize();
		textSize = 30;
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
		textPaint.setTextSize(textSize);
		selectedPaint.setTextSize(textSize);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
			if (textSize > specSize)
				textSize = specSize;
		} else {
			result = textSize * 2 + getPaddingLeft() + getPaddingRight();
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
			if (textSize > specSize / 27)
				textSize = specSize / 27;
		} else {
			result = textSize * 55 + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	
	@SuppressLint("NewApi")
	public LInitialsIndexView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public LInitialsIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}

	public LInitialsIndexView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LInitialsIndexView(Context context) {
		this(context,null);
	}


	public int getOnTouchBgColor() {
		return onTouchBgColor;
	}

	public void setOnTouchBgColor(int onTouchBgColor) {
		this.onTouchBgColor = onTouchBgColor;
		invalidate();
	}

	public int getUnTouchBgColor() {
		return unTouchBgColor;
	}

	public void setUnTouchBgColor(int unTouchBgColor) {
		this.unTouchBgColor = unTouchBgColor;
		invalidate();
	}

	public int getOnTouchTextColor() {
		return onTouchTextColor;
	}

	public void setOnTouchTextColor(int onTouchTextColor) {
		this.onTouchTextColor = onTouchTextColor;
		invalidate();
	}

	public int getUnTouchTextColor() {
		return unTouchTextColor;
	}

	public void setUnTouchTextColor(int unTouchTextColor) {
		this.unTouchTextColor = unTouchTextColor;
		invalidate();
	}

	public int getOnTouchSelectedColor() {
		return onTouchSelectedColor;
	}

	public void setOnTouchSelectedColor(int onTouchSelectedColor) {
		this.onTouchSelectedColor = onTouchSelectedColor;
		invalidate();
	}

	public int getUnTouchSelectedColor() {
		return unTouchSelectedColor;
	}

	public void setUnTouchSelectedColor(int unTouchSelectedColor) {
		this.unTouchSelectedColor = unTouchSelectedColor;
		invalidate();
	}

	public float getRoundedCorners() {
		return roundedCorners;
	}

	public void setRoundedCorners(float roundedCorners) {
		this.roundedCorners = roundedCorners;
		invalidate();
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
		invalidate();
	}

	public String[] getLetters() {
		return letters;
	}

	public void setLetters(String[] l) {
		this.letters = l;
		//排序,这里使用冒泡算法,效率不是最高，但是好在数据量不大且使用次数不多
		for(int i = 0;i<letters.length-1;i++){
			for(int j = 0;j<letters.length-i-1;j++){
				if(letters[j].charAt(0)>letters[j+1].charAt(0)){
					String b = letters[j];
					letters[j] = letters[j+1];
					letters[j+1] = b;
				}
			}
		}
		invalidate();
	}

	public LInitialsIndexViewListener getListener() {
		return listener;
	}

	public void setListener(LInitialsIndexViewListener listener) {
		this.listener = listener;
	}
	
}
