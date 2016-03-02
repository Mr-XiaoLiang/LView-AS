package mr_xiaoliang.com.github.lview_as.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 雷达图
 * @author LiuJ
 * @time 2015 10 14
 * @
 */
public class LRadarView extends View {
	/**
	 * 背景画笔
	 */
	private Paint bgPaint;
	/**
	 * 前景画笔
	 */
	private Paint graphPaint;
	/**
	 * 文字画笔
	 */
	private Paint textPaint;
	/**
	 * 文字画笔
	 */
	private Paint smallTextPaint;
	/**
	 * 背景色
	 * 从外到里面
	 */
	private int[] bgColors = { Color.parseColor("#609bd2"), Color.parseColor("#77abdb"),
			Color.parseColor("#8bbae5"), Color.parseColor("#b9d7f3"), Color.parseColor("#d6e8fa") };
	/**
	 * 背景的透明度
	 * 从外面到里面
	 */
	private int[] bgAlphas = {102,128,179,204,230};
	/**
	 * 前景颜色
	 */
	private int graphColor = Color.parseColor("#6c6fea");
	/**
	 * 数据集
	 * 一个map就一个顶点
	 * map包含了点的基本信息
	 * 名字
	 * 显示的值
	 * 百分比
	 */
	private ArrayList<HashMap<String, Object>> dataArray;
	/**
	 * 名字
	 * 传入数据的数据名
	 */
	public static final String VALUE_NAME = "VALUE_NAME";
	/**
	 * 百分比
	 * 传入数据的数据名
	 */
	public static final String VALUE_PERCENT = "VALUE_PERCENT";
	/**
	 * 显示值
	 * 传入数据的数据名
	 */
	public static final String VALUE_VALUE = "VALUE_VALUE";
	/**
	 * 顶点数量
	 */
	private int pointNum = 0;
	/**
	 * 单元角度
	 */
	private float angle = 0f;
	/**
	 * 半径
	 */
	private float radius = 0;
	/**
	 * 宽度
	 */
	private float width = 0;
	/**
	 * 高度
	 */
	private float height = 0;
	/**
	 * 大字尺寸
	 */
	private float bigTaxtSize = 0;
	/**
	 * 小字尺寸
	 */
	private float smallTaxtSize = 0;
	/**
	 * 动画计数器
	 */
	private int index = 0;
	/**
	 * 回调监听
	 */
	private RadarViewListener lis;

	public LRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setStyle(Paint.Style.FILL);

