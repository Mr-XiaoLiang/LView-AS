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
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 倒计时View
 * 
 * @author LiuJ 
 * 本view在列表里面存在大问题，放弃了，以后再说吧
 */
public class LCountDownView2 extends SurfaceView implements Callback {

	private SurfaceHolder holder;

	private boolean isRun = true;

	private Thread thread;

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
	private LCountDownViewListener2 listener;
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

	public LCountDownView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.parseColor("#F63375"));
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		sdf = new SimpleDateFormat("mm:ss");
		calendar = Calendar.getInstance();
	}

	public LCountDownView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LCountDownView2(Context context) {
		this(context, null);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isRun = true;
		if (thread == null) {
			thread = new Thread(new RunTask());
		}
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRun = false;
	}

	private class RunTask implements Runnable {

		private Canvas canvas;

		@Override
		public void run() {
			while (isRun) {
				synchronized (holder) {
					try {
						canvas = holder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
						canvas.drawColor(Color.WHITE);// 设置画布背景颜色
						change();
						FontMetrics fm = paint.getFontMetrics();
						float textY = getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
						canvas.drawText(timeStr, getWidth() / 2, textY, paint);
						Thread.sleep(1000);
					} catch (Exception e) {
					} finally {
						holder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}

	/**
	 * 倒计时通知
	 * 
	 * @author LiuJ
	 *
	 */
	public interface LCountDownViewListener2 {
		public void onCountDown(int thisPosition, int thisID, int second, String date, long timestamp);

		public void onCountDownForEnd(int thisPosition, int thisID);
	}

	private void change() {
		time--;
		if (time < 0 && conutDownForEndToZero) {// 等于0的原因是，只让他发起一次回调
			if (listener != null) {
				listener.onCountDownForEnd(thisPosition, thisID);
			}
			timeStr = "00:00";
		} else {
			timeStr = "";
			if (time / 60 < 10) {
				timeStr += "0" + (time / 60);
			} else {
				timeStr += (time / 60);
			}
			timeStr += ":";
			if (time % 60 < 10) {
				timeStr += "0" + (time % 60);
			} else {
				timeStr += (time % 60);
			}
		}
		textSize = getHeight()/3*2;
		if(textSize*timeStr.length()>getWidth()){
			textSize = getWidth()/timeStr.length();
		}
		paint.setTextSize(textSize);
		if(listener!=null)
			listener.onCountDown(thisPosition, thisID, (int)time,timeStr,System.currentTimeMillis());
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public LCountDownViewListener2 getListener() {
		return listener;
	}

	public void setListener(LCountDownViewListener2 listener) {
		this.listener = listener;
	}

	public boolean isConutDownForEndToZero() {
		return conutDownForEndToZero;
	}

	public void setConutDownForEndToZero(boolean conutDownForEndToZero) {
		this.conutDownForEndToZero = conutDownForEndToZero;
	}
	
}
