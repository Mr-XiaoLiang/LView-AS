package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Bitmap;
import android.graphics.Shader;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/5/30.
 * 刮刮卡的参数类
 */
public class LScratchCardOption {

    //文本内容
    public final String text;
    //覆盖层文本内容
    public final String mulchText;
    //文本字体大小
    public final int textSize;
    //覆盖层文本字体大小
    public final int mulchTextSize;
    //透明背景
    public final boolean transparentBg;
    //文字颜色
    public final int[] textColor;
    //字体颜色渐变的角度
    public final float textColorAngle;
    //文字颜色渲染
    public final Shader textColorShader;
    //显示的值
    public final Bitmap valueImg;
    //图片剪切方式
    public final ImageView.ScaleType valueImgScaleType;
    //覆盖文字颜色
    public final int[] mulchTextColor;
    //覆盖文字颜色渐变的角度
    public final float mulchTextColorAngle;
    //覆盖文字颜色渲染
    public final Shader mulchTextColorShader;
    //background
    public final int[] backgroundColor;
    //背景颜色渐变的角度
    public final float backgroundColorAngle;
    //背景图片
    public final Bitmap backgroundImg;
    //图片剪切方式
    public final ImageView.ScaleType backgroundImgScaleType;
    //背景渲染
    public final Shader backgroundImgShader;
    //手指刮的宽度
    public final int touchWdth;
    //自动清除涂层的比例（当开启图层与View大小的比达到界限时，自动清理涂层）
    public final float autoClean;
    //圆角半径
    public final int roundRadius;
    //覆盖层图片
    public final Bitmap mulchImg;
    //覆盖层图片剪切方式
    public final ImageView.ScaleType mulchImgScaleType;
    //覆盖层颜色
    public final int[] mulchColor;
    //覆盖层颜色渐变的角度
    public final float mulchColorAngle;
    //抖动忽略大小
    public final int jitter;
    //是否启用自动清理
    public final boolean autoCleanEnable;


    public LScratchCardOption(Builder builder) {
        this.text = builder.text;
        this.mulchText = builder.mulchText;
        this.transparentBg = builder.transparentBg;
        this.textColor = builder.textColor;
        this.textColorAngle = builder.textColorAngle;
        this.textColorShader = builder.textColorShader;
        this.valueImg = builder.valueImg;
        this.valueImgScaleType = builder.valueImgScaleType;
        this.mulchTextColor = builder.mulchTextColor;
        this.mulchTextColorAngle = builder.mulchTextColorAngle;
        this.mulchTextColorShader = builder.mulchTextColorShader;
        this.backgroundColor = builder.backgroundColor;
        this.backgroundColorAngle = builder.backgroundColorAngle;
        this.backgroundImg = builder.backgroundImg;
        this.backgroundImgScaleType = builder.backgroundImgScaleType;
        this.backgroundImgShader = builder.backgroundImgShader;
        this.touchWdth = builder.touchWdth;
        this.autoClean = builder.autoClean;
        this.roundRadius = builder.roundRadius;
        this.mulchTextSize = builder.mulchTextSize;
        this.textSize = builder.textSize;
        this.mulchImg = builder.mulchImg;
        this.mulchImgScaleType = builder.mulchImgScaleType;
        this.mulchColor = builder.mulchColor;
        this.mulchColorAngle = builder.mulchColorAngle;
        this.jitter = builder.jitter;
        this.autoCleanEnable = builder.autoCleanEnable;
    }

