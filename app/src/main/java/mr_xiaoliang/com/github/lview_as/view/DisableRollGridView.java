package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class DisableRollGridView extends GridView {
	public DisableRollGridView(Context context) {
		super(context);
	}

	public DisableRollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DisableRollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE/2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}