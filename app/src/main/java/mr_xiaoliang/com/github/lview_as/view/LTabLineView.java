package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.option.LTabLineViewOption;

/**
 * tab的线性下标
 * 
 * @author LiuJ
 *
 */
public class LTabLineView extends View {
	/**
	 * 标题的权重
	 */
	private int[] tabWeight = new int[] {};
	/**
	 * 当前变换的点
	 */
	private int lineIndex = 0;
	/**
	 * 小点颜色
	 */
	private int lineColor = Color.WHITE;
	/**
	 * 小点画笔
	 */
	private Paint linePaint;
	/**
	 * 是否让线使用圆形头部
	 */
	private boolean isRoundHead;
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
	/**
	 * 总权重
	 */
	private int tabAllWeight = 0;
	/**
	 * tab文字的大小
	 */
	private int textSize;
	/**
	 * 每个Tab的文字长度
	 */
	private int[] tabTextLength;
	/**
	 * 点半径
	 */
	private int pointRadius = 5;

	public LTabLineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
	}

	public LTabLineView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LTabLineView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (tabWeight.length < 1) {
			return;
		}
		int[] lineLocation = getLineLocation();
		if(lineLocation==null||lineLocation.length<2)
			return;
		RectF rectF = new RectF(lineLocation[0], height/2-pointRadius, lineLocation[1], height/2+pointRadius);
//		RectF rectF = new RectF(lineLocation[0], 0, lineLocation[1], height);
		if(isRoundHead){
			canvas.drawRoundRect(rectF, pointRadius, pointRadius, linePaint);
		}else{
			canvas.drawRect(rectF, linePaint);
		}
	}

	/**
	 * 计算线的X坐标
	 * @return
	 */
	private int[] getLineLocation() {
		try {
			width = getWidth();
			height = getHeight();
			linePaint.setColor(lineColor);
			if (lineIndex >= tabWeight.length)
				lineIndex = tabWeight.length - 1;
			if (lineIndex < 0)
				lineIndex = 0;
			// 本单元格的左侧偏移量
			int tabLeft = width / tabAllWeight * tabWeight[lineIndex] / 2;
			pointRadius = height / 2;
			// 当单元格宽度小于高度时，圆点的直径等于单元格宽度
			for (int i = 0; i < tabWeight.length; i++) {
				if (pointRadius > width / tabAllWeight * tabWeight[i] / 2) {
					pointRadius = width / tabAllWeight * tabWeight[i] / 2;
				}
			}
			// 计算左侧非本单元格的偏移量
			for (int i = 0; i < tabWeight.length; i++) {
				if (i < lineIndex) {
					tabLeft += width / tabAllWeight * tabWeight[i];
				} else {
					break;
				}
			}
			// 当小点滑动到最顶端时的算法
			if (lineIndex <= tabWeight.length - 2) {
				tabLeft += (width / tabAllWeight * (tabWeight[lineIndex] + tabWeight[lineIndex + 1]) / 2)
						* (pointPercent);
			} else {
				tabLeft += (width / tabAllWeight * (tabWeight[lineIndex]) / 2) * (pointPercent);
			}
			int left = 0;
			int right = 0;
			int offset = 0;
			//根据当前进度，来决定当前的标签的长度的偏移量
			if(lineIndex+1<tabTextLength.length){
				offset = (int) ((tabTextLength[lineIndex+1]*textSize-tabTextLength[lineIndex]*textSize)/2*pointPercent+tabTextLength[lineIndex]*textSize/2);
			}else{
				offset = (int) ((-tabTextLength[lineIndex]*textSize)/2*pointPercent+tabTextLength[lineIndex]*textSize/2);
			}
			left = tabLeft-offset;
			right = tabLeft+offset;
			return new int[]{left,right};
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取总权重
	 * 
	 * @return
	 */
	private int getAllWeight() {
		for (int i = 0; i < tabWeight.length; i++) {
			tabAllWeight += tabWeight[i];
		}
		return tabAllWeight;
	}

	/**
	 * 初始化
	 * 
	 * @param option
	 */
	public void setOption(LTabLineViewOption option) {
		this.lineColor = option.getLineColor();
		this.tabWeight = option.getTabWeight();
		this.isRoundHead = option.isRoundHead();
		this.textSize = option.getTextSize();
		this.tabTextLength = option.getTabTextLength();
		getAllWeight();
		invalidate();
	}

	/**
	 * 状态的修改 本方法放在viewPager的onPageScrolled里 传入第一和第二参数即可
	 * 
	 * @param index
	 * @param percent
	 */
	public void onChange(int index, float percent) {
		lineIndex = index;
		pointPercent = percent;
		invalidate();
	}

}
