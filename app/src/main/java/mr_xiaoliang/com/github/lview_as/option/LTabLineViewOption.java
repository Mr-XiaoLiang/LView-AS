package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Color;

public class LTabLineViewOption {
	/**
	 * tab之间的权重
	 */
	private final int[] tabWeight;
	/**
	 * 线的颜色
	 */
	private final int lineColor;
	/**
	 * 是否让线使用圆形头部
	 */
	private final boolean isRoundHead;
	/**
	 * tab文字的大小
	 */
	private final int textSize;
	/**
	 * 每个Tab的文字长度
	 */
	private final int[] tabTextLength;
	
	public LTabLineViewOption(Builder builder) {
		super();
		this.tabWeight = builder.getTabWeight();
		this.lineColor = builder.getLineColor();
		this.isRoundHead = builder.isRoundHead();
		this.textSize = builder.getTextSize();
		this.tabTextLength = builder.getTabTextLength();
	}
	

	public int[] getTabWeight() {
		return tabWeight;
	}


	public int getLineColor() {
		return lineColor;
	}


	public boolean isRoundHead() {
		return isRoundHead;
	}

	public int getTextSize() {
		return textSize;
	}


	public int[] getTabTextLength() {
		return tabTextLength;
	}


	public static class Builder{
		/**
		 * tab之间的权重
		 */
		private int[] tabWeight = new int[]{};
		/**
		 * 线的颜色
		 */
		private int lineColor = Color.WHITE;
		/**
		 * 是否让线使用圆形头部
		 */
		private boolean isRoundHead = true;
		/**
		 * tab文字的大小
		 */
		private int textSize = 0;
		/**
		 * 每个Tab的文字长度
		 */
		private int[] tabTextLength = new int[]{};
		
		/**
		 * tab之间的权重
		 * 与setTabSize互斥
		 * 使用本方法表示tab的数量等于tabWeight的长度
		 */
		public Builder setTabWeight(int[] tabWeight) {
			this.tabWeight = tabWeight;
			return this;
		}
		/**
		 * tab的数量
		 * 与setTabWeight互斥
		 * 使用本方法表示tab之间的权重相等
		 */
		public Builder setTabSize(int tabSize) {
			this.tabWeight = new int[tabSize];
			for(int i = 0;i<tabSize;i++){
				tabWeight[i] = 1;
			}
			return this;
		}
		/**
		 * 线的颜色
		 */
		public Builder setLineColor(int lineColor) {
			this.lineColor = lineColor;
			return this;
		}
		/**
		 * 是否让线使用圆形头部
		 */
		public Builder setRoundHead(boolean isRoundHead) {
			this.isRoundHead = isRoundHead;
			return this;
		}
		/**
		 * tab文字的大小
		 */
		public int getTextSize() {
			return textSize;
		}
		public Builder setTextSize(int textSize) {
			this.textSize = textSize;
			return this;
		}
		public int[] getTabTextLength() {
			return tabTextLength;
		}
		/**
		 * 每个Tab的文字长度
		 */
		public Builder setTabTextLength(int[] tabTextLength) {
			this.tabTextLength = tabTextLength;
			return this;
		}
		public int[] getTabWeight() {
			return tabWeight;
		}
		public int getLineColor() {
			return lineColor;
		}
		public boolean isRoundHead() {
			return isRoundHead;
		}
		
	}
}