    public static class Builder{
        //文本内容
        private String text = "";
        //覆盖层文本内容
        private String mulchText = "";
        //文本字体大小
        private int textSize = -1;
        //覆盖层文本字体大小
        private int mulchTextSize = -1;
        //透明背景
        private boolean transparentBg = false;
        //文字颜色
        private int[] textColor = null;
        //字体颜色渐变的角度
        private float textColorAngle = 0;
        //文字颜色渲染
        private Shader textColorShader = null;
        //显示的值
        private Bitmap valueImg = null;
        //图片剪切方式
        private ImageView.ScaleType valueImgScaleType = ImageView.ScaleType.CENTER_CROP;
        //覆盖文字颜色
        private int[] mulchTextColor = null;
        //覆盖文字颜色渐变的角度
        private float mulchTextColorAngle = 0;
        //覆盖文字颜色渲染
        private Shader mulchTextColorShader = null;
        //background
        private int[] backgroundColor = null;
        //背景颜色渐变的角度
        private float backgroundColorAngle = 0;
        //覆盖层颜色
        private int[] mulchColor = null;
        //覆盖层颜色渐变的角度
        private float mulchColorAngle = 0;
        //覆盖层图片
        private Bitmap mulchImg = null;
        //覆盖层图片剪切方式
        private ImageView.ScaleType mulchImgScaleType = ImageView.ScaleType.CENTER_CROP;
        //背景图片
        private Bitmap backgroundImg = null;
        //图片剪切方式
        private ImageView.ScaleType backgroundImgScaleType = ImageView.ScaleType.CENTER_CROP;
        //背景渲染
        private Shader backgroundImgShader = null;
        //手指刮的宽度
        private int touchWdth = 30;
        //自动清除涂层的比例（当开启图层与View大小的比达到界限时，自动清理涂层）
        private float autoClean = 1;
        //是否启用自动清理
        private boolean autoCleanEnable = false;
        //圆角半径
        private int roundRadius = 0;
        //抖动忽略大小
        private int jitter = 3;


        /**
         * 设置自动清理界限
         * @param autoClean
         * @return
         */
        public Builder setAutoClean(float autoClean) {
            this.autoClean = autoClean;
            return this;
        }

        /**
         * 圆角半径
         * @param roundRadius
         * @return
         */
        public Builder setRoundRadius(int roundRadius) {
            this.roundRadius = roundRadius;
            return this;
        }

        /**
         * 内容文本（Z轴高于内容图片）
         * @param text
         * @return
         */
        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * 是否透明化背景（打开后将影响性能）
         * @param transparentBg
         * @return
         */
        public Builder setTransparentBg(boolean transparentBg) {
            this.transparentBg = transparentBg;
            return this;
        }

        /**
         * 内容文本颜色，优先级低
         * @param textColor
         * @return
         */
        public Builder setTextColor(int... textColor) {
            this.textColor = textColor;
            return this;
        }

        /**
         * 设置文本渐变颜色角度(起点为数学坐标系X轴，逆时针旋转)
         * @param textColorAngle
         * @return
         */
        public Builder setTextColorAngle(float textColorAngle) {
            this.textColorAngle = textColorAngle;
            return this;
        }

        /**
         * 设置内容文本渲染，优先级高
         * @param textColorShader
         * @return
         */
        public Builder setTextColorShader(Shader textColorShader) {
            this.textColorShader = textColorShader;
            return this;
        }

        /**
         * 设置内容图片（Z轴低于文本内容）
         * @param valueImg
         * @return
         */
        public Builder setValueImg(Bitmap valueImg) {
            this.valueImg = valueImg;
            return this;
        }

        /**
         * 设置内容图片缩放方式(有效值只有：CENTER_CROP，CENTER_INSIDE，FIT_XY,CENTER)
         * @param valueImgScaleType
         * @return
         */
        public Builder setValueImgScaleType(ImageView.ScaleType valueImgScaleType) {
            this.valueImgScaleType = valueImgScaleType;
            return this;
        }
        /**
         * 设置覆盖层文字（Z轴高于覆盖层图片）
         * @param mulchText
         * @return
         */
        public Builder setMulchText(String mulchText) {
            this.mulchText = mulchText;
            return this;
        }

        /**
         * 设置覆盖层文字显示颜色（优先级低）
         * @param mulchTextColor
         * @return
         */
        public Builder setMulchTextColor(int... mulchTextColor) {
            this.mulchTextColor = mulchTextColor;
            return this;
        }

