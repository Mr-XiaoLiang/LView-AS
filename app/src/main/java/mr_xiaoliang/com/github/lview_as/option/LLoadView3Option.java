package mr_xiaoliang.com.github.lview_as.option;


import android.graphics.Typeface;

/**
 * Created by LiuJ on 2016/7/20.
 * 文字跳动的加载动画的参数
 */

public class LLoadView3Option {
    /**
     * 内容
     */
    public final String values;
    /**
     * 颜色
     */
    public final int[] colors;
    /**
     * 字体
     */
    public final Typeface[] typefaces;
    /**
     * 速度
     */
    public final int velocity;
    /**
     * 字体大小
     */
    public final int textSize;
    /**
     * 循环模式
     */
    public final int cycleType;
    /**
     * 高度比例
     */
    public final float heightProportion;
    /**
     * 显示类型
     */
    public final int showType;
    /**
     * 旋转圈数
     */
    public final int rotationNum;
    /**
     * 影子颜色
     */
    public final int[] shadowColors;
    /**
     * 字体比例
     */
    public final float[] textFloat;

    public LLoadView3Option(Builder builder) {
        this.colors = builder.colors;
        this.values = builder.values;
        this.typefaces = builder.typefaces;
        this.velocity = builder.velocity;
        this.textSize = builder.textSize;
        this.cycleType = builder.cycleType;
        this.heightProportion = builder.heightProportion;
        this.showType = builder.showType;
        this.rotationNum = builder.rotationNum;
        this.shadowColors = builder.shadowColors;
        this.textFloat = builder.textFloat;
    }

    public static class Builder {
        private String values = "";
        private int[] colors = null;
        private Typeface[] typefaces = null;
        private int velocity = 50;
        private int textSize = -1;
        private int cycleType = CYCLE_TYPE_FIFO;
        private float heightProportion = 0.5f;
        private int showType = SHOW_TYPE_ALL;
        private int rotationNum = 1;
        private int[] shadowColors = null;
        private float[] textFloat = null;

        /**
         * 设置字体颜色
         * @param colors
         * @return
         */
        public Builder setColors(int... colors) {
            this.colors = colors;
            return this;
        }

        /**
         * 设置循环方式
         * @param cycleType
         * @return
         */
        public Builder setCycleType(int cycleType) {
            this.cycleType = cycleType;
            return this;
        }

        /**
         * 设置字体与View高度的比例
         * @param heightProportion
         * @return
         */
        public Builder setHeightProportion(float heightProportion) {
            this.heightProportion = heightProportion;
            return this;
        }

        /**
         * 设置显示类型
         * @param showType
         * @return
         */
        public Builder setShowType(int showType) {
            this.showType = showType;
            return this;
        }

        /**
         * 设置文字大小
         * @param textSize
         * @return
         */
        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置自定义字体
         * @param typefaces
         * @return
         */
        public Builder setTypefaces(Typeface... typefaces) {
            this.typefaces = typefaces;
            return this;
        }

        /**
         * 设置文字内容
         * @param values
         * @return
         */
        public Builder setValues(String values) {
            this.values = values;
            return this;
        }

        /**
         * 设置速度
         * @param velocity
         * @return
         */
        public Builder setVelocity(int velocity) {
            this.velocity = velocity;
            return this;
        }

        /**
         * 旋转圈数
         * @param rotationNum
         * @return
         */
        public Builder setRotationNum(int rotationNum) {
            this.rotationNum = rotationNum;
            return this;
        }

        /**
         * 影子颜色
         * @param shadowColors
         * @return
         */
        public Builder setShadowColors(int... shadowColors) {
            this.shadowColors = shadowColors;
            return this;
        }

        /**
         * 设置字体间的比例
         * @param textFloat
         * @return
         */
        public Builder setTextFloat(float... textFloat) {
            this.textFloat = textFloat;
            return this;
        }
    }

    /**
     * 单向循环
     */
    public static final int CYCLE_TYPE_FIFO = 0;
    /**
     * 往复循环
     */
    public static final int CYCLE_TYPE_FILO = 1;
    /**
     * 显示模式：一次一个
     */
    public static final int SHOW_TYPE_ONCE = 0;
    /**
     * 显示模式：全部显示
     */
    public static final int SHOW_TYPE_ALL = 1;

}
