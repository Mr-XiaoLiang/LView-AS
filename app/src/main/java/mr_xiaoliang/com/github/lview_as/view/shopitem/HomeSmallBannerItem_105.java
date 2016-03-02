package mr_xiaoliang.com.github.lview_as.view.shopitem;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.option.MyApplication;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeMediumBannerItemBean;

/**
 * 小型横幅
 * @author LiuJ
 *
 */
public class HomeSmallBannerItem_105 extends LinearLayout implements OnClickListener {

	private HomeMediumBannerItemBean bean;
	private ImageView imageView;
	private ImageLoader loader;
	private Context context;
	private OnClickListener listener;
	private LinearLayout root;
	private int windowHeight = 0;
	
	public HomeSmallBannerItem_105(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init(){
		LayoutInflater.from(context).inflate(R.layout.item_home_small_banner_105,
				this, true);
		root = (LinearLayout) findViewById(R.id.item_home_small_banner_105_root);
		imageView = (ImageView) findViewById(R.id.item_home_small_banner_105_img);
		imageView.setOnClickListener(this);
		loader = ImageLoader.getInstance();
		if (windowHeight < 1) {
			WindowManager m = ((Activity) context).getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			windowHeight = d.getHeight();
		}
		ViewGroup.LayoutParams p = root.getLayoutParams();// getWindow().getAttributes();
		p.height = (int) (windowHeight * 0.15);
		root.setLayoutParams(p);
		if(bean!=null)
			dataSet();
	}
	
	private void dataSet(){
		loader.displayImage(bean.getImgUrl(), imageView, MyApplication.getImageLoaderOption());
	}
	
	@Override
	public void onClick(View v) {
		if(listener!=null)
			listener.onClick(v);
		if(bean==null)
			return;
		switch (v.getId()) {
		case R.id.item_home_medium_banner_103_img:
			switch (bean.getIntent()) {
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
			break;
		default:
			break;
		}
	}

	public HomeSmallBannerItem_105(Context context) {
		super(context);
		this.context = context;
		init();
	}
//	@Override
//	public void onWindowFocusChanged(boolean hasWindowFocus) {
//		super.onWindowFocusChanged(hasWindowFocus);
//		WindowManager m = ((Activity) context).getWindowManager();
//		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//		ViewGroup.LayoutParams p = getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
//		p.height = (int)(d.getHeight() * 0.1);
//		setLayoutParams(p);
//	}

	public HomeSmallBannerItem_105(Context context, HomeMediumBannerItemBean bean,OnClickListener listener) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		this.context = context;
		init();
	}

	public HomeSmallBannerItem_105(Context context, HomeMediumBannerItemBean bean, int windowHeight) {
		super(context);
		this.bean = bean;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}

	public HomeSmallBannerItem_105(Context context, HomeMediumBannerItemBean bean, OnClickListener listener,
			int windowHeight) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}
	
}
