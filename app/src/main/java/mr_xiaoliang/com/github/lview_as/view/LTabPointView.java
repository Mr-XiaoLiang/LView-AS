package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LTabPointView extends View {
	/**
	 * 标题的权重
	 */
	private int[] tabWeight = new int[]{};
	/**
	 * 当前变换的点
	 */
	private int pointIndex = 0;
	/**
	 * 点半径
	 */
	private int pointRadius = 5;
	/**
	 * 小点颜色
	 */
	private int pointColor = Color.WHITE;
	/**
	 * 小点画笔
	 */
	private Paint pointPaint;
	/**
	 * 宽度
	 */
	private int width = 0;
	/**
	 * 高度
	 */
	private int height = 0;
	/**
	 * 移动进度
	 */
	private float pointPercent = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		width = getWidth();
		height = getHeight();
		pointPaint.setColor(pointColor);
		if(tabWeight.length<1||pointIndex<0||pointIndex>=tabWeight.length){
			return;
		}
		int tabAllWeight = getAllWeight();
		//本单元格的左侧偏移量
		int tabLeft = width/tabAllWeight*tabWeight[pointIndex]/2;
		pointRadius = height/2;
		//当单元格宽度小于最小单元格宽度时，圆点的直径等于最小单元格宽度
		for(int i = 0;i<tabWeight.length;i++){
			if(pointRadius>width/tabAllWeight*tabWeight[i]/2){
				pointRadius=width/tabAllWeight*tabWeight[i]/2;
			}
		}
		//计算左侧非本单元格的偏移量
		for(int i = 0;i<tabWeight.length;i++){
			if(i<pointIndex){
				tabLeft += width/tabAllWeight*tabWeight[i];
			}else{
				break;
			}
		}
		//当小点滑动到最顶端时的算法
		if(pointIndex<=tabWeight.length-2){
			tabLeft+=(width/tabAllWeight*(tabWeight[pointIndex]+tabWeight[pointIndex+1])/2)*(pointPercent);
		}else{
			tabLeft+=(width/tabAllWeight*(tabWeight[pointIndex])/2)*(pointPercent);
		}
		canvas.drawCircle(tabLeft, height/2, pointRadius, pointPaint);
	}

	private int getAllWeight(){
		int tabAllWeight = 0;
		for(int i = 0;i<tabWeight.length;i++){
			tabAllWeight+=tabWeight[i];
		}
		return tabAllWeight;
	}

	public LTabPointView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);
	}
	public LTabPointView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public LTabPointView(Context context) {
		this(context,null);
	}
	/**
	 * 状态的修改
	 * @param index
	 * @param percent
	 */
	public void onChange(int index,float percent){
		pointIndex = index;
		pointPercent = percent;
		invalidate();
	}

	public int[] getTabWeight() {
		return tabWeight;
	}
	/**
	 * 设置Tab的宽度占比及数量
	 * 用于tab宽度不等者
	 * @param tabWeight
	 */
	public void setTabSize(int[] tabWeight) {
		this.tabWeight = tabWeight;
		invalidate();
	}
	/**
	 * 设置Tab的数量
	 * 用于Tab宽度相等的
	 * @param tabSize
	 */
	public void setTabSize(int tabSize) {
		this.tabWeight = new int[tabSize];
		for(int i = 0;i<tabSize;i++){
			tabWeight[i] = 1;
		}
		invalidate();
	}

	public int getPointColor() {
		return pointColor;
	}
	/**
	 * 设置点的颜色
	 * @param pointColor
	 */
	public void setPointColor(int pointColor) {
		this.pointColor = pointColor;
		invalidate();
	}

}
