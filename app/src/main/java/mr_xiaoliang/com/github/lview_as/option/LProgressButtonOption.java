package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Color;

/**
 *
 * @author LiuJ 这是进度条按钮的参数设置类
 *
 */
public class LProgressButtonOption {
	// 按钮圆角的尺寸
	private final int btnRadius;
	// 按钮开始背景色
	private final int btnStartBgColor;
	// 按钮开始背景图片
	private final int btnStartBgImage;
	// 按钮开始文字
	private final String btnStartText;
	// 按钮点击前字体颜色
	private final int btnStartTextColor;
	// 按钮开始的图片
	private final int btnStartIcon;
	// 按钮结束背景色
	private final int btnEndBgColor;
	// 按钮结束背景图片
	private final int btnEndBgImage;
	// 按钮结束文字
	private final String btnEndText;
	// 按钮结束字体颜色
	private final int btnEndTextColor;
	// 按钮结束的图片
	private final int btnEndIcon;
	// 按钮异常背景色
	private final int btnErrorBgColor;
	// 按钮异常背景图片
	private final int btnErrorBgImage;
	// 按钮结束文字
	private final String btnErrorText;
	// 按钮结束字体颜色
	private final int btnErrorTextColor;
	// 按钮结束的图片
	private final int btnErrorIcon;
	// 进度条颜色
	private final int btnProgressColor;
	// 进度条宽度(与按钮高度的比例)
	private final float btnProgressWidth;
	// 进度条背景颜色
	private final int btnProgressBgColor;
	// 是否显示百分比
	private final boolean isShowPercent;
	// 进度条中心背景是否透明
	private final boolean isProgressCenterLucency;
	// 进度条中心背景的颜色
	private final int btnProgressCenterColor;
	// 进度条数值的颜色
	private final int btnProgressPercentColor;
	// 进度条中心背景的图片
	private final int btnProgressCenterImage;
	//按钮图标的尺寸
	private final float btnIconSize;
	//图标按钮的比例类型
	private final int btnIconSizeType;
	//按钮图标的圆角大小
	private final int btnIconRadius;
	public LProgressButtonOption(Builder builder) {
		super();
		this.btnEndBgColor = builder.btnEndBgColor;
		this.btnEndBgImage = builder.btnEndBgImage;
		this.btnEndIcon = builder.btnEndIcon;
		this.btnEndText = builder.btnEndText;
		this.btnEndTextColor = builder.btnEndTextColor;
		this.btnErrorBgColor = builder.btnErrorBgColor;
		this.btnErrorBgImage = builder.btnErrorBgImage;
		this.btnErrorIcon = builder.btnErrorIcon;
		this.btnErrorText = builder.btnErrorText;
		this.btnErrorTextColor = builder.btnErrorTextColor;
		this.btnProgressBgColor = builder.btnProgressBgColor;
		this.btnProgressCenterColor = builder.btnProgressCenterColor;
		this.btnProgressColor = builder.btnProgressColor;
		this.btnProgressPercentColor = builder.btnProgressPercentColor;
		this.btnProgressWidth = builder.btnProgressWidth;
		this.btnRadius = builder.btnRadius;
		this.btnStartBgColor = builder.btnStartBgColor;
		this.btnStartBgImage = builder.btnStartBgImage;
		this.btnStartIcon = builder.btnStartIcon;
		this.btnStartText = builder.btnStartText;
		this.btnStartTextColor = builder.btnStartTextColor;
		this.isProgressCenterLucency = builder.isProgressCenterLucency;
		this.isShowPercent = builder.isShowPercent;
		this.btnProgressCenterImage = builder.btnProgressCenterImage;
		this.btnIconSize = builder.btnIconSize;
		this.btnIconSizeType = builder.btnIconSizeType;
		this.btnIconRadius = builder.btnIconRadius;

	}



	public int getBtnRadius() {
		return btnRadius;
	}



	public int getBtnStartBgColor() {
		return btnStartBgColor;
	}



	public int getBtnStartBgImage() {
		return btnStartBgImage;
	}



	public String getBtnStartText() {
		return btnStartText;
	}



	public int getBtnStartTextColor() {
		return btnStartTextColor;
	}



	public int getBtnStartIcon() {
		return btnStartIcon;
	}



