package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Color;

public class LWheelViewOption {
	/**
	 * 背景色
	 */
	private final int backgroundColor;
	/**
	 * 字体大小
	 */
	private final int textSize;
	/**
	 * 字体大小比例（View的高度）
	 */
	private final float textFloat;
	/**
	 * 间隔高度
	 */
	private final int intervalHeight;
	/**
	 * 间隔高度比例（View的高度）
	 */
	private final float intervalFloat;
	/**
	 * 黑色的字体色
	 */
	private final int textColor;
	/**
	 * 阻力大小 (0,1)
	 */
	private final float resistance;

	/**
	 * 放大镜
	 */
	private final boolean magnifier;
	/**
	 * 放大镜背景颜色
	 */
	private final int magnifierBgColor;
	/**
	 * 放大镜文字颜色
	 */
	private final int magnifierTextColor;
	/**
	 * 选中项背景色
	 */
	private final int selectedBgColor;
	/**
	 * 是否循环
	 */
	private final boolean cycle;
	
	public LWheelViewOption(Builder builder) {
		super();
		this.backgroundColor = builder.backgroundColor;
		this.intervalFloat = builder.intervalFloat;
		this.intervalHeight = builder.intervalHeight;
		this.resistance = builder.resistance;
		this.textColor = builder.textColor;
		this.textFloat = builder.textFloat;
		this.textSize = builder.textSize;
		this.magnifier = builder.magnifier;
		this.magnifierBgColor = builder.magnifierBgColor;
		this.magnifierTextColor = builder.magnifierTextColor;
		this.selectedBgColor = builder.selectedBgColor;
		this.cycle = builder.cycle;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public int getTextSize() {
		return textSize;
	}

	public float getTextFloat() {
		return textFloat;
	}

	public int getIntervalHeight() {
		return intervalHeight;
	}

	public float getIntervalFloat() {
		return intervalFloat;
	}

	public int getTextColor() {
		return textColor;
	}

	public float getResistance() {
		return resistance;
	}
	
	public boolean isMagnifier() {
		return magnifier;
	}

	public int getMagnifierBgColor() {
		return magnifierBgColor;
	}

	public int getMagnifierTextColor() {
		return magnifierTextColor;
	}

	public int getSelectedBgColor() {
		return selectedBgColor;
	}

	public boolean isCycle() {
		return cycle;
	}

	public final static class Builder {
		/**
		 * 背景色
		 */
		private int backgroundColor = Color.TRANSPARENT;
		/**
		 * 字体大小
		 */
		private int textSize = -1;
		/**
		 * 字体大小比例（View的高度）
		 */
		private float textFloat = 0.1f;
		/**
		 * 间隔高度
		 */
		private int intervalHeight = -1;
		/**
		 * 间隔高度比例（View的高度）
		 */
		private float intervalFloat = 0.03f;
		/**
		 * 黑色的字体色
		 */
		private int textColor = Color.BLACK;
		/**
		 * 阻力大小 (0,1)
		 */
		private float resistance = 0.01f;
		/**
		 * 放大镜
		 */
		private boolean magnifier = true;
		/**
		 * 放大镜背景颜色
		 */
		private int magnifierBgColor = Color.WHITE;
		/**
		 * 放大镜文字颜色
		 */
		private int magnifierTextColor = Color.BLACK;
		/**
		 * 选中项背景色
		 */
		private int selectedBgColor = Color.TRANSPARENT;
		/**
		 * 循环
		 */
		private boolean cycle = false;
		
		/**
		 * 设置背景颜色
		 * 
		 * @param backgroundColor
		 * @return
		 */
		public Builder setBackgroundColor(int backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		/**
		 * 设置文字大小
		 * 
		 * @param textSize
		 * @return
		 */
		public Builder setTextSize(int textSize) {
			this.textSize = textSize;
			return this;
		}

		/**
		 * 设置文字比例
		 * 
		 * @param textFloat
		 * @return
		 */
		public Builder setTextSize(float textFloat) {
			this.textFloat = textFloat;
			this.textSize = -1;
			return this;
		}

		/**
		 * 设置间隔高度
		 * 
		 * @param intervalHeight
		 * @return
		 */
		public Builder setIntervalHeight(int intervalHeight) {
			this.intervalHeight = intervalHeight;
			return this;
		}

		/**
		 * 设置间隔高度比例
		 * 
		 * @param intervalFloat
		 * @return
		 */
		public Builder setIntervalHeight(float intervalFloat) {
			this.intervalFloat = intervalFloat;
			this.intervalHeight = -1;
			return this;
		}

		/**
		 * 设置文字颜色
		 * 
		 * @param textColor
		 * @return
		 */
		public Builder setTextColor(int textColor) {
			this.textColor = textColor;
			return this;
		}

		/**
		 * 设置阻力 (0,1)
		 * 
		 * @param resistance
		 * @return
		 */
		public Builder setResistance(float resistance) {
			this.resistance = resistance;
			return this;
		}
		/**
		 * 是否显示放大镜
		 * @param magnifier
		 */
		public Builder setMagnifier(boolean magnifier) {
			this.magnifier = magnifier;
			return this;
		}
		/**
		 * 设置背景色颜色
		 * @param magnifierBgColor
		 */
		public Builder setMagnifierBgColor(int magnifierBgColor) {
			this.magnifierBgColor = magnifierBgColor;
			return this;
		}
		/**
		 * 设置放大镜颜色
		 * @param magnifierTextColor
		 */
		public Builder setMagnifierTextColor(int magnifierTextColor) {
			this.magnifierTextColor = magnifierTextColor;
			return this;
		}
		/**
		 * 选中项背景色
		 * @param selectedBgColor
		 */
		public Builder setSelectedBgColor(int selectedBgColor) {
			this.selectedBgColor = selectedBgColor;
			return this;
		}
		/**
		 * 设置是否循环
		 * @param cycle
		 */
		public Builder setCycle(boolean cycle) {
			this.cycle = cycle;
			return this;
		}
		
	}
}
