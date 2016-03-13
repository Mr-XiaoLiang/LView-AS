package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Color;

/**
 * Created by LiuJ on 2016/3/13.
 *
 */
public class LLineChartViewOption {

    /**
     * 最大值
     */
    public final float maxNum;
    /**
     * 外壳颜色
     */
    public final int shellColor;
    /**
     * 格子颜色
     */
    public final int gridColor;
    /**
     * 刻度颜色
     */
    public final int scaleColor;
    /**
     * 线条颜色
     */
    public final int lineColor;
    /**
     * 标签
     */
    public final String[] lable;
    /**
     * 刻度数量
     */
    public final int scaleSize;
    /**
     * 是否可以拉动
     */
    public final boolean canSlide;
    /**
     * 是否曲线
     */
    public final boolean isCurve;

    public LLineChartViewOption(Builder b){
        this.canSlide = b.canSlide;
        this.gridColor = b.gridColor;
        this.isCurve = b.isCurve;
        this.lable = b.lable;
        this.lineColor = b.lineColor;
        this.maxNum = b.maxNum;
        this.scaleColor = b.scaleColor;
        this.scaleSize = b.scaleSize;
        this.shellColor = b.shellColor;
    }

    public static class Builder{
        /**
         * 最大值
         */
        private float maxNum = 100;
        /**
         * 外壳颜色
         */
        private int shellColor = Color.WHITE;
        /**
         * 格子颜色
         */
        private int gridColor = Color.TRANSPARENT;
        /**
         * 刻度颜色
         */
        private int scaleColor = Color.WHITE;
        /**
         * 线条颜色
         */
        private int lineColor = Color.WHITE;
        /**
         * 标签
         */
        private String[] lable;
        /**
         * 刻度数量
         */
        private int scaleSize = 5;
        /**
         * 是否可以拉动
         */
        private boolean canSlide = false;
        /**
         * 是否曲线
         */
        private boolean isCurve = true;

        /**
         * 设置最大值
         * @param maxNum 最大值
         * @return 当前对象
         */
        public Builder setMaxNum(float maxNum) {
            this.maxNum = maxNum;
            return this;
        }

        /**
         * 设置坐标线颜色
         * @param shellColor 坐标线颜色
         * @return 当前对象
         */
        public Builder setShellColor(int shellColor) {
            this.shellColor = shellColor;
            return this;
        }

        /**
         * 设置格子颜色
         * @param gridColor 格子颜色
         * @return 当前对象
         */
        public Builder setGridColor(int gridColor) {
            this.gridColor = gridColor;
            return this;
        }

        /**
         * 设置刻度颜色
         * @param scaleColor 刻度颜色
         * @return 当前对象
         */
        public Builder setScaleColor(int scaleColor) {
            this.scaleColor = scaleColor;
            return this;
        }

        /**
         * 设置线条颜色
         * @param lineColor 线条颜色
         * @return 当前对象
         */
        public Builder setLineColor(int lineColor) {
            this.lineColor = lineColor;
            return this;
        }

        /**
         * 设置项目名
         * @param lable 项目名
         * @return 当前对象
         */
        public Builder setLable(String[] lable) {
            this.lable = lable;
            return this;
        }

        /**
         * 设置纵坐标数量
         * @param scaleSize 纵坐标数量
         * @return 当前对象
         */
        public Builder setScaleSize(int scaleSize) {
            this.scaleSize = scaleSize;
            return this;
        }

        /**
         * 是否可以滑动
         * @param canSlide 是否可以滑动
         * @return 当前对象
         */
        public Builder setCanSlide(boolean canSlide) {
            this.canSlide = canSlide;
            return this;
        }

        /**
         *
         * @param isCurve 是否使用曲线
         * @return 当前对象
         */
        public Builder setIsCurve(boolean isCurve) {
            this.isCurve = isCurve;
            return this;
        }
    }

}
