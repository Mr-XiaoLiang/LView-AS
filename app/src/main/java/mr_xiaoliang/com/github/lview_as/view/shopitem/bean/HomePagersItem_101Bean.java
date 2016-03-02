package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

import java.util.ArrayList;

/**
 * 商品列表中的滚动焦点图Bean
 * 
 * @author LiuJ
 *
 */
public class HomePagersItem_101Bean extends ShopItemBean {
	/**
	 * 图片地址
	 */
	private ArrayList<String> imgUrl;
	/**
	 * 图片意图
	 */
	private ArrayList<BeanType> imgIntent;
	/**
	 * 意图目标
	 */
	private ArrayList<String> intentMsg;

	public HomePagersItem_101Bean() {
		super();
		this.imgUrl = new ArrayList<String>();
		this.imgIntent = new ArrayList<BeanType>();
		this.intentMsg = new ArrayList<String>();
	}

	public HomePagersItem_101Bean(ArrayList<String> imgUrl, ArrayList<BeanType> imgIntent, ArrayList<String> intentMsg) {
		super();
		this.imgUrl = imgUrl;
		this.imgIntent = imgIntent;
		this.intentMsg = intentMsg;
	}
	
	public String getImgUrl(int i) {
		if(imgUrl==null)
			return "";
		return imgUrl.get(i);
	}

	public int getImgUrlSize() {
		if(imgUrl==null)
			return 0;
		return imgUrl.size();
	}

	public ArrayList<String> getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(ArrayList<String> imgUrl) {
		this.imgUrl = imgUrl;
	}

	public ArrayList<BeanType> getImgIntent() {
		return imgIntent;
	}

	public BeanType getImgIntent(int i) {
		if(imgIntent==null)
			return BeanType.DO_NOTHING;
		return imgIntent.get(i);
	}

	public int getImgIntentSize() {
		if(imgIntent==null)
			return 0;
		return imgIntent.size();
	}

	public void setImgIntent(ArrayList<BeanType> imgIntent) {
		this.imgIntent = imgIntent;
	}

	public ArrayList<String> getIntentMsg() {
		return intentMsg;
	}

	public int getIntentMsgSize() {
		if(intentMsg==null)
			return 0;
		return intentMsg.size();
	}

	public String getIntentMsg(int i) {
		if(intentMsg==null)
			return "";
		return intentMsg.get(i);
	}

	public void setIntentMsg(ArrayList<String> intentMsg) {
		this.intentMsg = intentMsg;
	}

	public void addImgUrl(String url){
		if(imgUrl==null)
			return ;
		this.imgUrl.add(url);
	}
	public void addIntentMsg(String msg){
		if(intentMsg==null)
			return ;
		this.intentMsg.add(msg);
	}
	public void addImgIntent(BeanType type){
		if(imgIntent==null)
			return ;
		this.imgIntent.add(type);
	}
}
