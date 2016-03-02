package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

import java.util.ArrayList;

/**
 * 特点声明
 * 最多4个，超过的无效
 * @author LiuJ
 *
 */
public class HomeTraitStatementItem_106Bean extends ShopItemBean {
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
	public ArrayList<String> getImgUrl() {
		return imgUrl;
	}
	public String getImgUrl(int i) {
		if(i<getImgUrlSize()&&i>-1){
			return imgUrl.get(i);
		}
		return "";
	}
	public int getImgUrlSize() {
		if(imgUrl==null){
			return 0;
		}
		return imgUrl.size();
	}
	public void setImgUrl(ArrayList<String> imgUrl) {
		this.imgUrl = imgUrl;
	}
	public ArrayList<BeanType> getImgIntent() {
		return imgIntent;
	}
	public BeanType getImgIntent(int i) {
		if(i<getImgIntentSize()&&i>-1){
			return imgIntent.get(i);
		}
		return BeanType.DO_NOTHING;
	}
	public int getImgIntentSize() {
		if(imgIntent==null){
			return 0;
		}
		return imgIntent.size();
	}
	public void setImgIntent(ArrayList<BeanType> imgIntent) {
		this.imgIntent = imgIntent;
	}
	public ArrayList<String> getIntentMsg() {
		return intentMsg;
	}
	public String getIntentMsg(int i) {
		if(i<getImgUrlSize()&&i>-1){
			return intentMsg.get(i);
		}
		return "";
	}
	public int getIntentMsgSize() {
		if(intentMsg==null){
			return 0;
		}
		return intentMsg.size();
	}
	public void setIntentMsg(ArrayList<String> intentMsg) {
		this.intentMsg = intentMsg;
	}
	public HomeTraitStatementItem_106Bean(ArrayList<String> imgUrl, ArrayList<BeanType> imgIntent,
			ArrayList<String> intentMsg) {
		super();
		this.imgUrl = imgUrl;
		this.imgIntent = imgIntent;
		this.intentMsg = intentMsg;
	}
	public HomeTraitStatementItem_106Bean() {
		super();
		this.imgUrl = new ArrayList<String>();
		this.imgIntent = new ArrayList<BeanType>();
		this.intentMsg = new ArrayList<String>();
	}
	
	public void addImgUrl(String url){
		if(imgUrl!=null){
			imgUrl.add(url);
		}
	}
	public void addIntentMsg(String msg){
		if(intentMsg!=null){
			intentMsg.add(msg);
		}
	}
	public void addImgIntent(BeanType t){
		if(imgIntent!=null){
			imgIntent.add(t);
		}
	}
	
}
