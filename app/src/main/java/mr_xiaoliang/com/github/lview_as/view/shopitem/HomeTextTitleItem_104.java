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
import android.widget.LinearLayout;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeTextTitleItem_104Bean;

/**
 * 文字标题
 * @author LiuJ
 *
 */
public class HomeTextTitleItem_104 extends LinearLayout implements OnClickListener {

	private Context context;
	private OnClickListener listener;
	private TextView textView;
	private HomeTextTitleItem_104Bean bean;
	private LinearLayout root;
	private int windowHeight = 0;
	
	@Override
	public void onClick(View v) {
		if(listener!=null){
			listener.onClick(v);
		}
		if(bean==null)
			return;
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
	}

	public HomeTextTitleItem_104(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	private void init(){
		LayoutInflater.from(context).inflate(R.layout.item_home_text_title_104,
				this, true);
		root = (LinearLayout) findViewById(R.id.item_home_text_title_104_root);
		textView = (TextView) findViewById(R.id.item_home_text_title_104_text);
		textView.setOnClickListener(this);
		if (windowHeight < 1) {
			WindowManager m = ((Activity) context).getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			windowHeight = d.getHeight();
		}
		ViewGroup.LayoutParams p = root.getLayoutParams();// getWindow().getAttributes();
		p.height = (int) (windowHeight * 0.08);
		root.setLayoutParams(p);
		if(bean==null){
			return;
		}
		dataSet();
	}
	private void dataSet(){
		textView.setText(bean.getText());
	}
	public HomeTextTitleItem_104(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public HomeTextTitleItem_104(Context context, HomeTextTitleItem_104Bean bean, OnClickListener listener) {
		super(context);
		this.listener = listener;
		this.bean = bean;
		this.context = context;
		init();
	}

	public HomeTextTitleItem_104(Context context, OnClickListener listener, HomeTextTitleItem_104Bean bean,
			int windowHeight) {
		super(context);
		this.listener = listener;
		this.bean = bean;
		this.windowHeight = windowHeight;
		this.context = context;
		init();
	}

	public HomeTextTitleItem_104(Context context, HomeTextTitleItem_104Bean bean, int windowHeight) {
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
