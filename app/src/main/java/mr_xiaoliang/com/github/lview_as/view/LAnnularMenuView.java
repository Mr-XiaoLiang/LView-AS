package mr_xiaoliang.com.github.lview_as.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.option.LAnnularMenuViewOption;

/**
 * 环形的菜单，类似建设银行的首页菜单 原本我是打算写成AdapterView的 但是看了ListView这些的源码发现看不懂 所以放弃了，先写个简化版的
 * 继承自View，扩展性比较差 不过勉强可以使用就是了
 * 
 * @author LiuJ
 *
 */
public class LAnnularMenuView extends View {

	private LAnnularMenuViewOption option;
	private Paint paint;
	private Paint bgPaint;
	// 半径
	private int radius = 0;
	// 圆环的中心点
	private int rX, rY;
	// 角度的偏移量
	private int offSet = 0;
	private int itemDiameter = 0;
	private int widht = 0, height = 0;
	private int maxOffSet = 0;
	private Shader detailBgRadialGradient = null;
	private int detailMargin = 0;
	private Shader ringBg = null;
	private Shader ringSrc = null;
	
	private void init() {
		int gradientRadius = 0;
		if (option == null || widht < 1 || height < 1)
			return;
		maxOffSet = 180 / (option.getMaxItemCount() + 1);
		switch (option.getGravityType()) {
		case Bottom:
		case Top:
			if (option.getRingDiameter() < 1)
				radius = (int) (widht / (option.getItemProportionWithRing() + 2) * option.getItemProportionWithRing()
						/ 2);
			else
				radius = widht * option.getRingDiameter() / 2;
			itemDiameter = (int) (radius / option.getItemProportionWithRing());
			if (option.getGravityType() == LAnnularMenuViewOption.GravityType.Top) {
				rY = height - radius - itemDiameter * 2;
				if (rY > Math.min(radius, widht / 2))
					rY = Math.min(radius, widht / 2);
				gradientRadius = height - rY;
			} else {
				rY = radius + itemDiameter * 2;
				if (height - rY > Math.min(radius, widht / 2))
					rY = height - Math.min(radius, widht / 2);
				gradientRadius = rY;
			}
			rX = widht / 2;
			if(widht/2 > radius){
				detailMargin = widht/2 - radius + itemDiameter/2;
			}else{
				detailMargin = itemDiameter/2;
			}
			break;
		case Left:
		case Right:
			if (option.getRingDiameter() < 1)
				radius = (int) (height / (option.getItemProportionWithRing() + 2) * option.getItemProportionWithRing()
						/ 2);
			else
				radius = height * option.getRingDiameter() / 2;
			itemDiameter = (int) (radius / option.getItemProportionWithRing());
			if (option.getGravityType() == LAnnularMenuViewOption.GravityType.Left) {
				rX = widht - radius - itemDiameter * 2;
				if (rX > Math.min(radius, height / 2))
					rX = Math.min(radius, height / 2);
				gradientRadius = widht - rX;
			} else {
				rX = radius + itemDiameter * 2;
				if (widht - rX > Math.min(radius, height / 2))
					rX = widht - Math.min(radius, height / 2);
				gradientRadius = rX;
			}
			rY = height / 2;
			if(height/2 > radius){
				detailMargin = height/2 - radius + itemDiameter/2;
			}else{
				detailMargin = itemDiameter/2;
			}
			break;
		default:
			break;
		}
		detailBgRadialGradient = new RadialGradient(rX, rY, gradientRadius,
				new int[] { option.getBgColor(), Color.TRANSPARENT }, new float[] { 0.8f, 0.2f },
				TileMode.REPEAT);
		if(option.getBg()!=null){
			if(option.getBackgroundType()== LAnnularMenuViewOption.BackgroundType.S){
				ringBg = getCroppedRoundBitmap(option.getBg(), radius);
			}else{
				ringBg = getCroppedRoundBitmap(option.getBg(), radius+itemDiameter*2);
			}
		}else{
			bgPaint.setColor(option.getBgColor());
		}
		if(option.getSrc()!=null){
			ringSrc = getCroppedRoundBitmap(option.getSrc(), radius);
		}
	}

	private void drawBg(Canvas canvas) {
		switch (option.getBackgroundType()) {
		case L:
			bgPaint.setShader(detailBgRadialGradient);
			switch (option.getGravityType()) {
			case Bottom:
				canvas.drawRect(detailMargin, 0, widht-detailMargin, rY-radius-itemDiameter*2, bgPaint);
				break;
			case Left:
				canvas.drawRect(rX+radius+itemDiameter*2, detailMargin, widht, height-detailMargin, bgPaint);
				break;
			case Right:
				canvas.drawRect(0, detailMargin, rX-radius-itemDiameter*2, height-detailMargin, bgPaint);
				break;
			case Top:
				canvas.drawRect(detailMargin, rY+radius+itemDiameter*2, widht-detailMargin, height, bgPaint);
				break;
			default:
				break;
			}
			bgPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
			canvas.drawCircle(rX, rY, radius+itemDiameter*2, bgPaint);
			bgPaint.reset();
			bgPaint.setAntiAlias(true);
		case M:
			if(option.getBg()!=null){
				bgPaint.setShader(ringBg);
				canvas.translate(rX-radius-itemDiameter*2, rY-radius-itemDiameter*2);
				canvas.drawCircle(rX, rY, radius+itemDiameter*2, bgPaint);
				canvas.restore();
			}else{
				canvas.drawCircle(rX, rY, radius+itemDiameter*2, bgPaint);
			}
			if(option.getSrc()!=null){
				bgPaint.setShader(ringSrc);
				canvas.translate(rX-radius, rY-radius);
				canvas.drawCircle(rX, rY, radius, bgPaint);
				canvas.restore();
			}
			bgPaint.reset();
			bgPaint.setAntiAlias(true);
			break;
		case S:
			if(option.getSrc()!=null){
				bgPaint.setShader(ringSrc);
				canvas.translate(rX-radius, rY-radius);
				canvas.drawCircle(rX, rY, radius, bgPaint);
				canvas.restore();
			}
			bgPaint.reset();
			bgPaint.setAntiAlias(true);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawBg(canvas);
		// TODO Auto-generated method stub
	}
	

	/**
	 * 使用渲染来绘制圆形图片
	 * 
	 * @param bmp
	 * @param radius
	 * @return
	 */
	public Shader getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Matrix matrix = null;
		// 将bmp作为着色器，就是在指定区域内绘制bmp
		Shader mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		scale = Math.max(radius * 2.0f / bmp.getWidth(), radius * 2.0f / bmp.getHeight());
		if(matrix==null){
			matrix = new Matrix();
		}
		// shader的变换矩阵，我们这里主要用于放大或者缩小
		matrix.setScale(scale, scale);
		// 设置变换矩阵
		mBitmapShader.setLocalMatrix(matrix);
		return mBitmapShader;
	}
	
	@SuppressLint("NewApi")
	public LAnnularMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		paint = new Paint();
		paint.setAntiAlias(true);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
	}

	public LAnnularMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public LAnnularMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LAnnularMenuView(Context context) {
		this(context, null);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		widht = getWidth();
		height = getHeight();
		init();
	}

	public LAnnularMenuViewOption getOption() {
		return option;
	}

	public void setOption(LAnnularMenuViewOption option) {
		this.option = option;
		init();
	}

}