	public int getBtnEndBgColor() {
		return btnEndBgColor;
	}



	public int getBtnEndBgImage() {
		return btnEndBgImage;
	}



	public String getBtnEndText() {
		return btnEndText;
	}



	public int getBtnEndTextColor() {
		return btnEndTextColor;
	}



	public int getBtnEndIcon() {
		return btnEndIcon;
	}



	public int getBtnErrorBgColor() {
		return btnErrorBgColor;
	}



	public int getBtnErrorBgImage() {
		return btnErrorBgImage;
	}



	public String getBtnErrorText() {
		return btnErrorText;
	}



	public int getBtnErrorTextColor() {
		return btnErrorTextColor;
	}



	public int getBtnErrorIcon() {
		return btnErrorIcon;
	}



	public int getBtnProgressColor() {
		return btnProgressColor;
	}



	public float getBtnProgressWidth() {
		return btnProgressWidth;
	}



	public int getBtnProgressBgColor() {
		return btnProgressBgColor;
	}



	public boolean isShowPercent() {
		return isShowPercent;
	}



	public boolean isProgressCenterLucency() {
		return isProgressCenterLucency;
	}



	public int getBtnProgressCenterColor() {
		return btnProgressCenterColor;
	}



	public int getBtnProgressPercentColor() {
		return btnProgressPercentColor;
	}



	public int getBtnProgressCenterImage() {
		return btnProgressCenterImage;
	}



	public float getBtnIconSize() {
		return btnIconSize;
	}


	public int getBtnIconSizeType() {
		return btnIconSizeType;
	}

	public int getBtnIconRadius() {
		return btnIconRadius;
	}



	public static class Builder {
		/**
		 * 按钮圆角半径的备选项 上下半圆的按钮样式
		 */
		public static final int btnRadius_VERTICAL = -3;
		/**
		 * 按钮圆角半径的备选项 左右半圆的样式
		 */
		public static final int btnRadius_HORIZONTAL = -2;
		/**
		 * 按钮圆角半径的备选项 方形
		 */
		public static final int btnRadius_SQUARE = -1;
		/**
		 * 按钮初始状态的默认颜色
		 */
		public static final int btnColor_DEFAULT = Color.rgb(0, 153, 204);
		/**
		 * 按钮默认的成功颜色
		 */
		public static final int btnColor_SUCCESS = Color.rgb(153, 204, 0);
		/**
		 * 按钮默认的异常颜色
		 */
		public static final int btnColor_ERROR = Color.rgb(255, 68, 68);
		/**
		 * 按钮默认的图片
		 */
		public static final int btnImage_NONE = -1;
		/**
		 * 按钮图标的尺寸变化对象
		 * 相对于按钮宽度
		 */
		public static final int btnIconSizeType_WIDTH = 0;
		/**
		 * 按钮图标的尺寸变化对象
		 * 相对于按钮高度
		 */
		public static final int btnIconSizeType_HEIGHT = 1;
		/**
		 * 按钮图标的尺寸变化对象
		 * 自动,自动选择较小的那个
		 */
		public static final int btnIconSizeType_AUTO = -1;

