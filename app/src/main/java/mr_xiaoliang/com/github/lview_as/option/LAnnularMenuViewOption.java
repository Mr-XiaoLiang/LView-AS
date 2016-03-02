package mr_xiaoliang.com.github.lview_as.option;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

/**
 * 环形菜单的参数项
 * @author LiuJ
 *
 */
public class LAnnularMenuViewOption {
	private final RingType ringType;
	private final GravityType gravityType;
	private final BackgroundType backgroundType;
	private final DetailScaleType detailScaleType;
	private final AnimationType animationType;
	private final ArrayList<Item> items;
	private final Bitmap src;
	private final Bitmap bg;
	private final int bgColor;
	private final int maxItemCount;
	private final float itemProportionWithRing;
	private final int ringDiameter;
	public LAnnularMenuViewOption(Builder builder) {
		super();
		this.ringType = builder.ringType;
		this.gravityType = builder.gravityType;
		this.backgroundType = builder.backgroundType;
		this.detailScaleType = builder.detailScaleType;
		this.animationType = builder.animationType;
		this.items = builder.items;
		this.src = builder.src;
		this.bg = builder.bg;
		this.bgColor = builder.bgColor;
		this.maxItemCount = builder.maxItemCount;
		this.itemProportionWithRing = builder.itemProportionWithRing;
		this.ringDiameter = builder.ringDiameter;
	}
	
