package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 收支管理首页的饼状图
 * @author xiao
 *
 */
public class LPieView extends View {

	private int index = 0;
	private float step = 3.6f;
	private float redSize = 46564;
	private float greenSize = 465464;
	private int width;
	private int height;
	private Paint redPaint;
	private Paint greenPaint;
	private int radius;
	private int textSize;

	public LPieView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		redPaint = new Paint();
		redPaint.setAntiAlias(true);
		redPaint.setStyle(Paint.Style.FILL);
		redPaint.setColor(Color.RED);
		redPaint.setTextAlign(Align.CENTER);
		greenPaint = new Paint();
		greenPaint.setAntiAlias(true);
		greenPaint.setStyle(Paint.Style.FILL);
		greenPaint.setColor(Color.parseColor("#27ce48"));
		greenPaint.setTextAlign(Align.CENTER);
	}

	public LPieView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LPieView(Context context) {
		this(context,null);
	}



	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		if(width>height){
			radius = (int) (height/2*0.8);
		}else{
			radius = (int) (width/2*0.8);
		}
		drawPie(canvas);
		if(index<100){
			index++;
			invalidate();
		}else{
			drawText(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	/**
	 * 获取角度
	 * @return
	 */
	private float[] getLocation(){
		float redF = redSize/(redSize+greenSize);
		float greenF = greenSize/(redSize+greenSize);
		float end1 = index*step*greenF;
		float end2 = index*step*redF;
		return new float[]{end1,end2};
	}
	/**
	 * 画饼
	 * @param canvas
	 */
	private void drawPie(Canvas canvas){
		int r = 0;
		RectF oval;
		float[] f = getLocation();
		int[] alphas = {100,42,42,42};
		for(int i = 0;i<4;i++){
			r = radius-(radius/4*i);
			greenPaint.setAlpha(alphas[i]);
			redPaint.setAlpha(alphas[i]);
			oval = new RectF(width/2-r, height/2-r, width/2+r, height/2+r);
			canvas.drawArc(oval, -90, f[0], true, greenPaint);
			canvas.drawArc(oval, f[0]-90, f[1], true, redPaint);
		}
	}
	/**
	 * 画字
	 * @param canvas
	 */
	private void drawText(Canvas canvas){
		textSize = radius/7;
		greenPaint.setTextSize(textSize);
		redPaint.setTextSize(textSize);
		greenPaint.setAlpha(255);
		redPaint.setAlpha(255);
		float[] f = getLocation();
		float greenA = (360-f[0]/2)+180;
		float redA = (360-f[1]/2)+180-f[0];
		float[] greenLoc = getLocation(greenA, radius);
		float[] redLoc = getLocation(redA, radius);
		FontMetrics fm = redPaint.getFontMetrics();
		float TextY = -fm.descent + (fm.descent - fm.ascent) / 2;
		String Text = redSize+"";
		float x = 0;
		float y = 0;
		x = redLoc[0];
		y = redLoc[1];
		if(x-(Text.length()*textSize/2)<0){
			x = (Text.length()*textSize/2);
		}
		if(x+(Text.length()*textSize/2)>width){
			x = width-(Text.length()*textSize/2);
		}
		if(y-textSize/2<0){
			y = textSize/2;
		}
		if(y+textSize/2>height){
			y = height-textSize/2;
		}
		canvas.drawText(Text, x, y+TextY, redPaint);
		Text = greenSize+"";
		x = greenLoc[0];
		y = greenLoc[1];
		if(x-(Text.length()*textSize/2)<0){
			x = (Text.length()*textSize/2);
		}
		if(x+(Text.length()*textSize/2)>width){
			x = width-(Text.length()*textSize/2);
		}
		if(y-textSize/2<0){
			y = textSize/2;
		}
		if(y+textSize/2>height){
			y = height-textSize/2;
		}
		canvas.drawText(Text, x, y+TextY, greenPaint);


		canvas.drawCircle(width-textSize*7.5f, height-textSize*0.7f, textSize*0.2f, redPaint);
		canvas.drawCircle(width-textSize*3.5f, height-textSize*0.7f, textSize*0.2f, greenPaint);
		canvas.drawText("收入", width-textSize*6, height-TextY, redPaint);
		canvas.drawText("支出", width-textSize*2, height-TextY, greenPaint);
	}
	/**
	 * 根据角度及半径计算位置
	 * @param angle 角度
	 * @param radiu 半径
	 * @return
	 * 公式:利用三角函数反向计算
	 * X = 宽度/2 + Math.sin(角度/180*Math.PI)*半径
	 * Y = 高度/2 + Math.cos(角度/180*Math.PI)*半径
	 */
	private float[] getLocation(float angle,float radiu){
		//把角度矫正为0°在y轴正方向
		//angle += 180;
		float x = (float) (width / 2 + (Math.sin(angle/180 * Math.PI) * radiu));
		float y = (float) (height / 2 + (Math.cos(angle/180 * Math.PI) * radiu));
		return new float[]{x,y};
	}

	public float getRedSize() {
		return redSize;
	}

	public void setRedSize(float redSize) {
		this.redSize = redSize;
		invalidate();
	}

	public float getGreenSize() {
		return greenSize;
	}

	public void setGreenSize(float greenSize) {
		this.greenSize = greenSize;
		invalidate();
	}

}