		// 按钮圆角的尺寸
		private int btnRadius = btnRadius_HORIZONTAL;
		// 按钮开始背景色
		private int btnStartBgColor = btnColor_DEFAULT;
		// 按钮开始背景图片
		private int btnStartBgImage = btnImage_NONE;
		// 按钮开始文字
		private String btnStartText = "";
		// 按钮点击前字体颜色
		private int btnStartTextColor = Color.WHITE;
		// 按钮开始的图片
		private int btnStartIcon = btnImage_NONE;
		// 按钮结束背景色
		private int btnEndBgColor = btnColor_SUCCESS;
		// 按钮结束背景图片
		private int btnEndBgImage = btnImage_NONE;
		// 按钮结束文字
		private String btnEndText = "";
		// 按钮结束字体颜色
		private int btnEndTextColor = Color.WHITE;
		// 按钮结束的图片
		private int btnEndIcon = btnImage_NONE;
		// 按钮异常背景色
		private int btnErrorBgColor = btnColor_ERROR;
		// 按钮异常背景图片
		private int btnErrorBgImage = btnImage_NONE;
		// 按钮结束文字
		private String btnErrorText = "";
		// 按钮结束字体颜色
		private int btnErrorTextColor = Color.WHITE;
		// 按钮结束的图片
		private int btnErrorIcon = btnImage_NONE;
		// 进度条颜色
		private int btnProgressColor = btnColor_DEFAULT;
		// 进度条宽度(与按钮高度的比例)
		private float btnProgressWidth = 0.05f;
		// 进度条背景颜色
		private int btnProgressBgColor = Color.parseColor("#e7e7e7");
		// 是否显示百分比
		private boolean isShowPercent = false;
		// 进度条中心背景是否透明
		private boolean isProgressCenterLucency = true;
		// 进度条中心背景的颜色
		private int btnProgressCenterColor = Color.WHITE;
		// 进度条中心背景的图片
		private int btnProgressCenterImage = btnImage_NONE;
		// 进度条数值的颜色
		private int btnProgressPercentColor = Color.GRAY;
		//按钮图标的尺寸
		private float btnIconSize = 0.4f;
		//图标按钮的比例类型
		private int btnIconSizeType = btnIconSizeType_AUTO;
		//按钮图标的圆角大小
		private int btnIconRadius = 0;
		/**
		 * 按钮圆角的尺寸
		 *
		 * @param btnRadius
		 *            圆角的px数
		 * @return
		 */
		public Builder setBtnRadius(int btnRadius) {
			this.btnRadius = btnRadius;
			return this;
		}

		/**
		 * 设置按钮开始时的背景色
		 *
		 * @param btnStartBgColor
		 * @return
		 */
		public Builder setBtnStartBgColor(int btnStartBgColor) {
			this.btnStartBgColor = btnStartBgColor;
			return this;
		}

		/**
		 * 设置按钮开始时的背景图片
		 *
		 * @param btnStartBgImage
		 *            图片的id
		 * @return
		 */
		public Builder setBtnStartBgImage(int btnStartBgImage) {
			this.btnStartBgImage = btnStartBgImage;
			return this;
		}

		/**
		 * 设置按钮开始时的文字显示
		 *
		 * @param btnStartText
		 * @return
		 */
		public Builder setBtnStartText(String btnStartText) {
			this.btnStartText = btnStartText;
			return this;
		}

		/**
		 * 设置按钮开始时显示文字的颜色
		 *
		 * @param btnStartTextColor
		 * @return
		 */
		public Builder setBtnStartTextColor(int btnStartTextColor) {
			this.btnStartTextColor = btnStartTextColor;
			return this;
		}

		/**
		 * 设置按钮开始时的图标显示
		 *
		 * @param btnStartIcon
		 * @return
		 */
		public Builder setBtnStartIcon(int btnStartIcon) {
			this.btnStartIcon = btnStartIcon;
			return this;
		}

		/**
		 * 设置按钮结束时的背景颜色
		 *
		 * @param btnEndBgColor
		 * @return
		 */
		public Builder setBtnEndBgColor(int btnEndBgColor) {
			this.btnEndBgColor = btnEndBgColor;
			return this;
		}

		/**
		 * 设置按钮结束时的背景图片
		 *
		 * @param btnEndBgImage
		 * @return
		 */
		public Builder setBtnEndBgImage(int btnEndBgImage) {
			this.btnEndBgImage = btnEndBgImage;
			return this;
		}

		/**
		 * 设置按钮结束时的文字显示
		 *
		 * @param btnEndText
		 * @return
		 */
		public Builder setBtnEndText(String btnEndText) {
			this.btnEndText = btnEndText;
			return this;
		}

		/**
		 * 设置按钮结束时的字体颜色
		 *
		 * @param btnEndTextColor
		 * @return
		 */
		public Builder setBtnEndTextColor(int btnEndTextColor) {
			this.btnEndTextColor = btnEndTextColor;
			return this;
		}

		/**
		 * 设置按钮结束时的图标
		 *
		 * @param btnEndIcon
		 * @return
		 */
		public Builder setBtnEndIcon(int btnEndIcon) {
			this.btnEndIcon = btnEndIcon;
			return this;
		}

		/**
		 * 设置按钮异常结束时的背景颜色
		 *
		 * @param btnErrorBgColor
		 * @return
		 */
		public Builder setBtnErrorBgColor(int btnErrorBgColor) {
			this.btnErrorBgColor = btnErrorBgColor;
			return this;
		}

