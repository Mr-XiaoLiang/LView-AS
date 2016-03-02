package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

import android.graphics.Color;

public class HomeDivisionTitleItem_108Bean extends ShopItemBean {
	private String text;
	private String img;
	private int color;
	public String getText() {
		if(text==null)
			return "";
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImg(){
		if(img==null)
			return "";
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public void setColor(String color) {
		this.color = Color.parseColor(color);
	}
	public HomeDivisionTitleItem_108Bean(String text, String img, int color) {
		super();
		this.text = text;
		this.img = img;
		this.color = color;
	}
	public HomeDivisionTitleItem_108Bean() {
		super();
		this.text = "";
		this.img = "";
		this.color = Color.parseColor("#F63375");
	}
	public HomeDivisionTitleItem_108Bean(String text, String img) {
		super();
		this.text = text;
		this.img = img;
		this.color = Color.parseColor("#F63375");
	}
	
}
