package mr_xiaoliang.com.github.lview_as.view.shopitem.bean;

import java.util.ArrayList;

/**
 * 商城列表
 * @author LiuJ
 *
 */
public class ShopListBean {
	/**
	 * 轮播图
	 */
	public static final int HomePagersItem_101 = 101;
	/**
	 * 大三图并列
	 */
	public static final int HomeBigThreeItem_102 = 102;
	/**
	 * 中型横幅
	 */
	public static final int HomeMediumBannerItem_103 = 103;
	/**
	 * 文字标题
	 */
	public static final int HomeTextTitleItem_104 = 104;
	/**
	 * 小型横幅
	 */
	public static final int HomeSmallBannerItem_105 = 105;
	/**
	 * 特点声明
	 */
	public static final int HomeTraitStatementItem_106 = 106;
	/**
	 * 动态三列图片
	 */
	public static final int HomeThreeImgItem_107 = 107;
	/**
	 * 间隔标题
	 */
	public static final int HomeDivisionTitleItem_108 = 108;
	/**
	 * item的类型
	 */
	private ArrayList<Integer> types;
	/**
	 * item的属性
	 */
	private ArrayList<ShopItemBean> beans;
	public ArrayList<Integer> getTypes() {
		return types;
	}
	public Integer getTypes(int i) {
		return types.get(i);
	}
	public int getTypesSize() {
		return types.size();
	}
	public void setTypes(ArrayList<Integer> types) {
		this.types = types;
	}
	public ArrayList<ShopItemBean> getBeans() {
		return beans;
	}
	public Object getBeans(int i) {
		return beans.get(i);
	}
	public int getBeansSize() {
		return beans.size();
	}
	public void setBeans(ArrayList<ShopItemBean> beans) {
		this.beans = beans;
	}
	public void addBean(ShopItemBean o){
		beans.add(o);
	}
	public void addType(int i){
		types.add(i);
	}
	public ShopListBean() {
		super();
		this.types = new ArrayList<Integer>();
		this.beans = new ArrayList<ShopItemBean>();
	}
	public ShopListBean(ArrayList<Integer> types, ArrayList<ShopItemBean> beans) {
		super();
		this.types = types;
		this.beans = beans;
	}
}