        /**
         * 设置覆盖层文字颜色渲染角度(起点为数学坐标系X轴，逆时针旋转)
         * @param mulchTextColorAngle
         * @return
         */
        public Builder setMulchTextColorAngle(float mulchTextColorAngle) {
            this.mulchTextColorAngle = mulchTextColorAngle;
            return this;
        }

        /**
         * 设置覆盖层文字渲染（优先级高）
         * @param mulchTextColorShader
         * @return
         */
        public Builder setMulchTextColorShader(Shader mulchTextColorShader) {
            this.mulchTextColorShader = mulchTextColorShader;
            return this;
        }

        /**
         * 设置背景颜色
         * @param backgroundColor
         * @return
         */
        public Builder setBackgroundColor(int... backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        /**
         * 设置背景颜色角度(起点为数学坐标系X轴，逆时针旋转)
         * @param backgroundColorAngle
         * @return
         */
        public Builder setBackgroundColorAngle(float backgroundColorAngle) {
            this.backgroundColorAngle = backgroundColorAngle;
            return this;
        }

        /**
         * 设置背景图片
         * @param backgroundImg
         * @return
         */
        public Builder setBackgroundImg(Bitmap backgroundImg) {
            this.backgroundImg = backgroundImg;
            return this;
        }

        /**
         * 设置背景图片剪切方式(有效值只有：CENTER_CROP，CENTER_INSIDE，FIT_XY,CENTER)
         * @param backgroundImgScaleType
         * @return
         */
        public Builder setBackgroundImgScaleType(ImageView.ScaleType backgroundImgScaleType) {
            this.backgroundImgScaleType = backgroundImgScaleType;
            return this;
        }

        /**
         * 设置背景图渲染
         * @param backgroundImgShader
         * @return
         */
        public Builder setBackgroundImgShader(Shader backgroundImgShader) {
            this.backgroundImgShader = backgroundImgShader;
            return this;
        }

        /**
         * 设置手指宽度
         * @param touchWdth
         * @return
         */
        public Builder setTouchWdth(int touchWdth) {
            this.touchWdth = touchWdth;
            return this;
        }

        /**
         * 设置内容字体大小（PX）
         * @param textSize
         * @return
         */
        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置覆盖层文字大小（px）
         * @param mulchTextSize
         * @return
         */
        public Builder setMulchTextSize(int mulchTextSize) {
            this.mulchTextSize = mulchTextSize;
            return this;
        }

        /**
         * 设置遮盖物的图片
         * @param mulchImg
         */
        public Builder setMulchImg(Bitmap mulchImg) {
            this.mulchImg = mulchImg;
            return this;
        }
        /**
         * 设置遮盖图片剪切方式(有效值只有：CENTER_CROP，CENTER_INSIDE，FIT_XY,CENTER)
         * @param mulchImgScaleType
         * @return
         */
        public Builder setMulchImgScaleType(ImageView.ScaleType mulchImgScaleType) {
            this.mulchImgScaleType = mulchImgScaleType;
            return this;
        }

        /**
         * 设置覆盖层的颜色
         * @param mulchColor
         * @return
         */
        public Builder setMulchColor(int... mulchColor) {
            this.mulchColor = mulchColor;
            return this;
        }

        /**
         * 设置覆盖层的颜色角度
         * @param mulchColorAngle
         * @return
         */
        public Builder setMulchColorAngle(float mulchColorAngle) {
            this.mulchColorAngle = mulchColorAngle;
            return this;
        }

        /**
         * 设置抖动忽略大小（范围内的手指移动将被忽略）
         * @param jitter
         * @return
         */
        public Builder setJitter(int jitter) {
            this.jitter = jitter;
            return this;
        }

        /**
         * 是否启用自动清理
         * @param autoCleanEnable
         * @return
         */
        public Builder setAutoCleanEnable(boolean autoCleanEnable) {
            this.autoCleanEnable = autoCleanEnable;
            return this;
        }
    }

}
