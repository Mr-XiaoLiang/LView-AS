package mr_xiaoliang.com.github.lview_as.view.shopitem;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.view.shopitem.bean.HomeDivisionTitleItem_108Bean;

/**
 * 分割标题
 * @author LiuJ
 *
 */
public class HomeDivisionTitleItem_108 extends FrameLayout implements OnClickListener {

	private ImageView img;
	private TextView text;
	private View line;
	private HomeDivisionTitleItem_108Bean bean;
	private Context context;
	private FrameLayout root;
	private ImageLoader imageLoader;
	private OnClickListener listener;
	
	private void getView(Context context){
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.item_home_division_title_108,
				this, true);
		root = (FrameLayout) findViewById(R.id.item_home_division_title_108_root);
		img = (ImageView) findViewById(R.id.item_home_division_title_108_img);
		text = (TextView) findViewById(R.id.item_home_division_title_108_text);
		line = findViewById(R.id.item_home_division_title_108_line);
		root.setOnClickListener(this);
		imageLoader = ImageLoader.getInstance();
	}
	
	private void init(){
		if(bean==null){
			return;
		}
		line.setBackgroundColor(bean.getColor());
		text.setTextColor(bean.getColor());
		text.setText(bean.getText());
		if(bean.getImg()==null||bean.getImg().equals("")){
			img.setVisibility(View.GONE);
		}else{
			imageLoader.displayImage(bean.getImg(), img);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(listener!=null)
			listener.onClick(v);
	}

	public HomeDivisionTitleItem_108(Context context, AttributeSet attrs) {
		super(context, attrs);
		getView(context);
	}

	public HomeDivisionTitleItem_108(Context context) {
		super(context);
		getView(context);
	}

	public HomeDivisionTitleItem_108(Context context, HomeDivisionTitleItem_108Bean bean, OnClickListener listener) {
		super(context);
		this.bean = bean;
		this.listener = listener;
		getView(context);
		init();
	}

	public HomeDivisionTitleItem_108(Context context, HomeDivisionTitleItem_108Bean bean) {
		super(context);
		this.bean = bean;
		getView(context);
		init();
	}

	public HomeDivisionTitleItem_108Bean getBean() {
		return bean;
	}

	public void setBean(HomeDivisionTitleItem_108Bean bean) {
		this.bean = bean;
		init();
	}

	public OnClickListener getListener() {
		return listener;
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}
	
}
