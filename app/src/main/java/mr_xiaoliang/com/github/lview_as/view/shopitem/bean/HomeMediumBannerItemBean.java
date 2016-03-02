package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

/**
 * 横幅
 * @author LiuJ
 *
 */
public class HomeMediumBannerItemBean extends ShopItemBean {
	private String imgUrl;
	private BeanType intent;
	private String initenMsg;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public HomeMediumBannerItemBean(String imgUrl, BeanType intent, String initenMsg) {
		super();
		this.imgUrl = imgUrl;
		this.intent = intent;
		this.initenMsg = initenMsg;
	}
	public HomeMediumBannerItemBean() {
		super();
		this.imgUrl = "";
		this.intent = BeanType.DO_NOTHING;
		this.initenMsg = "";
	}
	
}
