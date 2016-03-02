package mr_xiaoliang.com.github.lview_as.view.shopitem;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.option.MyApplication;
import mr_xiaoliang.com.github.lview_as.view.LPagePointView;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomePagersItem_101Bean;

/**
 * 商品列表中的滚动焦点图
 * @author LiuJ
 *
 */
public class HomePagersItem_101 extends FrameLayout implements OnPageChangeListener {
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 分页容器
	 */
	private ViewPager pagerView;
	/**
	 * 图片异步加载
	 */
	private ImageLoader imageLoader;
	/**
	 * 下标
	 */
	private LPagePointView pointView;
	/**
	 * 数据bean
	 */
	private HomePagersItem_101Bean bean;
	/**
	 * 图片数组
	 */
	private ImageView[] viewList;
	/**
	 * 点击事件
	 */
	private OnPageClickListener clickListener;
	/**
	 * 根节点
	 */
	private FrameLayout root;
	private int windowHeight = 0;
	
	private void init(){
		LayoutInflater.from(context).inflate(R.layout.item_home_pagers_101,
				this, true);
		pagerView = (ViewPager) findViewById(R.id.item_home_pagers_101_pager);
		pointView = (LPagePointView) findViewById(R.id.item_home_pagers_101_point);
		root = (FrameLayout) findViewById(R.id.item_home_pagers_101_root);
		if (windowHeight < 1) {
			WindowManager m = ((Activity) context).getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			windowHeight = d.getHeight();
		}
		ViewGroup.LayoutParams p = root.getLayoutParams();// getWindow().getAttributes();
		p.height = (int) (windowHeight * 0.25);
		root.setLayoutParams(p);
		
		imageLoader = ImageLoader.getInstance();
		if(bean==null||bean.getImgUrlSize()==0){
			return;
		}
		DataSet();
	}
	
	private void DataSet(){
		pointView.setPointSize(bean.getImgUrlSize());
		LinearLayout.LayoutParams imgParams;
		viewList = new ImageView[bean.getImgUrlSize()];
		for(int i  = 0;i<viewList.length;i++){
			ImageView image = new ImageView(context);
			imgParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			image.setLayoutParams(imgParams);
			image.setScaleType(ScaleType.CENTER_CROP);
			image.setOnClickListener(new OnImageClickListener(i));
			viewList[i] = image;
			imageLoader.displayImage(bean.getImgUrl(i), viewList[i], MyApplication.getImageLoaderOption());
		}
		pagerView.setOnPageChangeListener(this);
		pagerView.setAdapter(pagerAdapter);
	}
	
	private class OnImageClickListener implements OnClickListener {
		private int index;
		
		public OnImageClickListener(int index) {
			super();
			this.index = index;
		}
		@Override
		public void onClick(View v) {
			if(clickListener!=null){
				clickListener.onPageClick(HomePagersItem_101.this, index);
			}
			if(bean.getImgIntentSize()<=index)
				return;
			if(bean.getIntentMsgSize()<=index)
				return;
			
			switch (bean.getImgIntent(index)) {
			case GO_TO_EVENT:
				
				break;
			case GO_TO_FUNCTION:
				
				break;
			case GO_TO_GOODS:
				
				break;
			case GO_TO_SEARCH:
				
				break;
			case GO_TO_SHOP:
				
				break;
			case GO_TO_WEB:
				
				break;
			default:
				break;
			}
		}
	};
	/**
	 * 点击事件
	 * @author LiuJ
	 *
	 */
	public interface OnPageClickListener{
		public void onPageClick(View view, int index);
	}
	
	private PagerAdapter pagerAdapter = new PagerAdapter(){

		@Override
		public int getCount() {
			return viewList.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(viewList[position]);
		}

		/**
		 * 载入图片进去
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(viewList[position], 0);
//			viewList[position].setOnClickListener(new OnPagerClickListener(position));
			return viewList[position];
		}

	};
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		pointView.onChange(arg0, arg1);
	}
	@Override
	public void onPageSelected(int arg0) {
	}
	public HomePagersItem_101(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}
	public HomePagersItem_101(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	public HomePagersItem_101(Context context) {
		super(context);
		this.context = context;
		init();
	}
	public HomePagersItem_101(Context context,HomePagersItem_101Bean bean,OnPageClickListener listener) {
		super(context);
		this.bean = bean;
		this.clickListener = listener;
		this.context = context;
		init();
	}
	
	public HomePagersItem_101(Context context, HomePagersItem_101Bean bean, OnPageClickListener clickListener,
			int windowHeight) {
		super(context);
		this.bean = bean;
		this.clickListener = clickListener;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}

	public HomePagersItem_101(Context context, HomePagersItem_101Bean bean, int windowHeight) {
		super(context);
		this.bean = bean;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}

	public HomePagersItem_101Bean getBean() {
		return bean;
	}
	public void setBean(HomePagersItem_101Bean bean) {
		this.bean = bean;
		DataSet();
	}
	public OnPageClickListener getClickListener() {
		return clickListener;
	}
	public void setClickListener(OnPageClickListener clickListener) {
		this.clickListener = clickListener;
	}
	
}