		graphPaint = new Paint();
		graphPaint.setAntiAlias(true);
		graphPaint.setStyle(Paint.Style.FILL);
		graphPaint.setColor(graphColor);
		graphPaint.setAlpha(128);

		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);

		smallTextPaint = new Paint();
		smallTextPaint.setAntiAlias(true);
		smallTextPaint.setColor(graphColor);
		smallTextPaint.setTextAlign(Align.CENTER);

		dataArray = new ArrayList<HashMap<String,Object>>();
		for(int i = 0;i<7;i++){
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put(VALUE_NAME, "name"+i);
			map.put(VALUE_PERCENT, i*20);
			map.put(VALUE_VALUE, i*20+"");
			dataArray.add(map);
		}
	}

	public LRadarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LRadarView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		/**
		 * 在这里计算尺寸是因为在view创建之初获取尺寸是有问题的
		 * 所以在调用这个方法的时候获取的尺寸才是有效地
		 */
		width = getWidth();
		height = getHeight();
		//保证圆与文字显示正常
		if((height*2/3)>(width/2)){
			radius = width/2/2;
		}else{
			radius = height*2/3/2;
		}
		bigTaxtSize = radius/8;
		smallTaxtSize = radius/12;
		pointNum = dataArray.size();
		if(pointNum!=0){
			angle = 360/pointNum;
			drawBg(canvas);
			drawGraph(canvas);
			drawText(canvas);
			if(index<=100){
				index++;
				invalidate();
			}
		}
		super.onDraw(canvas);
	}
	/**
	 * 绘制雷达背景
	 * @param canvas
	 */
	private void drawBg(Canvas canvas) {
		float radiusWidth = radius/5;
		//5层
		for(int i = 0;i<5;i++){
			bgPaint.setColor(bgColors[i]);
			bgPaint.setAlpha(bgAlphas[i]);
			//定点
			Path path = new Path();
			for(int j = 0;j<pointNum;j++){
				float[] p = getLocation(j*angle, radius-(i*radiusWidth));
				if(j==0){
					path.moveTo(p[0], p[1]);
				}else{
					path.lineTo(p[0], p[1]);
				}
			}
			path.close();
			canvas.drawPath(path, bgPaint);
		}
	}
	/**
	 * 绘制雷达前景
	 * @param canvas
	 */
	private void drawGraph(Canvas canvas) {
		Path path = new Path();
		for(int j = 0;j<pointNum;j++){
			float[] p;
			int per = (Integer)dataArray.get(j).get(VALUE_PERCENT);
			if(index>per){
				p = getLocation(j*angle, radius*per/100);
			}else{
				p = getLocation(j*angle, radius*index/100);
			}
			if(j==0){
				path.moveTo(p[0], p[1]);
			}else{
				path.lineTo(p[0], p[1]);
			}
		}
		path.close();
		canvas.drawPath(path, graphPaint);
	}
	/**
	 * 绘制雷达文字
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {
		String name = "";
		String per = "";
		float[] p;
		/*
		 * 给画笔设置字体大小
		 */
		textPaint.setTextSize(bigTaxtSize);
		smallTextPaint.setTextSize(smallTaxtSize);
		/*
		 * 计算文字高度偏移量,让她高度居中
		 */
		FontMetrics fm = textPaint.getFontMetrics();
		float bigTextY = -fm.descent + (fm.descent - fm.ascent) / 2;
		fm = smallTextPaint.getFontMetrics();
		float smallTextY = -fm.descent + (fm.descent - fm.ascent) / 2;
		/**
		 * 开始绘制
		 */
		for(int j = 0;j<pointNum;j++){
			name = (String) dataArray.get(j).get(VALUE_NAME);
			per = "("+dataArray.get(j).get(VALUE_VALUE).toString()+")";
			p = getLocation(j*angle, radius);
			/*
			 * 为了避免文字覆盖图像,所以根据位置不同计算偏移量
			 */
			if(j*angle<90){
				canvas.drawText(name, p[0]+(name.length()*bigTaxtSize/2), p[1]+bigTextY+bigTaxtSize/2, textPaint);
				canvas.drawText(per, p[0]+(name.length()*bigTaxtSize/2), p[1]+smallTextY+bigTaxtSize*3/2, smallTextPaint);
			}else if(j*angle<180){
				canvas.drawText(name, p[0]+(name.length()*bigTaxtSize/2), p[1]-bigTextY-smallTaxtSize, textPaint);
				canvas.drawText(per, p[0]+(name.length()*bigTaxtSize/2), p[1]-smallTextY, smallTextPaint);
			}else if(j*angle<270){
				canvas.drawText(name, p[0]-(name.length()*bigTaxtSize/2), p[1]-bigTextY-smallTaxtSize, textPaint);
				canvas.drawText(per, p[0]-(name.length()*bigTaxtSize/2), p[1]-smallTextY, smallTextPaint);
			}else{
				canvas.drawText(name, p[0]-(name.length()*bigTaxtSize/2), p[1]+bigTextY+bigTaxtSize/2, textPaint);
				canvas.drawText(per, p[0]-(name.length()*bigTaxtSize/2), p[1]+smallTextY+bigTaxtSize*3/2, smallTextPaint);
			}
		}
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
//		angle += 180;
		float x = (float) (width / 2 + (Math.sin(angle/180 * Math.PI) * radiu));
		float y = (float) (height / 2 + (Math.cos(angle/180 * Math.PI) * radiu));
		return new float[]{x,y};
	}
	/**
	 * 设置显示数据
	 * @param dataArray
	 * 要求百分比为整数,否则会抛出运行时异常(类型转换异常)
	 */
	public void setDataArray(ArrayList<HashMap<String, Object>> dataArray) {
		this.dataArray = dataArray;
		index = 0;
		invalidate();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			int x = (int) (event.getX()-width/2);
			int y = (int) (event.getY()-height/2);
			double z = Math.sqrt((x * x) + (y * y));
			int p = (int) (Math.asin(x / z)*(180/Math.PI));
			if (y < 0) {
				if (x < 0) {
					p = -p - 180;
				} else {
					p = 180 - p;
				}
			}
			if(p<0){
				p+=360;
			}
			int num = (int) (p/angle);
			if(num*angle+angle/2>p){
				if(lis!=null){
					lis.onRadarViewClick(num);
				}
			}else{
				if(lis!=null){
					lis.onRadarViewClick(num+1);
				}
			}
		}
		return true;
	}
	/**
	 * 点击监听
	 * @author xiao
	 *
	 */
	public interface RadarViewListener{
		public void  onRadarViewClick(int index);
	}
	public void setRadarViewListener(RadarViewListener lis) {
		this.lis = lis;
	}
}
