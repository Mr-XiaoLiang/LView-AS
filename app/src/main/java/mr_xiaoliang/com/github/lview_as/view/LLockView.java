package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by LiuJ on 2016/6/23.
 * 一个密码锁的自定义view
 * 支持类型：
 * 1, 连线锁，3*3，4*4，5*5，6*6，或x*x
 * 2, 点按锁，3*3，4*4，5*5，6*6，或x*x
 * 3, 混合锁，3*3，4*4，5*5，6*6，或x*x
 */

public class LLockView extends ImageView {
    public LLockView(Context context) {
        super(context);
    }

    public LLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
