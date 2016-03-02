package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

/**
 * 中型横幅
 * @author LiuJ
 *
 */
public class HomeTextTitleItem_104Bean extends ShopItemBean {
	private String text;
	private BeanType intent;
	private String initenMsg;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public BeanType getIntent() {
		return intent;
	}
	public void setIntent(BeanType intent) {
		this.intent = intent;
	}
	public String getInitenMsg() {
		return initenMsg;
	}
	public void setInitenMsg(String initenMsg) {
		this.initenMsg = initenMsg;
	}
	public HomeTextTitleItem_104Bean(String text, BeanType intent, String initenMsg) {
		super();
		this.text = text;
		this.intent = intent;
		this.initenMsg = initenMsg;
	}
	public HomeTextTitleItem_104Bean() {
		super();
		this.text = "";
		this.intent = BeanType.DO_NOTHING;
		this.initenMsg = "";
	}
	
}
