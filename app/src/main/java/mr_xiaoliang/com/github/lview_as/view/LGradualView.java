package mr_xiaoliang.com.github.lview_as.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
/**
 * 一个渐变的动画View
 * @author LiuJ
 */
@SuppressLint("NewApi")
public class LGradualView extends View {

	private int[] colors = new int[]{Color.GREEN,Color.RED,Color.BLUE,Color.CYAN,Color.DKGRAY,Color.LTGRAY,Color.MAGENTA};
	private double red = 0,green = 0,blue = 0;
	private Paint paint;
	private Shader mRadialGradient = null;
	private float width = 1,height = 1,radius = 1;
	private int speed = 255;
	private int thisColor = 0,nextColor = 0;
	private int step = 0;
	private boolean stop = false;
	private boolean changeCenterColor = true;
	private int sleep = 50;
	private boolean isDiffusion = false; 
	
	@Override
	protected void onDraw(Canvas canvas) {
		//注释掉这几行之后，概率性重新打开崩溃,原因是半径丢失
		width = getWidth();
		height = getHeight();
		radius = (float) Math.sqrt(width*width+height*height);
		onChange();
		canvas.drawRect(0, 0, width, height, paint);
		handler.sendEmptyMessageAtTime(200, sleep);
	}
	
	private Handler handler  = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 200:
				if(stop)
					return;
				if(isDiffusion){
					if(step<speed){
						step++;
					}else if(step==speed&&changeCenterColor){
						changeCenterColor = false;
						step = 0;
					}else if(step==speed&&!changeCenterColor){
						thisColor++;
						thisColor %= colors.length;
						changeCenterColor = true;
						step = 0;
						onColorChange();
					}
				}else{
					if(step<speed){
						step++;
					}else if(step==speed){
						thisColor++;
						thisColor %= colors.length;
						changeCenterColor = true;
						step = 0;
						onColorChange();
					}
				}
				invalidate();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	private void onColorChange(){
		nextColor = thisColor+1;
		nextColor %= colors.length;
		red = (Color.red(colors[nextColor])-Color.red(colors[thisColor]))*1.0/speed;
		green = (Color.green(colors[nextColor])-Color.green(colors[thisColor]))*1.0/speed;
		blue = (Color.blue(colors[nextColor])-Color.blue(colors[thisColor]))*1.0/speed;
	}
	
	private void onChange(){
		onColorChange();
		int r = (int) (Color.red(colors[thisColor])+red*step),
				g = (int) (Color.green(colors[thisColor])+green*step),
				b = (int) (Color.blue(colors[thisColor])+blue*step);
//		if(Math.abs(Color.red(colors[nextColor])-r)<Math.abs(red)){
//			r = Color.red(colors[nextColor]);
//		}
//		if(Math.abs(Color.green(colors[nextColor])-r)<Math.abs(green)){
//			g = Color.green(colors[nextColor]);
//		}
//		if(Math.abs(Color.blue(colors[nextColor])-r)<Math.abs(blue)){
//			b = Color.blue(colors[nextColor]);
//		}
		r = r>255?255:r;
		r = r<0?0:r;
		g = g>255?255:g;
		g = g<0?0:g;
		b = b>255?255:b;
		b = b<0?0:b;
		int centerColor = Color.rgb(r, g, b);
		if(isDiffusion){
		if(changeCenterColor){
			mRadialGradient = new RadialGradient(width / 2, height / 2, radius, centerColor,
					colors[thisColor], Shader.TileMode.REPEAT);
			paint.setColor(centerColor);
		}else{
			mRadialGradient = new RadialGradient(width / 2, height / 2, radius, colors[nextColor],
					centerColor, Shader.TileMode.REPEAT);
			paint.setColor(colors[thisColor]);
		}
		paint.setShader(mRadialGradient);
		}else{
			paint.setColor(centerColor);
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		width = getWidth();
		height = getHeight();
		radius = (float) Math.sqrt(width*width+height*height);
	}
	
	public LGradualView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	public LGradualView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}

	public LGradualView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LGradualView(Context context) {
		this(context,null);
	}

	public int[] getColors() {
		return colors;
	}
	/**
	 * 设置颜色集合
	 * @param colors
	 */
	public void setColors(int[] colors) {
		this.colors = colors;
		step = 0;
		onColorChange();
		invalidate();
	}

	public int getSpeed() {
		return speed;
	}
	/**
	 * 设置绘制速度
	 * @param speed 1-255
	 * 默认255
	 */
	public void setSpeed(int speed) {
		if(speed>255)
			speed = 255;
		if(speed<1)
			speed = 1;
		this.speed = speed;
	}

	public boolean isStop() {
		return stop;
	}
	/**
	 * 是否停止变色
	 * @param stop
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
		invalidate();
	}

	public int getSleep() {
		return sleep;
	}
	/**
	 * 设置绘制间隔时间
	 * @param sleep 毫秒
	 */
	public void setSleep(int sleep) {
		this.sleep = sleep;
	}
	
}
