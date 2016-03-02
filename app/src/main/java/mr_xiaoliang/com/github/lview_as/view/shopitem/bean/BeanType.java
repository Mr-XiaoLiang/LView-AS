package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;
/**
 * item点击的类型
 * @author LiuJ
 *
 */
public enum BeanType {
	/**
	 * 什么也不做
	 */
	DO_NOTHING (0),
	/**
	 * 1，跳转到店铺首页
	 */
	GO_TO_SHOP (1),
	/**
	 * 2，活动专页
	 */
	GO_TO_EVENT (2),
	/**
	 * 3，功能分区
	 */
	GO_TO_FUNCTION (3),
	/**
	 * 4，单品页
	 */
	GO_TO_GOODS (4),
	/**
	 * 5，网页
	 */
	GO_TO_WEB (5),
	/**
	 * 6，分类搜索结果页
	 */
	GO_TO_SEARCH (6);
	
	private int type;
	private BeanType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