	public RingType getRingType() {
		return ringType;
	}
	public GravityType getGravityType() {
		return gravityType;
	}
	public BackgroundType getBackgroundType() {
		return backgroundType;
	}
	public DetailScaleType getDetailScaleType() {
		return detailScaleType;
	}
	public AnimationType getAnimationType() {
		return animationType;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public Bitmap getSrc() {
		return src;
	}
	public Bitmap getBg() {
		return bg;
	}
	public int getBgColor() {
		return bgColor;
	}
	public int getMaxItemCount() {
		return maxItemCount;
	}
	public float getItemProportionWithRing() {
		return itemProportionWithRing;
	}
	public int getRingDiameter() {
		return ringDiameter;
	}

	public static class Builder{
		/**
		 * 占满所在的边
		 */
		public final static int RingDiameter_FitXY = 1;
		/**
		 * 在保留Item控件的情况下占满边
		 */
		public final static int RingDiameter_WithItem = 0;
		private RingType ringType = RingType.Small;
		private GravityType gravityType = GravityType.Right;
		private BackgroundType backgroundType = BackgroundType.L;
		private DetailScaleType detailScaleType = DetailScaleType.CenterCrop;
		private AnimationType animationType = AnimationType.Both;
		private ArrayList<Item> items = null;
		private Bitmap src = null;
		private Bitmap bg = null;
		private int bgColor = Color.argb(128, 255, 255, 255);
		private int maxItemCount = 3;
		private float itemProportionWithRing = 5;
		private int ringDiameter = RingDiameter_WithItem;
		
		public Builder(ArrayList<Item> items) {
			super();
			this.items = items;
		}
		public RingType getRingType() {
			return ringType;
		}
		public Builder setRingType(RingType ringType) {
			this.ringType = ringType;
			return this;
		}
		public GravityType getGravityType() {
			return gravityType;
		}
		public Builder setGravityType(GravityType gravityType) {
			this.gravityType = gravityType;
			return this;
		}
		public BackgroundType getBackgroundType() {
			return backgroundType;
		}
		public Builder setBackgroundType(BackgroundType backgroundType) {
			this.backgroundType = backgroundType;
			return this;
		}
		public DetailScaleType getDetailScaleType() {
			return detailScaleType;
		}
		public Builder setDetailScaleType(DetailScaleType detailScaleType) {
			this.detailScaleType = detailScaleType;
			return this;
		}
		public AnimationType getAnimationType() {
			return animationType;
		}
		public Builder setAnimationType(AnimationType animationType) {
			this.animationType = animationType;
			return this;
		}
		public ArrayList<Item> getItems() {
			return items;
		}
		public Builder setItems(ArrayList<Item> items) {
			this.items = items;
			return this;
		}
		public Bitmap getSrc() {
			return src;
		}
		public Builder setSrc(Bitmap src) {
			this.src = src;
			return this;
		}
		public Bitmap getBg() {
			return bg;
		}
		public Builder setBg(Bitmap bg) {
			this.bg = bg;
			return this;
		}
		public int getBgColor() {
			return bgColor;
		}
		public Builder setBgColor(int bgColor) {
			this.bgColor = bgColor;
			return this;
		}
		public int getMaxItemCount() {
			return maxItemCount;
		}
		/**
		 * 这里的值是指半个圆上面显示的内容数量
		 * @param maxItemCount
		 * @return
		 */
		public Builder setMaxItemCount(int maxItemCount) {
			this.maxItemCount = maxItemCount;
			return this;
		}
		public float getItemProportionWithRing() {
			return itemProportionWithRing;
		}
		public Builder setItemProportionWithRing(float itemProportionWithRing) {
			this.itemProportionWithRing = itemProportionWithRing;
			return this;
		}
		public int getRingDiameter() {
			return ringDiameter;
		}
		/**
		 * 修改轮子直径
		 * 填入的值为与所在边长度的倍数
		 * 或者填入RingDiameter_FitXY或RingDiameter_WithItem
		 * @param ringDiameter
		 * @return
		 */
		public Builder setRingDiameter(int ringDiameter) {
			if(ringDiameter<0)
				ringDiameter = 0;
			this.ringDiameter = ringDiameter;
			return this;
		}
		
	}
	/**
	 * Big：环形轮子占满整个View，菜单项环绕轮子，详细内容显示在轮子中
	 * Small：环形轮子不占满整个View，菜单项环绕轮子，详细内容显示在轮子外
	 * @author LiuJ
	 */
	public enum RingType{
		Big,Small
	}
	/**
	 * 轮子所在的位置
	 * @author LiuJ
	 */
	public enum GravityType{
		Left,Right,Top,Bottom
	}
	/**
	 * 背景的大小
	 * S：只有轮子有背景
	 * M：轮子+菜单项
	 * L：轮子+菜单项+详细（仅RingType.Small下有效）
	 * @author LiuJ
	 *
	 */
	public enum BackgroundType{
		S,M,L
	}
	/**
	 * 详细图片显示类型
	 * @author LiuJ
	 *
	 */
	public enum DetailScaleType{
		CenterInside,CenterCrop
	}
	/**
	 * 动画类型（详细内容的出现动画）
	 * Alpha:渐隐渐出
	 * Location：缩进弹出
	 * Both：都有
	 * @author LiuJ
	 * 
	 */
	public enum AnimationType{
		Alpha,Location,Both
	}
	/**
	 * Item的参数集
	 * @author LiuJ
	 *
	 */
	public class Item{
		private Bitmap icon;
		private Bitmap detail;
		private String name;
		public Item(Bitmap icon, Bitmap detail, String name) {
			super();
			this.icon = icon;
			this.detail = detail;
			this.name = name;
		}
		public Item(Context context,int icon, int detail, String name) {
			super();
			Resources resources = context.getResources();
			this.icon = ((BitmapDrawable)(resources.getDrawable(icon))).getBitmap();
			this.detail = ((BitmapDrawable)(resources.getDrawable(detail))).getBitmap();
			this.name = name;
		}
		public Item() {
			super();
			this.icon = null;
			this.detail = null;
			this.name = null;
		}
		public Bitmap getIcon() {
			return icon;
		}
		public void setIcon(Bitmap icon) {
			this.icon = icon;
		}
		public Bitmap getDetail() {
			return detail;
		}
		public void setDetail(Bitmap detail) {
			this.detail = detail;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
