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
import android.widget.LinearLayout;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeTraitStatementItem_106Bean;

/**
 * 特点声明
 * @author LiuJ
 *
 */
public class HomeTraitStatementItem_106 extends LinearLayout implements OnClickListener {

	private ImageView img1,img2,img3,img4;
	private ImageLoader imageLoader;
	private Context context;
	private HomeTraitStatementItem_106Bean bean;
	private OnClickListener listener;
	private LinearLayout root;
	private int windowHeight = 0;
	
	@Override
	public void onClick(View v) {
		if(listener!=null){
			listener.onClick(v);
		}
		switch (v.getId()) {
		case R.id.item_home_trait_statement_106_img1:
			
			break;
		case R.id.item_home_trait_statement_106_img2:
			
			break;
		case R.id.item_home_trait_statement_106_img3:
			
			break;
		case R.id.item_home_trait_statement_106_img4:
			
			break;

		default:
			break;
		}
	}

	public HomeTraitStatementItem_106(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init(){
		LayoutInflater.from(context).inflate(R.layout.item_home_trait_statement_106,
				this, true);
		root = (LinearLayout) findViewById(R.id.item_home_trait_statement_106_root);
		img1 = (ImageView) findViewById(R.id.item_home_trait_statement_106_img1);
		img2 = (ImageView) findViewById(R.id.item_home_trait_statement_106_img2);
		img3 = (ImageView) findViewById(R.id.item_home_trait_statement_106_img3);
		img4 = (ImageView) findViewById(R.id.item_home_trait_statement_106_img4);
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);
		imageLoader = ImageLoader.getInstance();
		if (windowHeight < 1) {
			WindowManager m = ((Activity) context).getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			windowHeight = d.getHeight();
		}
		ViewGroup.LayoutParams p = root.getLayoutParams();// getWindow().getAttributes();
		p.height = (int) (windowHeight * 0.12);
		root.setLayoutParams(p);
		dataSet();
	}
	
	private void dataSet(){
		if(bean!=null){
			img1.setVisibility(View.GONE);
			img2.setVisibility(View.GONE);
			img3.setVisibility(View.GONE);
			img4.setVisibility(View.GONE);
			if(bean.getImgUrlSize()>0){
				img1.setVisibility(View.VISIBLE);
				imageLoader.displayImage(bean.getImgUrl(0), img1);
			}
			if(bean.getImgUrlSize()>1){
				img2.setVisibility(View.VISIBLE);
				imageLoader.displayImage(bean.getImgUrl(0), img2);
			}
			if(bean.getImgUrlSize()>2){
				img3.setVisibility(View.VISIBLE);
				imageLoader.displayImage(bean.getImgUrl(0), img3);
			}
			if(bean.getImgUrlSize()>3){
				img4.setVisibility(View.VISIBLE);
				imageLoader.displayImage(bean.getImgUrl(0), img4);
			}
		}
	}
	
	public HomeTraitStatementItem_106(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public HomeTraitStatementItem_106(Context context, HomeTraitStatementItem_106Bean bean, OnClickListener listener) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		this.context = context;
		init();
	}

	
	
	public HomeTraitStatementItem_106(Context context, HomeTraitStatementItem_106Bean bean) {
		super(context);
		this.bean = bean;
		this.context = context;
		init();
	}

	public HomeTraitStatementItem_106(Context context, HomeTraitStatementItem_106Bean bean, OnClickListener listener,
			int windowHeight) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}

	public HomeTraitStatementItem_106(Context context, HomeTraitStatementItem_106Bean bean, int windowHeight) {
		super(context);
		this.bean = bean;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}

//	@Override
//	public void onWindowFocusChanged(boolean hasWindowFocus) {
//		super.onWindowFocusChanged(hasWindowFocus);
//		WindowManager m = ((Activity) context).getWindowManager();
//		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//		ViewGroup.LayoutParams p = getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
//		p.height = (int)(d.getHeight() * 0.08);
//		setLayoutParams(p);
//	}
}
