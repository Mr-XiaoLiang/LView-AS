package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

import java.util.ArrayList;
/**
 * 三个并排式，大图
 * @author LiuJ
 *
 */
public class HomeBigThreeItem_102Bean extends ShopItemBean {
	private ArrayList<String> bgUrl;
	private ArrayList<String> iconUrl;
	private ArrayList<String> title;
	private ArrayList<String> msg;
	private ArrayList<BeanType> intent;
	private ArrayList<String> intentMsg;
	
	public void addIntent(BeanType s){
		if(intent!=null)
			intent.add(s);
	}
	
	public void addIntentMsg(String s){
		if(intentMsg!=null)
			intentMsg.add(s);
	}
	
	public void addMsg(String s){
		if(msg!=null)
			msg.add(s);
	}
	
	public void addTitle(String s){
		if(title!=null)
			title.add(s);
	}
	
	public void addIconUrl(String s){
		if(iconUrl!=null)
			iconUrl.add(s);
	}
	
	public void addBgUrl(String s){
		if(bgUrl!=null)
			bgUrl.add(s);
	}
	
	public String getIntentMsg(int i){
		if(intentMsg==null||intentMsg.size()<=i)
			return "";
		return intentMsg.get(i);
	}
	
	public BeanType getIntent(int i){
		if(intent==null||intent.size()<=i)
			return BeanType.DO_NOTHING;
		return intent.get(i);
	}
	
	public String getMsg(int i){
		if(msg==null||msg.size()<=i)
			return "";
		return msg.get(i);
	}
	
	public String getTitle(int i){
		if(title==null||title.size()<=i)
			return "";
		return title.get(i);
	}
	
	public String getIconUrl(int i){
		if(iconUrl==null||iconUrl.size()<=i)
			return "";
		return iconUrl.get(i);
	}
	
	public String getBgUrl(int i){
		if(bgUrl==null||bgUrl.size()<=i)
			return "";
		return bgUrl.get(i);
	}
	
	public int getIntentMsgSize(){
		if(intentMsg==null)
			return 0;
		return intentMsg.size();
	}
	
	public int getIntentSize(){
		if(intent==null)
			return 0;
		return intent.size();
	}
	
	public int getMsgSize(){
		if(msg==null)
			return 0;
		return msg.size();
	}
	
	public int getTitleSize(){
		if(title==null)
			return 0;
		return title.size();
	}
	
	public int getBgUrlSize(){
		if(bgUrl==null)
			return 0;
		return bgUrl.size();
	}
	
	public int getIconUrlSize(){
		if(iconUrl==null)
			return 0;
		return iconUrl.size();
	}
	
	public ArrayList<String> getBgUrl() {
		return bgUrl;
	}
	public void setBgUrl(ArrayList<String> bgUrl) {
		this.bgUrl = bgUrl;
	}
	public ArrayList<String> getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(ArrayList<String> iconUrl) {
		this.iconUrl = iconUrl;
	}
	public ArrayList<String> getTitle() {
		return title;
	}
	public void setTitle(ArrayList<String> title) {
		this.title = title;
	}
	public ArrayList<String> getMsg() {
		return msg;
	}
	public void setMsg(ArrayList<String> msg) {
		this.msg = msg;
	}
	public ArrayList<BeanType> getIntent() {
		return intent;
	}
	public void setIntent(ArrayList<BeanType> intent) {
		this.intent = intent;
	}
	public ArrayList<String> getIntentMsg() {
		return intentMsg;
	}
	public void setIntentMsg(ArrayList<String> intentMsg) {
		this.intentMsg = intentMsg;
	}

	public HomeBigThreeItem_102Bean(ArrayList<String> bgUrl, ArrayList<String> iconUrl, ArrayList<String> title,
			ArrayList<String> msg, ArrayList<BeanType> intent, ArrayList<String> intentMsg) {
		super();
		this.bgUrl = bgUrl;
		this.iconUrl = iconUrl;
		this.title = title;
		this.msg = msg;
		this.intent = intent;
		this.intentMsg = intentMsg;
	}

	public HomeBigThreeItem_102Bean() {
		super();
		this.bgUrl = new ArrayList<String>();
		this.iconUrl = new ArrayList<String>();
		this.title = new ArrayList<String>();
		this.msg = new ArrayList<String>();
		this.intent = new ArrayList<BeanType>();
		this.intentMsg = new ArrayList<String>();
	}
	
}
