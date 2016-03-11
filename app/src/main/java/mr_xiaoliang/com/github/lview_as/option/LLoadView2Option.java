package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Color;

/**
 * Created by LiuJ on 2016/3/11.
 * 水滴加载动画的参数类
 */
public class LLoadView2Option {

    private final int waterColor;
    private final int borderColor;
    private final float borderWidth;
    private final float waveRange;
    private final float progress;
    private final int dropNum;
    private final float maxDropSize;
    private final boolean fitXY;
    private final float waveStep;
    private final float dropStep;

    public LLoadView2Option(Builder b) {
        this.waterColor = b.waterColor;
        this.borderColor = b.borderColor;
        this.borderWidth = b.borderWidth;
        this.waveRange = b.waveRange;
        this.progress = b.progress;
        this.maxDropSize = b.maxDropSize;
        this.dropNum = b.dropNum;
        this.fitXY = b.fitXY;
        this.waveStep = b.waveStep;
        this.dropStep = b.dropStep;
    }

    public int getWaterColor() {
        return waterColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public float getWaveRange() {
        return waveRange;
    }

    public float getProgress() {
        return progress;
    }

    public int getDropNum() {
        return dropNum;
    }

    public float getMaxDropSize() {
        return maxDropSize;
    }

    public boolean isFitXY() {
        return fitXY;
    }

    public float getWaveStep() {
        return waveStep;
    }

    public float getDropStep() {
        return dropStep;
    }

    public static class Builder{
        private int waterColor = Color.parseColor("#fa99ba");
        private int borderColor = Color.parseColor("#cccccc");
        private float borderWidth = 0.02F;
        private float waveRange = 0.1F;
        private float progress = 0.4F;
        private int dropNum = 7;
        private float maxDropSize = 0.05F;
        private boolean fitXY = false;
        private float waveStep = 0.01F;
        private float dropStep = 0.01F;
        /**
         * 设置水的颜色
         * @param waterColor 水的颜色
         * @return 当前对象
         */
        public Builder setWaterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        /**
         * 设置边框的颜色
         * @param borderColor 边框的颜色
         * @return 当前对象
         */
        public Builder setBorderColor(int borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        /**
         * 设置边框的宽度
         * @param borderWidth 边框的宽度
         * @return 当前对象
         */
        public Builder setBorderWidth(float borderWidth) {
            this.borderWidth = borderWidth;
            return this;
        }

        /**
         * 设置波浪幅度
         * @param waveRange 波浪幅度
         * @return 当前对象
         */
        public Builder setWaveRange(float waveRange) {
            this.waveRange = waveRange;
            return this;
        }

        /**
         * 设置进度值
         * @param progress 进度值
         * @return 当前对象
         */
        public Builder setProgress(float progress) {
            this.progress = progress;
            return this;
        }

        /**
         * 设置水滴数量
         * @param dropNum 水滴数量
         * @return 当前对象
         */
        public Builder setDropNum(int dropNum) {
            this.dropNum = dropNum;
            return this;
        }

        /**
         * 设置水滴最大尺寸
         * @param maxDropSize 水滴最大尺寸
         * @return 当前对象
         */
        public Builder setMaxDropSize(float maxDropSize) {
            this.maxDropSize = maxDropSize;
            return this;
        }

        /**
         * 是否贴边
         * true：view不再是正圆，而是根据view尺寸变化
         * @param fitXY 是否贴边
         * @return 当前对象
         */
        public Builder setFitXY(boolean fitXY) {
            this.fitXY = fitXY;
            return this;
        }

        /**
         * 波浪步长
         * @param waveStep 波浪步长
         * @return 当前对象
         */
        public Builder setWaveStep(float waveStep) {
            this.waveStep = waveStep;
            return this;
        }

        /**
         * 水滴步长
         * @param dropStep 水滴步长
         * @return 当前对象
         */
        public Builder setDropStep(float dropStep) {
            this.dropStep = dropStep;
            return this;
        }
    }
}
