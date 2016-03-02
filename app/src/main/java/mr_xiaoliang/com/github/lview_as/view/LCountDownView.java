package mr_xiaoliang.com.github.lview_as.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 倒计时控件
 * @author LiuJ
 *
 */
public class LCountDownView extends TextView {
	/**
	 * 字体大小
	 */
	private float textSize;
	/**
	 * 画笔
	 */
	private Paint paint;
	/**
	 * 时间文字
	 */
	private String timeStr = "00:00";
	/**
	 * 时间 秒
	 */
	private long time = 0;
	/**
	 * 是否已经结束
	 */
	private boolean isDetached = false;
	/**
	 * 当前序号
	 */
	private int thisPosition;
	/**
	 * 当前id
	 */
	private int thisID;
	/**
	 * 回调监听
	 */
	private LCountDownViewListener listener;
	/**
	 * 长度
	 */
	private int strLength = 0;
	/**
	 * 线程管理
	 */
	private Thread thread;
	/**
	 * 日期格式化
	 */
	private SimpleDateFormat sdf;
	/**
	 * 日历类
	 */
	private Calendar calendar;
	/**
	 * 倒计时到0时停止
	 */
	private boolean conutDownForEndToZero = true;
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		FontMetrics fm = paint.getFontMetrics();
		float textY = getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(timeStr, getWidth()/2, textY, paint);
	}
	
	public LCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		paint.setColor(Color.parseColor("#F63375"));
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		isDetached = false;
		thread = new Thread(new TimeRunnable());
		thread.start();
		sdf = new SimpleDateFormat("mm:ss");
		calendar = Calendar.getInstance();
	}

	public LCountDownView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LCountDownView(Context context) {
		this(context,null);
	}
	
	private void change(){
		if(time<0&&conutDownForEndToZero){//等于0的原因是，只让他发起一次回调
			if(listener!=null){
				listener.onCountDownForEnd(thisPosition, thisID);
			}
			timeStr = "00:00";
		}else{
			timeStr = "";
			if(time/60<10){
				timeStr+="0"+(time/60);
			}else{
				timeStr+=(time/60);
			}
			timeStr+=":";
			if(time%60<10){
				timeStr+="0"+(time%60);
			}else{
				timeStr+=(time%60);
			}
		}
		if(strLength!=timeStr.length()){
			requestLayout();
		}
		strLength = timeStr.length();
		if(thread==null){
			thread = new Thread(new TimeRunnable());
			thread.start();
		}
	}
	
	private class TimeRunnable implements Runnable{
		@Override
		public void run() {
			while (!isDetached) {
				handler.sendEmptyMessage(200);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 200:
				time--;
				if(time<0){
					break;
				}
				change();
				if(listener!=null){
					listener.onCountDown(thisPosition, thisID, (int)time,timeStr,System.currentTimeMillis());
				}
				invalidate();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	/**
	 * 倒计时通知
	 * @author LiuJ
	 *
	 */
	public interface LCountDownViewListener{
		public void onCountDown(int thisPosition, int thisID, int second, String date, long timestamp);
		public void onCountDownForEnd(int thisPosition, int thisID);
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
			if (textSize > specSize / (timeStr.length() + 2))
				textSize = specSize / ((timeStr.length() + 2));
		} else {
			result = (int) (textSize * ((timeStr.length() + 2))) + getPaddingLeft() + getPaddingRight();
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

	public long getTime() {
		return time;
	}

	public void setTime(int thisPosition,int thisID,int second,long timestamp) {
		this.time = second;
		this.thisPosition = thisPosition;
		this.thisID = thisID;
		long thisTimestamp = System.currentTimeMillis();
		if(timestamp!=0){
			time-=(thisTimestamp-timestamp)/1000;
			if((thisTimestamp-timestamp)%1000>500){
				time++;
			}
		}
		change();
		invalidate();
	}

	public void setTime(int thisPosition,int thisID,String date,long timestamp) {
		int minute,second;
		try {
			calendar.setTime(sdf.parse(date));
			minute = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
			this.time = minute*60+second;
			this.thisPosition = thisPosition;
			this.thisID = thisID;
			long thisTimestamp = System.currentTimeMillis();
			if(timestamp!=0){
				time-=(thisTimestamp-timestamp)/1000;
				if((thisTimestamp-timestamp)%1000>500){
					time++;
				}
			}
			change();
			invalidate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		isDetached = true;
		super.onDetachedFromWindow();
	}

	public LCountDownViewListener getListener() {
		return listener;
	}

	public void setListener(LCountDownViewListener listener) {
		this.listener = listener;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public boolean isConutDownForEndToZero() {
		return conutDownForEndToZero;
	}

	public void setConutDownForEndToZero(boolean conutDownForEndToZero) {
		this.conutDownForEndToZero = conutDownForEndToZero;
	}
	
}
