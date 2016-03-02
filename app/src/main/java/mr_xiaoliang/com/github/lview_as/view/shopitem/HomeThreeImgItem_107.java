package mr_xiaoliang.com.github.lview_as.view.shopitem;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.util.DensityUtil;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeThreeImgItem_107Bean;

/**
 * 三图Item
 * 随着图片数量的变化，高度也会变化
 * @author LiuJ
 *
 */
public class HomeThreeImgItem_107 extends LinearLayout implements OnClickListener {

	private Context context;
	private ImageView img1,img2,img3;
	private HomeThreeImgItem_107Bean bean;
	private OnClickListener listener;
	private int windowHeight = 0;
	private LinearLayout root;
	private ImageLoader imageLoader;
	
	private void getView(Context context){
		this.context = context;
		LayoutInflater.from(this.context).inflate(R.layout.item_home_three_img_107,
				this, true);
		img1 = (ImageView) findViewById(R.id.item_home_three_img_107_img1);
		img2 = (ImageView) findViewById(R.id.item_home_three_img_107_img2);
		img3 = (ImageView) findViewById(R.id.item_home_three_img_107_img3);
		root = (LinearLayout) findViewById(R.id.item_home_three_img_107_root);
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		imageLoader = ImageLoader.getInstance();
	}
	
	private void init(){
		if (windowHeight < 1) {
			WindowManager m = ((Activity) context).getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			windowHeight = d.getHeight();
		}
		ViewGroup.LayoutParams p = root.getLayoutParams();// getWindow().getAttributes();
		if(bean==null||bean.getImgUrlSize()>2){
			p.height = (int) (windowHeight * 0.2);
			img1.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img3.setVisibility(View.VISIBLE);
			imageLoader.displayImage(bean.getImgUrl(0), img1);
			imageLoader.displayImage(bean.getImgUrl(1), img2);
			imageLoader.displayImage(bean.getImgUrl(2), img3);
			int dp = DensityUtil.dip2px(context, 5);
			root.setPadding(dp, dp, dp, dp);
			img1.setScaleType(ScaleType.CENTER_INSIDE);
			img2.setScaleType(ScaleType.CENTER_INSIDE);
			img3.setScaleType(ScaleType.CENTER_INSIDE);
		}else if(bean.getImgUrlSize()>1){
			p.height = (int) (windowHeight * 0.3);
			img1.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img3.setVisibility(View.GONE);
			imageLoader.displayImage(bean.getImgUrl(0), img1);
			imageLoader.displayImage(bean.getImgUrl(1), img2);
			img1.setScaleType(ScaleType.CENTER_INSIDE);
			img2.setScaleType(ScaleType.CENTER_INSIDE);
			int dp = DensityUtil.dip2px(context, 5);
			root.setPadding(dp, dp, dp, dp);
		}else if(bean.getImgUrlSize()>0){
			p.height = (int) (windowHeight * 0.4);
			imageLoader.displayImage(bean.getImgUrl(0), img1);
			img1.setVisibility(View.VISIBLE);
			img2.setVisibility(View.GONE);
			img3.setVisibility(View.GONE);
			root.setPadding(0, 0, 0, 0);
			img1.setScaleType(ScaleType.CENTER_CROP);
		}else{
			p.height = 0;
			img1.setVisibility(View.GONE);
			img2.setVisibility(View.GONE);
			img3.setVisibility(View.GONE);
		}
		root.setLayoutParams(p);
		
	}
	
	@Override
	public void onClick(View v) {
		if(listener!=null){
			listener.onClick(v);
		}
	}

	public HomeThreeImgItem_107(Context context, AttributeSet attrs) {
		super(context, attrs);
		getView(context);
		init();
	}

	public HomeThreeImgItem_107(Context context) {
		super(context);
		getView(context);
		init();
	}

	public HomeThreeImgItem_107(Context context, HomeThreeImgItem_107Bean bean, OnClickListener listener,
			int windowHeight) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		this.windowHeight = windowHeight;
		getView(context);
		init();
	}

	public HomeThreeImgItem_107(Context context, HomeThreeImgItem_107Bean bean, int windowHeight) {
		super(context);
		this.bean = bean;
		this.windowHeight = windowHeight;
		getView(context);
		init();
	}

	public HomeThreeImgItem_107(Context context, HomeThreeImgItem_107Bean bean) {
		super(context);
		this.bean = bean;
		getView(context);
		init();
	}

	public HomeThreeImgItem_107(Context context, HomeThreeImgItem_107Bean bean, OnClickListener listener) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		getView(context);
		init();
	}
}
