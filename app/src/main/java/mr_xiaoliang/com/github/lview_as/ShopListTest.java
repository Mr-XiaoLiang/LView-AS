package mr_xiaoliang.com.github.lview_as;


import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeBigThreeItem_102;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeDivisionTitleItem_108;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeMediumBannerItem_103;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomePagersItem_101;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeSmallBannerItem_105;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeTextTitleItem_104;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeThreeImgItem_107;
import mr_xiaoliang.com.github.lview_as.view.shopitem.HomeTraitStatementItem_106;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.BeanType;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeBigThreeItem_102Bean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeDivisionTitleItem_108Bean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeMediumBannerItemBean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomePagersItem_101Bean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeTextTitleItem_104Bean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeThreeImgItem_107Bean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeTraitStatementItem_106Bean;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.ShopListBean;

/**
 * 商城首页
 * 
 * @author LiuJ
 */
public class ShopListTest extends Activity {

	private ListView listView;

	private TextView hint;

	private ShopListBean bean;

	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_list);
		listView = (ListView) findViewById(R.id.shop_list_listview);
		hint = (TextView) findViewById(R.id.shop_list_hint);
		hint.setText("本系列的View都是我在工作中，写商城类APP想要偷懒，封装的一些Item，" + "为了自己以后方便，也厚颜无耻的上传上来了，本着反正已经加进来的心理，"
				+ "就再做了这个展示页面，哈哈，掠过即可，顺便吐槽一句，想用RecyclerView的，" + "但是死活会报错，人太懒，就用ListView了,Item样式总共有十多个，慢慢更新");
		initData();
	}

	private class MyAdapter extends BaseAdapter {
		private int height;
		
		public MyAdapter() {
			super();
			WindowManager m = ShopListTest.this.getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			height = d.getHeight();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			switch (bean.getTypes(position)) {
			case ShopListBean.HomePagersItem_101:
				if (convertView instanceof HomePagersItem_101) {
					((HomePagersItem_101) convertView).setBean((HomePagersItem_101Bean) bean.getBeans(position));
				} else {
					convertView = new HomePagersItem_101(ShopListTest.this,
							(HomePagersItem_101Bean) bean.getBeans(position), null);
				}
				break;
			case ShopListBean.HomeBigThreeItem_102:
				convertView = new HomeBigThreeItem_102(ShopListTest.this,
						(HomeBigThreeItem_102Bean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeMediumBannerItem_103:
				convertView = new HomeMediumBannerItem_103(ShopListTest.this,
						(HomeMediumBannerItemBean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeTextTitleItem_104:
				convertView = new HomeTextTitleItem_104(ShopListTest.this,
						(HomeTextTitleItem_104Bean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeSmallBannerItem_105:
				convertView = new HomeSmallBannerItem_105(ShopListTest.this,
						(HomeMediumBannerItemBean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeTraitStatementItem_106:
				convertView = new HomeTraitStatementItem_106(ShopListTest.this,
						(HomeTraitStatementItem_106Bean) bean.getBeans(position));
				break;
			case ShopListBean.HomeThreeImgItem_107:
				convertView = new HomeThreeImgItem_107(ShopListTest.this, (HomeThreeImgItem_107Bean) bean.getBeans(position), height);
				break;
			case ShopListBean.HomeDivisionTitleItem_108:
				convertView = new HomeDivisionTitleItem_108(ShopListTest.this, (HomeDivisionTitleItem_108Bean)bean.getBeans(position));
				break;
			default:
				break;
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return bean.getBeansSize();
		}

		@Override
		public Object getItem(int position) {
			return bean.getBeans(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	private void initData() {
		HomePagersItem_101Bean item_101Bean = new HomePagersItem_101Bean();
		item_101Bean.addImgUrl("http://c.vpimg1.com/upcb/2016/02/01/110/07097254.jpg");
		item_101Bean.addImgUrl("http://d.vpimg1.com/upcb/2016/01/29/72/48224197.jpg");
		item_101Bean.addImgUrl("http://c.vpimg1.com/upcb/2016/01/25/125/41534633.jpg");
		item_101Bean.addImgUrl("http://c.vpimg1.com/upcb/2016/02/05/162/31238899.jpg");
		item_101Bean.addImgUrl("http://d.vpimg1.com/upcb/2016/02/01/59/51306403.jpg");
		bean = new ShopListBean();
		bean.addBean(item_101Bean);
		bean.addType(ShopListBean.HomePagersItem_101);
		HomeBigThreeItem_102Bean item_102Bean = new HomeBigThreeItem_102Bean();
		item_102Bean
				.addBgUrl("http://d.vpimg1.com/upcb/2016/02/05/10/18276626.jpg");
		item_102Bean.addBgUrl("http://img4.duitang.com/uploads/blog/201310/18/20131018213446_smUw4.thumb.700_0.jpeg");
		item_102Bean.addBgUrl("http://img3.3lian.com/2013/s1/65/d/113.jpg");
		item_102Bean.addIconUrl("http://static.freepik.com/free-photo/circle-arrow-right_318-26614.jpg");
		item_102Bean.addIconUrl("http://static.freepik.com/free-photo/circle-arrow-right_318-26614.jpg");
		item_102Bean.addIconUrl("http://static.freepik.com/free-photo/circle-arrow-right_318-26614.jpg");
		item_102Bean.addMsg("啊啊啊啊，就是测试");
		item_102Bean.addMsg("啊啊啊啊，就是测试");
		item_102Bean.addMsg("啊啊啊啊，就是测试");
		item_102Bean.addTitle("减价大优惠啊");
		item_102Bean.addTitle("减价大优惠啊");
		item_102Bean.addTitle("减价大优惠啊");
		bean.addBean(item_102Bean);
		bean.addType(ShopListBean.HomeBigThreeItem_102);
		HomeMediumBannerItemBean item_103Bean = new HomeMediumBannerItemBean(
				"http://c.vpimg1.com/upcb/2016/02/16/198/50468782.jpg",
				BeanType.DO_NOTHING, "");
		bean.addBean(item_103Bean);
		bean.addType(ShopListBean.HomeMediumBannerItem_103);
		HomeTextTitleItem_104Bean item_104Bean = new HomeTextTitleItem_104Bean("新店开张，全场5折", BeanType.DO_NOTHING, "");
		bean.addBean(item_104Bean);
		bean.addType(ShopListBean.HomeTextTitleItem_104);
		HomeMediumBannerItemBean item_105Bean = new HomeMediumBannerItemBean(
				"https://aecpm.alicdn.com/simba/img/TB1_TemLFXXXXbiaXXXSutbFXXX.jpg",
				BeanType.DO_NOTHING, "");
		bean.addBean(item_105Bean);
		bean.addType(ShopListBean.HomeSmallBannerItem_105);
		HomeTraitStatementItem_106Bean item_106Bean = new HomeTraitStatementItem_106Bean();
		item_106Bean.addImgUrl("drawable://" + R.mipmap.demo1);
		item_106Bean.addImgUrl("drawable://" + R.mipmap.demo1);
		item_106Bean.addImgUrl("drawable://" + R.mipmap.demo1);
		item_106Bean.addImgUrl("drawable://" + R.mipmap.demo1);
		bean.addBean(item_106Bean);
		bean.addType(ShopListBean.HomeTraitStatementItem_106);
		HomeDivisionTitleItem_108Bean item_108Bean = new HomeDivisionTitleItem_108Bean("海外精品·每日精选", "");
		bean.addBean(item_108Bean);
		bean.addType(ShopListBean.HomeDivisionTitleItem_108);
		HomeThreeImgItem_107Bean item_107Bean = new HomeThreeImgItem_107Bean();
		item_107Bean.addImgUrl("http://img.taopic.com/uploads/allimg/130628/235093-13062R2422935.jpg");
		item_107Bean.addImgUrl("http://pic.nipic.com/2008-01-08/200818105723778_2.jpg");
		item_107Bean.addImgUrl("http://img.taopic.com/uploads/allimg/130628/235093-13062R2422935.jpg");
		bean.addBean(item_107Bean);
		bean.addType(ShopListBean.HomeThreeImgItem_107);
		item_107Bean = new HomeThreeImgItem_107Bean();
		item_107Bean.addImgUrl("http://pic17.nipic.com/20111108/5682361_111022092000_2.jpg");
		item_107Bean.addImgUrl("http://pic17.nipic.com/20111108/5682361_111022092000_2.jpg");
		bean.addBean(item_107Bean);
		bean.addType(ShopListBean.HomeThreeImgItem_107);
		item_107Bean = new HomeThreeImgItem_107Bean();
		item_107Bean.addImgUrl("https://img.alicdn.com/tps/TB1Of0SLFXXXXb8XpXXXXXXXXXX-520-280.jpg");
		bean.addBean(item_107Bean);
		bean.addType(ShopListBean.HomeThreeImgItem_107);
		listView.setAdapter(adapter = new MyAdapter());
	}
}
