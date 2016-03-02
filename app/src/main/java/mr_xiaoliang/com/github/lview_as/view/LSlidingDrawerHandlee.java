package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 滑动抽屉的头
 * 
 * @author LiuJ 因为公司需要才写的，用途很有局限性 藏起来好了
 * 这是一个半圆行的抽屉拉手
 */
public class LSlidingDrawerHandlee extends View {
	/**
	 * 画笔
	 */
	private Paint paint;
	
	private int bgColor;

	public LSlidingDrawerHandlee(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LSlidingDrawerHandlee(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LSlidingDrawerHandlee(Context context) {
		this(context, null);
	}

}
