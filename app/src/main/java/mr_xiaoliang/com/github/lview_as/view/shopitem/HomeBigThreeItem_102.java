package mr_xiaoliang.com.github.lview_as.view.shopitem;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.option.MyApplication;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeBigThreeItem_102Bean;

/**
 * 三个并排式，大图
 * @author LiuJ
 *
 */
public class HomeBigThreeItem_102 extends LinearLayout implements OnClickListener {

	private HomeBigThreeItem_102Bean bean = null;
	private ImageView bgView1,bgView2,bgView3,iconView1,iconView2,iconView3;
	private TextView titleView1,titleView2,titleView3,msgView1,msgView2,msgView3;
	private Context context;
	private FrameLayout rootLayout1,rootLayout2,rootLayout3;
	private ImageLoader loader;
	private OnClickListener clickListener;
	private LinearLayout root;
	private int windowHeight = 0;
	
	private void init(){
		LayoutInflater.from(context).inflate(R.layout.item_home_big_three_102,
				this, true);
		root = (LinearLayout) findViewById(R.id.item_home_big_three_102_root);
		bgView1 = (ImageView) findViewById(R.id.item_home_big_three_102_bg1);
		bgView2 = (ImageView) findViewById(R.id.item_home_big_three_102_bg2);
		bgView3 = (ImageView) findViewById(R.id.item_home_big_three_102_bg3);
		iconView1 = (ImageView) findViewById(R.id.item_home_big_three_102_icon1);
		iconView2 = (ImageView) findViewById(R.id.item_home_big_three_102_icon2);
		iconView3 = (ImageView) findViewById(R.id.item_home_big_three_102_icon3);
		titleView1 = (TextView) findViewById(R.id.item_home_big_three_102_title1);
		titleView2 = (TextView) findViewById(R.id.item_home_big_three_102_title2);
		titleView3 = (TextView) findViewById(R.id.item_home_big_three_102_title3);
		msgView1 = (TextView) findViewById(R.id.item_home_big_three_102_msg1);
		msgView2 = (TextView) findViewById(R.id.item_home_big_three_102_msg2);
		msgView3 = (TextView) findViewById(R.id.item_home_big_three_102_msg3);
		rootLayout1 = (FrameLayout) findViewById(R.id.item_home_big_three_102_root1);
		rootLayout2 = (FrameLayout) findViewById(R.id.item_home_big_three_102_root2);
		rootLayout3 = (FrameLayout) findViewById(R.id.item_home_big_three_102_root3);
		loader = ImageLoader.getInstance();
		if(windowHeight<1){
			WindowManager m = ((Activity) context).getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			windowHeight = d.getHeight();
		}
		ViewGroup.LayoutParams p = root.getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int)(windowHeight * 0.25);
		root.setLayoutParams(p);
		if(bean==null){
			return;
		}
		DataSet();
	}
	private void DataSet(){
		loader.displayImage(bean.getBgUrl(0), bgView1, MyApplication.getImageLoaderOption());
		loader.displayImage(bean.getBgUrl(1), bgView2,MyApplication.getImageLoaderOption());
		loader.displayImage(bean.getBgUrl(2), bgView3,MyApplication.getImageLoaderOption());
		loader.displayImage(bean.getIconUrl(0), iconView1,MyApplication.getImageLoaderOption());
		loader.displayImage(bean.getIconUrl(1), iconView2,MyApplication.getImageLoaderOption());
		loader.displayImage(bean.getIconUrl(2), iconView3,MyApplication.getImageLoaderOption());
		titleView1.setText(bean.getTitle(0));
		titleView2.setText(bean.getTitle(1));
		titleView3.setText(bean.getTitle(2));
		msgView1.setText(bean.getMsg(0));
		msgView2.setText(bean.getMsg(1));
		msgView3.setText(bean.getMsg(2));
		rootLayout1.setOnClickListener(this);
		rootLayout2.setOnClickListener(this);
		rootLayout3.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(clickListener!=null){
			clickListener.onClick(v);
		}
		switch (v.getId()) {
		case R.id.item_home_big_three_102_root1:
			onClick(0);
			break;
		case R.id.item_home_big_three_102_root2:
			onClick(1);
			break;
		case R.id.item_home_big_three_102_root3:
			onClick(2);
			break;

		default:
			break;
		}
	}

	private void onClick(int i){
		if(bean.getIntentSize()<=i){
			return;
		}
		switch (bean.getIntent(i)) {
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
	
	public HomeBigThreeItem_102(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public HomeBigThreeItem_102(Context context) {
		super(context);
		this.context = context;
		init();
	}
	public HomeBigThreeItem_102Bean getBean() {
		return bean;
	}
	public void setBean(HomeBigThreeItem_102Bean bean) {
		this.bean = bean;
	}
	public OnClickListener getClickListener() {
		return clickListener;
	}
	public void setClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}
	public HomeBigThreeItem_102(Context context, HomeBigThreeItem_102Bean bean, OnClickListener clickListener) {
		super(context);
		this.bean = bean;
		this.context = context;
		this.clickListener = clickListener;
		init();
	}
	public HomeBigThreeItem_102(Context context, HomeBigThreeItem_102Bean bean,
			OnClickListener clickListener, int windowHeight) {
		super(context);
		this.bean = bean;
		this.context = context;
		this.clickListener = clickListener;
		this.windowHeight = windowHeight;
		init();
	}
	public HomeBigThreeItem_102(Context context, HomeBigThreeItem_102Bean bean, int windowHeight) {
		super(context);
		this.context = context;
		this.bean = bean;
		this.windowHeight = windowHeight;
		init();
	}
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}
	
//	@Override
//	public void onWindowFocusChanged(boolean hasWindowFocus) {
//		super.onWindowFocusChanged(hasWindowFocus);
//		WindowManager m = ((Activity) context).getWindowManager();
//		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//		ViewGroup.LayoutParams p = getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
//		p.height = (int)(d.getHeight() * 0.25);
//		setLayoutParams(p);
//	}
}
