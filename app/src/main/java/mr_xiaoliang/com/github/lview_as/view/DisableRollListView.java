package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 不可滚动的listview
 * 用于嵌套到ScrollView
 * @author xiao
 *
 */
public class DisableRollListView extends ListView {

	public DisableRollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DisableRollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DisableRollListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