		/**
		 * 设置按钮异常结束时的背景图片
		 *
		 * @param btnErrorBgImage
		 * @return
		 */
		public Builder setBtnErrorBgImage(int btnErrorBgImage) {
			this.btnErrorBgImage = btnErrorBgImage;
			return this;
		}

		/**
		 * 设置按钮异常时的文字
		 *
		 * @param btnErrorText
		 * @return
		 */
		public Builder setBtnErrorText(String btnErrorText) {
			this.btnErrorText = btnErrorText;
			return this;
		}

		/**
		 * 设置按钮异常时的字体的颜色
		 *
		 * @param btnErrorTextColor
		 * @return
		 */
		public Builder setBtnErrorTextColor(int btnErrorTextColor) {
			this.btnErrorTextColor = btnErrorTextColor;
			return this;
		}

		/**
		 * 设置按钮异常时的图标
		 *
		 * @param btnErrorIcon
		 * @return
		 */
		public Builder setBtnErrorIcon(int btnErrorIcon) {
			this.btnErrorIcon = btnErrorIcon;
			return this;
		}

		/**
		 * 设置按钮进度条颜色
		 *
		 * @param btnProgressColor
		 * @return
		 */
		public Builder setBtnProgressColor(int btnProgressColor) {
			this.btnProgressColor = btnProgressColor;
			return this;
		}

		/**
		 * 设置按钮进度条宽度
		 *
		 * @param btnProgressWidth
		 * @return
		 */
		public Builder setBtnProgressWidth(float btnProgressWidth) {
			this.btnProgressWidth = btnProgressWidth;
			return this;
		}

		/**
		 * 设置按钮进度条的背景颜色
		 *
		 * @param btnProgressBgColor
		 * @return
		 */
		public Builder setBtnProgressBgColor(int btnProgressBgColor) {
			this.btnProgressBgColor = btnProgressBgColor;
			return this;
		}

		/**
		 * 设置按钮是否显示进度数值显示
		 *
		 * @param isShowPercent
		 * @return
		 */
		public Builder setShowPercent(boolean isShowPercent) {
			this.isShowPercent = isShowPercent;
			return this;
		}

		/**
		 * 设置按钮进度条中心部分是否透明
		 *
		 * @param isProgressCenterLucency
		 * @return
		 */
		public Builder setProgressCenterLucency(boolean isProgressCenterLucency) {
			this.isProgressCenterLucency = isProgressCenterLucency;
			return this;
		}

		/**
		 * 设置按钮进度条中心部分颜色
		 *
		 * @param btnProgressCenterColor
		 * @return
		 */
		public Builder setBtnProgressCenterColor(int btnProgressCenterColor) {
			this.btnProgressCenterColor = btnProgressCenterColor;
			return this;
		}
		/**
		 * 设置进度条中心的图片
		 * @param btnProgressCenterImage
		 * @return
		 */
		public Builder setBtnProgressCenterImage(int btnProgressCenterImage) {
			this.btnProgressCenterImage = btnProgressCenterImage;
			return this;
		}

		/**
		 * 设置按钮进度条数值的颜色
		 *
		 * @param btnProgressPercentColor
		 * @return
		 */
		public Builder setBtnProgressPercentColor(int btnProgressPercentColor) {
			this.btnProgressPercentColor = btnProgressPercentColor;
			return this;
		}
		/**
		 * 设置按钮图标的大小尺寸
		 * 浮点数,相对于按钮的大小
		 * @param btnIconSize
		 * @return
		 */
		public Builder setBtnIconSize(float btnIconSize) {
			this.btnIconSize = btnIconSize;
			return this;
		}
		/**
		 * 按钮图标大小对比相对对象
		 * @param btnIconSizeType
		 * @return
		 */
		public Builder setBtnIconSizeType(int btnIconSizeType) {
			this.btnIconSizeType = btnIconSizeType;
			return this;
		}
		/**
		 * 设置图标的圆角大小
		 * @param btnIconRadius
		 * @return
		 */
		public Builder setBtnIconRadius(int btnIconRadius) {
			this.btnIconRadius = btnIconRadius;
			return this;
		}

	}
}
