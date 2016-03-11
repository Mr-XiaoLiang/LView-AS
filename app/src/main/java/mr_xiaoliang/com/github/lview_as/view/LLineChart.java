package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by LiuJ on 2016/3/9.
 * 折线图
 * |
 * |         /\
 * |    /\  /  \
 * |   /  \/    \
 * |  /          \
 * |_/_____________
 *
 */
public class LLineChart extends View {
    /**
     * 最大值
     */
    private float maxNum;
    /**
     * 外壳颜色
     */
    private int shellColor = Color.WHITE;
    /**
     * 刻度颜色
     */
    private int scaleColor = Color.WHITE;
    /**
     * 数据
     */
    private ArrayList<LLineChartBean> beans;
    /**
     * 标签
     */
    private String[] lable;
    /**
     * 刻度数量
     */
    private int scaleSize;
    /**
     * 是否可以拉动
     */
    private boolean canSlide = true;
    /**
     * 横坐标偏移量，用来滑动
     */
    private int offset;

    public LLineChart(Context context) {
        this(context, null);
    }

    public LLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public class LLineChartBean{
        float[] lable;
        int color;
    }
}
